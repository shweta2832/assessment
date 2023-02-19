package pages;

import java.util.LinkedList;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {
	
	
	By product_name_links=By.className("product-item-link");
	
	By product_name = By.className("base");
	
	By product_price = By.className("price");
	
	By product_size = By.cssSelector("div[class='swatch-option text']");
	
	By product_color=By.cssSelector("div[class='swatch-option color']");
	
	By add_to_cart_btn=By.cssSelector("button[title='Add to Cart']");
	
	By success_text=By.cssSelector("div[role='alert']");
	
	By shopping_cart_symbol=By.cssSelector("a[class='action showcart']");
	
	By view_cart=By.cssSelector("a[class='action viewcart']");
	
	WebDriver driver;
	
	public ProductPage(WebDriver driver) {
		this.driver=driver;
	}
	
	public List<String> get_individual_product_names_in_list() {
		// Get list of product names present on Product page
		List<String> all_product_list=new LinkedList<>();
		List<WebElement> product_name_links_list=driver.findElements(product_name_links);
        for (WebElement product_name : product_name_links_list) {
            all_product_list.add(product_name.getText());
        }
        return all_product_list;
	}
	
	public void click_product_name(String product_name) {
		//click specific product to order
		WebElement product_link=driver.findElement(By.partialLinkText(product_name));
    	product_link.click();
	}
	public void wait_for_page_to_load() {
		//wait for page to load
    	WebDriverWait wait = new WebDriverWait(driver, 30); 
        wait.until(ExpectedConditions.titleContains("website to practice selenium"));
    }
	
	public String get_product_name() {
		//get product name of product
		WebDriverWait wait = new WebDriverWait(driver, 10); 
    	wait.until(ExpectedConditions.presenceOfElementLocated(product_name));
    	String name="";
    	while(name.isEmpty()) {
    		name=driver.findElement(product_name).getText();
    	}
		return name;
	}
	
	public String get_product_price() {
		// get price of product
		WebDriverWait wait = new WebDriverWait(driver, 10); 
    	wait.until(ExpectedConditions.presenceOfElementLocated(product_price));
    	String price="";
    	while(price.isEmpty()) {
    		price=driver.findElement(product_price).getText();
    	}
		return price;
	}
	
	public void click_product_size() {
		// select the first available size present on product detail
		//wait for product size to be clickable
		FluentWait<WebDriver> wait1 = new FluentWait<WebDriver>(driver)
			    .withTimeout(30,TimeUnit.SECONDS)
			    .pollingEvery(5,TimeUnit.SECONDS)
			    .ignoring(NoSuchElementException.class);
		wait1.until(ExpectedConditions.elementToBeClickable(product_size));
		driver.findElement(product_size).click();
	}
	
	public void click_product_color() {
		//// select the first available color present on product detail
		//wait for product size to be clickable
		FluentWait<WebDriver> wait1 = new FluentWait<WebDriver>(driver)
			    .withTimeout(30,TimeUnit.SECONDS)
			    .pollingEvery(5,TimeUnit.SECONDS)
			    .ignoring(NoSuchElementException.class);
		wait1.until(ExpectedConditions.elementToBeClickable(product_color));
		driver.findElement(product_color).click();
	}
	
	public void click_add_to_cart() {
		//click on add to cart to add the product in shopping cart
		//wait for product size to be clickable
		FluentWait<WebDriver> wait1 = new FluentWait<WebDriver>(driver)
			    .withTimeout(30,TimeUnit.SECONDS)
			    .pollingEvery(5,TimeUnit.SECONDS)
			    .ignoring(NoSuchElementException.class);
		wait1.until(ExpectedConditions.elementToBeClickable(add_to_cart_btn));
		driver.findElement(add_to_cart_btn).click();
	}
	
	public void wait_for_success_message() {
		//wait for message that you added the product to shopping cart
		WebDriverWait wait = new WebDriverWait(driver, 10); 
    	wait.until(ExpectedConditions.presenceOfElementLocated(success_text));
	}
	
	public void scroll_till_top_of_page() {
		//to scrill till top of page
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 0)");
	}

	public void wait_for_shopping_cart_to_be_clickable() {
		//wait for shopping cart to be clickable
		FluentWait<WebDriver> wait1 = new FluentWait<WebDriver>(driver)
			    .withTimeout(30,TimeUnit.SECONDS)
			    .pollingEvery(5,TimeUnit.SECONDS)
			    .ignoring(NoSuchElementException.class);
		wait1.until(ExpectedConditions.elementToBeClickable(shopping_cart_symbol));
		driver.findElement(shopping_cart_symbol).click();
		
	}
	
	public void view_shopping_cart() {
		//click on view and edit cart to go to cart page
		driver.findElement(view_cart).click();
	}
	
}