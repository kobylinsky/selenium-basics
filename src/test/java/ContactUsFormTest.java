import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author bogdankobylinsky
 */
public class ContactUsFormTest extends BaseContactUsTest {

    @Test
    public void testSubmit() {
        // Setting correct test data
        setValue(XPATH_INPUT_NAME, "Bogdan");
        setValue(XPATH_INPUT_MAIL, "BOGDAN.KOBYLYNSKYI@AVID.COM");
        setValue(XPATH_INPUT_TELEPHONE, "460776017125670");
        setValue(XPATH_INPUT_COUNTRY, "KIEV");
        setValue(XPATH_INPUT_COMPANY, "GL");
        setValue(XPATH_INPUT_MESSAGE, "_");

        // Check that success popup is absent
        assertFalse(isElementPresent(XPATH_SUBMITION_SUCCESS_ALERT));

        // Submit
        clickSubmit();

        // Popup appeared
        waitForElementToBeVisible(XPATH_SUBMITION_SUCCESS_ALERT);
        assertTrue(isElementPresent(XPATH_SUBMITION_SUCCESS_ALERT));
    }

    @Test
    public void testSubmitIncorrectEmail() {
        // Setting incorrect email
        setValue(XPATH_INPUT_NAME, "Bogdan");
        setValue(XPATH_INPUT_MAIL, "IncorrectEmail");
        setValue(XPATH_INPUT_TELEPHONE, "460776017125670");
        setValue(XPATH_INPUT_COUNTRY, "KIEV");
        setValue(XPATH_INPUT_COMPANY, "GL");
        setValue(XPATH_INPUT_MESSAGE, "_");

        // Check that success popup is absent
        assertFalse(isElementPresent(XPATH_SUBMITION_SUCCESS_ALERT));
        assertFalse(isElementPresent(XPATH_INPUT_MAIL + XPATH_ALERT_SUFFIX));

        // Submit
        clickSubmit();

        // Success popup is absent
        assertFalse(isElementPresent(XPATH_SUBMITION_SUCCESS_ALERT));
        assertTrue(isElementPresent(XPATH_INPUT_MAIL + XPATH_ALERT_SUFFIX));
        assertTrue(getValue(XPATH_INPUT_MAIL + XPATH_ALERT_SUFFIX).contains("Invalid email address"));
    }

}
