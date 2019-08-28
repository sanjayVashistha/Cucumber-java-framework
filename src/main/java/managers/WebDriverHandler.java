package managers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import dataReader.ConfigReader;

public class WebDriverHandler {
	private WebDriver driver;
	private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
	private static final String FIREFOX_DRIVER_PROPERTY = "webdriver.firefox.marionette";
	private ConfigReader configFileReader;

	public WebDriverHandler() {
		configFileReader = ConfigReader.getInstance();
	}

	public WebDriver getDriver() {
		if(driver == null) driver = createDriver();
		return driver;
	}

	private WebDriver createDriver() {
		if(!configFileReader.getRunOnSauceLab())
			driver = createNormalDriver();
		else
			driver = createSauceLabDriver();
	        	
		return driver;
	}

	private WebDriver createNormalDriver() {
		String browserName = configFileReader.getBrowserName();
		String driverPath = configFileReader.getDriverPath();
		Boolean maximizeWindow = configFileReader.getWindowMaximize();
		if(browserName==null || browserName.equalsIgnoreCase("Chrome")){
			if(driverPath.contains("chromedriver.exe")){
				System.setProperty(CHROME_DRIVER_PROPERTY, driverPath);
				driver = new ChromeDriver();
			}
			else
				throw new RuntimeException("Chrome Driver Path not specified correctly in configuration.properties file");
		}
		else if(browserName.equalsIgnoreCase("firefox")){
			if(driverPath.contains("GeckoDriver.exe")){
				System.setProperty(FIREFOX_DRIVER_PROPERTY, driverPath);
				driver = new FirefoxDriver();
			}
			else
				throw new RuntimeException("Gecko Driver Path not specified correctly in configuration.properties file");
		}
		else
			throw new RuntimeException("The driver for "+browserName+" is not currently supported by the framwork. Update WebDriverHandler.createNormalDriver() method to support it.");
		
		if(maximizeWindow) 
			driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(configFileReader.getImplicitlyWait(), TimeUnit.SECONDS);
		return driver;
	}

	private WebDriver createSauceLabDriver() {
		// TODO Auto-generated method stub
		// throw new RuntimeException("RemoteWebDriver is not yet implemented");
		return null;
	}

	public void closeDriver() {
		driver.close();
		driver.quit();
	}

}