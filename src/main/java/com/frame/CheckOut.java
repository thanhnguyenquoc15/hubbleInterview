/**
 * 
 */
package com.frame;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.lib.BrowserFactory;

/**
 * @author gamer
 *
 */
public class CheckOut {

	/**
	 * 
	 */
	public CheckOut() {
		// TODO Auto-generated constructor stub
	}

	private WebDriver driver = BrowserFactory.driver();

	CommonFunction funcObj = new CommonFunction();

	// Define logger
	protected Logger log = Logger.getLogger(this.getClass().getName());

	// Page Objects
	@FindBy(id = "id_address_delivery")
	public WebElement deliveryAddressDropDownBox;
	
	@FindBy(xpath = "//*[@class='cart_navigation clearfix']"
			      + "//*[contains(text(),'Proceed to checkout')]")
	public WebElement proceedToCheckout;
	
	@FindBy(xpath = "//*[@class='cart_navigation clearfix']"
		      + "//*[contains(text(),'Continue shopping')]")
public WebElement continueShopping;
	
	@FindBy(xpath = "//*[contains(text(),'Pay by bank wire')]")
	public WebElement payBankWire;
	
	@FindBy(xpath = "//*[contains(text(),'Pay by check')]")
	public WebElement payCheck;
	
	@FindBy(xpath = "//*[contains(text(),'I confirm my order')]")
	public WebElement confirmOrder;	
	
	// Page Methods
		/**
	 * description:
	 * 
	 * @param
	 */
	public void summaryCheckout(String avail,
								String unitPrice,
								String qty,
								String total,
								String action) {
		log.debug("Entering into Method : " + Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		//modify quantities
		
		//verify data
		
		//delete items
		
		//check availability
		
		log.info("Navigate to Sign in step");
		this.proceedToCheckout.click();
		
	}
	
	public void addressCheckout (String deliveryDestination,
								 Boolean useSameAddess,
								 String deliveryAddDetails,
								 String billingAddDetails,
								 String comment) {
		log.debug("Entering into Method : " + Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		//choose a delivery address
		funcObj.selectDropDownOption(driver, deliveryAddressDropDownBox, deliveryDestination);
		
		//use same address
		if(useSameAddess) {
			try {
				//if checked then proceed to next step
				driver.findElement(By.xpath("//*[@id='uniform-addressesAreEquals']//*[@class='checked']"));
			} catch (Exception e) {
				//if not checked => click
				driver.findElement(By.xpath("//*[@id='uniform-addressesAreEquals']//span")).click();;
			}
		} else if(!useSameAddess) {
			try {
				//if checked then  => click
				driver.findElement(By.xpath("//*[@id='uniform-addressesAreEquals']//*[@class='checked']"));
				driver.findElement(By.xpath("//*[@id='uniform-addressesAreEquals']//span")).click();
			} catch (Exception e) {
				//if not checked proceed to next step
			}
		}
		//update or verify delivery address
		WebElement deliAddressTable = driver.findElement(By.xpath("//h3[contains(text(),'delivery')]"));
		String updateBtn = ".//span[contains(text(),'Update')]";
		deliAddressTable.findElement(By.xpath(updateBtn)).click();;
		
		
		
		//update or verify billing address
		WebElement billAddressTable = driver.findElement(By.xpath("//h3[contains(text(),'billing')]"));
		billAddressTable.findElement(By.xpath(updateBtn)).click();
		
		//add new address
		driver.findElement(By.xpath("//*[@class='address_add submit']"
				                  + "//*[contains(text(),'Add a new address')]")).click();;
		
		//add comment
		driver.findElement(By.xpath("//textarea")).sendKeys(comment);
		
		log.info("Navigate to Shipping step");
		this.proceedToCheckout.click();
	}
	
	public String shippingCheckout (String shipOption,
									Boolean isAgreedToTerm,
									Boolean isReadTerm) {
		log.debug("Entering into Method : " + Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		//Chosse a shipping option and verify price
		
		
		//Agree to Terms of service
		if (!isAgreedToTerm) {
			this.proceedToCheckout.click();
			//verify error mess
			String error = driver.findElement(By.xpath("//*[@class='fancybox-error']")).getText().trim();
			Assert.assertEquals(error, "You must agree to the terms of service before continuing.");
			driver.findElement(By.xpath("//*[@title='Close']")).click();
		}
		driver.findElement(By.xpath("//*[@id='uniform-cgv']//span")).click();
		
		
		//Read the Terms of Service
		if (isReadTerm) {
			driver.findElement(By.linkText("(Read the Terms of Service)")).click();
			//verify terms then close
			driver.findElement(By.xpath("//*[@title='Close']")).click();
			
			
		}
		String shipPrice = driver.findElement(By.xpath("//div[@class='delivery_option_price']")).getText();
		log.info("Navigate to Payment step");
		this.proceedToCheckout.click();
		return shipPrice;
	}
	
	public void paymentCheckout (String productName,
			Boolean avail,
			String unitPrice,
			String quantity,
			String total,
			String shipPrice,
			String payMethod) {
		log.debug("Entering into Method : " + Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		//verify productName 
		List<WebElement> products = driver.findElements(By.xpath("//*[@class='cart_product']/..//*[@class='product-name']"));
		for (WebElement product:products) {
			log.info(product.getText());
		}
		
		//verify avail
		List<WebElement> cartAvails = driver.findElements(By.xpath("//*[@class='cart_avail']/span"));
		for (WebElement product:cartAvails) {
			log.info(product.getText());
		}
		
		//verify Unit Price
		List<WebElement> unitsPrice = driver.findElements(By.xpath("//*[@class='cart_unit']/*[@class='price']"));
		for (WebElement product:unitsPrice) {
			log.info(product.getText());
		}
		
		//verify Quantity
		List<WebElement> quantities = driver.findElements(By.xpath("//*[@class='cart_quantity text-center']"));
		for (WebElement product:quantities) {
			log.info(product.getText());
		}
		
		//verify Total price for each products
		List<WebElement> totalsProductPrice = driver.findElements(By.xpath("//*[contains(@id,'total_product_price')]"));
		for (WebElement product:totalsProductPrice) {
			log.info(product.getText());
		}
		//verify shipPrice and Total Price
		driver.findElement(By.id("total_product")).getText();
		driver.findElement(By.id("total_shipping")).getText();
		driver.findElement(By.id("total_price")).getText();
		
		//payment
		payMethod.toLowerCase().trim();
		switch (payMethod) {
		case "pay by bank wire":
			this.payBankWire.click();
			//verify some data
			
			
			this.confirmOrder.click();
			break;
		case "pay by check":
			this.payCheck.click();
			//verify some data
			
			this.confirmOrder.click();
			
			break;
		default:
			//continue to shopping
			driver.findElement(By.xpath("//*[contains(@class,'button-exclusive')]")).click();
			
		}
		
	}
	
	
	
	
}

