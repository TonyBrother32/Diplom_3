package praktikum;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.pages.*;

import java.time.Duration;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static praktikum.Url.*;

public class ProfileTest {

    @RegisterExtension
    private DriverExtension extension = new DriverExtension();

    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private String auth;
    private Client client;
    private ClientGenerator clientGenerator;


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

    private ProfilePage loginUser() {
        mainPage.clickProfileButton();
        loginPage = new LoginPage(driver, wait);
        loginPage.authorize(clientGenerator.getEmail(), clientGenerator.getPassword());
        return new ProfilePage(driver, wait);
    }

    private void assertCurrentUrlEquals(String expectedUrl) {
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Переход по клику на «Личный кабинет»")
    void checkProfilePageUrlAfterClickOnProfile() {
        profilePage = loginUser();
        mainPage.clickProfileButton();

        assertCurrentUrlEquals(PROFILE);
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на «Конструктор»")
    void checkGoToConstructorFromProfile() {
        profilePage = loginUser();
        mainPage.clickProfileButton();
        profilePage.clickConstructorButton();

        assertCurrentUrlEquals(MAIN_PAGE);

    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на логотип Stellar Burgers")
    void checkGoToConstructorClickLogo() {
        profilePage = loginUser();
        mainPage.clickProfileButton();
        profilePage.clickLogoImage();

        assertCurrentUrlEquals(MAIN_PAGE);

    }

    @Test
    @DisplayName("Выход по кнопке «Выйти» в личном кабинете")
    void checkLogout() {
        profilePage = loginUser();
        mainPage.clickProfileButton();
        profilePage.clickExitButton();

        assertCurrentUrlEquals(LOGIN);

    }
}
