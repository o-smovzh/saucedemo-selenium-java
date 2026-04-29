package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.InventoryPage.SortOption;
import pages.LoginPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void loginAndNavigate() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        inventoryPage = loginPage.login(STANDARD_USER, VALID_PASSWORD);
    }

    @Test(description = "Inventory page loads with Products title and 6 items")
    public void testInventoryPageLoads() {
        Assert.assertEquals(inventoryPage.getPageTitle(), "Products",
                "Page title should be 'Products'");
        Assert.assertEquals(inventoryPage.getProductNames().size(), 6,
                "There should be exactly 6 products listed");
    }

    @Test(description = "Sort products by name A→Z")
    public void testSortByNameAZ() {
        inventoryPage.sortBy(SortOption.NAME_AZ);
        List<String> names = inventoryPage.getProductNames();
        List<String> sorted = new ArrayList<>(names);
        Collections.sort(sorted);
        Assert.assertEquals(names, sorted, "Products should be sorted A→Z");
    }

    @Test(description = "Sort products by name Z→A")
    public void testSortByNameZA() {
        inventoryPage.sortBy(SortOption.NAME_ZA);
        List<String> names = inventoryPage.getProductNames();
        List<String> sorted = new ArrayList<>(names);
        sorted.sort(Collections.reverseOrder());
        Assert.assertEquals(names, sorted, "Products should be sorted Z→A");
    }

    @Test(description = "Sort products by price low to high")
    public void testSortByPriceLowHigh() {
        inventoryPage.sortBy(SortOption.PRICE_LOW_HIGH);
        List<Double> prices = inventoryPage.getProductPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) <= prices.get(i + 1),
                    "Price at index " + i + " should be <= price at index " + (i + 1));
        }
    }

    @Test(description = "Sort products by price high to low")
    public void testSortByPriceHighLow() {
        inventoryPage.sortBy(SortOption.PRICE_HIGH_LOW);
        List<Double> prices = inventoryPage.getProductPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) >= prices.get(i + 1),
                    "Price at index " + i + " should be >= price at index " + (i + 1));
        }
    }

    @Test(description = "Cart badge increments when item is added")
    public void testAddItemToCartUpdatesBadge() {
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 0,
                "Cart badge should be 0 initially");
        inventoryPage.addFirstItemToCart();
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 1,
                "Cart badge should show 1 after adding one item");
    }

    @Test(description = "Multiple items can be added and badge count reflects all")
    public void testAddMultipleItemsToCart() {
        inventoryPage.addItemToCartByIndex(0);
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 1,
                "Cart badge should show 1 after first item");

        inventoryPage.addItemToCartByIndex(1);
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 2,
                "Cart badge should show 2 after second item");

        inventoryPage.addItemToCartByIndex(2);
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 3,
                "Cart badge should show 3 after adding three items");
    }
}
