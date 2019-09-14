package pageObjects;

import helpers.SFHelper;

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
}
