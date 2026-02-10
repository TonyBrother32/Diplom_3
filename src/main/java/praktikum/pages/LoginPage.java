package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;


    By emailFieldLocator = By.name("name");
    By passwordFieldLocator = By.name("Пароль");
    By loginButtonLocator = By.xpath("//*[contains(text(), 'Войти')]");
    By registrationButtonLocator = By.linkText("Зарегистрироваться");
    By restorePasswordLocator = By.linkText("Восстановить пароль");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }


    @Step("Заполнить поле «Email»")
    public LoginPage setEmailField(String email) {
        wait.until(ExpectedConditions.elementToBeClickable(emailFieldLocator));
        driver.findElement(emailFieldLocator).clear();
        driver.findElement(emailFieldLocator).sendKeys(email);
        return this;
    }

    @Step("Заполнить поле «Пароль»")
    public LoginPage setPasswordField(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(passwordFieldLocator));
        driver.findElement(passwordFieldLocator).clear();
        driver.findElement(passwordFieldLocator).sendKeys(password);
        return this;
    }

    @Step("Клик на кнопку «Войти»")
    public MainPage login() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator));
        driver.findElement(loginButtonLocator).click();
        return new MainPage(driver, wait);
    }

    @Step("Авторизация пользователя")
    public void authorize(String email, String password) {
        setEmailField(email);
        setPasswordField(password);
        login();
    }

    @Step("Клик на кнопку «Зарегистрироваться»")
    public RegistrationPage clickRegistrationButton() {
        wait.until(ExpectedConditions.elementToBeClickable(registrationButtonLocator));
        driver.findElement(registrationButtonLocator).click();
        return new RegistrationPage(driver, wait);
    }

    @Step("Клик на кнопку «Восстановить пароль»")
    public ForgotPasswordPage restorePassword() {
        wait.until(ExpectedConditions.elementToBeClickable(restorePasswordLocator));
        driver.findElement(restorePasswordLocator).click();
        return new ForgotPasswordPage(driver, wait);
    }
}
