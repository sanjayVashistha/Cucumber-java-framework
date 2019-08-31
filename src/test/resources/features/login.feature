Feature: Login in the org
  The purpose of this test is to test the login functionality

  Scenario: User is able to login into salesforce.
    Given User is on Login Page.
    When User enters username ""
    And User enters password ""
    And User clicks on Login button.
    Then User is logged in Successfully.
