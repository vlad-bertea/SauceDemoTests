package utils;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ProductsPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelperMethods {

    public static ArrayList<Map<String, String>> scanPageForProducts(
            WebDriver driver,
            String listPath,
            String itemPath) {
        int numberOfProducts = driver.findElements(
                By.xpath(listPath)).size();
        ArrayList<Map<String, String>> allProducts = new ArrayList<>();

        for (int htmlIndex = 1; htmlIndex <= numberOfProducts; htmlIndex++) {
            Map<String, String> product = new HashMap<>();
            String xpathPrefix = String.format("%s[%s]", listPath, htmlIndex);
            String itemXpath = xpathPrefix + itemPath;
            String itemPriceXpath = xpathPrefix + "//div[@class=\"inventory_item_price\"]";
            String buttonXpath = xpathPrefix + "//button";

            product.put("itemName", driver.findElement(By.xpath(itemXpath)).getText());
            product.put("itemPrice", driver.findElement(By.xpath(itemPriceXpath)).getText());
            product.put("button", driver.findElement(By.xpath(buttonXpath)).getDomAttribute("id"));
            allProducts.addLast(product);
        }
        return allProducts;
    }

    public static double getProductPriceAsDouble(Map<String, String> product) {
        String productPriceAsString = product.get("itemPrice");
        return Double.parseDouble(productPriceAsString.replace("$", ""));
    }

    public static double getTotalAsDouble(String totalAsString) {
        String[] totalValue = totalAsString.split("\\$");
        return Double.parseDouble(totalValue[totalValue.length - 1]);
    }

    public static double getTotalPrice(ArrayList<Map<String, String>> products) {
        double total = 0.0;
        for (Map<String, String> product : products) {
            total += getProductPriceAsDouble(product);
        }
        return total;
    }

    public static boolean compareProducts(ArrayList<Map<String, String>> expected,
                                          ArrayList<Map<String, String>> actual) {
        boolean productsMatch = false;
        boolean pricesMatch = false;
        if (expected.size() != actual.size()) {
            return false;
        } else {
            for (int productIndex = 0; productIndex < expected.size(); productIndex += 1) {
                productsMatch = expected.get(productIndex).get("itemName")
                        .equals(actual.get(productIndex).get("itemName"));
                pricesMatch = expected.get(productIndex).get("itemPrice")
                        .equals(actual.get(productIndex).get("itemPrice"));
            }
        }
        return productsMatch && pricesMatch;
    }

    public static void acceptFacebookCookies(WebDriver driver) {
            driver.findElement(By.xpath("//div[@class='x1ja2u2z x78zum5 x2lah0s " +
                    "x1n2onr6 xl56j7k x6s0dn4 xozqiw3 x1q0g3np xi112ho x17zwfj4 x585lrc x1403ito x972fbf xcfux6l " +
                    "x1qhh985 xm0m39n x9f619 xn6708d x1ye3gou xtvsq51 x1r1pt67']")).click();
        }

    public static void checkSocialMediaRedirects(WebDriver driver,
                                          String socialMediaType,
                                          String expectedPageTitle,
                                          ProductsPage productsPage) {
        String twitterWaitElementXpath = "//div[@class='css-175oi2r r-18u37iz r-1w6e6rj r-6gpygo r-14gqq1x']";
        productsPage.getFooterButton(driver, socialMediaType).click();
        List<String> handles = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(handles.get(1));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(5000));
        if (socialMediaType.equals("twitter")) {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(twitterWaitElementXpath)));
        } else if (socialMediaType.equals("facebook")) {
            HelperMethods.acceptFacebookCookies(driver);
        }

        Assertions.assertEquals(expectedPageTitle, driver.getTitle());
    }
}
