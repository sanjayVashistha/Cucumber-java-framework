Feature: Create TSMC Records, submit them for approval and check it in reports

Scenario Outline: TSMC Records pending approval present in reports
	Given I create records mentioned in "DataSetReports.csv" as user "User 4"
	And I submit all records for Approval
	Then I login as "Jason Chan"
	And Go to "Reports" tab
	And I verify below records in their respective reports folder
	|Purpose	| Process Node | Reports Folder | Report Name |
	|EDA		| N7		   | TSMC Reports   | N7 + EDA    | 
	|EDA		| 7nm		   | TSMC Reports   | 7nm EDA     | 