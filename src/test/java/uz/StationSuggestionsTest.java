package uz;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * @author bogdankobylinsky
 */
public class StationSuggestionsTest extends BaseBookingTest {

    @DataProvider(name = "stationNames")
    public Object[][] stationNamesProvider() {
        return new Object[][] {
                { "Kyi", Arrays.asList("Kyiv", "Kyivska Rusanivka") },
                { "Lot", Arrays.asList("Lotos", "Lotskyne", "Lotva", "Lotykove") },
                { "Lv", Arrays.asList("Lviv", "Lvovo", "Lvovskaia") }
        };
    }

    @Test(dataProvider = "stationNames")
    public void testStationSuggestions(String station, List<String> expectedsuggestions) throws ParseException {
        System.out.println("Running test in thread #" + Thread.currentThread().getId());
        // Load page
        bookingPage.openPage();

        // Set station
        bookingPage.setStationFrom(station);

        // get suggestions
        List<String> actualSuggestions = bookingPage.getSuggestionsForStationFrom();

        assertTrue(actualSuggestions.containsAll(expectedsuggestions));
        assertTrue(expectedsuggestions.containsAll(actualSuggestions));
    }

}
