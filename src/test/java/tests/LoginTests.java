package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.ProductsPage;
import utils.TestData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTests extends BaseTests {

    @Test
    public void testLoginWithValidCredentials() {
        login();
        ProductsPage productsPage = new ProductsPage(driver);
        assertEquals("Products", productsPage.getPageTitle());
        closeAll();
    }

    @Test
    public void testLoginWithInvalidPassword() {
        LoginPage loginPage = openPageAndSubmitCredentials(TestData.STANDARD_USER, TestData.INVALID_PASSWORD);

        Assertions.assertEquals("Epic sadface: Username and password do not match any user in this service",
                loginPage.getErrorMessage());
        closeAll();
    }

    @Test
    public void testLoginWithoutUsername() {
        LoginPage loginPage = openPageAndSubmitCredentials("", TestData.INVALID_PASSWORD);

        Assertions.assertEquals("Epic sadface: Username is required", loginPage.getErrorMessage());
        closeAll();
    }

    @Test
    public void testLoginWithoutPassword() {
        LoginPage loginPage = openPageAndSubmitCredentials(TestData.STANDARD_USER, "");

        Assertions.assertEquals("Epic sadface: Password is required", loginPage.getErrorMessage());
        closeAll();
    }

    @Test
    public void testLoginTime() {
        LoginPage loginPage = new LoginPage(driver);
        long startTime = System.currentTimeMillis();
        loginPage.loginAs(TestData.PERFORMANCE_GLITCH_USER, TestData.PASSWORD);
        long loadTime = System.currentTimeMillis() - startTime;
        assertTrue(loadTime < 2000);
        closeAll();
    }

    public LoginPage openPageAndSubmitCredentials(String userName, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(userName, password);
        return loginPage;
    }
}
