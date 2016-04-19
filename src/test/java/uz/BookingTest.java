package uz;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * AUT: http://booking.uz.gov.ua/
 *
 * You have to implement the following Test Case:
 * - Select Departure Station from list
 * - Select Destination Station from list
 * - Select Departure Date
 * - Click "Search" button
 * - Verify that table with results are displayed
 * - Verify that table contains necessary train numbers
 *
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

    @Before
    public void setUp() {
        webDriver = new FirefoxDriver();
        bookingPage = new BookingPage(webDriver);
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
        final SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        final Date newDate = new Date(calendar.getTime().getTime());
        return dateFormat.format(newDate);
    }

}
