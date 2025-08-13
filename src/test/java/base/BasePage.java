package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class BasePage{
    protected final Logger log = LogManager.getLogger(getClass());
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.explicitWaitSec()));
    }

    protected WebElement $(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator){
        log.debug("Click: {}", locator);
        $(locator).click();
    }

    protected void type(By locator, String text){
        log.debug("Type: {} -> {}", locator,text);
        WebElement el = $(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected String text(By locator){
        return $(locator).getText();
    }
}