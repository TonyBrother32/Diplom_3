package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class RegistrationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    By nameFieldLocator = By.xpath(".//fieldset[1]//input");
    By emailFieldLocator = By.xpath(".//fieldset[2]//input");
    By passwordFieldLocator = By.name("Пароль");
    By loginButtonLocator = By.linkText("Войти");
    By registrationButtonLocator = By.xpath(".//button[text()='Зарегистрироваться']");
    By errorPasswordFieldLocator = By.xpath("//p[contains(concat(' ',@class,' '),'input__error') and normalize-space(.)='Некорректный пароль']");

    public RegistrationPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }


    @Step("Ввод значения в поле «Имя»")
    public void setNameField(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(nameFieldLocator));
        driver.findElement(nameFieldLocator).clear();
        driver.findElement(nameFieldLocator).sendKeys(name);
    }

    @Step("Ввод значения в поле «Email»")
    public void setEmailField(String email) {
        wait.until(ExpectedConditions.elementToBeClickable(emailFieldLocator));
        driver.findElement(emailFieldLocator).clear();
        driver.findElement(emailFieldLocator).sendKeys(email);
    }

    @Step("Ввод значения в поле «Пароль»")
    public void setPasswordField(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(passwordFieldLocator));
        driver.findElement(passwordFieldLocator).clear();
        driver.findElement(passwordFieldLocator).sendKeys(password);
    }

    @Step("Клик по кнопке «Зарегистрироваться»")
    public void submitRegistration() {
        driver.findElement(registrationButtonLocator).click();
    }

    @Step("Проверить наличие сообщения об ошибке пароля")
    public boolean getPasswordError() {
        try {
            return driver.findElement(errorPasswordFieldLocator).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false; // Элемент не найден → ошибки нет
        }
    }

    @Step("Клик по ссылке «Войти»")
    public LoginPage login() {
        driver.findElement(loginButtonLocator).click();
        return new LoginPage(driver, wait);
    }
}
