package helpers;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SFHelper {
	
	private static final String LOOKUP_ICON_PATTERN = "img[@alt='%s']";
	private static final String EDIT_LINK_PATTERN = "//*[@class='listBody']//*[contains(text(),'%s')]/../../../preceding-sibling::td[contains(@class,'ACTION_COLUMN')]//a[1]/span";
	private static final String LIST_VIEW_RECORD_LINK_PATTERN = "//div[@class='listBody']//span[text()='%s']";
	private static final String GLOBAL_SEARCH_INPUT_BOX_SELECTOR = "//input[@id='phSearchInput']";
	private static final String SEARCH_BUTTON_ID = "phSearchButton";
	private static final String SEARCH_STRING_PICKLIST_PATTERN = "//strong[text()='%s']/ancestor::a[1]";

	public static void selectElementFromLookup(WebDriver driver, String altText, String search_value) {
		// Store the current window handle
		//String winHandleBefore = driver.getWindowHandle();
		
		///lookup_accountName.click();
		String selector = String.format(LOOKUP_ICON_PATTERN, altText);
		driver.findElement(By.xpath(selector)).click();
		
		//Wait
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Set<String> WINDOWIDS= driver.getWindowHandles();
 	    Iterator<String> iter=WINDOWIDS.iterator();    
 	    //String Window1=iter.next();//Returns first window id
 	    String Window2=iter.next();//Returns second window id
 	    driver.switchTo().window(Window2);
		
 	    driver.switchTo().frame("searchFrame");
	    driver.findElement(By.id("lksrch")).click();
	    driver.findElement(By.id("lksrch")).sendKeys(search_value);
	    driver.findElement(By.name("go")).click();
	    
	    driver.switchTo().defaultContent();
	    driver.switchTo().parentFrame();
	    driver.switchTo().frame("Results");
	    driver.findElement(By.linkText(search_value)).click();

	    // Switch back to original browser (first window)
	    //driver.switchTo().window(winHandleBefore);
	}
	
	public static void goToTab(WebDriver driver, String tabName) {
		driver.findElement(By.cssSelector("#AllTab_Tab > a")).click();
	    driver.findElement(By.linkText(tabName)).click();
	}
	
	public static void click_link(WebDriver driver, String link_name) {
		driver.findElement(By.linkText(link_name)).click();
	}
	
	public static void click_Edit_link_on_list_view(WebDriver driver, String record_name) {
		String selector = String.format(EDIT_LINK_PATTERN, record_name);
		driver.findElements(By.xpath(selector)).get(0).click();
	}
	
	public static void click_record_link_list_view(WebDriver driver, String record_name){
		String selector = String.format(LIST_VIEW_RECORD_LINK_PATTERN, record_name);
		wait_until_element_is_not_visible(driver, "waitingSearchDiv");
		driver.findElements(By.xpath(selector)).get(0).click();
	}
	
	public static void go_to_user_from_global_search(WebDriver driver, String record_name)
	{
		driver.findElement(By.xpath(GLOBAL_SEARCH_INPUT_BOX_SELECTOR)).sendKeys(record_name);
		driver.findElement(By.id(SEARCH_BUTTON_ID)).click();
		driver.findElements(By.xpath(String.format(SEARCH_STRING_PICKLIST_PATTERN, record_name))).get(0).click();
	}
	
	public static void wait_until_element_is_not_visible(WebDriver driver, String element_text)
	{
		By loadingImage = By.className(element_text); 
		WebDriverWait wait = new WebDriverWait(driver, 20); 
		wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingImage));
	}
}
