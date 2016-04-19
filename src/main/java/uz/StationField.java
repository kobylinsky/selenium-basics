package uz;

import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;

/**
 * @author bogdankobylinsky
 */
public class StationField extends HtmlElement {

    private WebDriver webDriver;

    @FindBy(xpath = ".//input")
    private WebElement input;

    @FindBy(xpath = ".//div[@class = 'autosuggest']/div")
    private List<WebElement> suggestedStations;

    /**
     * Set station name in the input field
     *
     * @param stationString station name
     */
    public void setValue(String stationString) {
        input.sendKeys(stationString);
        chooseSuggestedStation(stationString);
    }

    /**
     * Click on one station from the suggested list
     *
     * @param stationString station name
     */
    private void chooseSuggestedStation(String stationString) {
        new WebDriverWait(webDriver, 10).until((Object x) -> {
            return !suggestedStations.isEmpty();
        });

        WebElement suggestedStation = suggestedStations.stream().filter(suggestion -> suggestion.getText().equals(stationString)).findFirst().get();
        if (suggestedStation != null) {
            suggestedStation.click();
        }

    }

    /**
     * Requires override
     */
    @Override
    public Rectangle getRect() {
        return new Rectangle(getLocation(), getSize());
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
}