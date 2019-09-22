package pageObjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class BasePage {
	 private WebDriver driver;
	 ArrayList<String> tabs;
	 
	 public BasePage(WebDriver driver) {
		 this.driver = driver;
	 }
	 
	 public void visit(String url) {
		 driver.get(url);
	 }
	 public void click(WebElement locator) {
		 WebDriverWait wait = new WebDriverWait(driver, 20);
		 WebElement element = wait.until(ExpectedConditions.visibilityOf(locator));
		 locator.click();
	 }

	 public void acceptAlert()
	 {
		Alert confirmationAlert = driver.switchTo().alert();
		confirmationAlert.accept();
	 }
	 
	 public void type(WebElement locator, String input) {
		 locator.sendKeys(input);
	 }
	 
	 public Boolean isDisplayed(WebElement locator) {
		 return locator.isDisplayed();
	 }
	 
	 public void selectPicklist(WebElement selector, String type){
		Select picklist = new Select(selector);
		picklist.selectByValue(type);
	 }
	 
	 public void assertValue(String expected, String actual){
		 assertEquals(expected,actual);
	 }
	 
	 public String getTextByXpath(String xpath) {
		 return driver.findElement(By.xpath(xpath)).getText();
	 }
	 
	 public void openURLInNewTab(String URL) {
		 ((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
		 tabs = new ArrayList<String> (driver.getWindowHandles());
		 System.out.println(tabs.size());
		 driver.switchTo().window(tabs.get(1)); 
		 driver.get(URL);
	 }
	 
	 public void assertText(WebElement element, String text){
		 assertEquals(text, element.getText());
	 }
	 
	 public void closeCurrentTab(){
		 driver.findElement(By.tagName("body")).sendKeys(Keys.CONTROL + "w");
		 driver.switchTo().window(tabs.get(0));	 
     }
}
