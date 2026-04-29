package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import pages.MenuPage;

/**
 * LogoutTest - Verifies logout behaviour via the burger menu.
 */
public class LogoutTest extends BaseTest {

    private MenuPage menuPage;

    @BeforeMethod
    public void loginFirst() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.login(STANDARD_USER, VALID_PASSWORD);
        menuPage = new MenuPage(driver);
    }

    @Test(description = "Burger menu opens and is visible")
    public void testBurgerMenuOpens() {
        menuPage.openMenu();

        Assert.assertTrue(menuPage.isMenuOpen(),
                "Burger menu should be visible after clicking the menu button");
    }

    @Test(description = "Logout redirects user back to login page")
    public void testLogoutRedirectsToLogin() {
        LoginPage loginPage = menuPage.logout();

        Assert.assertTrue(loginPage.isOnLoginPage(),
                "Should be redirected to the login page after logout");
    }

    @Test(description = "User cannot access inventory after logout")
    public void testCannotAccessInventoryAfterLogout() {
        menuPage.logout();

        // Attempt direct navigation to inventory
        driver.get("https://www.saucedemo.com/inventory.html");

        // Should be bounced back to login
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isOnLoginPage(),
                "Accessing inventory after logout should redirect to login page");
    }

    @Test(description = "After logout, re-login works successfully")
    public void testReLoginAfterLogout() {
        LoginPage loginPage = menuPage.logout();
        InventoryPage inventoryPage = loginPage.login(STANDARD_USER, VALID_PASSWORD);

        Assert.assertTrue(inventoryPage.isOnInventoryPage(),
                "Re-login after logout should succeed and land on inventory page");
    }
}
