package com.xe.currencyconverter.ccproject;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.xe.currencyconverter.basescript.BaseScript;
import com.xe.currencyconverter.pages.CurrencyConverterPage;
import com.xe.currencyconverter.utils.DateUtils;
import com.xe.currencyconverter.utils.ExcelUtils;

public class CurrencyConversions extends BaseScript{

	@Test
	public void convertCurrencies() throws InterruptedException, IOException {

		//Get the webdriver from base class
		WebDriver driver = getDriver();
		driver.manage().window().maximize();

		//Create a test name
		test = extent.createTest("Currency Conversion Test");

		//Create object for CurrencyConverterPage class
		CurrencyConverterPage currencyConverterPage = new CurrencyConverterPage(driver);
		
		//Read the currency data into a map
		Map<Integer, Map<String, String>> data = ExcelUtils.getData("./data/CurrencyConversion.xlsx", "Sheet1");

		//Open web page
		driver.get("https://www.xe.com/currencyconverter/");

		WebDriverWait d=new WebDriverWait(driver,20);
		d.until(ExpectedConditions.visibilityOf(currencyConverterPage.returnWrapper()));

		test.log(Status.PASS, "Currency Converter Page Opened with Wrapper").pass(MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(driver,"home_wrapper"), "Home Wrapper").build());

		//Close the wrapper
		int top = (int)Double.parseDouble((currencyConverterPage.returnWrapper().getCssValue("top")).replace("px", ""));
		int left = (int)Double.parseDouble((currencyConverterPage.returnWrapper().getCssValue("left")).replace("px", ""));
		int width = (int)Double.parseDouble((currencyConverterPage.returnWrapper().getCssValue("width")).replace("px", ""));

		int right = left + width;

		Actions actions = new Actions(driver);
		actions.moveByOffset(right, top). click(). build(). perform();

		//Wait until the wrapper is closed
		d.until(ExpectedConditions.invisibilityOf(currencyConverterPage.returnWrapper()));
		
		test.log(Status.PASS, "Currency Converter Page Opened with Wrapper Closed").pass(MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(driver,"home_wrapper_closed"), "Home Wrapper Closed").build());

		Assert.assertTrue(currencyConverterPage.returnHeader().isDisplayed());
		
		Assert.assertTrue(currencyConverterPage.returnAmountTextbox().isDisplayed());
		
		Assert.assertTrue(currencyConverterPage.returnFromCurrencyDropdown().isDisplayed());
		
		//Select from Currency
		currencyConverterPage.returnFromCurrencyDropdown().click();

		//Wait until from currency list is populated 
		d.until(ExpectedConditions.numberOfElementsToBeMoreThan(currencyConverterPage.returnByFromCurrency(), 0));

		//Select From Currency
		int noOfFromCurrencies = currencyConverterPage.returnFromCurrencyDropdownList().size();
		//System.out.println("noOfFromCountries "+noOfFromCountries);

		for(int i=1; i<=noOfFromCurrencies; i++) {
			String currency = currencyConverterPage.returnFromCurrencyDropdownListElement(i).getText();
			if(currency.contains(data.get(1).get("from_currency"))) {
				currencyConverterPage.returnFromCurrencyDropdownListElement(i).click();
				break;
			}
		}

		
		test.log(Status.PASS, "From Currency Selected").pass(MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(driver,"from_currency_"+DateUtils.returnDateInMS()), "From Currency").build());

		Assert.assertTrue(currencyConverterPage.returnToCurrencyDropdown().isDisplayed());
		
		//Select To Currency
		currencyConverterPage.returnToCurrencyDropdown().click();

		//Wait until To currency list is populated
		d.until(ExpectedConditions.numberOfElementsToBeMoreThan(currencyConverterPage.returnByToCurrency(), 0));
		
		int noOfTocurrencies = currencyConverterPage.returnToCurrencyDropdownList().size();

		for(int i=1; i<=noOfTocurrencies; i++) {
			String currency = currencyConverterPage.returnToCurrencyDropdownListElement(i).getText();
			if(currency.contains(data.get(1).get("to_currency"))) {
				currencyConverterPage.returnToCurrencyDropdownListElement(i).click();
				break;
			}
		}

		test.log(Status.PASS, "To Currency Selected").pass(MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(driver,"to_currency"), "To Currency"+DateUtils.returnDateInMS()).build());

		//loop through the number of amounts in the data
		for(int dataCount=1; dataCount<=data.size(); dataCount++) {

			//Delete the amount
			for(int trail=0; trail<10; trail++) {
				currencyConverterPage.returnAmountTextbox().sendKeys(Keys.BACK_SPACE);
				if(currencyConverterPage.returnAmountErrorMessage().size() > 0){
					break;
				}
			}

			//Enter amount in amount field
			currencyConverterPage.returnAmountTextbox().sendKeys(data.get(dataCount).get("amount"));
			currencyConverterPage.returnAmountLabel().click();
			
			test.log(Status.PASS, dataCount+": Amount Entered").pass(MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(driver,"amount_"+DateUtils.returnDateInMS()), "Amount").build());
			
			//Click on convert button only first time
			if(dataCount == 1) {
				currencyConverterPage.returnConvertButton().click();
			}
			
			String inputAmount = currencyConverterPage.returnAmountTextbox().getAttribute("value");
			
			d.until(ExpectedConditions.numberOfElementsToBeMoreThan(currencyConverterPage.returnByConvertedFromText(inputAmount, data.get(1).get("from_currency")), 0));
						
			String convertedFromText = currencyConverterPage.returnConvertedFromText().getText();
			String convertedToText = currencyConverterPage.returnConvertedToText().getText();
			
			test.log(Status.PASS, dataCount+": Converted Successfully - "+convertedFromText+convertedToText).pass(MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(driver,"amount_"+DateUtils.returnDateInMS()), convertedFromText+convertedToText).build());
			
		}
		driver.quit();
	}

}
