package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private static final By USERNAME_INPUT = By.id("user-name");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOGIN_BUTTON   = By.id("login-button");
    private static final By ERROR_MESSAGE  = By.cssSelector("[data-test='error']");

    public static final String URL = "https://www.saucedemo.com/";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        driver.get(URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT));
        type(USERNAME_INPUT, username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_INPUT));
        type(PASSWORD_INPUT, password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
        click(LOGIN_BUTTON);
    }

    public InventoryPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return new InventoryPage(driver);
    }

    public boolean isErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
        return getText(ERROR_MESSAGE);
    }

    public boolean isOnLoginPage() {
        try {
            wait.until(ExpectedConditions.urlToBe(URL));
            wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
