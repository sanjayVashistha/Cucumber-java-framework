package stepDefinitions;

import managers.PageObjectHandler;
import managers.WebDriverHandler;

import org.openqa.selenium.WebDriver;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataReader.ConfigReader;
import pageObjects.LoginPage;

public class Steps {
	
	WebDriver driver;
	LoginPage login;
	PageObjectHandler pageObjectHandler;
	WebDriverHandler driverHandler;
	//ConfigReader configFileReader;
	
	@Given("User is on Login Page.")
	public void user_is_on_Login_Page() {
		driverHandler = new WebDriverHandler();
		driver = driverHandler.getDriver();
		pageObjectHandler = new PageObjectHandler(driver);
		login = pageObjectHandler.getLoginPage();
		login.navigate_to(driver, ConfigReader.getInstance().getApplicationUrl());
	    //throw new cucumber.api.PendingException();
	}

	@When("User enters username {string}")
	public void user_enters_username(String string) {
		login = pageObjectHandler.getLoginPage();
		login.enter_userName(string);
	}
	
	@When("User enters password {string}")
	public void user_enters_password(String string) {
		login = pageObjectHandler.getLoginPage();
		login.enter_passWord(string);
	}

	@When("User clicks on Login button.")
	public void user_clicks_on_Login_button() {
		login = pageObjectHandler.getLoginPage();
	    login.click_loginButton();
	}

	@Then("User is logged in Successfully.")
	public void user_is_logged_in_Successfully() {
		driverHandler.closeDriver();
	}

}
