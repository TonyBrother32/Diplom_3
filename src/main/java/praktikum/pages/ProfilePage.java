package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ProfilePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    By logoutButtonLocator = By.xpath("//*[contains(text(), 'Выход')]");
    By constructorButtonLocator = By.xpath("//*[contains(text(), 'Конструктор')]");
    By logoImageLocator = By.className("AppHeader_header__logo__2D0X2");


    public ProfilePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }


    @Step("Кликаем на кнопку «Выход»")
    public LoginPage clickExitButton() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButtonLocator));
        driver.findElement(logoutButtonLocator).click();
        return new LoginPage(driver, wait);
    }

    @Step("Кликаем на кнопку «Конструктор»")
    public MainPage clickConstructorButton() {
        wait.until(ExpectedConditions.elementToBeClickable(constructorButtonLocator));
        driver.findElement(constructorButtonLocator).click();
        return new MainPage(driver, wait);
    }

    @Step("Кликаем на логотип сайта")
    public MainPage clickLogoImage() {
        wait.until(ExpectedConditions.elementToBeClickable(logoImageLocator));
        driver.findElement(logoImageLocator).click();
        return new MainPage(driver, wait);
    }
}
