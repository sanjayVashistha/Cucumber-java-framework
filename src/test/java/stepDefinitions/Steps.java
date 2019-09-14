package stepDefinitions;

import helpers.SFHelper;
import helpers.World;
import managers.PageObjectHandler;
import managers.WebDriverHandler;

import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataReader.ConfigReader;
import pageObjects.LoginPage;
import pageObjects.OpportunityPage;
import pageObjects.TSMCRequestPage;

public class Steps {
	
	 WebDriver driver;
	 LoginPage login;
	 OpportunityPage opp;
	 TSMCRequestPage tsmcRequest;
	 PageObjectHandler pageObjectHandler;
	 WebDriverHandler driverHandler;
	 World world;
	 //ConfigReader configFileReader;

	 public Steps(World world) {
	 this.world = world;
	 login = world.getPageObjectHandler().getLoginPage();
	 opp = world.getPageObjectHandler().getOpportunityPage();
	 driver = world.getWebDriverHandler().getDriver();
	 System.out.println("Login- "+ login);
	 System.out.println("Driver- "+ driver);
	 }
	
	//@Given("User is on Login Page.")
	public void user_is_on_Login_Page() {
		login.navigate_to(driver, ConfigReader.getInstance().getApplicationUrl());
	    //throw new cucumber.api.PendingException();
	}

	//@When("User enters username {string}")
	public void user_enters_username(String string) {
		//login = pageObjectHandler.getLoginPage();
		login.enter_userName(string);
	}
	
	//@When("User enters password {string}")
	public void user_enters_password(String string) {
		//login = pageObjectHandler.getLoginPage();
		login.enter_passWord(string);
	}

	//@When("User clicks on Login button.")
	public void user_clicks_on_Login_button() {
		//login = pageObjectHandler.getLoginPage();
	    login.click_loginButton();
	}

	//@Then("User is logged in Successfully.")
	public void user_is_logged_in_Successfully() {
		driverHandler.closeDriver();
	}
	
	@Given("User is logged in to Salesforce as SV User")
	public void user_is_logged_in_to_Salesforce_as() {
		user_is_on_Login_Page();
		user_enters_username("internalcossupportadmin@cadence.com");
		user_enters_password("Cdns1234");
//		user_enters_username("testusertest@test.com");
//		user_enters_password("welcome@1");
		user_clicks_on_Login_button();
	}
	
	@And("Go to Opportunity Test oppo3")
	public void go_to_opportunity(){
		SFHelper.goToTab(driver, "Opportunities");
		opp.clickGoButton();
		
	}

	@And("Go to TSMC Request page")
	public void go_to_tsmc_request(){
		SFHelper.goToTab(driver, "TSMC Request");
		tsmcRequest.clickGoButton();
		
	}

	@When("Click on Edit Button")
	public void click_on_edit_button(){
		opp.clickListViewQuickIcon();
		SFHelper.click_record_link_list_view(driver, "Test oppo1");
		opp.clickEditButton();
	}
	
	@And("Update Name Field to SV Opp")
	public void update_name_field(){
		opp.setOpportunityName("SV Opp");
	}
	
	@And("Click on Save Button")
	public void click_save_button(){
		opp.clickSaveButton();
		driver.close();
		driver.quit();
	}

}
