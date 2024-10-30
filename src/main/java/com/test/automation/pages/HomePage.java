package com.test.automation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {


    private final By homeLink= By.xpath("//div[@class=\"main-navigation ast-inline-flex\"]//ul[@id=\"ast-hf-menu-1\"]//li//a[text() = 'Home']");
    private final By addToCartBtn =By.xpath("//h2[text()='%s']/ancestor::div[contains(@class, 'astra-shop-summary-wrap')]//a[contains(@class, 'add_to_cart_button')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Adding Product with ProductName: {0} ")
    public HomePage addProductToCart(String ProductName) throws InterruptedException {
        webActions.click(homeLink);
        webActions.click(ProductName,addToCartBtn);

       // webActions.click(loginButton);
        Thread.sleep(2);
        return new HomePage(driver);
    }
}
