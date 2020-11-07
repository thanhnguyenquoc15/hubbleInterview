package com.frame;

import java.util.Hashtable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.lib.BrowserFactory;

public class GlobalFunc {

	// contrustor
//	public MainPage(WebDriver dr) {
//		this.driver = BrowserFactory.driver();
//	}

	private WebDriver driver = BrowserFactory.driver();

	CommonFunction funcObj = new CommonFunction();

	// Define logger
	protected Logger log = Logger.getLogger(this.getClass().getName());

	// Page Objects
	@FindBy(id = "search_query_top")
	public WebElement searchTextBox;
	
	@FindBy(xpath = "//button[@name='submit_search']")
	public WebElement submitSearch;
	
	@FindBy(id = "uniform-selectProductSort")
	public WebElement sortDropBox;
	
	@FindBy(id = "grid")
	public WebElement gridView;
	
	@FindBy(id = "list")
	public WebElement listView;
	
	@FindBy(xpath = "//span[@class='heading-counter']")
	public WebElement searchResultText;
	
	@FindBy(xpath = "//span[@class='lighter']")
	public WebElement searchValueText;
	
	// Page Methods
	
	public void searchItem(String searchValue,
							String sortBy,
							String view) {
		log.debug("Entering into Method : " + Thread.currentThread().getStackTrace()[1].getMethodName());
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		log.info("Search items contains: "+ searchValue);
		this.searchTextBox.sendKeys(searchValue);
		
		log.info("Click Search Button");
		this.submitSearch.click();
		
		log.info("Sort Items By: " + sortBy);
		funcObj.selectDropDownOption(driver, sortDropBox, sortBy);
		
		view.toLowerCase().trim();
		switch (view) {
		case "grid":
			this.gridView.click();
			break;
		case "list":
			this.listView.click();
			break;
		default:
			//keep default Grid view	
		}
		String searchReturnMess = "Search with: " +  searchValueText.getText().toString()
							 + "return message: " +  searchResultText.getText().toString();
		Assert.assertTrue(searchValue.equals(searchValueText.getText().toString().toLowerCase().trim()));
		log.info(searchReturnMess);
		
		
		WebElement productList = driver.findElement(By.xpath("//ul[contains(@class,'product_list')]"));
		List<WebElement> products = productList.findElements(By.xpath("//*[contains(@class,'product-name')]"));
		
		log.info("These products are: ");
		for (WebElement product:products) {
			log.info(product.getText().toString());
			
		}	
	}
	

	
	
	
}