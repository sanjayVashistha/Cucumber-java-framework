Feature: Check Opportunity Validations

  Scenario: Verify validations on existing Opportunity
    Given User is logged in to Salesforce as SV User
		And Go to Opportunity Test oppo3
    When Click on Edit Button
    And Update Name Field to SV Opp
    And Click on Save Button