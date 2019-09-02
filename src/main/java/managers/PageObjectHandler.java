package managers;

import org.openqa.selenium.WebDriver;

import pageObjects.LoginPage;
import pageObjects.OpportunityPage;

public class PageObjectHandler {
	
	private WebDriver driver;
	private LoginPage login;
	private OpportunityPage opp;
	
	public PageObjectHandler(WebDriver driver){
		this.driver = driver;
	}
	
	public LoginPage getLoginPage(){
		return (login==null) ? login = new LoginPage(driver) : login;
	}
	
	public OpportunityPage getOpportunityPage(){
		return (opp==null) ? opp = new OpportunityPage(driver) : opp;
	}
}