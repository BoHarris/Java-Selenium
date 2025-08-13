package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import io.qameta.allure.testng.AllureTestNg;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.BeforeSuite;
import java.util.Properties;

import utils.Allure.AllureEnv;
import utils.ConfigReader;
import utils.ScreenshotUtil;
import java.time.Duration;


@Listeners({AllureTestNg.class})
public abstract class BaseTest{
    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    private static final ThreadLocal<WebDriver> TL_DRIVER = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true)
    public void writeAllureEnvironment(){
        Properties extra = new Properties();
        extra.setProperty("Selenium", "4.24.0");
        extra.setProperty("TestNG", "7.10.2");
        AllureEnv.write(extra);
    }

    @Parameters({"browser", "headless"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional String browser, @Optional String headless){
        String b = (browser != null && !browser.isBlank()) ? browser : ConfigReader.browser();
        boolean isHeadless = (headless != null) ? Boolean.parseBoolean(headless) : ConfigReader.headless();

        WebDriver driver = createDriver(b,isHeadless);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.pageLoadTimeoutSec()));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.implicitWaitSec()));
        TL_DRIVER.set(driver);

        log.info("Opening base URL: {}", ConfigReader.baseUrl());
        driver.get(ConfigReader.baseUrl());
    }

    private WebDriver createDriver(String browser, boolean headless){
        switch(browser.toLowerCase()){
            case "firefox" ->{
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ff = new FirefoxOptions();
                ff.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                if(headless) ff.addArguments("-headless");
                return new FirefoxDriver(ff);
            }
            case "edge" ->{
                WebDriverManager.edgedriver().setup();
                EdgeOptions eo = new EdgeOptions();
                eo.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                if(headless) eo.addArguments("--headless=new");
                return new EdgeDriver(eo);
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions co = new ChromeOptions();
                co.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                if(headless) co.addArguments("--headless=new");
                return new ChromeDriver(co);
            }
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result){
        if (result.getStatus() == ITestResult.FAILURE && getDriver() != null){
            log.error("Test failed: {}", result.getName());
            saveScreenshot();
            attachPageSource(new String(getDriver().getPageSource().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        }
        if(getDriver() != null){ getDriver().quit(); TL_DRIVER.remove();}
    }

    @Attachment(value = "Page Source", type = "text/html")
    public String attachPageSource(String html){return html;}


    public WebDriver getDriver(){
        return TL_DRIVER.get();
    }

    @Attachment(value = "Failure Screenshot", type = "image/png")
    public byte[] saveScreenshot(){
        return ScreenshotUtil.takePng(getDriver());
    }

}

