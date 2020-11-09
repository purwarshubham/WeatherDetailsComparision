package util;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;


public class WebEventListener implements WebDriverEventListener {

    Logger log = Logger.getLogger(WebEventListener.class);

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        log.info("Navigating back to previous page");
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        log.info("Navigated back to previous page");
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        log.info("Navigating forward to next page");
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        log.info("Navigated forward to next page");
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        log.info("Before navigating to: '" + url + "'");
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        log.info("Navigated to:'" + url + "'");
    }

    public void beforeChangeValueOf(WebElement element, WebDriver driver) {
        log.info("Value of the:" + element.toString() + " before any changes made");
    }

    public void afterChangeValueOf(WebElement element, WebDriver driver) {
        log.info("Element value changed to: " + element.toString());
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        log.info("Trying to click on: " + element.toString());
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        log.info("Clicked on: " + element.toString());
    }


    @Override
    public void beforeAlertAccept(WebDriver driver) {

    }

    @Override
    public void afterAlertAccept(WebDriver driver) {

    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {

    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {

    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }

    @Override
    public void beforeScript(String script, WebDriver driver) {

    }

    @Override
    public void afterScript(String script, WebDriver driver) {

    }

    @Override
    public void beforeSwitchToWindow(String windowName, WebDriver driver) {

    }

    @Override
    public void afterSwitchToWindow(String windowName, WebDriver driver) {

    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {

    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {

    }

    @Override
    public void onException(Throwable error, WebDriver driver) {
      log.error("Exception occurred: " + error.getMessage());
    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> target) {

    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {

    }

    @Override
    public void beforeGetText(WebElement element, WebDriver driver) {

    }

    @Override
    public void afterGetText(WebElement element, WebDriver driver, String text) {

    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        log.info("Trying to find Element By : " + by.toString());
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
       log.info("Found Element By : " + by.toString());
    }


}