package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

/**
 * LoginTest - Verifies all login scenarios on SauceDemo.
 */
public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void navigateToLogin() {
        loginPage = new LoginPage(driver);
        loginPage.navigateTo();
    }

    @Test(description = "Successful login with valid standard_user credentials")
    public void testValidLogin() {
        InventoryPage inventoryPage = loginPage.login(STANDARD_USER, VALID_PASSWORD);

        Assert.assertTrue(inventoryPage.isOnInventoryPage(),
                "Should be redirected to inventory page after successful login");
        Assert.assertEquals(inventoryPage.getPageTitle(), "Products",
                "Page title should be 'Products'");
    }

    @Test(description = "Login fails with invalid password")
    public void testInvalidPassword() {
        loginPage.enterUsername(STANDARD_USER);
        loginPage.enterPassword(INVALID_PASSWORD);
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed for invalid password");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
                "Error text should indicate credential mismatch");
    }

    @Test(description = "Login fails when username is empty")
    public void testEmptyUsername() {
        loginPage.enterPassword(VALID_PASSWORD);
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be shown for empty username");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"),
                "Error should mention username is required");
    }

    @Test(description = "Login fails when password is empty")
    public void testEmptyPassword() {
        loginPage.enterUsername(STANDARD_USER);
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be shown for empty password");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Password is required"),
                "Error should mention password is required");
    }

    @Test(description = "Locked-out user sees specific error message")
    public void testLockedOutUser() {
        loginPage.login(LOCKED_USER, VALID_PASSWORD);

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Locked user should see an error message");
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"),
                "Error should indicate the user is locked out");
    }
}
