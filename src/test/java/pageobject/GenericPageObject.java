package pageobject;

import utilities.PropertiesWrapper;
import utilities.RemoteWebDriverWrapper;
import utilities.WebDriverWrapper;

public class GenericPageObject {
	protected static RemoteWebDriverWrapper driverWrapper;
	protected static PropertiesWrapper propertiesWrapper;

	public static void setWebDriver(RemoteWebDriverWrapper _driverWrapper) {
		driverWrapper = _driverWrapper;
	}
	
	public static void setProperties(PropertiesWrapper _propertiesWrapper) {
		propertiesWrapper = _propertiesWrapper;
	}
}
