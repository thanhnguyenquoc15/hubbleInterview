package com.scripts;

import java.io.IOException;
import java.sql.Driver;
import java.text.ParseException;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.frame.CheckOut;
import com.frame.CommonFunction;
import com.frame.GlobalFunc;
import com.lib.ReadData;

public class DemoScripts extends HelperClass {

	protected CheckOut CheckOutObj;
	protected GlobalFunc GlobalFuncObj;

	protected Logger log = Logger.getLogger(this.getClass().getName());

	protected CommonFunction funcObj = new CommonFunction();

	@Override
	public void performBeforeMethodOperation() {
		// TODO Auto-generated method stub
		CheckOutObj = PageFactory.initElements(BrowserFactory.driver(), CheckOut.class);
		GlobalFuncObj = PageFactory.initElements(BrowserFactory.driver(), GlobalFunc.class);
		
		log.info("-----");
	}

	@Override
	public void performAfterMethodOperation() {
		// TODO Auto-generated method stub
		log.info("-----------");

	}

	@Test(dataProvider = "dataMap", dataProviderClass = ReadData.class)
	public void searchAndBuy(Hashtable testData) throws Exception, ParseException {
		log.info("Test Case:" + testData.get("Test Name").toString() + "=> Start");
		log.info("-----------------------------------------------------------");
		log.info(testData);
		log.info("-----------------------------------------------------------");
		
		String productName = testData.get("Product Name").toString();
		String avail = testData.get("Availability").toString();
		try {
			GlobalFuncObj.searchItem(searchValue, sortBy, view);
			
			CheckOutObj.summaryCheckout(avail, unitPrice, qty, total, action);
			CheckOutObj.addressCheckout(deliveryDestination, useSameAddess, deliveryAddDetails, billingAddDetails,
					comment);
			CheckOutObj.shippingCheckout(shipOption, isAgreedToTerm, isReadTerm);
			CheckOutObj.paymentCheckout(productName, avail, unitPrice, quantity, total, shipPrice, payMethod);
		} finally {
			//clean data
		}
	}
	


}