package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.HelperMethods;

import java.util.ArrayList;
import java.util.Map;


public class CartPage extends BasePage {

    @FindBy(id = "checkout")
    private WebElement checkoutButton;
    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    private ArrayList<Map<String, String>> productsInCart;

    public CartPage(WebDriver driver) {
        super(driver);
        String listPath = "//div[@class=\"cart_item\"]";
        String itemPath = "//div[@class=\"inventory_item_name\"]";
        productsInCart = HelperMethods.scanPageForProducts(driver, listPath, itemPath);
    }

    public ArrayList<Map<String, String>> getCartProducts() {
        return this.productsInCart;
    }

    public CheckoutInformationPage checkout(WebDriver driver) {
        this.checkoutButton.click();
        return new CheckoutInformationPage(driver);
    }

    public void continueShopping() {
        this.checkoutButton.click();
    }

    public WebElement getCheckoutButton() {
        return this.checkoutButton;
    }
}
