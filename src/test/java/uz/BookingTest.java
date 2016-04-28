package uz;

import org.joda.time.DateTime;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.List;

import static org.testng.Assert.assertTrue;

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
public class BookingTest extends BaseBookingTest {

    @DataProvider(name = "stations")
    public Object[][] stationsProvider() {
        return new Object[][] {
                { "Kyiv",            "Ivano-Frankivsk", "143 К" },
                { "Kyiv",            "Nizhyn",          "780 К" },
                { "Odesa",           "Lviv",            "108 Ш" },
                { "Ivano-Frankivsk", "Kyiv",            "143 Л" }
        };
    }

    @DataProvider(name = "stationsWithPartialName")
    public Object[][] stationsWithPartialNameProvider() {
        return new Object[][] {
                { "Kyi", "Kyiv",              "Ivano", "Ivano-Frankivsk", "143 К" },
                { "Kyi", "Kyiv",              "Niz", "Nizhyn",            "780 К" },
                { "Ode", "Odesa",             "Lvi", "Lviv",              "108 Ш" },
                { "Ivano", "Ivano-Frankivsk", "Kyi", "Kyiv",              "143 Л" }
        };
    }

    @Test(dataProvider = "stations")
    public void testTrains(String from, String to, String expectedTrain) throws ParseException {
        System.out.println("Running test in thread #" + Thread.currentThread().getId());
        // Load page
        bookingPage.openPage();

        // Set stations
        bookingPage.setStationFromAndChoose(from);
        bookingPage.setStationToAndChoose(to);

        // Set date
        bookingPage.setDate(DateTime.now().plusMonths(1));

        // Search
        bookingPage.searchTrains();

        assertTrue(bookingPage.isTrainsListPresent());

        // Get list of results
        List<String> actualTrains = bookingPage.getTrainNames();

        // Check that train 143 exists
        assertTrue(actualTrains.contains(expectedTrain), String.format("Train '%s' should be present in the results: %s", expectedTrain, actualTrains));
    }

    @Test(dataProvider = "stationsWithPartialName")
    public void testTrainsWithPartialName(String fromPartial, String from, String toPartial, String to, String expectedTrain) throws ParseException {
        System.out.println("Running test in thread #" + Thread.currentThread().getId());

        // Load page
        bookingPage.openPage();

        // Set stations
        bookingPage.setStationFromAndChoose(fromPartial, from);
        bookingPage.setStationToAndChoose(toPartial, to);

        // Set date
        bookingPage.setDate(DateTime.now().plusMonths(1));

        // Search
        bookingPage.searchTrains();

        assertTrue(bookingPage.isTrainsListPresent());

        // Get list of results
        List<String> actualTrains = bookingPage.getTrainNames();

        // Check that train 143 exists
        assertTrue(actualTrains.contains(expectedTrain), String.format("Train '%s' should be present in the results: %s", expectedTrain, actualTrains));
    }

}
