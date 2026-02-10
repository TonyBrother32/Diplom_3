package praktikum;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {
    private RemoteWebDriver driver;

    public void initDriver() {
        if ("firefox".equals(System.getProperty("browser"))) {
            setupFirefox();
        } else if ("yandex".equals(System.getProperty("browser"))) {
            initYandex();
        } else {
            setupChrome();
        }

    }

    public void setupChrome() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

    }

    public void setupFirefox() {
        WebDriverManager.firefoxdriver().setup();
        var opts = new FirefoxOptions().configureFromEnv();

        driver = new FirefoxDriver(opts);
    }

    private void initYandex() {
        WebDriverManager.chromedriver().driverVersion(System.getProperty("driver.version")).setup();

        var options = new ChromeOptions();
        options.setBinary(System.getProperty("webdriver.yandex.bin"));

        driver = new ChromeDriver(options);
    }

    public RemoteWebDriver getDriver() {
        return driver;
    }
}
