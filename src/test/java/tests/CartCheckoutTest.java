package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;

/**
 * CartCheckoutTest - Verifies cart operations and end-to-end checkout flow.
 */
public class CartCheckoutTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void loginAndAddItem() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        inventoryPage = loginPage.login(STANDARD_USER, VALID_PASSWORD);
        inventoryPage.addFirstItemToCart();
    }

    // ── Cart Tests ────────────────────────────────────────────────────────────

    @Test(description = "Cart page shows added item")
    public void testCartContainsAddedItem() {
        CartPage cartPage = inventoryPage.goToCart();

        Assert.assertTrue(cartPage.isOnCartPage(),
                "Should navigate to cart page");
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Cart should contain 1 item");
    }

    @Test(description = "Item can be removed from cart")
    public void testRemoveItemFromCart() {
        CartPage cartPage = inventoryPage.goToCart();
        cartPage.removeFirstItem();

        Assert.assertTrue(cartPage.isCartEmpty(),
                "Cart should be empty after removing the only item");
    }

    @Test(description = "Continue shopping returns user to inventory")
    public void testContinueShopping() {
        CartPage cartPage = inventoryPage.goToCart();
        InventoryPage backToInventory = cartPage.continueShopping();

        Assert.assertTrue(backToInventory.isOnInventoryPage(),
                "Should return to inventory page on 'Continue Shopping'");
    }

    // ── Checkout Tests ────────────────────────────────────────────────────────

    @Test(description = "Checkout step one loads after clicking Checkout")
    public void testProceedToCheckout() {
        CartPage cartPage = inventoryPage.goToCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        Assert.assertTrue(checkoutPage.isOnCheckoutStepOne(),
                "Should be on checkout step one page");
    }

    @Test(description = "Checkout fails without first name")
    public void testCheckoutWithMissingFirstName() {
        CartPage cartPage = inventoryPage.goToCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        checkoutPage.enterLastName("Doe");
        checkoutPage.enterPostalCode("12345");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isErrorDisplayed(),
                "Error should appear when first name is missing");
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("First Name"),
                "Error should mention First Name");
    }

    @Test(description = "Checkout fails without postal code")
    public void testCheckoutWithMissingPostalCode() {
        CartPage cartPage = inventoryPage.goToCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        checkoutPage.enterFirstName("Jane");
        checkoutPage.enterLastName("Doe");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isErrorDisplayed(),
                "Error should appear when postal code is missing");
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("Postal Code"),
                "Error should mention Postal Code");
    }

    @Test(description = "Full end-to-end checkout completes with order confirmation")
    public void testSuccessfulEndToEndCheckout() {
        CartPage cartPage = inventoryPage.goToCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // Step 1: fill in info
        checkoutPage.fillCheckoutInfo("Jane", "Doe", "60061");

        Assert.assertTrue(checkoutPage.isOnCheckoutStepTwo(),
                "Should advance to order summary page");

        // Step 2: review and finish
        checkoutPage.clickFinish();

        Assert.assertTrue(checkoutPage.isOnConfirmationPage(),
                "Should reach order confirmation page");
        Assert.assertTrue(checkoutPage.isOrderConfirmed(),
                "Order confirmation message should contain 'Thank you'");
    }
}
