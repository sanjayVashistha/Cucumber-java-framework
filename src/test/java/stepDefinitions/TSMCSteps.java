package stepDefinitions;

import java.util.Map;
import java.util.List;

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
	
	@When("I enter TSMC Request details as below")
	public void i_create_a_New_TSMC_Record_by_using_below_details(io.cucumber.datatable.DataTable dataTable) {   
	   
		tsmcRequest.clickNewButton();
		List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
	    	
		tsmcRequest.selectPurpose(list.get(0).get("Purpose"));
		tsmcRequest.selectProcessNode(list.get(0).get("ProcessNode"));
		tsmcRequest.selectEmployeeType(list.get(0).get("Employee Type"));
		tsmcRequest.setUser(list.get(0).get("User"));
		tsmcRequest.setEDARequest(list.get(0).get("EDA Tool IP Name"));
		tsmcRequest.selectSupportType(list.get(0).get("Support Type"));
		tsmcRequest.setPermittedPurpose(list.get(0).get("Permitted Purpose"));
		tsmcRequest.selectField(list.get(0).get("Field Sales AE"));
		tsmcRequest.selectPhysicalLocation(list.get(0).get("Office Site"));
		tsmcRequest.setJobDescription(list.get(0).get("Detailed Job Description"));
		tsmcRequest.setRemark(list.get(0).get("Remark"));
		
		tsmcRequest.clickSaveButton();
	}
	
	@And("I click on New button")
	public void i_click_on_new_button() {
		tsmcRequest.clickNewButton();
	}
	
	@And("I click on Save button")
	public void i_click_on_save_button() {
		tsmcRequest.clickSaveButton();
	}
	
	@Then("I click on Submit for Approval button")
	public void i_click_on_submit_for_approval_button() {
		tsmcRequest.clickSubmitForApprovalButton();
	}
	
	@Then("^Verify Approval History Status is \"(.*)\"$")
	public void verify_approval_history_status(String status) {
		tsmcRequest.assertApprovalHistoryStatus(status);
	}
}
