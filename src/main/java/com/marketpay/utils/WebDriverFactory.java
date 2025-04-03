package com.marketpay.utils;

import org.openqa.selenium.WebDriver;

public interface WebDriverFactory {
    WebDriver createDriver();
    void quitDriver(WebDriver driver);
} 