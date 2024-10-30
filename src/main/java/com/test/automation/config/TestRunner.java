package com.test.automation.config;

public class TestRunner {
    public static void main(String[] args) {
        // Load the desired environment
        ConfigurationNodeTree.loadEnvironment("cdev"); // Change this to "development", "cqat", "preprod", or "prod" as needed
        ConfigurationNodeTree config = ConfigurationNodeTree.getInstance();

        // Example of using the configuration
        System.out.println("Browser: " + config.getBrowserType());
        System.out.println("Base URL: " + config.getBaseUrl());
        System.out.println("Implicit Wait: " + config.getImplicitWaitSeconds() + " seconds");
        System.out.println("Page Load Timeout: " + config.getPageLoadTimeoutSeconds() + " seconds");
        System.out.println("Headless: " + config.isHeadless());

        // Here you can initialize your WebDriver using config.getBrowserType() and config.getBaseUrl()
        // Run your tests...
    }
}
