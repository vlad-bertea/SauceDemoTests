package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutInformationPage extends BasePage {

    @FindBy(id = "cancel")
    WebElement cancelButton;
    @FindBy(id = "continue")
    WebElement continueButton;
    @FindBy(id = "first-name")
    WebElement firstName;
    @FindBy(id = "last-name")
    WebElement lastName;
    @FindBy(id = "postal-code")
    WebElement postalCode;
    @FindBy(xpath = "//h3[@data-test='error']")
    WebElement errorMessage;

    public CheckoutInformationPage(WebDriver driver) {
        super(driver);
    }

    public void typeFirstName(String firstName) {
        this.firstName.sendKeys(firstName);
    }

    public void typeLastName(String lastName) {
        this.lastName.sendKeys(lastName);
    }

    public void typePostalCode(String postalCode) {
        this.postalCode.sendKeys(postalCode);
    }

    public void typeAllInputFields(String firstName, String lastName, String postalCode) {
        typeFirstName(firstName);
        typeLastName(lastName);
        typePostalCode(postalCode);
    }

    public CheckoutOverviewPage navigateToOverview(WebDriver driver) {
        clickOnContinue();
        return new CheckoutOverviewPage(driver);
    }

    public void clickOnContinue() {
        this.continueButton.click();
    }

    public String getErrorMessage(String firstName, String lastName, String postalCode) {
        typeAllInputFields(firstName, lastName, postalCode);
        clickOnContinue();
        return this.errorMessage.getText();
    }
}
