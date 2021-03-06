package utilities;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.google.common.io.Files;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import testcases.BaseTest;

public class TestUtils extends BaseTest {
	//extent report bit, used in the listeners
	private static ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static ThreadLocal<ExtentTest> exTest = new ThreadLocal<ExtentTest>();
	
	public void setExtentTest(ExtentTest et) {
		exTest.set(et);
	}
	
	public ExtentTest getExtentTest() {
		return exTest.get();
	}
	
	public ExtentReports getReportInstance() {
		return rep;
	}
	
	public static String captureScreenshot(String fileName,String directory) {
		String pathToFile = System.getProperty("user.dir") + "\\src\\test\\resources\\screenshots\\" + directory +"\\"+ getCurrentTime()+fileName + ".png";
		try {
			File scrFile = ((TakesScreenshot)getWrapperInstance().getWebDriver()).getScreenshotAs(OutputType.FILE);
			File newFileToCreate = new File(pathToFile);	
			newFileToCreate.getParentFile().mkdirs();
			Files.copy(scrFile, newFileToCreate);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pathToFile;
	}
	
	public static RemoteWebDriverWrapper getWrapperInstance() {
		return rdw;
	}
	
	public static String generateString(int length){
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rng = new Random();
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++){
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
	
	public static String getCurrentTime() {
		return new Date().toString().replace(":","_").replace(" ", "_");
	}
	
}
