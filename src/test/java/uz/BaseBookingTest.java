package uz;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.DriverManager;

import static utils.DriverManager.Browser;

/**
 * AUT: http://booking.uz.gov.ua/
 * <p>
 * You have to implement the following Test Case:
 * - Select Departure Station from list
 * - Select Destination Station from list
 * - Select Departure Date
 * - Click "Search" button
 * - Verify that table with results are displayed
 * - Verify that table contains necessary train numbers
 * <p>
 * Test Data:
 * Departure Destination Train
 * 1. Kyiv Ivano-Frankivsk 115 О, 269 К
 * Use PageObject and HTMLElements approaches to achieve DRY and KISS principles
 * Note: You can use http://booking.uz.gov.ua/en/ for English version (Multilanguage is not a case for now)
 *
 * @author bogdankobylinsky
 */
public class BaseBookingTest {

    protected WebDriver webDriver;
    protected BookingPage bookingPage;

    @BeforeClass
    @Parameters({ "url", "browser" })
    public void setUp(@Optional(value = "http://booking.uz.gov.ua/en") String url,
            @Optional(value = "FF") Browser browser) {
        webDriver = DriverManager.getWebDriverFor(browser);
        bookingPage = new BookingPage(webDriver, url);
    }

    @BeforeMethod
    public void refreshPage() {
        webDriver.navigate().refresh();
    }

    @AfterClass
    public void tearDown() {
        webDriver.close();
    }

}
