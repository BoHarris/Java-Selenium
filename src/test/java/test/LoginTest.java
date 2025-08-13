package test;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;


@Epic("Authentication")
@Feature("Login")
@Owner("Bokha")
public class LoginTest extends BaseTest{

    @Story("Valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "User logs in with valid credentials and sees successful banner")
    public void validLogin_showsSuccessBanner(){
        var page = new LoginPage(getDriver())
                .open()
                .enterUsername("tomsmith")
                .enterPassword("SuperSecretPassword!")
                .submit();
        Assert.assertTrue(page.getFlashMessage().contains("You logged into a secure area!"));
    }

    @Story("Invalid credentials")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "User sees error with invalid credentials")
    public void invalidLogin_showsError(){
        var page = new LoginPage(getDriver())
                .open()
                .enterUsername("bad-user")
                .enterPassword("bad-pass")
                .submit();
        Assert.assertTrue(page.getFlashMessage().toLowerCase().contains("your username is invalid"));

    }
}