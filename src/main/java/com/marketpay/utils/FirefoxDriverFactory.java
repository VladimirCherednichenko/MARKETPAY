package com.marketpay.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverFactory implements WebDriverFactory {
    private static final Logger logger = LoggerUtil.getLogger(FirefoxDriverFactory.class);

    @Override
    public WebDriver createDriver() {
        logger.info("Setting up Firefox WebDriver");
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        
        WebDriver driver = new FirefoxDriver(options);
        logger.info("Firefox WebDriver initialized successfully");
        return driver;
    }

    @Override
    public void quitDriver(WebDriver driver) {
        if (driver != null) {
            logger.info("Quitting WebDriver");
            driver.quit();
        }
    }
} 