package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.ProductsPage;

import static utils.HelperMethods.checkSocialMediaRedirects;

public class SocialMediaLinksTests extends BaseTests {

    ProductsPage productsPage;

    @BeforeEach
    protected void login() {
        super.login();
        this.productsPage = new ProductsPage(driver);
    }

    @Test
    public void checkTwitterRedirect() {
        checkSocialMediaRedirects(driver,
                "twitter",
                "Sauce Labs (@saucelabs) / X",
                productsPage);
    }

    @Test
    public void checkFacebookRedirect() {
        checkSocialMediaRedirects(driver,
                "facebook",
                "Sauce Labs | Facebook",
                productsPage);
    }

    @Test
    public void checkLinkedinRedirect() {
        checkSocialMediaRedirects(driver,
                "linkedin",
                "Sauce Labs | LinkedIn",
                productsPage);
    }


}
