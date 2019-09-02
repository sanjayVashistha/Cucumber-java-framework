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
	
	@FindBy(how = How.ID, using = "username")
	private WebElement textBox_userName;
	
	@FindBy(how = How.ID, using = "password")
	private WebElement textBox_password;
	
	@FindBy(how = How.ID, using = "Login")
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
