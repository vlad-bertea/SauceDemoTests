package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.HelperMethods;

import java.util.ArrayList;
import java.util.Map;

public class ProductsPage extends BasePage {

    @FindBy(xpath = "//span[@class='shopping_cart_badge']")
    private WebElement cartProductsCount;
    @FindBy(xpath = "//div[@id='shopping_cart_container']")
    private WebElement cartButton;

    private ArrayList<Map<String, String>> allProducts = new ArrayList<>();
    private ArrayList<Map<String, String>> productsInCart;

    public ProductsPage(WebDriver driver) {
        super(driver);
        String basePath = "//div[@class=\"inventory_item\"]";
        String itemXpath = "//div[@class=\"inventory_item_name \"]";
        this.allProducts = HelperMethods.scanPageForProducts(driver, basePath, itemXpath);
        this.productsInCart = new ArrayList<>();
    }

    public void addProductToCart(WebDriver driver, int productIndex) {
        interactWithProducts(driver, allProducts.get(productIndex).get("button"));
        this.productsInCart.add(allProducts.get(productIndex));
    }

    public void removeProductFromCart(WebDriver driver, Map<String, String> product) {
        String removeButton = product.get("button").replace("add-to-cart", "remove");
        interactWithProducts(driver, removeButton);
        this.productsInCart.remove(product);
    }

    public void interactWithProducts(WebDriver driver, String buttonId) {
        driver.findElement(By.id(buttonId)).click();
    }

    public Map<String, String> getFirstProductFromCart() {
        return this.productsInCart.getFirst();
    }

    public int getProductsCount() {
        return Integer.parseInt(this.cartProductsCount.getText());
    }

    public ArrayList<Map<String, String>> getProductsInCart() {
        return this.productsInCart;
    }

    public CartPage openCart(WebDriver driver) {
        this.cartButton.click();
        return new CartPage(driver);
    }

    public boolean hasBadge() {
        try {
            this.cartProductsCount.getText();
        } catch (Exception _) {
            return false;
        }
        return true;
    }

    public WebElement getFooterButton(WebDriver driver, String socialMediaType) {
        String socialMediaButtonXpath = "//li[@class='social_%s']";
        return driver.findElement(By.xpath(String.format(socialMediaButtonXpath, socialMediaType)));
    }
}
