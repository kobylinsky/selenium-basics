package utils;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ThreadGuard;

/**
 * @author bogdankobylinsky
 */
public class DriverManager {

    /**
     * For now only two
     */
    public enum Browser {
        FF, CHROME
    }

    /**
     * Firefox by default
     */
    public static WebDriver getWebDriverFor(Browser browser) {
        final WebDriver driver;
        if (browser == Browser.CHROME) {
            ChromeDriverManager.getInstance().setup();
            driver = ThreadGuard.protect(new ChromeDriver());
        } else {
            driver = ThreadGuard.protect(new FirefoxDriver());
        }
        driver.manage().window().maximize();
        return driver;
    }
}
