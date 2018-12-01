package utilities;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.relevantcodes.extentreports.LogStatus;


public class WebDriverWrapper {
	
	private WebDriver wb = null;
	
	public WebDriver getWbInstance() {
		return this.wb;
	}
	
	public String getBrowserName() {
	    Capabilities cap = ((RemoteWebDriver)wb).getCapabilities();
	    return cap.getBrowserName().toLowerCase();
	}
	
	public String getPlatform() {
		Capabilities cap = ((RemoteWebDriver)wb).getCapabilities();
		return cap.getPlatform().toString();
	}
	
	public String getVersion() {
		Capabilities cap = ((RemoteWebDriver)wb).getCapabilities();
		return cap.getVersion().toString();
	}

	public void init(String browser) {
		if (browser.equals("chrome")) {
			Reporter.log("initiating chrome");
			System.setProperty("webdriver.chrome.driver", 
					System.getProperty("user.dir")+"\\src\\test\\resources\\excutables\\chromedriver.exe");
			wb = new ChromeDriver();
		} else if (browser.equals("firefox")) {
			Reporter.log("initiating firefox");
			System.setProperty("webdriver.gecko.driver", 
					System.getProperty("user.dir")+"\\src\\test\\resources\\excutables\\geckodriver.exe");
			wb = new FirefoxDriver(); 
		}
	}

	public void openUrl(String url) {
		Reporter.log("Opening url:" + url);
		wb.get(url);
	}
	
	public static enum FINDTYPE{
		XPATH,ID,TAG
	}
	
	public static enum CONDITIONTYPE{
		VISIBLE,CLICKABLE,PRESENT
	}

	//explicit wait find
	public WebElement getElementByType(String value, FINDTYPE type, CONDITIONTYPE condition) {
		String txtForLog = "";
		WebElement element = null;

		By by = null;

		if (type == FINDTYPE.XPATH) {
			txtForLog += "Find By Xpath: ";
			by = By.xpath(value);
		} else if (type == FINDTYPE.ID) {
			txtForLog += "Find By Id: ";
			by = By.id(value);
		} else if(type == FINDTYPE.TAG) {
			txtForLog += "Find By Tag: ";
			by = By.tagName(value);
		}
		
		txtForLog += value;

		try {
			WebDriverWait driverWait = new WebDriverWait(wb, 10, 1000);
			if (condition == CONDITIONTYPE.PRESENT) {
				txtForLog += " When present";
				element = driverWait.until(ExpectedConditions.presenceOfElementLocated(by));	
			} else if (condition == CONDITIONTYPE.CLICKABLE) {
				txtForLog += " When clickable";
				element = driverWait.until(ExpectedConditions.elementToBeClickable(by));
			} else if (condition == CONDITIONTYPE.VISIBLE) {
				txtForLog += " When visible";
				element = driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
			}

		} catch (Exception e) {
			txtForLog += " Failed: " + e.getMessage();
			Reporter.log(txtForLog);
			TestUtils.test.log(LogStatus.INFO, txtForLog);
			e.printStackTrace();
		}

		if (element == null) {
			Assert.fail();
		}

		TestUtils.test.log(LogStatus.INFO, txtForLog);
		Reporter.log(txtForLog);
		return element;
	}
	
	public void clickElement(WebElement wb) {
		TestUtils.test.log(LogStatus.INFO, "Clicking");
		Reporter.log("Clicking");
		wb.click();
	}
	
	public void typeInElement(WebElement wb,String strToType) {
		TestUtils.test.log(LogStatus.INFO, "Typing: "+strToType);
		Reporter.log("Typing: "+strToType);
		wb.sendKeys(strToType);
	}
	
	public void selectElementByIndex(WebElement el,int choiceNum) {
		TestUtils.test.log(LogStatus.INFO, "Selecting choice: "+choiceNum);
		Reporter.log("Selecting choice: "+choiceNum);
		Select s = new Select(el);
		s.selectByIndex(choiceNum);
	}
	
	public List<WebElement> getElements(String value) {
		List<WebElement> elements = null;
		By by = By.xpath(value);
		try {
			WebDriverWait driverWait = new WebDriverWait(wb, 10, 1000);
			//default
			elements = driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (elements == null) {
			Assert.fail();
		}

		return elements;
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
	
	

	public void quit() {
		Reporter.log("Quiting browser");
		wb.quit();
	}

}
