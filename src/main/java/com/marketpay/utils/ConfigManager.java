package com.marketpay.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "src/test/resources/config.properties";
    private static final org.apache.logging.log4j.Logger logger = LoggerUtil.getLogger(ConfigManager.class);
    private static boolean isInitialized = false;

    private ConfigManager() {
    }

    public static synchronized void init() {
        if (!isInitialized) {
            try (InputStream input = new FileInputStream(CONFIG_FILE)) {
                properties.load(input);
                isInitialized = true;
                logger.info("Configuration loaded successfully from {}", CONFIG_FILE);
            } catch (IOException e) {
                logger.error("Failed to load configuration from {}: {}", CONFIG_FILE, e.getMessage());
            }
        }
    }

    public static String getProperty(String key, String defaultValue) {
        if (!isInitialized) {
            init();
        }
        String systemProperty = System.getProperty(key);
        return systemProperty != null ? systemProperty : properties.getProperty(key, defaultValue);
    }
    
    public static String getBaseUrl() {
        return getProperty("baseUrl", "https://marketplace.example.com");
    }
} 