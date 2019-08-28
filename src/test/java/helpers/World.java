package helpers;

import managers.PageObjectHandler;
import managers.WebDriverHandler;

public class World {
	private WebDriverHandler webDriverHandler;
	private PageObjectHandler pageObjectHandler;
	
	public World(){
		webDriverHandler = new WebDriverHandler();
		pageObjectHandler = new PageObjectHandler(webDriverHandler.getDriver());
	}
	
	public WebDriverHandler getWebDriverHandler(){
		return webDriverHandler;
	}
	
	public PageObjectHandler getPageObjectHandler(){
		return pageObjectHandler;
	}
}