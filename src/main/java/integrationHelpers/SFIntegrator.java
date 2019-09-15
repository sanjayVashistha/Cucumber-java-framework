package integrationHelpers;

import com.sforce.soap.partner.*;
import com.sforce.soap.partner.Error;
import com.sforce.soap.partner.sobject.*;
import com.sforce.ws.*;
import java.io.FileNotFoundException;
    
public class SFIntegrator {
		static PartnerConnection connection;
		
		public static void main(String[] ids) throws FileNotFoundException {
			SFIntegrator s = new SFIntegrator();
			try {
				connection = s.createConnection("USERNAME", "PASSWORDTOKEN", "https://test.salesforce.com");
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
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
			               Object id = object.getId();
			               ids[i] = (String) id;
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