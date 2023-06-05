package com.xe.currencyconverter.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CurrencyConverterPage {

	WebDriver driver;
	public CurrencyConverterPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebElement returnWrapper() {
		WebElement element = driver.findElement(By.xpath("//div[contains(@id,'yie-inner-overlay')]"));
		return element;
	}
	
	public WebElement returnHeader() {
		WebElement element = driver.findElement(By.xpath("//h1[text()='Xe Currency Converter']"));
		return element;
	}
	
	public WebElement returnFromCurrencyDropdown() {
		WebElement element = driver.findElement(By.xpath("//input[@id='midmarketFromCurrency']"));
		return element;
	}
	
	public By returnByFromCurrency() {
		By byFromCurrency = By.xpath("//ul[@id='midmarketFromCurrency-listbox']/li");
		return byFromCurrency;
	}
	
	public List<WebElement> returnFromCurrencyDropdownList() {
		List<WebElement> elements = driver.findElements(By.xpath("//ul[@id='midmarketFromCurrency-listbox']/li"));
		return elements;
	}
	
	
	public WebElement returnFromCurrencyDropdownListElement(int elementNo) {
		WebElement element = driver.findElement(By.xpath("//ul[@id='midmarketFromCurrency-listbox']/li["+elementNo+"]"));
		return element;
	}
	
	public WebElement returnToCurrencyDropdown() {
		WebElement element = driver.findElement(By.xpath("//input[@id='midmarketToCurrency']"));
		return element;
	}
	
	public By returnByToCurrency() {
		By byToCurrency = By.xpath("//ul[@id='midmarketToCurrency-listbox']/li");
		return byToCurrency;
	}
	
	public List<WebElement> returnToCurrencyDropdownList() {
		List<WebElement> elements = driver.findElements(By.xpath("//ul[@id='midmarketToCurrency-listbox']/li"));
		return elements;
	}
	
	
	public WebElement returnToCurrencyDropdownListElement(int elementNo) {
		WebElement element = driver.findElement(By.xpath("//ul[@id='midmarketToCurrency-listbox']/li["+elementNo+"]"));
		return element;
	}
	
	public WebElement returnAmountTextbox() {
		WebElement element = driver.findElement(By.xpath("//input[@id='amount']"));
		return element;
	}
	
	public List<WebElement> returnAmountErrorMessage() {
		List<WebElement> elements = driver.findElements(By.xpath("//div[text()='Please enter a valid amount']"));
		return elements;
	}
	
	public WebElement returnAmountLabel() {
		WebElement element = driver.findElement(By.xpath("//label[@for='amount']"));
		return element;
	}
	
	public WebElement returnConvertButton() {
		WebElement element = driver.findElement(By.xpath("//button[text()='Convert']"));
		return element;
	}
	
	public WebElement returnConvertedFromText() {
		WebElement element = driver.findElement(By.xpath("(//div[contains(@class,'currency-converter')]/following-sibling::div//p)[1]"));
		return element;
	}
	
	public WebElement returnConvertedToText() {
		WebElement element = driver.findElement(By.xpath("(//div[contains(@class,'currency-converter')]/following-sibling::div//p)[2]"));
		return element;
	}
	
	public By returnByConvertedFromText(String amount, String currency) {
		By byToCurrency = By.xpath("//div[contains(@class,'currency-converter')]/following-sibling::div//p[1][contains(text(),'"+amount+" "+currency+"')]");
		return byToCurrency;
	}
}
