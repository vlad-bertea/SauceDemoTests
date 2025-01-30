package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutCompletePage extends BasePage {

    @FindBy(xpath = "//h2[@class='complete-header']")
    WebElement orderCompleteHeader;

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public String getOrderCompleteText() {
        return this.orderCompleteHeader.getText();
    }
}
