package praktikum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.Test;
import praktikum.pages.ForgotPasswordPage;
import praktikum.pages.MainPage;
import praktikum.pages.LoginPage;
import praktikum.pages.RegistrationPage;

import java.time.Duration;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static praktikum.Url.*;

public class LoginTest {

    @RegisterExtension
    private DriverExtension extension = new DriverExtension();
    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;
    private String auth;
    private ClientGenerator clientGenerator;
    private Client client;


    @BeforeEach
    public void setUp() {

        driver = extension.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        client = new Client();
        clientGenerator = ClientGenerator.getRandomClient();

        // Проверка успешности создания клиента
        var response = client.createClient(clientGenerator);
        if (response.extract().statusCode() != SC_OK) {
            throw new RuntimeException("Не удалось создать тестового пользователя");
        }
        auth = response.extract().path("accessToken");

        driver.get(MAIN_PAGE);
        mainPage = new MainPage(driver, wait);
    }

    @AfterEach
    public void clear() {
        if (auth != null) {
            try {
                System.out.println("Удаляем пользователя: " + auth);
                client.deleteClient(auth)
                        .assertThat()
                        .statusCode(SC_ACCEPTED)
                        .and()
                        .body("success", is(true))
                        .and()
                        .body("message", is("User successfully removed"));
            } catch (Exception e) {
                System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной странице")
    public void LoginViaMainPageAccountButton() {
        // 1. Нажимаем кнопку входа на главной
        mainPage.clickAccountEntryButton();

        // 2. Заполняем форму входа и авторизуемся
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.authorize(clientGenerator.getEmail(), clientGenerator.getPassword());

        // 3. Проверяем редирект на главную страницу
        wait.until(ExpectedConditions.urlToBe(MAIN_PAGE));
        assertEquals(MAIN_PAGE, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет» на главной странице")
    public void LoginViaProfileButtonOnMainPage() {
        // 1. Открываем форму входа через профиль
        mainPage.clickProfileButton();

        // 2. Заполняем форму входа и авторизуемся
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.authorize(clientGenerator.getEmail(), clientGenerator.getPassword());

        // 3. Проверяем редирект на главную страницу
        wait.until(ExpectedConditions.urlToBe(MAIN_PAGE));
        assertEquals(MAIN_PAGE, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Вход из формы регистрации")
    public void LoginFromRegistrationForm() {
        // 1. Нажимаем кнопку входа на главной
        mainPage.clickAccountEntryButton();
        LoginPage loginPage = new LoginPage(driver, wait);

        // 2. Переходим в форму регистрации
        loginPage.clickRegistrationButton();
        RegistrationPage registrationPage = new RegistrationPage(driver, wait);

        // 3. Возвращаемся к форме входа, заполняем форму и авторизуемся
        registrationPage.login();
        loginPage = new LoginPage(driver, wait);
        loginPage.authorize(clientGenerator.getEmail(), clientGenerator.getPassword());

        // 4. Проверяем редирект на главную страницу
        wait.until(ExpectedConditions.urlToBe(MAIN_PAGE));
        assertEquals(MAIN_PAGE, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Вход из формы восстановления пароля")
    public void LoginFromPasswordRecoveryForm() {
        // 1. Нажимаем кнопку входа на главной
        mainPage.clickAccountEntryButton();
        LoginPage loginPage = new LoginPage(driver, wait);

        // 2. Переходим к восстановлению пароля
        loginPage.restorePassword();
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver, wait);

        // 3. Возвращаемся к форме входа, заполняем форму и авторизуемся
        forgotPasswordPage.clickEnterButton();
        loginPage = new LoginPage(driver, wait); // Переинициализируем страницу
        loginPage.authorize(clientGenerator.getEmail(), clientGenerator.getPassword());

        // 4. Проверяем редирект на главную страницу
        wait.until(ExpectedConditions.urlToBe(MAIN_PAGE));
        assertEquals(MAIN_PAGE, driver.getCurrentUrl());
    }
}
