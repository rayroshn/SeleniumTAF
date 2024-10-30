package com.test.automation.tests;

import com.test.automation.pages.HomePage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Featured Products")
public class HomePageTest extends BaseTest {

    @Test
    @Story("Add To Cart")
    public void addProductTCart() throws InterruptedException {

    pageObjectManager.getHomePage().addProductToCart("Denim Blue Jeans");
     // homePage.addProductToCart("Denim Blue Jeans");

    }

    @Test
    @Story("Add To Cart")
    public void addProductTCart1() throws InterruptedException {

        pageObjectManager.getHomePage().addProductToCart("Denim Blue Jeans");
        //homePage.addProductToCart("Denim Blue Jeans");

    }

}
