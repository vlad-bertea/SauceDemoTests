package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

    @FindBy(xpath = "//span[@class='title']")
    WebElement pageTitle;

    protected WebElement driver;

    public BasePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        }

    public String getPageTitle() {
        return this.pageTitle.getText();
    }

}
