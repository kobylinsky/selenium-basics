package utils;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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
            driver = new ChromeDriver();
        } else {
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        return driver;
    }
}
