package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.Url;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;


    By accountEntryButtonLocator = By.xpath(".//button[text()='Войти в аккаунт']");
    By profileButtonLocator = By.xpath(".//p[text()='Личный Кабинет']");
    By bunsTabLocator = By.xpath(".//span[text()='Булки']");
    By saucesTabLocator = By.xpath(".//span[text()='Соусы']");
    By fillingsTabLocator = By.xpath(".//span[text()='Начинки']");
    By activeChooseTabLocator = By.xpath("//div[contains(concat(' ',@class,' '),'tab_tab_type')]");

    public MainPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Открываем главную страницу")
    public void openMainPage() {

        driver.get(Url.BASE_URL);
    }

    @Step("Кликаем на 'Войти в аккаунт'")
    public void clickAccountEntryButton() {
        wait.until(ExpectedConditions.elementToBeClickable(accountEntryButtonLocator));
        driver.findElement(accountEntryButtonLocator).click();
    }

    @Step("Кликаем на кнопку 'Личный кабинет'")
    public void clickProfileButton() {
        wait.until(ExpectedConditions.elementToBeClickable(profileButtonLocator));
        driver.findElement(profileButtonLocator).click();
    }

    @Step("Название выбранной вкладки")
    public String getActiveTabText() {
        return driver.findElement(activeChooseTabLocator).getText();
    }

    @Step("Клик по вкладке 'Булки'")
    public void bunsButtonClick() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsTabLocator));
        driver.findElement(bunsTabLocator).click();
    }

    @Step("Клик по вкладке 'Соусы'")
    public void saucesTabClick() {
        wait.until(ExpectedConditions.elementToBeClickable(saucesTabLocator));
        driver.findElement(saucesTabLocator).click();
    }

    @Step("Клик по вкладке 'Начинки'")
    public void fillingsTabClick() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsTabLocator));
        driver.findElement(fillingsTabLocator).click();
    }

}
