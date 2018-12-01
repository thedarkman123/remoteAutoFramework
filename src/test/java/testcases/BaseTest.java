package testcases;

import java.net.MalformedURLException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import pageobject.GenericPageObject;
import utilities.PropertiesWrapper;
import utilities.RemoteWebDriverWrapper;
import utilities.WebDriverWrapper;

public class BaseTest {
	protected static RemoteWebDriverWrapper rdw;
	protected PropertiesWrapper or; //object repository
	
	@BeforeMethod
	@Parameters("browser")
	public void setup(String browser) {
		//a wrapper for properties, or stands for OBJECT REPOSITORY 
		or = new PropertiesWrapper("OR");
		//a wrapper for the webdriver
		rdw = new RemoteWebDriverWrapper();
		//here we can add the propery for the browser we initiate the test OR using annotation
		try {
			rdw.init(browser,"http://192.168.99.100:4444/wd/hub");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		GenericPageObject.setWebDriver(rdw);
		GenericPageObject.setProperties(or);//the only needed properties file
	}
	
	@AfterMethod
	public void teardown() {
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//do everything related to closing and removing stuff here
		rdw.quit(); //close the browser		
	}
}
