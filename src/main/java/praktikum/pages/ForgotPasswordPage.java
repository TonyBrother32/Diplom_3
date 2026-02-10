package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ForgotPasswordPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локатор кнопки «Войти»
    By loginButtonLocator = By.linkText("Войти");

    public ForgotPasswordPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }


    @Step("Кликнуть на кнопку «Войти»")
    public LoginPage clickEnterButton() {
        driver.findElement(loginButtonLocator).click();
        return new LoginPage(driver, wait);
    }
}
