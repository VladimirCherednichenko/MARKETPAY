# Payment Form Automation (Java + Selenium + Cucumber + Allure)

This project demonstrates end-to-end test automation for the Market Pay payment form using Java, Selenium WebDriver, Cucumber BDD, and Allure reporting.

## Requirements

- Java 17+
- Maven
- Chrome browser
- Allure CLI

## Project Structure

```
├── src
│   ├── main
│   │   └── resources
│   └── test
│       ├── java
│       │   ├── pages       # Page Objects
│       │   ├── runner      # Cucumber Test Runner
│       │   └── steps       # Step Definitions
│       └── resources
│           └── features    # Cucumber Feature Files
└── pom.xml
```

## Setup

```bash

mvn clean install
```

## Run Tests

```bash
mvn test
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

- Test URL: https://demoshop.preprod.mpg.market-pay.com/shop/#/checkout
- Routing: NETCETERA - Netcetera 3DS
- Uncheck the Customer (/CUS) checkbox before clicking Checkout
- Card data: 4000000000001208, 08/25, 123 