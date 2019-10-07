package integrationHelpers;

import com.sforce.soap.partner.*;
import com.sforce.soap.partner.Error;
import com.sforce.soap.partner.sobject.*;
import com.sforce.ws.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
    
public class SFIntegrator {
		static PartnerConnection connection;
		static Map<String,String> userdetailmap;
		public static Set<String> createdrecordids;
		public static Map<String,String> tsmcrecordsdetailmap;
		public static void main(String[] ids) throws FileNotFoundException {
			SFIntegrator s = new SFIntegrator();
			try {
				connection = s.createConnection("niry@cadence.com.hfx", "test1234", "https://test.salesforce.com");
				String[] approverIds={"005d0000001T4Ft"};
				userdetailmap = new HashMap<String,String>();
				String outputfilepath="Data/TSMC_Input_temp.csv";
				s.generateCSVFileToInsert(outputfilepath);
				SFBulkIntegrator example = new SFBulkIntegrator();
		        createdrecordids=example.createRecords("TSMC_Agreement__c", "niry@cadence.com.hfx", "test1234GLtveHssuazsBi6WMqDtCuhzU", outputfilepath);
		        s.submitForApprovalRecords(createdrecordids,approverIds);
		        s.saveRecordIdsInCSV(createdrecordids,"records");
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
			catch(Exception e){
				
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

		public void submitForApprovalRecords(Set<String> createdrecordids, String[] approverIds) {
		for(String id : createdrecordids)
		        {
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
			}
		
			public void generateCSVFileToInsert(String outputfilepath)throws IOException
			{
				ArrayList<String> usernames = new ArrayList<String>();    
				File csvFile = new File("Data/TSMC_Input.csv");  
				File csvFileTemp = new File(outputfilepath);
			    BufferedReader br = new BufferedReader(new FileReader(csvFile)); 
			    String line = "";  
			    StringTokenizer st = null;  
			    int lineNumber = 0;   
			    int tokenNumber = 0;  
			    while ((line = br.readLine()) != null) 
			    {  
			       lineNumber++;  
			       st = new StringTokenizer(line, ",");  
			       while (st.hasMoreTokens()) {  
			       tokenNumber++;  
			       String sd=st.nextToken() + "  ";  
			       if(tokenNumber == 4 && lineNumber > 1)
			       {  
			          usernames.add(sd);  
			       }  
			       }  
			     tokenNumber = 0;  
			     }  
			     String query= "select ID,name from User where isactive =true and Employee_Type__c='Employee' and Employment_Status__c='Available' and name in ";
			     String userparameter="(";
			     for(int i=0;i<usernames.size();i++)
			     {
			        userparameter += usernames.get(i) + ",";
			     }
			     userparameter = userparameter.substring(0,(userparameter.length()-2));
			     userparameter += ")";
			     query += userparameter ;
			     query = query.replaceAll("\"","'");
			     String ids[]=queryIds("User",query);
			     //System.out.println("result "+userdetailmap.size() +  "@"+userdetailmap.get(ids[0]));
			     BufferedWriter bw = new BufferedWriter(new FileWriter(csvFileTemp)); 
			     br = new BufferedReader(new FileReader(csvFile)); 
			     line = "";  
			     st = null;  
			     lineNumber = 0;   
			     tokenNumber = 0;  
			     while ((line = br.readLine()) != null) {  
			     //System.out.println("line before udpate :::" + line);
			     lineNumber++;  
			     st = new StringTokenizer(line, ",");  
			     while (st.hasMoreTokens()) 
			     {  
			             tokenNumber++;  
			              String sd=st.nextToken();  
			              String uid ="";
			              if(tokenNumber == 4 && lineNumber > 1){ 
			            	  for (String entry : userdetailmap.keySet()) {
				                   if (entry.contains(sd.replace("\"", ""))) {
				                	   uid = userdetailmap.get(entry);
				                	   line=line.replace(sd,"\""+ uid+"\"");
				                	   break;
				                   }
				               } 
			            	
			             }  
			              if(tokenNumber == 8 && lineNumber > 1 && sd.contains("--None--"))
			              {
			            	  line=line.replace(sd,"\"\"");
			              }
			               
			       }  
			       tokenNumber = 0; 
			       //System.out.println("going to update file"+line);
                   bw.write(line);
			       bw.newLine();
			       }  
			       bw.close();
			         
			}
			public void saveRecordIdsInCSV(Set<String> ids,String fileName) throws IOException
			{
				File fobj = new File("Data/"+fileName);
				BufferedWriter bw = new BufferedWriter(new FileWriter(fobj));
				for(String id : ids)
				{
					bw.write(id);
					bw.newLine();
				}  
		        bw.close();
		        System.out.println(fileName +" is successfully created in Data folder haaving created record ids");
			}
			/*public void getRecordAckNumbersBasedOnUniqueValue(Set<String> uniquevalues)
			{
				try {
					String query="select id,name,remark__c from TSMC_Agreement__c where remark__c in (";
					QueryResult qResult = null;
					int i=0;
					for(String val:uniquevalues)
					{
						if(i==0)
							query += "'"+val+"'";
						else
							query += ",'"+val+"'";
						i++;
					}
					query += ")";
					qResult = connection.query(query);
					boolean done = false;
					if (qResult.getSize() > 0) 
					{
						while (!done) {
				            SObject[] records = qResult.getRecords();
				            for (i = 0; i < records.length; ++i) {
				               SObject object = records[i];
				               System.out.println((String)object.getField("Id"));
				               System.out.println((String)object.getField("Name"));
				               System.out.println((String)object.getField("Remar__c"));
				            }
				            if (qResult.isDone()) {
				               done = true;
				            } else {
				               qResult = connection.queryMore(qResult.getQueryLocator());
				            }
				         }
					}
					else 
					{
				         System.out.println("No records found.");
				    }
				}
				catch(Exception ce)
				{
					ce.printStackTrace();
				}
			}*/
		
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
			         ids= new String[qResult.getSize()];
			         while (!done) {
			            SObject[] records = qResult.getRecords();
			            for (int i = 0; i < records.length; ++i) {
			               SObject object = records[i];
			               ids[i] = object.getId();
			               //if(sObject.toUpperCase() == "USER")
			               userdetailmap.put(((String)object.getField("Name"))+":"+object.getId(),object.getId());
			               
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