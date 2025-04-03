package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.PaymentFormPage;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PaymentFormSteps {
    private WebDriver driver;
    private PaymentFormPage paymentFormPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
        paymentFormPage = new PaymentFormPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am on the payment checkout page")
    public void navigateToPaymentCheckoutPage() {
        paymentFormPage.navigateToCheckout();
    }

    @When("I select {string} from the dropdown")
    public void selectOptionFromDropdown(String option) {
        paymentFormPage.selectTestDataOption(option);
    }

    @When("I uncheck the {string} checkbox")
    public void uncheckCheckbox(String checkboxName) {
        if (checkboxName.equals("Customer (/CUS)")) {
            paymentFormPage.toggleCustomerCheckbox();
        }
    }

    @When("I click the {string} button")
    public void clickButton(String buttonName) {
        if (buttonName.equals("Checkout")) {
            paymentFormPage.clickCheckoutButton();
        }
    }

    @Then("the payment form should be visible")
    public void verifyPaymentFormIsVisible() {
        assertTrue("Payment form is not visible", paymentFormPage.isPaymentFormVisible());
    }

    @When("I enter card number {string}")
    public void enterCardNumber(String cardNumber) {
        paymentFormPage.enterCardNumber(cardNumber);
    }

    @When("I enter expiration date {string}")
    public void enterExpirationDate(String expiryDate) {
        paymentFormPage.enterExpiryDate(expiryDate);
    }

    @When("I enter CVV code {string}")
    public void enterCVVCode(String cvv) {
        paymentFormPage.enterCVV(cvv);
    }

    @When("I enter name on card {string}")
    public void enterNameOnCard(String name) {
        paymentFormPage.enterNameOnCard(name);
    }

    @When("I enter email {string}")
    public void enterEmail(String email) {
        paymentFormPage.enterEmail(email);
    }

    @When("I click the Pay button")
    public void clickPayButton() {
        paymentFormPage.clickPayButton();
    }

    @When("I hover over the card number field")
    public void hoverOverCardNumberField() {
        paymentFormPage.hoverOverCardNumberField();
    }

    @When("I hover over the expiration date field")
    public void hoverOverExpirationDateField() {
        paymentFormPage.hoverOverExpiryDateField();
    }

    @When("I hover over the CVV field")
    public void hoverOverCVVField() {
        paymentFormPage.hoverOverCVVField();
    }

    @When("I hover over the name on card field")
    public void hoverOverNameOnCardField() {
        paymentFormPage.hoverOverNameOnCardField();
    }

    @When("I hover over the email field")
    public void hoverOverEmailField() {
        paymentFormPage.hoverOverEmailField();
    }

    @Then("I should see successful payment confirmation")
    public void verifySuccessfulPaymentConfirmation() {
        assertTrue("Payment status is not 'Completed'", paymentFormPage.validateSessionStatus("Completed"));
    }

    @Then("the response should include success code {string}")
    public void verifyResponseIncludesSuccessCode(String code) {
        assertTrue("Bank Response Code is not '" + code + "'", paymentFormPage.validateBankResponseCode(code));
    }

    @Then("the response should include message {string}")
    public void verifyResponseIncludesMessage(String message) {
        assertTrue("Service message is not '" + message + "'", paymentFormPage.validateServiceMessage(message));
    }

    @Then("I should see card number error message {string}")
    public void verifyCardNumberErrorMessage(String expectedMessage) {
        String actualMessage = paymentFormPage.getCardNumberErrorMessage();
        assertEquals("Incorrect error message shown for card number field", expectedMessage, actualMessage);
    }

    @Then("I should see expiration date error message {string}")
    public void verifyExpirationDateErrorMessage(String expectedMessage) {
        String actualMessage = paymentFormPage.getExpiryDateErrorMessage();
        assertEquals("Incorrect error message shown for expiration date field", expectedMessage, actualMessage);
    }

    @Then("I should see CVV error message {string}")
    public void verifyCVVErrorMessage(String expectedMessage) {
        String actualMessage = paymentFormPage.getCVVErrorMessage();
        assertEquals("Incorrect error message shown for CVV field", expectedMessage, actualMessage);
    }

    @Then("I should see name on card error message {string}")
    public void verifyNameOnCardErrorMessage(String expectedMessage) {
        String actualMessage = paymentFormPage.getNameOnCardErrorMessage();
        assertEquals("Incorrect error message shown for name on card field", expectedMessage, actualMessage);
    }

    @Then("I should see email error message {string}")
    public void verifyEmailErrorMessage(String expectedMessage) {
        String actualMessage = paymentFormPage.getEmailErrorMessage();
        assertEquals("Incorrect error message shown for email field", expectedMessage, actualMessage);
    }

    @Then("the card number field should have a red border")
    public void verifyCardNumberFieldRedBorder() {
        assertTrue("Card number field does not have a red border", paymentFormPage.hasCardNumberRedBorder());
    }

    @Then("the expiration date field should have a red border")
    public void verifyExpirationDateFieldRedBorder() {
        assertTrue("Expiration date field does not have a red border", paymentFormPage.hasExpiryDateRedBorder());
    }

    @Then("the CVV field should have a red border")
    public void verifyCVVFieldRedBorder() {
        assertTrue("CVV field does not have a red border", paymentFormPage.hasCVVRedBorder());
    }

    @Then("the name on card field should have a red border")
    public void verifyNameOnCardFieldRedBorder() {
        assertTrue("Name on card field does not have a red border", paymentFormPage.hasNameOnCardRedBorder());
    }

    @Then("the email field should have a red border")
    public void verifyEmailFieldRedBorder() {
        assertTrue("Email field does not have a red border", paymentFormPage.hasEmailRedBorder());
    }

    @Then("the {string} field should not be visible")
    public void verifyFieldNotVisible(String fieldName) {
        if (fieldName.equals("Name")) {
            paymentFormPage.waitForNameOnCardFieldNotVisible();
        } else if (fieldName.equals("Email")) {
            paymentFormPage.waitForEmailFieldNotVisible();
        }
    }

    @Then("the {string} field should be visible")
    public void verifyFieldVisible(String fieldName) {
        if (fieldName.equals("Name")) {
            paymentFormPage.waitForNameOnCardFieldVisible();
        } else if (fieldName.equals("Email")) {
            paymentFormPage.waitForEmailFieldVisible();
        }
    }
} 