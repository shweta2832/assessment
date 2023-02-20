package dataProvider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

	Properties properties;
	String propertyFilePath = "configs//Configuration.properties";

	public ConfigFileReader() {
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

	public String getDriverPath() {
		String driverPath = properties.getProperty("driverPath");
		if (driverPath != null)
			return driverPath;
		else
			throw new RuntimeException("driverPath not specified in the Configuration.properties file.");
	}

	public long getImplicitlyWait() {
		String implicitlyWait = properties.getProperty("implicitWait");
		if (implicitlyWait != null)
			return Long.parseLong(implicitlyWait);
		else
			throw new RuntimeException("implicitlyWait not specified in the Configuration.properties file.");
	}

	public String getApplicationUrl() {
		String url = properties.getProperty("shopping_website_url");
		if (url != null)
			return url;
		else
			throw new RuntimeException("url not specified in the Configuration.properties file.");
	}

	public String getUserEmailAddress() {
		String url = properties.getProperty("user_Email_address");
		if (url != null)
			return url;
		else
			throw new RuntimeException("user Email address not specified in the Configuration.properties file.");
	}

	public String getUserPassword() {
		String url = properties.getProperty("user_password");
		if (url != null)
			return url;
		else
			throw new RuntimeException("user password not specified in the Configuration.properties file.");
	}

	public String getFirstName() {
		String url = properties.getProperty("FirstName");
		if (url != null)
			return url;
		else
			throw new RuntimeException("FirstName not specified in the Configuration.properties file.");
	}

	public String getLastName() {
		String url = properties.getProperty("LastName");
		if (url != null)
			return url;
		else
			throw new RuntimeException("LastName not specified in the Configuration.properties file.");
	}

	public String getCompany() {
		String url = properties.getProperty("Company");
		if (url != null)
			return url;
		else
			throw new RuntimeException("Company not specified in the Configuration.properties file.");
	}

	public String getStreetAddress1() {
		String url = properties.getProperty("StreetAddress1");
		if (url != null)
			return url;
		else
			throw new RuntimeException("StreetAddress1 not specified in the Configuration.properties file.");
	}

	public String getStreetAddress2() {
		String url = properties.getProperty("StreetAddress2");
		if (url != null)
			return url;
		else
			throw new RuntimeException("StreetAddress2 not specified in the Configuration.properties file.");
	}

	public String getStreetAddress3() {
		String url = properties.getProperty("StreetAddress3");
		if (url != null)
			return url;
		else
			throw new RuntimeException("StreetAddress3 not specified in the Configuration.properties file.");
	}

	public String getCity() {
		String url = properties.getProperty("City");
		if (url != null)
			return url;
		else
			throw new RuntimeException("City not specified in the Configuration.properties file.");
	}

	public String getState() {
		String url = properties.getProperty("State");
		if (url != null)
			return url;
		else
			throw new RuntimeException("State not specified in the Configuration.properties file.");
	}

	public String getZip() {
		String url = properties.getProperty("Zip");
		if (url != null)
			return url;
		else
			throw new RuntimeException("Zip not specified in the Configuration.properties file.");
	}

	public String getCountry() {
		String url = properties.getProperty("Country");
		if (url != null)
			return url;
		else
			throw new RuntimeException("Country not specified in the Configuration.properties file.");
	}

	public String getPhone() {
		String url = properties.getProperty("Phone");
		if (url != null)
			return url;
		else
			throw new RuntimeException("Phone title not specified in the Configuration.properties file.");
	}

	public String getsaveAddress() {
		String url = properties.getProperty("saveAddress");
		if (url != null)
			return url;
		else
			throw new RuntimeException("saveAddress not specified in the Configuration.properties file.");
	}

	public String getDeliveryMethod() {
		String url = properties.getProperty("DeliveryMethod");
		if (url != null)
			return url;
		else
			throw new RuntimeException("DeliveryMethod not specified in the Configuration.properties file.");
	}

	public String getHomePageTtile() {
		String url = properties.getProperty("home_page_title");
		if (url != null)
			return url;
		else
			throw new RuntimeException("home page title not specified in the Configuration.properties file.");
	}
}