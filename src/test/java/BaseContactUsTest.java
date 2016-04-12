import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author bogdankobylinsky
 */
public class BaseContactUsTest {

    public static final WebDriver DRIVER = new FirefoxDriver();

    public static final int DEFAULT_TIMEOUT_SECONDS = 10;

    public static final String BASE_URL = "http://www.seleniumframework.com/Practiceform/";

    public static final String XPATH_CONTACT_FORM = ".//form[contains(@class,'contact-form')]";
    public static final String XPATH_INPUT_NAME = XPATH_CONTACT_FORM + "//span[@class='form-name']/input";
    public static final String XPATH_INPUT_MAIL = XPATH_CONTACT_FORM + "//span[@class='form-mail']/input";
    public static final String XPATH_INPUT_TELEPHONE = XPATH_CONTACT_FORM + "//span[@class='form-telephone']/input";
    public static final String XPATH_INPUT_COUNTRY = XPATH_CONTACT_FORM + "//span[@class='form-country']/input";
    public static final String XPATH_INPUT_COMPANY = XPATH_CONTACT_FORM + "//span[@class='form-company']/input";
    public static final String XPATH_INPUT_MESSAGE = XPATH_CONTACT_FORM + "//span[@class='form-message']/textarea";

    public static final String XPATH_BUTTON_CLEAR = XPATH_CONTACT_FORM + "//a[text()='clear']";
    public static final String XPATH_BUTTON_SUBMIT = XPATH_CONTACT_FORM + "//a[text()='Submit']";

    public static final String XPATH_SUBMITION_SUCCESS_ALERT = XPATH_CONTACT_FORM + "//div[contains(@class,'greenPopup')]/div";
    public static final String XPATH_ALERT_SUFFIX = "/..//div[@class='formErrorContent']";

    /**
     * Loading a URL for verifications
     */
    @BeforeClass
    public static void setUp() {
        DRIVER.get(BASE_URL);
    }

    /**
     * Closing the WebDriver
     */
    @AfterClass
    public static void tearDown() {
        DRIVER.close();
    }

    /**
     * Clear inputs after each test
     */
    @After
    public void clearInputs() {
        waitForElementToBeVisible(XPATH_INPUT_NAME);
        clearInput(XPATH_INPUT_NAME);
        clearInput(XPATH_INPUT_MAIL);
        clearInput(XPATH_INPUT_TELEPHONE);
        clearInput(XPATH_INPUT_COUNTRY);
        clearInput(XPATH_INPUT_COMPANY);
        clearInput(XPATH_INPUT_MESSAGE);
    }

    public void clearInput(String... elementsXPathes) {
        for (String elementXPath : elementsXPathes) {
            waitForElementToBeVisible(elementXPath);
            DRIVER.findElement(By.xpath(elementXPath)).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        }
    }

    public String getValue(String xpath) {
        return DRIVER.findElement(By.xpath(xpath)).getText();
    }

    public void setValue(String xpath, String value) {
        clearInput(xpath);
        DRIVER.findElement(By.xpath(xpath)).sendKeys(new CharSequence[] { value });
    }

    public void clickClear() {
        waitForElementToBeVisible(XPATH_BUTTON_CLEAR);
        DRIVER.findElement(By.xpath(XPATH_BUTTON_CLEAR)).click();
    }

    public void clickSubmit() {
        waitForElementToBeVisible(XPATH_BUTTON_SUBMIT);
        DRIVER.findElement(By.xpath(XPATH_BUTTON_SUBMIT)).click();
    }

    public boolean isElementPresent(String xpath) {
        try {
            DRIVER.findElement(By.xpath(xpath));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForElementToBeVisible(String xpath) {
        WebDriverWait webDriverWait = new WebDriverWait(DRIVER, DEFAULT_TIMEOUT_SECONDS);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

}
