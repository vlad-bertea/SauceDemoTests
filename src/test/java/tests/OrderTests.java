package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.*;
import utils.HelperMethods;
import utils.TestData;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTests extends BaseTests {

    ProductsPage productsPage;
    CartPage cartPage;
    CheckoutInformationPage checkoutInformationPage;
    CheckoutOverviewPage checkoutOverviewPage;
    CheckoutCompletePage checkoutCompletePage;

    @BeforeEach
    protected void login() {
        super.login();
        this.productsPage = new ProductsPage(driver);
    }

    @Test
    public void testCartBadgeAfterAddingProduct() {
        productsPage.addProductToCart(driver, 0);
        productsPage.addProductToCart(driver, 1);
        assertEquals(2, productsPage.getProductsCount());
    }

    @Test
    public void testCartBadgeAfterProductRemoval() {
        productsPage.addProductToCart(driver, 0);
        productsPage.removeProductFromCart(driver, productsPage.getFirstProductFromCart());
        assertFalse(productsPage.hasBadge());
    }

    @Test
    public void testPlaceOrderHappyFlow(){
        productsPage.addProductToCart(driver, 0);
        productsPage.addProductToCart(driver, 1);
        cartPage = productsPage.openCart(driver);
        assertEquals("Your Cart", cartPage.getPageTitle());
        assertTrue(HelperMethods.compareProducts(productsPage.getProductsInCart(), cartPage.getCartProducts()));
        checkoutInformationPage = cartPage.checkout(driver);
        assertEquals("Checkout: Your Information", checkoutInformationPage.getPageTitle());
        checkoutInformationPage.typeAllInputFields(TestData.FIRST_NAME, TestData.FIRST_NAME, TestData.ZIP_CODE);
        checkoutOverviewPage = checkoutInformationPage.navigateToOverview(driver);
        assertEquals(checkoutOverviewPage.getItemTotal(), HelperMethods.getTotalPrice(cartPage.getCartProducts()));
        checkoutCompletePage = checkoutOverviewPage.finnishOrder(driver);
        assertEquals("Thank you for your order!", checkoutCompletePage.getOrderCompleteText());
    }

    @Test
    public void testCheckOutWithoutProducts() {
        cartPage = productsPage.openCart(driver);
        assertFalse(cartPage.getCheckoutButton().isDisplayed()
                && cartPage.getCheckoutButton().isEnabled());
    }

    @Test
    public void testCheckoutWithInvalidUserData() {
        productsPage.addProductToCart(driver, 0);
        cartPage = productsPage.openCart(driver);
        checkoutInformationPage = cartPage.checkout(driver);
        assertEquals("Error: First Name is required", checkoutInformationPage.
                getErrorMessage(TestData.EMPTY_STRING, TestData.LAST_NAME, TestData.ZIP_CODE));
        driver.navigate().refresh();
        assertEquals("Error: Last Name is required", checkoutInformationPage.
                getErrorMessage(TestData.FIRST_NAME, TestData.EMPTY_STRING, TestData.ZIP_CODE));
        driver.navigate().refresh();
        assertEquals("Error: Postal Code is required", checkoutInformationPage.
                getErrorMessage(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.EMPTY_STRING));
    }
}
