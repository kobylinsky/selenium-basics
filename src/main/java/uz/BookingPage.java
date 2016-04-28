package uz;

import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.List;
import java.util.stream.Collectors;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author bogdankobylinsky
 */
public class BookingPage {

    private WebDriver webDriver;

    private String url;

    @FindBy(id = "station_from")
    private StationField stationFrom;

    @FindBy(id = "station_till")
    private StationField stationTo;

    @FindBy(id = "date_dep")
    private WebElement dateDepartion;

    @FindBy(name = "search")
    private WebElement submitButton;

    @FindBy(xpath = ".//table[@id='ts_res_tbl']//td[@class='num']//a")
    private List<WebElement> trains;

    private static final String DEPARTURE_DATE_XPATH = ".//*[@id='ui-datepicker-div']//td[@data-month='%d']//a[text()='%d']";

    public BookingPage(WebDriver webDriver, String url) {
        this.webDriver = webDriver;
        this.url = url;
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(webDriver)), this);
        stationFrom.setWebDriver(webDriver);
        stationTo.setWebDriver(webDriver);
    }

    public void setStationFrom(String station) {
        stationFrom.setValue(station);
    }

    public void setStationTo(String station) {
        stationTo.setValue(station);
    }

    public List<String> getSuggestionsForStationFrom() {
        return stationFrom.getSuggestions();
    }

    public List<String> getSuggestionsForStationTo() {
        return stationTo.getSuggestions();
    }

    public void setStationToAndChoose(String station) {
        stationTo.setValueAndChoose(station);
    }

    public void setStationFromAndChoose(String station) {
        stationFrom.setValueAndChoose(station);
    }

    public void searchTrains() {
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();
    }

    public String getDate() {
        return dateDepartion.getAttribute("value");
    }

    public void setDate(DateTime date) {
        dateDepartion.click();
        int retry = 0;
        do {
            try {
                new WebDriverWait(webDriver, 5).until(visibilityOfElementLocated(By.xpath(
                        String.format(DEPARTURE_DATE_XPATH, date.monthOfYear().get() - 1,
                                date.dayOfMonth().get() - retry)))).click();
                break;
            } catch (WebDriverException ignored) {
                retry++;
            }
        } while (retry <= 3); // If currently there is Jan31 and we are trying to click on Feb31->Feb30->Feb29->Feb28
    }

    public void openPage() {
        webDriver.get(url);
    }

    public List<String> getTrainNames() {
        return trains.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public boolean isTrainsListPresent() {
        try {
            new WebDriverWait(webDriver, 10).until(ExpectedConditions.visibilityOfAllElements(trains));
            return true;
        } catch (TimeoutException ignored) {
            return false;
        }
    }
}
