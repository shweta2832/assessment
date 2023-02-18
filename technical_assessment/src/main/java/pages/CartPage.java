package pages;
import java.util.LinkedList;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

public class CartPage {
	
	@FindBy(xpath="//table[@id='shopping-cart-table']//descendant::strong[@class='product-item-name']//a")
	List<WebElement> product_names_anchor_tag_list;
	
	@FindBy(xpath="//table[@id='shopping-cart-table']//descendant::td[@class='col price']//span[@class='price']")
	List<WebElement> product_price_anchor_tag_list;
	
	@FindBy(xpath="//*[contains(text(),'Estimate Shipping and Tax')]")
	WebElement shipping_tax;
	
	@FindBy(xpath="//th[contains(text(),'Subtotal')]")
	WebElement subtotal;
	
	@FindBy(xpath="//*[contains(text(),'(Flat Rate - Fixed)')]")
	WebElement flat_rate;
	
	@FindBy(xpath="//*[contains(text(),'Order Total')]")
	WebElement order_total;
	
	@FindBy(xpath="//button[@title='Proceed to Checkout']//span")
	WebElement checkout;
	
	public CartPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public List<String> get_cart_product_names(){
		//Add product names from cart to list
		List<String> cart_product_name_list=new LinkedList<>();
		for(WebElement product_name:product_names_anchor_tag_list) {
        	cart_product_name_list.add(product_name.getText());
        }
		return cart_product_name_list;
	}
	
	public List<String> get_cart_product_price(){
		//Add product price from cart to list
		List<String> cart_product_price_list=new LinkedList<>();
		for(WebElement price:product_price_anchor_tag_list) {
			cart_product_price_list.add(price.getText());
        }
		return cart_product_price_list;
	}
	
	public void wait_for_elements_to_be_visible(Wait wait) {
		//wait until elements are visible
		wait.until(ExpectedConditions.visibilityOf(shipping_tax));
		wait.until(ExpectedConditions.visibilityOf(subtotal));
		wait.until(ExpectedConditions.visibilityOf(flat_rate));
		wait.until(ExpectedConditions.visibilityOf(order_total));
	}
	
	public void scroll_to_checkout_and_click(JavascriptExecutor js,Wait wait){
		//scroll till element View and Edit cart and wait for it to be visible
		js.executeScript("arguments[0].scrollIntoView();", checkout);
		wait.until(ExpectedConditions.visibilityOf(checkout));
		checkout.click();
	}

}
