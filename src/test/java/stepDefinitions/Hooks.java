package stepDefinitions;

import java.io.IOException;

import managers.WebDriverHandler;

import com.sforce.async.AsyncApiException;
import com.sforce.ws.ConnectionException;

import integrationHelpers.SFIntegrator;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
	
	@Before
	public void beforeScenario() throws AsyncApiException, ConnectionException, IOException{
		SFIntegrator integrator = new SFIntegrator();
		integrator.createRecords("Opportunity", "testusertest@test.com", "welcome@1GXcj25SJUCPsJozxbdLRwz2J", "Data/Opportunity.csv");
	}
	
	@After
	public void afterScenario(){
		/*WebDriverHandler handler = new WebDriverHandler();
		handler.getDriver();
		handler.closeDriver();*/
	}

}
