package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

public class ShippingAddressPage {
	
	@FindBy(xpath="//div[text()='Shipping Address']")
	WebElement shipping_adddress_text;
	
	@FindBy(xpath="//div[@class='new-address-popup']//button")
	WebElement NewAddress;
	
	@FindBy(xpath="//input[@name='firstname']")
	WebElement firstname;
	
	@FindBy(xpath="//input[@name='lastname']")
	WebElement lastname;
	
	@FindBy(xpath="//input[@name='company']")
	WebElement company;
	
	@FindBy(xpath="//input[@name='street[0]']")
	WebElement street0;
	
	@FindBy(xpath="//input[@name='street[1]']")
	WebElement street1;
	
	
	@FindBy(xpath="//input[@name='street[2]']")
	WebElement street2;
	
	@FindBy(xpath="//input[@name='city']")
	WebElement city;
	
	@FindBy(xpath="//select[@name='region_id']")
	WebElement state;
	
	@FindBy(xpath="//select[@name='country_id']")
	WebElement country;
	
	@FindBy(xpath="//input[@name='postcode']")
	WebElement zip;
	
	@FindBy(xpath="//input[@name='telephone']")
	WebElement telephone;
	
	@FindBy(xpath="//input[@type='checkbox']")
	WebElement checkbox;
	
	@FindBy(xpath="//button[@class='action primary action-save-address']")
	WebElement ship_here;
	
	@FindBy(xpath="//td[@id='label_carrier_flatrate_flatrate']//parent::tr//td[1]")
	WebElement flat_rate;
	
	@FindBy(xpath="//td[@id='label_carrier_bestway_tablerate']//parent::tr//td[1]")
	WebElement Bestway;
	
	@FindBy(xpath="//button[@type='submit' and @class='button action continue primary']")
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
