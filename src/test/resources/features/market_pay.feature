@payments
Feature: Market Pay Payment Processing and Validation
  As a user of the Market Pay payment form
  I want to process payments and validate input
  So that I can complete transactions successfully and handle errors appropriately

  Background:
    Given I am on the payment checkout page
    When I select "NETCETERA - Netcetera 3DS" from the dropdown
    And I uncheck the "Customer (/CUS)" checkbox
    And I click the "Checkout" button
    Then the payment form should be visible

  Scenario: Successfully complete a payment with valid card details
    When I enter card number "4000000000001208"
    And I enter expiration date "08/25"
    And I enter CVV code "123"
    And I enter name on card "John Doe"
    And I enter email "test@example.com"
    And I click the Pay button
    Then I should see successful payment confirmation
    And the response should include success code "000"
   # And the response should include message {string}

  Scenario Outline: Card number validation displays appropriate error messages
    When I enter card number "<card_number>"
    And I click the Pay button
    And I hover over the card number field
    Then I should see card number error message "<expected_error>"
    And the card number field should have a red border

    Examples:
      | card_number        | expected_error                   |
      | invalid123456      | Please enter a valid card number |
      | 4111               | Please enter a valid card number |
      | 4111-1111-1111-xyz | Please enter a valid card number |


  Scenario Outline: Verify specific error messages for various invalid CVV formats
    When I enter card number "4000000000001208"
    And I enter CVV code "<cvv>"
    And I enter expiration date "08/25"
    And I enter name on card "John Doe"
    And I enter email "test@example.com"
    And I click the Pay button
    And I hover over the CVV field
    Then I should see CVV error message "<expected_error>"
    And the CVV field should have a red border

    Examples:
      | cvv | expected_error                     |
      | 1   | Please enter a valid security code |
      | 12  | Please enter a valid security code |
      | abc | Please enter a security code       |
      | 1@3 | Please enter a valid security code |
      |     | Please enter a security code       |

  Scenario Outline: Verify specific error messages for various invalid expiry date formats
    When I enter card number "4000000000001208"
    And I enter expiration date "<expiry_date>"
    And I enter CVV code "123"
    And I enter name on card "John Doe"
    And I enter email "test@example.com"
    And I click the Pay button
    And I hover over the expiration date field
    Then I should see expiration date error message "<expected_error>"
    And the expiration date field should have a red border

    Examples:
      | expiry_date | expected_error                                                       |
      | 0122        | The expiration date is incorrect. Please check the date and re-enter |
      | 1299        | The expiration date is incorrect. Please check the date and re-enter |
      | abcd        | Please enter an expiration date                                      |
      | 12ab        | The expiration date is incorrect. Please check the date and re-enter |
      | @#$%        | Please enter an expiration date                                      |
      | 13/25       | The expiration date is incorrect. Please check the date and re-enter |
      |             | Please enter an expiration date                                      |

  Scenario Outline: Verify specific error messages for various invalid name formats
    When I enter card number "4000000000001208"
    And I enter expiration date "08/25"
    And I enter CVV code "123"
    And I enter name on card "<name>"
    And I enter email "test@example.com"
    And I click the Pay button
    And I hover over the name on card field
    Then I should see name on card error message "<expected_error>"
    And the name on card field should have a red border

    Examples:
      | name  | expected_error                                  |
      |       | Please provide a value for the \"Name on card\" |
      | 12345 | Please enter a valid cardholder name            |
      | !@#$% | Please enter a valid cardholder name            |


  Scenario Outline: Verify specific error messages for various invalid email formats
    When I enter card number "4000000000001208"
    And I enter expiration date "08/25"
    And I enter CVV code "123"
    And I enter name on card "John Doe"
    And I enter email "<email>"
    And I click the Pay button
    And I hover over the email field
    Then I should see email error message "<expected_error>"
    And the email field should have a red border

    Examples:
      | email                  | expected_error                      |
      |                        | Please enter an E-mail address      |
      | plainaddress           | Please enter a valid e-mail address |
      | @missingusername.com   | Please enter a valid e-mail address |
      | user@                  | Please enter a valid e-mail address |
      | user@.com              | Please enter a valid e-mail address |
      | user@domain@domain.com | Please enter a valid e-mail address |
      | user@domain..com       | Please enter a valid e-mail address |

Scenario: Submit form without entering any details
  When I click the Pay button
  Then I should see card number error message "Please enter a card number"
  And I hover over the expiration date field
  And I should see expiration date error message "Please enter an expiration date"
  And I hover over the CVV field
  And I should see CVV error message "Please enter a security code"
  And the card number field should have a red border
  And the CVV field should have a red border
  And the expiration date field should have a red border


Scenario: Visual verification of red borders on blank fields
  When I click the Pay button
  And I hover over the card number field
  Then the card number field should have a red border
  And the expiration date field should have a red border
  And the CVV field should have a red border


Scenario: Name and Email fields visibility behavior
  Then the "Name" field should not be visible
  And the "Email" field should not be visible
  When I enter card number "4000000000001208"
  Then the "Name" field should be visible
  And the "Email" field should be visible







