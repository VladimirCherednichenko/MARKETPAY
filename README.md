# Payment Form Automation (Java + Selenium + Cucumber + Allure)

This project provides robust end-to-end test automation for the Market Pay payment form using Java, Selenium WebDriver, Cucumber BDD, and Allure reporting.

## Requirements

- Java 17+
- Maven
- Chrome/Firefox browser
- Allure CLI (for report generation)

## Technology Stack

- **Java 17**: programming language
- **Selenium 4.16.1**: WebDriver framework
- **Cucumber 7.14.0**: BDD test framework
- **Allure 2.24.0**: reporting
- **WebDriverManager 5.5.3**: Automated driver management
- **Log4j 2.22.1**:  logging 

## Project Structure

```
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── marketpay
│   │               └── utils     # Utility classes
│   │                   ├── BrowserType.java          # Browser enum types
│   │                   ├── ChromeDriverFactory.java  # Chrome-specific driver factory
│   │                   ├── ConfigManager.java        # Property/configuration manager
│   │                   ├── Constants.java            # Application constants
│   │                   ├── FirefoxDriverFactory.java # Firefox-specific driver factory
│   │                   ├── LoggerUtil.java           # Logging utility
│   │                   ├── WebDriverFactory.java     # Browser factory interface
│   │                   └── WebDriverManager.java     # WebDriver management
│   └── test
│       ├── java
│       │   ├── pages            # Page Objects (POM implementation)
│       │   ├── runner           # Cucumber Test Runner
│       │   ├── services         # Service layer classes
│       │   └── steps            # Step Definitions
│       └── resources
│           ├── features         # Cucumber Feature Files
│           └── config.properties # Configuration file
└── pom.xml                      # Maven project configuration
```

## Core Components

### Page Object Model
The project follows the Page Object Model (POM) design pattern:
- `pages`: Contains page objects representing different UI components
- Each page class encapsulates page elements and interactions
- Promotes reusability and maintainability

### Browser Factory
Implements a factory pattern for browser instantiation:
- `WebDriverFactory`: Interface defining browser creation contract
- `ChromeDriverFactory`: Chrome-specific implementation
- `FirefoxDriverFactory`: Firefox-specific implementation
- `BrowserType`: Enum of supported browsers

### Configuration Management
- `ConfigManager`: Handles property loading and access
- `Constants`: Stores application-wide constant values
- `WebDriverManager`: Manages driver

## Setup

```bash
mvn clean install
```

## Configuration

The project uses a configuration system to manage test settings. The main configuration file is located at:

```
src/test/resources/config.properties
```

### Configuration Properties

| Property | Description | Default Value |
|----------|-------------|---------------|
| browser  | Browser for test execution (chrome/firefox) | chrome |
| baseUrl  | Base URL for test execution | https://marketplace.example.com/shop/#/checkout |

### Using System Properties

You can override configuration properties by using system properties:

```bash
mvn test -Dbrowser=firefox -DbaseUrl=https://staging.marketplace.example.com/shop/#/checkout
```

## Run Tests

### Run All Tests

```bash
mvn test
```

### Run Specific Features

```bash
mvn test -Dcucumber.filter.tags="@smoke"
```

## Generate Allure Report

To generate and open the Allure report:

```bash
mvn allure:serve
```

Alternatively, to generate the report without opening it:

```bash
mvn allure:report
```

The generated report will be available in `target/site/allure-maven-plugin/`.

## Test Case Details

The automated tests validate the payment form functionality on the Market Pay Demoshop platform:

- Navigation to the payment checkout page
- Form completion and submission
- Validation of various field inputs
- Verification of field appearance behavior
- Success criteria validation

## Test Data

- Test URL: Uses baseUrl from config.properties
- Routing: NETCETERA - Netcetera 3DS
- Uncheck the Customer (/CUS) checkbox before clicking Checkout
- Card data: 4000000000001208, 08/25, 123 