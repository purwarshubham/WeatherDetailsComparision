import base.TestBase;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.WeatherPage;
import pages.common.HeaderComponent;
import model.WeatherDetails;
import util.TestUtil;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WeatherPageTest extends TestBase {

    private WebDriver driver;
    private HeaderComponent hd ;
    private WeatherPage wp;
    private WeatherDetails web_wd;
    private WeatherDetails api_wd;

    private String expectedWeatherPageUrlContains = "/static/Weather/report/";
    Logger log = Logger.getLogger(WeatherPage.class);

    @BeforeClass()
    public void setup(){
        TestBase tb = new TestBase();
        driver = tb.initialization();
        driver.get(prop.getProperty("url"));
    }

    @Test(description = "Verify after click on news link, user is redirected to News Page", priority = 1)
    public void validateUrl() throws InterruptedException {
        hd = new HeaderComponent(driver);
        wp =  hd.clickOnWeatherLink();
        String url = wp.getCurrentUrl();
        assertThat(url, containsString(expectedWeatherPageUrlContains));
    }


    @Parameters({"cityName"})
    @Test(description = "Enter City name and select and validate and verify same is displaying on Map", priority = 2)
    public void validateCityNameIsPresentAfterSearch(String cityName) {
        wp.enterCityInSearchBox(cityName);
        wp.selectCity();

        boolean isCityDisplayedOnMap = wp.cityIsDisplayedInMap();
        assertThat(true, is(isCityDisplayedOnMap));
    }


    @Test(description = "Verify Web Response", priority = 3)
    public void validateWebResponse() {

        HashMap<String, String> hm = wp.getWeatherInfoOfCity();

        assertThat(true, is(not(hm.isEmpty())));

        web_wd = new WeatherDetails();

        web_wd.setTemp(Float.parseFloat(hm.get("temp")));
        web_wd.setHumidity(Integer.parseInt(hm.get("humidity")));
        web_wd.setWind(Float.parseFloat(hm.get("wind")));


    }

    @Parameters({"cityName"})
    @Test(description = "Verify API Response", priority = 4)
    public void validateAPIResponse(String cityName){

        Response res = wp.getAPIResponse(cityName).then().statusCode(200)
                .contentType(ContentType.JSON)
                .assertThat()
                .time(lessThan(5L), TimeUnit.SECONDS).extract().response();

        api_wd = new WeatherDetails();

        api_wd.setTemp(res.path("main.temp"));
        api_wd.setHumidity(res.path("main.humidity"));
        api_wd.setWind(res.path("wind.speed"));

    }

    @Test(description = "Compare Temperature value of Web Response against API Response", priority = 5)
    @Parameters({"tempDiff"})
    public void validateTemperatureValueOfAPIResponseAgainstWebResult(String tempDiff){
        boolean actual = TestUtil.compare(web_wd.getTemp(), api_wd.getTemp(), Float.parseFloat(tempDiff));
        assertThat(true, is(actual));
    }

    @Test(description = "Compare Humidity value of Web Response against API Response", priority = 6)
    @Parameters({"humidityDiff"})
    public void validateHumidityValueOfAPIResponseAgainstWebResult(String humidityDiff){
        boolean actual = TestUtil.compare(web_wd.getHumidity(), api_wd.getHumidity(), Integer.parseInt(humidityDiff));
        assertThat(true, is(actual));
    }

    @Test(description = "Compare Wind Speed value of Web Response against API Response", priority = 7)
    @Parameters({"windSpeedDiff"})
    public void validateWindSpeedValueOfAPIResponseAgainstWebResult(String windSpeedDiff) {
        boolean actual = TestUtil.compare(web_wd.getWind(), api_wd.getTemp(), Float.parseFloat(windSpeedDiff));
        assertThat(true, is(actual));
    }

    @AfterClass()
    public void tearDown(){
        driver.close();
    }

    @AfterSuite
    public void quitDriver() {
        if (null != gerDriverObjMap().get(getClass().getName())) {
            gerDriverObjMap().get(getClass().getName()).quit();
            gerDriverObjMap().remove(getClass().getName());
            log.info("driver for class : " + getClass().getName() + "is closed");
        }
    }
}
