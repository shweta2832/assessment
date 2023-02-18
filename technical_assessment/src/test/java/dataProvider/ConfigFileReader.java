package dataProvider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {
	
	Properties properties;
	String propertyFilePath= "configs//Configuration.properties";

	
	public ConfigFileReader(){
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
		String url = properties.getProperty("shopping_website_url");
		if(url != null) return url;
		else throw new RuntimeException("url not specified in the Configuration.properties file.");
	}
	
	public String getUserEmailAddress() {
		String url = properties.getProperty("user_Email_address");
		if(url != null) return url;
		else throw new RuntimeException("user Email address not specified in the Configuration.properties file.");
	}
	
	public String getUserPassword() {
		String url = properties.getProperty("user_password");
		if(url != null) return url;
		else throw new RuntimeException("user password not specified in the Configuration.properties file.");
	}
	
	public String getHomePageTtile() {
		String url = properties.getProperty("home_page_title");
		if(url != null) return url;
		else throw new RuntimeException("home page title not specified in the Configuration.properties file.");
	}
}