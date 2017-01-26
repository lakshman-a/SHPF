package report;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import wrappers.FunctionLibrary;

/**
 * 
 * <h1>ExtentTestManager</h1>
 * 
 * <p>
 * <b>Class has all the functions to write and create a Report for Parallel Run</b> 
 * <b>Note :  Need not change any without clarifications asit affects Parallel thread RUn</b> 
 * 
 * @author Lakshman A
 * @since OCT 1, 2016
 *
 */

public class ExtentTestManager {
	static Map<Integer,ExtentTest> extentTestMap = new HashMap<Integer,ExtentTest>();
	static Map<Integer,ExtentTest> extentCompMap = new HashMap<Integer,ExtentTest>();
	private static Logger log = Logger.getLogger(FunctionLibrary.class.getName());
	private static ExtentReports extent;
	private static Properties property;

	public synchronized static void startReportWithFileName(int count,String fileName) throws Exception{

		property =new Properties();
		property.load(new FileInputStream(new File("./src/properties/SystemConfig.properties")));

		String log4jConfPath = property.getProperty("log4jConfPath");
		PropertyConfigurator.configure(log4jConfPath);

		extent = ExtentManager.getReporter("./"+property.getProperty("extentReportFilePath")+"/"+fileName);
		extent.loadConfig(new File(property.getProperty("extentReportConfigFile")));

	}

	public static synchronized void addReportInfo(String param, String value){
		extent.addSystemInfo(param,value);
	}

	public static synchronized void skipTestCase(String testCaseName, String testDescription,String category,String authorName) throws FileNotFoundException, IOException{
		startTestCase(testCaseName, testDescription, category, authorName);
		ExtentTest test = getTestCaseThread();
		test.log(LogStatus.SKIP, "'"+testCaseName+"' Execution status - No in TestBatch.");
		endTestCase();
	}

	public static synchronized void failTestCaseNoSheet(String testCaseName, String testDescription,String category,String authorName) throws FileNotFoundException, IOException{
		startTestCase(testCaseName, testDescription, category, authorName);
		ExtentTest test = getTestCaseThread();
		test.log(LogStatus.FATAL, "'"+testCaseName+"' TestData Excel does not have the Sheet named same as TestData Excel FileName");
		log.error("TestData Excel does not have the Sheet named  [ "+testCaseName+" ] which is same as TestData Excel FileName");
		endTestCase();
	}

