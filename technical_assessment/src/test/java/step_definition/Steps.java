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
	public void addScreenshot(Scenario scenario) {
		final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		scenario.attach(screenshot, "image/png", "image");
	}

	@Before
	public void setup() {
		configFileReader = new ConfigFileReader(); // config file to read test data values
		System.setProperty("webdriver.chrome.driver", configFileReader.getDriverPath());
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(configFileReader.getImplicitlyWait(), TimeUnit.SECONDS);
	}

	@After
	public void close_browser() {
		driver.close();
	}

	@Given("^Registered customer navigates to shopping website$")
	public void registered_customer_navigates_to_shopping_website() {
		try {
			driver.get(configFileReader.getApplicationUrl());
			websiteDefaultPage = new WebsiteDefaultPage(driver);
			websiteDefaultPage.click_on_sign_in(); // registered user need to click on signin link
			String email_address = configFileReader.getUserEmailAddress(); // read email address from configuration file
			String password = configFileReader.getUserPassword(); // read password from configuration file
			loginPage = new LoginPage(driver);
			loginPage.login_to_website(email_address, password);
			WebDriverWait wait = new WebDriverWait(driver, 30); // wait for up to 30 seconds for page to load
			wait.until(ExpectedConditions.titleContains(configFileReader.getHomePageTtile()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Then("^Customer adds products to shopping cart$")
	public void customer_adds_products_to_shopping_cart(DataTable tables) {
		product_price_list = new LinkedList<>(); // dynamic list to add product price which are selected from shopping
													// website
		product_name_list = new LinkedList<>(); // dynamic list to add product names which are selected from shopping
												// website
		homePage = new HomePage(driver);
		productPage = new ProductPage(driver);
		// loop through products which are provided in feature file as data tables
		List<Map<String, String>> data = tables.asMaps();
		for (Map<String, String> row : data) {
			try {
				String feature_product_quantity = row.get("quantity");
				homePage.build_main_menu_actions(row);
				homePage.wait_for_page_to_load();
				// list to store product names which are anchor tags to view product details
				List<String> all_product_list = productPage.get_individual_product_names_in_list();
				String url = driver.getCurrentUrl();
				// select the items for the number provided in feature file
				for (int i = 0; i < Integer.parseInt(feature_product_quantity); i++) {
					try {
						String product_selected = all_product_list.get(i);
						productPage.events_to_add_product_to_shopping_cart(product_selected, product_name_list,
								product_price_list);
					} catch (StaleElementReferenceException | NoSuchElementException e) {
						// If StaleElementReferenceException or NoSuchElementException is thrown,
						// refresh the page and try again
						driver.navigate().refresh();
						driver.navigate().to(url);
						productPage.wait_for_page_to_load();
						String product_selected = all_product_list.get(i);
						productPage.events_to_add_product_to_shopping_cart(product_selected, product_name_list,
								product_price_list);
					}
				}
			} catch (StaleElementReferenceException | NoSuchElementException e) {
				driver.navigate().refresh();
				homePage.build_main_menu_actions(row);
				homePage.wait_for_page_to_load();

			}
		}
	}

	@Then("^Customer successfully places the order by making payment$")
	public void customer_successfully_places_the_order_by_making() {
		try {
			customer_verfies_order_summary();
			user_clicks_on_proceed_to_checkout_button();
			user_provides_delivery_address_details();
			user_selects_shipping_method_for_delivery();
			user_clicks_on_place_order_button();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@And("^Customer gets Success message with order number on screen$")
	public void customer_gets_success_message_with_order_number_on_screen() throws InterruptedException {
		try {
			get_order_number_from_screen();
			user_verify_the_submitted_order_under_my_orders_section();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void customer_verfies_order_summary() {
		try {
			productPage.scroll_till_top_of_page();
			// wait for page to load and click shopping cart
			productPage.wait_for_shopping_cart_to_be_clickable();
			// click view and edit cart
			productPage.view_shopping_cart();
			cartPage = new CartPage(driver);
			// Store the product names and list visible at cart page in list
			List<String> cart_product_name_list = cartPage.get_cart_product_names();
			List<String> cart_product_price_list = cartPage.get_cart_product_price();
			// compare the list with previously created list having product details from
			// shopping website
			assert product_name_list.equals(cart_product_name_list);
			assert product_price_list.equals(cart_product_price_list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void user_clicks_on_proceed_to_checkout_button() throws InterruptedException {
		// wait for Proceed to check button to be completely visible
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			cartPage.wait_for_elements_to_be_visible(wait);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			cartPage.scroll_to_checkout_and_click(js, wait);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void user_provides_delivery_address_details() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		shippingAddressPage = new ShippingAddressPage(driver);

		try {
			shippingAddressPage.add_new_address(wait);
		} catch (StaleElementReferenceException | ElementClickInterceptedException e) {
			// If StaleElementReferenceException or ElementClickInterceptedException is
			// thrown, refresh the page and try again
			driver.navigate().refresh();
			shippingAddressPage = new ShippingAddressPage(driver);
			shippingAddressPage.add_new_address(wait);
		}

		try {
			String txt_first_name = configFileReader.getFirstName();
			String txt_last_name = configFileReader.getLastName();
			String company_text = configFileReader.getCompany();
			String text_street0 = configFileReader.getStreetAddress1();
			String text_street1 = configFileReader.getStreetAddress2();
			String text_street2 = configFileReader.getStreetAddress3();
			String text_city = configFileReader.getCity();
			String text_state = configFileReader.getState();
			String text_zip = configFileReader.getZip();
			String country_text = configFileReader.getCountry();
			String text_phone = configFileReader.getPhone();
			String text_save_address = configFileReader.getsaveAddress();
			shippingAddressPage.add_address_details(txt_first_name, txt_last_name, company_text, text_street0,
					text_street1, text_street2, text_city, text_state, text_zip, country_text, text_phone,
					text_save_address, wait);
		} catch (StaleElementReferenceException | ElementClickInterceptedException e) {
			// If StaleElementReferenceException or ElementClickInterceptedException is
			// thrown, refresh the page and try again
			driver.navigate().refresh();
			shippingAddressPage = new ShippingAddressPage(driver);
			shippingAddressPage.add_new_address(wait);
			String txt_first_name = configFileReader.getFirstName();
			String txt_last_name = configFileReader.getLastName();
			String company_text = configFileReader.getCompany();
			String text_street0 = configFileReader.getStreetAddress1();
			String text_street1 = configFileReader.getStreetAddress2();
			String text_street2 = configFileReader.getStreetAddress3();
			String text_city = configFileReader.getCity();
			String text_state = configFileReader.getState();
			String text_zip = configFileReader.getZip();
			String country_text = configFileReader.getCountry();
			String text_phone = configFileReader.getPhone();
			String text_save_address = configFileReader.getsaveAddress();
			shippingAddressPage.add_address_details(txt_first_name, txt_last_name, company_text, text_street0,
					text_street1, text_street2, text_city, text_state, text_zip, country_text, text_phone,
					text_save_address, wait);
		}
	}

	public void user_selects_shipping_method_for_delivery() {
		// click delivery method
		try {
			String delivery_method = configFileReader.getDeliveryMethod();
			shippingAddressPage.click_deliver_method(delivery_method);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void user_clicks_on_place_order_button() throws InterruptedException {
		try {
			paymentPage = new PaymentPage(driver);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			paymentPage.place_order(wait);
		} catch (StaleElementReferenceException | ElementClickInterceptedException e) {
			// If StaleElementReferenceException or ElementClickInterceptedException is
			// thrown,
			// refresh the page and try again
			driver.navigate().refresh();
			paymentPage = new PaymentPage(driver);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			paymentPage.place_order(wait);
		}

	}

	public void get_order_number_from_screen() {
		try {
			confirmationPage = new ConfirmationPage(driver);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			confirmationPage.waitForTextToBeVisible(wait);
		} catch (StaleElementReferenceException | ElementClickInterceptedException | AssertionError e) {
			driver.navigate().refresh();
			WebDriverWait wait = new WebDriverWait(driver, 30);
			confirmationPage.waitForTextToBeVisible(wait);

		}

	}

	public void user_verify_the_submitted_order_under_my_orders_section() {
		try {
			String order_placed = confirmationPage.get_order_number();
			confirmationPage.go_to_home_screen();
			homePage.wait_for_page_to_load();
			homePage.go_to_my_orders_section();
			homePage.verify_order_number_in_orders(order_placed);
		} catch (StaleElementReferenceException | ElementClickInterceptedException e) {
			// TODO Auto-generated catch block
			String order_placed = confirmationPage.get_order_number();
			confirmationPage.go_to_home_screen();
			homePage.wait_for_page_to_load();
			homePage.go_to_my_orders_section();
			homePage.verify_order_number_in_orders(order_placed);
		}

	}
}
