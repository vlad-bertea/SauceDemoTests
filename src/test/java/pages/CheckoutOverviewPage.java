package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.HelperMethods;

public class CheckoutOverviewPage extends BasePage {

    @FindBy(xpath = "//div[@class='summary_subtotal_label']")
    WebElement itemTotalPrice;
    @FindBy(id = "finish")
    WebElement finnishButton;

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public double getItemTotal() {
        return HelperMethods.getTotalAsDouble(this.itemTotalPrice.getText());
    }

    public CheckoutCompletePage finnishOrder(WebDriver driver) {
        this.finnishButton.click();
        return new CheckoutCompletePage(driver);
    }
}
