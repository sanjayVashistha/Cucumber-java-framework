package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	public LoginPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.XPATH, using = "//input[@id = 'username' or @id = 'okta-signin-username']")
	private WebElement textBox_userName; //For octa login locator id will be ''
	
	@FindBy(how = How.XPATH, using = "//input[@id = password or @id = 'okta-signin-password']")
	private WebElement textBox_password;
	
	@FindBy(how = How.XPATH, using = "//input[@id = 'Login' or @id='okta-signin-submit']")
	private WebElement button_Login;
	
	public void enter_userName(String userName){
		textBox_userName.sendKeys(userName);
	}
	
	public void enter_passWord(String password){
		textBox_password.sendKeys(password);
	}
	
	public void click_loginButton(){
		button_Login.click();
	}
	
	public void navigate_to(WebDriver driver,String url){
		driver.get(url);
	}

}
