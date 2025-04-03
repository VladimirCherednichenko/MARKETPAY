package com.marketpay.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverFactory implements WebDriverFactory {
    private static final Logger logger = LoggerUtil.getLogger(ChromeDriverFactory.class);

    @Override
    public WebDriver createDriver() {
        logger.info("Setting up Chrome WebDriver");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        
        WebDriver driver = new ChromeDriver(options);
        logger.info("Chrome WebDriver initialized successfully");
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