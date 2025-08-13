package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;

import utils.ConfigReader;

public class LoginPage extends BasePage{
    private static final String PATH = "/login";

    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By loginBtn = By.cssSelector("button[type='submit']");
    private final By flash = By.id("flash");

    public LoginPage(WebDriver driver){super(driver);}


    @Step("Open login Page")
    public LoginPage open(){driver.navigate().to(ConfigReader.baseUrl() + PATH); return this;}

    @Step("Type username: {user}")
    public LoginPage enterUsername(String user){type(username,user); return this;}

    @Step("Type password")
    public LoginPage enterPassword(String pass){ type(password, pass); return this;}

    @Step("Submit login form")
    public LoginPage submit(){ click(loginBtn); return this;}


    public String getFlashMessage(){ return text(flash);}
}