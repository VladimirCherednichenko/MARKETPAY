package com.marketpay.utils;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class WebDriverManager {
    private static final Logger logger = LoggerUtil.getLogger(WebDriverManager.class);
    private static WebDriverFactory defaultFactory = null;
    
    private WebDriverManager() {
    }
    
    public static WebDriver getDriver() {
        initializeDefaultFactory();
        return defaultFactory.createDriver();
    }
    
    public static WebDriver getDriver(BrowserType browserType) {
        WebDriverFactory factory;
        
        switch (browserType) {
            case FIREFOX:
                factory = new FirefoxDriverFactory();
                break;
            case CHROME:
            default:
                factory = new ChromeDriverFactory();
                break;
        }
        
        logger.info("Creating WebDriver for browser: {}", browserType);
        return factory.createDriver();
    }
    
    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            initializeDefaultFactory();
            defaultFactory.quitDriver(driver);
        }
    }
    
    public static void setDriverFactory(WebDriverFactory factory) {
        if (factory != null) {
            logger.info("Setting custom WebDriver factory: {}", factory.getClass().getSimpleName());
            defaultFactory = factory;
        }
    }
    
    public static void setDefaultBrowser(BrowserType browserType) {
        switch (browserType) {
            case FIREFOX:
                defaultFactory = new FirefoxDriverFactory();
                break;
            case CHROME:
            default:
                defaultFactory = new ChromeDriverFactory();
                break;
        }
        logger.info("Set default browser to: {}", browserType);
    }
    
    private static void initializeDefaultFactory() {
        if (defaultFactory == null) {
            BrowserType browserType = getBrowserTypeFromConfig();
            setDefaultBrowser(browserType);
        }
    }
    
    private static BrowserType getBrowserTypeFromConfig() {
        String browserName = ConfigManager.getProperty("browser", "chrome").toUpperCase();
        try {
            return BrowserType.valueOf(browserName);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid browser name: {}. Using default Chrome browser.", browserName);
            return BrowserType.CHROME;
        }
    }
} 