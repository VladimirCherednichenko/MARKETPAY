package pages;

import com.marketpay.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import services.PaymentService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class PaymentFormPage extends BasePage {
    private static final Logger logger = LoggerUtil.getLogger(PaymentFormPage.class);

    @FindBy(xpath = "//n-dropdown[@name='testData' and @placeholder='Select test data']//button[@role='listbox']")
    private WebElement testDataDropdown;

    @FindBy(xpath = "//div[@class='dropdown_menu' and @style='display: block;']//label[@class='search_group']//input[@type='search']")
    private WebElement dropdownSearchInput;

    @FindBy(xpath = "//n-checkbox[@name='customerCheckbox']//input[@type='checkbox' and @id='checkbox-0']")
    private WebElement customerCheckbox;

    @FindBy(xpath = "//n-checkbox[@name='customerCheckbox']//label[contains(., 'Customer (/CUS)')]")
    private WebElement customerCheckboxLabel;

    @FindBy(xpath = "//div[@class='checkout-button']//button[contains(text(), 'Checkout')]")
    private WebElement checkoutButton;

    @FindBy(id = "ipgframe")
    private WebElement paymentIframe;

    @FindBy(xpath = "//input[@id='form_CREDITCARD:creditCardNumber0']")
    private WebElement cardNumberField;

    @FindBy(xpath = "//input[@id='form_CREDITCARD:expirationDate0']")
    private WebElement expiryDateField;

    @FindBy(xpath = "//input[@id='form_CREDITCARD:cardVerificationCode00']")
    private WebElement cvvField;

    @FindBy(xpath = "//input[@id='form_CREDITCARD:nameOnCard0' and @autocomplete='cc-name']")
    private WebElement nameOnCardField;

    @FindBy(xpath = "//input[@id='form_CREDITCARD:billingEmailAddress0']")
    private WebElement emailField;

    @FindBy(xpath = "//div[@id='accordionButtonCREDITCARD' and contains(@class, 'continueButton')]")
    private WebElement payButton;

    @FindBy(xpath = "//div[contains(@class, 'session-status-xml')]//code")
    private WebElement xmlContainer;

    @FindBy(xpath = "//h4[@class='status success']")
    private WebElement sessionStatusElement;

    public PaymentFormPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToCheckout() {
        logger.info("Navigating to checkout page");
        driver.get("https://demoshop.preprod.mpg.market-pay.com/shop/#/checkout");
        waitForPageToLoad();
        logger.info("Checkout page loaded successfully");
    }

    public void selectTestDataOption(String optionText) {
        logger.info("Selecting test data option: {}", optionText);
        waitForElementToBeClickable(testDataDropdown);
        click(testDataDropdown);

        waitForElementToBeVisible(dropdownSearchInput);
        type(dropdownSearchInput, optionText);

        String optionXpath = String.format("//div[@class='dropdown_menu']//ul[@class='listbox']//li[span[text()='%s']]/span", optionText);
        WebElement optionElement = driver.findElement(By.xpath(optionXpath));
        click(optionElement);
        logger.info("Selected test data option: {}", optionText);
    }

    public void toggleCustomerCheckbox() {
        logger.info("Toggling customer checkbox");
        waitForElementToBeVisible(customerCheckboxLabel);
        if (customerCheckbox.isSelected()) {
            click(customerCheckboxLabel);
            logger.info("Customer checkbox unchecked");
        } else {
            logger.info("Customer checkbox was already unchecked");
        }
    }

    public void clickCheckoutButton() {
        logger.info("Clicking checkout button");
        waitForElementToBeClickable(checkoutButton);
        click(checkoutButton);
        logger.info("Checkout button clicked");
    }

    public boolean isPaymentFormVisible() {
        try {
            logger.info("Checking if payment form is visible");
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ipgframe")));
            boolean isVisible = iframe.isDisplayed();
            logger.info("Payment form visible: {}", isVisible);
            return isVisible;
        } catch (Exception e) {
            logger.error("Payment form is not visible: {}", e.getMessage());
            return false;
        }
    }

    public void switchToPaymentFrame() {
        logger.debug("Switching to payment iframe");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(paymentIframe));
    }

    public void switchToDefaultFrame() {
        logger.debug("Switching back to default content");
        driver.switchTo().defaultContent();
    }

    public void enterCardNumber(String cardNumber) {
        logger.info("Entering card number: {}", cardNumber.substring(0, 4) + "********" + cardNumber.substring(cardNumber.length() - 4));
        switchToPaymentFrame();
        waitForElementToBeClickable(cardNumberField);
        type(cardNumberField, cardNumber);
        switchToDefaultFrame();
    }

    public void enterExpiryDate(String expiryDate) {
        logger.info("Entering expiry date: {}", expiryDate);
        switchToPaymentFrame();
        String formattedExpiryDate = expiryDate.replace("/", "");
        type(expiryDateField, formattedExpiryDate);
        switchToDefaultFrame();
    }

    public void enterCVV(String cvv) {
        logger.info("Entering CVV: ***");
        switchToPaymentFrame();
        type(cvvField, cvv);
        switchToDefaultFrame();
    }

    public void enterNameOnCard(String name) {
        logger.info("Entering name on card: {}", name);
        switchToPaymentFrame();
        waitForElementToBeVisible(nameOnCardField);
        nameOnCardField.clear();
        type(nameOnCardField, name);
        switchToDefaultFrame();
    }

    public void enterEmail(String email) {
        logger.info("Entering email: {}", email);
        switchToPaymentFrame();
        waitForElementToBeVisible(emailField);
        type(emailField, email);
        switchToDefaultFrame();
    }

    public void clickPayButton() {
        logger.info("Clicking Pay button");
        switchToPaymentFrame();
        click(payButton);
        switchToDefaultFrame();
        logger.info("Pay button clicked");
    }

    public void hoverOverCardNumberField() {
        switchToPaymentFrame();
        hoverOverElement(cardNumberField);
        switchToDefaultFrame();
    }

    public void hoverOverExpiryDateField() {
        switchToPaymentFrame();
        hoverOverElement(expiryDateField);
        switchToDefaultFrame();
    }

    public void hoverOverCVVField() {
        switchToPaymentFrame();
        hoverOverElement(cvvField);
        switchToDefaultFrame();
    }

    public void hoverOverNameOnCardField() {
        switchToPaymentFrame();
        hoverOverElement(nameOnCardField);
        switchToDefaultFrame();
    }

    public void hoverOverEmailField() {
        switchToPaymentFrame();
        hoverOverElement(emailField);
        switchToDefaultFrame();
    }

    public String getXmlContent() {
        waitForElementToBeVisible(xmlContainer);
        return xmlContainer.getText();
    }

    public Document getXmlDocument() {
        try {
            return PaymentService.parseXmlContent(getXmlContent());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException("Failed to parse XML content", e);
        }
    }

    public boolean validateBankResponseCode(String expectedCode) {
        logger.info("Validating bank response code: {}", expectedCode);
        return PaymentService.verifyBankResponseCode(getXmlDocument(), expectedCode);
    }

    public boolean validateServiceMessage(String expectedMessage) {
        logger.info("Validating service message: {}", expectedMessage);
        return PaymentService.verifyServiceMessage(getXmlDocument(), expectedMessage);
    }

    public boolean validateSessionStatus(String expectedStatus) {
        logger.info("Validating session status: {}", expectedStatus);
        try {
            waitForElementToBeVisible(sessionStatusElement);
            String actualStatus = sessionStatusElement.getText();
            boolean isMatch = actualStatus.contains(expectedStatus);
            logger.info("Session status validation result: {} (actual: {})", isMatch, actualStatus);
            return isMatch;
        } catch (Exception e) {
            logger.error("Failed to validate session status: {}", e.getMessage());
            return false;
        }
    }

    public String getErrorMessage(WebElement field) {
        String ariaDescribedBy = field.getAttribute("aria-describedby");
        if (ariaDescribedBy != null && ariaDescribedBy.endsWith("_errorMsg")) {
            try {
                WebElement errorMsgElement = driver.findElement(By.id(ariaDescribedBy));
                waitForElementToBeVisible(errorMsgElement);
                return errorMsgElement.getText();
            } catch (Exception e) {
                logger.debug("Error message element not found or not visible: {}", e.getMessage());
                return "";
            }
        }
        return "";
    }

    public String getCardNumberErrorMessage() {
        switchToPaymentFrame();
        String errorMessage = getErrorMessage(cardNumberField);
        switchToDefaultFrame();
        return errorMessage;
    }

    public String getExpiryDateErrorMessage() {
        switchToPaymentFrame();
        String errorMessage = getErrorMessage(expiryDateField);
        switchToDefaultFrame();
        return errorMessage;
    }

    public String getCVVErrorMessage() {
        switchToPaymentFrame();
        String errorMessage = getErrorMessage(cvvField);
        switchToDefaultFrame();
        return errorMessage;
    }

    public String getNameOnCardErrorMessage() {
        switchToPaymentFrame();
        String errorMessage = getErrorMessage(nameOnCardField);
        switchToDefaultFrame();
        return errorMessage;
    }

    public String getEmailErrorMessage() {
        switchToPaymentFrame();
        String errorMessage = getErrorMessage(emailField);
        switchToDefaultFrame();
        return errorMessage;
    }

    public boolean hasRedBorder(WebElement element) {
        try {
            String elementId = element.getAttribute("id");
            if (elementId == null || elementId.isEmpty()) {
                return false;
            }
            String containerId = elementId + "_container";
            WebElement containerDiv = driver.findElement(By.id(containerId));
            String classAttribute = containerDiv.getAttribute("class");
            boolean hasErrorClass = classAttribute != null && classAttribute.contains("error");
            String borderColor = containerDiv.getCssValue("border-color");
            boolean hasRedBorder = borderColor != null && (borderColor.contains("rgb(231, 29, 50)"));
            return hasErrorClass && hasRedBorder;
        } catch (Exception e) {
            System.out.println("Error checking for red border: " + e.getMessage());
            return false;
        }
    }

    public boolean hasCardNumberRedBorder() {
        switchToPaymentFrame();
        boolean result = hasRedBorder(cardNumberField);
        switchToDefaultFrame();
        return result;
    }

    public boolean hasExpiryDateRedBorder() {
        switchToPaymentFrame();
        boolean result = hasRedBorder(expiryDateField);
        switchToDefaultFrame();
        return result;
    }

    public boolean hasCVVRedBorder() {
        switchToPaymentFrame();
        boolean result = hasRedBorder(cvvField);
        switchToDefaultFrame();
        return result;
    }

    public boolean hasNameOnCardRedBorder() {
        switchToPaymentFrame();
        boolean result = hasRedBorder(nameOnCardField);
        switchToDefaultFrame();
        return result;
    }

    public boolean hasEmailRedBorder() {
        switchToPaymentFrame();
        boolean result = hasRedBorder(emailField);
        switchToDefaultFrame();
        return result;
    }

    public void waitForNameOnCardFieldVisible() {
        switchToPaymentFrame();
        waitForElementDisplayed(nameOnCardField);
        switchToDefaultFrame();
    }

    public void waitForNameOnCardFieldNotVisible() {
        switchToPaymentFrame();
        waitForElementNotDisplayed(nameOnCardField);
        switchToDefaultFrame();
    }

    public void waitForEmailFieldVisible() {
        switchToPaymentFrame();
        waitForElementDisplayed(emailField);
        switchToDefaultFrame();
    }

    public void waitForEmailFieldNotVisible() {
        switchToPaymentFrame();
        waitForElementNotDisplayed(emailField);
        switchToDefaultFrame();
    }
} 