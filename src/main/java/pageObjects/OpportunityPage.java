package pageObjects;

import helpers.SFHelper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class OpportunityPage {
	WebDriver driver;
	public OpportunityPage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	//**********Buttons Selector*************//
	@FindBy(how = How.CSS, using = "div.filterOverview input[name=go]")
	private WebElement button_go;
	
	@FindBy(how = How.NAME, using = "new")
	private WebElement button_new;
	
	@FindBy(how = How.NAME, using = "newOpp")
	private WebElement button_new_opportunity;
	
	@FindBy(how = How.XPATH, using = "//*[@value='Continue']")
	private WebElement button_continue;
	
	@FindBy(how = How.NAME, using = "save")
	private WebElement button_save;
	
	@FindBy(how = How.NAME, using = "edit")
	private WebElement button_edit;
	
	
	//**********Fields Selector*************//
	@FindBy(how = How.ID, using = "fcf")
	private WebElement picklist_opportunityView;
	
	@FindBy(how = How.ID, using = "opp3")
	private WebElement textbox_opportunityName;
	
	@FindBy(how = How.ID, using = "opp4")
	private WebElement textbox_accountName;
	
	@FindBy(how = How.ID, using = "opp9")
	private WebElement textbox_closeDate;
	
	@FindBy(how = How.ID, using = "opp7")
	private WebElement textbox_amount;
	
	@FindBy(how = How.ID, using = "opp12")
	private WebElement textbox_probability;
	
	@FindBy(how = How.ID, using = "opp14")
	private WebElement textarea_description;
	
	@FindBy(how = How.ID, using = "opp11")
	private WebElement picklist_stage;
	
	@FindBy(how = How.ID, using = "opp5")
	private WebElement picklist_opportunityType;
	
	@FindBy(how = How.ID, using = "opp6")
	private WebElement picklist_lead_source;
	
	@FindBy(how = How.XPATH, using = "//span[text()='T' and @class='listItemPad']")
	private WebElement list_view_quick_icon;
	
	
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
	
	public void clickGoButton(){
		button_go.click();
	}
	
	
	public void clickOpportunityOnListView(String opportunity_name) {
		SFHelper.click_link(driver, opportunity_name);
	}
	
	public void clickEditOpportunityOnListView(String opportunity_name) {
		SFHelper.click_Edit_link_on_list_view(driver, opportunity_name);
	}
	
	public void selectOpportunityView(String view_name) {
		selectPicklist(picklist_opportunityView, view_name);
	}
	
	public void selectOpportunityType(String type){
		selectPicklist(picklist_opportunityType, type);
	}
	
	public void selectLeadSource(String source){
		selectPicklist(picklist_lead_source, source);
	}
	
	public void selectStage(String stage_name){
		selectPicklist(picklist_stage, stage_name);
	}
	
	public void setAccountNameFromLookup(String account_name) {
		SFHelper.selectElementFromLookup(driver,"Account Name Lookup (New Window)",account_name);
	}
	
	public void setOpportunityName(String opportunity_name) {
		textbox_opportunityName.sendKeys(opportunity_name);
	}
	
	public void setAccountName(String account_name) {
		textbox_accountName.sendKeys(account_name);
	}
	
	public void setProbability(String probability) {
		textbox_probability.sendKeys(probability);
	}
	
	public void setAmount(String amount) {
		textbox_amount.sendKeys(amount);
	}
	
	public void setCloseDate(String close_date) {
		textbox_closeDate.sendKeys(close_date);
	}
	
	
	public void setDescription(String description) {
		textarea_description.sendKeys(description);
	}
	//*****************Need to move this Method************************//
	private void selectPicklist(WebElement selector, String type){
		Select picklist = new Select(selector);
		picklist.selectByValue(type);
	}
}
