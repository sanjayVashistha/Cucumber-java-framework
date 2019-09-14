package managers;

import org.openqa.selenium.WebDriver;

import pageObjects.LoginPage;
import pageObjects.OpportunityPage;
import pageObjects.TSMCRequestPage;

public class PageObjectHandler {
	
	private WebDriver driver;
	private LoginPage login;
	private OpportunityPage opp;
	private TSMCRequestPage tsmcRequest;
	
	public PageObjectHandler(WebDriver driver){
		this.driver = driver;
	}
	
	public LoginPage getLoginPage(){
		return (login==null) ? login = new LoginPage(driver) : login;
	}
	
	public OpportunityPage getOpportunityPage(){
		return (opp==null) ? opp = new OpportunityPage(driver) : opp;
	}
	
	public TSMCRequestPage getTSMCRequestPage(){
		return (tsmcRequest==null) ? tsmcRequest = new TSMCRequestPage(driver) : tsmcRequest;
	}
}