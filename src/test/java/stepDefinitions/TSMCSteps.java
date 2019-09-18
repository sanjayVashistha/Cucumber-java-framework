package stepDefinitions;

import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helpers.SFHelper;
import helpers.World;
import pageObjects.TSMCRequestPage;

public class TSMCSteps {
	World world;
	TSMCRequestPage tsmcRequest;
	WebDriver driver;
	
	 public TSMCSteps(World world) {
		 this.world = world;
		 tsmcRequest = world.getPageObjectHandler().getTSMCRequestPage();
		 driver = world.getWebDriverHandler().getDriver();
	}
	
	@Then("Verify Error Message on {string} Field")
	public void verify_Error_Message_on_Field(String string) {
		tsmcRequest.assertErrorMessageOnPurposeField();
	    // Write code here that turns the phrase above into concrete actions
	}
	
	@And("Click on Go Button on Record List View")
	public void click_on_Go_Button_on_Record_list_view() {
		tsmcRequest.clickGoButton();
	}
	
	
	@When("^Open \"(.*)\" record from the Record List View$")
	public void open_record_from_the_Record_List_View(String recordName) {
		SFHelper.click_record_link_list_view(driver, recordName);
	}
	
	@And("^Click Link \"(.*)\" to Open Record$")
	public void click_link_to_open_record(String user) {
		SFHelper.click_link(driver, user);
	}
}
