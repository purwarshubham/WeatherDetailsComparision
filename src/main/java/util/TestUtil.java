package util;


import base.TestBase;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class TestUtil extends TestBase {


    private static Logger log = Logger.getLogger(TestUtil.class);

    public static boolean compare(int a, int b, int expectedDiff){

        log.info("Value seen in Web - " + a + ", Value received from API Response - " + b);
        int sub = Math.abs(a - b);

        if ( expectedDiff > sub){
            log.error("Difference found is : " + sub + ", which is lesser than than expected : " + expectedDiff);
            return true;
        }
        log.error("Difference found is : " + sub + ", which is greater than expected : " + expectedDiff);
        return false;
    }

    public static boolean compare(float a, float b, float expectedDiff){

        log.info("Value seen in Web - " + a + ", Value received from API Response - " + b);
        float sub = Math.abs(a - b);

        if ( expectedDiff > sub){
            log.info("Difference found is : " + sub + ", which is lesser than than expected : " + expectedDiff);
            return true;
        }
        log.error("Difference found is : " + sub + ", which is greater than expected : " + expectedDiff);
        return false;
    }

    public static String takeScreenShot(WebDriver driver) throws IOException {

        String fileName = "screenshots/" + System.currentTimeMillis() + ".png";
        File screenShotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        File destination = new File(fileName);

        FileUtils.copyFile(screenShotFile, destination);
        return String.valueOf(destination);

    }

}
