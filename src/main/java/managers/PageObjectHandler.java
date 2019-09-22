package managers;

import org.openqa.selenium.WebDriver;

import pageObjects.EchoSignPDFPage;
import pageObjects.LoginPage;
import pageObjects.OpportunityPage;
import pageObjects.TSMCRequestPage;
import pageObjects.UserDetailPage;

public class PageObjectHandler {
	
	private WebDriver driver;
	private LoginPage login;
	private OpportunityPage opp;
	private TSMCRequestPage tsmcRequest;
	private UserDetailPage userDetail;
	private EchoSignPDFPage echoSignPDF;
	
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
	
	public UserDetailPage getUserDetailPage(){
		return (userDetail==null) ? userDetail = new UserDetailPage(driver) : userDetail;
	}
	
	public EchoSignPDFPage getEchoSignPDFPage(){
		return (echoSignPDF==null) ? echoSignPDF = new EchoSignPDFPage(driver) : echoSignPDF;
	}
}