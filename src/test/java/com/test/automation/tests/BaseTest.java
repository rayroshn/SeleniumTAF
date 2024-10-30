package com.test.automation.tests;

import com.test.automation.config.ConfigurationNodeTree;
import com.test.automation.driver.DriverFactory;
import com.test.automation.config.Configuration;
import com.test.automation.pages.PageObjectManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BaseTest {
    protected WebDriver driver;
   // protected Configuration config;
    protected ConfigurationNodeTree config;
    protected PageObjectManager pageObjectManager;

    @BeforeMethod
    public void setUp() {
       // config = Configuration.getInstance();
        config.loadEnvironment("cdev");
        config= ConfigurationNodeTree.getInstance();
        driver = DriverFactory.getDriver();
        pageObjectManager = new PageObjectManager(driver);
        driver.get(config.getBaseUrl());
        log.info("Started test with URL...: {}", config.getBaseUrl());
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
