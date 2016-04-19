package old;

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

        // Success popup is absent
        assertFalse(isElementPresent(XPATH_SUBMITION_SUCCESS_ALERT));

        // Submit
        clickSubmit();

        // Wait for submission
        waitForElementToBeVisible(XPATH_SUBMITION_SUCCESS_ALERT);
        // Success popup is present
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

        // Success popup is absent
        assertFalse(isElementPresent(XPATH_SUBMITION_SUCCESS_ALERT));
        assertFalse(isElementPresent(XPATH_INPUT_MAIL + XPATH_ALERT_SUFFIX));

        // Submit
        clickSubmit();

        // Success popup is absent
        assertFalse(isElementPresent(XPATH_SUBMITION_SUCCESS_ALERT));
        // Alert is present
        assertTrue(isElementPresent(XPATH_INPUT_MAIL + XPATH_ALERT_SUFFIX));

        final String mailErrorText = getValue(XPATH_INPUT_MAIL + XPATH_ALERT_SUFFIX);
        assertTrue("Error: " + mailErrorText, mailErrorText.contains("Invalid email address"));
    }

    @Test
    public void testSubmitIncorrectTelephone() {
        // Setting incorrect email
        setValue(XPATH_INPUT_NAME, "Bogdan");
        setValue(XPATH_INPUT_MAIL, "correct.email@gl.com");
        setValue(XPATH_INPUT_TELEPHONE, "INcorrect telephone");
        setValue(XPATH_INPUT_COUNTRY, "KIEV");
        setValue(XPATH_INPUT_COMPANY, "GL");
        setValue(XPATH_INPUT_MESSAGE, "_");

        // Auccess popup is absent
        assertFalse(isElementPresent(XPATH_SUBMITION_SUCCESS_ALERT));
        // Alert is absent
        assertFalse(isElementPresent(XPATH_INPUT_TELEPHONE + XPATH_ALERT_SUFFIX));

        // Submit
        clickSubmit();

        // Success popup is absent
        assertFalse(isElementPresent(XPATH_SUBMITION_SUCCESS_ALERT));
        // Alert is present
        assertTrue(isElementPresent(XPATH_INPUT_TELEPHONE + XPATH_ALERT_SUFFIX));

        final String phoneErrorText = getValue(XPATH_INPUT_TELEPHONE + XPATH_ALERT_SUFFIX);
        assertTrue("Error: " + phoneErrorText, phoneErrorText.contains("Invalid phone number"));
    }

    @Test
    public void testSubmitIncorrectTelephoneAndEmail() {
        // Setting incorrect email
        setValue(XPATH_INPUT_NAME, "Bogdan");
        setValue(XPATH_INPUT_MAIL, "Incorrect.email");
        setValue(XPATH_INPUT_TELEPHONE, "INcorrect telephone");
        setValue(XPATH_INPUT_COUNTRY, "KIEV");
        setValue(XPATH_INPUT_COMPANY, "GL");
        setValue(XPATH_INPUT_MESSAGE, "_");

        // Auccess popup is absent
        assertFalse(isElementPresent(XPATH_SUBMITION_SUCCESS_ALERT));
        // Alerts are absent
        assertFalse(isElementPresent(XPATH_INPUT_MAIL + XPATH_ALERT_SUFFIX));
        assertFalse(isElementPresent(XPATH_INPUT_TELEPHONE + XPATH_ALERT_SUFFIX));

        // Submit
        clickSubmit();

        // Success popup is absent
        assertFalse(isElementPresent(XPATH_SUBMITION_SUCCESS_ALERT));
        // Alerts are present
        assertTrue(isElementPresent(XPATH_INPUT_MAIL + XPATH_ALERT_SUFFIX));
        assertTrue(isElementPresent(XPATH_INPUT_TELEPHONE + XPATH_ALERT_SUFFIX));

        final String mailErrorText = getValue(XPATH_INPUT_MAIL + XPATH_ALERT_SUFFIX);
        assertTrue("Error: " + mailErrorText, mailErrorText.contains("Invalid email address"));

        final String phoneErrorText = getValue(XPATH_INPUT_TELEPHONE + XPATH_ALERT_SUFFIX);
        assertTrue("Error: " + phoneErrorText, phoneErrorText.contains("Invalid phone number"));
    }

}
