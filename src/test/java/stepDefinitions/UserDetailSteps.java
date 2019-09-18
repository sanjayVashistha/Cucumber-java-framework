package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helpers.SFHelper;
import helpers.World;
import pageObjects.UserDetailPage;

public class UserDetailSteps{
	World world;
	UserDetailPage userDetail;
	WebDriver driver;
	
	 public UserDetailSteps(World world) {
		 this.world = world;
		 userDetail = world.getPageObjectHandler().getUserDetailPage();
		 driver = world.getWebDriverHandler().getDriver();
	}

	@Then("Go To User Detail Page")
	public void go_to_user_detail_page()
	{
		userDetail.goToUserDetailPage();
	}
	
	@And("Click on Login Button")
	public void clickOnLoginButton() throws InterruptedException
	{
		userDetail.clickOnLoginButton();
		Thread.sleep(10000);
	}
}
