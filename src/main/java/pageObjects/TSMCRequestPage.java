package pageObjects;

import helpers.SFHelper;
import junit.framework.Assert;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class TSMCRequestPage {
	WebDriver driver;
	public TSMCRequestPage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//*********************** Generic Xpaths ***************
	public static String  error_message_below_fields = "//*[text()='%s']/ancestor::td[1]/following-sibling::td[1]//div[text()=' You must enter a value']";
	
	//**********Buttons Selector*************//
	@FindBy(how = How.CSS, using = "div.filterOverview input[name=go]")
	private WebElement button_go;
	
	@FindBy(how = How.NAME, using = "new")
	private WebElement button_new;
	
	@FindBy(how = How.XPATH, using = "//input[@title='New TSMC Request']")
	private WebElement button_new_tsmc_request;
	
	@FindBy(how = How.XPATH, using = "//*[@value='Continue']")
	private WebElement button_continue;
	
	@FindBy(how = How.NAME, using = "save")
	private WebElement button_save;
	
	@FindBy(how = How.NAME, using = "edit")
	private WebElement button_edit;
	
	
	//**********Fields Selector*************//
	@FindBy(how = How.ID, using = "fcf")
	private WebElement picklist_opportunityView;
	
	@FindBy(how = How.XPATH, using = "//span[text()='T' and @class='listItemPad']")
	private WebElement list_view_quick_icon;
	
	//@FindBy(how = How.XPATH, using = String.format(error_message_below_fields, 'adf'))
	//private WebElement error_message_for_fields;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Purpose']/ancestor::td[1]/following-sibling::td[1]//div[text()=' You must enter a value']")
	private WebElement error_message_for_purpose_field;

	@FindBy(how = How.XPATH, using = "//*[text()='Employee Type']/ancestor::td[1]/following-sibling::td[1]//div[text()=' You must enter a value']")
	private WebElement error_message_for_employee_type_field;

	@FindBy(how = How.XPATH, using = "//*[text()='EDA Tool,IP Name,DS project and customer']/ancestor::td[1]/following-sibling::td[1]//div[text()=' You must enter a value']")
	private WebElement error_message_for_eda_tool_ip_name_field;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Permitted Purpose']/ancestor::td[1]/following-sibling::td[1]//div[text()=' You must enter a value']")
	private WebElement error_message_for_permitted_purpose_field;

	@FindBy(how = How.XPATH, using = "//*[text()='Physical Location']/ancestor::td[1]/following-sibling::td[1]//div[text()=' You must enter a value']")
	private WebElement error_message_for_physical_location_field;

	@FindBy(how = How.XPATH, using = "//*[text()='Detailed Job Description']/ancestor::td[1]/following-sibling::td[1]//div[text()=' You must enter a value']")
	private WebElement error_message_for_detailed_job_description_field;

	@FindBy(how = How.XPATH, using = "//*[text()='User']/ancestor::td[1]/following-sibling::td[1]//div[text()=' You must enter a value']")
	private WebElement error_message_for_user_field;

	@FindBy(how = How.XPATH, using = "//*[text()='Support Type']/ancestor::td[1]/following-sibling::td[1]//div[text()=' You must enter a value']")
	private WebElement error_message_for_support_type_field;

	//    	//label[text()='Purpose']/ancestor::td[1]/following-sibling::td[1]//select
	//		//*[text()='EDA Tool,IP Name,DS project and customer']/ancestor::td[1]/following-sibling::td[1]//input

	//**********Methods *******************//
	
	public void clickListViewQuickIcon(){
		list_view_quick_icon.click();
	}
	
	public void clickContinueButton(){
		button_continue.click();
	}
	
	public void clickSaveButton(){
		button_save.click();
	}
	
	public void clickEditButton(){
		button_edit.click();
	}
	
	public void clickNewButton(){
		button_new.click();
	}

	public void clickNewTSMCRequest()
	{
		button_new_tsmc_request.click();
	}
	
	public void clickGoButton(){
		button_go.click();
	}

	public void clickOpportunityOnListView(String opportunity_name) {
		SFHelper.click_link(driver, opportunity_name);
	}
	
	public void clickEditOpportunityOnListView(String opportunity_name) {
		SFHelper.click_Edit_link_on_list_view(driver, opportunity_name);
	}
	
	public void selectPurpose(String purpose) {
		selectPicklist(picklist_opportunityView, purpose);
	}

	//*****************Need to move this Method************************//
	private void selectPicklist(WebElement selector, String type){
		Select picklist = new Select(selector);
		picklist.selectByValue(type);
	}

	public void assertErrorMessageOnPurposeField() {
		assertEquals("Error: You must enter a value", error_message_for_purpose_field.getText());
	}

	public void assertErrorMessageOnEmployeeTypeField() {
		assertEquals("Error: You must enter a value", error_message_for_employee_type_field.getText());
		
	}

	public void assertErrorMessageOnEdaToolIPNameDSProjectandCustomerField() {
		assertEquals("Error: You must enter a value", error_message_for_eda_tool_ip_name_field.getText());
	}
	
	public void assertErrorMessageOnPermittedPurposeField() {
		assertEquals("Error: You must enter a value", error_message_for_permitted_purpose_field.getText());
	}
	
	public void assertErrorMessageOnPhysicalLocationField() {
		assertEquals("Error: You must enter a value", error_message_for_physical_location_field.getText());
	}

	public void assertErrorMessageOnDetailedJobDescriptionField() {
		assertEquals("Error: You must enter a value", error_message_for_detailed_job_description_field.getText());
	}

	public void assertErrorMessageOnUserField() {
		assertEquals("Error: You must enter a value", error_message_for_user_field.getText());
	}

	public void assertErrorMessageOnSupportTypeField() {
		assertEquals("Error: You must enter a value", error_message_for_support_type_field.getText());
	}
}
