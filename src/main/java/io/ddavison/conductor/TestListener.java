package io.ddavison.conductor;

import io.ddavison.conductor.util.ScreenShotUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private Locomotive driver;

    @Override
    public void onTestStart(ITestResult iTestResult) {
        driver = (Locomotive) iTestResult.getInstance();
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (driver.configuration.isScreenshotOnFail()) {
            ScreenShotUtil.take(driver,
                    result.getTestClass().getName() + "." + result.getMethod().getMethodName(),
                    result.getThrowable().getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        driver.quit();
    }
}
