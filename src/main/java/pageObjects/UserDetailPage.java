package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class UserDetailPage extends BasePage{
	
	public UserDetailPage(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
	}

	//**********Buttons Selector*************//
	@FindBy(how = How.XPATH, using = "(//input[@name='login'])[1]")
	private WebElement loginButton;
	
	//**********Edit Page Fields Selector*************//
	@FindBy(how = How.XPATH, using = "//input[@title='User']")
	private WebElement textbox_user;
	
	@FindBy(how = How.XPATH, using = "//a[@title='User Action Menu']")
	private WebElement userActionMenuLink;
	
	@FindBy(how = How.XPATH, using = "//a[@title='User Detail']")
	private WebElement userDetailLink;
	//***********Error Message Selectors*******//
	
	
	
	//**********Methods *******************//
	
	public void goToUserDetailPage()
	{
		userActionMenuLink.click();
		userDetailLink.click();		
	}

	public void clickOnLoginButton() {
		loginButton.click();
	}
	
}
