package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConfirmationPage {
	
	@FindBy(css ="span[class='base']")
	WebElement success_msg_link;
	
	@FindBy(css ="a.order-number strong")
	WebElement order_number;
	
	@FindBy(css ="button[class='action switch']")
	WebElement profile_dropdown;
	
	@FindBy(partialLinkText = "My Account")
	WebElement my_account;
	
	public ConfirmationPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	 public String expected_message() {
		 //set the expected message
		 String expectedText = "Thank you for your purchase!";
		 return expectedText; 
	 }
	 
	 public void waitForTextToBeVisible(WebDriverWait wait) {
		 //wait for success message to be visible
	        wait.until(ExpectedConditions.visibilityOf(success_msg_link));
	    }
	 public String get_order_number() {
		 //Store order number from confirmation page in object
		 String order_placed=order_number.getText();
		 return order_placed;	 
	 }
	 
	 public void go_to_home_screen() {
		 // click in profile dropdown and my account to go to home page
		 profile_dropdown.click();
		 my_account.click();
	 }

}

