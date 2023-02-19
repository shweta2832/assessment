package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	@FindBy(id="email")
	WebElement email;
	
	@FindBy(id="pass")
	WebElement password;
	
	@FindBy(id="send2")
	WebElement submit;
	
	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);	
	}
	
	public void send_email(String email_address) {
		//provide email address
		email.sendKeys(email_address);
	}
	
	public void send_password(String valid_password) {
		//provide password
		password.sendKeys(valid_password);
	}
	
	public void click_submit() {
		//click on sign button to login
		submit.click();
	}
	

}
