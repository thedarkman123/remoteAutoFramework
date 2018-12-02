package testcases;

import java.net.MalformedURLException;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import pageobject.GenericPageObject;
import utilities.PropertiesWrapper;
import utilities.RemoteWebDriverWrapper;
import utilities.TestUtils;
import utilities.WebDriverWrapper;

public class BaseTest {
	protected static RemoteWebDriverWrapper rdw;
	protected PropertiesWrapper or; //object repository
	
	public static Logger log;
	public static ThreadLocal<Logger> loggerThread= 
			 new ThreadLocal<Logger>();
	
	public static ThreadLocal<FileAppender> appenderThread= 
			 new ThreadLocal<FileAppender>();	
	
	 public static void setAppender(FileAppender ap) {
		 appenderThread.set(ap);
	 }
	
	public static FileAppender getAppender() {
		return appenderThread.get();
	}
	
	 public static void setLogger(Logger lg) {
		 loggerThread.set(lg);
	 }
	
	public static Logger getLogger() {
		return loggerThread.get();
	}
	
	
	@BeforeMethod
	@Parameters("browser")
	public void setup(String browser) {
		String fileString = TestUtils.generateString(5);
		//initialize log
		FileAppender appender = new FileAppender();
        appender.setFile("logs/" + fileString +".log");
        appender.setLayout(new PatternLayout("%d [%t] %-5p %c - %m%n"));
        appender.activateOptions();
        setAppender(appender);
        // Get logger and add appender
        log = Logger.getLogger(fileString);
        log.setAdditivity(false);
        log.addAppender(getAppender());		
        setLogger(log);
        
        getLogger().info("testing");
		
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
