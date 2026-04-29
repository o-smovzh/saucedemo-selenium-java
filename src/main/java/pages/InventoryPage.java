package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {

    private static final By PAGE_TITLE     = By.cssSelector(".title");
    private static final By SORT_DROPDOWN  = By.cssSelector("[data-test='product-sort-container']");
    private static final By PRODUCT_NAMES  = By.cssSelector(".inventory_item_name");
    private static final By PRODUCT_PRICES = By.cssSelector(".inventory_item_price");
    private static final By ADD_BTN        = By.cssSelector("[data-test^='add-to-cart']");
    private static final By CART_BADGE     = By.cssSelector(".shopping_cart_badge");
    private static final By CART_ICON      = By.cssSelector(".shopping_cart_link");
    private static final By INVENTORY_LIST = By.cssSelector(".inventory_list");

    // ── SortOption Enum ───────────────────────────────────────────────────────
    public enum SortOption {
        NAME_AZ("az"),
        NAME_ZA("za"),
        PRICE_LOW_HIGH("lohi"),
        PRICE_HIGH_LOW("hilo");

        private final String value;
        SortOption(String value) { this.value = value; }
        public String getValue() { return value; }
    }

    public InventoryPage(WebDriver driver) {
        super(driver);
        // Wait for inventory page to fully load when instantiated
        wait.until(ExpectedConditions.urlContains("inventory"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(INVENTORY_LIST));
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    public void sortBy(SortOption option) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(SORT_DROPDOWN));
        new Select(waitForVisible(SORT_DROPDOWN)).selectByValue(option.getValue());
        wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_NAMES));
    }

    public void addItemToCartByIndex(int index) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ADD_BTN));
        List<WebElement> buttons = driver.findElements(ADD_BTN);
        // Wait for the specific button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(buttons.get(index)));
        buttons.get(index).click();
    }

    public void addFirstItemToCart() { addItemToCartByIndex(0); }

    public CartPage goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(CART_ICON));
        click(CART_ICON);
        return new CartPage(driver);
    }

    // ── Assertions / Getters ──────────────────────────────────────────────────

    public String getPageTitle() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PAGE_TITLE));
        return getText(PAGE_TITLE);
    }

    public List<String> getProductNames() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(PRODUCT_NAMES));
        return driver.findElements(PRODUCT_NAMES)
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(PRODUCT_PRICES));
        return driver.findElements(PRODUCT_PRICES)
                .stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    public int getCartBadgeCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(CART_BADGE));
            return Integer.parseInt(getText(CART_BADGE));
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isOnInventoryPage() {
        return driver.getCurrentUrl().contains("inventory");
    }
}
