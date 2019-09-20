Feature: Check TSMC Validations

	Scenario: Create a TSMC Record and Submit for Approval
	Given User is logged in to Salesforce as SV User
    And Go to TSMC Request page
    When I enter TSMC Request details as below
    | Purpose |	ProcessNode |	Employee Type  |	User                | EDA Tool IP Name |	Support Type  	   |  Permitted Purpose    | Field Sales AE    |	Office Site |	Detailed Job Description |	Remark   |
    | EDA     |	7nm	        | Employee       | Adar Segal |	Test1	           | Product development |	Test2	               | No                |  Brazil      | Test3                    |  Test4     |
    And I click on Save button
    Then I click on Submit for Approval button
    Then Verify Approval History Status is "Pending"
	Then Verify Status is "Pending For Approval"
	Then Go To "Jason Chen" from AssignedTo in Approval History
	Then Verify Gmail "TSMC-00018762" for submit for Approval on manager's email
	Then Login with "Jason Chen" 
	Then Go to "TSMC-00018762" record
	Then Click on Approve link present in Approval History Related List
	Then Add Comment "Approving from my side"
	Then Click on Approve button
	Then Verify the status field value "Out For Signature"
	Then Go to Gmail and Get the link of PDF for "TSMC-00018762"
	Then Visit link landing to PDF
	Then Click on the Start button in PDF
	Then Click on Text box and New Box will appear in PDF
	Then Enter Text "Approving Adar" in the textfield in PDF 
	Then Click on "Apply" Button in PDF
	Then Click on "Click to Sign" Button in PDF 
	Then Go to record "TSMC-00018762"
	Then Verify the status field value "Fully Signed"
	Then Verify in Notes and Attachments that new attachment is addded to record
	Then Click on "Complete" button and handle Alert OK
	Then Verify the status field value "Completed"
	Then Go to Gmail and verify the policy creation
	
	
	
	
	
