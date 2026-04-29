package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    private static final By FIRST_NAME_INPUT  = By.id("first-name");
    private static final By LAST_NAME_INPUT   = By.id("last-name");
    private static final By POSTAL_CODE_INPUT = By.id("postal-code");
    private static final By CONTINUE_BUTTON   = By.id("continue");
    private static final By ERROR_MESSAGE     = By.cssSelector("[data-test='error']");
    private static final By FINISH_BUTTON     = By.id("finish");
    private static final By TOTAL_LABEL       = By.cssSelector(".summary_total_label");
    private static final By CONFIRMATION_HDR  = By.cssSelector(".complete-header");
    private static final By BACK_HOME_BUTTON  = By.id("back-to-products");

    public CheckoutPage(WebDriver driver) {
        super(driver);
        // Wait for checkout page to load
        wait.until(ExpectedConditions.urlContains("checkout"));
    }

    // ── Step One Actions ──────────────────────────────────────────────────────

    public void enterFirstName(String v) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME_INPUT));
        type(FIRST_NAME_INPUT, v);
    }

    public void enterLastName(String v) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(LAST_NAME_INPUT));
        type(LAST_NAME_INPUT, v);
    }

    public void enterPostalCode(String v) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(POSTAL_CODE_INPUT));
        type(POSTAL_CODE_INPUT, v);
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_BUTTON));
        click(CONTINUE_BUTTON);
    }

    public void fillCheckoutInfo(String first, String last, String zip) {
        enterFirstName(first);
        enterLastName(last);
        enterPostalCode(zip);
        clickContinue();
    }

    // ── Step Two Actions ──────────────────────────────────────────────────────

    public void clickFinish() {
        wait.until(ExpectedConditions.elementToBeClickable(FINISH_BUTTON));
        click(FINISH_BUTTON);
    }

    public InventoryPage backToProducts() {
        wait.until(ExpectedConditions.elementToBeClickable(BACK_HOME_BUTTON));
        click(BACK_HOME_BUTTON);
        return new InventoryPage(driver);
    }

    // ── Assertions / Getters ──────────────────────────────────────────────────

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

    public String getOrderTotal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(TOTAL_LABEL));
        return getText(TOTAL_LABEL);
    }

    public boolean isOrderConfirmed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(CONFIRMATION_HDR));
            return getText(CONFIRMATION_HDR).toLowerCase().contains("thank you");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOnCheckoutStepOne() {
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
        return driver.getCurrentUrl().contains("checkout-step-one");
    }

    public boolean isOnCheckoutStepTwo() {
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
        return driver.getCurrentUrl().contains("checkout-step-two");
    }

    public boolean isOnConfirmationPage() {
        wait.until(ExpectedConditions.urlContains("checkout-complete"));
        return driver.getCurrentUrl().contains("checkout-complete");
    }
}
