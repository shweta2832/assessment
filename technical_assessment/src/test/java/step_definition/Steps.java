package step_definition;

import java.util.LinkedList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dataProvider.ConfigFileReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CartPage;
import pages.ConfirmationPage;
import pages.HomePage;
import pages.LoginPage;
import pages.PaymentPage;
import pages.ProductPage;
import pages.ShippingAddressPage;
import pages.WebsiteDefaultPage;

public class Steps {
	
	WebDriver driver;
	CartPage cartPage;
	ConfirmationPage confirmationPage;
	HomePage homePage;
	LoginPage loginPage;
	PaymentPage paymentPage;
	ShippingAddressPage shippingAddressPage;
	WebsiteDefaultPage websiteDefaultPage;
	ProductPage productPage;
	String home_page_url;
	List<String> product_price_list;
	List<String> product_name_list;
	ConfigFileReader configFileReader;
	
	@AfterStep
	public void addScreenshot(Scenario scenario){
	      final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	      scenario.attach(screenshot, "image/png", "image"); 	
	}
	
	@Before
	public void setup() {
		configFileReader= new ConfigFileReader(); //config file to read the hard coded values
		System.setProperty("webdriver.chrome.driver",configFileReader.getDriverPath());
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(configFileReader.getImplicitlyWait(), TimeUnit.SECONDS);
	}
	
	@After
	public void close_browser() {
		driver.close();
	}
	
