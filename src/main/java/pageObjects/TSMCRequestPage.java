package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class TSMCRequestPage extends BasePage{
	
	public TSMCRequestPage(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	//*********************** Generic Xpaths ***************
	public static String  error_message_below_fields = "//*[text()='%s']/ancestor::td[1]/following-sibling::td[1]//div[text()=' You must enter a value']";
	public String  field_value_xpath = "//td[text()='%s']/following-sibling::td[1]/div";

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
	
	@FindBy(how = How.NAME, using = "submit")
	private WebElement button_submitForApproval;
	
	//**********Edit Page Fields Selector*************//
	@FindBy(how = How.ID, using = "fcf")
	private WebElement picklist_tsmcView;
	
	@FindBy(how = How.XPATH, using = "//span[text()='T' and @class='listItemPad']")
	private WebElement list_view_quick_icon;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Purpose']/../following-sibling::td/div/span/select")
	private WebElement picklist_purpose;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Process Node']/../following-sibling::td/div//select")
	private WebElement picklist_processNode;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Employee Type']/../following-sibling::td/div/span/select")
	private WebElement picklist_employeeType;
	
	@FindBy(how = How.XPATH, using = "//input[@title='User']")
	private WebElement textbox_user;
	
	@FindBy(how = How.XPATH, using = "//*[text()='EDA Tool,IP Name,DS project and customer']/../../following-sibling::td/div//input")
	private WebElement textbox_edaRequest;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Support Type']/../following-sibling::td/div//select")
	private WebElement picklist_supportType;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Permitted Purpose']/../../following-sibling::td/div//input")
	private WebElement textbox_permittedPurpose;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Field(Sales/AE)']/../../following-sibling::td/span/select")
	private WebElement picklist_fieldSalesAE;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Physical Location']/../following-sibling::td/div//select")
	private WebElement picklist_physicalLocation;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Detailed Job Description']/../../following-sibling::td/div/textarea")
	private WebElement textarea_detailedJobDescription;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Remark']/../following-sibling::td/textarea")
	private WebElement textarea_remark;
	
	@FindBy(how = How.XPATH, using = "//div[contains(.,'Approval History')][contains(@class,'bRelatedList')]//span[@class='extraStatus']")
	private WebElement relatedlist_approvalHistoryStatus;
	
	@FindBy(how = How.XPATH, using = "//div[contains(.,'Approval History')][contains(@class,'bRelatedList')]//a[text()='Approve / Reject']")
	private WebElement link_approvalHistoryApproveReject;
	
	@FindBy(how = How.XPATH, using = "//div[contains(.,'Approval History')][contains(@class,'bRelatedList')]//a[text()='Reassign']")
	private WebElement link_approvalHistoryReassign;
	
	@FindBy(how = How.CSS, using = "#Comments")
	private WebElement textarea_approvalHistoryComments;
	
	@FindBy(how = How.XPATH, using = "//input[@name='goNext']")
	private WebElement button_approvalHistoryApprove;
	
	@FindBy(how = How.XPATH, using = "//input[@name='Reject']")
	private WebElement button_approvalHistoryReject;
	
	@FindBy(how = How.XPATH, using = "//h2[@class='pageDescription']")
	private WebElement tsmc_record_id;
	
	//***********Error Message Selectors*******//
	
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

	
	//**********Methods *******************//
	
	public void clickListViewQuickIcon(){
		click(list_view_quick_icon);
	}
	
	public void clickContinueButton(){
		click(button_continue);
	}
	
	public void clickSaveButton(){
		click(button_save);
	}
	
	public void clickEditButton(){
		click(button_edit);
	}
	
	public void clickNewButton(){
		click(button_new);
	}

	public void clickNewTSMCRequest()
	{
		click(button_new_tsmc_request);
	}
	
	public void clickGoButton(){
		click(button_go);
	}
	
	public void clickSubmitForApprovalButton(){
		click(button_submitForApproval);
		acceptAlert();
	}
	
	public void selectPurpose(String purpose) {
		selectPicklist(picklist_purpose, purpose);
	}
	
	public void selectProcessNode(String process_node) {
		selectPicklist(picklist_processNode, process_node);
	}
	
	public void selectEmployeeType(String employee_type) {
		selectPicklist(picklist_employeeType, employee_type);
	}
	
	public void selectSupportType(String support_type) {
		selectPicklist(picklist_supportType, support_type);
	}
	
	public void selectPhysicalLocation(String physical_location) {
		selectPicklist(picklist_physicalLocation, physical_location);
	}
	
	public void selectField(String field) {
		selectPicklist(picklist_fieldSalesAE, field);
	}
	
	//======================================Getters======================================
	public String getTSMCRecordId()
	{
		return tsmc_record_id.getText();
	}
	
	public String getTSMCFieldValue(String fieldName) {
		String selector = String.format(field_value_xpath, fieldName);
		return getTextByXpath(selector);
	}

	//======================================Setters======================================
	public void setUser(String user) {
		type(textbox_user,user);
	}
	
	public void setEDARequest(String eda_request) {
		type(textbox_edaRequest,eda_request);
	}
	
	public void setPermittedPurpose(String permitted_purpose) {
		type(textbox_permittedPurpose,permitted_purpose);
	}
	
	public void setJobDescription(String job_description) {
		type(textarea_detailedJobDescription,job_description);
	}
	
	public void setRemark(String remark) {
		type(textarea_remark,remark);
	}
	
	public void assertApprovalHistoryStatus(String status) {
		assertValue(status, relatedlist_approvalHistoryStatus.getText());
	}
	
	public void assertErrorMessageOnPurposeField() {
		assertValue("Error: You must enter a value", error_message_for_purpose_field.getText());
	}

	public void assertErrorMessageOnEmployeeTypeField() {
		assertValue("Error: You must enter a value", error_message_for_employee_type_field.getText());
		
	}

	public void assertErrorMessageOnEdaToolIPNameDSProjectandCustomerField() {
		assertValue("Error: You must enter a value", error_message_for_eda_tool_ip_name_field.getText());
	}
	
	public void assertErrorMessageOnPermittedPurposeField() {
		assertValue("Error: You must enter a value", error_message_for_permitted_purpose_field.getText());
	}
	
	public void assertErrorMessageOnPhysicalLocationField() {
		assertValue("Error: You must enter a value", error_message_for_physical_location_field.getText());
	}

	public void assertErrorMessageOnDetailedJobDescriptionField() {
		assertValue("Error: You must enter a value", error_message_for_detailed_job_description_field.getText());
	}

	public void assertErrorMessageOnUserField() {
		assertValue("Error: You must enter a value", error_message_for_user_field.getText());
	}

	public void assertErrorMessageOnSupportTypeField() {
		assertValue("Error: You must enter a value", error_message_for_support_type_field.getText());
	}
}
