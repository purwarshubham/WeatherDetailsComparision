package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterSuite;
import util.WebEventListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static Properties prop;
    private static final HashMap<String, WebDriver> DRIVER_OBJ_MAP = new HashMap<>();
    EventFiringWebDriver eDriver;
    WebEventListener eventListener;

    public TestBase(){
        prop = new Properties();
        FileInputStream fi = null;
        String currDirectory = System.getProperty("user.dir");

        try {
            fi = new FileInputStream(currDirectory+"/src/main/java/config/config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            prop.load(fi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WebDriver initialization(){

        WebDriver driver = null;
        String browserName = prop.getProperty("browserName");

        if ("chrome".equals(browserName)){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        if ("fireFox".equals(browserName)){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        DRIVER_OBJ_MAP.put(stackTrace[1].getClassName(), driver);

        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        eDriver = new EventFiringWebDriver(driver);
        eventListener = new WebEventListener();
        eDriver.register(eventListener);
        driver = eDriver;
        return driver;

    }

    public static WebDriver getDriverDetails(String className) {
        return DRIVER_OBJ_MAP.get(className);
    }

    public static HashMap<String, WebDriver> gerDriverObjMap() {
        return DRIVER_OBJ_MAP;
    }


}
