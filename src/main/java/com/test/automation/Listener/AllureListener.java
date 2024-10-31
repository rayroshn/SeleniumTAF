package com.test.automation.Listener;

import com.test.automation.driver.DriverFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AllureListener implements ITestListener {

    private WebDriver driver;

    public AllureListener(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        saveScreenshotForAllure("Screenshot on Success");
        //captureScreenshot();
        DriverFactory.quitDriver();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        saveScreenshotForAllure("Screenshot on Failure");
        //captureScreenshot()
        DriverFactory.quitDriver();
    }

   @Attachment(value = "Step Screenshot", type = "image/png")
    public byte[] saveScreenshotForAllure(String screenshotName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            return FileUtils.readFileToByteArray(srcFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*@Attachment(value = "Step Screenshot", type = "image/png")
    public  byte[] captureScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }*/
}
