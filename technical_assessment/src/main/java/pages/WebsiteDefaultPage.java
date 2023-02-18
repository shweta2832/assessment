package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WebsiteDefaultPage {
	
	@FindBy(partialLinkText = "Sign In")
	WebElement signin_for_registered_user;
	
	public WebsiteDefaultPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void click_on_sign_in() {
		// click sign in button to sign in
		signin_for_registered_user.click();
	}

}
