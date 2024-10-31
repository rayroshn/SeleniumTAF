package com.test.automation.utils;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {

    public static void takeScreenshot(WebDriver driver, String screenshotName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            // Save the screenshot to a desired location
            FileUtils.copyFile(screenshot, new File("screenshots/" + screenshotName + ".png"));

            // Attach screenshot to Allure report
            Allure.addAttachment(screenshotName, new ByteArrayInputStream(FileUtils.readFileToByteArray(screenshot)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
