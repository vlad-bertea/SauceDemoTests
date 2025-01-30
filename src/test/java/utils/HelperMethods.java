package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.HashMap;
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
}
