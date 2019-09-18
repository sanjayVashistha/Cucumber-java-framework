package stepDefinitions;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.sforce.async.AsyncApiException;
import com.sforce.ws.ConnectionException;

import integrationHelpers.SFBulkIntegrator;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import helpers.World;

public class Hooks {
	WebDriver driver;
	World world;
	
	public Hooks(World world) 
	{
		 this.world = world;
		 driver = world.getWebDriverHandler().getDriver();
		 System.out.println("Driver- "+ driver);
	}
	
	@Before
	public void beforeScenario() throws AsyncApiException, ConnectionException, IOException{
		//SFBulkIntegrator integrator = new SFBulkIntegrator();
		//integrator.createRecords("Opportunity", "testusertest@test.com", "welcome@1GXcj25SJUCPsJozxbdLRwz2J", "Data/Opportunity.csv");
	}
	
	@After
	public void afterScenario(){
		world.getWebDriverHandler().getDriver().close();
	}
	
	
	

}
