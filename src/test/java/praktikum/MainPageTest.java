package praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.Test;
import praktikum.pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.Duration;

public class MainPageTest {
    @RegisterExtension
    private DriverExtension extension = new DriverExtension();
    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;

    private static final String BUNS_TAB_LABEL = "Булки";
    private static final String SAUCES_TAB_LABEL = "Соусы";
    private static final String FILLINGS_TAB_LABEL = "Начинки";

    @BeforeEach
    void setUp() {
        driver = extension.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        mainPage = new MainPage(driver, wait);
        mainPage.openMainPage();
        wait.until(ExpectedConditions.titleContains("Stellar Burgers"));
    }

    @Test
    @DisplayName("При клике на вкладку «Булки» она становится активной")
    void shouldDisplayBunsTabWhenBunsButtonClicked() {
        mainPage.saucesTabClick();
        assertNotEquals(BUNS_TAB_LABEL, mainPage.getActiveTabText());
        mainPage.bunsButtonClick();
        assertEquals(BUNS_TAB_LABEL, mainPage.getActiveTabText());
    }

    @Test
    @DisplayName("При клике на вкладку «Соусы» она становится активной")
    void shouldDisplaySaucesTabWhenSaucesButtonClicked() {
        assertNotEquals(SAUCES_TAB_LABEL, mainPage.getActiveTabText());
        mainPage.saucesTabClick();
        assertEquals(SAUCES_TAB_LABEL, mainPage.getActiveTabText());
    }

    @Test
    @DisplayName("При клике на вкладку «Начинки» она становится активной")
    void shouldDisplayFillingsTabWhenFillingsButtonClicked() {
        assertNotEquals(FILLINGS_TAB_LABEL, mainPage.getActiveTabText());
        mainPage.fillingsTabClick();
        assertEquals(FILLINGS_TAB_LABEL, mainPage.getActiveTabText());
    }
}

