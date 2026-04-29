package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * BaseTest — handles WebDriver lifecycle for all test classes.
 * Uses WebDriverManager to auto-detect the installed Chrome version.
 */
public class BaseTest {

    protected WebDriver driver;

    protected static final String STANDARD_USER    = "standard_user";
    protected static final String LOCKED_USER      = "locked_out_user";
    protected static final String PROBLEM_USER     = "problem_user";
    protected static final String VALID_PASSWORD   = "secret_sauce";
    protected static final String INVALID_PASSWORD = "wrong_password";

    @BeforeMethod
    public void setUp() {
        // Let WebDriverManager detect the installed Chrome version automatically
        WebDriverManager.chromedriver()
                .clearDriverCache()
                .setup();

        ChromeOptions options = new ChromeOptions();

        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"))
                || "true".equals(System.getenv("CI"));
        if (headless) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