	public static synchronized ExtentTest getTestCaseThread() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public static synchronized ExtentTest getComponentThread() {
		return (ExtentTest) extentCompMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public static synchronized void endTestCase() throws FileNotFoundException, IOException {
		extent.endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));
		extent.flush();
		log.info("*** End of TestCase ***\n");
	}

	public static synchronized void reportStepPass(String message){

		ExtentTest testStep = getComponentThread();
		//Delimit the XML Values
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < message.length(); i++){
			char c = message.charAt(i);
			switch(c){
			case '<': sb.append("&lt;"); break;
			case '>': sb.append("&gt;"); break;
			case '\"': sb.append("&quot;"); break;
			case '&': sb.append("&amp;"); break;
			case '\'': sb.append("&apos;"); break;
			default:
				if(c>0x7e) {
					sb.append("&#"+((int)c)+";");
				}else
					sb.append(c);
			}
		}
		String messageToWrite =  sb.toString();
		testStep.log(LogStatus.PASS, messageToWrite);
		log.info("PASS : "+message );

	}
	
	public static synchronized void reportStepFail(RemoteWebDriver driver, String message, boolean takeScreenShot){

		ExtentTest testStep = getComponentThread();

		//Delimit the XML Values
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < message.length(); i++){
			char c = message.charAt(i);
			switch(c){
			case '<': sb.append("&lt;"); break;
			case '>': sb.append("&gt;"); break;
			case '\"': sb.append("&quot;"); break;
			case '&': sb.append("&amp;"); break;
			case '\'': sb.append("&apos;"); break;
			default:
				if(c>0x7e) {
					sb.append("&#"+((int)c)+";");
				}else
					sb.append(c);
			}
		}
		String messageToWrite =  sb.toString();

		if(takeScreenShot){

			String  ssFileName=new SimpleDateFormat ("dd_MM_yyyy_hh_mm_ss_a").format(new Date());
			//Make a directory if not available for Extent Reports
			if(!(new File(System.getProperty("user.dir")+"/"+property.getProperty("extentReportFilePath")+"/"+"_Screenshots/").exists())){
				new File(System.getProperty("user.dir")+"/"+property.getProperty("extentReportFilePath")+"/"+"_Screenshots/").mkdir();
			}


			//Create Screenshots based in driver value
			if(driver.getSessionId() == null || driver.getSessionId().toString().length()==0 || driver==null){
				try {
					Robot robot = new Robot();
					String fileName =System.getProperty("user.dir")+"/"+property.getProperty("extentReportFilePath")+"/"+"_Screenshots/"+ssFileName+".jpg";
					Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
					BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
					ImageIO.write(screenFullImage, "jpg", new File(fileName));
				} catch (Exception e) {
					log.error("Exception while creating ScreenShot using Robot. Exception is --> "+ e);
				}
			}else{
				try {
					FileUtils.moveFile(driver.getScreenshotAs(OutputType.FILE) , new File("./"+property.getProperty("extentReportFilePath")+"/"+"_Screenshots/"+ssFileName+".jpg"));
				} catch (IOException e) {
					log.error("Exception while creating ScreenShot using WebDriver. Exception is --> "+ e);
				}
			}

			testStep.log(LogStatus.FAIL, messageToWrite+testStep.addScreenCapture("./_Screenshots/"+ssFileName+".jpg"));
		}else{
			testStep.log(LogStatus.FAIL, messageToWrite);
		}

		log.info("FAIL : "+message);
	}

	public static synchronized void reportStepFail(String message, boolean takeScreenShot){
		RemoteWebDriver driver = null;
		//Delimit the XML Values
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < message.length(); i++){
			char c = message.charAt(i);
			switch(c){
			case '<': sb.append("&lt;"); break;
			case '>': sb.append("&gt;"); break;
			case '\"': sb.append("&quot;"); break;
			case '&': sb.append("&amp;"); break;
			case '\'': sb.append("&apos;"); break;
			default:
				if(c>0x7e) {
					sb.append("&#"+((int)c)+";");
				}else
					sb.append(c);
			}
		}
		String messageToWrite =  sb.toString();

		reportStepFail(driver, messageToWrite, takeScreenShot);
	}

	public static synchronized void reportStepInfo(String message){

		ExtentTest testStep = getComponentThread();
		testStep.log(LogStatus.INFO, message);
		log.info("INFO : "+message );

	}

	public static synchronized void reportStepSkip(String message){

		ExtentTest testStep = getComponentThread();
		testStep.log(LogStatus.SKIP, message);
		log.info("INFO : "+message );

	}

	public static synchronized void reportStepError(String message){

		ExtentTest testStep = getComponentThread();
		testStep.log(LogStatus.ERROR, message );
		log.info("ERROR : "+message);

	}

	public static synchronized void startTestCase(String testCaseName, String testDescription) {
		ExtentTest testCase = extent.startTest(testCaseName, testDescription);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), testCase);
		log.info("***** TestCase Execution Starts *****");
		log.info("TestCase name : "+testCaseName+" | TestCase Desc : "+testDescription);
	}

	public static synchronized void startTestCase(String testCaseName, String testDescription,String category,String authorName){

		ExtentTest testCase = extent.startTest(testCaseName, testDescription);

		//Add Category(s)
		String[] arrCategory = category.split(",");
		for (String eachCategory : arrCategory) {
			if( !(eachCategory.equals("")) ){
				testCase.assignCategory(eachCategory.trim());
			}
		}
		//Add Author(s)
		String[] arrAuthor = authorName.split(",");
		for (String eachAuthor : arrAuthor) {
			if( !(eachAuthor.equals("")) ){
				testCase.assignAuthor(eachAuthor.trim());
			}
		}

		log.info("***** TestCase Execution Starts *****");
		log.info("TestCase name : "+testCaseName+" | TestCase Desc : "+testDescription);
		log.info("Category : "+category+" | Author : "+authorName);

		extentTestMap.put((int) (long) (Thread.currentThread().getId()), testCase);

	}

	public static synchronized ExtentTest startComponent(String componentName, String componentDesc){

		ExtentTest rComponent = extent.startTest(componentName, componentDesc);
		extentCompMap.put((int) (long) (Thread.currentThread().getId()), rComponent);
		log.info("*** Component Execution Starts ***");
		log.info("Component Name : "+componentName+" | Component Desc : "+componentDesc);
		return rComponent;
	}

	public static synchronized void appendComponent(){
		ExtentTest testCase = getTestCaseThread();
		ExtentTest component = getComponentThread();
		testCase.appendChild(component);
		extent.flush();
		log.info("*** End of Component ***");
	}

}
