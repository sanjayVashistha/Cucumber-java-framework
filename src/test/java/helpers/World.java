package helpers;

import managers.PageObjectHandler;
import managers.ScenarioContext;
import managers.WebDriverHandler;

public class World {
	private WebDriverHandler webDriverHandler;
	private PageObjectHandler pageObjectHandler;
	public ScenarioContext scenarioContext;
	
	public World(){
		webDriverHandler = new WebDriverHandler();
		pageObjectHandler = new PageObjectHandler(webDriverHandler.getDriver());
		scenarioContext = new ScenarioContext();
	}
	
	public WebDriverHandler getWebDriverHandler(){
		return webDriverHandler;
	}
	
	public PageObjectHandler getPageObjectHandler(){
		return pageObjectHandler;
	}
	
	public ScenarioContext getScenarioContext() {
		return scenarioContext;
	}
}