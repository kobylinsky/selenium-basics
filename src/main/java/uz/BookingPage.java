package uz;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

import java.util.List;
import java.util.stream.Collectors;

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
    private Button submitButton;

    @FindBy(xpath = ".//table[@id='ts_res_tbl']//td[@class='num']//a")
    private List<WebElement> trains;

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

    public void searchTrains() {
        submitButton.click();
    }

    public String getDate() {
        return dateDepartion.getAttribute("value");
    }

    public void setDate(String date) {
        // Clear a date field
        if (webDriver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) webDriver).executeScript("document.getElementById('date_dep').setAttribute('value', '')");
        } else {
            throw new RuntimeException("Browser doesn't support JS");
        }
        dateDepartion.sendKeys(date);
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
        } catch (Exception ignored) {
            return false;
        }
    }
}
