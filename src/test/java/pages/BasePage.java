package pages;

import com.marketpay.utils.Constants;
import com.marketpay.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WebDriverWait shortWait;
    protected WebDriverWait extendedWait;
    private static final Logger logger = LoggerUtil.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_TIMEOUT_SECONDS));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(Constants.SHORT_TIMEOUT));
        this.extendedWait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXTENDED_TIMEOUT));
        PageFactory.initElements(driver, this);
        logger.debug("Initialized page: {}", this.getClass().getSimpleName());
    }

    protected void waitForElementToBeVisible(WebElement element) {
        try {
            logger.debug("Waiting for element to be visible: {}", element);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            logger.error("Element not visible after {} seconds: {}", Constants.ELEMENT_VISIBILITY_TIMEOUT, element);
        }
    }

    protected void waitForElementToBeClickable(WebElement element) {
        try {
            logger.debug("Waiting for element to be clickable: {}", element);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            logger.error("Element not clickable after {} seconds: {}", Constants.ELEMENT_CLICKABLE_TIMEOUT, element);
        }
    }

    protected void waitForPageToLoad() {
        try {
            logger.debug("Waiting for page to load");
            wait.until((ExpectedCondition<Boolean>) wd ->
                    ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        } catch (TimeoutException e) {
            logger.error("Page not loaded completely after {} seconds", Constants.PAGE_LOAD_TIMEOUT);
        }
    }

    protected void click(WebElement element) {
        try {
            logger.debug("Clicking on element: {}", element);
            waitForElementToBeClickable(element);
            element.click();
        } catch (Exception e) {
            logger.error("Failed to click element: {}", e.getMessage());
            throw e;
        }
    }

    protected void type(WebElement element, String text) {
        try {
            logger.debug("Typing text '{}' into element: {}", text, element);
            waitForElementToBeVisible(element);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            logger.error("Failed to type text: {}", e.getMessage());
        }
    }


    protected void waitForElementDisplayed(WebElement element) {
        logger.debug("Waiting for element to be displayed: {}", element);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementNotDisplayed(WebElement element) {
        logger.debug("Waiting for element to not be displayed: {}", element);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected void hoverOverElement(WebElement element) {
        logger.debug("Hovering over element: {}", element);
        waitForElementToBeVisible(element);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }


} 