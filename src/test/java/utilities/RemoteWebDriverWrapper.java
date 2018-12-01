package utilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.relevantcodes.extentreports.LogStatus;
public class RemoteWebDriverWrapper {
	    //for thread context, seperate the instances of the program
		public static ThreadLocal<RemoteWebDriver> dr = new ThreadLocal<RemoteWebDriver>();	
		private RemoteWebDriver rwb = null;
		
		public void init(String browser,String remoteHubUrl) throws MalformedURLException {
			DesiredCapabilities cap = null;
			if (browser.equals("chrome")) {
				cap = DesiredCapabilities.chrome();
				cap.setBrowserName("chrome");
				cap.setPlatform(Platform.ANY);
			} else if (browser.equals("firefox")) {
				cap = DesiredCapabilities.firefox();
				cap.setBrowserName("firefox");
				cap.setPlatform(Platform.ANY);
			} else if (browser.equals("ie")) {
				cap = DesiredCapabilities.internetExplorer();
				cap.setBrowserName("ieplorer");
				cap.setPlatform(Platform.WINDOWS);
			}
			
			rwb = new RemoteWebDriver(new URL(remoteHubUrl),cap);
			setWebDriver(rwb); //the webdriver of the current instance/thread
			getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			getWebDriver().manage().window().maximize();
		}
		
		public void clickElement(WebElement wb) {
			wb.click();
		}
		
		public void typeInElement(WebElement wb,String strToType) {
			wb.sendKeys(strToType);
		}
		
		public static enum FINDTYPE{
			XPATH,ID,TAG
		}
		
		public static enum CONDITIONTYPE{
			VISIBLE,CLICKABLE,PRESENT
		}
		
		//explicit wait find
		public WebElement getElementByType(String value, FINDTYPE type, CONDITIONTYPE condition) {
			WebElement element = null;

			By by = null;

			if (type == FINDTYPE.XPATH) {
				by = By.xpath(value);
			} else if (type == FINDTYPE.ID) {
				by = By.id(value);
			} else if(type == FINDTYPE.TAG) {
				by = By.tagName(value);
			}
			

			try {
				WebDriverWait driverWait = new WebDriverWait(getWebDriver(), 10, 1000);
				if (condition == CONDITIONTYPE.PRESENT) {
					element = driverWait.until(ExpectedConditions.presenceOfElementLocated(by));	
				} else if (condition == CONDITIONTYPE.CLICKABLE) {
					element = driverWait.until(ExpectedConditions.elementToBeClickable(by));
				} else if (condition == CONDITIONTYPE.VISIBLE) {
					element = driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
				}

			} catch (Exception e) {
				System.out.println("some exception here finding element");
				e.printStackTrace();
			}

			if (element == null) {
				Assert.fail();
			}

			return element;
		}
		
		public WebElement getVisibleElementById(String id) {
			return getElementByType(id,FINDTYPE.ID,CONDITIONTYPE.VISIBLE);
		}
		
		public WebElement getVisibleElementByXpath(String xpath) {
			return getElementByType(xpath,FINDTYPE.XPATH,CONDITIONTYPE.VISIBLE);
		}
		
		public WebElement getVisibleElementByTagName(String tagName) {
			return getElementByType(tagName,FINDTYPE.TAG,CONDITIONTYPE.VISIBLE);
		}
		
		public WebElement getClickableElementById(String id) {
			return getElementByType(id,FINDTYPE.ID,CONDITIONTYPE.CLICKABLE);
		}
		
		public WebElement getClickableElementByXpath(String xpath) {
			return getElementByType(xpath,FINDTYPE.XPATH,CONDITIONTYPE.CLICKABLE);
		}
		
		public WebElement getClickableElementByTagName(String tagName) {
			return getElementByType(tagName,FINDTYPE.TAG,CONDITIONTYPE.CLICKABLE);
		}
		
		public WebElement getPresentElementById(String id) {
			return getElementByType(id,FINDTYPE.ID,CONDITIONTYPE.PRESENT);
		}
		
		public WebElement getPresentElementByXpath(String xpath) {
			return getElementByType(xpath,FINDTYPE.XPATH,CONDITIONTYPE.PRESENT);
		}
		
		public WebElement getPresentElementByTagName(String tagName) {
			return getElementByType(tagName,FINDTYPE.TAG,CONDITIONTYPE.PRESENT);
		}
		
		public void openUrl(String url) {
			getWebDriver().get(url);
		}
		
		public WebDriver getWebDriver() {
			return dr.get();
		}
		
		public void setWebDriver(RemoteWebDriver driver) {
			dr.set(driver);
		}
		
		public void quit() {
			getWebDriver().quit();
		}
}
