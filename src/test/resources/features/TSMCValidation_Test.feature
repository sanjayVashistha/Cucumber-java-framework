Feature: Check TSMC Validations

	Scenario: Verify validations on TSMC records
    Given User is logged in to Salesforce as SV User
    And Go to TSMC Request page
    When Click on New Button
    And Click on Save Button for TSMC record
    Then Verify Error Message on Purpose Field
    And Verify Error Message on Employee Type Field
    And Verify Error Message on EDA Tool,IP Name,DS project and customer Field
    And Verify Error Message on Permitted Purpose Field
    And Verify Error Message on Physical Location Field
	And Verify Error Message on Detailed Job Description Field
	And Verify Error Message on User Field
	And Verify Error Message on Support Type Field

	Scenario: Create a TSMC Record and Submit for Approval
	Given User is logged in to Salesforce as SV User
    And Go to TSMC Request page
    When I enter TSMC Request details as below
    | Purpose |	ProcessNode |	Employee Type  |	User                | EDA Tool IP Name |	Support Type  	   |  Permitted Purpose    | Field Sales AE    |	Office Site |	Detailed Job Description |	Remark   |
    | EDA     |	10nm	        | Employee       | Adnan Assar |	Test1	           | Product development |	Test2	               | No                |  Brazil      | Test3                    |  Test4     |
    And I click on Save button
    Then I click on Submit for Approval button
    Then Verify Approval History Status is "Pending"

	Scenario: Go to User for TSMC records Approval
    Given User is logged in to Salesforce as SV User
    And Go to TSMC Request page
    And Click on Go Button on Record List View
    When Open "TSMC-00018748" record from the Record List View
    And Get the TSMCRecord Id and Save it
    And Click Link "Adnan Assar" to Open Record
    Then Go To User Detail Page
  	And Click on Login Button
     
    