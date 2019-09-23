package stepDefinitions;

import java.util.Map;
import java.util.List;

import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import enums.Context;
import helpers.SFHelper;
import helpers.World;
import managers.ScenarioContext;
import pageObjects.EchoSignPDFPage;
import pageObjects.TSMCRequestPage;

public class TSMCSteps {
	World world;
	TSMCRequestPage tsmcRequest;
	EchoSignPDFPage echoSignPDF;
	WebDriver driver;
	ScenarioContext sc;
	
	 public TSMCSteps(World world) {
		 this.world = world;
		 tsmcRequest = world.getPageObjectHandler().getTSMCRequestPage();
		 echoSignPDF = world.getPageObjectHandler().getEchoSignPDFPage();
		 driver = world.getWebDriverHandler().getDriver();
		 sc = world.getScenarioContext();
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
		tsmcRequest.sortListInDescendingOrder();
		SFHelper.click_record_link_list_view(driver, String.valueOf(world.getScenarioContext().getContext(Context.TSMC_RECORD_ID)));
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
	
	@And("Get the TSMCRecord Id and Save it")
	public void get_the_tsmcrecord_id_and_save_it()
	{
		sc.setContext(Context.TSMC_RECORD_ID, tsmcRequest.getTSMCRecordId());
		System.out.println(world.getScenarioContext().getContext(Context.TSMC_RECORD_ID));
	}
	
	@Then("Verify Status is \"(.*)\"")
	public void verify_Status_is(String statusFieldValue) {
		tsmcRequest.assertFieldValue(String.format(TSMCRequestPage.detail_page_field_value_xpath, "Status"), statusFieldValue);
	}
	
	@Then("Visit EchoSign PDF link in the new tab")
	public void visit_echosign_pdf_link_in_the_new_tab() {
		echoSignPDF.goToEchoSignPDF((String) world.getScenarioContext().getContext(Context.ECHOSIGN_PDF_LINK));
	}
	
	@Then("Click on the Next button in PDF")
	public void click_on_next_button_in_PDF() {
		echoSignPDF.clickNextButton();
	}
	
	@Then("Click and enter Text \"(.*)\" in the textfield in PDF")
	public void enter_text_in_the_textfield_in_pdf(String text) {
		echoSignPDF.setSignatureField(text);
	}
	
	@Then("Click on Apply button in PDF")
	public void click_on_apply_button_in_PDF() {
		echoSignPDF.clickApplyButton();
	}
	
	@Then("Click on Click to Sign button in PDF")
	public void click_on_click_to_sign_button_in_PDF() {
		echoSignPDF.clickClickToEsignButton();
	}
	
	@Then("Verify success message of signed PDF")
	public void verify_success_message_of_signed_PDF() {
		echoSignPDF.assertSuccessMessage((String) world.getScenarioContext().getContext(Context.TSMC_RECORD_ID));
	}
	
	@Then("Close EchoSign browser tab")
	public void close_EchoSign_browser_tab() {
		echoSignPDF.closeCurentTab();
	}
	
	@Then("Click on Approve link present in Approval History Related List")
	public void click_on_approve_link_present_in_approval_history_related_list()
	{
		tsmcRequest.clickOnApproveLinkPresentInApprovalHistoryRelatedList();
	}
	
	@Then("Click on Approve button")
	public void click_on_approve_button()
	{
		tsmcRequest.clickApproveButton();
	}
	
	@Then("Add Comment \"(.*)\"")
	public void set_comments(String commentValue)
	{
		tsmcRequest.setComments(commentValue);
	}
	
	@Then("Click on Complete button and handle Alert OK")
	public void click_complete_button()
	{
		tsmcRequest.clickCompleteButton();
	}
	
	@Then("Verify in Notes and Attachments that new attachment is added to record")
	public void verify_in_notes_and_attachments_that_new_attachment_is_added_to_record()
	{
		
	}
}
