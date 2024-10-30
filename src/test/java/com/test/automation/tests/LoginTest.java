package com.test.automation.tests;

import com.test.automation.pages.AccountPage;
import com.test.automation.pages.LoginPage;
import com.test.automation.data.TestDataBuilder;
import com.test.automation.utils.CredentialsLoader;
import com.test.automation.utils.CredentialsLoaderOld;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;


@Log4j2
@Feature("Authentication")
public class LoginTest extends BaseTest {

    @Test
    @Story("User Login")
    public void testSuccessfulLogin() throws InterruptedException {
        //LoginPage loginPage = new LoginPage(driver);
        TestDataBuilder testData = TestDataBuilder.generateRandomData();
        // Retrieve credentials using CredentialsLoader
       // CredentialsLoaderOld loader = new CredentialsLoaderOld();
        CredentialsLoader credentialsLoader = new CredentialsLoader();
        log.info("loader.getUsername() = " + credentialsLoader.getUsername().toString());
        pageObjectManager.getLoginPage().login(credentialsLoader.getUsername(),credentialsLoader.getPassword());
       // loginPage.login(credentialsLoader.getUsername(), credentialsLoader.getPassword());
        // Add assertions here
        //AccountPage accountPage = new AccountPage(driver);
        //String logoutBtnText = accountPage.getLogoutButtonText();
        String logoutBtnText=pageObjectManager.getAccountPage().getLogoutButtonText();
        Assert.assertEquals(logoutBtnText,"Logout");


    }

}
