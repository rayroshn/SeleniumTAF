package com.test.automation.pages;

import org.openqa.selenium.WebDriver;

public class PageObjectManager {
    private WebDriver driver;

    // Lazy-loaded page objects
    private HomePage homePage;
    private LoginPage loginPage;
    private AccountPage accountPage;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    // Home Page object creation
    public HomePage getHomePage() {
        if (homePage == null) {
            homePage = new HomePage(driver);
        }
        return homePage;
    }

    //Login page object creation
    public LoginPage getLoginPage()
    {
        if(loginPage ==null)
        {
            loginPage = new LoginPage(driver);
        }
        return  loginPage;
    }

    public AccountPage getAccountPage() {

        if(accountPage == null)
        {
            accountPage = new AccountPage(driver);
        }
        return accountPage;
    }
}
