Feature: Check TSMC Validations

  Scenario: Verify validations on TSMC records
    Given User is logged in to Salesforce as SV User
    And Go to TSMC Request page
    When Click on New Button
    And Click on Save Button for TSMC record
    Then Verify Error Message on Purpose Field
    Then Verify Error Message on Employee Type Field
    Then Verify Error Message on EDA Tool,IP Name,DS project and customer Field
    Then Verify Error Message on Permitted Purpose Field
    Then Verify Error Message on Physical Location Field
		Then Verify Error Message on Detailed Job Description Field
		Then Verify Error Message on User Field
		Then Verify Error Message on Support Type Field