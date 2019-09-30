package integrationHelpers;

import com.sforce.soap.partner.*;
import com.sforce.soap.partner.Error;
import com.sforce.soap.partner.sobject.*;
import com.sforce.ws.*;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
    
public class SFIntegrator {
		static PartnerConnection connection;
		
		public static void main(String[] ids) throws FileNotFoundException {
			SFIntegrator s = new SFIntegrator();
			try {
				connection = s.createConnection("USERNAME", "PASSWORD", "https://test.salesforce.com");
				String createdrecordid=s.createTSMCRecord();
				String[] approverIds={"005d0000001T4Ft"};
				s.processRecords(createdrecordid,approverIds);
				//String acknowledgementNumber = s.queryFieldById("TSMC_Agreement__c","Name","ID");
				//System.out.println(acknowledgementNumber);
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public String createTSMCRecord() {
		    String result = null;
		    try {
		        // Create a new sObject of type Contact
		           // and fill out its fields.
		        SObject tsmc = new SObject();
		        tsmc.setType("TSMC_Agreement__c");
		        tsmc.setField("Purpose__c", "EDA");
		        tsmc.setField("Process_Node__c", "7nm");
		        tsmc.setField("Employee_Type__c", "Employee");
		        tsmc.setField("User__c", "ID");
		        tsmc.setField("EDA_Tool_IP_Name__c", "Test");
		        tsmc.setField("Support_Type__c", "Product development");
		        tsmc.setField("Permitted_Purpose__c", "test 2");
		        tsmc.setField("Field_Sales_AE__c", "No");
		        tsmc.setField("Office_Site__c", "Brazil");
		        tsmc.setField("Detailed_Job_Description__c", "test 3");
		        tsmc.setField("Remark__c", "test 4");
		        
		        // Add this sObject to an array 
		        SObject[] tsmcRecords = new SObject[1];
		        tsmcRecords[0] = tsmc;
		        // Make a create call and pass it the array of sObjects
		        SaveResult[] results = connection.create(tsmcRecords);
		    
		        // Iterate through the results list
		        // and write the ID of the new sObject
		        // or the errors if the object creation failed.
		        // In this case, we only have one result
		        // since we created one contact.
		        for (int j = 0; j < results.length; j++) {
		            if (results[j].isSuccess()) {
		                result = results[j].getId();
		                System.out.println(
		                    "\nA TSMC Record was created with an ID of: " + result
		                );
		             } else {
		                // There were errors during the create call,
		                // go through the errors array and write
		                // them to the console
		                for (int i = 0; i < results[j].getErrors().length; i++) {
		                    Error err = results[j].getErrors()[i];
		                    System.out.println("Errors were found on item " + j);
		                    System.out.println("Error code: " + 
		                        err.getStatusCode().toString());
		                    System.out.println("Error message: " + err.getMessage());
		                }
		             }
		        }
		    } catch (ConnectionException ce) {
		        ce.printStackTrace();
		    }
		    return result;
		}

		public void processRecords(String id, String[] approverIds) {
			   ProcessSubmitRequest request = new ProcessSubmitRequest();
			   request.setComments("Submit for approval process.");
			   request.setObjectId(id);
			   request.setNextApproverIds(approverIds);
			   try {
			      ProcessResult[] processResults = connection
			            .process(new ProcessSubmitRequest[] { request });
			      for (ProcessResult processResult : processResults) {
			         if (processResult.isSuccess()) {
			            System.out.println("Approval submitted for: " + id + ":");
			            for (int i = 0; i < approverIds.length; i++) {
			               System.out
			                     .println("\tBy: " + approverIds[i] + " successful.");
			            }
			            System.out.println("Process Instance Status: "
			                  + processResult.getInstanceStatus());
			         } else {
			            System.out.println("Approval submitted for: " + id
			                  + ", approverIds: " + approverIds.toString() + " FAILED.");
			            System.out.println("Error: "
			                  + processResult.getErrors().toString());
			         }
			      }
			   } catch (ConnectionException e) {
			      e.printStackTrace();
			   }
			}
		
		public void deleteRecords(String sObject, String username, String password, String endpoint, String query) throws FileNotFoundException {
			System.out.println("Deleting the records.....");
			try {
				connection = createConnection(username,password,endpoint);
				String[] ids = queryIds(sObject,query);
				deleteRecords(ids);
				closeConnection();
				
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
		}
		
		private void deleteRecords(String[] ids) {
			   try {
			      DeleteResult[] deleteResults = connection.delete(ids);
			      for (int i = 0; i < deleteResults.length; i++) {
			         DeleteResult deleteResult = deleteResults[i];
			         if (deleteResult.isSuccess()) {
			            System.out.println("Deleted Record ID: " + deleteResult.getId());
			         } else {
			            // Handle the errors.
			            // We just print the first error out for sample purposes.
			            Error[] errors = deleteResult.getErrors();
			            if (errors.length > 0) {
			               System.out.println("Error: could not delete " + "Record ID "
			                     + deleteResult.getId() + ".");
			               System.out.println("   The error reported was: ("
			                     + errors[0].getStatusCode() + ") "
			                     + errors[0].getMessage() + "\n");
			            }
			         }
			      }
			   } catch (ConnectionException ce) {
			      ce.printStackTrace();
			   }
			}
		
		public String[] queryIds(String sObject,String query) {
			   QueryResult qResult = null;
			   String[] ids = null;
			   try {
			      String soqlQuery = query;
			      qResult = connection.query(soqlQuery);
			      boolean done = false;
			      if (qResult.getSize() > 0) {
			         System.out.println("Logged-in user can see a total of "
			            + qResult.getSize() + " " + sObject + " records.");
			         while (!done) {
			            SObject[] records = qResult.getRecords();
			            for (int i = 0; i < records.length; ++i) {
			               SObject object = records[i];
			               ids[i] = object.getId();
			            }
			            if (qResult.isDone()) {
			               done = true;
			            } else {
			               qResult = connection.queryMore(qResult.getQueryLocator());
			            }
			         }
			      } else {
			         System.out.println("No records found.");
			      }
			      System.out.println("\nQuery succesfully executed.");
			   } catch (ConnectionException ce) {
			      ce.printStackTrace();
			   }
			   return ids;
			}
		
		  public String queryFieldById(String sObject,String fieldAPIName, String id) {
			   QueryResult qResult = null;
			   String value = null;
			   try {
			      String soqlQuery = "SELECT " + fieldAPIName + " FROM " + sObject + " WHERE Id ='" + id + "'";
			      qResult = connection.query(soqlQuery);
			      boolean done = false;
			      if (qResult.getSize() > 0) {
			         System.out.println("Logged-in user can see a total of "
			            + qResult.getSize() + " " + sObject + " records.");
			         while (!done) {
			            SObject[] records = qResult.getRecords();
			            SObject object = records[0];
			            value = (String) object.getField(fieldAPIName);
			            if (qResult.isDone()) {
			               done = true;
			            } else {
			               qResult = connection.queryMore(qResult.getQueryLocator());
			            }
			         }
			      } else {
			         System.out.println("No records found.");
			      }
			      System.out.println("\nQuery succesfully executed.");
			   } catch (ConnectionException ce) {
			      ce.printStackTrace();
			   }
			   return value;
			}
		 /**
		 * Create the PartnerConnection used to call API operations.
		 * @throws FileNotFoundException 
		 */
	    private PartnerConnection createConnection(String userName, String password, String endpoint)
	          throws ConnectionException, FileNotFoundException {
	        ConnectorConfig partnerConfig = new ConnectorConfig();
	        partnerConfig.setUsername(userName);
	        partnerConfig.setPassword(password);
	        partnerConfig.setAuthEndpoint(endpoint + "/services/Soap/u/37.0/");
	        partnerConfig.setTraceFile("traceLogs.txt");
	        partnerConfig.setTraceMessage(true);
	        partnerConfig.setPrettyPrintXml(true);

	        // Creating the connection automatically handles login and stores
	        // the session in partnerConfig
	        return new PartnerConnection(partnerConfig);
	    }
	    
	    /**
		 * Close the PartnerConnection
	     * @throws ConnectionException 
		 */
	    private void closeConnection() throws ConnectionException {
	        connection.logout();
	    }
}