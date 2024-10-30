package com.test.automation.pages;
import com.test.automation.actions.WebActions;
import com.test.automation.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebActions webActions;
    protected WaitUtils waitUtils;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.webActions = new WebActions(driver);
        this.waitUtils = new WaitUtils(driver, Duration.ofSeconds(10));
    }
}