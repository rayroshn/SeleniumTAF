package com.test.automation.pages;


import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    // Page locators
    private final By usernameInput = By.xpath("//input[@id='username']");
    private final By passwordInput = By.xpath("//input[@id='password']");
    private final By loginButton = By.xpath("//button[normalize-space()='Log in']");
    private final By rememberMeCheckbox = By.xpath("//span[normalize-space()='Remember me']");
    private final By forgotPasswordLink = By.xpath("//a[normalize-space()='Lost your password?']");
    private final By accountLink= By.xpath("//li[@id='menu-item-1237']//a[@class='menu-link'][normalize-space()='Account']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Login with username: {0} and password: {1}")
    public AccountPage login(String username, String password) throws InterruptedException {
        webActions.click(accountLink);
        webActions.enterText(usernameInput, username);
        webActions.enterText(passwordInput, password);
        webActions.click(loginButton);
        Thread.sleep(2);
        return new AccountPage(driver);
    }

   /* @Step("Click Forgot Password link")
    public ForgotPasswordPage clickForgotPassword() {
        webActions.click(forgotPasswordLink);
        return new ForgotPasswordPage(driver);
    }*/

    @Step("Toggle Remember Me checkbox")
    public LoginPage toggleRememberMe() {
        webActions.click(rememberMeCheckbox);
        return this;
    }

    public boolean isLoginButtonEnabled() {
        return driver.findElement(loginButton).isEnabled();
    }
}