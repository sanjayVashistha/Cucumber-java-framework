Feature: Check TSMC Validations

  Scenario: Verify validations on TSMC records
    Given User is logged in to Salesforce as SV User
    And Go to TSMC Request page
    When Click on Edit Button
    And Update Name Field to SV Opp
    And Click on Save Button
