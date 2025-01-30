package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    @FindBy(id = "user-name")
    private WebElement userNameInput;
    @FindBy(id = "password")
    private WebElement passwordInput;
    @FindBy(id = "login-button")
    private WebElement loginButton;
    @FindBy(xpath = "//div[@class = \"error-message-container error\"]")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void loginAs(String userName, String password) {
        this.userNameInput.sendKeys(userName);
        this.passwordInput.sendKeys(password);
        this.loginButton.click();
    }

    public String getErrorMessage() {
        return this.errorMessage.getText();
    }
}
