package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MenuPage extends BasePage {

    private static final By BURGER_MENU_BTN = By.id("react-burger-menu-btn");
    private static final By CLOSE_MENU_BTN  = By.id("react-burger-cross-btn");
    private static final By LOGOUT_LINK     = By.id("logout_sidebar_link");
    private static final By ALL_ITEMS_LINK  = By.id("inventory_sidebar_link");
    private static final By RESET_APP_LINK  = By.id("reset_sidebar_link");

    public MenuPage(WebDriver driver) {
        super(driver);
    }

    public void openMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(BURGER_MENU_BTN));
        click(BURGER_MENU_BTN);
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGOUT_LINK));
    }

    public void closeMenu() {
        click(CLOSE_MENU_BTN);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(LOGOUT_LINK));
    }

    public LoginPage logout() {
        openMenu();
        wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_LINK));
        click(LOGOUT_LINK);
        // Wait for redirect to login page
        wait.until(ExpectedConditions.urlToBe(LoginPage.URL));
        return new LoginPage(driver);
    }

    public InventoryPage goToAllItems() {
        openMenu();
        wait.until(ExpectedConditions.elementToBeClickable(ALL_ITEMS_LINK));
        click(ALL_ITEMS_LINK);
        return new InventoryPage(driver);
    }

    public void resetAppState() {
        openMenu();
        wait.until(ExpectedConditions.elementToBeClickable(RESET_APP_LINK));
        click(RESET_APP_LINK);
    }

    public boolean isMenuOpen() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(LOGOUT_LINK));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
