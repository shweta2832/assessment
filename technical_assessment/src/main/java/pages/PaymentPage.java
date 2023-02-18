package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

public class PaymentPage {
	
	@FindBy(xpath="//button[@type='submit' and @class='action primary checkout']")
	WebElement place_order;

	public PaymentPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void place_order(Wait wait) {
		//wait for Place order link to be visible
		wait.until(ExpectedConditions.visibilityOf(place_order));
		place_order.click();
	}
}

