package pages;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage {

	WebDriver driver;
    public HomePage(WebDriver driver) {
        this.driver=driver;
    }
	
//    By my_orders=By.xpath("//a[text()='My Orders']");
//    By my_orders=By.cssSelector("a:contains('My Orders')");
    By my_orders=By.partialLinkText("My Orders");
    
    public void build_main_menu_actions(String feature_product_menu,
    		String feature_product_category,String feature_product_subcategory) {
    	//build series of actions to perform like menu and then category and finally subcategory
    	Wait wait = new WebDriverWait(driver, 10); 
    	
    	WebElement menu_dropdown=driver.findElement(By.partialLinkText(feature_product_menu));
    	wait.until(ExpectedConditions.visibilityOf(menu_dropdown));
        Actions actions=new Actions(driver);
        actions.moveToElement(menu_dropdown).build().perform();
        WebElement category_link=driver.findElement(By.partialLinkText(feature_product_category));
        wait.until(ExpectedConditions.visibilityOf(category_link));
        actions.moveToElement(category_link).build().perform();
        WebElement sub_category_dropdown=driver.findElement(By.partialLinkText(feature_product_subcategory));
        wait.until(ExpectedConditions.visibilityOf(sub_category_dropdown));
        actions.moveToElement(sub_category_dropdown).click().build().perform();
    }
    
    public void wait_for_page_to_load() {
    	//wait for page to get completely load
    	WebDriverWait wait = new WebDriverWait(driver, 30); 
        wait.until(ExpectedConditions.titleContains("website to practice selenium"));
    }
    public void go_to_my_orders_section() {
    	//click on my orders on home page
    	driver.findElement(my_orders).click();
    }
    
    public void verify_order_number_in_orders(String order_number) {
    	//method to verify order number from confirmation page is present in my orders section
    	String order_xpath="//table[@id='my-orders-table']//tbody/tr/td[text()='"+order_number+"']";
        WebElement order=driver.findElement(By.xpath(order_xpath));
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	// Highlight the element by changing its border and background color
    	js.executeScript("arguments[0].style.border='3px solid red'; arguments[0].style.backgroundColor='yellow';", order);
       
       String val=order.getText();
       assert order_number.equals(val);
	 }
}
