package pages;

import base.TestBase;
import com.google.common.util.concurrent.Uninterruptibles;
import constants.EndPoint;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;


public class WeatherPage extends TestBase {

    private WebDriver driver;
    private WebDriverWait wait;
    private String cityName;
    private Logger log;

    @FindBy(id = "searchBox")
    private WebElement searchBox;

    @FindBy(xpath = "//div/div[@style='']")
    private List<WebElement> searchedElement;

    @FindBy(css= "div.outerContainer")
    private List<WebElement> cityOnMap;

    @FindBy(xpath = "//div[@class='leaflet-popup-content']/div/span[1]")
    private WebElement conditionInfo ;

    @FindBy(xpath = "//div[@class='leaflet-popup-content']/div/span[2]")
    private WebElement windInfo ;

    @FindBy(xpath = "//div[@class='leaflet-popup-content']/div/span[3]")
    private WebElement humidityInfo ;

    @FindBy(xpath = "//div[@class='leaflet-popup-content']/div/span[4]")
    private WebElement tempInDegree ;

    @FindBy(xpath = "//div[@class='leaflet-popup-content']/div/span[5]")
    private WebElement tempInFarehn ;


    public WeatherPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        wait = new WebDriverWait(this.driver, 20);
        log = Logger.getLogger(this.getClass());
    }

    // Enter City name in Search Box
    public void enterCityInSearchBox(String cityName){
        this.cityName = cityName;
        wait.until( t -> searchBox.isDisplayed());
        searchBox.clear();
        searchBox.sendKeys(this.cityName);
        searchBox.sendKeys(Keys.ENTER);
    }

    // Select City
    public void selectCity(){
        WebElement ele =  this.driver.findElement(By.id(""+ cityName +""));
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        // Select city name is not already selected
        if (!ele.getAttribute("class").equals("defaultChecked")){
            ele.click();
        }
    }

    // Get Current Url of the page
    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public boolean cityIsDisplayedInMap(){

        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        List<String> cities = cityOnMap.stream().map(t -> t.getAttribute("title")).collect(Collectors.toList());

        if (!cities.contains(this.cityName)){
            log.error("CityName is not Present in the Map");
            return false;
        }
        log.info("CityName is present in the Map");
        return true;
    }

    // Get weather City information
    public HashMap<String, String> getWeatherInfoOfCity() {

        cityOnMap.stream().filter(t -> t.getAttribute("title").equals(this.cityName)).findFirst().get().click();

        System.out.println("CCity Name : " + this.cityName);
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

        String wind = windInfo.getText().split(":")[1].trim().substring(0,3);
        String humidity = humidityInfo.getText().split(":")[1].trim().substring(0,2);
        String temp = tempInFarehn.getText().split(":")[1].trim().substring(0,2);

        HashMap<String, String> weatherDetails = new HashMap<>();
        weatherDetails.put("wind", wind);
        weatherDetails.put("humidity", humidity);
        weatherDetails.put("temp", temp);

        log.info("Weather Details : " + weatherDetails);

        return weatherDetails;

        }

    // Get API Response
    public Response getAPIResponse(String cityName){

        Response res =  given()
                .queryParam("q", cityName)
                .queryParam("appid", prop.getProperty("key"))
                .queryParam("units", prop.getProperty("tempunit"))
                .when()
                .get(prop.getProperty("baseUrl")+ EndPoint.WEATHER);

        log.info(res.asString());

        return res;
    }

}

