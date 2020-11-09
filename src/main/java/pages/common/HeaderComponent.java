package pages.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.WeatherPage;

public class HeaderComponent {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//a[text()=\"WEATHER\"]")
    private WebElement weatherLink;

    @FindBy(css = "a.topnavmore")
    private WebElement tapNavMore;

    @FindBy(linkText = "LIVE TV")
    private WebElement liveTV;

    public HeaderComponent(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver,this);
        this.wait = new WebDriverWait(this.driver,20);
    }

    public void clickOnLiveTVLink(){
        liveTV.click();
    }

    public WeatherPage clickOnWeatherLink() {
        this.wait.until(t -> this.tapNavMore.isDisplayed());
        tapNavMore.click();
        this.wait.until(t -> this.weatherLink.isDisplayed());
        weatherLink.click();
        return new WeatherPage(driver);
    }

}

