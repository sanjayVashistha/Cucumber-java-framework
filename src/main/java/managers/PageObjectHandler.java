package managers;

import org.openqa.selenium.WebDriver;

import pageObjects.LoginPage;

public class PageObjectHandler {
	
	private WebDriver driver;
	private LoginPage login;
	
	public PageObjectHandler(WebDriver driver){
		this.driver = driver;
	}
	
	public LoginPage getLoginPage(){
		return (login ==null) ? login = new LoginPage(driver) : login;
	}
}