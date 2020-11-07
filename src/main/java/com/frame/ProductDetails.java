package com.frame;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.lib.BrowserFactory;

public class ProductDetails {

	public ProductDetails() {
		// TODO Auto-generated constructor stub
	}

	
	private WebDriver driver = BrowserFactory.driver();

	CommonFunction funcObj = new CommonFunction();

	// Define logger
	protected Logger log = Logger.getLogger(this.getClass().getName());

	// Page Objects

	@FindBy(id = "quantity_wanted")
	public WebElement quantityBox;
	
	@FindBy(xpath = "//*[contains(@class,'button-plus')]")
	public WebElement quantityPlus;
	
	@FindBy(xpath = "//*[contains(@class,'button-minus')]")
	public WebElement quantityMinus;
	
	@FindBy(id = "group_1")
	public WebElement sizeDropDownBox;
	
	@FindBy(xpath = "//span[contains(text(),'Add to cart')]")
	public WebElement addToCart;
	
	@FindBy(xpath = "//*[contains(text(),'Add to wishlist')]")
	public WebElement addToWishList;
	
	@FindBy(xpath = "//span[contains(text(),'Proceed to checkout')]")
	public WebElement proceedToCheckout;
	
	@FindBy(xpath = "//*[@title='Continue shopping']")
	public WebElement continueShopping;
	
	
	// Page Methods
	/**
	 * description:
	 * 
	 * @toDo
	 * @needWorks
	 */
	public void selectProduct(String productName, 
								String productColor,
								String productQuantity,
								String productSize,
								String action) {
		log.debug("Entering into Method : " + Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		log.info("Choose color: " + productColor );
		driver.findElement(By.xpath("//a[@title='" + productColor + "']")).click();
		
		log.info("Choose Quantity: " + productQuantity );
		this.quantityBox.clear();
		this.quantityBox.sendKeys(productQuantity);
		
		log.info("Choose Size: " + productSize );
		funcObj.selectDropDownOption(driver, sizeDropDownBox, productSize);
		
		//after fill all the details choose what button to click
		action.toLowerCase().trim();
		switch (action) {
		case "add to cart":
			this.addToCart.click();
			this.popUpAddToCart("", "Proceed to checkout");
			break;
		case "add to wishlist":
			this.addToWishList.click();
			
			break;
		case "send to a friend":
			
			break;
		case "print":
			
			break;
		case "tweet":
			
			break;
		case "facebook":
			
			break;
		case "google+":
			
			break;
		case "write a review":
			
			break;
		default:
				
		}
	}
	
	/**
	 * description:
	 * 
	 * @toDo
	 * @needWorks
	 */
	private void popUpAddToCart(String verifyData, 
								String action) {
		log.debug("Entering into Method : " + Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		//codes to verify data in this popup here
		
		
		//click to change page
		switch (action) {
		case "Proceed to checkout":
			this.proceedToCheckout.click();
			break;
		case "Continue shopping":
			this.continueShopping.click();
			break;
		default:
			driver.findElement(By.xpath("//*[@title='Close window']")).click();
		}
		
	}
	
}
