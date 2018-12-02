package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.google.common.html.HtmlEscapers;
import com.relevantcodes.extentreports.LogStatus;

import utilities.LoggerWrapper;
import utilities.TestUtils;

public class CustomListeners extends TestUtils implements ITestListener {

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		test = getReportInstance().startTest(result.getName());
		setExtentTest(test); //unique thread
//		LoggerWrapper.init(System.getProperty("user.dir")+
//		"\\logs\\"+getCurrentTime()+".log","randomNamedLog");
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println(result.getName().toString() + " success");
		getExtentTest().log(LogStatus.PASS, result.getName() + " PASSED");
		
		String fileName    = result.getName() + TestUtils.getCurrentTime();
		String browserName = TestUtils.getWrapperInstance().getBrowserName();
		String pathToFile  = TestUtils.captureScreenshot(fileName,browserName);
		getExtentTest().log(LogStatus.INFO,getExtentTest().addScreenCapture(pathToFile));
		
		getReportInstance().endTest(getExtentTest());
		getReportInstance().flush();
	}

	public void onTestFailure(ITestResult result) {	
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		String fileName    = result.getName() + TestUtils.getCurrentTime();
		String browserName = TestUtils.getWrapperInstance().getBrowserName();
		String pathToFile  = TestUtils.captureScreenshot(fileName,browserName);
		String errToPrint  = HtmlEscapers.htmlEscaper().escape(result.getThrowable().getMessage().toString());
		
		getExtentTest().log(LogStatus.FAIL, result.getName() + " Failed,exception: " + errToPrint);
		getExtentTest().log(LogStatus.FAIL,test.addScreenCapture(pathToFile));
		
		getReportInstance().endTest(getExtentTest());
		getReportInstance().flush();
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println("Tests done");
	}

}