	@Given("^User is on Shopping Website page$")
	public void user_is_on_shopping_website_page() {
		try {
			driver.get(configFileReader.getApplicationUrl());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@When("^Clicks on Signin Option to Login$")
	public void clicks_on_signin_option_to_login() {
		try {
		    websiteDefaultPage = new WebsiteDefaultPage(driver);
		    websiteDefaultPage.click_on_sign_in(); //registered user need to click on signin link
		} 
		catch (Exception e) {
		    e.printStackTrace();
		}
	}
	   	
	@And("^Provides the credentials emailaddress and password$")
	public void provide_the_credentials_emailaddress_and_password() {
		try {
		    String email_address = configFileReader.getUserEmailAddress(); //read email address from configuration file
		    String password = configFileReader.getUserPassword(); //read password from configuration file
		    loginPage = new LoginPage(driver);
		    loginPage.send_email(email_address);
		    loginPage.send_password(password);
		} 
		catch (Exception e) {
		    e.printStackTrace();
		}

	}

	@And("^Clicks Signin for logging into application$")
	public void clicks_signin_for_logging_into_application() {
		try {
			loginPage.click_submit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Then("^User gets login successfully$")
	public void user_gets_login_successfully(){
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30); // wait for up to 30 seconds for page to load
			wait.until(ExpectedConditions.titleContains(configFileReader.getHomePageTtile()));
		} catch (Exception e) {
			e.printStackTrace();
		}	
    }
	
	@When("^User provides product details add them to shopping cart$")
	public void user_provides_product_details_add_them_to_shopping_cart(DataTable tables) {
		product_price_list=new LinkedList<>();  //dynamic list to add product price which are selected from shopping website
		product_name_list=new LinkedList<>(); //dynamic list to add product names which are selected from shopping website
		homePage=new HomePage(driver);
		productPage=new ProductPage(driver);
		//loop through products which are provided in feature file as data tables
		List<Map<String, String>> data = tables.asMaps();
		for (Map<String, String> row : data){
	        try {
	        	String feature_product_menu=row.get("menu");
				String feature_product_category=row.get("category");
				String feature_product_subcategory=row.get("subcategory");
				String feature_product_quantity=row.get("quantity");
				homePage.build_main_menu_actions(feature_product_menu,feature_product_category,feature_product_subcategory);
				homePage.wait_for_page_to_load();
				//list to store product names which are anchor tags to view product details
		        List<String> all_product_list=productPage.get_individual_product_names_in_list();
		        String url=driver.getCurrentUrl();
		        //select the items for the number provided in feature file
	        for(int i=0; i < Integer.parseInt(feature_product_quantity); i++) {
	            try {
	                productPage.click_product_name(all_product_list.get(i));
	                productPage.wait_for_page_to_load();
	                String product_name = productPage.get_product_name();
	                product_name_list.add(product_name);
	                String price_value = productPage.get_product_price();
	                product_price_list.add(price_value);
	                productPage.click_product_size();
	                productPage.click_product_color();
	                productPage.click_add_to_cart();
	                productPage.wait_for_success_message();
	                driver.navigate().back();
	                productPage.wait_for_page_to_load();
	            } catch (StaleElementReferenceException | NoSuchElementException e) {
	                // If StaleElementReferenceException or NoSuchElementException is thrown, refresh the page and try again
	                driver.navigate().refresh();
	                driver.navigate().to(url);
	                productPage.wait_for_page_to_load();
	                productPage.click_product_name(all_product_list.get(i));
	                productPage.wait_for_page_to_load();
	                String product_name = productPage.get_product_name();
	                product_name_list.add(product_name);
	                String price_value = productPage.get_product_price();
	                product_price_list.add(price_value);
	                productPage.click_product_size();
	                productPage.click_product_color();
	                productPage.click_add_to_cart();
	                productPage.wait_for_success_message();
	                driver.navigate().back();
	                productPage.wait_for_page_to_load();
	            }
	        }
	        }
	        catch (Exception e) {
	        	String feature_product_menu=row.get("menu");
				String feature_product_category=row.get("category");
				String feature_product_subcategory=row.get("subcategory");
	        	homePage.build_main_menu_actions(feature_product_menu,feature_product_category,feature_product_subcategory);
				homePage.wait_for_page_to_load();
			}
		}
	}
	@And("^Go to Cart page to verify added Products and Price$")
	public void go_to_cart_page_to_verify_added_products_and_price() {
		try {
			productPage.scroll_till_top_of_page();
			//wait for page to load and click shopping cart
			productPage.wait_for_shopping_cart_to_be_clickable();
			//click view and edit cart
			productPage.view_shopping_cart();
			cartPage=new CartPage(driver);
			//Store the product names and list visible at cart page in list
			List<String> cart_product_name_list=cartPage.get_cart_product_names();
			List<String> cart_product_price_list=cartPage.get_cart_product_price();
			//compare the list with previously created list having product details from shopping website
			assert product_name_list.equals(cart_product_name_list);
			assert product_price_list.equals(cart_product_price_list);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}

	@Then("^User checkout the items$")
	public void user_checkout_the_items() throws InterruptedException  {
		//wait for Proceed to check button to be completely visible
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			cartPage.wait_for_elements_to_be_visible(wait);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			cartPage.scroll_to_checkout_and_click(js, wait);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@And("^Provide the Delivery Address details$")
	public void provide_the_delivery_address_details(DataTable tables) throws InterruptedException {  
		WebDriverWait wait = new WebDriverWait(driver, 30);
		shippingAddressPage = new ShippingAddressPage(driver);

		try {
		    shippingAddressPage.add_new_address(wait);
		} catch (StaleElementReferenceException | ElementClickInterceptedException e) {
		    // If StaleElementReferenceException or ElementClickInterceptedException is thrown, refresh the page and try again
		    driver.navigate().refresh();
		    shippingAddressPage = new ShippingAddressPage(driver);
		    shippingAddressPage.add_new_address(wait);
		}

		List<Map<String, String>> data = tables.asMaps();
		for (Map<String, String> row : data) {
		    try {
		        String txt_first_name = row.get("FirstName");
		        String txt_last_name = row.get("LastName");
		        String company_text = row.get("Company");
		        String text_street0 = row.get("StreetAddress1");
		        String text_street1 = row.get("StreetAddress2");
		        String text_street2 = row.get("StreetAddress3");
		        String text_city = row.get("City");
		        String text_state = row.get("State");
		        String text_zip = row.get("Zip");
		        String country_text = row.get("Country");
		        String text_phone = row.get("Phone");
		        String text_save_address = row.get("saveAddress");
		        shippingAddressPage.add_address_details(txt_first_name, txt_last_name, company_text, text_street0,
		                                                text_street1, text_street2, text_city, text_state, text_zip,
		                                                country_text, text_phone, text_save_address, wait);
		    } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
		        // If StaleElementReferenceException or ElementClickInterceptedException is thrown, refresh the page and try again
		        driver.navigate().refresh();
		        shippingAddressPage = new ShippingAddressPage(driver);
		        shippingAddressPage.add_new_address(wait);
		        String txt_first_name = row.get("FirstName");
		        String txt_last_name = row.get("LastName");
		        String company_text = row.get("Company");
		        String text_street0 = row.get("StreetAddress1");
		        String text_street1 = row.get("StreetAddress2");
		        String text_street2 = row.get("StreetAddress3");
		        String text_city = row.get("City");
		        String text_state = row.get("State");
		        String text_zip = row.get("Zip");
		        String country_text = row.get("Country");
		        String text_phone = row.get("Phone");
		        String text_save_address = row.get("saveAddress");
		        shippingAddressPage.add_address_details(txt_first_name, txt_last_name, company_text, text_street0,
		                                                text_street1, text_street2, text_city, text_state, text_zip,
		                                                country_text, text_phone, text_save_address, wait);
		    }
		}

	}
	
	@And("^Select Shipping method as \"([^\\\"]*)\" for Delivery$")
	public void select_shipping_method_for_delivery_(String delivery_method) {
		//click delivery method
	    try {
			shippingAddressPage.click_deliver_method(delivery_method);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Then("^User places the order$")
	public void user_places_the_order() throws InterruptedException {
		try {
		    paymentPage = new PaymentPage(driver);
		    WebDriverWait wait = new WebDriverWait(driver, 30);
		    paymentPage.place_order(wait);
		} catch (StaleElementReferenceException | ElementClickInterceptedException e) {
		    // If StaleElementReferenceException or ElementClickInterceptedException is thrown,
		    // refresh the page and try again
		    driver.navigate().refresh();
		    paymentPage = new PaymentPage(driver);
		    WebDriverWait wait = new WebDriverWait(driver, 30);
		    paymentPage.place_order(wait);
		}

	}
	@And("^Verify if order is successfully placed$")
	public void verify_if_order_is_successfully_placed() throws InterruptedException {		
		try {
			confirmationPage=new ConfirmationPage(driver);
			WebDriverWait wait = new WebDriverWait(driver, 30); 
		    confirmationPage.waitForTextToBeVisible(wait);
		} catch (StaleElementReferenceException | ElementClickInterceptedException | AssertionError e) {
			driver.navigate().refresh();
			WebDriverWait wait = new WebDriverWait(driver, 30);
			confirmationPage.waitForTextToBeVisible(wait);
		    
		}

	}
	
	@And("^Verify the Submitted order under My Orders section$")
	public void verify_the_submitted_order_under_my_orders_section() {  
        try {
			String order_placed= confirmationPage.get_order_number();
			confirmationPage.go_to_home_screen();
			homePage.wait_for_page_to_load();
			homePage.go_to_my_orders_section();
			homePage.verify_order_number_in_orders(order_placed);
		} catch (StaleElementReferenceException | ElementClickInterceptedException e) {
			// TODO Auto-generated catch block
			String order_placed= confirmationPage.get_order_number();
			confirmationPage.go_to_home_screen();
			homePage.wait_for_page_to_load();
			homePage.go_to_my_orders_section();
			homePage.verify_order_number_in_orders(order_placed);
		}
        
	}
}
