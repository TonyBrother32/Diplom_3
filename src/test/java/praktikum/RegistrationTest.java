package praktikum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.RegistrationPage;

import java.time.Duration;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static praktikum.Url.*;
import static org.apache.http.HttpStatus.SC_OK;


public class RegistrationTest {
    @RegisterExtension
    private DriverExtension extension = new DriverExtension();
    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private String auth;
    private ClientGenerator clientGenerator;
    private Client client;

        @BeforeEach
    void setUp() {
        client = new Client();
        clientGenerator = ClientGenerator.getRandomClient();
        driver = extension.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        mainPage = new MainPage(driver, wait);
        mainPage.openMainPage();
        wait.until(ExpectedConditions.titleContains("Stellar Burgers"));
        mainPage.clickAccountEntryButton();
        loginPage = new LoginPage(driver, wait);
        loginPage.clickRegistrationButton();
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

    private String getAuthTokenIfLoginSuccessful(ClientGenerator generator) {
        var response = client.loginClient(generator);
        if (response.extract().statusCode() == SC_OK) {
            return response.extract().body().path("accessToken");
        }
        return null;
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void validRegistrationTest() {

        registrationPage = new RegistrationPage(driver, wait);

        registrationPage.setNameField(clientGenerator.getName());
        registrationPage.setEmailField(clientGenerator.getEmail());
        registrationPage.setPasswordField(clientGenerator.getPassword());
        registrationPage.submitRegistration();

        wait.until(ExpectedConditions.urlToBe(LOGIN));
        auth = getAuthTokenIfLoginSuccessful(clientGenerator);

        assertNotNull(auth, "Токен авторизации не получен после успешной регистрации");
    }

    @Test
    @DisplayName("Проверка ошибки для некорректного пароля. Минимальный пароль — шесть символов")
    public void invalidRegistrationTest() {

        registrationPage = new RegistrationPage(driver, wait);

        registrationPage.setNameField(clientGenerator.getName());
        registrationPage.setEmailField(clientGenerator.getEmail());
        registrationPage.setPasswordField(Faker.instance().bothify("?#?#?"));

        registrationPage.submitRegistration();
        registrationPage.getPasswordError();
        auth = getAuthTokenIfLoginSuccessful(clientGenerator);

        assertTrue(registrationPage.getPasswordError());
    }
}
