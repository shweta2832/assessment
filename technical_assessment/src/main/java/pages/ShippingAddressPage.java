package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

public class ShippingAddressPage {
	
	@FindBy(css ="div[class='step-title']")
	WebElement shipping_adddress_text;
	
	@FindBy(css ="button[class='action action-show-popup']")
	WebElement NewAddress;
	
	@FindBy(name="firstname")
	WebElement firstname;
	
	@FindBy(name="lastname")
	WebElement lastname;
	
	@FindBy(name="company")
	WebElement company;
	
	@FindBy(name="street[0]")
	WebElement street0;
	
	@FindBy(name="street[1]")
	WebElement street1;
	
	@FindBy(name="street[2]")
	WebElement street2;
	
	@FindBy(name="city")
	WebElement city;
	
	@FindBy(name="region_id")
	WebElement state;
	
	@FindBy(name="country_id")
	WebElement country;
	
	@FindBy(name="postcode")
	WebElement zip;
	
	@FindBy(name="telephone")
	WebElement telephone;
	
	@FindBy(id="shipping-save-in-address-book")
	WebElement checkbox;
	
	@FindBy(css ="button[class='action primary action-save-address']")
	WebElement ship_here;
	
	@FindBy(name="ko_unique_2")
	WebElement flat_rate;
	
	@FindBy(name="ko_unique_3")
	WebElement Bestway;
	
	@FindBy(css ="button[class='button action continue primary']")
	WebElement confirm_Delivery;
	
	public ShippingAddressPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void add_new_address(Wait wait) {
		//wait for New Address button to be clickable
		wait.until(ExpectedConditions.visibilityOf(shipping_adddress_text));
		wait.until(ExpectedConditions.visibilityOf(NewAddress));
        NewAddress.click();
	}
	public void add_address_details(String txt_first_name,String txt_last_name,String company_text,
			String text_street0,String text_street1,String text_street2,String text_city,
			String text_state,String text_zip,String country_text ,String text_phone,String text_save_address,Wait wait) {
		//add the address details received from feature file
		firstname.clear();
		firstname.sendKeys(txt_first_name);
		lastname.clear();
		lastname.sendKeys(txt_last_name);
		company.sendKeys(company_text);
		street0.sendKeys(text_street0);
		street1.sendKeys(text_street1);
		street2.sendKeys(text_street2);
		city.sendKeys(text_city);
		Select select=new Select(state);
		select.selectByVisibleText(text_state);
		zip.sendKeys(text_zip);
		select = new Select(country);
		select.selectByVisibleText(country_text);
		telephone.sendKeys(text_phone);
		String check_text_save_address=text_save_address.toLowerCase();
		if(check_text_save_address.equals("false")) {
        	checkbox.click();
             }  
		wait.until(ExpectedConditions.visibilityOf(ship_here));
		ship_here.click();	
	}
	
	public void click_deliver_method(String delivery_method) {
		//select delivery method
		if (delivery_method.equals("Flat Rate")) {
			flat_rate.click();
		}
		else {
			Bestway.click();
		}
		confirm_Delivery.click();
	}
	

}
