package ExtentReportListener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import util.TestUtil;

import java.io.IOException;

import static base.TestBase.getDriverDetails;


//Extent Report Declarations

public class ExtentTestNGITestListener implements ITestListener {

    private final ExtentReports extent =  ExtentManager.createInstance();
    private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();


    @Override
    public synchronized void onStart(ITestContext context) {
        ExtentTest parent = extent.createTest(context.getName());
        parentTest.set(parent);
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        ExtentTest child = parentTest.get()
                .createNode(result.getMethod().getMethodName());
        test.set(child);

    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");
    }



    @Override
    public synchronized void onTestFailure(ITestResult result) {
        String path = null;
        WebDriver driver = getDriverDetails(result.getTestClass().getName());
        if (null == driver) {
            test.get()
                    .log(Status.FAIL,
                            "Screenshot is not getting displayed because, no driver is registered with caller test class. Please check Test class setup method.");
        } else {
            try {
                path = TestUtil.takeScreenShot(driver);
                System.out.println(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                test.get()
                        .log(Status.FAIL,
                                result.getThrowable().getMessage(),
                                MediaEntityBuilder.createScreenCaptureFromPath(path).build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        test.get().skip(result.getThrowable());
    }

    @Override
    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod()
                .getMethodName()));
    }
}
