package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    private static final By PAGE_TITLE   = By.cssSelector(".title");
    private static final By CART_ITEMS   = By.cssSelector(".cart_item");
    private static final By ITEM_NAMES   = By.cssSelector(".inventory_item_name");
    private static final By REMOVE_BTNS  = By.cssSelector("[data-test^='remove']");
    private static final By CHECKOUT_BTN = By.id("checkout");
    private static final By CONTINUE_BTN = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        super(driver);
        // Wait for cart page to fully load when CartPage is instantiated
        wait.until(ExpectedConditions.urlContains("cart"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(PAGE_TITLE));
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    public CheckoutPage proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(CHECKOUT_BTN));
        click(CHECKOUT_BTN);
        return new CheckoutPage(driver);
    }

    public InventoryPage continueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_BTN));
        click(CONTINUE_BTN);
        return new InventoryPage(driver);
    }

    public void removeFirstItem() {
        wait.until(ExpectedConditions.elementToBeClickable(REMOVE_BTNS));
        driver.findElements(REMOVE_BTNS).get(0).click();
    }

    // ── Assertions / Getters ──────────────────────────────────────────────────

    public String getPageTitle() {
        return getText(PAGE_TITLE);
    }

    public int getCartItemCount() {
        return driver.findElements(CART_ITEMS).size();
    }

    public List<String> getCartItemNames() {
        return driver.findElements(ITEM_NAMES)
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public boolean isCartEmpty() {
        return driver.findElements(CART_ITEMS).isEmpty();
    }

    public boolean isOnCartPage() {
        return driver.getCurrentUrl().contains("cart");
    }
}
