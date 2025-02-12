package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import utils.TestData;

public class BaseTests {

    protected WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
    }

    protected void login() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(TestData.STANDARD_USER, TestData.PASSWORD);
    }

    @AfterEach
    public void closeAll() {
        if (driver != null) {
            driver.quit();
        }
    }
}
