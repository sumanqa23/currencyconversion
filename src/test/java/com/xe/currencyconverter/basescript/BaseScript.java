package com.xe.currencyconverter.basescript;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.xe.currencyconverter.utils.DateUtils;

public class BaseScript {

	public static WebDriver driver;

	public static WebDriver getDriver() {

		Properties prop = new Properties();

		try {
			FileInputStream fip = new FileInputStream("properties/application.properties");
			prop.load(fip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		if(prop.getProperty("browser").equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "servers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else if(prop.getProperty("browser").equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "servers\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		else {
			System.setProperty("webdriver.chrome.driver", "servers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		
		return driver;
	}
	
	public static ExtentReports extent;
	public static ExtentSparkReporter spark;
	public static ExtentTest test;

	@BeforeTest
	public void initiateExtentReports() {
		extent = new ExtentReports();

		spark = new ExtentSparkReporter("reports/report_"+DateUtils.returnDateInMS()+"/report.html");

		extent.attachReporter(spark);
	}
	
	@AfterMethod
	public void getResult(ITestResult result) throws IOException {

		if(result.getStatus() == ITestResult.FAILURE) {

			test.log(Status.FAIL, "Test Case Failed: "+result.getName());

			test.log(Status.FAIL, "Test Case Failed: "+result.getThrowable()).fail(MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(driver,"failed"), "Failed Test").build());
			
			driver.quit();
		}
		else if(result.getStatus() == ITestResult.SKIP) {
			
			test.log(Status.SKIP, "Test Case Skipped: "+result.getName());
			
			test.log(Status.SKIP, "Test Case Skipped: "+result.getThrowable()).skip(MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot(driver,"failed"), "Failed").build());
			
			driver.quit();
		}
	}
	
	@AfterTest
	public void flushReports() {
		extent.flush();
	}
	
	public static String captureScreenshot(WebDriver driver) {
		TakesScreenshot takesScreenshot = (TakesScreenshot)driver;
		String base64code = takesScreenshot.getScreenshotAs(OutputType.BASE64);
		return base64code;
	}
	
	public static String captureScreenshot(WebDriver driver, String fileName) {
		
		long time = System.currentTimeMillis();
		TakesScreenshot takesScreenshot = (TakesScreenshot)driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		File destFile = new File("reports/screenshots/"+fileName+"_"+time+".png");
		try {
			FileUtils.copyFile(sourceFile, destFile);
		}
		catch(IOException e) {
			System.out.println("Screenshot can't be captured.");
		}
		return destFile.getAbsolutePath();
	}
}