package uz;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.DriverManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
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
public class BookingTest {

    private WebDriver webDriver;
    private BookingPage bookingPage;

    @BeforeMethod
    @Parameters({"url", "browser"})
    public void setUp(String url, Browser browser) {
        webDriver = DriverManager.getWebDriverFor(browser);
        bookingPage = new BookingPage(webDriver, url);
    }

    @AfterMethod
    public void tearDown() {
        webDriver.close();
    }

    @Test
    public void testKievIvanoFrankivskTrains() throws ParseException {
        // Load page
        bookingPage.openPage();

        // Set stations
        bookingPage.setStationFrom("Kyiv");
        bookingPage.setStationTo("Ivano-Frankivsk");

        // Set date
        final String datePlusMonth = getCurrentDatePlusMonth();
        bookingPage.setDate(datePlusMonth);

        // Search
        bookingPage.searchTrains();

        // Get list of results
        List<String> trains = bookingPage.getTrainNames();

        // Check that train 143 exists
        assertTrue("Train #143 should be present in the results", trains.contains("143 К"));
    }

    private String getCurrentDatePlusMonth() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return new SimpleDateFormat("MM.dd.yyyy").format(new Date(calendar.getTime().getTime()));
    }

}
