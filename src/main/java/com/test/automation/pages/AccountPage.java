package com.test.automation.pages;

import com.test.automation.actions.WebActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

public class AccountPage extends BasePage {
    private final By welcomeMessage = By.className("welcome-text");
    private final By userMenu = By.id("userMenu");
    private final By logoutButton = By.xpath("//a[normalize-space()='Logout']");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get welcome message")
    public String getWelcomeMessage() {
        return driver.findElement(welcomeMessage).getText();
    }

    @Step("Logout from application")
    public LoginPage logout() {
        webActions.click(userMenu);
        webActions.click(logoutButton);
        return new LoginPage(driver);
    }

    @Step("Logout Button Text from Account page")
    public String getLogoutButtonText() {
        WebElement element = driver.findElement(logoutButton);
        return new WebActions(driver).getElementText(element);

    }
}
