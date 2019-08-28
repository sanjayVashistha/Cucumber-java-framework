package dataReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	
	private Properties properties;
	private final String propertyFilePath= "configs/Configuation.properties";
	private static ConfigReader reader;
	
	private ConfigReader(){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}		
	}
	
	public static ConfigReader getInstance(){
		if(reader==null)
			reader = new ConfigReader();
		return reader;
	}
	public String getDriverPath(){
		String driverPath = properties.getProperty("driverPath");
		if(driverPath!= null) return driverPath;
		else throw new RuntimeException("driverPath not specified in the Configuration.properties file.");		
	}
	
	public long getImplicitlyWait() {		
		String implicitlyWait = properties.getProperty("implicitWait");
		if(implicitlyWait != null) return Long.parseLong(implicitlyWait);
		else throw new RuntimeException("implicitlyWait not specified in the Configuration.properties file.");		
	}
	
	public String getApplicationUrl() {
		String url = properties.getProperty("applicationURL");
		if(url != null) return url;
		else throw new RuntimeException("url not specified in the Configuration.properties file.");
	}
	
	public String getBrowserName(){
		String browserName = properties.getProperty("browser");
		return browserName;
	}

	public Boolean getWindowMaximize(){
		String windowMaximize = properties.getProperty("windowMaximize");
		if(windowMaximize!=null) return Boolean.valueOf(windowMaximize);
		else throw new RuntimeException("windowMaximize property not specified in the Configuration.properties file.");
	}
	
	public Boolean getRunOnSauceLab(){
		String runOnSauceLab = properties.getProperty("runOnSauceLab");
		if(runOnSauceLab!=null) return Boolean.valueOf(runOnSauceLab);
		else throw new RuntimeException("runOnSauceLab property not specified in the Configuration.properties file.");
	}
}
