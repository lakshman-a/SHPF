package wrappers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jacob.com.LibraryLoader;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import autoitx4java.AutoItX;
import report.ExtentTestManager;
import utils.ReadExcel;

/**
 * 
 * <h1>FunctionLibrary</h1>
 * 
 * <p>
 * Class holds all Low Level Functions 
 * 
 * @author Lakshman A
 * @since OCT 1, 2016
 *
 */

public class FunctionLibrary {

	protected RemoteWebDriver driver;

	//private String primaryWindowHandle,sHubUrl,sHubPort;
	protected static Logger log = Logger.getLogger(FunctionLibrary.class.getName());
	protected String testDataFilePathStatic;
	protected String  testComponentNameStatic;
	protected int gblrecordsCounterStatic;
	
	protected boolean testCaseStatus;
	protected String firstFailureStatus;
	

	String getDisplayTimeout = null;
	String getAlertTimeout = null;
	int intElementDisplayTimeout = 0;

	//Common Properties for full Project
	public static Properties property;
	public static Properties sysProperty;
	public static Properties Runtimevalue;
	public static Properties browserProperty;
	public static Properties GAFValue;

	//DB Connections:
	private Connection con;
	private Statement stmt;
	Connection con_TT;
	Statement stmt_TT;
	Connection EshopConnection;
	Statement EShopstmt;
	Connection rrbsconnection;
	Statement rrbsstatement; 
	Connection exibsconnection;
	Statement exibsstatement; 
	Connection hlrConnection;
	Statement hlrStatement; 
	Connection imgConnection;
	Statement imgStatement;
	Connection msAcsConnection;
	Statement msAcsStatement;
	
	//Unix object variable declaration
	JSch jsch=null;
	Session JSHsession=null;
	Properties Jschconfig=null;
	Channel channel=null;
	boolean connectionStatus=false;
	boolean disconnectedStatus=false;
	boolean Executionstatus=false;

	/*public synchronized WebElement getLocator(String elementInOR) {
	WebElement element = null;

	// retrieve the specified object from the object list
	String locator = OR.getProperty(elementInOR);
	if(locator==null){
		ExtentTestManager.reportStepFail(driver, "The Given Locator is not available in the Object Repository file. Check Component or OR file.", false);
	}

	String locatorType = locator.split("#")[0];
	String locatorValue = locator.split("#")[1];

	try{
		// return a instance of the WebElement class based on the type of the locator
		if(locatorType.toLowerCase().equals("id"))
			element = driver.findElement(By.id(locatorValue));
		else if(locatorType.toLowerCase().equals("name"))
			element = driver.findElement(By.name(locatorValue));
		else if((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
			element = driver.findElement(By.className(locatorValue));
		else if((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
			element = driver.findElement(By.className(locatorValue));
		else if((locatorType.toLowerCase().equals("linktext")) || (locatorType.toLowerCase().equals("link")))
			element = driver.findElement(By.linkText(locatorValue));
		else if(locatorType.toLowerCase().equals("partiallinktext"))
			element = driver.findElement(By.partialLinkText(locatorValue));
		else if((locatorType.toLowerCase().equals("cssselector")) || (locatorType.toLowerCase().equals("css")))
			element = driver.findElement( By.cssSelector(locatorValue));
		else if(locatorType.toLowerCase().equals("xpath"))
			element = driver.findElement(By.xpath(locatorValue));
		else
			throw new Exception("Unknown locator type '" + locatorType + "'");

	}catch(StaleElementReferenceException e){
		log.info("[getLocator] -- > {StaleElementReferenceException}");
		//Logic to Handle th Stale Element Exception
		if(countVariable < Integer.parseInt(property.get("staleWaitTime").toString())){
			countVariable++;
			element = getLocator(elementInOR);
			countVariable=0;
			return element;
		}else{
			throw new RuntimeException("Retried for "+property.get("staleWaitTime")+" units of StaleElementReferenceException. "
					+ "Element takes more time to Load/Page Loads Slowly.");
		}

	}catch(NoSuchElementException e){
		log.info("[getLocator] -- > {NoSuchElementException}");
		//Logic to Handle th Stale Element Exception
		throw new NoSuchElementException("Element given in the OR file cannot be Found. Check the OR or Element may be changed in WebPage.");
	}
	return element;
}*/

	/*public synchronized List<WebElement> listGetLocator(String elementInOR) {
List<WebElement> element = null;
String locator = OR.getProperty(elementInOR);

String locatorType = locator.split("#")[0];
String locatorValue = locator.split("#")[1];

try{
	// return a instance of the WebElement class based on the type of the locator
	if(locatorType.toLowerCase().equals("id"))
		element = driver.findElements(By.id(locatorValue));
	else if(locatorType.toLowerCase().equals("name"))
		element = driver.findElements(By.name(locatorValue));
	else if((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
		element = driver.findElements(By.className(locatorValue));
	else if((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
		element = driver.findElements(By.className(locatorValue));
	else if((locatorType.toLowerCase().equals("linktext")) || (locatorType.toLowerCase().equals("link")))
		element = driver.findElements(By.linkText(locatorValue));
	else if(locatorType.toLowerCase().equals("partiallinktext"))
		element = driver.findElements(By.partialLinkText(locatorValue));
	else if((locatorType.toLowerCase().equals("cssselector")) || (locatorType.toLowerCase().equals("css")))
		element = driver.findElements( By.cssSelector(locatorValue));
	else if(locatorType.toLowerCase().equals("xpath"))
		element = driver.findElements(By.xpath(locatorValue));
	else
		throw new Exception("Unknown locator type '" + locatorType + "'");

}catch(StaleElementReferenceException e){
	log.info("[getLocator] -- > {StaleElementReferenceException}");
	//Logic to Handle th Stale Element Exception
	if(countVariable < Integer.parseInt(sysProperty.get("elementDisplayTimeout").toString())){
		countVariable++;
		element = listGetLocator(elementInOR);
		countVariable=0;
		return element;
	}else{
		throw new RuntimeException("Retried for "+sysProperty.get("elementDisplayTimeout")+" units of StaleElementReferenceException. "
				+ "Element takes more time to Load/Page Loads Slowly.");
	}

}catch(NoSuchElementException e){
	log.info("[getLocator] -- > {NoSuchElementException}");
	//Logic to Handle th Stale Element Exception
	throw new NoSuchElementException("Element given in the OR file cannot be Found. Check the OR or Element may be changed in WebPage.");
}
return element;
}*/

	/*public synchronized boolean RefreshObject(String getValueFromPOM){
	boolean refresh = false;
	try{
		log.info("RefreshObject function Try block");
		int a = 0;
		while(a < 10){
			Thread.sleep(1000);	
			//selectByLocatorType(getValueFromPOM);
			a++;
			if(selectByLocatorType(getValueFromPOM).isDisplayed()){
				refresh = true;
				log.info("Element is displayed by used refresh object");
				break;
			}
		}
	}catch(Exception e){
		log.info("While refreshing, element is not found.");
		refresh = false;
	}
	return refresh;

}*/

	public void initializeFiles() {

		try {

			property =new Properties();
			property.load(new FileInputStream(new File("./src/properties/Env.properties")));

			sysProperty =new Properties();
			sysProperty.load(new FileInputStream(new File("./src/properties/SystemConfig.properties")));

			Runtimevalue=new Properties();
		
			GAFValue =new Properties();
			GAFValue.load(new FileInputStream(new File("./src/properties/GlobalAddressFinderValues.properties")));

			PropertyConfigurator.configure(sysProperty.getProperty("log4jConfPath"));
			log.info("Initialized all the files...");
			
			browserProperty =new Properties();
			browserProperty.load(new FileInputStream(new File("./src/properties/browser.properties")));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized String getTestData(String testDataFilePath, String testComponentName, String colName, int gblrecordsCounter) throws Exception{

		String testData=null;
		try{

			ReadExcel readTestData= new ReadExcel(testDataFilePath);
			testData = readTestData.getCellData(testComponentName, colName, gblrecordsCounter);

		}catch(Exception e){
			ExtentTestManager.reportStepError("Error in reading the value from test data sheet's Column '" + colName + "' .");
			throw e;
		}
		if(testData==null){
			ExtentTestManager.reportStepError("Column Name'" + colName + "' is not available in '"+testComponentName+"' Component Sheet.");
			throw new RuntimeException("Column Name'" + colName + "' is not available in '"+testComponentName+"' Component Sheet.");
		}
		return testData;
	}

	public synchronized String RetrieveTestDataValue(String testDataFilePath, String testComponentName, String colName, int gblrecordsCounter) throws Exception{

		String testData=null;
		try{

			ReadExcel readTestData= new ReadExcel(testDataFilePath);
			testData = readTestData.getCellData(testComponentName, colName, gblrecordsCounter);

		}catch(Exception e){
			ExtentTestManager.reportStepError("Error in reading the value from test data sheet's Column '" + colName + "' .");
			throw e;
		}
		if(testData==null){
			ExtentTestManager.reportStepError("Column Name'" + colName + "' is not available in '"+testComponentName+"' Component Sheet.");
			throw new RuntimeException("Column Name'" + colName + "' is not available in '"+testComponentName+"' Component Sheet.");
		}
		return testData;
	}

	public synchronized WebElement selectByLocatorType(String getValueFromPOM){

		WebElement element = null;
		if(getValueFromPOM==null){
			ExtentTestManager.reportStepFail(driver, "Given Locator or key is not available in the Page Object Class. Check Component or POM.", false);
			return null;
		}
		
		String locatorFromPOM=getValueFromPOM;

		try{
			String locatorType = getValueFromPOM.split("#")[0];
			String locatorValue = getValueFromPOM.split("#")[1];
			
			String loc = locatorType;
			switch(loc.toLowerCase()){
			case "id":
				element = driver.findElement(By.id(locatorValue));
				break;
			case "xpath":
				element = driver.findElement(By.xpath(locatorValue));
				break;
			case "css":
				element = driver.findElement(By.cssSelector(locatorValue));
				break;
			case "classname":
				element = driver.findElement(By.className(locatorValue));
				break;
			case "name":
				element = driver.findElement(By.name(locatorValue));
				break;
			case "linktext":
				element = driver.findElement(By.linkText(locatorValue));
				break;
			case "tagname":
				element = driver.findElement(By.tagName(locatorValue));
				break;
			case "partiallinktext":
				element = driver.findElement(By.partialLinkText(locatorValue));
				break;
			default:
				throw new IllegalArgumentException("Check the given locator Type in POM");
			}	
		}catch(StaleElementReferenceException e){
			return selectByLocatorType(locatorFromPOM);
		}catch (NoSuchElementException e) {
			ExtentTestManager.reportStepInfo("No such element '"+ getValueFromPOM +"' found or dislayed");
			//return (WebElement) e;
			return null;
		}catch (Exception e) {
			ExtentTestManager.reportStepInfo("Exception occured while finding the element'"+ getValueFromPOM +"'. Exception is "+e);
			//return (WebElement) e;
			return null;
		}

		//for ie browser and chrome bring the element to view and perform any actions
		if(element!=null){
			String browserName = browserProperty.getProperty("testBrowser");
			if (browserName.equalsIgnoreCase("firefox")||browserName.equalsIgnoreCase("ie")) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
		}

		return element;
	}

	public boolean copyFileFromSourceToDest(String sourcePath,String destPath){
		File source = new File(sourcePath);
		File dest = new File(destPath);
		try {
		    FileUtils.copyFile(source, dest);
		    return true;
		} catch (Exception e) {
		    e.printStackTrace();
		    return false;
		}
	}
	
	public synchronized boolean pageLoadCheck(){

		//do nothing
		//return true;

		String browserName=browserProperty.getProperty("testBrowser");
		if (browserName.equalsIgnoreCase("chrome")||browserName.equalsIgnoreCase("ie")) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			Date s = new GregorianCalendar().getTime();
			ExtentTestManager.reportStepPass("Page load Start Time " + s);
			//Report_Functions.ReportEventSuccess(doc,"4","","Page load Start Time " + s,3);
			int flag = 0;
			String docStatus;
			JavascriptExecutor js = (JavascriptExecutor) driver;
			try {
				for (int i = 0; i < 60; i++) {
					if (js.executeScript("return document.readyState").toString().equals("complete")) {

						flag = 1;
						break;
					}

					else {
						docStatus = js.executeScript("return document.readyState").toString();
						Thread.sleep(1000);
						// Report_Functions.ReportEventSuccess(doc,"4","","Else condition page loading status :" +docStatus, 3);
						ExtentTestManager.reportStepPass("Else condition page loading status :" + docStatus);
					}

				}

			} catch (InterruptedException e) {
				log.info("InterruptedException Occured " + e);

			} catch (Exception e1) {
				log.info("Exception Occured " + e1);
			}
			if (flag == 1) {
				Date s2 = new GregorianCalendar().getTime();
				//Report_Functions.ReportEventSuccess(doc,"Page is loaded successfully",3);
				ExtentTestManager.reportStepPass("Page is loaded successfully");
				//Report_Functions.ReportEventSuccess(doc,"4","","Page load End Time " + s2,3);
				ExtentTestManager.reportStepPass("Page load End Time " + s2);
				return true;
			} else {
				Date s1 = new GregorianCalendar().getTime();
				ExtentTestManager.reportStepFail(driver, "Page doesn't loaded Successfully, Load time :" + s1 + "",
						true);
				//Report_Functions.ReportEventFailure(doc,"","Page doesn't loaded Successfully, Load time :"+s1+"" , true);
				ExtentTestManager.reportStepFail(driver, "Page load End Time " + s1, true);
				//Report_Functions.ReportEventSuccess(doc,"4","","Page load End Time " + s1,3);
				return false;
			} 
		}else if(browserName.equalsIgnoreCase("firefox")){
			//Do Nothing
			return true;
		}else{
			return false;
		}

	}

	@SuppressWarnings("deprecation")
	public synchronized void launchAppWithTimeout()
			throws InterruptedException {

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					log.info("Starting the IE Browser with Thread ID - "+(int) Thread.currentThread().getId());
					System.setProperty("webdriver.ie.driver", "./BrowserDrivers/IEDriverServer.exe");
					DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setCapability("ignoreZoomSetting", true);
					capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
					driver = new InternetExplorerDriver(capabilities);
					log.info("Driver started in launchAppWithTimeout");
				} catch (Exception e) {
					e.printStackTrace();
					log.info("Exception#Error occured in the launchAppWithTimeout");
				}

			}
		});

		thread.start();
		long endTimeMillis = System.currentTimeMillis() + 15000;

		while (thread.isAlive()) {
			if (System.currentTimeMillis() > endTimeMillis) {
				break;
			}
			try {
				Thread.sleep(500);
			}
			catch (InterruptedException t) {}
		}

		thread.stop();
		log.info("Thread Stopped after the action done! ID "+(int) Thread.currentThread().getId());
	}
	
	public synchronized boolean LaunchApplication(String strAppURL,int strExecEventFlag ) throws InterruptedException {
		String urlToLaunch=null;
		String urlEnvVariableNameFromExcel=null;
		String browserName=null;

		try {
			if(strExecEventFlag==1){
				urlEnvVariableNameFromExcel= getTestData(testDataFilePathStatic, testComponentNameStatic, strAppURL, gblrecordsCounterStatic);
			}

			urlToLaunch=property.getProperty(urlEnvVariableNameFromExcel);
			browserName=browserProperty.getProperty("testBrowser");
			
			if (browserName.equalsIgnoreCase("firefox")) {
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				driver.get(urlToLaunch);
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			} else if (browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "./BrowserDrivers/chromedriver.exe");
				driver = new ChromeDriver();
				driver.manage().window().maximize();
				driver.get(urlToLaunch);
				//Do not un-comment the below line or u may face - 'cannot determine loading status from timeout' issue
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			} else if (browserName.equalsIgnoreCase("ie")) {
				launchAppWithTimeout();
				log.info("After First launchAppWithTimeout Trigger. Now IE Driver should be NULL or May have value.");
				if(driver==null){
					log.info("IE Driver which was initialized before was having blank Page!. So triggering LaunchappTimeout once again...");
					launchAppWithTimeout();
				}else{
					log.info("IE Driver instantiated successfully at the first time itself.");
				}
			}

			if (browserName.equalsIgnoreCase("ie")) {
			driver.manage().window().maximize();
			driver.get(urlToLaunch);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			}

			ExtentTestManager.reportStepPass("'" + browserName.toUpperCase() + "' Browser lauched with URL '" + urlToLaunch + "' successfully." );
			return  true;

		} catch(WebDriverException e){
			e.printStackTrace();
			ExtentTestManager.reportStepFail(driver,"'Webdriver Exception : " + browserName.toUpperCase() + "' Browser failed to launch with URL '" + urlToLaunch + "'. Exception is : "+e,true);
			log.info("Webdriver Exception : "+e.getMessage());
			//return LaunchApplication(strAppURL, strExecEventFlag);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			ExtentTestManager.reportStepFail(driver,"'" + browserName.toUpperCase() + "' Browser failed to launch with URL '" + urlToLaunch + "'. Exception is : "+e,true);
			return false;
		}
	}

	public synchronized boolean waitUntilExist(String strLocator,  String strTestObject) {
		boolean functionStatus= false;
		WebElement element = null;
		
		try{
			
			String locatorType = strLocator.split("#")[0];
			String locatorValue = strLocator.split("#")[1];
			
			intElementDisplayTimeout=Integer.parseInt(sysProperty.getProperty("elementDisplayTimeout"));
			WebDriverWait wait = new WebDriverWait(driver,intElementDisplayTimeout);
			
			switch(locatorType.toLowerCase()){

			case "id":	element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "xpath": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "css": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "classname": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "name": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "linktext": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "tagname": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "partiallinktext": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;
			default:
				functionStatus = false;
				throw new IllegalArgumentException("Check the Given Locator Type");
			}

		}catch(NullPointerException e){
			ExtentTestManager.reportStepFail(driver,"Exception occured while locationg the Locator '"+strLocator+"'. Exception is "+e,false);
			return false;
		}catch(StaleElementReferenceException e){
			log.info("Exception is : "+e);
			return waitUntilExist(strLocator, strTestObject);
		}catch(TimeoutException e){
			e.printStackTrace();
			ExtentTestManager.reportStepFail(driver,"Timed out after "+intElementDisplayTimeout+" seconds waiting for visibility of Element '"+ strTestObject +"'",true);
			return false;
		}catch(Exception e){
			e.printStackTrace();
			ExtentTestManager.reportStepFail(driver,"Exception occured while waiting for visibility of Element '"+ strTestObject +"'",true);
			return false;
		}

		if(functionStatus){
			ExtentTestManager.reportStepPass("Element '"+ strTestObject +"' is displayed successfully within "+intElementDisplayTimeout+" seconds" );
		}else{
			ExtentTestManager.reportStepFail(driver,"Timed out after "+intElementDisplayTimeout+" seconds waiting for visibility of Element '"+ strTestObject +"'",true);
		}

		return functionStatus;
	}
	
	public synchronized boolean waitUntilExistZoomOut(String strLocator,  String strTestObject) {
		boolean functionStatus= false;
		WebElement element = null;
		
		try{
			
			String browserName = browserProperty.getProperty("testBrowser");
			try {
				if (browserName.equalsIgnoreCase("firefox")||browserName.equalsIgnoreCase("ie")) {

					WebElement html = driver.findElement(By.tagName("html"));
					html.sendKeys(Keys.chord(Keys.CONTROL, Keys.ADD));
					Thread.sleep(200);
					html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));

				}else if (browserName.equalsIgnoreCase("chrome")) {

					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("document.body.style.zoom='90%'");
					Thread.sleep(200);
					js.executeScript("document.body.style.zoom='100%'");

				}
			} catch (Exception e) {
				e.printStackTrace();
				log.info("Exception is :"+e);
			} 
			
			String locatorType = strLocator.split("#")[0];
			String locatorValue = strLocator.split("#")[1];
			
			intElementDisplayTimeout=Integer.parseInt(sysProperty.getProperty("elementDisplayTimeout"));
			WebDriverWait wait = new WebDriverWait(driver,intElementDisplayTimeout);
			
			switch(locatorType.toLowerCase()){

			case "id":	element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "xpath": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "css": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "classname": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "name": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "linktext": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "tagname": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "partiallinktext": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;
			default:
				functionStatus = false;
				throw new IllegalArgumentException("Check the Given Locator Type");
			}

		}catch(NullPointerException e){
			ExtentTestManager.reportStepFail(driver,"Exception occured while locationg the Locator '"+strLocator+"'. Exception is "+e,false);
			return false;
		}catch(StaleElementReferenceException e){
			log.info("Exception is : "+e);
			return waitUntilExist(strLocator, strTestObject);
		}catch(TimeoutException e){
			e.printStackTrace();
			ExtentTestManager.reportStepFail(driver,"Timed out after "+intElementDisplayTimeout+" seconds waiting for visibility of Element '"+ strTestObject +"'",true);
			return false;
		}catch(Exception e){
			e.printStackTrace();
			ExtentTestManager.reportStepFail(driver,"Exception occured while waiting for visibility of Element '"+ strTestObject +"'",true);
			return false;
		}

		if(functionStatus){
			ExtentTestManager.reportStepPass("Element '"+ strTestObject +"' is displayed successfully within "+intElementDisplayTimeout+" seconds" );
		}else{
			ExtentTestManager.reportStepFail(driver,"Timed out after "+intElementDisplayTimeout+" seconds waiting for visibility of Element '"+ strTestObject +"'",true);
		}

		return functionStatus;
	}

	public synchronized boolean waitUntilExistForGivenSeconds(String strLocator,  String strTestObject, int secondsToWait) {
		boolean functionStatus= false;
		WebElement element = null;

		try{
			String locatorType = strLocator.split("#")[0];
			String locatorValue = strLocator.split("#")[1];

			WebDriverWait wait = new WebDriverWait(driver,secondsToWait);
			switch(locatorType.toLowerCase()){

			case "id":	element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "xpath": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "css": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "classname": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "name": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "linktext": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "tagname": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;

			case "partiallinktext": element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
			if(element.isDisplayed()) functionStatus = true;
			else functionStatus = false;
			break;
			default:
				functionStatus = false;
				throw new IllegalArgumentException("Unable to found");
			}

		}catch(NullPointerException e){
			ExtentTestManager.reportStepFail(driver,"Given Locator '"+strLocator+"' in the Component is not available in the corresponding POM page.",false);
			return false;
		}catch(StaleElementReferenceException e){
			return waitUntilExist(strLocator, strTestObject);
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Exception occured while waiting for visibility of Element '"+ strTestObject +"'",true);
			return false;
		}

		if(functionStatus){
			ExtentTestManager.reportStepPass("Element '"+ strTestObject +"' is displayed successfully within "+secondsToWait+" seconds" );
		}else{
			ExtentTestManager.reportStepFail(driver,"Timed out after "+secondsToWait+" seconds waiting for visibility of Element '"+ strTestObject +"'",true);
		}

		return functionStatus;
	}	

	public synchronized boolean WebEditEnterUsername(String strLocator, String strTestObject,String strtestData,int strExecEventFlag ) {

		boolean functionStatus= false;
		String testData=null;
		String strUserID=null;
		try {
			if(strExecEventFlag==1){
				testData= getTestData(testDataFilePathStatic, testComponentNameStatic, strtestData, gblrecordsCounterStatic);
			}else{
				testData=strtestData;
			}
			if(testData==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the data sheet.",false);
				return false;
			}
			if(property.getProperty("Use_ENV_UserID_Password").equalsIgnoreCase("Yes")){
				ExtentTestManager.reportStepPass("Username is fetched from the Property file with Key '"+testData+"'");
				strUserID=property.getProperty(testData);
			}else{
				strUserID=testData;
			}
			if(strUserID==null){
				ExtentTestManager.reportStepFail(driver,"Username is NULL. No NULL value can be entered in the '"+ strTestObject +"'.",false);
				return false;
			}
			selectByLocatorType(strLocator).sendKeys(strUserID);
			ExtentTestManager.reportStepPass("Username '" +  strUserID + "' is successfully entered in the textBox '"+strTestObject+"'");
			functionStatus = true;
		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Username '" +  strUserID + "' is not entered in the textBox '"+strTestObject+"'",true);
			functionStatus = false;
		}
		return functionStatus;
	}

	public synchronized boolean WebEditEnterPassword(String strLocator, String strTestObject,String strtestData,int strExecEventFlag ) {

		boolean functionStatus= false;
		String testData=null;
		String strPwd=null;
		try {
			if(strExecEventFlag==1){
				testData= getTestData(testDataFilePathStatic, testComponentNameStatic, strtestData, gblrecordsCounterStatic);
			}else{
				testData=strtestData;
			}
			if(testData==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the data sheet.",false);
				return false;
			}
			if(property.getProperty("Use_ENV_UserID_Password").equalsIgnoreCase("Yes")){
				ExtentTestManager.reportStepPass("Password is fetched from the Property file with Key '"+testData+"'");
				strPwd=property.getProperty(testData);
			}else{
				strPwd=testData;
			}
			if(strPwd==null){
				ExtentTestManager.reportStepFail(driver,"Password is NULL. No NULL value can be entered in the '"+ strTestObject +"'.",false);
				return false;
			}
			selectByLocatorType(strLocator).sendKeys(strPwd);
			ExtentTestManager.reportStepPass("Password '*******' is successfully entered in the textBox '"+strTestObject+"'");
			functionStatus = true;
		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Password '*******' is not entered in the textBox '"+strTestObject+"'",true);
			functionStatus = false;
		}
		return functionStatus;
	}

	public synchronized boolean WebElementClick(String strLocator, String elementName) {
		try{
			String browserName = browserProperty.getProperty("testBrowser");
			if (browserName.equalsIgnoreCase("firefox")||browserName.equalsIgnoreCase("ie")||browserName.equalsIgnoreCase("chrome")) {
				WebElement element = selectByLocatorType(strLocator);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
				Thread.sleep(200);
				element = selectByLocatorType(strLocator);
				element.click();
			}else{
				WebElement element = selectByLocatorType(strLocator);
				element.click();
			}
			ExtentTestManager.reportStepPass("Element '"+elementName+"' is clicked successfully.");
			return true;
		}catch (StaleElementReferenceException e) {
			log.info("Exception occured in Webelement click Exception is "+e.getStackTrace());
			return WebElementClick(strLocator, elementName);
		} catch (Exception e) {
			log.info("Exception occured in Webelement click Exception is "+e.getStackTrace());
			ExtentTestManager.reportStepFail(driver,"Element '"+elementName+"' is not clicked.",true);
			return false;
		}
	}

	public synchronized boolean webListDropdownClick(String getValueFromPOM, String strTestObject, String strtestData, int strExecEventFlag){

		boolean functionStatus = false;
		String actualDropdownValue = null;
		String testData= null;
		try{
			if(strExecEventFlag==1){
				testData= getTestData(testDataFilePathStatic, testComponentNameStatic, strtestData, gblrecordsCounterStatic);
			}else{
				testData=strtestData;
			}

			if(testData == null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the data sheet.",false);
				return false;
			}
			List<WebElement> element = listSelectByLocatorType(getValueFromPOM);

			for(WebElement dropdownValue : element){
				actualDropdownValue = dropdownValue.getText();
				if((dropdownValue.getText()).equalsIgnoreCase(testData)){
					dropdownValue.click();
					ExtentTestManager.reportStepPass("'"+actualDropdownValue+"' is clicked successfully from the Dropdown list '"+strTestObject+"'");
					functionStatus = true;
				}
			}

		}catch(Exception e){
			log.info("Exception occurred in web table radio button :"+e.getMessage());
			ExtentTestManager.reportStepFail(driver,"'"+actualDropdownValue+"' is not clicked from the Dropdown list '"+strTestObject+"'.",true);
			functionStatus = false;
		}
		return functionStatus;
	}

	public synchronized List<WebElement> listSelectByLocatorType(String getValueFromPOM){

		List<WebElement> element = null;
		String locatorType = getValueFromPOM.split("#")[0];
		String locatorValue = getValueFromPOM.split("#")[1];

		try{
			switch(locatorType.toLowerCase()){
			case "id":
				element = driver.findElements(By.id(locatorValue));
				break;
			case "xpath":
				element = driver.findElements(By.xpath(locatorValue));
				break;
			case "css":
				element = driver.findElements(By.cssSelector(locatorValue));
				break;
			case "classname":	
				element = driver.findElements(By.className(locatorValue));
				break;
			case "name":
				element = driver.findElements(By.name(locatorValue));
				break;
			case "linktext":
				element = driver.findElements(By.linkText(locatorValue));
				break;
			case "tagname":
				element = driver.findElements(By.tagName(locatorValue));
				break;
			case "partiallinktext":
				element = driver.findElements(By.partialLinkText(locatorValue));
				break;
			default:
				throw new IllegalArgumentException("Unable to found");
			}	
		}catch(StaleElementReferenceException e1){
			listSelectByLocatorType(getValueFromPOM);
		}catch (Exception e) {
			log.info("Exception: Element is not found ;"+ e);
			return null;
		}	
		return element;
	}

	public synchronized boolean enterValuesAndClickEnterBtn(String getValueFromPOM, String strTestObject, String strtestData, int strExecEventFlag){

		boolean functionStatus= false;
		String testData=null;
		try {

			if(strExecEventFlag==1){
				testData= getTestData(testDataFilePathStatic, testComponentNameStatic, strtestData, gblrecordsCounterStatic);
			}else{
				testData=strtestData;
			}

			selectByLocatorType(getValueFromPOM).clear();
			selectByLocatorType(getValueFromPOM).sendKeys(testData);
			selectByLocatorType(getValueFromPOM).sendKeys(Keys.ENTER);
			ExtentTestManager.reportStepPass("Key '" +  testData + "' is entered in the Textbox '"+strTestObject+"' and 'ENTER' key is pressed successfully.");
			functionStatus = true;

		} catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver,"Key '" +  testData + "' is not entered in the Textbox '"+strTestObject+"' and 'ENTER' key is not pressed successfully.",true);
			functionStatus = false;
			log.info("No Element Found to enter text : " + e);
		}
		return functionStatus;
	}

	public synchronized boolean doubleClickOnElement(String getValueFromPOM, String strTestObject){
		try {
			if(browserProperty.getProperty("testBrowser").equalsIgnoreCase("chrome")|| (browserProperty.getProperty("testBrowser").equalsIgnoreCase("ie")) ){
				WebElement element = selectByLocatorType(getValueFromPOM);
				Actions action = new Actions(driver);
				action.doubleClick(element).build().perform();
				ExtentTestManager.reportStepPass("'"+strTestObject+"' is double clicked successfully");
				return true;
			}else if( (browserProperty.getProperty("testBrowser").equalsIgnoreCase("firefox")) ){
				
			/*	WebElement element = selectByLocatorType(getValueFromPOM);
				log.info("Object :"+element);
				((JavascriptExecutor)driver).executeScript("arguments[0].dblClick();", element);
				Thread.sleep(500);
				
				WebElement element = selectByLocatorType(getValueFromPOM);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
				Thread.sleep(700);
				element = selectByLocatorType(getValueFromPOM);
				element.click();
				//Thread.sleep(500); 
				//((JavascriptExecutor)driver).executeScript("arguments[0].dblclick();", element);
				//((JavascriptExecutor)driver).executeScript("arguments[0].dblclick;", element);
				Actions action = new Actions(driver);
				action.doubleClick(element).build().perform();
				Thread.sleep(200); */
				
				WebElement element = selectByLocatorType(getValueFromPOM);
				Actions action = new Actions(driver);
				action.doubleClick(element).build().perform();
				ExtentTestManager.reportStepPass("'"+strTestObject+"' is double clicked successfully");
				return true;
			
			}else{
				return false;
			}

		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"'"+strTestObject+"' was not double clicked",true);
			return false;
		}
	}

	public synchronized boolean alertAccept(String strTestObject){

		boolean functionStatus = false;
		try{
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			//alert.getText();
			alert.accept();
			functionStatus=true;
			ExtentTestManager.reportStepPass("Displayed '"+strTestObject+"' Alert is accepted successfully");
		}catch(NoAlertPresentException e){
			log.info("Exception occurred, while accepting the alert :"+e.getMessage());
			ExtentTestManager.reportStepFail(driver,"No Alert is displayed to Accept",true);
		}catch(Exception e){
			log.info("Exception occurred, while accepting the alert :"+e.getMessage());
			ExtentTestManager.reportStepFail(driver,"'"+strTestObject+"' Alert is not accepted",true);
		}
		return functionStatus;
	}

	public synchronized boolean CloseWebBrowser(){

		boolean functionStatus=false;
		String strBrowserName=null;
		try{
			if(browserProperty.getProperty("testBrowser").equalsIgnoreCase("firefox"))
				strBrowserName = "Mozilla";
			else if(browserProperty.getProperty("testBrowser").equalsIgnoreCase("chrome"))
				strBrowserName = "chrome";
			else if(browserProperty.getProperty("testBrowser").equalsIgnoreCase("IE"))
				strBrowserName = "IE";
			try{
				driver.close();
				log.info("Closing the Focused Browser.");
			}catch(Exception e){
				log.info("close : "+e);
			}
			try{
				if(driver!=null){
					log.info("Quiting the Driver Session!");	
					Thread.sleep(1000);
					//Below function commented since this Window popup appears only while using the Selenium 3x and Gecko driver
					//Now we are currently using only Selenium 2x without Gecko
					/*if(browserProperty.getProperty("testBrowser").equalsIgnoreCase("firefox")){
						Runtime.getRuntime().exec("taskkill /F /IM plugin-container.exe");
						Thread.sleep(1000);
					}*/
					
					driver.quit();
				}
			}catch(Exception e){
				log.info("quit : "+e);
			}

			ExtentTestManager.reportStepPass("Successfully Closed the browser "+ strBrowserName+"");
			functionStatus=true;
		}catch(Exception e){
			log.info("In Exception of close browser. Exception is : " + e);
			ExtentTestManager.reportStepFail(driver,"Unable to close the browser "+strBrowserName+"", false);
		}finally{
			driver=null;
		}
		return functionStatus;
	}

	public synchronized boolean WebListSelect(String getValueFromPOM, String strTestObject,String strtestData,int strExecEventFlag){
		String testData=null;

		try {
			if(strExecEventFlag==1){
				testData= getTestData(testDataFilePathStatic, testComponentNameStatic, strtestData, gblrecordsCounterStatic);
			}else{
				testData=strtestData;
			}
			if(testData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}
			new Select(selectByLocatorType(getValueFromPOM)).selectByVisibleText(testData);
			ExtentTestManager.reportStepPass("Item '" +  testData + "' is selected from the '"+strTestObject+"' List box successfully");
			return true;
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Item '" +  testData + "' was not selected from the  '"+strTestObject+"' List box ",true);
			return false;
		}
	}

	public synchronized boolean WebEditEnterText(String getValueFromPOM, String strTestObject,String strtestData,int strExecEventFlag ){

		boolean functionStatus= false;
		String testData=null;

		try {
			if(strExecEventFlag==1)
				testData= getTestData(testDataFilePathStatic, testComponentNameStatic, strtestData, gblrecordsCounterStatic);
			else
				testData=strtestData;

			if(testData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			selectByLocatorType(getValueFromPOM).clear();
			selectByLocatorType(getValueFromPOM).sendKeys(testData);
			ExtentTestManager.reportStepPass("Text '" +  testData + "' is entered successfully in the textbox '"+strTestObject+"'");
			functionStatus=true;	

		} catch (Exception e) { 	
			ExtentTestManager.reportStepFail(driver,"Text '" + testData + "' was not entered in the textbox '"+strTestObject+"'", true);
			log.info("No Element Found to enter text : " + e);
		}
		return functionStatus;
	}
	
	public synchronized boolean WebElementTextCompare(String getValueFromPOM, String strTestObject,String strtestData, int strExecEventFlag ){
		String actualResult=null;
		boolean functionStatus= false;
		String testData=null;

		try{
			if(strExecEventFlag==1)
				testData= getTestData(testDataFilePathStatic, testComponentNameStatic, strtestData, gblrecordsCounterStatic);
			else
				testData=strtestData;

			if(testData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			actualResult = selectByLocatorType(getValueFromPOM).getText();

		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while getting the text from the WebElement '"+strTestObject+"' . Error description is :"+e.getMessage(),true);
			functionStatus=false;
		}

		try{
			if((actualResult.trim()).equalsIgnoreCase(testData.trim())){
				ExtentTestManager.reportStepPass("Actual value '" +actualResult+ "' matches with the expected value '"+testData+ "' in the input field '"+strTestObject+"'");
				functionStatus=true;
			}else{
				ExtentTestManager.reportStepFail(driver,"Actual Value '" +actualResult+ "' does not match with the Expected value '"+testData+ "' in the input field '"+strTestObject+"'",true);
				functionStatus=false;
			}
		}catch (StaleElementReferenceException e){
			return WebElementTextCompare(getValueFromPOM, strTestObject, strtestData, strExecEventFlag);
		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing actual and expected values. Error description is :"+e.getMessage(), true);
			functionStatus=false;
		}
		return functionStatus;
	}

	public synchronized boolean WebEditEnterValueAndCompareSame(String getValueFromPOM, String strTestObject,String strtestData,String strtestDataExpected,int strExecEventFlag ){
		boolean functionStatus= false;
		String testData=null;
		String strExpectedData=null;
		String actualResult=null;

		try{
			if(strExecEventFlag==1){
				testData= getTestData(testDataFilePathStatic, testComponentNameStatic, strtestData, gblrecordsCounterStatic);
				strExpectedData= getTestData(testDataFilePathStatic, testComponentNameStatic, strtestDataExpected, gblrecordsCounterStatic);
			}else{
				testData=strtestData;
				strExpectedData=strtestDataExpected;
			}
			if(testData==null || strExpectedData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}
			selectByLocatorType(getValueFromPOM).clear();
			selectByLocatorType(getValueFromPOM).sendKeys(testData);
			ExtentTestManager.reportStepPass("Entering the Text '" +  testData + "' in the Textbox '"+strTestObject+"'");
		} catch (Exception e) { 	
			ExtentTestManager.reportStepFail(driver,"Text '" +  testData + "' was not entered in the Textbox '"+strTestObject+"'", true);
			functionStatus=false;
		}

		try {
			actualResult = selectByLocatorType(getValueFromPOM).getAttribute("value");

			if((actualResult.trim()).equalsIgnoreCase(strExpectedData.trim())){
				ExtentTestManager.reportStepPass("'"+strTestObject+"'s  accepted value '" + actualResult + "' matches the Expected value '" + strExpectedData + "'");
				functionStatus=true;
			}else{
				ExtentTestManager.reportStepFail(driver,"'"+strTestObject+"'s accepted value '" + actualResult + "' does not match the Expected value '" + strExpectedData + "'",true);
				functionStatus=false;
			}

		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Exception occured while comparing"+strTestObject+"'s accepted value and Expected Value '" + strExpectedData + "'",true);
			functionStatus=false;
			log.info("No Element Found to compare Text : " + e);
		}
		return functionStatus;
	}

	public synchronized boolean SQLDBOpenConnection(String sqlserver, String sqldbname, String sqlusername, String sqlpassword){

		String dbUrl = "jdbc:sqlserver://"+ sqlserver +";DatabaseName=" + sqldbname +";";                  
		String username = sqlusername;   
		String password = sqlpassword; 

		if(sqldbname==null || sqlusername==null || sqlpassword==null || sqlserver==null){
			ExtentTestManager.reportStepFail(driver,"SQL DB details not provided in the Property file.", false);
			return false;
		}
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
			DriverManager.setLoginTimeout(10);
			con = DriverManager.getConnection(dbUrl,username,password);
			stmt = con.createStatement(); 
			ExtentTestManager.reportStepPass("SQL Connection for DB '"+sqldbname+"' in server '"+sqlserver+"' is established Successfully.");
			return true;

		} catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver,"Error occured while connecting to the SQL Server. Error description is : "+ e.getMessage(), false);
			return false;
		}
	}

	public synchronized boolean RRBSDBOpenConnection(String dbserver, String portnumber, String dbname, String dbusername, String dbpassword){
		String serverName = property.getProperty(dbserver);
		String portNumber = property.getProperty(portnumber);
		String sid = property.getProperty(dbname);
		String username = property.getProperty(dbusername);   
		String password = property.getProperty(dbpassword); 

		if(serverName==null || portNumber==null || sid==null || username==null||password==null){
			ExtentTestManager.reportStepFail(driver,"RRBS DB details not provided in the Property file.", false);
			return false;
		}

		try{
			String dbUrl = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid; 
			Class.forName("oracle.jdbc.OracleDriver");         
			rrbsconnection = DriverManager.getConnection(dbUrl,username,password);
			rrbsstatement = rrbsconnection.createStatement(); 
			ExtentTestManager.reportStepPass("RRBS DB Connection is established from '"+sid+"' DB in '"+serverName+"' Successfully.");
			return true;

		} catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver, "Error occured while connecting to the SQL Server. Error description is : "+ e.getMessage(), false);
			return false;
		}
	}

	public synchronized boolean SQLDBDelete(String sqltablename, String sqlcondition, int strExecEventFlag){
		String tablename = null;
		String condition = null;

		try {
		if(strExecEventFlag==1){
			tablename= getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
			condition= getTestData(testDataFilePathStatic, testComponentNameStatic, sqlcondition, gblrecordsCounterStatic);
		}else{
			tablename= sqltablename;
			condition= sqlcondition;
		}

		if(tablename==null || condition==null){
			ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
			return false;
		}
	
			String check = "select * from "+tablename +" where "+condition;
			String query = "Delete from "+ tablename +" where "+ condition;
			ResultSet rs = stmt.executeQuery(check);		
			int temp=0;	

			while(rs.next()){
				temp++;
			}
			if(temp >= 1){
				stmt.execute(query);
				ExtentTestManager.reportStepPass("SQL Delete Query  "+ query + "  executed successfully.");
				return true;
			}else{
				ExtentTestManager.reportStepPass( "SQL Delete Query  "+ query + "  has NO RECORDS in DB");
				return true;
			}

		}catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver,"Error occured while deleting to the SQL query. Error description is : "+ e.getMessage(), false);
			return false;
		}
	}

	public synchronized boolean SQLDBUpdate(String sqltablename, String strsqlcolumnname,String strsqlcolumnvalue,String strsqlcondition,int strExecEventFlag){
		String Table_name;
		String Column_name;
		String Column_Value;
		String SQL_condition;
		String query;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				Column_Value=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnvalue, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
			}else{
				Table_name=sqltablename;
				Column_name=strsqlcolumnname;
				Column_Value=strsqlcolumnvalue;
				SQL_condition=strsqlcondition;
			}

			if(Table_name==null || Column_name==null || Column_Value==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			if(Column_Value.equalsIgnoreCase("NULL")){
				query = "update "+Table_name+" set "+Column_name+"=NULL where "+SQL_condition;
			}else{
				query = "update "+Table_name+" set "+Column_name+"="+Column_Value+" where "+SQL_condition;
			}

			stmt.execute(query);
			ExtentTestManager.reportStepPass("SQL Update Query  "+ query + " executed successfully.");
			return true;

		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Error occured while executing to the SQL Update query. Error description is : "+ e.getMessage(), false);
			return false;
		}
	}

	public synchronized boolean RRBSDBUpdate(String sqltablename, String strsqlcolumnname,String strsqlcolumnvalue,String strsqlcondition,int strExecEventFlag){
		String Table_name;
		String Column_name;
		String Column_Value;
		String SQL_condition;
		String query;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				Column_Value=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnvalue, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
			}else{
				Table_name=sqltablename;
				Column_name=strsqlcolumnname;
				Column_Value=strsqlcolumnvalue;
				SQL_condition=strsqlcondition;
			}

			if(Table_name==null || Column_name==null || Column_Value==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			if(Column_Value.contains("to_date")){
				query = "update "+Table_name+" set "+Column_name+"="+Column_Value+" where "+SQL_condition;
			}else{
				if(Column_Value.equalsIgnoreCase("null")){
					query = "update "+Table_name+" set "+Column_name+"=null where "+SQL_condition;
				}else{
					query = "update "+Table_name+" set "+Column_name+"='"+Column_Value+"' where "+SQL_condition;
				}
			}

			rrbsstatement.execute(query);
			ExtentTestManager.reportStepPass("RRBS Update Query  "+ query + "  executed successfully.");
			return true;
		} catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver,"Error occured while executing RRBS Update Query. Error description is : "+ e.getMessage(), false);
			return false;
		}
	}

	public synchronized boolean SQLDBCloseConnection(){
		try {
			con.close(); 
			ExtentTestManager.reportStepPass("SQL DB Connection disconnected successfully.");
			return true;
		} catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver,"Error occured while closing the SQL DB.Error description is : "+ e.getMessage(), false);
			return false;
		}
	}

	public synchronized boolean SQLDBCloseConnection_TT(){
		try {
			con_TT.close(); 
			ExtentTestManager.reportStepPass("SQL TT DB Connection disconnected successfully.");
			return true;
		} catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver,"Error occured while closing the SQL DB.Error description is : "+ e.getMessage(), false);
			return false;
		}
	}

	public synchronized boolean RRBSDBCloseConnection(){
		try {
			rrbsconnection.close(); 
			ExtentTestManager.reportStepPass("RRBS DB Connection disconnected successfully.");
			return true;
		} catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver,"Error occured while closing the RRBS DB.Error description is : "+ e.getMessage(), false);
			return false;
		}
	}

	public synchronized boolean SQLDBSelect(String sqltablename, String strsqlcolumnname,String strsqlcondition,String strExpectedvalue,int strExecEventFlag){
		boolean functionStatus=false;
		String query=null;
		String check;
		String Table_name;
		String Column_name;
		String SQL_condition;
		String Expected_value;
		String Actual_Value;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
				Expected_value=getTestData(testDataFilePathStatic, testComponentNameStatic, strExpectedvalue, gblrecordsCounterStatic);
			}else{
				Table_name=sqltablename;
				Column_name=strsqlcolumnname;
				SQL_condition=strsqlcondition;
				Expected_value=strExpectedvalue;
			}

			if(Table_name==null || Column_name==null || SQL_condition==null||Expected_value==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			String pattern = "^Count\\((.*)\\)";
			// Create a Pattern object
			  Pattern r = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
			// Now create matcher object.
			  Matcher m = r.matcher(Column_name);
			  if (m.find( )) {
				return SQLDBCheckNoOfRowsExist(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue, strExecEventFlag);
			  }
				
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;

			//Check for Record Available
			ResultSet rs_SQLServerCheck = stmt.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = stmt.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the SQL Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the SQL Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else{

				ResultSet rs_SQLServer = stmt.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1).trim();

				if (Expected_value.contains("CURRENT_DATE")){
					String []ExpectedvalueWithFormat=Expected_value.split("#");
					String db_Date = Actual_Value.split(" ")[0];
					Actual_Value=db_Date;
					Date date=new Date();			
					SimpleDateFormat dateformat=new SimpleDateFormat(ExpectedvalueWithFormat[1]);
					Expected_value=dateformat.format(date);
				}

				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the SQL Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Actual_Value.equalsIgnoreCase(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the SQL Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in SQL query '"+query+"'.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean SQLDBDateCompare(String sqltablename, String strsqlcolumnname,String strsqlcondition,String Date_Format,int strExecEventFlag){
		boolean functionStatus= false;
		String query = null;  
		String check = null; 
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String Expected_value = null;
		String Actual_Value = null;
		String Current_Date=null;
		String expected_db_Date = null;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			Current_Date = dateFormat.format(date);
			Expected_value = Current_Date.trim();

			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;

			ResultSet rs_SQLServerCheck= stmt.executeQuery(check);

			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = stmt.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the SQL Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the SQL Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else if(!(rs_SQLServerCheck.wasNull())){

				ResultSet rs_SQLServer= stmt.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1);

				String db_Date = Actual_Value.split(" ")[0];
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				Date dateToChange = dateFormat1.parse(db_Date);
				SimpleDateFormat finalDateFormat = new SimpleDateFormat(Date_Format);
				expected_db_Date = finalDateFormat.format(dateToChange);

				if(expected_db_Date.equals(Expected_value)){
					ExtentTestManager.reportStepPass("Actual Date '"+Actual_Value+"' for the SQL Query "+query+" matches the expected Date '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(expected_db_Date.equals(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual Date '"+Actual_Value+"' for the SQL Query "+query+" does not match the expected Date '"+Expected_value+"'",false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in SQL query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean RRBSDBSelect(String sqltablename, String strsqlcolumnname,String strsqlcondition,String strExpectedvalue,int strExecEventFlag){
		boolean functionStatus=false;
		String query=null;
		String check;
		String Table_name;
		String Column_name;
		String SQL_condition;
		String Expected_value;
		String Actual_Value;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
				Expected_value=getTestData(testDataFilePathStatic, testComponentNameStatic, strExpectedvalue, gblrecordsCounterStatic);
			}else{
				Table_name=sqltablename;
				Column_name=strsqlcolumnname;
				SQL_condition=strsqlcondition;
				Expected_value=strExpectedvalue;
			}

			if(Table_name==null || Column_name==null || SQL_condition==null||Expected_value==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			query = "SELECT "+Column_name+" FROM "+Table_name+" WHERE "+SQL_condition;
			check = "SELECT "+Column_name+" FROM "+Table_name+" WHERE "+SQL_condition;

			//Check for Record Available
			ResultSet rs_SQLServerCheck = rrbsstatement.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = rrbsstatement.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the RRBS Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the RRBS Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else if(!(rs_SQLServerCheck.wasNull())){

				ResultSet rs_SQLServer = rrbsstatement.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1).trim();

				if (Expected_value.contains("CURRENT_DATE")){
					String []ExpectedvalueWithFormat=Expected_value.split("#");
					String db_Date = Actual_Value.split(" ")[0];
					Actual_Value=db_Date;
					Date date=new Date();			
					SimpleDateFormat dateformat=new SimpleDateFormat(ExpectedvalueWithFormat[1]);
					Expected_value=dateformat.format(date);
				}

				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the RRBS Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Actual_Value.equalsIgnoreCase(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the RRBS Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in RRBS query || "+query+".Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean alertAccept(){

		try{
			Thread.sleep(1200);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			//alert.getText();
			alert.accept();
			//ExtentTestManager.reportStepPass( "Alert with Text '"+alert.getText()+"' is Accepted successfully");
			ExtentTestManager.reportStepPass( "Displayed Alert accepted successfully");
			return true;

		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Alert not Accepted. Exception is '"+e.getMessage()+"'", true);
			return false;

		}
	}

	public synchronized boolean WebElementResxKeyValueStaticCompare(String getValueFromPOM, String strTestObject,String strColumnName, int strExecEventFlag ){
		String actualResult=null;
		String automationKey=null;
		String valueFromResxFile=null;

		try{
			if(strExecEventFlag==1){
				automationKey=getTestData(testDataFilePathStatic, testComponentNameStatic, strColumnName, gblrecordsCounterStatic);
			}else{
				automationKey=strColumnName;
			}

			log.info("automationKey in Fl RESX STATIC COMPARE is : "+automationKey);

			if(automationKey==null || automationKey.equalsIgnoreCase("")){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			//Actual value of the Element
			actualResult = selectByLocatorType(getValueFromPOM).getText();

			//Get the Value for the Key from the .resx file using the Automation from Resource Management file
			valueFromResxFile=RetrieveValueUsingAutomationKey(automationKey);
			log.info("valueFromResxFile in Fl RESX STATIC COMPARE is : "+valueFromResxFile);

			if(valueFromResxFile==null || automationKey.equalsIgnoreCase("")){
				ExtentTestManager.reportStepFail(driver, "Error occured while retrieving data from the Resource Management Data file.", true);
				return false;
			}

			if((actualResult.trim()).equals(valueFromResxFile.trim())){
				ExtentTestManager.reportStepPass("'"+strTestObject+"'s actual value '" +actualResult+ "' matches with the content in the Resource file '"+valueFromResxFile+"'");
				return true;
			}else{
				ExtentTestManager.reportStepFail(driver,"'"+strTestObject+"'s actual value '" +actualResult+ "' does not match with the content in the Resource file '"+valueFromResxFile+"'", true);
				return false;
			}
		}catch (StaleElementReferenceException e){
			return WebElementResxKeyValueStaticCompare(getValueFromPOM, strTestObject, strColumnName, strExecEventFlag);
		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while comparing the text of a WebElement '"+strTestObject+"'and the error description is :"+e.getMessage(), true);
			return false;
		}

	}

	public synchronized String RetrieveValueUsingAutomationKey(String strAutomationkey){
		String resourceFileName=null;
		String resourceKey=null;
		String resourceCountry=null;
		String filePathLocation=null;
		NodeList nodeList=null;
		String resxFilePath="//\\"+property.getProperty("resourceMgmtfilekeyPath");
		ReadExcel suiteXL = new ReadExcel(resxFilePath);

		try{
			resourceFileName =suiteXL.RetrieveAutomationKeyFromExcel( property.getProperty("resourcefileSheetName"), "Resource_FileName", strAutomationkey);
			resourceKey=suiteXL.RetrieveAutomationKeyFromExcel(property.getProperty("resourcefileSheetName"), "Resource_Key", strAutomationkey);
			resourceCountry=suiteXL.RetrieveAutomationKeyFromExcel(property.getProperty("resourcefileSheetName"), "Country_KeyInPropFile", strAutomationkey);

			if(resourceFileName==null || resourceFileName.trim().equals("") || resourceKey==null || resourceKey.trim().equals("") || resourceCountry==null || resourceCountry.trim().equals("")){
				ExtentTestManager.reportStepFail(driver, "Resource Filename or key is Empty or NULL in the Resource Management sheet", false);
				return null;
			}

			filePathLocation=property.getProperty(resourceCountry);
			String resourceFileToGetValue=filePathLocation+"\\"+resourceFileName;
			log.info("File path is "+resourceFileToGetValue);
			File file=new File("//\\"+resourceFileToGetValue);

			String commonAttribute=property.getProperty("attributeCommonValue");
			String fullAttributewithName=commonAttribute+"[@name='"+resourceKey+"']/value";
			log.info("Attribute to Retrieve Node value is : "+fullAttributewithName);

			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document document=builder.parse(file);
			document.getDocumentElement().normalize();
			XPath xpath=XPathFactory.newInstance().newXPath();
			nodeList=(NodeList)xpath.compile(fullAttributewithName).evaluate(document,XPathConstants.NODESET);

			String nodeValue = nodeList.item(0).getTextContent();
			log.info("Value for the Key is : "+nodeValue);
			ExtentTestManager.reportStepPass("Retrieved Value of the Key '" +resourceKey+ "' from the Resource file '"+resourceFileName+"' is '"+nodeValue+"'.");
			return nodeValue;

		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while Retrieving the value for the corresponding key from Resource file. Error description is :"+e.getMessage(), false);
			e.printStackTrace();
			return null;
		}
	}

	public synchronized boolean storeSQLDBValueInEnv(String sqltablename, String strsqlcolumnname, String strsqlcondition,String envValColumnName, int strExecEventFlag){
		String query = null;
		String check = null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String getIDValueFromRecord = null;
		String setParameterName=null;;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
				setParameterName=getTestData(testDataFilePathStatic, testComponentNameStatic, envValColumnName, gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null||setParameterName==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			//Query to Execute      
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;

			//Check for Record Available
			ResultSet rs_SQLServerCheck = stmt.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = stmt.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				ExtentTestManager.reportStepFail(driver,"Selected DB value is NULL. No values stored in RUNTIME Variable", false);
				return false;

			}else{
				ResultSet rs_SQLServer = stmt.executeQuery(query);
				rs_SQLServer.next();
				getIDValueFromRecord = rs_SQLServer.getString(1).trim();
				Runtimevalue.setProperty(setParameterName, getIDValueFromRecord);
				ExtentTestManager.reportStepPass("Actual DB value '"+getIDValueFromRecord+"' is stored in RunTime variable '"+setParameterName+"'  successfully");
				return true;
			}

		} catch (Exception e) {    
			ExtentTestManager.reportStepFail(driver,"Error occured while storing the values in SQL query.Error description is : "+ e.getMessage(), false);
			return false;
		}

	}

	public synchronized boolean enterEnvValueAndClickEnter(String getValueFromPOM, String envVariableColName, String strTestObject, int strExecEventFlag){
		String envVariable=null;
		String strData = null;
		try {
			if(strExecEventFlag == 1){
				envVariable = getTestData(testDataFilePathStatic, testComponentNameStatic, envVariableColName, gblrecordsCounterStatic);
			}else{
				envVariable=envVariableColName;
			}

			if(envVariable == null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}
			strData = Runtimevalue.getProperty(envVariable);
			if(strData == null){
				ExtentTestManager.reportStepFail(driver, "Environment Variable '" + strData + "' is Empty", false);
				return false;
			}

			WebElement element = selectByLocatorType(getValueFromPOM);
			if(element.isDisplayed()){

				selectByLocatorType(getValueFromPOM).sendKeys(Keys.chord(Keys.CONTROL, "a"));
				selectByLocatorType(getValueFromPOM).sendKeys(strData);
				selectByLocatorType(getValueFromPOM).sendKeys(Keys.ENTER);

				ExtentTestManager.reportStepPass("Text '" +  strData + "' is entered in the Textbox '"+strTestObject+"' and 'ENTER' button pressed successfully");
				return true;
			}else{
				ExtentTestManager.reportStepFail(driver,"The Text '" + strData + "' was not entered in the Textbox - '"+strTestObject+"' and ENTER button not pressed.", true);
				return false;

			}
		} catch (Exception e) { 	
			ExtentTestManager.reportStepFail(driver,"The Text '" + strData + "' was not entered in the Textbox - '"+strTestObject+"' and ENTER button not pressed.", true);
			return false;
		}
	}

	public synchronized boolean PerformAction_MoveToElementAndClick(String getValueFromPOM, String strOject) {
		
		try{
			if(browserProperty.getProperty("testBrowser").equalsIgnoreCase("chrome")){
				/*WebElement element=selectByLocatorType(getValueFromPOM);
				Actions action = new Actions(driver);
				action.moveToElement(element).click().build().perform();
				ExtentTestManager.reportStepPass("Mouse moved to the element '"+strOject+"' and click action is done successfully");
				return true;*/
				
				WebElement element = selectByLocatorType(getValueFromPOM);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
				Thread.sleep(700); 
				element = selectByLocatorType(getValueFromPOM);
				element.click();
				ExtentTestManager.reportStepPass("Mouse moved to the element '"+strOject+"' and click action is done successfully");
				return true;
				
			}else if(browserProperty.getProperty("testBrowser").equalsIgnoreCase("ie") || browserProperty.getProperty("testBrowser").equalsIgnoreCase("firefox")){
				WebElement element = selectByLocatorType(getValueFromPOM);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
				Thread.sleep(700); 
				element = selectByLocatorType(getValueFromPOM);
				element.click();
				ExtentTestManager.reportStepPass("Mouse moved to the element '"+strOject+"' and click action is done successfully");
				return true;
			}else{
				return false;
			}

		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Mouse not  moved to the element '"+strOject+"' and click action is not performed", true);
			return false;
		}
		
	}

	public synchronized boolean noAction() {
		try{
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public synchronized boolean SQLDBOpenConnection_TT(String sqlserver, String sqldbname, String sqlusername, String sqlpassword){

		String dbUrl = "jdbc:sqlserver://"+ sqlserver +";DatabaseName=" + sqldbname +";";                  
		String username = sqlusername;   
		String password = sqlpassword; 

		if(sqldbname==null || sqlusername==null || sqlpassword==null || sqlserver==null){
			ExtentTestManager.reportStepFail(driver,"SQL DB details not provided in the Property file.", false);
			return false;
		}
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			con_TT = DriverManager.getConnection(dbUrl,username,password);
			stmt_TT = con_TT.createStatement(); 
			ExtentTestManager.reportStepPass("SQL Connection for TT DB '"+sqldbname+"' in server '"+sqlserver+"' is established Successfully.");
			return true;

		} catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver,"Error occured while connecting to the SQL Server. Error description is : "+ e.getMessage(), false);
			return false;
		}
	}

	public synchronized boolean SQLDBDelete_TT(String sqltablename, String sqlcondition, int strExecEventFlag){
		String tablename = null;
		String condition = null;
		
		try {	
		if(strExecEventFlag==1){
			tablename= getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
			condition= getTestData(testDataFilePathStatic, testComponentNameStatic, sqlcondition, gblrecordsCounterStatic);
		}else{
			tablename= sqltablename;
			condition= sqlcondition;
		}

		if(tablename==null || condition==null){
			ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
			return false;
		}

		
			String check = "select * from "+tablename +" where "+condition;
			String query = "Delete from "+ tablename +" where "+ condition;
			ResultSet rs = stmt_TT.executeQuery(check);		
			int temp=0;	

			while(rs.next()){
				temp++;
			}
			if(temp >= 1){
				stmt_TT.execute(query);
				ExtentTestManager.reportStepPass("SQL TT Delete Query  "+ query + "  executed successfully.");
				return true;
			}else{
				ExtentTestManager.reportStepPass( "SQL TT Delete Query  "+ query + "  has NO RECORDS in DB");
				return true;
			}

		}catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver,"Error occured while deleting to the SQL query. Error description is : "+ e.getMessage(), false);
			return false;
		}
	}

	public synchronized boolean javaScriptDatePicker(String getValueFromPOM, String strTestObject, String strColumnName, int strExecEventFlag){
		String elementValue = null;

		try{
			String elementFromPOM=null;
			elementFromPOM=getValueFromPOM;
			int count=elementFromPOM.split("#").length;
			//log.info("JS POM Count : "+count);
			if(count==2){
				getValueFromPOM=elementFromPOM.split("#")[1];
				//log.info("changed getValueFromPOM is : "+getValueFromPOM);
			}else{
				//log.info("Not changed getValueFromPOM is : "+getValueFromPOM);
			}
			
			if(strExecEventFlag==1){
				elementValue = getTestData(testDataFilePathStatic, testComponentNameStatic, strColumnName, gblrecordsCounterStatic);
			}
			if(elementValue==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}
			//Get the locatorType from POM during runtime
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementById('"+getValueFromPOM+"').value = '"+elementValue+"'");
			ExtentTestManager.reportStepPass("Date picker value '"+elementValue+"' is entered for "+strTestObject+"");
			return true;

		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Date picker value '"+elementValue+"' is entered for "+strTestObject+". Exception is : "+e.getLocalizedMessage(), true);
			return false;

		}


	}

	/*public synchronized boolean VerifyIsModalPopupPresent(String strTestObject){
		Object isModalPresent=false;
		try{
			for(int i=0; i<60; i++){
				try{
					Thread.sleep(1000);
					log.info("Inside Loop... i is -> "+i);
					isModalPresent = ((JavascriptExecutor)driver).executeScript("return document.activeElement.getElementsByClassName('modal-footer')[0].innerHTML.trim()!=null;");
					if((boolean) isModalPresent){
						log.info("Element is appeared");
						break;
					}
				}catch(WebDriverException e){
					log.info("WebDriverException caught inside for since Popup is not appeared");
				}
			}

			if((boolean) isModalPresent){
				ExtentTestManager.reportStepPass("'The Modal Popup '"+ strTestObject +"' is displayed.");
				return true;
			}else{
				ExtentTestManager.reportStepFail(driver,"'The Modal Popup '"+ strTestObject +"' is not displayed within 60 secs.", true);
				return false;
			}


		}catch(StaleElementReferenceException e){
			log.info("StaleElementReferenceException caught inside for since Popup is not appeared");
			ExtentTestManager.reportStepPass("'The element '"+ strTestObject +"' is not displayed");
			return false;
		}
		catch(Exception e){
			log.info("Element is not found :"+e);
			ExtentTestManager.reportStepFail(driver,"Exception occured. Error message is : "+ e +".", true);
			return false;
		}
	}*/

	public synchronized boolean VerifyIsModalPopupPresent(String strTestObject) throws Exception{
		Object isModalPresent=false;
		String ffBrowserName = null;

		try{

			ffBrowserName = browserProperty.getProperty("testBrowser");
			if(ffBrowserName.equalsIgnoreCase("Firefox")){
				WebDriverWait ww = new WebDriverWait(driver, 60);
				ww.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='ErrmsgClose']/ancestor::div[@class='modal-content']")));
				WebElement element = driver.findElement(By.xpath("//button[@id='ErrmsgClose']/ancestor::div[@class='modal-content']"));
				if(element.isDisplayed()){
					ExtentTestManager.reportStepPass("'The Modal Popup '"+ strTestObject +"' is displayed.");
					return true;
				}
			}
			else{
				for(int i=0; i<60; i++){
					try{
						Thread.sleep(1000);
						log.info("Inside Loop... i is -> "+i);
						isModalPresent = ((JavascriptExecutor)driver).executeScript("return document.activeElement.getElementsByClassName('modal-footer')[0].innerHTML.trim()!=null;");
						if((boolean) isModalPresent){
							break;
						}
					}catch(WebDriverException e){
						log.info("WebDriverException caught inside for since Popup is not appeared");
					}
				}
			}

			if((boolean) isModalPresent){
				ExtentTestManager.reportStepPass("'The Modal Popup '"+ strTestObject +"' is displayed.");
				return true;
			}else{
				ExtentTestManager.reportStepFail(driver,"'The Modal Popup '"+ strTestObject +"' is not displayed within 60 secs.", true);
				return false;
			}

		}catch(StaleElementReferenceException e){
			log.info("StaleElementReferenceException caught inside for since Popup is not appeared");
			ExtentTestManager.reportStepPass("'The element '"+ strTestObject +"' is not displayed");
			return false;
		}
		catch(Exception e){
			log.info("Element is not found :"+e);
			ExtentTestManager.reportStepFail(driver,"Exception occured. Error message is : "+ e +".", true);
			return false;
		}
	}

	public synchronized boolean VerifyModalPopupDynamicText(String getValueFromPOM, String strTestObject,String strPattern, int strExecEventFlag){

		boolean ModalVerifyDynamicText=false;
		boolean matchedStatus=false;
		Matcher matchedPattern = null;
		String Pattern_String = null;
		Object RetrievedMessage=null;

		try{

			JavascriptExecutor jse = (JavascriptExecutor)driver;
			RetrievedMessage = (jse.executeScript("return arguments[0].innerHTML;", selectByLocatorType(getValueFromPOM)));
			log.info("RetrievedMessage is : "+RetrievedMessage);

			try{
				if(strExecEventFlag==1){
					Pattern_String = getTestData(testDataFilePathStatic, testComponentNameStatic, strPattern, gblrecordsCounterStatic);
				}
				if(Pattern_String==null){
					ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
					return false;
				}

				Pattern_String = Pattern_String.trim();
				Pattern expPattern=Pattern.compile(Pattern_String);
				matchedPattern = expPattern.matcher(RetrievedMessage.toString());
				matchedStatus=matchedPattern.find();
			}catch(Exception e){
				ExtentTestManager.reportStepFail(driver,"Error occured while matching the Expected pattern-matchedPattern.find():"+e.getMessage(), true);
				ModalVerifyDynamicText=false;
			}

			try{
				if(matchedStatus==true){
					ExtentTestManager.reportStepPass("The Expected pattern '"+Pattern_String+"' in the webElement '"+strTestObject+"' matches with the actual pattern: '"+RetrievedMessage+"' successfully");
					ModalVerifyDynamicText=true;
				}else if(matchedStatus==false){
					ExtentTestManager.reportStepFail(driver,"The Expected pattern '"+Pattern_String+"' in the webElement '"+strTestObject+"' does not match with the actual pattern: '"+RetrievedMessage+"'", true);
					ModalVerifyDynamicText=false;
				}
			} catch (Exception e){
				ExtentTestManager.reportStepFail(driver,"Error occured while finding the Pattern in the function 'WebElementDynamicStringVerify'.Error description is : "+ e.getMessage() +".", true);
				log.info("WebElementDynamicStringVerify Error : " + e);
				ModalVerifyDynamicText=false;
			}

		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver, "Error occured while Verifying Moda lPopup Dynamic Text. Error description is : "+ e.getMessage() +".", true);
			log.info("WebElementDynamicStringVerify Error : " + e);
			ModalVerifyDynamicText=false;
		}
		return ModalVerifyDynamicText;
	}

	public synchronized boolean StoreModalPopupDynamicSubstringValue(String getValueFromPOM, String strTestObject,String envVariableColumn,String startIndexColumn,String endIndexColumn, int strExecEventFlag ){

		Object actualText=null;
		String substring=null;
		String strDataStart=null;
		String strDataEnd=null;
		String envVariableName=null;
		int intDataStart=0;
		int intDataend=0;
		boolean WebElementTextCompare=false;

		try{

			if(strExecEventFlag==1){
				strDataStart=getTestData(testDataFilePathStatic, testComponentNameStatic, startIndexColumn, gblrecordsCounterStatic);
				strDataEnd=getTestData(testDataFilePathStatic, testComponentNameStatic, endIndexColumn, gblrecordsCounterStatic);
				envVariableName=getTestData(testDataFilePathStatic, testComponentNameStatic, envVariableColumn, gblrecordsCounterStatic);
			}
			if(strDataStart==null|| strDataEnd==null||envVariableName==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			JavascriptExecutor jse = (JavascriptExecutor)driver;
			actualText = (jse.executeScript("return arguments[0].innerHTML;", selectByLocatorType(getValueFromPOM)));
			intDataStart = Integer.parseInt(strDataStart);
			intDataend = Integer.parseInt(strDataEnd);
			substring = actualText.toString().trim().substring(intDataStart, intDataend).trim();

		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while getting the text from the WebElement :'"+strTestObject+"'and the error description is :"+e.getMessage(), true);
			return false;
		}

		try{
			Runtimevalue.setProperty(envVariableName, substring);
			ExtentTestManager.reportStepPass("The Dynamic value '"+substring+"' of Element '"+strTestObject+"' is successfully stored in the Runtime variable '"+envVariableName+"'");
			WebElementTextCompare=true;
		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while Stroing the Dynamic value of element '"+strTestObject+"' in the Runtime variable '"+envVariableName+"'. Error description is :"+e.getMessage(), true);
			WebElementTextCompare=false;
		}
		return WebElementTextCompare;
	}

	public synchronized boolean javascriptWebElementClick(String getValueFromPOM, String strTestObject){

		boolean WebElementClick= false;
		try {
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", selectByLocatorType(getValueFromPOM));
			ExtentTestManager.reportStepPass("'"+strTestObject+"' is clicked successfully ");
			WebElementClick=true;
		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"'"+strTestObject+"' was not clicked" , true);
			WebElementClick=false;
		}
		return WebElementClick;
	}

	public synchronized boolean WebElementEditable(String getValueFromPOM, String strTestObject) {
		String elementenable;
		boolean WebElementEnabled = false;
		try {
			elementenable=selectByLocatorType(getValueFromPOM).getAttribute("readonly");
			if(elementenable.equalsIgnoreCase("false")){
				WebElementEnabled = true;
				ExtentTestManager.reportStepPass("The object '"+strTestObject+"' is  editable as expected.");
			}else{
				WebElementEnabled=false;
				ExtentTestManager.reportStepFail(driver,"The object '"+strTestObject+"' is not editable.", true);
			}
		} catch (Exception e) { 	
			ExtentTestManager.reportStepFail(driver,"Error occured while checking the object '"+strTestObject+"' is editable.", true);
			WebElementEnabled=false;
		}
		return WebElementEnabled;
	}

	public synchronized boolean WebElementEnabled(String getValueFromPOM, String strTestObject) {

		boolean elementenable;
		boolean WebElementEnabled = false;
		try {
			elementenable=selectByLocatorType(getValueFromPOM).isEnabled();

			if(elementenable){
				WebElementEnabled = true;
				ExtentTestManager.reportStepPass("The object '"+strTestObject+"' is  enabled as expected.");
			}else{
				WebElementEnabled=false;
				ExtentTestManager.reportStepFail(driver,"The object '"+strTestObject+"' is not enabled.", true);
			}

		} catch (Exception e) { 	
			ExtentTestManager.reportStepFail(driver,"Error occured while checking the object '"+strTestObject+"' is enabled.", true);
			WebElementEnabled=false;
		}
		return WebElementEnabled;
	}

	public synchronized boolean WaitUntilElementClickable(String getValueFromPOM, String strTestObject) {
		boolean Waituntilexpectedtext= false;
		String Element_Text = null;
		try{
			FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver);
			fWait.withTimeout(300, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(selectByLocatorType(getValueFromPOM)));
			Element_Text = selectByLocatorType(getValueFromPOM).getText();
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while getting the text from the element '"+strTestObject+"' .Error description is : "+ e.getMessage() +"." , true);
			Waituntilexpectedtext=false;
		}
		if(!(Element_Text==null)){
			ExtentTestManager.reportStepPass("Element '"+strTestObject+ "' is now Clickable");
			Waituntilexpectedtext= true;
		}else{
			ExtentTestManager.reportStepFail(driver,"Element '"+strTestObject+ "' still not Clickable after certain period of time", false);
			Waituntilexpectedtext= false;
		}
		return Waituntilexpectedtext;
	}

	public synchronized boolean WebEditEnterTextFromEnvVariable(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag ){
		String strData=null;
		String dataToEnter=null;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic, strColumnName, gblrecordsCounterStatic);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}
			dataToEnter=Runtimevalue.getProperty(strData);
			selectByLocatorType(getValueFromPOM).clear();
			selectByLocatorType(getValueFromPOM).sendKeys(dataToEnter);
			ExtentTestManager.reportStepPass("The Text '" +  dataToEnter + "' from Runtime Variable '"+strData+"' is entered in the Textbox -  '"+strTestObject+"'  successfully");
			return true;	
		} catch (Exception e) { 	
			ExtentTestManager.reportStepFail(driver,"The Text '" + dataToEnter + "' from  Runtime Variable '"+strData+"' was not entered in the Textbox - '"+strTestObject+"'", true);
			return false;
		}
	}

	public synchronized boolean JavaScriptWebEditEnterCurrentDate(String getValueFromPOM, String strTestObject, String strColumnName, int strExecEventFlag){
		String dateFormatToEnter=null;
		String Current_Date=null;
		try{
			
			String elementFromPOM=null;
			elementFromPOM=getValueFromPOM;
			int count=elementFromPOM.split("#").length;
			//log.info("JS POM Count : "+count);
			if(count==2){
				getValueFromPOM=elementFromPOM.split("#")[1];
				//log.info("changed getValueFromPOM is : "+getValueFromPOM);
			}else{
				//log.info("Not changed getValueFromPOM is : "+getValueFromPOM);
			}

			if(strExecEventFlag==1){
				dateFormatToEnter=getTestData(testDataFilePathStatic, testComponentNameStatic, strColumnName, gblrecordsCounterStatic);
			}
			if(dateFormatToEnter==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			JavascriptExecutor js = (JavascriptExecutor) driver;
			//20/09/2016
			DateFormat dateFormat = new SimpleDateFormat(dateFormatToEnter);
			Date date = new Date();
			Current_Date = dateFormat.format(date).trim();

			js.executeScript("document.getElementById(\""+getValueFromPOM+"\").value = \""+Current_Date+"\"");
			ExtentTestManager.reportStepPass("Current Date '"+Current_Date+"' entered in the Element "+strTestObject+" using JavaScript");
			return true;

		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Current date '"+Current_Date+"' is not entered in the element "+strTestObject+" using JavaScript" , true);
			e.printStackTrace();
			return false;
		}
	}

	public synchronized boolean EshopSQLDBOpenConnection(String sqlserver, String sqldbname, String sqlusername, String sqlpassword){
		String dbUrl = "jdbc:sqlserver://"+ sqlserver +";DatabaseName=" + sqldbname +";";                  
		String username = sqlusername;   
		String password = sqlpassword; 

		if(sqldbname==null || sqlusername==null || sqlpassword==null || sqlserver==null){
			ExtentTestManager.reportStepFail(driver,"Required details are not provided in the datasheet.", false);
			return false;
		}
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			EshopConnection = DriverManager.getConnection(dbUrl,username,password);
			EShopstmt = EshopConnection.createStatement(); 
			ExtentTestManager.reportStepPass("ESHOP SQL Connection for GBR is established successfully with DB Name '"+sqldbname+"' and Server '"+sqlserver+"'.");
			return true;
		} catch (Exception e) { 
			ExtentTestManager.reportStepPass("Error while establishing the ESHOP SQL Connection for GBR with DB Name '"+sqldbname+"' and Server '"+sqlserver+"'.");
			return false;
		}
	}

	public synchronized boolean EshopSQLDBUpdate(String sqltablename, String strsqlcolumnname,String strsqlcolumnvalue,String strsqlcondition,int strExecEventFlag){
		String Table_name = null;
		String Column_name = null;
		String Column_Value = null;
		String SQL_condition = null;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				Column_Value=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnvalue,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || Column_Value==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the data sheet." , false);
				return false;
			}

			String query = "update "+Table_name+" set "+Column_name+"="+Column_Value+" where "+SQL_condition;
			EShopstmt.execute(query);
			ExtentTestManager.reportStepPass("ESHOP SQL Update Query  "+ query + " executed successfully.");
			return true;
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver, "Error occured while executing the ESHOP Update Query.Error description is : "+ e.getMessage() +".", false);
			return false;
		}
	}

	public synchronized boolean EshopSQLDBDelete(String sqltablename, String sqlcondition, int strExecEventFlag){
		String tablename = null;
		String condition = null;

		try{
			if(strExecEventFlag==1){
				tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				condition=getTestData(testDataFilePathStatic, testComponentNameStatic, sqlcondition,gblrecordsCounterStatic);
			}
			if(tablename==null || condition==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the data sheet.", false);
				return false;
			}
			String check = "select * from "+tablename +" where "+condition;
			String query = "Delete from "+ tablename +" where "+ condition;
			ResultSet rs = null;
			rs = EShopstmt.executeQuery(check);		
			int temp=0;	
			while(rs.next()){
				temp++;
			}
			if(temp >= 1){
				EShopstmt.execute(query);
				ExtentTestManager.reportStepPass("ESHOP SQL Delete Query "+ query + " executed successfully.");
				return true;
			}else{
				ExtentTestManager.reportStepPass("ESHOP SQL Delete Query "+ query +" has NO RECORDS available in DB");
				return true;
			}
		}catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver,"Error occured while executing delete ESHOP SQL query. Error description is : "+ e.getMessage() +".", false);
			return false;
		}
	}

	public synchronized boolean RRBSDBCommonPreCondition(String actionName,String sqltablename, String strsqlcolumnname,String strsqlcolumnvalue,String strsqlcondition,int strExecEventFlag){
		boolean result;
		String action=null;
		try {
			if (strExecEventFlag==1){
				action=getTestData(testDataFilePathStatic, testComponentNameStatic,actionName,gblrecordsCounterStatic);
			}
			if(action==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the data sheet.", false);
				return false;
			}
			if(action.trim().equalsIgnoreCase("Update")){
				result=RRBSDBUpdate(sqltablename, strsqlcolumnname, strsqlcolumnvalue, strsqlcondition, strExecEventFlag);
			}else if(action.trim().equalsIgnoreCase("Delete")){
				result=RRBSDBDelete(sqltablename, strsqlcondition, strExecEventFlag);
			}else{
				ExtentTestManager.reportStepFail(driver, "Invalid Action Type described in Excel sheet - "+action+"", false);
				return false;
			}

		} catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver,"Error occured while executing the Common Precondition with RRBS query. Error description is : "+ e.getMessage() +".", false);
			return false;
		}
		return result;
	}

	public synchronized boolean RRBSDBDelete(String rrbstablename, String rrbscondition, int strExecEventFlag){
		String tablename = null;
		String condition = null;
		try{
			if(strExecEventFlag==1){
				tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbstablename,gblrecordsCounterStatic);
				condition=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbscondition,gblrecordsCounterStatic);
			}
			if(tablename==null || condition==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in test data sheet.", false);
				return false;
			}
			String check = "select * from "+tablename +" where "+condition;
			String query = "Delete from "+ tablename +" where "+ condition;
			ResultSet rs = rrbsstatement.executeQuery(check);
			int temp = 0;
			while(rs.next()){
				temp++;
			}
			if(temp > 0){
				rrbsstatement.execute(query); 
				ExtentTestManager.reportStepPass("RRBS Delete Query "+ query +" executed successfully");
				return true;
			}
			else{
				ExtentTestManager.reportStepPass("RRBS Delete Query "+ query +" has NO RECORDS available in DB");
				return true;
			}
		} catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver, "Error occured while executing the RRBS query.Error description is : "+ e.getMessage() +".", false);
			return false;
		}
	}

	public synchronized boolean WebRadioSelect(String getValueFromPOM, String strTestObject, String rdbOptions){
		try {
			selectByLocatorType(getValueFromPOM).click();
			ExtentTestManager.reportStepPass("Option '"+rdbOptions+"' is selected successfully from the Radio button '" + strTestObject + "'");		
			return true;
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Option '"+rdbOptions+"' is not Selected from the Radio button '" + strTestObject + "'", true); 	
			return false;
		}
	}

	public synchronized boolean javaScriptEnterText(String getValueFromPOM, String strTestObject, String strColumnName, int strExecEventFlag) throws Exception{
		String elementValue = null;
		try{
			
			String elementFromPOM=null;
			elementFromPOM=getValueFromPOM;
			int count=elementFromPOM.split("#").length;
			//log.info("JS POM Count : "+count);
			if(count==2){
				getValueFromPOM=elementFromPOM.split("#")[1];
				//log.info("changed getValueFromPOM is : "+getValueFromPOM);
			}else{
				//log.info("Not changed getValueFromPOM is : "+getValueFromPOM);
			}
			
			if(strExecEventFlag==1){
				elementValue = getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName, gblrecordsCounterStatic);
			}
			if(elementValue == null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementById('"+getValueFromPOM+"').value = '"+elementValue+"'");
			ExtentTestManager.reportStepPass("Text '"+elementValue+"' is entered successfully in the element "+strTestObject+" using JavaScript");	
			return true;
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Text '"+elementValue+"' is not entered in the element "+strTestObject+" using JavaScript", true);  
			return false;
		}
	}

	public synchronized boolean WebEditClickAndEnterText(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag ){
		String strData=null;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			selectByLocatorType(getValueFromPOM).click();
			selectByLocatorType(getValueFromPOM).clear();
			selectByLocatorType(getValueFromPOM).sendKeys(strData);
			ExtentTestManager.reportStepPass("Click action is done on the element "+strTestObject+" and text '"+strData+"' is entered successfully on the same element.");
			return true;	
		} catch (Exception e) { 	
			ExtentTestManager.reportStepFail(driver, "Click action is not done on the element "+strTestObject+" and text '"+strData+"' is not entered in Same.", true);
			return false;
		}
	}

	public synchronized boolean waitUntilDisappear(String getValueFromPOM, String strTestObject){
		boolean disappear =false;
		try{
			Thread.sleep(500);
			if(selectByLocatorType(getValueFromPOM).isDisplayed()){
				for(int i=0; i<90; i++){
					Thread.sleep(1000);
					if(!selectByLocatorType(getValueFromPOM).isDisplayed()){
						disappear = true;
						Thread.sleep(1000);
						ExtentTestManager.reportStepPass("'The Element '"+ strTestObject +"' is not appearing in the Page");
						break;
					}
				}
			}else{
				Thread.sleep(500);
				disappear = true;
				Thread.sleep(1000);
				ExtentTestManager.reportStepPass("'The Element '"+ strTestObject +"' is not displayed in the Page");
			}
		}catch(StaleElementReferenceException e){
			ExtentTestManager.reportStepPass("'The Element '"+ strTestObject +"' is not displayed in the Page");
			disappear= true;
		}catch(NoSuchElementException e){
			ExtentTestManager.reportStepPass("'The Element '"+ strTestObject +"' is not displayed in the Page");
			disappear= true;
		}
		catch(NullPointerException e){
			ExtentTestManager.reportStepPass("'The Element '"+ strTestObject +"' is not displayed in the Page");
			disappear= true;
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Exception occured in wait until disappear of an Element. Error message is : "+ e +"." , true);
			disappear= false;
		}
		return disappear;
	}
	
	public synchronized boolean WebElementDynamicStringVerify(String getValueFromPOM, String strPattern, int strExecEventFlag) throws Exception
	{
		boolean matchedStatus;
		String ExpectedPattern=null;
		try{
			if(strExecEventFlag==1){
				ExpectedPattern=getTestData(testDataFilePathStatic, testComponentNameStatic,strPattern,gblrecordsCounterStatic);
			}
			if(ExpectedPattern==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}

			String RetrievedMessage=selectByLocatorType(getValueFromPOM).getText();
			Pattern expPattern=Pattern.compile(ExpectedPattern);
			Matcher matchedPattern = expPattern.matcher(RetrievedMessage);
			matchedStatus=matchedPattern.find();
			if(matchedStatus)
			{	
				ExtentTestManager.reportStepPass("Retrieved pattern from the webElement Matched Successfully with the message : '"+matchedPattern.group(0)+"'");
				return true;
			}else{
				ExtentTestManager.reportStepFail(driver,"Error occured while matching the Expected pattern '"+ExpectedPattern+"' and Actual pattern '"+matchedPattern.group(0)+"'", true);
				return false;
			}
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while matching the Expected pattern-matchedPattern.find() "+e.getMessage(), true);
			return false;
		}

	}

	public synchronized boolean WebElementDynamicStringVerify(String getValueFromPOM, String strTestObject, String strPattern,int strExecEventFlag){
		boolean WebElementDynamicStringVerify=false;
		boolean matchedStatus=false;
		Matcher matchedPattern = null;
		String Pattern_String = null;
		String RetrievedMessage=null;
		try{
			if(strExecEventFlag==1){
				Pattern_String=getTestData(testDataFilePathStatic, testComponentNameStatic,strPattern,gblrecordsCounterStatic);
			}

			if(Pattern_String==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in test data sheet.", false);
				return false;
			}
			Pattern_String = Pattern_String.trim();
			RetrievedMessage=selectByLocatorType(getValueFromPOM).getText().trim();
			Pattern expPattern=Pattern.compile(Pattern_String);
			matchedPattern = expPattern.matcher(RetrievedMessage);
			matchedStatus=matchedPattern.find();
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while matching the Expected pattern-matchedPattern.find() "+e.getMessage(), true);
			WebElementDynamicStringVerify=false;
		}

		try{
			if(matchedStatus==true){
				log.info("Matched status pass:"+matchedStatus);
				ExtentTestManager.reportStepPass("The Expected pattern '"+Pattern_String+"' in the webElement '"+strTestObject+"' matches with the actual pattern '"+RetrievedMessage+"' successfully");
				WebElementDynamicStringVerify=true;
			}else if(matchedStatus==false){
				log.info("Matched status fail:"+matchedStatus);
				ExtentTestManager.reportStepFail(driver,"The Expected pattern '"+Pattern_String+"' in the webElement '"+strTestObject+"' does not match with the actual pattern '"+RetrievedMessage+"'", true);
				WebElementDynamicStringVerify=false;
			}
		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while finding the Pattern. Error description is  "+ e.getMessage() +".", true);
			log.info("WebElementDynamicStringVerify Error : " + e);
			WebElementDynamicStringVerify=false;
		}
		return WebElementDynamicStringVerify;
	}

	public synchronized boolean WebElementTextStoreDynamicValue(String getValueFromPOM, String strTestObject,String strColumnName, int strExecEventFlag ){
		String actualText="";
		String strData=null;
		boolean WebElementTextCompare=false;
		try{
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in test data sheet.", false);
				return false;
			}

			actualText = selectByLocatorType(getValueFromPOM).getText();
			if(actualText == null){
				actualText="";
				ExtentTestManager.reportStepPass("The Empty value '"+actualText+"' is stored in the '"+strTestObject+"' is stored in the Runtime variable '"+strData+"'");
				return false;
			}

			Runtimevalue.setProperty(strData, actualText);
			log.info("Value set to Runtime Property '"+strData+"' is => '"+Runtimevalue.getProperty(strData)+"'");
			ExtentTestManager.reportStepPass("The Dynamic value '"+actualText+"' of Element '"+strTestObject+"' is successfully stored in the Runtime variable '"+strData+"'");
			WebElementTextCompare=true;
		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while getting the text from the WebElement '"+strTestObject+"'and the error description is "+e.getMessage(), true);
			WebElementTextCompare=false;
		}
		return WebElementTextCompare;
	}

	public synchronized boolean SQLDBSelectFromEnv(String sqltablename, String strsqlcolumnname,String strsqlcondition,String strEnvVariableColumn,int strExecEventFlag){
		String query = null;
		String check;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String Expected_value = null;
		String envVariable=null;
		String Actual_Value = null;
		boolean functionStatus=false;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				envVariable=getTestData(testDataFilePathStatic, testComponentNameStatic,strEnvVariableColumn,gblrecordsCounterStatic);
			}
			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}

			Expected_value=Runtimevalue.getProperty(envVariable);
			if(Expected_value==null){
				ExtentTestManager.reportStepFail(driver,"Dynamic Variable '"+envVariable+"' has NO VALUE", false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;

			//Check for Record Available
			ResultSet rs_SQLServerCheck = stmt.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = stmt.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {

				log.info("Acual value is nULL");

				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the SQL Query "+query+" matches the expected value from ENV Variable '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the SQL Query "+query+" does not match with the expected value from ENV Variable '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else{
				ResultSet rs_SQLServer = stmt.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1).trim();
				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the SQL Query "+query+" matches the expected value from ENV Variable '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Actual_Value.equalsIgnoreCase(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the SQL Query "+query+" does not match with the expected value from ENV Variable '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in SQL query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean EShopSQLDBSelect(String sqltablename, String strsqlcolumnname,String strsqlcondition,String strExpectedvalue,int strExecEventFlag){
		boolean functionStatus=false;
		String query;
		String check;
		String Table_name;
		String Column_name;
		String SQL_condition;
		String Expected_value;
		String Actual_Value;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
				Expected_value=getTestData(testDataFilePathStatic, testComponentNameStatic, strExpectedvalue, gblrecordsCounterStatic);
			}else{
				Table_name=sqltablename;
				Column_name=strsqlcolumnname;
				SQL_condition=strsqlcondition;
				Expected_value=strExpectedvalue;
			}

			if(Table_name==null || Column_name==null || SQL_condition==null||Expected_value==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			String pattern = "^Count\\((.*)\\)";
			// Create a Pattern object
			  Pattern r = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
			// Now create matcher object.
			  Matcher m = r.matcher(Column_name);
			  if (m.find( )) {
				return ESHOPSQLDBCheckNoOfRowsExist(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue, strExecEventFlag);
			  }
			  
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;

			//Check for Record Available
			ResultSet rs_SQLServerCheck = EShopstmt.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in ESHOP DB",false);
				return false;
			}

			rs_SQLServerCheck = EShopstmt.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the ESHOP SQL Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the ESHOP SQL Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else{

				ResultSet rs_SQLServer = EShopstmt.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1).trim();

				if (Expected_value.contains("CURRENT_DATE")){
					String []ExpectedvalueWithFormat=Expected_value.split("#");
					String db_Date = Actual_Value.split(" ")[0];
					Actual_Value=db_Date;
					Date date=new Date();			
					SimpleDateFormat dateformat=new SimpleDateFormat(ExpectedvalueWithFormat[1]);
					Expected_value=dateformat.format(date);
				}

				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the ESHOP SQL Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Actual_Value.equalsIgnoreCase(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the ESHOP SQL Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values from ESHOP SQL query. Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean EShopSQLDBSelectFromEnv(String sqltablename, String strsqlcolumnname,String strsqlcondition,String strEnvVariableColumn,int strExecEventFlag){
		String query = null;
		String check;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String Expected_value = null;
		String envVariable=null;
		String Actual_Value = null;
		boolean functionStatus=false;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				envVariable=getTestData(testDataFilePathStatic, testComponentNameStatic,strEnvVariableColumn,gblrecordsCounterStatic);
			}

			log.info("Excel COlumn Name is : "+strEnvVariableColumn);
			log.info("Env name is : "+envVariable);

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}

			Expected_value=Runtimevalue.getProperty(envVariable);

			log.info("Expected_value from env is : "+Expected_value);

			if(Expected_value==null){
				ExtentTestManager.reportStepFail(driver,"Dynamic Variable '"+envVariable+"' has NO VALUE", false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;

			//Check for Record Available
			ResultSet rs_SQLServerCheck = EShopstmt.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the ESHOP SQL Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = EShopstmt.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the ESHOP SQL Query "+query+" matches the expected value from ENV Variable '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the ESHOP SQL Query "+query+" does not match with the expected value from ENV Variable '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else{
				ResultSet rs_SQLServer = EShopstmt.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1).trim();
				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the ESHOP SQL Query "+query+" matches the expected value from ENV Variable '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Actual_Value.equalsIgnoreCase(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the ESHOP SQL Query "+query+" does not match with the expected value from ENV Variable '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in SQL query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean EshopSQLDBCloseConnection(){
		try {
			EshopConnection.close(); 
			ExtentTestManager.reportStepPass("ESHOP SQL DB Connection disconnected successfully.");
			return true;
		} catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver,"Error occured while closing the ESHOP SQL DB connection. Error description is : "+ e.getMessage() +".", false);
			return false;
		}
	}

	public synchronized boolean JSWebElementValueCompare(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag ){
		String actualResult=null;
		String strData=null;
		boolean WebElementValueCompare=false;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the datasheet.", false);
				return false;
			}
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			actualResult = (String) executor.executeScript("return arguments[0].getAttribute('value');", selectByLocatorType(getValueFromPOM));
			if((actualResult.trim()).equalsIgnoreCase(strData.trim())){
				WebElementValueCompare=true;
				ExtentTestManager.reportStepPass("'"+strTestObject+"'s  actual text value '" + actualResult + "' matches the expected value '" + strData + "'");
			}else{
				WebElementValueCompare=false;
				ExtentTestManager.reportStepFail(driver,"'"+strTestObject+"'s  actual text value '" + actualResult + "' does not match the expected value '" + strData + "'", true);
			}
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"'"+strTestObject+"'s  actual text value '" + actualResult + "' matches the expected value '" + strData + "'. Exception is "+e.getLocalizedMessage(), true);
			WebElementValueCompare=false;
		}
		return WebElementValueCompare;
	}

	public synchronized boolean SQLDBFutureDateCompare(String sqltablename, String strsqlcolumnname,String strsqlcondition,String Date_Format,String Days_to_add,int strExecEventFlag){
		boolean functionStatus= false;
		String query = null;  
		String check = null; 
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String Expected_value = null;
		String Actual_Value = null;
		String Current_Date=null;
		String expected_db_Date = null;
		String daystoadd = null;


		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
				daystoadd=getTestData(testDataFilePathStatic, testComponentNameStatic, Days_to_add, gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null || daystoadd==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			int Add_Days = Integer.parseInt(daystoadd);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			Calendar expdate = Calendar.getInstance();
			expdate.setTime(date);
			expdate.add(Calendar.DATE, Add_Days);
			Current_Date = dateFormat.format(expdate.getTime());
			Expected_value = Current_Date.trim();

			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;

			ResultSet rs_SQLServerCheck= stmt.executeQuery(check);

			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = stmt.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the RRBS Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the RRBS Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else if(!(rs_SQLServerCheck.wasNull())){

				ResultSet rs_SQLServer= stmt.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1);

				String db_Date = Actual_Value.split(" ")[0];
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				Date dateToChange = dateFormat1.parse(db_Date);
				SimpleDateFormat finalDateFormat = new SimpleDateFormat(Date_Format);
				expected_db_Date = finalDateFormat.format(dateToChange);

				if(expected_db_Date.equals(Expected_value)){
					ExtentTestManager.reportStepPass("Actual Date '"+Actual_Value+"' for the SQL Query "+query+" matches the future expected Date '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(expected_db_Date.equals(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual Date '"+Actual_Value+"' for the SQL Query "+query+" does not match the future expected Date '"+Expected_value+"'",false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in SQL query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean WebListSelectedValue(String getValueFromPOM, String strTestObject, String strColumnName, int strExecEventFlag) throws Exception{
		String strData = null;
		boolean WebListSelectedValue = false;
		String selectedValue = null;
		try{
			if(strExecEventFlag==1){
				strData = getTestData(testDataFilePathStatic, testComponentNameStatic, strColumnName, gblrecordsCounterStatic);
			}	
			if(strData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}
			selectedValue = new Select(selectByLocatorType(getValueFromPOM)).getFirstSelectedOption().getText();
			if(selectedValue.trim().equalsIgnoreCase(strData.trim())){
				ExtentTestManager.reportStepPass(""+strTestObject +"'s selected dropdown value '"+selectedValue + "' matches with the expected value '"+strData+"'");
				WebListSelectedValue = true;
			}else{
				ExtentTestManager.reportStepFail(driver,""+strTestObject +"'s selected dropdown value '"+selectedValue + "' does not matches with the Expected Value '"+strData+"'"  , true); 
				WebListSelectedValue=false;
			}
		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Error occured while verifying the dropdown value of object "+strTestObject +"'", true); 
			WebListSelectedValue=false;
		}
		return WebListSelectedValue;
	}

	public synchronized boolean DBCommonPreCondition(String dbType, String actionName,String sqltablename, String strsqlcolumnname,String strsqlcolumnvalue,String strsqlcondition,int strExecEventFlag){

		boolean status= false;
		String action=null;
		String Database=null;
		try {

			if(strExecEventFlag==1){
				Database = getTestData(testDataFilePathStatic, testComponentNameStatic, dbType, gblrecordsCounterStatic);
				action = getTestData(testDataFilePathStatic, testComponentNameStatic, actionName, gblrecordsCounterStatic);
			}	
			if(Database==null || action==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			if(Database.trim().equalsIgnoreCase("SQL")){
				if(action.trim().equalsIgnoreCase("Update")){
					status=SQLDBUpdate(sqltablename, strsqlcolumnname, strsqlcolumnvalue, strsqlcondition, strExecEventFlag);
				}else if(action.trim().equalsIgnoreCase("Delete")){
					status=SQLDBDelete(sqltablename, strsqlcondition, strExecEventFlag);
				}else{
					ExtentTestManager.reportStepFail(driver,"Invalid Action Type is described in Excel sheet : "+action+" for SQL", false);
					status=false;
				}

			}else if(Database.trim().equalsIgnoreCase("RRBS")) {

				if(action.trim().equalsIgnoreCase("Update")){
					status=RRBSDBUpdate(sqltablename, strsqlcolumnname, strsqlcolumnvalue, strsqlcondition, strExecEventFlag);
				}else if(action.trim().equalsIgnoreCase("Delete")){
					status=RRBSDBDelete(sqltablename, strsqlcondition, strExecEventFlag);
				}else{
					ExtentTestManager.reportStepFail(driver,"Invalid Action Type is described in Excel sheet : "+action+" for RRBS", false);
					status=false;
				}
			}else{
				ExtentTestManager.reportStepFail(driver,"Invalid Database is described in Excel sheet : "+action+"", false);
				status=false;
			}

		} catch (Exception e) { 
			status=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while executing the DB Common PreCondition. Error description is : "+ e.getLocalizedMessage()+".", true);
			log.info("RRBSDBUpdate Error : " + e);
		}
		return status;
	}

	public synchronized boolean SQLDBCommonPreCondition(String actionName,String sqltablename, String strsqlcolumnname,String strsqlcolumnvalue,String strsqlcondition,int strExecEventFlag){
		boolean elementStatus= false;
		String action=null;
		try {
			if(strExecEventFlag==1){
				action = getTestData(testDataFilePathStatic, testComponentNameStatic, actionName, gblrecordsCounterStatic);
			}	
			if( action==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			if(action.trim().equalsIgnoreCase("Update")){
				elementStatus=SQLDBUpdate(sqltablename, strsqlcolumnname, strsqlcolumnvalue, strsqlcondition, strExecEventFlag);
			}else if(action.trim().equalsIgnoreCase("Delete")){
				elementStatus=SQLDBDelete(sqltablename, strsqlcondition, strExecEventFlag);
			}else{
				log.info("Invalid Action item from Excel");
				ExtentTestManager.reportStepFail(driver,"Invalid Action Type described in Excel sheet : "+action+"", false);
			}

		} catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver,"Error occured while executing the SQL DB Common PreCondition. Error description is : "+ e.getLocalizedMessage() +".", false);
			log.info("RRBSDBUpdate Error : " + e);
		}
		return elementStatus;
	}

	public synchronized boolean deleteAllFileInPath(String filePath, int strExecEventFlag) throws Exception{
		boolean functionStatus = true;
		String path = null;
		int flag = 0;
		try{

			if(strExecEventFlag == 1){
				path = property.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic, filePath, gblrecordsCounterStatic));
			}
			if(path==null ){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}
			File directory = new File("//\\" +path);
			for(File listOfFiles : directory.listFiles()){
				if(true){
					listOfFiles.delete();
					flag = 1;
				}
			}

		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occurred, while deleting the file in the Given Path"+path+"", false);
			functionStatus = false;
		}

		if(flag == 1){
			ExtentTestManager.reportStepPass("All files in the given folder path '"+path+"' are deleted sucessfully.");
		} else {
			ExtentTestManager.reportStepPass("No file available in the given folder path '"+path+"' to delete.");
		}

		return functionStatus;
	}

	public synchronized boolean WinserviceStartStopUsingSC(String strServerIP,String strActionToDo, String strWinserviceName,int strExecEventFlag){
		boolean result= false;
		String serverIP=null;
		String actionToDo=null;
		String winserviceName=null;
		String state=null;
		Process p;

		try {

			if (strExecEventFlag==1){
				serverIP=getTestData(testDataFilePathStatic, testComponentNameStatic, strServerIP, gblrecordsCounterStatic);
				actionToDo=getTestData(testDataFilePathStatic, testComponentNameStatic, strActionToDo, gblrecordsCounterStatic);
				winserviceName=getTestData(testDataFilePathStatic, testComponentNameStatic, strWinserviceName, gblrecordsCounterStatic);
			}
			if(serverIP==null ||actionToDo==null ||winserviceName==null ){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the data sheet.", false);
				return false;
			}

			try{
				p=Runtime.getRuntime().exec("sc \\\\"+serverIP+" query "+winserviceName+"");
			}catch(Exception e){
				ExtentTestManager.reportStepFail(driver, "Error occured while Query the state of winservice. Exception is : "+ e.getLocalizedMessage()+".", false);
				return false;
			}

			BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
			String line=reader.readLine();
			while(line!=null) { 
				if(line.trim().startsWith("STATE")){
					if (line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("1")){
						log.info("Stopped");
						state="STOPPED";
					}else if(line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("2")){
						log.info("Startting....");
						state="STARTING";
					}else if (line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("3")){
						log.info("Stopping....");
						state="STOPPING";
					}else if (line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("4")){
						log.info("Running");
						state="RUNNING";
					}
				}
				line=reader.readLine(); 
			} 
			//Start the Winservice
			if(actionToDo.trim().equalsIgnoreCase("START")){
				if(state.equalsIgnoreCase("STOPPED")){
					try{
						p=Runtime.getRuntime().exec("sc \\\\"+serverIP+" start "+winserviceName+"");
						ExtentTestManager.reportStepPass("SC Command 'sc \\\\"+serverIP+" start "+winserviceName+"' exceuted successfully.");

					}catch(Exception e){
						ExtentTestManager.reportStepFail(driver,"Error occured while starting the winservice. Exception is : "+ e.getLocalizedMessage() +".", false);
						return false;
					}

					while(line!=null) { 

						if(line.trim().startsWith("STATE")){
							if (line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("1")){
								log.info("Stopped");
							}else if(line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("2")){
								log.info("Startting....");
							}else if (line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("3")){
								log.info("Stopping....");
							}else if (line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("4")){
								log.info("Running");
							}
						}
						line=reader.readLine(); 
					}
					ExtentTestManager.reportStepPass("Winservice '"+winserviceName+"' in Server IP '"+serverIP+"' is Started successfully.");
					Thread.sleep(4000);
					result=true;
				}else if(state.equalsIgnoreCase("STOPPING")){
					log.info("Winservice is Stopping state. Wait and starting again");
					Thread.sleep(5000);
					p=Runtime.getRuntime().exec("sc \\\\"+serverIP+" start "+winserviceName+"");

					while(line!=null) { 
						if(line.trim().startsWith("STATE")){
							if (line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("1")){
								log.info("Stopped");
								state="STOPPED";
							}else if(line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("2")){
								log.info("Startting....");
								state="STARTING";
							}else if (line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("3")){
								log.info("Stopping....");
								state="STOPPING";
							}else if (line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("4")){
								log.info("Running");
								state="RUNNING";
							}
						}
						line=reader.readLine(); 
					}

					ExtentTestManager.reportStepPass("Winservice '"+winserviceName+"' in Server IP '"+serverIP+"' is Started successfully.");
					Thread.sleep(4000);
					result=true;

				}else if(state.equalsIgnoreCase("STARTING")){

					log.info("Winservice is starting state. Cannot push start command again");
					ExtentTestManager.reportStepPass("Winservice '"+winserviceName+"' in Server IP '"+serverIP+"' is already in Starting State");
					result=true;

				}else if(state.equalsIgnoreCase("RUNNING")){

					log.info("Winservice is already in Running state. Cannot push start command again");
					ExtentTestManager.reportStepPass("Winservice '"+winserviceName+"' in Server IP '"+serverIP+"' is already in Running State");
					result=true;

				}
				//Start the Winservice
			}else if(actionToDo.trim().equalsIgnoreCase("STOP")){
				if(state.equalsIgnoreCase("RUNNING")){
					log.info("Stopping the service...");
					try{
						p=Runtime.getRuntime().exec("sc \\\\"+serverIP+" stop "+winserviceName+"");
						ExtentTestManager.reportStepPass("SC Command 'sc \\\\"+serverIP+" stop "+winserviceName+"' exceuted successfully.");

					}catch(Exception e){
						ExtentTestManager.reportStepFail(driver, "Error occured while stopping the winservice. Exception is : "+ e.getLocalizedMessage() +".", false);
						return false;
					}

					while(line!=null) { 
						if(line.trim().startsWith("STATE")){
							if (line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("1")){
								log.info("Stopped");
							}else if(line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("2")){
								log.info("Startting....");
							}else if (line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("3")){
								log.info("Stopping....");
							}else if (line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("4")){
								log.info("Running");
							}
						}
						line=reader.readLine(); 
					}

					ExtentTestManager.reportStepPass("Winservice '"+winserviceName+"' in Server IP '"+serverIP+"' is Stopped successfully");
					Thread.sleep(4000);
					result=true;
				}else if(state.equalsIgnoreCase("STARTING")){
					log.info("Winservice is Starting state. Wait and stopping the service");
					Thread.sleep(3000);
					p=Runtime.getRuntime().exec("sc \\\\"+serverIP+" stop "+winserviceName+"");

					while(line!=null) { 

						if(line.trim().startsWith("STATE")){
							if (line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("1")){
								log.info("Stopped");
							}else if(line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("2")){
								log.info("Startting....");
							}else if (line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("3")){
								log.info("Stopping....");
							}else if (line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim().equals("4")){
								log.info("Running");
							}
						}
						line=reader.readLine(); 
					}

					ExtentTestManager.reportStepPass("Winservice '"+winserviceName+"' in Server IP '"+serverIP+"' is Stopped successfully");
					Thread.sleep(4000);
					result=true;

				}else if(state.equalsIgnoreCase("STOPPING")){
					log.info("Winservice is Stopping state. Cannot push Stop command again");
					ExtentTestManager.reportStepPass("Winservice '"+winserviceName+"' in Server IP '"+serverIP+"' is already in Stopping State.");
					result=true;
				}else if(state.equalsIgnoreCase("STOPPED")){
					log.info("Winservice is already in Stopped state. Cannot push Stop command again");
					ExtentTestManager.reportStepPass("Winservice '"+winserviceName+"' in Server IP '"+serverIP+"' is already in Stooped State.");
					result=true;
				}
			}

		} catch(Exception e) { 
			result=false;
			ExtentTestManager.reportStepFail(driver, "Error occured in Start-Stop Winservice function. Error description is : "+ e.getLocalizedMessage() +".", false);
		}
		return result;
	}

	public synchronized boolean WaitUntilPatternAppearsInLog(String filePath,String fileNameValue,String strLineContains,String strPatterToCheck,String envVariableName ,String strdateFormat,int strExecEventFlag){
		boolean result= false;
		String path = null;
		File[] listOfFile = null;
		String fileName = null;
		String fileNameFromExcel=null;
		Scanner in = null;
		boolean found=false;
		String lineContains=null;
		String patterToCheck=null;
		String dateFormat=null;
		String envVariable=null;

		try{
			if(strExecEventFlag == 1){
				path = property.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic, filePath, gblrecordsCounterStatic));
				fileNameFromExcel= getTestData(testDataFilePathStatic, testComponentNameStatic,fileNameValue, gblrecordsCounterStatic);
				lineContains= getTestData(testDataFilePathStatic, testComponentNameStatic, strLineContains, gblrecordsCounterStatic);
				patterToCheck= getTestData(testDataFilePathStatic, testComponentNameStatic, strPatterToCheck, gblrecordsCounterStatic);
				dateFormat= getTestData(testDataFilePathStatic, testComponentNameStatic, strdateFormat, gblrecordsCounterStatic);
				envVariable= getTestData(testDataFilePathStatic, testComponentNameStatic, envVariableName, gblrecordsCounterStatic);
			}

			if(path==null ||fileNameFromExcel==null ||lineContains==null||patterToCheck==null||dateFormat==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in test data sheet.", false);
				return false;
			}
			if(fileNameFromExcel.trim().equalsIgnoreCase("DateFormat"))
				fileNameFromExcel=dateFormatter(dateFormat,".txt");
			log.info("File name to search is : "+fileNameFromExcel);
			File directory = new File("//\\" +path);

			boolean fileexist=false;
			int fileAppeartime=0;
			while(fileAppeartime<30){
				Thread.sleep(1000);
				listOfFile = directory.listFiles();
				if(listOfFile.length != 0){
					log.info("Directory has files");
					fileexist=true;
					break;
				}else{
					log.info("No File is available in directory. looping again with 60 secs");
				}
				log.info("No File is available in directory with 60 secs");
				fileAppeartime++;
			}

			if(fileexist){
				log.info("Files avialble in the directiory");
			}else{
				log.info("No File is available in directory for 60secs");
				ExtentTestManager.reportStepFail(driver,"No file is available in given directory "+path+"" , false);
				return false;
			}

			boolean foundstatus=false;
			int time=0;
			while(time<30){
				Thread.sleep(1000);	
				listOfFile = directory.listFiles();
				for(int i = 0; i<listOfFile.length; i++){
					if(listOfFile[i].isFile()){
						fileName = listOfFile[i].getName();
						log.info("Found a file and name is : "+fileName);
						if(fileName.equals(fileNameFromExcel)){
							log.info("FileName exact match : "+fileName);
							fileName=fileNameFromExcel;
							foundstatus=true;
							break;
						}
					}
				}

				if(foundstatus){
					log.info("FileName exact match is found. Braking the loop");
					break;
				}else{
					log.info("FileName not found in path. Continue the loop");
				}
				time++;
			}

			if(fileName == null){
				ExtentTestManager.reportStepFail(driver,"'"+fileNameFromExcel+"' file is not available in the directory" , false);
				log.info("Search File is not available in directory");
				return false;
			}

			if(!(fileName.equals(fileNameFromExcel))){
				ExtentTestManager.reportStepFail(driver,"'"+fileNameFromExcel+"' file is not available in the directory" , false);
				log.info("Search File is not available in directory");
				return false;
			}

			File file =new File("//\\" +path +fileName);
			log.info(file.getAbsolutePath()+" || "+file.getName());
			int i=0;
			while(i<60){
				Thread.sleep(1000);
				try {
					in = new Scanner(file);
					while(in.hasNext())	{
						String line=in.nextLine();
						if(line.contains(lineContains)){
							log.info("Line contains Match found. Line -> "+line);
							Pattern p = Pattern.compile(patterToCheck);
							Matcher m = p.matcher(line);

							while (m.find()){
								found=true;
								log.info("Pattern Match found in the Line!");
								log.info("Match is : "+m.group(0));
								ExtentTestManager.reportStepPass("Line containing the word '"+lineContains+"' has the Matched Expected Pattern '"+m.group(0) +"' in the Log");
								if(!(envVariable.trim().equalsIgnoreCase("NA"))){
									log.info("Storing the Pattern Matched in the Env Variable '"+envVariable+"'");
									Runtimevalue.setProperty(envVariable, m.group(0));
									ExtentTestManager.reportStepPass("The Dynamic Value '"+m.group(0)+"' is successfully stored in the Runtime Varaible '"+envVariable+"'.");
								}
								break;
							}
							if(found){
								log.info("Pattern Match found breaking the HasNext Loop");
								break;
							}else{
								log.info("Pattern Match not found. Checking teh next line that contains expected word");
							}
						}
					}
					if(found){
						log.info("Match found, Breaking the Time Loop");
						result=true;
						break;
					}else{
						log.info("Match not found. Continue the Loop...");
					}

				} catch (FileNotFoundException e) {
					log.info("FileNotFoundException occured match founder... :"+e);
				}catch (Exception e) {
					log.info("Excption occured match founder... : "+e);
					ExtentTestManager.reportStepFail(driver, "Error occured while finding the pattern from Winservice Log. Error description is : "+ e.getLocalizedMessage() +".", false);
					//e.printStackTrace();
				}

				i++;
			}

			if(result){
				ExtentTestManager.reportStepPass("Log file '"+fileNameFromExcel+"' from Path '"+path+"' contains the Expected Log Pattern '"+patterToCheck+"'");
			}else{
				ExtentTestManager.reportStepFail(driver,"Log file '"+fileNameFromExcel+"' from Path '"+path+"' does not contains the Expected Log Pattern '"+patterToCheck+"' within 60 secs", false);
			}

		} catch(Exception e) { 
			result=false;
			ExtentTestManager.reportStepFail(driver,"Error occured in the WinserviceWaitUntilPatternAppears. Error description is : "+ e.getLocalizedMessage() +".", false);
		}
		return result;
	}

	public static String dateFormatter(String format, String fileType){

		String expectedDate=null;

		try{
			if(format.trim().equalsIgnoreCase("ddMMyyyy") || (format.trim().equalsIgnoreCase("dMyyyy")) ){

				DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
				Date date = new Date();

				dateFormat = new SimpleDateFormat("dd");
				String checkDate=dateFormat.format(date);
				int checkDateInt=Integer.parseInt(checkDate);
				expectedDate=String.valueOf(checkDateInt);

				dateFormat = new SimpleDateFormat("MM");
				String checkMonth=dateFormat.format(date);
				int checkDMonthInt=Integer.parseInt(checkMonth);
				expectedDate=expectedDate+String.valueOf(checkDMonthInt);

				dateFormat = new SimpleDateFormat("yyyy");
				String checkYear=dateFormat.format(date);
				expectedDate=expectedDate+checkYear+fileType;

				log.info("Needed Date is : "+expectedDate);
			}

		} catch(Exception e) { 
			log.info("Error in date formater : " + e);
		}
		return expectedDate;
	}

	public synchronized boolean DBCommonPostCondition(String dbType, String strformatType,String sqltablename, String strsqlcolumnname,String strsqlcondition,String strExpectedvalue,String Days_to_add,int strExecEventFlag){
		boolean status= false;
		String formatType=null;
		String Database=null;
		try {

			if (strExecEventFlag==1){
				Database=getTestData(testDataFilePathStatic, testComponentNameStatic, dbType, gblrecordsCounterStatic);
				formatType=getTestData(testDataFilePathStatic, testComponentNameStatic, strformatType, gblrecordsCounterStatic);
			}
			if(Database==null || formatType==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			if(Database.trim().equalsIgnoreCase("SQL")){

				if(formatType.trim().equalsIgnoreCase("Normal")){
					status= SQLDBSelect(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue, strExecEventFlag);
				}else if(formatType.trim().equalsIgnoreCase("Date")){
					status= SQLDBDateFormatCompare(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue, strExecEventFlag);
				}else if(formatType.trim().equalsIgnoreCase("FutureDate")){
					status= SQLDBFutureDateCompare(sqltablename, strsqlcolumnname, strsqlcondition, "dd/MM/yyyy", Days_to_add, strExecEventFlag);
				}else if(formatType.trim().equalsIgnoreCase("EnvVariable")){
					status= SQLDBSelectFromEnv(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue, strExecEventFlag);
				}else{
					log.info("Invalid Action item from Excel");
					ExtentTestManager.reportStepFail(driver, "Invalid Action Type described in Excel sheet for SQL: "+formatType+"", false);
				}

			}else if(Database.trim().equalsIgnoreCase("RRBS")) {

				if(formatType.trim().equalsIgnoreCase("Normal")){
					status= RRBSDBSelect(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue, strExecEventFlag);
				}else if(formatType.trim().equalsIgnoreCase("Date")){
					status= RRBSDBDateCompare(sqltablename, strsqlcolumnname, strsqlcondition, "dd/MM/yyyy", strExecEventFlag);
				}else if(formatType.trim().equalsIgnoreCase("FutureDate")){
					status= RRBSDBFutureDateCompare(sqltablename, strsqlcolumnname, strsqlcondition, "dd/MM/yyyy", Days_to_add, strExecEventFlag);
				}else{
					log.info("Invalid Action item from Excel");
					ExtentTestManager.reportStepFail(driver, "Invalid Action Type described in Excel sheet for RRBS: "+formatType+"", false);
				}

			}else{
				log.info("Invalid Action item from Excel");
				ExtentTestManager.reportStepFail(driver, "Invalid Database Type described in Excel sheet: "+Database+"", false);
				status=false;
			}

		} catch (Exception e) { 
			status=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while executing the SQL query.Error description is : "+ e.getLocalizedMessage() +".", true);
			log.info("RRBSDBUpdate Error : " + e);
		}
		return status;
	}

	public synchronized boolean SQLDBDateFormatCompare(String sqltablename, String strsqlcolumnname,String strsqlcondition,String dateFormatFromExcel,int strExecEventFlag){
		boolean functionStatus= false;
		String query = null;  
		String check = null; 
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String Expected_value = null;
		String Actual_Value = null;
		String Current_Date=null;
		String expected_db_Date = null;
		String dateFormatToConvert = null;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
				dateFormatToConvert=getTestData(testDataFilePathStatic, testComponentNameStatic, dateFormatFromExcel, gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null || dateFormatToConvert==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			DateFormat dateFormat = new SimpleDateFormat(dateFormatToConvert);
			Date date = new Date();
			Current_Date = dateFormat.format(date);
			Expected_value = Current_Date.trim();

			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;

			ResultSet rs_SQLServerCheck= stmt.executeQuery(check);

			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = stmt.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the RRBS Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the RRBS Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else if(!(rs_SQLServerCheck.wasNull())){

				ResultSet rs_SQLServer= stmt.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1);

				String db_Date = Actual_Value.split(" ")[0];
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				Date dateToChange = dateFormat1.parse(db_Date);
				SimpleDateFormat finalDateFormat = new SimpleDateFormat(dateFormatToConvert);
				expected_db_Date = finalDateFormat.format(dateToChange);

				if(expected_db_Date.equals(Expected_value)){
					ExtentTestManager.reportStepPass("Actual Date '"+Actual_Value+"' for the SQL Query "+query+" matches the expected Date '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(expected_db_Date.equals(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual Date '"+Actual_Value+"' for the SQL Query "+query+" does not match the expected Date '"+Expected_value+"'",false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in SQL query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean RRBSDBDateCompare(String sqltablename, String strsqlcolumnname,String strsqlcondition,String Date_Format,int strExecEventFlag){
		boolean functionStatus= false;
		String query = null;  
		String check = null; 
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String Expected_value = null;
		String Actual_Value = null;
		String Current_Date=null;
		String expected_db_Date = null;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			Current_Date = dateFormat.format(date);
			Expected_value = Current_Date.trim();

			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition;

			ResultSet rs_SQLServerCheck= rrbsstatement.executeQuery(check);

			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = rrbsstatement.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the RRBS Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the RRBS Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else if(!(rs_SQLServerCheck.wasNull())){

				ResultSet rs_SQLServer= rrbsstatement.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1);

				String db_Date = Actual_Value.split(" ")[0];
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				Date dateToChange = dateFormat1.parse(db_Date);
				SimpleDateFormat finalDateFormat = new SimpleDateFormat(Date_Format);
				expected_db_Date = finalDateFormat.format(dateToChange);

				if(expected_db_Date.equals(Expected_value)){
					ExtentTestManager.reportStepPass("Actual Date '"+Actual_Value+"' for the RRBS Query "+query+" matches the expected Date '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(expected_db_Date.equals(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual Date '"+Actual_Value+"' for the RRBS Query "+query+" does not match the expected Date '"+Expected_value+"'",false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in SQL query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean RRBSDBFutureDateCompare(String sqltablename, String strsqlcolumnname,String strsqlcondition,String Date_Format,String Days_to_add,int strExecEventFlag){
		boolean functionStatus= false;
		String query = null;  
		String check = null; 
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String Expected_value = null;
		String Actual_Value = null;
		String Current_Date=null;
		String expected_db_Date = null;
		String daystoadd = null;


		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
				daystoadd=getTestData(testDataFilePathStatic, testComponentNameStatic, Days_to_add, gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null || daystoadd==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			int Add_Days = Integer.parseInt(daystoadd);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			Calendar expdate = Calendar.getInstance();
			expdate.setTime(date);
			expdate.add(Calendar.DATE, Add_Days);
			Current_Date = dateFormat.format(expdate.getTime());
			Expected_value = Current_Date.trim();

			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";

			ResultSet rs_SQLServerCheck= rrbsstatement.executeQuery(check);

			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = rrbsstatement.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the RRBS Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the RRBS Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else if(!(rs_SQLServerCheck.wasNull())){

				ResultSet rs_SQLServer= rrbsstatement.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1);

				String db_Date = Actual_Value.split(" ")[0];
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				Date dateToChange = dateFormat1.parse(db_Date);
				SimpleDateFormat finalDateFormat = new SimpleDateFormat(Date_Format);
				expected_db_Date = finalDateFormat.format(dateToChange);

				if(expected_db_Date.equals(Expected_value)){
					ExtentTestManager.reportStepPass("Actual Date '"+Actual_Value+"' for the RRBS Query "+query+" matches the future expected Date '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(expected_db_Date.equals(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual Date '"+Actual_Value+"' for the RRBS Query "+query+" does not match the future expected Date '"+Expected_value+"'",false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in SQL query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean WeblistNumbersVerify(String getValueFromPOM, String strTestObject, String totalNumbersColumn,int itemscount_not_consider,int strExecEventFlag){

		boolean WeblistSQLDBitemsverify= false;
		String strData=null;
		int totalNumber;

		String[] weblistvalues = null;
		
		try{
		if(strExecEventFlag==1){
			strData=getTestData(testDataFilePathStatic, testComponentNameStatic,totalNumbersColumn,gblrecordsCounterStatic);
		}
		if(strData==null){
			ExtentTestManager.reportStepFail(driver, "Required details are not provided in the datasheet.", false);
			return false;
		}

		totalNumber=Integer.parseInt(strData);

		

			Select se = new Select(selectByLocatorType(getValueFromPOM));
			List<WebElement> options = se.getOptions();

			//if you want to get all elements text into array list
			List<String> all_elements_text=new ArrayList<String>();

			for(int j=0; j<options.size(); j++){

				//loading text of each element in to array all_elements_text
				all_elements_text.add(options.get(j).getText());

			}

			weblistvalues = (String[]) all_elements_text.toArray(new String[all_elements_text.size()]);

			log.info("Int length is : "+totalNumbersColumn);
			log.info("weblistvalues length is : "+weblistvalues.length);

			if(totalNumber == weblistvalues.length - itemscount_not_consider)
			{
				if((weblistvalues.length - itemscount_not_consider)==0){

					log.info("No value present in DB as well list");
					ExtentTestManager.reportStepPass("No Dropdown Value is Present in the Dropdown '"+strTestObject+"' and the corresponding Table.");
					WeblistSQLDBitemsverify=true;

				}else{
					for (int i=0; i<(weblistvalues.length - itemscount_not_consider); i++){

						if (weblistvalues[i+1].equals(Integer.toString(i+1))){
							ExtentTestManager.reportStepPass("The actual value '"+ weblistvalues[i+1] +"' in the dropdown '"+ strTestObject +"' matches the expected value '"+(i+1)+"'.");
							WeblistSQLDBitemsverify=true;
						} else{
							ExtentTestManager.reportStepFail(driver,"The actual value '"+ weblistvalues[i+1] +"' in the dropdown '"+ strTestObject +"' doesn't matches the expected value '"+(i+1)+"'.", true);
							WeblistSQLDBitemsverify=false;
						}
					}
				}
			} else {
				ExtentTestManager.reportStepFail(driver, "The number of items present in the dropdown '"+ strTestObject +"' doesn't matches with the given number of items '"+ totalNumber +"'.", true);
				WeblistSQLDBitemsverify=false;
			}

		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while comparing the values. Error description is : "+ e.getLocalizedMessage() +".", true);
			WeblistSQLDBitemsverify=false;
		}
		return WeblistSQLDBitemsverify;
	}

	public synchronized boolean WebElementValueCompare(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag ){

		String actualResult=null;
		String strData=null;
		boolean WebElementValueCompare=false;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the datasheet.", false);
				return false;
			}

			actualResult = selectByLocatorType(getValueFromPOM).getAttribute("value");

			if((actualResult.trim()).equalsIgnoreCase(strData.trim())){
				WebElementValueCompare=true;
				ExtentTestManager.reportStepPass("'"+strTestObject+"'  Actual Value '" + actualResult + "' matches the Expected value '" + strData + "'");
			}else{
				WebElementValueCompare=false;
				ExtentTestManager.reportStepFail(driver,"'"+strTestObject+"'  Actual Value '" + actualResult + "' does not match the Expected Value '" + strData + "'", true);
			}
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"'"+strTestObject+"' Actual Value '" + actualResult + "' does not match the Expected Value '" + strData + "'", true);
			WebElementValueCompare=false;
		}
		return WebElementValueCompare;
	}

	public synchronized boolean WebElementEnterCurrentDate(String getValueFromPOM,  String strTestObject,String strdateFormatInDataSheet,String envVariable, int strExecEventFlag) throws Exception{
		boolean functionStatus= false;
		String strDateFormat=null;
		String currentDate=null;
		String envVariableName=null;
		try {
			
			String elementFromPOM=null;
			elementFromPOM=getValueFromPOM;
			int count=elementFromPOM.split("#").length;
			//log.info("JS POM Count : "+count);
			if(count==2){
				getValueFromPOM=elementFromPOM.split("#")[1];
				//log.info("changed getValueFromPOM is : "+getValueFromPOM);
			}else{
				//log.info("Not changed getValueFromPOM is : "+getValueFromPOM);
			}
			
			if(strExecEventFlag==1){
				strDateFormat=getTestData(testDataFilePathStatic, testComponentNameStatic,strdateFormatInDataSheet,gblrecordsCounterStatic);
				envVariableName=getTestData(testDataFilePathStatic, testComponentNameStatic,envVariable,gblrecordsCounterStatic);
			}else{
				strDateFormat=strdateFormatInDataSheet;
				envVariableName=envVariable;
			}

			SimpleDateFormat dateformat = new SimpleDateFormat(strDateFormat);
			Date date = new Date();
			currentDate = dateformat.format(date);

			if(!(envVariableName.equalsIgnoreCase("NA"))){
				Runtimevalue.setProperty(envVariableName, currentDate);
				ExtentTestManager.reportStepPass("Dynamic Date value '"+currentDate+"' is stored in the Runtime Variable  '"+envVariableName+"'");
			}

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementById('"+getValueFromPOM+"').value = '"+currentDate+"'");
			ExtentTestManager.reportStepPass("Current Date '"+currentDate+"' is entered in the '"+strTestObject+"'");
			functionStatus=true;	
		} catch (Exception e) { 	
			ExtentTestManager.reportStepFail(driver, "Current Date '"+currentDate+"' is not entered in the '"+strTestObject+"'", true);
			functionStatus=false;
		}
		return functionStatus;

	}

	public synchronized boolean WebElementFutureMonthValueComapre(String getValueFromPOM,  String strTestObject,String strdateFormatInDataSheet,String monthsToAddInSheet,String envVariable,int strExecEventFlag){

		boolean functionStatus= false;
		String strDateFormat=null;
		String monthsToAdd=null;
		String funtureMonthDate=null;
		String actualResult=null;
		String envVariableName=null;

		try {
			if(strExecEventFlag==1){
				strDateFormat=getTestData(testDataFilePathStatic, testComponentNameStatic,strdateFormatInDataSheet,gblrecordsCounterStatic);
				monthsToAdd=getTestData(testDataFilePathStatic, testComponentNameStatic,monthsToAddInSheet,gblrecordsCounterStatic);
				envVariableName=getTestData(testDataFilePathStatic, testComponentNameStatic,envVariable,gblrecordsCounterStatic);
			}else{
				strDateFormat=strdateFormatInDataSheet;
				monthsToAdd=monthsToAddInSheet;
				envVariableName=envVariable;
			}

			SimpleDateFormat dateformat = new SimpleDateFormat(strDateFormat);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, +(Integer.parseInt(monthsToAdd)));
			funtureMonthDate = dateformat.format(cal.getTime());

			if(!(envVariableName.equalsIgnoreCase("NA"))){
				Runtimevalue.setProperty(envVariableName, funtureMonthDate);
				ExtentTestManager.reportStepPass("Dynamic Future date '"+funtureMonthDate+"' is stored in the Runtime Variable  '"+envVariableName+"'");
			}

			actualResult = selectByLocatorType(getValueFromPOM).getAttribute("value");

			if((actualResult.trim()).equalsIgnoreCase(funtureMonthDate.trim())){
				functionStatus=true;
				ExtentTestManager.reportStepPass("'"+strTestObject+"'s  actual date '" + actualResult + "' matches the Expected future date '" + funtureMonthDate + "'");
			}else{
				functionStatus=false;
				ExtentTestManager.reportStepFail(driver,"'"+strTestObject+"' actual date '" + actualResult + "' does not match the expected date '" + funtureMonthDate + "'", true);
			}
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Exception occured while comparing the Future date.", true);
			functionStatus=false;
		}
		return functionStatus;	
	}

	public synchronized boolean deleteLogFile(String filePath, String fileName, int strExecEventFlag){

		boolean functionStatus = false;
		String path = null;
		String fileNameValue = null;
		int flag = 0;

		try{
			if(strExecEventFlag == 1){
				path = property.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic, filePath, gblrecordsCounterStatic));
				fileNameValue = getTestData(testDataFilePathStatic, testComponentNameStatic, fileName, gblrecordsCounterStatic);
			}

			if(path==null || fileNameValue==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}

			File directory = new File("//\\" +path);

			if(directory.listFiles().length == 0){
				log.info("Files are not available");
				ExtentTestManager.reportStepPass("Expected Log file '"+fileNameValue+"' is not available in directory "+path+"");
				return true;
			}else {
				log.info("Files are available its going to delete the files");
			}

			for(File listOfFiles : directory.listFiles()){

				if(listOfFiles.getName().equals(fileNameValue)){

					log.info("File with given filename is available in the Path");
					listOfFiles.delete();
					log.info("file is deleted");

					flag = 1;
					functionStatus = true;
					break;

				}else if(!(listOfFiles.getName().equals(fileNameValue))){
					log.info("File with given filename is available in the Path");
					functionStatus = true;
				}
			}
			Thread.sleep(5000);

		}catch(Exception e){
			log.info("Exception occurs in deleteFiles function "+e.getMessage());
			ExtentTestManager.reportStepFail(driver,"Error occurred while deleting the file with Name '"+fileNameValue+"'" , false);
			functionStatus = false;
		}

		if(flag == 1){
			ExtentTestManager.reportStepPass("Log File with expected filename '"+fileNameValue+"' in the path '"+path+"' deleted sucessfully.");
		}else{
			ExtentTestManager.reportStepPass("Expected Log file '"+fileNameValue+"' is not available in directory "+path+"");
		}
		return functionStatus;
	}

	public synchronized boolean WebElementTextCompareFromEnv(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag ){
		String actualResult=null;
		String strData=null;
		String envVariable=null;
		boolean WebElementValueCompareFromEnv=false;
		try{
			if (strExecEventFlag==1){
				envVariable=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
				strData=Runtimevalue.getProperty(envVariable);
			}
			if(envVariable==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			actualResult = selectByLocatorType(getValueFromPOM).getText();
		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while getting the text from the WebElement '"+strTestObject+"' and the error description is "+e.getLocalizedMessage()+"", true);
			WebElementValueCompareFromEnv=false;
		}

		try{
			if((actualResult.trim()).equalsIgnoreCase(strData.trim())){
				ExtentTestManager.reportStepPass("Actual value of the element '" +actualResult+ "' matches with the Expected value '"+strData+ "' retrievd from Runtime Variable '"+envVariable+ "' in the input field '"+strTestObject+"'");
				WebElementValueCompareFromEnv=true;
			}else{
				ExtentTestManager.reportStepFail(driver,"Actual value of the element '" +actualResult+ "' does not match with the Expected value '"+strData+ "' retrievd from Runtime Variable '"+envVariable+ "' in the input field '"+strTestObject+"'", true);
				WebElementValueCompareFromEnv=false;
			}
		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while comparing actual and expected values. Error description is "+e.getLocalizedMessage()+"", true);
			WebElementValueCompareFromEnv=false;
		}
		return WebElementValueCompareFromEnv;
	}

	public synchronized boolean SQLAllSelect(String Type,String sqltablename, String strsqlcolumnname,String strsqlcondition,String strExpectedvalue,String Days_to_add,String strenvironmentvariable,int strExecEventFlag){

		boolean result=false;
		String actionType=null;
		try{

			if (strExecEventFlag==1){
				actionType=getTestData(testDataFilePathStatic, testComponentNameStatic,Type,gblrecordsCounterStatic);
			}
			if(actionType==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			if(actionType.trim().equalsIgnoreCase("Normal")){
				result= SQLDBSelect(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue, strExecEventFlag);
			}else if(actionType.trim().equalsIgnoreCase("Date")){
				result= SQLDBDateFormatCompare(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue, strExecEventFlag);
			}else if(actionType.trim().equalsIgnoreCase("FutureDate")){
				result= SQLDBFutureDateCompare(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue, Days_to_add, strExecEventFlag);
			}else if(actionType.trim().equalsIgnoreCase("EnvVar")){
				result= SQLDBSelectFromEnv(sqltablename, strsqlcolumnname, strsqlcondition, strenvironmentvariable, strExecEventFlag);
			}else if(actionType.trim().equalsIgnoreCase("EnvCon")){
				result= SQLDBSelectConditionFromEnvvar(sqltablename, strsqlcolumnname, strsqlcondition, strenvironmentvariable, strExpectedvalue, strExecEventFlag);
			}else if(actionType.trim().equalsIgnoreCase("DateInEnvVar")){
				result= SQLDBDateCompareInEnvVar(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue, strenvironmentvariable, 1);
			}else{
				ExtentTestManager.reportStepFail(driver, "Invalid Action Type described in Excel sheet  "+actionType+"", false);
			}
		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver, "Error occured while comparing the values in SQL query.Error description is : "+ e.getLocalizedMessage() +".", false);
			return false;
		}
		return result;
	}

	public synchronized boolean SQLDBSelectConditionFromEnvvar(String sqltablename, String strsqlcolumnname,String strsqlcondition,String strenvironmentvariable,String strExpectedvalue,int strExecEventFlag){
		boolean functionStatus= false;
		String query = null;
		String check=null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String SQL_condition_value = null;		
		String Expected_value = "";
		String Actual_Value = null;
		String environmentvariable=null;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				Expected_value=getTestData(testDataFilePathStatic, testComponentNameStatic,strExpectedvalue,gblrecordsCounterStatic);
				Expected_value=getTestData(testDataFilePathStatic, testComponentNameStatic,strExpectedvalue,gblrecordsCounterStatic);
				environmentvariable=getTestData(testDataFilePathStatic, testComponentNameStatic,strenvironmentvariable,gblrecordsCounterStatic);
				SQL_condition_value = Runtimevalue.getProperty(environmentvariable);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in test data sheet.", false);
				return false;
			}

			//Query to Execute 
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"='"+ SQL_condition_value +"'";
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"='"+ SQL_condition_value +"'";
			log.info("query is : "+query);

			//Check for Record Available
			ResultSet rs_SQLServerCheck = stmt.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = stmt.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the SQL Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the SQL Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else{

				ResultSet rs_SQLServer = stmt.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1).trim();

				if (Expected_value.contains("CURRENT_DATE")){
					String []ExpectedvalueWithFormat=Expected_value.split("#");
					String db_Date = Actual_Value.split(" ")[0];
					Actual_Value=db_Date;
					Date date=new Date();			
					SimpleDateFormat dateformat=new SimpleDateFormat(ExpectedvalueWithFormat[1]);
					Expected_value=dateformat.format(date);
				}

				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the SQL Query "+query+" matches the expected value : '"+Expected_value+"' where Condition value from ENV Variable '"+environmentvariable+"'");
					functionStatus= true;
				}else if(!(Actual_Value.equalsIgnoreCase(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the SQL Query "+query+" does not match with the expected value '"+Expected_value+"' where Condition value from ENV Variable '"+environmentvariable+"'", false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in SQL query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean SQLDBDateCompareInEnvVar(String sqltablename, String strsqlcolumnname,String strsqlcondition,String dateFormatInSheet,String envVariable,int strExecEventFlag){
		boolean functionStatus= false;
		String query = null;  
		String check=null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String Expected_value = null;
		String Actual_Value = null;
		String expected_db_Date = null;
		String Date_Format=null;
		String envVariableName=null;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				Date_Format=getTestData(testDataFilePathStatic, testComponentNameStatic,dateFormatInSheet,gblrecordsCounterStatic);
				envVariableName=getTestData(testDataFilePathStatic, testComponentNameStatic,envVariable,gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null || Date_Format ==null || envVariableName==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in test data sheet.", false);
				return false;
			}

			Expected_value=Runtimevalue.getProperty(envVariableName);
			log.info("envVariableName is :"+envVariableName);
			log.info("value of envVariableName is :"+Expected_value);

			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";

			ResultSet rs_SQLServerCheck= stmt.executeQuery(check);

			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = stmt.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the SQL Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the SQL Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else if(!(rs_SQLServerCheck.wasNull())){

				ResultSet rs_SQLServer= stmt.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1);

				String db_Date = Actual_Value.split(" ")[0];

				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				Date dateToChange = dateFormat1.parse(db_Date);
				SimpleDateFormat finalDateFormat = new SimpleDateFormat(Date_Format);
				expected_db_Date = finalDateFormat.format(dateToChange);

				if(expected_db_Date.equals(Expected_value)){
					ExtentTestManager.reportStepPass("Actual Date '"+expected_db_Date+"' for the SQL Query "+query+" matches the expected Date '"+Expected_value+"' from ENV Variable '"+envVariableName+"'");
					functionStatus= true;
				}else if(!(expected_db_Date.equals(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual Date '"+expected_db_Date+"' for the SQL Query "+query+" does not match the expected Date '"+Expected_value+"' from ENV Variable '"+envVariableName+"'",true);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in SQL query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean SQLDBSelectStoreValueInEnvVar(String sqltablename, String strsqlcolumnname,String strsqlcondition,String envVariable,int strExecEventFlag){
		String query = null;
		String check = null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String envVariableName=null;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				envVariableName=getTestData(testDataFilePathStatic, testComponentNameStatic,envVariable,gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null||envVariableName==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}

			//Query to Execute      
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";

			//Check for Record Available
			ResultSet rs_SQLServerCheck = stmt.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = stmt.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				ExtentTestManager.reportStepFail(driver,"Selected DB value is NULL. No values stored in RUNTIME Variable", false);
				return false;

			}else{
				ResultSet rs_SQLServer = stmt.executeQuery(query);
				rs_SQLServer.next();
				String value = rs_SQLServer.getString(1).trim();
				Runtimevalue.setProperty(envVariableName, value);
				ExtentTestManager.reportStepPass("Actual DB value '"+value+"' is stored in RunTime variable '"+envVariableName+"' successfully");
				return true;
			}

		} catch (Exception e) {    
			ExtentTestManager.reportStepFail(driver,"Error occured while storing the values in SQL query.Error description is : "+ e.getMessage(), false);
			return false;
		}

	}

	public synchronized boolean WebElementValueCompareFromRuntimeEnv(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag ){
		String actualResult=null;
		String strData=null;
		boolean WebElementValueCompareFromEnv=false;
		String envVariable=null;
		try{
			if (strExecEventFlag==1){
				envVariable=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}else{
				envVariable=strColumnName;
			}

			if(envVariable==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the data sheet.", false);
				return false;
			}

			strData=Runtimevalue.getProperty(envVariable);
			actualResult = selectByLocatorType(getValueFromPOM).getAttribute("value");

		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while getting the text from the WebElement '"+strTestObject+"'and the error description is "+e.getLocalizedMessage()+"'", true);
			WebElementValueCompareFromEnv=false;
		}

		try{
			if((actualResult.trim()).equalsIgnoreCase(strData.trim())){
				ExtentTestManager.reportStepPass("Actual Value '" +actualResult+ "' matches with the EnvVariable '"+envVariable+"'s value '"+strData+ "' in the input field '"+strTestObject+"'");
				WebElementValueCompareFromEnv=true;
			}else{
				ExtentTestManager.reportStepFail(driver,"Actual Value '" +actualResult+ "' does not match with the EnvVariable  '"+envVariable+"'s value '"+strData+ "' in the input field '"+strTestObject+"'", true);
				WebElementValueCompareFromEnv=false;
			}
		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing actual and expected values. Error description is "+e.getLocalizedMessage()+"", true);
			WebElementValueCompareFromEnv=false;
		}
		return WebElementValueCompareFromEnv;
	}

	public synchronized boolean waitUntilListLoads(String getValueFromPOM, String strTestObject){
		String strData=null;
		boolean WebListSelect=false;
		Select se=null;
		List<WebElement> options=null;
		try{
			int i=0;
			int listSize=0;
			boolean displayed=false;
			while(i<10){
				Thread.sleep(1000);

				se=new Select(selectByLocatorType(getValueFromPOM));
				options = se.getOptions();
				listSize=options.size();
				if(options.size()>1){
					displayed=true;
					break;
				}else{
					log.info("List yet not populated");
				}

				i++;
			}

			if(displayed){
				ExtentTestManager.reportStepPass("The List is Loaded for the dropdown '"+strTestObject+"' successfully");
				WebListSelect=true;
			}else{
				if(listSize==1){
					ExtentTestManager.reportStepPass("The List has only one value Loaded for the dropdown '"+strTestObject+"'");
				}
				WebListSelect=true;
			}

		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"The Item '"+strData+"' was not selected from the  '"+strTestObject+"' List box " , true); 
			WebListSelect=false;
		}
		return WebListSelect;
	}

	public synchronized boolean WeblistMonthVerify(String getYearValueFromPOM,String getMonthValueFromPOM, String strYearObject,String strMonthObject, String totalNumbersColumn,int itemscount_not_consider,int strExecEventFlag){
		boolean WeblistSQLDBitemsverify= false;
		String strData=null;
		int totalNumberMonths;
		String[] weblistvalues = null;
		
		try{
		if(strExecEventFlag==1){
			strData=getTestData(testDataFilePathStatic, testComponentNameStatic,totalNumbersColumn,gblrecordsCounterStatic);
		}
		if(strData==null){
			ExtentTestManager.reportStepFail(driver,"Required details are not provided in the datasheet.", false);
			return false;
		}

		totalNumberMonths=Integer.parseInt(strData);
		int noOfYears = totalNumberMonths/12;

		
			if(noOfYears>=1){
				//Weblist the years box
				Select se = new Select(selectByLocatorType(getYearValueFromPOM));
				List<WebElement> options = se.getOptions();
				//if you want to get all elements text into array list
				List<String> all_elements_text=new ArrayList<String>();
				for(int j=0; j<options.size(); j++){
					//loading text of each element in to array all_elements_text
					all_elements_text.add(options.get(j).getText());
				}

				weblistvalues = (String[]) all_elements_text.toArray(new String[all_elements_text.size()]);

				if(noOfYears == weblistvalues.length - itemscount_not_consider)
				{
					if((weblistvalues.length - itemscount_not_consider)==0){
						ExtentTestManager.reportStepPass( "No Dropdown Value is Present in the Dropdown '"+strYearObject+"' and the corresponding Table");
						WeblistSQLDBitemsverify=true;

					}else{
						for (int i=0; i<(weblistvalues.length - itemscount_not_consider); i++){

							if (weblistvalues[i+1].equals(Integer.toString(i+1))){
								ExtentTestManager.reportStepPass("Actual value '"+ weblistvalues[i+1] +"' in the dropdown '"+ strYearObject +"' matches the expected value '"+(i+1)+"'.");
								WeblistSQLDBitemsverify=true;
							} else{
								ExtentTestManager.reportStepFail(driver, "Actual value '"+ weblistvalues[i+1] +"' in the dropdown '"+ strYearObject +"' doesn't matches the expected value '"+(i+1)+"'", true);
								WeblistSQLDBitemsverify=false;
							}
						}
					}
				} else {
					ExtentTestManager.reportStepFail(driver, "The number of items present in the dropdown '"+ strYearObject +"' doesn't matches with the given number of items '"+ noOfYears +"'.", true);
					WeblistSQLDBitemsverify=false;
				}

			}

			Select se = new Select(selectByLocatorType(getMonthValueFromPOM));
			List<WebElement> options = se.getOptions();

			//if you want to get all elements text into array list
			List<String> all_elements_text=new ArrayList<String>();
			for(int j=0; j<options.size(); j++){
				//loading text of each element in to array all_elements_text
				all_elements_text.add(options.get(j).getText());
			}

			weblistvalues = (String[]) all_elements_text.toArray(new String[all_elements_text.size()]);
			log.info("Int length is : "+strData);
			log.info("weblistvalues length is : "+weblistvalues.length);
			itemscount_not_consider=0;
			if(totalNumberMonths == weblistvalues.length - itemscount_not_consider)
			{
				if((weblistvalues.length - itemscount_not_consider)==0){

					log.info("No value present in DB as well list");
					ExtentTestManager.reportStepPass("No Dropdown Value is Present in the Dropdown '"+strMonthObject+"' and the corresponding Table");
					WeblistSQLDBitemsverify=true;

				}else{
					for (int i=0; i<(weblistvalues.length - 1); i++){

						if (weblistvalues[i+1].equals(Integer.toString(i+1))){
							ExtentTestManager.reportStepPass("Actual value '"+ weblistvalues[i+1] +"' in the dropdown '"+ strMonthObject +"' matches the expected value '"+(i+1)+"'.");
							WeblistSQLDBitemsverify=true;
						} else{
							ExtentTestManager.reportStepFail(driver,"Actual value '"+ weblistvalues[i+1] +"' in the dropdown '"+ strMonthObject +"' doesn't matches the expected value '"+(i+1)+"'.", true);
							WeblistSQLDBitemsverify=false;
						}
					}
				}
			} else {
				ExtentTestManager.reportStepFail(driver, "Number of items present in the dropdown '"+ strMonthObject +"' doesn't matches with the given number of items '"+ totalNumberMonths +"'.", true);
				WeblistSQLDBitemsverify=false;
			}



		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while comparing the values. Error description is : "+ e.getLocalizedMessage() +".", true);
			WeblistSQLDBitemsverify=false;
		}
		return WeblistSQLDBitemsverify;
	}

	public synchronized boolean WebElementValueStoreDynamicValue(String getValueFromPOM, String strTestObject,String strColumnName, int strExecEventFlag ){
		String actualText="";
		String strData=null;
		boolean WebElementTextCompare=false;
		try{
			try{
				if(strExecEventFlag==1){
					strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
				}
				actualText = selectByLocatorType(getValueFromPOM).getAttribute("value");
			} catch (Exception e){
				ExtentTestManager.reportStepFail(driver, "Error occured while getting the text from the WebElement :'"+strTestObject+"'and the error description is :"+e.getLocalizedMessage()+"'", true);
				return false;
			}

			if(actualText == null){
				actualText="";
				log.info("Value is NULL . Setting EMPTY to Runtime Property '"+strData+"'");
				ExtentTestManager.reportStepPass("Empty value '"+actualText+"' is stored in the '"+strTestObject+"' is stored in the Runtime variable '"+strData+"'");
				return false;
			}

			try{
				Runtimevalue.setProperty(strData, actualText);
				log.info("Value set to Runtime Property '"+strData+"' is => '"+Runtimevalue.getProperty(strData)+"'");
				ExtentTestManager.reportStepPass("The Dynamic value '"+actualText+"' of Element '"+strTestObject+"' is successfully stored in the Runtime variable '"+strData+"'");
				WebElementTextCompare=true;
			} catch (Exception e){
				ExtentTestManager.reportStepFail(driver, "Error occured while Stroing the Dynamic value of element '"+strTestObject+"' in the Runtime variable '"+strData+"'. Error description is "+e.getLocalizedMessage()+"'", true);
				WebElementTextCompare=false;
			}

		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while getting the text from the WebElement '"+strTestObject+"'and the error description is "+e.getLocalizedMessage()+"'", true);
			WebElementTextCompare=false;
		}
		return WebElementTextCompare;
	}

	public synchronized boolean WebElementFutureMonthValueComapreFromEnv(String getValueFromPOM,  String strTestObject,String strdateFormatInDataSheet,String strMonthsFromSheet,String envVariableHoldingDate,String envVariableToStoreNewDate,int strExecEventFlag){

		boolean functionStatus= false;
		String strDateFormat=null;
		String monthsToAdd=null;
		String funtureMonthDate=null;
		String actualResult=null;
		String envVariableName=null;
		String dateInEnvVariable=null;

		try {
			if(strExecEventFlag==1){
				strDateFormat=getTestData(testDataFilePathStatic, testComponentNameStatic,strdateFormatInDataSheet,gblrecordsCounterStatic);
				monthsToAdd=getTestData(testDataFilePathStatic, testComponentNameStatic,strMonthsFromSheet,gblrecordsCounterStatic);
				dateInEnvVariable=getTestData(testDataFilePathStatic, testComponentNameStatic,envVariableHoldingDate,gblrecordsCounterStatic);
				envVariableName=getTestData(testDataFilePathStatic, testComponentNameStatic,envVariableToStoreNewDate,gblrecordsCounterStatic);

			}else{
				strDateFormat=strdateFormatInDataSheet;
				monthsToAdd=strMonthsFromSheet;
				dateInEnvVariable=envVariableHoldingDate;
				envVariableName=envVariableToStoreNewDate;
			}

			String dateFromEnvVariable=Runtimevalue.getProperty(dateInEnvVariable);

			// 31/10/218

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
			cal.setTime(sdf.parse(dateFromEnvVariable));			
			cal.add(Calendar.MONTH, +(Integer.parseInt(monthsToAdd)));
			funtureMonthDate = sdf.format(cal.getTime());

			if(!(envVariableName.equalsIgnoreCase("NA"))){
				Runtimevalue.setProperty(envVariableName, funtureMonthDate);
				ExtentTestManager.reportStepPass("Dynamic Future date '"+funtureMonthDate+"' is stored in the Runtime Variable  '"+envVariableName+"'");
			}

			actualResult = selectByLocatorType(getValueFromPOM).getAttribute("value");

			if((actualResult.trim()).equalsIgnoreCase(funtureMonthDate.trim())){
				functionStatus=true;
				ExtentTestManager.reportStepPass(""+strTestObject+"'s  actual date '" + actualResult + "' matches the Expected future date '" + funtureMonthDate + "'");
			}else{
				functionStatus=false;
				ExtentTestManager.reportStepFail(driver, ""+strTestObject+"'s actual date '" + actualResult + "' does not match the expected date '" + funtureMonthDate + "'", true);
			}
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Exception occured while comparing the Future date", true);
			functionStatus=false;
		}
		return functionStatus;	
	}

	public synchronized boolean readLogFile(String filePath, String startsWith, String endsWith,String fileNameValue, String textName, int strExecEventFlag) throws Exception{

		boolean functionStatus = false;
		String path = null;
		String startValue = null;
		String endValue = null;
		String textValue = null;
		File[] listOfFile = null;
		String fileName = null;
		String actualText = null;
		Pattern pattern = null;
		String fileNameFromExcel=null;

		try{
			if(strExecEventFlag == 1){
				path = property.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic, filePath, gblrecordsCounterStatic));
				startValue = getTestData(testDataFilePathStatic, testComponentNameStatic, startsWith, gblrecordsCounterStatic);
				endValue = getTestData(testDataFilePathStatic, testComponentNameStatic, endsWith, gblrecordsCounterStatic);
				textValue = getTestData(testDataFilePathStatic, testComponentNameStatic, textName, gblrecordsCounterStatic);
				fileNameFromExcel= getTestData(testDataFilePathStatic, testComponentNameStatic, fileNameValue, gblrecordsCounterStatic);
			}

			if(path==null || startValue==null || endValue==null || textValue==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}

			File directory = new File("//\\" +path);
			boolean fileexist=false;
			int fileAppeartime=0;

			while(fileAppeartime<30){
				Thread.sleep(1000);
				listOfFile = directory.listFiles();
				if(listOfFile.length != 0){
					log.info("Directory has files");
					fileexist=true;
					break;

				}else{
					log.info("No File is available in directory. looping again with 60 secs");
				}

				log.info("No File is available in directory with 60 secs");
				fileAppeartime++;

			}

			if(fileexist){
				log.info("Files avialble in the directiory");

			}else{
				log.info("No File is available in directory for 60secs");
				ExtentTestManager.reportStepFail(driver,"No Log files are available in the given directory "+directory+"" , false);
				return false;
			}
			
			boolean foundstatus=false;
			int time=0;
			while(time<30){
				Thread.sleep(1000);	
				listOfFile = directory.listFiles();
				for(int i = 0; i<listOfFile.length; i++){
					log.info("i is : "+time);
					if(listOfFile[i].isFile()){

						fileName = listOfFile[i].getName();
						log.info("Found a file and name is : "+fileName);
						if(fileName.equals(fileNameFromExcel)){
							log.info("FileName exact match : "+fileName);
							fileName=fileNameFromExcel;
							foundstatus=true;
							break;
						}
					}
				}
				if(foundstatus){
					log.info("FileName exact match is found. Braking the loop");
					break;
				}else{
					log.info("FileName not found in path. Continue the loop");
				}
				time++;
			}

			if(fileName == null){
				ExtentTestManager.reportStepFail(driver,"'"+fileNameFromExcel+"' file is not available in the directory "+directory+"" , false);
				log.info("Search File is not available in directory");
				return false;
			}

			if(!(fileName.equals(fileNameFromExcel))){
				ExtentTestManager.reportStepFail(driver,"'"+fileNameFromExcel+"' file is not available in the directory "+directory+"" , false);
				log.info("Search File is not available in directory");
				return false;
			}

			File ff = new File("//\\" +path +fileName);
			log.info(ff.getAbsolutePath()+" || "+ff.getName());
			String fileContent = IOUtils.toString(ff.toURI(),"UTF-8");
			actualText = "Text is not available in log file";
			pattern = Pattern.compile(textValue);
			Matcher matcher = pattern.matcher(fileContent);
			while(matcher.find()){
				actualText = matcher.group(1)+matcher.group(2)+matcher.group(3);
				log.info("Text is matched");
				ExtentTestManager.reportStepPass("Text '"+actualText+"' is matched with expected text in the filename of '"+startValue+"'");
				return true;
			}

			log.info(actualText);
			functionStatus = false;
			ExtentTestManager.reportStepFail(driver,"'"+actualText+" is not available in the filename '"+startValue+"'" , false);
		}catch(Exception e){

			log.info("Exception occurs in read log file"+e.getMessage());
			functionStatus = false;
			ExtentTestManager.reportStepFail(driver,"Error occurred, while match with expected text in the filename of '"+startValue+"'" , false);
		}
		return functionStatus;
	}
	
	public synchronized boolean ticketingAttachmentFileDownload(String strTestObject) {
		boolean elementStatus = false;
		try{
			String locatioPath = property.getProperty("download_FilePath");
			//Call the AutoIT for download process and store the file in specific path which is given in AutoIT script	
			Runtime.getRuntime().exec("AutoIT/CRM_file_download_ticketsAttach.exe");
			long first;
			long second;
			while(true){
				File file = new File(locatioPath);
				File files[] = file.listFiles();
				if(files.length > 0){
					first = files[0].length()/1024;
					Thread.sleep(1000);
					file = new File(locatioPath);
					files = file.listFiles();
					second = files[0].length()/1024;
					if(first != second){
						log.info("File downloading");
					}else {
						log.info("File download completed");
						ExtentTestManager.reportStepPass("File is downloaded from '"+strTestObject+"' and stored in specific location");	
						elementStatus = true;
						break;
					}
				}

			}

		}catch(Exception e){
			log.info("Exception occurred in AutoIT file download for tickets attachment :"+e.getMessage());
			ExtentTestManager.reportStepFail(driver,"File is not downloaded from the browser" , true);  
			elementStatus = false;
		}
		return elementStatus;
	}

	public synchronized boolean uploadFileNewTicket(String strTestObject) {
		boolean elementStatus = false;
		try{
			//Call the AutoIT for upload the file in specific path which is given in AutoIT script	
			Runtime.getRuntime().exec("AutoIT/CRM_upload_file.exe");
			ExtentTestManager.reportStepPass("File uploaded successfully for '"+strTestObject+"' module");	
			elementStatus = true;
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"File is not uploaded" , true);  
			elementStatus = false;
		}
		return elementStatus;
	}

	public synchronized boolean checkScrollBarIsPresent(String getValueFromPOM, String strTestObject) {
		boolean elementStatus = false;
		try{
			
			String elementFromPOM=null;
			elementFromPOM=getValueFromPOM;
			int count=elementFromPOM.split("#").length;
			//log.info("JS POM Count : "+count);
			if(count==2){
				getValueFromPOM=elementFromPOM.split("#")[1];
				//log.info("changed getValueFromPOM is : "+getValueFromPOM);
			}else{
				//log.info("Not changed getValueFromPOM is : "+getValueFromPOM);
			}
			
			//Xpath for Javascript executor, if value is present will return as true or not present will return as false
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Object scrollBarStatus = js.executeScript("return document.evaluate(\""+getValueFromPOM+"\",document.body,null,XPathResult.UNORDERED_NODE_ITERATOR_TYPE,null).iterateNext()!=null;");
			if(scrollBarStatus.equals(true)){
				log.info("Scroll bar is available on page");
				//Runtimevalue.setProperty("checkScrollBarPresent", property.getProperty("scrollBarPresent"));
				ExtentTestManager.reportStepPass("Scroll bar is available on "+strTestObject+" page");
				elementStatus = true;
			}else{				
				log.info("Scroll bar is not available on page");
				//Runtimevalue.setProperty("checkScrollBarPresent", property.getProperty("scrollBarNotPresent"));
				ExtentTestManager.reportStepPass("Scroll bar is not available on "+strTestObject+" page");
				elementStatus = true;
			}
		}catch(Exception e){
			log.info("Error occurred, while finding the scroll bar element on page");
			ExtentTestManager.reportStepFail(driver,"Scroll bar is not available on "+strTestObject+" page" , true);
			elementStatus = false;
		}

		return elementStatus;
	}

	public synchronized boolean verifyScrollPresentOnBrowser(String strTestObject) {
		boolean elementStatus = false;
		try{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Object scrollBarStatus = js.executeScript("return document.documentElement.scrollHeight>document.documentElement.clientHeight;");
			if(scrollBarStatus.equals(true)){
				log.info("Scroll bar is available on page");
				//Runtimevalue.setProperty("checkScrollBarPresent", property.getProperty("scrollBarPresent"));
				ExtentTestManager.reportStepPass("Scroll bar is available on "+strTestObject+" page");
				elementStatus = true;
			}else{				
				log.info("Scroll bar is not available on page");
				//Runtimevalue.setProperty("checkScrollBarPresent", property.getProperty("scrollBarNotPresent"));
				ExtentTestManager.reportStepPass("Scroll bar is not available on "+strTestObject+" page");
				elementStatus = true;
			}
		}catch(Exception e){
			log.info("Error occurred, while finding the scroll bar element on page");
			ExtentTestManager.reportStepFail(driver,"Scroll bar is not available on "+strTestObject+" page" , true);
			elementStatus = false;
		}
		return elementStatus;
	}

	public synchronized boolean setScrollBarOFF(String strTestObject) {
		/*boolean elementStatus = false;
		try{
			Runtimevalue.setProperty("checkScrollBarPresent", property.getProperty("scrollBarNotPresent"));
			ExtentTestManager.reportStepPass("Changed status for Scroll bar status as 'No' in "+strTestObject+"");
			elementStatus = true;
		}catch(Exception e){
			log.info("Error occurred, while set the status for scroll bar");
			ExtentTestManager.reportStepFail(driver,"Error occurred, while set the status for scroll bar in "+strTestObject+"" , true);
			elementStatus = false;
		}

		return elementStatus;*/
		
		//do nothing
		return true;
		
	}

	public synchronized boolean changeToCoordinates(String strTestObject) {
		/*boolean elementStatus = false;
		try{
			Runtimevalue.setProperty("checkScrollBarPresent", property.getProperty("scrollBarPresent"));
			ExtentTestManager.reportStepPass("Changed status for Scroll bar status as 'Yes' in "+strTestObject+" page");
			elementStatus = true;
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occurred while set the status for scroll bar in "+strTestObject+" page" , true);
			elementStatus = false;
		}
		return elementStatus;*/

		//do nothing
		return true;
	}

	public synchronized boolean checkTicketMsgIsPresent(String getValueFromPOM, String strTestObject) {
		boolean elementStatus = false;
		try{
			Thread.sleep(10000);
			String elementFromPOM=null;
			elementFromPOM=getValueFromPOM;
			int count=elementFromPOM.split("#").length;
			//log.info("JS POM Count : "+count);
			if(count==2){
				getValueFromPOM=elementFromPOM.split("#")[1];
				//log.info("changed getValueFromPOM is : "+getValueFromPOM);
			}else{
				//log.info("Not changed getValueFromPOM is : "+getValueFromPOM);
			}
			
			//Xpath for Javascript executor, if value is present will return as true or not present will return as false
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Object scrollBarStatus = js.executeScript("return document.evaluate(\""+getValueFromPOM+"\",document.body,null,XPathResult.UNORDERED_NODE_ITERATOR_TYPE,null).iterateNext()!=null;");
			if(scrollBarStatus.equals(true)){
				log.info("Faded frame is present is available on page");
				//Runtimevalue.setProperty("checkScrollBarPresent", property.getProperty("scrollBarPresent"));
				ExtentTestManager.reportStepPass("Text Message Box is displayed in '"+strTestObject+"'");
				elementStatus = true;
			}else{				
				log.info("Faded frame is not available on page");
				//Runtimevalue.setProperty("checkScrollBarPresent", property.getProperty("scrollBarNotPresent"));
				ExtentTestManager.reportStepPass("Text Message Box is not displayed in '"+strTestObject+"'");
				elementStatus = true;
			}
		}catch(Exception e){
			log.info("Error occurred, while finding the scroll bar element on page");
			ExtentTestManager.reportStepFail(driver,"Message box is not available on '"+strTestObject+"'" , true);
			elementStatus = false;
		}
		return elementStatus;
	}
	
	public synchronized boolean pageScrollUp(String getValueFromPOM, String strTestObject) {
		boolean elementStatus = false;
		try{
			WebElement scroll = selectByLocatorType(getValueFromPOM);
			scroll.sendKeys(Keys.PAGE_UP);
			ExtentTestManager.reportStepPass("Scroll bar moved up successfully in "+strTestObject+" page");
			elementStatus = true;
		}catch(WebDriverException e){
			ExtentTestManager.reportStepPass("Scroll bar moved up successfully in "+strTestObject+" page");
			elementStatus = true;
		}
		catch(Exception e){
			log.info("Error occurred, while set the status for scroll bar");
			ExtentTestManager.reportStepFail(driver,"Error occurred, while scroll bar moved up in "+strTestObject+" page" , true);
			elementStatus = false;
		}
		return elementStatus;
	}
	
	public synchronized boolean getValueFromPagination(String getValueFromPOM, String nextBtnValueFromPOM, String pageCountValFromPOM, String strTestObject) {
		boolean elementStatus = false;
		try{
			String elementFromPOMForJS=null;
			String elementFromPOM=null;
			elementFromPOM=getValueFromPOM;
			int countNo=elementFromPOM.split("#").length;
			//log.info("JS POM Count : "+count);
			if(countNo==2){
				elementFromPOMForJS=elementFromPOM.split("#")[1];
				log.info("changed elementFromPOMForJS is : "+elementFromPOMForJS);
			}else{
				log.info("Not changed getValueFromPOM is : "+getValueFromPOM);
			}
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Object checkElementStatusJS;
			WebElement clickElement;
			checkElementStatusJS = js.executeScript("return document.evaluate(\""+elementFromPOMForJS+"\",document.body,null,XPathResult.UNORDERED_NODE_ITERATOR_TYPE,null).iterateNext()!=null;");

			//Checking the element is exist on page
			if(checkElementStatusJS.equals(true)){
				//If exist, clicking on element
				clickElement = selectByLocatorType(getValueFromPOM);
				clickElement.click();
				ExtentTestManager.reportStepPass("Element is clicked successfully from pagination table in "+strTestObject+"");
				elementStatus = true;
			} else {
				WebElement pageCount = selectByLocatorType(pageCountValFromPOM);
				//Get the page count and convert into Integer
				String strCount = pageCount.getText();
				int count = Integer.parseInt(strCount);

				//Element is not exist, clicking on paginaton by page count
				for(int i = 1; i <= count; i++){

					WebElement clickingOnNextBtn = selectByLocatorType(nextBtnValueFromPOM);
					clickingOnNextBtn.click();
					//Again find the element by using JS executor
					checkElementStatusJS = js.executeScript("return document.evaluate(\""+elementFromPOMForJS+"\",document.body,null,XPathResult.UNORDERED_NODE_ITERATOR_TYPE,null).iterateNext()!=null;");
					//If element is exist in loop
					if(checkElementStatusJS.equals(true)){
						clickElement = selectByLocatorType(getValueFromPOM);
						clickElement.click();
						ExtentTestManager.reportStepPass("Element is clicked successfully from pagination table in "+strTestObject+"");
						elementStatus = true;
						break;
					}

				} if(checkElementStatusJS.equals(false)) {
					log.info("Element is not available in table pagination");
					ExtentTestManager.reportStepFail(driver,"Element is not available from pagination table in "+strTestObject+"" , true);
					elementStatus = false;
				}
			}
		}catch(Exception e){
			log.info("Exception occurred in 'getValueFromPagination' method :" +e.getMessage());
			ExtentTestManager.reportStepFail(driver,"Element is not clicked from pagination table in"+strTestObject+"" , true);
			elementStatus = false;
		}
		return elementStatus;
	}

	public synchronized boolean threadingForEvents(){
		try{
			Runtime.getRuntime().exec("AutoIT//Registration_page_alert_close.exe");
		}catch(Exception e){
			log.info("Error occurred, while executing AutoIT function for Registration process");
			e.printStackTrace();
		}
		return true;
	}

	public synchronized boolean waituntiltextpresent(String getValueFromPOM, String strTestObject) {
		boolean elementStatus= false;
		int icount = 0;
		try{
			if((selectByLocatorType(getValueFromPOM)).getText().length() != 0){
				elementStatus = true;
			}
			while((selectByLocatorType(getValueFromPOM)).getText().length() == 0){
				//RefreshObject(getValueFromPOM);
				Thread.sleep(1000);
				if((selectByLocatorType(getValueFromPOM)).getText().length() != 0){
					elementStatus = true;
					break;
				}
				if(icount == 30  && (selectByLocatorType(getValueFromPOM)).getText().length() == 0){
					break;
				}
				icount = icount + 1;
			}
		} catch(StaleElementReferenceException e1){
			return waituntiltextpresent(getValueFromPOM, strTestObject);
		} catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Exception occured while waiting for the Text to appear in the element : '"+ strTestObject +"'. Exception is "+ e , true);
			return false;
		}

		if(elementStatus){
			ExtentTestManager.reportStepPass("'Text is present in "+ strTestObject +"' successfully");
			return elementStatus;
		}else{
			ExtentTestManager.reportStepFail(driver,"'Text is not present in "+ strTestObject +"'." , true);
			return elementStatus;
		}
 
	}

	public synchronized boolean waituntilvaluepresent(String getValueFromPOM, String strTestObject) {
		boolean elementStatus= false;
		int icount = 0;
		try{
			if((selectByLocatorType(getValueFromPOM)).getAttribute("value").length() != 0){
				elementStatus = true;
			}
			while((selectByLocatorType(getValueFromPOM)).getAttribute("value").length() == 0){
				Thread.sleep(1000);
				//RefreshObject(getValueFromPOM);
				if((selectByLocatorType(getValueFromPOM)).getAttribute("value").length() != 0){
					elementStatus = true;
					break;
				}
				if(icount == 10  && (selectByLocatorType(getValueFromPOM)).getAttribute("value").length() == 0){
					break;
				}
				icount = icount + 1;
			}
		} catch(StaleElementReferenceException e1){
			return waituntilvaluepresent(getValueFromPOM, strTestObject);
		} catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while waiting for the element : '"+ strTestObject +"'.Error description is : "+ e +"." , true);
			elementStatus = false;
		}

		if(elementStatus){
			ExtentTestManager.reportStepPass("'Text is present in "+ strTestObject +"' successfully");
			return elementStatus;
		}else{
			ExtentTestManager.reportStepFail(driver,"'Text is not present in "+ strTestObject +"'." , true);
			return elementStatus;
		}
	}

	public synchronized boolean waituntilvaluenotpresent(String getValueFromPOM, String strTestObject) {
		boolean elementStatus= false;
		getDisplayTimeout = sysProperty.getProperty("elementDisplayTimeout");
		intElementDisplayTimeout = Integer.parseInt(getDisplayTimeout);
		try{

			elementStatus = new WebDriverWait(driver,intElementDisplayTimeout).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return selectByLocatorType(getValueFromPOM).getAttribute("value").equals("");
				}
			});
		} catch(StaleElementReferenceException e1){
				return waituntilvaluenotpresent(getValueFromPOM, strTestObject);
		} catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while waiting for the element : '"+ strTestObject +"'.Error description is : "+ e +"." , true);
			elementStatus = false;
		}

		if(elementStatus){
			ExtentTestManager.reportStepPass("'Text is not present in "+ strTestObject +"' successfully");
			return elementStatus;
		}else{
			ExtentTestManager.reportStepFail(driver,"'Text is present in "+ strTestObject +"'." , true);
			return elementStatus;
		}
	}

	public synchronized boolean WebCheckboxON(String getValueFromPOM, String strTestObject ){
		boolean elementStatus=false;
		try {

			if (!selectByLocatorType(getValueFromPOM).isSelected())
			{
				selectByLocatorType(getValueFromPOM).click();
			}
			ExtentTestManager.reportStepPass("Checkbox  '"+ strTestObject +"' is  selected successfully.");
			elementStatus=true;
		} catch (StaleElementReferenceException e) {
			return WebCheckboxON(getValueFromPOM, strTestObject);
		} catch (NoSuchElementException e) {
			ExtentTestManager.reportStepFail(driver,"WebElement  '"+ strTestObject +"'  is not selected." , true); 
			elementStatus=false;
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"WebElement  '"+ strTestObject +"'  is not selected." , true); 
			elementStatus=false;
		}
		return elementStatus;
	}

	public synchronized boolean WebElementAttTitleCompare(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag ){
		String actualResult=null;
		String strData=null;
		boolean WebElementAttTitleCompare = false;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the datasheet.", false);
				return false;
			}

			actualResult = selectByLocatorType(getValueFromPOM).getAttribute("title");

			if((actualResult.trim()).equalsIgnoreCase(strData.trim())){
				WebElementAttTitleCompare = true;
				ExtentTestManager.reportStepPass(strTestObject+"'s  actual value '" + actualResult + "' matches the expected value '" + strData + "'");
			}else{
				WebElementAttTitleCompare = false;
				ExtentTestManager.reportStepFail(driver, strTestObject+"'s actual Value '" + actualResult + "' does not match the Expected Value '" + strData + "'", true);
			}
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver, strTestObject+"'s actual Value '" + actualResult + "' does not match the Expected Value '" + strData + "'", true);
			WebElementAttTitleCompare = false;
		}
		return WebElementAttTitleCompare;
	}

	public synchronized boolean WebListVerifyValue(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag) {
		String strData=null;
		boolean match=false;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the datasheet.", false);
				return false;
			}
			WebElement dropdown = selectByLocatorType(getValueFromPOM);
			Select select = new Select(dropdown);  
			List<WebElement> options = select.getOptions();  
			for(WebElement we:options)  
			{  
				if (we.getText().equals(strData)){
					match = true;
				}
			}
			if(match){
				ExtentTestManager.reportStepPass("Item '"+ strData +"' is present in WebList  '"+strTestObject+"'.");
			}else{
				ExtentTestManager.reportStepFail(driver,"Item '"+ strData +"' is not present in WebList  '"+strTestObject+"'." , true);
			}
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Exception occured while Verifying the WebList value. "+ e , true);
		}
		return match;
	}

	public synchronized boolean WebElementDisabled(String getValueFromPOM, String strTestObject) {

		boolean elementenable;
		boolean WebElementDisabled = false;
		try {
			elementenable=selectByLocatorType(getValueFromPOM).isEnabled();

			if(elementenable){
				WebElementDisabled=false;
				ExtentTestManager.reportStepFail(driver, "The Element '"+strTestObject+"' is not disabled.", true);
			}else{
				WebElementDisabled = true;
				ExtentTestManager.reportStepPass(  "The Element '"+strTestObject+"' is disabled as expected.");
			}
		} catch (Exception e) { 
			WebElementDisabled=false;
			ExtentTestManager.reportStepFail(driver, "Exception occured while checking WebElementDisabled."+e, true);
		}
		return WebElementDisabled;
	}

	public synchronized boolean webCheckBoxCheckStatus(String getValueFromPOM, String strTestObject) {
		boolean elementStatus = false;
		try{
			if(selectByLocatorType(getValueFromPOM).getAttribute("checked").equals("true")){
				ExtentTestManager.reportStepPass("Checkbox '"+ strTestObject +"' is Checked successfully.");
				elementStatus = true;
			}
			else if(selectByLocatorType(getValueFromPOM).getAttribute("checked").equals(null)){
				ExtentTestManager.reportStepFail(driver,"Checkbox '"+ strTestObject +"' is Unchecked.", true);
				elementStatus = false;
			}
		}catch(StaleElementReferenceException e){
				return webCheckBoxCheckStatus(getValueFromPOM, strTestObject);
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Exception occured while verifying the webCheckBoxCheckStatus. "+e, true);
			elementStatus = false;
		}
		return elementStatus;
	}

	public synchronized boolean webCheckBoxUnCheckStatus(String getValueFromPOM, String strTestObject) {
		boolean elementStatus = false;
		try{
			if(selectByLocatorType(getValueFromPOM).getAttribute("disabled").equals("true")){
				ExtentTestManager.reportStepPass("Checkbox '"+ strTestObject +"' is UNCHECKED successfully.");
				elementStatus = true;
			}
			else{
				ExtentTestManager.reportStepFail(driver, "Checkbox '"+ strTestObject +"' is not UNCHECKED.", true);
				elementStatus = false;
			}
		}catch(StaleElementReferenceException e){
			return webCheckBoxUnCheckStatus(getValueFromPOM, strTestObject);
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Exception occured while verifying the webCheckBoxUnCheckStatus. "+e, true);
			elementStatus = false;
		}
		return elementStatus;
	}

	public synchronized boolean webCheckBoxStatus(String getValueFromPOM, String strTestObject) {
		boolean elementStatus = false;
		try{
			if(selectByLocatorType(getValueFromPOM).getAttribute("value").equals("on")){
				ExtentTestManager.reportStepPass("Checkbox '"+ strTestObject +"' status is ON");
				elementStatus = true;
			}
			else{
				ExtentTestManager.reportStepFail(driver,"Checkbox '"+ strTestObject +"' status is OFF", true);
				elementStatus = false;
			}

		}catch(StaleElementReferenceException e){
			return webCheckBoxStatus(getValueFromPOM, strTestObject);
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Exception occured while verifying the webCheckBoxStatus. "+e, true);
			elementStatus = false;
		}
		return elementStatus;
	}

	public synchronized boolean WebCheckboxOFF(String getValueFromPOM, String strTestObject){
		boolean elementStatus=false;
		try {
			if ((selectByLocatorType(getValueFromPOM)).isSelected())
			{
				selectByLocatorType(getValueFromPOM).click();
			}
			ExtentTestManager.reportStepPass("Checkbox '"+ strTestObject +"' is  UNCHECKED successfully.");
			elementStatus=true;
		} catch (StaleElementReferenceException e) {
			return WebCheckboxOFF(getValueFromPOM, strTestObject);
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Element '"+ strTestObject +"'  is not UNCHECKED. Exception is "+e, true); 
			log.info("No Element Found to check" + e);
			elementStatus=false;
		}
		return elementStatus;
	}

	public synchronized boolean WebElementEmpty(String getValueFromPOM, String strTestObject ){
		boolean elementStatus=false;
		try {
			if ((selectByLocatorType(getValueFromPOM)).getAttribute("value").equals(""))
			{
				ExtentTestManager.reportStepPass("Element '"+ strTestObject +"'s values is Empty.");
				elementStatus=true;
			}
		} catch (StaleElementReferenceException e) {
			return WebElementEmpty(getValueFromPOM, strTestObject);
		}  catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Exception occured while verfiying the WebElementEmpty. Exception is "+e , true); 
			elementStatus=false;
		}
		return elementStatus;
	}

	public synchronized boolean WebElementLengthCompare(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag ){
		int actualResult;
		int expectedresult;
		String strData=null;
		boolean WebElementLengthCompare=false;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in test data sheet.", false);
				return false;
			}

			expectedresult = Integer.parseInt(selectByLocatorType(getValueFromPOM).getAttribute("maxlength"));
			selectByLocatorType(getValueFromPOM).sendKeys(strData);
			actualResult = selectByLocatorType(getValueFromPOM).getAttribute("value").length();

			if(actualResult == expectedresult){
				WebElementLengthCompare=true;
				ExtentTestManager.reportStepPass("Element '"+strTestObject+"'s  actual length '" + actualResult + "' matches the expected length '" + expectedresult + "'");
			}else{
				WebElementLengthCompare=false;
				ExtentTestManager.reportStepFail(driver,"Element '"+strTestObject+"'s  actual length '" + actualResult + "' does not matches with the Expected length '" + expectedresult + "'", true);
			}
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver, "Exception occured while getting the maxlength of the object  '"+ strTestObject +"'. Exception is "+e, true);
			WebElementLengthCompare=false;
			log.info("No Element Found to compare Text : " + e);
		}
		return WebElementLengthCompare;
	}

	public synchronized boolean Statictextcompare(String Expected_Value, int strExecEventFlag){
		boolean elementStatus= false;
		String expectedtext = null;
		String actualtext = null;
		try{
			if(strExecEventFlag==1){
				expectedtext=getTestData(testDataFilePathStatic, testComponentNameStatic,Expected_Value,gblrecordsCounterStatic);
			}

			if(expectedtext==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}

			new WebDriverWait(driver,30).until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			actualtext = alert.getText();
			String actual = actualtext.replaceAll("[\n\r]", "");
			String exp = expectedtext.replaceAll("[\n\r]", "");

			if(actual.equalsIgnoreCase(exp))
			{
				elementStatus=true;
				ExtentTestManager.reportStepPass("Actual value '"+ actualtext +"' matches with the expected value '"+ expectedtext +"'.");
			} else {
				elementStatus=false;
				ExtentTestManager.reportStepFail(driver, "Actual value '"+ actualtext + "' doesn't matches with the expected value '"+ expectedtext +"'.", true);	
			}
			alert.accept();
		} catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while retriveing the text from Popup alert.Error description is : "+ e.getMessage() +".", true);
			log.info("Statictextcompare Error : " + e);
		}
		return elementStatus;
	}

	public synchronized boolean staticAlertDismiss(String expectedValue, int strExecEventFlag) {
		boolean elementStatus = false;
		String actualText = null;
		String expectedText = null;
		
		try{
			if(strExecEventFlag==1){
				expectedText = getTestData(testDataFilePathStatic, testComponentNameStatic, expectedValue, gblrecordsCounterStatic);
			}

			if(expectedText == null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}

			new WebDriverWait(driver, 30).until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			actualText = alert.getText();
			alert.dismiss();

			if(actualText.equalsIgnoreCase(expectedText))
			{
				elementStatus=true;
				ExtentTestManager.reportStepPass("Actual value '"+ actualText +"' matches with the expected value '"+expectedText+"' and Alert is dismissed successfully.");
			} else {
				elementStatus=false;
				ExtentTestManager.reportStepFail(driver, "Actual value '"+ actualText +"' doesn't matches with the expected value '"+ expectedText +"' and alert is dismissed", true);	
			}
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Exception occured while staticAlertDismiss. Exception is "+e , true);
			elementStatus = false;
		}
		return elementStatus;
	}

	public synchronized boolean WebButtonClickEnter(String getValueFromPOM, String strTestObject) {
		boolean WebButtonClickEnter= false;
		try {
			selectByLocatorType(getValueFromPOM).sendKeys("\n");
			Thread.sleep(1000);
			ExtentTestManager.reportStepPass("'Button "+strTestObject+"' is clicked successfully.");
			WebButtonClickEnter=true;
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"'Button "+strTestObject+"' is not clicked. Exception is "+e, true); 	
			WebButtonClickEnter=false;
		}
		return WebButtonClickEnter;
	}

	public synchronized boolean Waituntilexpectedtext(String getValueFromPOM, String strTestObject) {
		boolean Waituntilexpectedtext= false;
		String Element_Text = null;
		try{
			FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver);
			fWait.withTimeout(300, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(selectByLocatorType(getValueFromPOM)));
			Element_Text = selectByLocatorType(getValueFromPOM).getText();
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while getting the text from the element '"+strTestObject+"' .Error description is : "+ e.getMessage() +"." , true); 
			Waituntilexpectedtext=false;
		}
		if(!(Element_Text==null)){
			ExtentTestManager.reportStepPass("Text '"+Element_Text+ "' is present in the Element '"+strTestObject+"'");
			Waituntilexpectedtext= true;
		}else{
			ExtentTestManager.reportStepFail(driver,"No Text is present in the element '"+strTestObject+"'", true);
			Waituntilexpectedtext= false;
		}
		return Waituntilexpectedtext;
	}

	public synchronized boolean WebElementRetrieveValuesEnvVar(String getValueFromPOM, String strTestObject,String strSplitString,int NumberOfCharacters,String EnvVariableName,int strExecEventFlag) {
		boolean WebElementRetrieveValuesEnvVar=false;
		String SplitString = null;
		String ContentToStore = null;
		try{
			if(strExecEventFlag==1){
				SplitString = getTestData(testDataFilePathStatic, testComponentNameStatic,strSplitString,gblrecordsCounterStatic);
			}
			if(SplitString==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}
			String RetrievedMessage=selectByLocatorType(getValueFromPOM).getText().trim();
			String[] split1Content=RetrievedMessage.split(SplitString);
			ContentToStore=split1Content[1].substring(0,NumberOfCharacters);
			Runtimevalue.setProperty(EnvVariableName, ContentToStore);
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Error in retrieving the value from webelement  :'"+strTestObject+"'and the error description is :"+e.getMessage(), true);
			WebElementRetrieveValuesEnvVar=false;
		}
		if(!(ContentToStore==null)){
			ExtentTestManager.reportStepPass("The Value '" +ContentToStore+ "' is stored in the Environment Variable: '"+ EnvVariableName +"' successfully");
			WebElementRetrieveValuesEnvVar=true;
		}else{
			ExtentTestManager.reportStepFail(driver, "No Value is stored in Environment Variable: '"+ EnvVariableName +"'", true);
			WebElementRetrieveValuesEnvVar=false;
		}
		return WebElementRetrieveValuesEnvVar;
	}

	public synchronized boolean WebEditDisabled(String getValueFromPOM, String strTestObject){
		boolean WebEditDisabled= false;
		boolean Enable_Flag = true;
		try {
			Enable_Flag = selectByLocatorType(getValueFromPOM).isEnabled();

			if(Enable_Flag==false){
				ExtentTestManager.reportStepPass("Text field '" +strTestObject+ "' is disabled");
				WebEditDisabled=true;
			}else{
				ExtentTestManager.reportStepFail(driver, "Text field '" +strTestObject+ "' is not disabled", true);
				WebEditDisabled=false;
			}
		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver, "Error occured while getting the 'disabled' property of the input field '"+strTestObject+"'and the error description is :"+e.getMessage(), true);
			WebEditDisabled=false;
		}
		return WebEditDisabled;
	}

	public synchronized boolean WebEditTextCompare(WebElement element,String strTestObject,String strexpectedValue,int strExecEventFlag) {
		boolean WebEditTextCompare=false;
		String ActualValue=null;
		String ExpectedValue=null;
		try {
			if(strExecEventFlag==1){
				ExpectedValue = getTestData(testDataFilePathStatic, testComponentNameStatic,strexpectedValue,gblrecordsCounterStatic);
			}
			if(ExpectedValue==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in test data sheet.", false);
				return false;
			}
			ActualValue = element.getAttribute("value");
			if((ActualValue.trim()).equals(ExpectedValue.trim())){
				ExtentTestManager.reportStepPass("Actual value '" +ActualValue+ "' matches with the expected value '"+ExpectedValue+ "' in the input field '"+strTestObject+"'");
				WebEditTextCompare=true;
			}else{
				ExtentTestManager.reportStepFail(driver," Actual value '" +ActualValue+ "' does not match with the expected value '"+ExpectedValue+ "' in the input field '"+strTestObject+"'", true);				
				WebEditTextCompare=false;
			}
		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver, "Error occured while getting the text from the input field '"+strTestObject+"'and the error description is :"+e.getMessage(), true);
			WebEditTextCompare=false;
		}
		return WebEditTextCompare;
	}

	public synchronized boolean getTicketIDDynamicValues(String getValueFromPOM, String strTestObject,String strColumnName, int strExecEventFlag ){
		String actualResult = null;
		String expectedText = null;
		String dynamicValue = null;
		boolean getTicketIDDynamicValues = false;

		try{
			if(strExecEventFlag == 1){
				expectedText = getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			actualResult = selectByLocatorType(getValueFromPOM).getText();

			Pattern pattern = Pattern.compile(expectedText);
			Matcher matcher = pattern.matcher(actualResult);

			while(matcher.find()){
				dynamicValue = matcher.group(1)+matcher.group(2)+matcher.group(3);
				log.info("dynamicValue :"+dynamicValue);

			}
		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while getting the text from the WebElement '"+strTestObject+"'and the error description is :"+e.getMessage(), true);
			getTicketIDDynamicValues = false;
		}
		return getTicketIDDynamicValues;
	}

	public synchronized boolean WebElementTextCompareEnvVar(String getValueFromPOM, String strTestObject,String strEnvironmentVariable){
		boolean WebElementTextCompareEnvVar=false;
		String actualResult=null;
		String strData=null;
		try{
			strData = Runtimevalue.getProperty(strEnvironmentVariable);

			if(strData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the datasheet.", false);
				return false;
			}

			actualResult = selectByLocatorType(getValueFromPOM).getText();
			if((actualResult.trim()).equals(strData.trim())){
				ExtentTestManager.reportStepPass("Actual value '" +actualResult+ "' matches with the Expected value '"+strData+ "' in the input field '"+strTestObject+"");
				WebElementTextCompareEnvVar=true;
			}else{
				ExtentTestManager.reportStepFail(driver,"Actual value '" +actualResult+ "' does not match with the Expected value '"+strData+ "' in the input field '"+strTestObject+"", true);
				WebElementTextCompareEnvVar=false;
			}
		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while Comparing Text from Env variable. Error description is :"+e.getMessage(), true);
			WebElementTextCompareEnvVar=false;
		}
		return WebElementTextCompareEnvVar;
	}

	public synchronized boolean SQLDBCheckValueExist(String sqltablename, String strsqlcolumnname,String strsqlcondition,int strExecEventFlag){
		boolean functionStatus=false;
		String query;
		String check;
		String Table_name;
		String Column_name;
		String SQL_condition;
		String Actual_Value;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
			}else{
				Table_name=sqltablename;
				Column_name=strsqlcolumnname;
				SQL_condition=strsqlcondition;
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";

			//Check for Record Available
			ResultSet rs_SQLServerCheck = stmt.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = stmt.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' exist for the SQL Query "+query+".", false);
					functionStatus= false;
			}else{
				ResultSet rs_SQLServer = stmt.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1).trim();

				if(!(Actual_Value==null)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' exist for the SQL Query "+query+".");
					functionStatus= true;
				}else if(Actual_Value==null){
					ExtentTestManager.reportStepFail(driver,"Value does not exist for the SQL Query "+query+"'", false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in SQL query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean RRBSDBCheckValueExist(String sqltablename, String strsqlcolumnname,String strsqlcondition,int strExecEventFlag){
		boolean functionStatus=false;
		String query;
		String check;
		String Table_name;
		String Column_name;
		String SQL_condition;
		String Actual_Value;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
			}else{
				Table_name=sqltablename;
				Column_name=strsqlcolumnname;
				SQL_condition=strsqlcondition;
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";

			//Check for Record Available
			ResultSet rs_SQLServerCheck = rrbsstatement.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = rrbsstatement.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' exist for the RRBS Query "+query+".", false);
					functionStatus= false;
			}else{
				ResultSet rs_SQLServer = rrbsstatement.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1).trim();

				if(!(Actual_Value==null)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' exist for the RRBS Query "+query+".");
					functionStatus= true;
				}else if(Actual_Value==null){
					ExtentTestManager.reportStepFail(driver,"Value does not exist for the RRBS Query "+query+"'", false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in RRBS query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean Web_MainMenu_Click(String getValueFromPOM, String strTestObject) {
		boolean Web_MainMenu_Click= false;
		try {
			((JavascriptExecutor)driver).executeScript("arguments[0].click()",selectByLocatorType(getValueFromPOM));
			ExtentTestManager.reportStepPass("Main menu link '"+strTestObject+"' is clicked successfully");
			Web_MainMenu_Click=true;
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Main menu link '"+strTestObject+"' is not clicked", true); 
			Web_MainMenu_Click=false;
		}
		return Web_MainMenu_Click;
	}

	public synchronized boolean EXIBSDBOpenConnection(String dbserver, String portnumber, String dbname, String dbusername, String dbpassword){
		boolean elementStatus= false;
		String serverName = property.getProperty(dbserver);
		String portNumber = property.getProperty(portnumber);
		String sid = property.getProperty(dbname);
		String dbUrl = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;                  
		String username = property.getProperty(dbusername);   
		String password = property.getProperty(dbpassword); 

		if(serverName==null || portNumber==null || sid==null || username==null || password==null){
			ExtentTestManager.reportStepFail(driver,"Required details are not provided in test data sheet.", false);
			return false;
		}
		try {
			String driverName = "oracle.jdbc.OracleDriver";
			Class.forName(driverName);         
			exibsconnection = DriverManager.getConnection(dbUrl, username, password);
			exibsstatement = exibsconnection.createStatement(); 
			elementStatus=true;
			ExtentTestManager.reportStepPass("EXIBS DB Connection is established Successfully.");

		} catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while connecting to the EXIBS DB Server.Error description is : "+ e.getMessage() +".", false);
		}
		return elementStatus;
	}

	public synchronized boolean EXIBSDBDelete(String rrbstablename, String rrbscondition, int strExecEventFlag){
		String tablename = null;
		String condition = null;
		try{
			if(strExecEventFlag==1){
				tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbstablename,gblrecordsCounterStatic);
				condition=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbscondition,gblrecordsCounterStatic);
			}
			if(tablename==null || condition==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in test data sheet.", false);
				return false;
			}
			String check = "select * from "+tablename +" where "+condition;
			String query = "Delete from "+ tablename +" where "+ condition;
			ResultSet rs = exibsstatement.executeQuery(check);
			int temp = 0;
			while(rs.next()){
				temp++;
			}
			if(temp > 0){
				exibsstatement.execute(query); 
				ExtentTestManager.reportStepPass("EXIBS Delete Query "+ query +" executed successfully");
				return true;
			}
			else{
				ExtentTestManager.reportStepPass("EXIBS Delete Query "+ query +" has NO RECORDS available in DB");
				return true;
			}
		} catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver, "Error occured while executing the EXIBS query.Error description is : "+ e.getMessage() +".", false);
			return false;
		}
	}

	public synchronized boolean EXIBSDBSelect(String sqltablename, String strsqlcolumnname, String strsqlcondition, String strExpectedvalue, int strExecEventFlag){
		boolean functionStatus=false;
		String query;
		String check;
		String Table_name;
		String Column_name;
		String SQL_condition;
		String Expected_value;
		String Actual_Value;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
				Expected_value=getTestData(testDataFilePathStatic, testComponentNameStatic, strExpectedvalue, gblrecordsCounterStatic);
			}else{
				Table_name=sqltablename;
				Column_name=strsqlcolumnname;
				SQL_condition=strsqlcondition;
				Expected_value=strExpectedvalue;
			}

			if(Table_name==null || Column_name==null || SQL_condition==null||Expected_value==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			query = "SELECT "+Column_name+" FROM "+Table_name+" WHERE "+SQL_condition+" order by 1 desc";
			check = "SELECT "+Column_name+" FROM "+Table_name+" WHERE "+SQL_condition+" order by 1 desc";

			//Check for Record Available
			ResultSet rs_SQLServerCheck = exibsstatement.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = exibsstatement.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the EXIBS Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the EXIBS Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else if(!(rs_SQLServerCheck.wasNull())){

				ResultSet rs_SQLServer = exibsstatement.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1).trim();

				if (Expected_value.contains("CURRENT_DATE")){
					String []ExpectedvalueWithFormat=Expected_value.split("#");
					String db_Date = Actual_Value.split(" ")[0];
					Actual_Value=db_Date;
					Date date=new Date();			
					SimpleDateFormat dateformat=new SimpleDateFormat(ExpectedvalueWithFormat[1]);
					Expected_value=dateformat.format(date);
				}

				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the EXIBS Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Actual_Value.equalsIgnoreCase(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the EXIBS Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in EXIBS query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean EXIBSDBUpdate(String sqltablename, String strsqlcolumnname,String strsqlcolumnvalue,String strsqlcondition,int strExecEventFlag){
		String Table_name;
		String Column_name;
		String Column_Value;
		String SQL_condition;
		String query;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				Column_Value=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnvalue, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
			}else{
				Table_name=sqltablename;
				Column_name=strsqlcolumnname;
				Column_Value=strsqlcolumnvalue;
				SQL_condition=strsqlcondition;
			}

			if(Table_name==null || Column_name==null || Column_Value==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			if(Column_Value.contains("to_date")){
				query = "update "+Table_name+" set "+Column_name+"="+Column_Value+" where "+SQL_condition;
			}else{
				if(Column_Value.equalsIgnoreCase("null")){
					query = "update "+Table_name+" set "+Column_name+"=null where "+SQL_condition;
				}else{
					query = "update "+Table_name+" set "+Column_name+"='"+Column_Value+"' where "+SQL_condition;
				}
			}

			exibsstatement.execute(query);
			ExtentTestManager.reportStepPass("EXIBS Update Query  "+ query + "  executed successfully.");
			return true;
		} catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver,"Error occured while executing EXIBS Update Query. Error description is : "+ e.getMessage(), false);
			return false;
		}
	}

	public synchronized boolean EXIBSDBCloseConnection(){
		boolean elementStatus= false;
		try {
			exibsconnection.close(); 
			exibsstatement.close();
			elementStatus=true;
			ExtentTestManager.reportStepPass("EXIBS DB Connection is disconnected successfully.");
		} catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while closing the EXIBS DB connection.Error description is : "+ e.getMessage() +".", false);
		}
		return elementStatus;
	}

	public synchronized boolean XMLValueUpdate(String filePath, String key, String value, int strExecEventFlag) {
		boolean elementStatus = false;
		String Path = null;
		String validKey = null;
		String validValue = null;
		String prevValue = null;
		String newValue = null;
		try{
			if(strExecEventFlag == 1){
				Path = property.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic,filePath, gblrecordsCounterStatic));
				validKey = getTestData(testDataFilePathStatic, testComponentNameStatic,key,gblrecordsCounterStatic);
				validValue = property.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic,value, gblrecordsCounterStatic));
			}

			if(Path==null || validKey==null || validValue==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}

			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			File input = new File("//\\" +Path);
			Document doc = builder.parse(input);
			XPath xpath = XPathFactory.newInstance().newXPath();
			//Xpath Expression
			String expression = "//*[@key='"+validKey+"']";
			//Using node and elements to get the all child attributes 
			NodeList nodeList = (NodeList) xpath.compile(expression).evaluate(doc, XPathConstants.NODESET);
			for(int i = 0; i<nodeList.getLength(); i++){
				Node nNode = nodeList.item(i);
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
					Element eElement = (Element) nNode;
					if(eElement.getAttribute("value") != null){
						prevValue = eElement.getAttribute("value");
						log.info("Current Value :"+prevValue);
						eElement.setAttribute("value", validValue);
						newValue = eElement.getAttribute("value");
						log.info("Updated Value :"+newValue);
					} else {
						log.info("Given attribute value is not available");
					}
				}
				// write the content into config file
				TransformerFactory transFormerFactory = TransformerFactory.newInstance();
				Transformer transFormer = transFormerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(input);
				transFormer.transform(source, result);
				elementStatus = true;
			}
		}catch(Exception e){
			e.printStackTrace();
			elementStatus = false;
		}

		if(elementStatus){
			ExtentTestManager.reportStepPass("XML Node is updated with the value '"+newValue+"' for the key '"+validKey+"' successfully");
		}else{
			ExtentTestManager.reportStepFail(driver,"XML Node is not updated with the value '"+newValue+"' for the key '"+validKey+"'" , true);
		}
		return elementStatus;
	}

	public synchronized boolean deleteLogFiles(String filePath, String startsWith, String endsWith, int strExecEventFlag) {
		boolean functionStatus = true;
		String path = null;
		String startValue = null;
		String endValue = null;
		int flag = 0;

		try{
			if(strExecEventFlag == 1){
				path = property.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic, filePath, gblrecordsCounterStatic));
				startValue = getTestData(testDataFilePathStatic, testComponentNameStatic, startsWith,gblrecordsCounterStatic);
				endValue = getTestData(testDataFilePathStatic, testComponentNameStatic, endsWith,gblrecordsCounterStatic);

			}
			if(path==null || startValue==null || endValue==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}
			File directory = new File("//\\" +path);
			for(File listOfFiles : directory.listFiles()){
				if(listOfFiles.getName().startsWith(startValue) && listOfFiles.getName().endsWith(endValue)){
					listOfFiles.delete();
					flag = 1;
				}
			}

		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occurred while deleting the file with expected file Start Name '"+startValue+"'" , false);
			functionStatus = false;
		}

		if(flag == 1){
			ExtentTestManager.reportStepPass("Files with names starting like '"+startValue+"' and file type as '"+endValue+"' are deleted sucessfully.");
		} else {
			ExtentTestManager.reportStepPass("Files with names starting like '"+startValue+"' and file type as '"+endValue+"' are not present in the given Path : '"+ path +"'.");
		}
		return functionStatus;
	}

	public synchronized boolean readLogFiles(String filePath, String startsWith, String endsWith, String textName, int strExecEventFlag) {
		boolean functionStatus = false;
		String path = null;
		String startValue = null;
		String endValue = null;
		String textValue = null;
		File[] listOfFile = null;
		String fileName = null;
		String actualText = null;
		Pattern pattern = null;
		try{
			if(strExecEventFlag == 1){
				path = property.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic, filePath, gblrecordsCounterStatic));
				startValue = getTestData(testDataFilePathStatic, testComponentNameStatic,startsWith,gblrecordsCounterStatic);
				endValue = getTestData(testDataFilePathStatic, testComponentNameStatic,endsWith,gblrecordsCounterStatic);
				textValue = getTestData(testDataFilePathStatic, testComponentNameStatic,textName,gblrecordsCounterStatic);
			}

			if(path==null || startValue==null || endValue==null || textValue==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}

			//Get the filename by given startName and endName
			File directory = new File("//\\" +path);
			listOfFile = directory.listFiles();
			if(listOfFile.length != 0){
				for(int i = 0; i<listOfFile.length; i++){
					if(listOfFile[i].isFile()){
						fileName = listOfFile[i].getName();
						if(fileName.startsWith(startValue) && fileName.endsWith(endValue)){
							//Read the file and match the expected value

							File ff = new File("//\\" +path +fileName);
							String fileContent = IOUtils.toString(ff.toURI(),"UTF-8");
							actualText = "Text is not available in log file";
							pattern = Pattern.compile(textValue);
							Matcher matcher = pattern.matcher(fileContent);

							while(matcher.find()){
								actualText = matcher.group();
								log.info("Text is matched");
								ExtentTestManager.reportStepPass("Text '"+actualText+"' is matched with expected text in the filename of '"+startValue+"'");
								return true;
							}
						}
					}
				}
			}else{
				log.info("File is not available in directory");
				ExtentTestManager.reportStepFail(driver,"File is not available in directory" , true);
				return false;
			}
			log.info(actualText);
			functionStatus = false;
			ExtentTestManager.reportStepFail(driver,"Expected text '" + textValue + "' doesn't exist in the filename '"+startValue+"'" , false);

		}catch(Exception e){
			functionStatus = false;
			ExtentTestManager.reportStepFail(driver,"Error occurred while finding the expected text in the filename '"+startValue+"'" , false);
		}
		return functionStatus;
	}

	public synchronized boolean EXIBSDBConditionEnvVar(String SwitchCase, String exibstablename, String exibscolumnname, String exibscondition, String exibscolumnvalue, String strenvvar, int strLength, String strenvvartostore, int strExecEventFlag){
		boolean EXIBSDBConditionEnvVar= false;
		String tablename = null;
		String condition = null;
		String columnname = null;
		String columnvalue = null;
		String actualvalue = null;
		String envvar = null;
		String strvalue = null;
		String reqCondition = null;
		String query = null;
		switch (SwitchCase){

		case "Select" :
			try{
				
				if(strExecEventFlag==1){
					tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,exibstablename,gblrecordsCounterStatic);
					condition=getTestData(testDataFilePathStatic, testComponentNameStatic,exibscondition,gblrecordsCounterStatic);
					columnname=getTestData(testDataFilePathStatic, testComponentNameStatic,exibscolumnname,gblrecordsCounterStatic);
					columnvalue=getTestData(testDataFilePathStatic, testComponentNameStatic,exibscolumnvalue,gblrecordsCounterStatic);
				}
				if(tablename==null || condition==null || columnname==null ){
					ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
					return false;
				}
				envvar = Runtimevalue.getProperty(strenvvar);
				if(envvar==null){
					ExtentTestManager.reportStepFail(driver,    "Value in the environment variable : '"+ strenvvar +"' is empty.", false);
					return false;
				}
				strvalue = envvar.substring(envvar.length() - strLength);
				Runtimevalue.setProperty(strenvvartostore, strvalue);
				EXIBSDBConditionEnvVar=true;
				ExtentTestManager.reportStepPass("Value "+ strvalue + " is stored in the environment variable :"+ strenvvartostore +".");
				reqCondition = condition + " = '"+ strvalue +"'";
				//Query to Execute      
				query = "select "+ columnname +" from "+ tablename +" where "+ reqCondition;

				ResultSet exibsresultset = exibsstatement.executeQuery(query);
				while (exibsresultset.next()){
					actualvalue = exibsresultset.getString(1);
				}
				if(actualvalue.equalsIgnoreCase(columnvalue))
				{
					EXIBSDBConditionEnvVar=true;
					ExtentTestManager.reportStepPass("Actual value "+ actualvalue + " matches with the expected value "+ columnvalue +" for the Query "+query+".");
				} else {
					EXIBSDBConditionEnvVar=false;
					ExtentTestManager.reportStepFail(driver,"Actual value : "+ actualvalue + " does not matches with the expected value "+ columnvalue +" for the Query "+query+".", false);	
				}
			} catch (Exception e) { 
				EXIBSDBConditionEnvVar=false;
				ExtentTestManager.reportStepFail(driver,"Error occured while executing the EXIBS query. Error description is : "+ e.getMessage() +".", false);
			}
			break;
		
		case "Delete" :
			try{
				if(strExecEventFlag==1){
					tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,exibstablename,gblrecordsCounterStatic);
					condition=getTestData(testDataFilePathStatic, testComponentNameStatic,exibscondition,gblrecordsCounterStatic);
				}
				if(tablename==null || condition==null ){
					ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
					return false;
				}
				envvar = Runtimevalue.getProperty(strenvvar);
				if(envvar==null){
					ExtentTestManager.reportStepFail(driver,    "Value in the environment variable : '"+ strenvvar +"' is empty.", false);
					return false;
				}
				strvalue = envvar.substring(envvar.length() - strLength);
				Runtimevalue.setProperty(strenvvartostore, strvalue);
				reqCondition = condition + " = '"+ strvalue +"'";
				query = "Delete from "+ tablename +" where "+ reqCondition;
				exibsstatement.executeQuery(query);
				EXIBSDBConditionEnvVar=true;
				ExtentTestManager.reportStepPass( "Delete Query "+query+" is executed.");

			} catch (Exception e) { 
				EXIBSDBConditionEnvVar=false;
				ExtentTestManager.reportStepFail(driver, "Error occured while executing the EXIBS delete query. Error description is : "+ e.getMessage() +".", false);
			}
			break;
		}

		return EXIBSDBConditionEnvVar;
	}

	public synchronized boolean SQLDBConditionEnvVar(String SwitchCase, String sqltablename, String sqlcolumnname, String sqlcondition, String sqlcolumnvalue, String strenvvar, int strLength, String strenvvartostore, int strExecEventFlag){
		boolean SQLDBConditionEnvVar= false;
		String tablename = null;
		String condition = null;
		String columnname = null;
		String columnvalue = null;
		String actualvalue = null;
		String envvar = null;
		String strvalue = null;
		String reqCondition = null;
		String query = null;
		switch (SwitchCase){

		case "Select" :
			try{

				if(strExecEventFlag==1){
					tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
					condition=getTestData(testDataFilePathStatic, testComponentNameStatic,sqlcondition,gblrecordsCounterStatic);
					columnname=getTestData(testDataFilePathStatic, testComponentNameStatic,sqlcolumnname,gblrecordsCounterStatic);
					columnvalue=getTestData(testDataFilePathStatic, testComponentNameStatic,sqlcolumnvalue,gblrecordsCounterStatic);
				}

				if(tablename==null || condition==null || columnname==null ){
					ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
					return false;
				}
				envvar = Runtimevalue.getProperty(strenvvar);
				if(envvar==null){
					ExtentTestManager.reportStepFail(driver,    "Value in the environment variable : '"+ strenvvar +"' is empty.", false);
					return false;
				}
				strvalue = envvar.substring(envvar.length() - strLength);
				Runtimevalue.setProperty(strenvvartostore, strvalue);
				SQLDBConditionEnvVar=true;
				ExtentTestManager.reportStepPass( "Value '"+ strvalue + "' is stored in the environment variable :"+ strenvvartostore +".");
				reqCondition = condition + " = '"+ envvar +"'";
				query = "select "+ columnname +" from "+ tablename +" where "+ reqCondition;
				ResultSet rs_SQLServer = stmt.executeQuery(query);
				while (rs_SQLServer.next()){
					actualvalue = rs_SQLServer.getString(1);
				}
				if(actualvalue.equalsIgnoreCase(columnvalue))
				{
					SQLDBConditionEnvVar=true;
					ExtentTestManager.reportStepPass("Actual value '"+ actualvalue + "' for the Query '"+query+"' matches with the expected value "+ columnvalue +".");
				} else {
					SQLDBConditionEnvVar=false;
					ExtentTestManager.reportStepFail(driver,"Actual value '"+ actualvalue + "' for the Query '"+query+"' does not match with the expected value "+ columnvalue +".", false);	
				}
			} catch (Exception e) { 
				SQLDBConditionEnvVar=false;
				ExtentTestManager.reportStepFail(driver,    "Error occured while executing the EXIBS query.Error description is : "+ e.getMessage() +".", false);
			}
			break;
		
		case "Delete" :
			try{
				if(strExecEventFlag==1){
					tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
					condition=getTestData(testDataFilePathStatic, testComponentNameStatic,sqlcondition,gblrecordsCounterStatic);
				}
				if(tablename==null || condition==null ){
					ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
					return false;
				}

				envvar = Runtimevalue.getProperty(strenvvar);
				if(envvar==null){
					ExtentTestManager.reportStepFail(driver,    "Value in the environment variable : '"+ strenvvar +"' is empty.", false);
					return false;
				}
				strvalue = envvar.substring(envvar.length() - strLength);
				Runtimevalue.setProperty(strenvvartostore, strvalue);
				reqCondition = condition + " = '"+ envvar +"'";
				String checkquery = "Select * from "+ tablename +" where "+ reqCondition;
				//Query to Execute      
				query = "Delete from "+ tablename +" where "+ reqCondition;
				ResultSet rs_SQLServer = stmt.executeQuery(checkquery);
				int temp=0;	
				while(rs_SQLServer.next()){
					temp++;
				}

				if(temp >= 1){
					stmt.executeQuery(query);
					SQLDBConditionEnvVar=true;
					ExtentTestManager.reportStepPass("Delete Query '"+query+"' is executed successfully");
				} else {
					SQLDBConditionEnvVar=true;
					ExtentTestManager.reportStepPass("No Record available to Delete for the Query '"+query+"'.");	
				}
			} catch (Exception e) { 
				SQLDBConditionEnvVar=false;
				ExtentTestManager.reportStepFail(driver,"Error occured while executing the SQL query.Error description is : "+ e.getMessage() +".", false);
			}
			break;
		}
		return SQLDBConditionEnvVar;
	}

	public synchronized boolean RRBSDBConditionEnvVar(String SwitchCase, String rrbstablename, String rrbscolumnname, String rrbscondition, String rrbscolumnvalue, String strenvvar, int strExecEventFlag){
		boolean RRBSDBConditionEnvVar= false;
		String tablename = null;
		String condition = null;
		String columnname = null;
		String columnvalue = null;
		String actualvalue = null;
		String envvar = null;
		String reqCondition = null;
		String query = null;
		String query2 = null;
		switch (SwitchCase){

		case "Select" :
			try{
				if(strExecEventFlag==1){
					tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbstablename,gblrecordsCounterStatic);
					condition=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbscondition,gblrecordsCounterStatic);
					columnname=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbscolumnname,gblrecordsCounterStatic);
					columnvalue=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbscolumnvalue,gblrecordsCounterStatic);
				}
				if(tablename==null || condition==null || columnname==null ){
					ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
					return false;
				}
				envvar = Runtimevalue.getProperty(strenvvar);
				if(envvar==null){
					ExtentTestManager.reportStepFail(driver,    "Value in the environment variable : '"+ strenvvar +"' is empty.", false);
					return false;
				}
				reqCondition = condition + " = '"+ envvar +"'";
				query = "select "+ columnname +" from "+ tablename +" where "+ reqCondition;
				ResultSet rrbsresultset = rrbsstatement.executeQuery(query);
				while (rrbsresultset.next()){
					actualvalue = rrbsresultset.getString(1);
				}
				if(actualvalue.equalsIgnoreCase(columnvalue))
				{
					RRBSDBConditionEnvVar=true;
					ExtentTestManager.reportStepPass("Actual value '"+ actualvalue + "' for the Query '"+query+"' matches with the expected value '"+ columnvalue +"'.");
				} else {
					RRBSDBConditionEnvVar=false;
					ExtentTestManager.reportStepFail(driver, "Actual value '"+ actualvalue + "' for the Query '"+query+"' does not match with the expected value '"+ columnvalue +"'.", false);	
				}
			} catch (Exception e) { 
				RRBSDBConditionEnvVar=false;
				ExtentTestManager.reportStepFail(driver,    "Error occured while executing the EXIBS query. Error description is : "+ e.getMessage() +".", false);
			}

			break;
		case "Delete" :
			try{
				if(strExecEventFlag==1){
					tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbstablename,gblrecordsCounterStatic);
					condition=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbscondition,gblrecordsCounterStatic);
				}
				if(tablename==null || condition==null ){
					ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
					return false;
				}

				envvar = Runtimevalue.getProperty(strenvvar);
				if(envvar==null){
					ExtentTestManager.reportStepFail(driver,"Value in the environment variable : '"+ strenvvar +"' is empty.", false);
					return false;
				}
				reqCondition = condition + " = '"+ envvar +"'";
				query = "Delete from "+ tablename +" where "+ reqCondition;
				rrbsstatement.executeQuery(query);
				RRBSDBConditionEnvVar=true;
				ExtentTestManager.reportStepPass("Delete Query '"+query+"' is executed successfully");

			} catch (Exception e) { 
				RRBSDBConditionEnvVar=false;
				ExtentTestManager.reportStepFail(driver,    "Error occured while executing the EXIBS query. Error description is : "+ e.getMessage() +".", false);
				log.info("RRBSDBConditionEnvVar Error : " + e);
			}
			break;

		case "Update" :
			try{

				if(strExecEventFlag==1){
					tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbstablename,gblrecordsCounterStatic);
					condition=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbscondition,gblrecordsCounterStatic);
					columnname=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbscolumnname,gblrecordsCounterStatic);
					columnvalue=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbscolumnvalue,gblrecordsCounterStatic);
				}
				if(tablename==null || condition==null || columnname==null || columnvalue==null ){
					ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
					return false;
				}
				envvar = Runtimevalue.getProperty(strenvvar);
				if(envvar==null){
					ExtentTestManager.reportStepFail(driver,    "Value in the environment variable : '"+ strenvvar +"' is empty.", false);
					return false;
				}
				reqCondition = condition + " = '"+ envvar +"'";
				query = "select "+ columnname +" from "+ tablename +" where "+ reqCondition;
				ResultSet rrbsresultset = rrbsstatement.executeQuery(query);
				if (rrbsresultset.next()){
					query2 = "update "+ tablename +" set "+ columnname +" = "+ columnvalue +" where "+ reqCondition +"";
					rrbsstatement.executeQuery(query2);
					RRBSDBConditionEnvVar=true;
					ExtentTestManager.reportStepPass("Column Value '"+ columnvalue + "' is updated successfully for the Query '"+query2+"'.");
				}else {
					RRBSDBConditionEnvVar=false;
					ExtentTestManager.reportStepFail(driver,"No records found to update for the Query '"+query2+"'.", false);	
				}
			} catch (Exception e) { 
				RRBSDBConditionEnvVar=false;
				ExtentTestManager.reportStepFail(driver,    "Error occured while executing the EXIBS query.Error description is : "+ e.getMessage() +".", false);
			}
			break;
		}
		return RRBSDBConditionEnvVar;
	}

	public synchronized boolean RetrieveRRBSValueStoresInEnvVar(String rrbstablename, String rrbscolumnname, String rrbscondition, String strenvvar, int strExecEventFlag){
		boolean RetrieveRRBSValueStoresInEnvVar = false;
		String tablename = null;
		String condition = null;
		String columnname = null;
		String actualvalue = null;
		try{

			if(strExecEventFlag==1){
				tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbstablename,gblrecordsCounterStatic);
				condition=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbscondition,gblrecordsCounterStatic);
				columnname=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbscolumnname,gblrecordsCounterStatic);
			}

			if(tablename==null || condition==null || columnname==null ){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
				return false;
			}

			//Query to Execute      
			String query = "select "+ columnname +" from "+ tablename +" where "+ condition;
			ResultSet rrbsresultset = rrbsstatement.executeQuery(query);
			while (rrbsresultset.next()){
				actualvalue = rrbsresultset.getString(1);
			}
			Runtimevalue.setProperty(strenvvar, actualvalue);
			RetrieveRRBSValueStoresInEnvVar = true;
			ExtentTestManager.reportStepPass("Column value '"+ actualvalue + "' is stored in the environment variable '"+ strenvvar +"'.");

		} catch (Exception e) { 
			RetrieveRRBSValueStoresInEnvVar = false;
			ExtentTestManager.reportStepFail(driver, "Error occured while executing the RRBS query. Error description is : "+ e.getMessage() +".", false);
		}
		return RetrieveRRBSValueStoresInEnvVar;
	}

	public synchronized boolean WebElementValueCompareFromEnv(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag ){
		String actualResult=null;
		String strData=null;
		boolean WebElementValueCompareFromEnv=false;
		try{
			if (strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
				strData=property.getProperty(strData);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}
			actualResult = selectByLocatorType(getValueFromPOM).getAttribute("value");

			if((actualResult.trim()).equalsIgnoreCase(strData.trim())){
				ExtentTestManager.reportStepPass("Actual value '" +actualResult+ "' matches with the Expected value '"+strData+ "' in the input field '"+strTestObject+"'");
				WebElementValueCompareFromEnv=true;
			}else{
				ExtentTestManager.reportStepFail(driver, "Actual value '" +actualResult+ "' does not match with the Expected value '"+strData+ "' in the input field '"+strTestObject+"'", true);
				WebElementValueCompareFromEnv=false;
			}
		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while comparing actual and expected values. Error description is :"+e.getMessage(), true);
			WebElementValueCompareFromEnv=false;
		}
		return WebElementValueCompareFromEnv;
	}

	public synchronized boolean WebListSelectFromEnv(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag){
		String strData=null;
		boolean WebListSelectFromEnv=false;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
				strData=property.getProperty(strData);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the data sheet." , true);
				return false;
			}

			new Select(selectByLocatorType(getValueFromPOM)).selectByVisibleText(strData);
			ExtentTestManager.reportStepPass("Item '"+strData+"' is selected from the  '"+strTestObject+"' List box successfully" );
			WebListSelectFromEnv=true;

		} catch (StaleElementReferenceException e) {
			return WebListSelectFromEnv(getValueFromPOM, strTestObject, strColumnName, strExecEventFlag);
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Item '" +  strData + "' was not selected from the  '"+strTestObject+"' List box "+e.getMessage() , true); 
			WebListSelectFromEnv=false;
		}
		return WebListSelectFromEnv;
	}

	public synchronized boolean EshopSQLDBOpenConnection(){
		boolean elementStatus= false;
		String dbUrl = "jdbc:sqlserver://"+ property.getProperty("ESHOP_SQL_Server") +";DatabaseName=" + property.getProperty("ESHOP_SQL_Server_DB_Name") +";";                  
		String username = property.getProperty("ESHOP_SQL_Server_UID");   
		String password = property.getProperty("ESHOP_SQL_Server_PWD"); 
		if(username==null || password==null){
			ExtentTestManager.reportStepFail(driver,"Required details are not provided in the datasheet." , false);
			return false;
		}
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");         
			EshopConnection = DriverManager.getConnection(dbUrl,username,password);
			EShopstmt = EshopConnection.createStatement(); 
			elementStatus=true;
			ExtentTestManager.reportStepPass("Eshop SQL Connection is established Successfully.");
		} catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while connecting to the ESHOP SQL Server. Error description is : "+ e.getMessage() +".", false);
		}
		return elementStatus;
	}

	public synchronized boolean WebListCheckMoreItemsExist(String getValueFromPOM, String strTestObject) {

		boolean WebListCheckMoreItemsExist=false;
		try {
			WebElement dropdown = selectByLocatorType(getValueFromPOM);
			Select select = new Select(dropdown);  
			int itemlistcount = select.getOptions().size();

			if(itemlistcount > 1){
				ExtentTestManager.reportStepPass("Number of items present in WebList  '"+strTestObject+"' is : '"+ itemlistcount +"' which is more than 1.");
				WebListCheckMoreItemsExist = true;
			}else{
				ExtentTestManager.reportStepFail(driver,"Number of items present in WebList  '"+strTestObject+"' is : '"+ itemlistcount +"' which is less than or equal to 1." , true);
				WebListCheckMoreItemsExist = false;
			}
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Error occured while getting the items from the dropdown. Error description is : '"+ e.getMessage() +"'." , true);
		}
		return WebListCheckMoreItemsExist;
	}

	public synchronized boolean WebElementNotEditable(String getValueFromPOM, String strTestObject) {

		String elementenable;
		boolean WebElementEnabled = false;
		try {
			elementenable=selectByLocatorType(getValueFromPOM).getAttribute("readonly");
			if(elementenable.equalsIgnoreCase("true")){
				WebElementEnabled = true;
				ExtentTestManager.reportStepPass("Element '"+strTestObject+"' is not editable.");
			}else{
				WebElementEnabled=false;
				ExtentTestManager.reportStepFail(driver, "Element '"+strTestObject+"' is editable.", true);
			}

		} catch (Exception e) { 	
			ExtentTestManager.reportStepFail(driver,"Exception while finding enabled or disabled. Exception is " + e,true);
			WebElementEnabled=false;
		}
		return WebElementEnabled;
	}

	public synchronized boolean fileUpload(String filePath, String strTestObject) {

		boolean elementStatus = false;
		String getBrowserName = browserProperty.getProperty("testBrowser");

		try{
			//Set the Path for DLL
			File file = new File("lib", "jacob-1.14.3-x86.dll");
			//Get the absolute path for DLL
			System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
			//Get the Jacob DLL path from local
			File jacobDLLPath = new File(property.getProperty("AutoIT_Jacob_DLL_Path"));
			String dLLAbsolutePath = jacobDLLPath.getAbsolutePath();
			//Get the Jacob DLL absolute path
			System.setProperty(LibraryLoader.JACOB_DLL_PATH, dLLAbsolutePath);
			LibraryLoader.loadJacobLibrary();
			File localPath = new File(filePath);
			//Get the upload file absolute path
			String absoluteFilepath = localPath.getAbsolutePath();
			AutoItX autoIT = new AutoItX();

			//Based on Browsers AutoIT tool will be executed
			if(getBrowserName.equalsIgnoreCase("IE")){

				autoIT.winActivate("Choose File to Upload");
				if(autoIT.winWaitActive("Choose File to Upload", "", 10)){
					if(autoIT.winExists("Choose File to Upload")){
						autoIT.send(absoluteFilepath);
						autoIT.send("{Enter}",false);	
						log.info("File has been uploaded successfully in IE browser");
						elementStatus = true;

					}
				}

				log.info("File has been uploaded successfully in IE browser");
				elementStatus = true;
			} 
			else if(getBrowserName.equalsIgnoreCase("Firefox")){
				autoIT.winActivate("File Upload");
				if(autoIT.winWaitActive("File Upload", "", 10)){
					if(autoIT.winExists("File Upload")){
						autoIT.sleep(500);
						autoIT.send(absoluteFilepath);	                
						autoIT.send("{Enter}",false);
						log.info("File has been uploaded successfully in Firefox browser");
						elementStatus = true;
					}
				}
			}else if(getBrowserName.equalsIgnoreCase("Chrome")){

				autoIT.winActivate("Open");
				if(autoIT.winWaitActive("Open", "", 10)){
					if(autoIT.winExists("Open")){
						autoIT.sleep(500);
						autoIT.send(absoluteFilepath);	                
						autoIT.send("{Enter}",false);
						log.info("File has been uploaded successfully in Chrome browser");
						elementStatus = true;
					}		
				}
			}	
		}catch(Exception e){
			log.info("Exception occurred in FileUpload using AutoITX :"+e.getMessage());
			e.printStackTrace();
			elementStatus = false;
		}
		
		if(elementStatus){
			ExtentTestManager.reportStepPass("The file is uploaded successfully using AUTOIT");
		} else{
			ExtentTestManager.reportStepFail(driver, "Error occured while uploading the file using AUTOIT", true);
		}
		
		return elementStatus;
	}

	public synchronized boolean WebElementTextStoreDynamicSubstringValue(String getValueFromPOM, String strTestObject,String envVariableColumn,String startIndexColumn,String endIndexColumn, int strExecEventFlag ){
		String actualText=null;
		String substring=null;
		String strDataStart=null;
		String strDataEnd=null;
		String envVariableName=null;
		int intDataStart=0;
		int intDataend=0;
		boolean WebElementTextCompare=false;

		try{
			if(strExecEventFlag==1){
				strDataStart=getTestData(testDataFilePathStatic, testComponentNameStatic,startIndexColumn,gblrecordsCounterStatic);
				strDataEnd=getTestData(testDataFilePathStatic, testComponentNameStatic,endIndexColumn,gblrecordsCounterStatic);
				envVariableName=getTestData(testDataFilePathStatic, testComponentNameStatic,envVariableColumn,gblrecordsCounterStatic);
			}

			if(strDataEnd==null|| strDataEnd==null || envVariableName==null){
				ExtentTestManager.reportStepFail(driver, "Error occured while retreiving data from Spreadsheet. Value is NULL", false);
				return false;
			}
			actualText = selectByLocatorType(getValueFromPOM).getText();
			intDataStart = Integer.parseInt(strDataStart);
			intDataend = Integer.parseInt(strDataEnd);
			substring = actualText.substring(intDataStart, intDataend);
			Runtimevalue.setProperty(envVariableName, substring);
			ExtentTestManager.reportStepPass("The Dynamic value '"+substring+"' of Element '"+strTestObject+"' is successfully stored in the Runtime variable '"+envVariableName+"'");
			WebElementTextCompare=true;
		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while Stroing the Dynamic value of element '"+strTestObject+"' in the Runtime variable '"+envVariableName+"'. Error description is :"+e.getMessage(), true);
			WebElementTextCompare=false;
		}
		return WebElementTextCompare;
	}

	public synchronized boolean WaitUntilElementVisible(String getValueFromPOM, String strTestObject) {
		boolean Waituntilexpectedtext= false;
		String Element_Text = null;
		try{
			FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver);
			fWait.withTimeout(300, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(selectByLocatorType(getValueFromPOM)));
			Element_Text = selectByLocatorType(getValueFromPOM).getText();
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while getting the text from the element '"+strTestObject+"' .Error description is : "+ e.getMessage() +"." , true); 
			Waituntilexpectedtext=false;
		}
		if(!(Element_Text==null)){
			ExtentTestManager.reportStepPass("Element '"+strTestObject+ "' is now Visible");
			Waituntilexpectedtext= true;
		}else{
			ExtentTestManager.reportStepFail(driver,"Element '"+strTestObject+ "' still not Visible", true);
			Waituntilexpectedtext= false;
		}
		return Waituntilexpectedtext;
	}

	public synchronized boolean VerifyWebElementisVisible(String getValueFromPOM, String strTestObject) {
		boolean elementvisible;
		boolean WebElementisVisible = false;
		try {

			elementvisible=selectByLocatorType(getValueFromPOM).isEnabled();

			String output=selectByLocatorType(getValueFromPOM).getText();
			if(elementvisible){
				WebElementisVisible = true;
				ExtentTestManager.reportStepPass( "'"+strTestObject+"' is Displayed with value '"+output+ "'");
			}else{
				WebElementisVisible=false;
				ExtentTestManager.reportStepFail(driver,"'"+strTestObject+"' is not Displayed", true);
			}
		} catch (Exception e) { 	
			ExtentTestManager.reportStepFail(driver,"Exception occured while Verify WebElementisVisible. Exception is "+e, true);
			WebElementisVisible=false;
		}
		return WebElementisVisible;
	}

	public synchronized boolean waitUntilEnabled(String getValueFromPOM,  String strTestObject) {
		boolean elementStatus= false;
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver,30);
		try{
			String locatorType = getValueFromPOM.split("#")[0];
			String locatorValue = getValueFromPOM.split("#")[1];

			switch(locatorType.toLowerCase()){

			case "id":
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
				if(element.isEnabled()){
					elementStatus = true;
				}else{
					elementStatus = false;
				}
				break;


			case "xpath":
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
				if(element.isEnabled()){
					elementStatus = true;
				}else{
					elementStatus = false;
				}
				break;

			case "css":
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
				if(element.isEnabled()){
					elementStatus = true;
				}else{
					elementStatus = false;
				}
				break;

			case "classname":	
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
				if(element.isEnabled()){
					elementStatus = true;
				}else{
					elementStatus = false;
				}
				break;

			case "name":
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
				if(element.isEnabled()){
					elementStatus = true;
				}else{
					elementStatus = false;
				}
				break;

			case "linktext":
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
				if(element.isEnabled()){
					elementStatus = true;
				}else{
					elementStatus = false;
				}
				break;

			case "tagname":
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorValue)));
				if(element.isDisplayed()){
					elementStatus = true;
				}else{
					elementStatus = false;
				}
				break;

			case "partiallinktext":
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
				if(element.isEnabled()){
					elementStatus = true;
				}else{
					elementStatus = false;
				}
				break;

			default:
				elementStatus = false;
				throw new IllegalArgumentException("Unable to found");
			}	

		}catch(StaleElementReferenceException e1){
				return waitUntilEnabled(getValueFromPOM, strTestObject);
		}catch (NoSuchElementException e) {
			ExtentTestManager.reportStepInfo("No such element '"+ getValueFromPOM +"' found or dislayed");
			return false;
		}catch (Exception e) {
			ExtentTestManager.reportStepInfo("Exception occured while finding the element'"+ getValueFromPOM +"'. Exception is "+e);
			return false;
		}

		if(elementStatus){
			ExtentTestManager.reportStepPass("Element '"+ strTestObject +"' is enabled and visible successfully");
		}else{
			ExtentTestManager.reportStepFail(driver,"Element '"+ strTestObject +"' is not enabled and visible within time of "+intElementDisplayTimeout+" seconds" , true);
		}

		return elementStatus;
	}	

	public synchronized boolean PerformAction_doubleClick(String getValueFromPOM, String strOject) {
		boolean elementStatus= false;
		try{
			WebElement element=selectByLocatorType(getValueFromPOM);
			Actions action = new Actions(driver);
			action.moveToElement(element).doubleClick(element).build().perform();
			elementStatus=true;
		}catch(Exception e){
			elementStatus = false;
		}

		if(elementStatus){
			ExtentTestManager.reportStepPass("Action double click performed over the Element '"+ strOject +"'");
		}else{
			ExtentTestManager.reportStepFail(driver,"Action double click could not be performed over the Element '"+ strOject +"'" , true);
		}
		return elementStatus;
	}

	public synchronized boolean SQLDBSelect_TT(String sqltablename, String strsqlcolumnname,String strsqlcondition,String strExpectedvalue,int strExecEventFlag){
		boolean functionStatus=false;
		String query;
		String check;
		String Table_name;
		String Column_name;
		String SQL_condition;
		String Expected_value;
		String Actual_Value;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
				Expected_value=getTestData(testDataFilePathStatic, testComponentNameStatic, strExpectedvalue, gblrecordsCounterStatic);
			}else{
				Table_name=sqltablename;
				Column_name=strsqlcolumnname;
				SQL_condition=strsqlcondition;
				Expected_value=strExpectedvalue;
			}

			if(Table_name==null || Column_name==null || SQL_condition==null||Expected_value==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";

			//Check for Record Available
			ResultSet rs_SQLServerCheck = stmt_TT.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = stmt_TT.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the SQL TT Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the SQL TT Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else{

				ResultSet rs_SQLServer = stmt_TT.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1).trim();

				if (Expected_value.contains("CURRENT_DATE")){
					String []ExpectedvalueWithFormat=Expected_value.split("#");
					String db_Date = Actual_Value.split(" ")[0];
					Actual_Value=db_Date;
					Date date=new Date();			
					SimpleDateFormat dateformat=new SimpleDateFormat(ExpectedvalueWithFormat[1]);
					Expected_value=dateformat.format(date);
				}

				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the SQL TT Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Actual_Value.equalsIgnoreCase(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the SQL TT Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in SQL query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean SQLDBUpdate_TT(String sqltablename, String strsqlcolumnname,String strsqlcolumnvalue,String strsqlcondition,int strExecEventFlag){
		String Table_name;
		String Column_name;
		String Column_Value;
		String SQL_condition;
		String query;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				Column_Value=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnvalue, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
			}else{
				Table_name=sqltablename;
				Column_name=strsqlcolumnname;
				Column_Value=strsqlcolumnvalue;
				SQL_condition=strsqlcondition;
			}

			if(Table_name==null || Column_name==null || Column_Value==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			if(Column_Value.equalsIgnoreCase("NULL")){
				query = "update "+Table_name+" set "+Column_name+"=NULL where "+SQL_condition;
			}else{
				query = "update "+Table_name+" set "+Column_name+"="+Column_Value+" where "+SQL_condition;
			}

			stmt_TT.execute(query);
			ExtentTestManager.reportStepPass("SQL TT Update Query  "+ query + " executed successfully.");
			return true;

		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Error occured while executing to the SQL TT Update query. Error description is : "+ e.getMessage(), false);
			return false;
		}
	}

	public synchronized boolean storeSQLDBValueInEnv_TT(String sqltablename, String strsqlcolumnname, String strsqlcondition,String envValColumnName, int strExecEventFlag){
		String query = null;
		String check = null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String getIDValueFromRecord = null;
		String setParameterName=null;;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
				setParameterName=getTestData(testDataFilePathStatic, testComponentNameStatic, envValColumnName, gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null||setParameterName==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			//Query to Execute      
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";

			//Check for Record Available
			ResultSet rs_SQLServerCheck = stmt_TT.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query TT "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = stmt_TT.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				ExtentTestManager.reportStepFail(driver,"Selected DB TT value is NULL. No values stored in RUNTIME Variable", false);
				return false;

			}else{
				ResultSet rs_SQLServer = stmt_TT.executeQuery(query);
				rs_SQLServer.next();
				getIDValueFromRecord = rs_SQLServer.getString(1).trim();
				Runtimevalue.setProperty(setParameterName, getIDValueFromRecord);
				ExtentTestManager.reportStepPass("Actual DB value '"+getIDValueFromRecord+"' is stored in RunTime variable '"+setParameterName+"'  successfully");
				return true;
			}

		} catch (Exception e) {    
			ExtentTestManager.reportStepFail(driver,"Error occured while storing the values in SQL TT query.Error description is : "+ e.getMessage(), false);
			return false;
		}

	}

	public synchronized boolean XMLTextUpdateMethod(String Location,String AttributeXPath,String ValueToSet,int strExecEventFlag) throws Exception, IOException{
		boolean Executionstatus=false;

		if (Location==""){
			ExtentTestManager.reportStepFail(driver,    "Location Path for WebConfig is missing", true);
			return false;
		}
		if (AttributeXPath==""){
			ExtentTestManager.reportStepFail(driver,    "AttributeXPath Path for WebConfig is missing", true);
			return false;
		}
		if (ValueToSet==""){
			ExtentTestManager.reportStepFail(driver,    "ValueToSet in the node for WebConfig is missing", true);
			return false;
		}
		if(strExecEventFlag==1){
			Location=getTestData(testDataFilePathStatic, testComponentNameStatic,Location,gblrecordsCounterStatic);
			Location=property.getProperty(Location);
			AttributeXPath=getTestData(testDataFilePathStatic, testComponentNameStatic,AttributeXPath,gblrecordsCounterStatic);
			ValueToSet=getTestData(testDataFilePathStatic, testComponentNameStatic,ValueToSet,gblrecordsCounterStatic);
			ValueToSet=property.getProperty(ValueToSet);

			if (ValueToSet==""){
				ExtentTestManager.reportStepFail(driver,    "Value present in Property File Seems to Empty.Please check the property file.", true);
				return false;
			}
		}

		try {
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();;
			File file=new File("//\\"+Location);

			Document document=builder.parse(file);
			document.getDocumentElement().normalize();
			NodeList nodeList=null;
			XPath xpath=XPathFactory.newInstance().newXPath();
			nodeList=(NodeList)xpath.compile(AttributeXPath).evaluate(document,XPathConstants.NODESET);
			nodeList.item(0).setTextContent(ValueToSet);
			TransformerFactory transFormerFactory = TransformerFactory.newInstance();
			Transformer transFormer = transFormerFactory.newTransformer();
			DOMSource source = new DOMSource(document);

			StreamResult result = new StreamResult(file);
			transFormer.transform(source, result);
			result.getOutputStream().close();
			Executionstatus=true;
			ExtentTestManager.reportStepPass("XML config File '"+Location+"' has been updated successfully for the tag '"+AttributeXPath+"' with the value set as '"+ValueToSet+"'");
			Thread.sleep(2000);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			ExtentTestManager.reportStepFail(driver, "ValueToSet in the node for WebConfig is Not successfull due to reason: '"+e.getMessage()+"'", false);
			Executionstatus=false;
		} catch (Exception e) {
			e.printStackTrace();
			ExtentTestManager.reportStepFail(driver, "Exception occured during XMLTextUpdate. Reason: '"+e.getMessage()+"'", false);
			Executionstatus=false;
		}
		return Executionstatus;
	}

	public synchronized boolean WebEditCheckMaxLengthAndEnterValueCompareSame(String getValueFromPOM, String strTestObject,String strMaxLegthColumn,String strColumnName,String strExpectedValueColumn,int strExecEventFlag ){
		String actualResult=null;
		String strData=null;
		String strExpectedData=null;
		String strMaxLengthOfWebEdit=null;
		String actualfieldlength=null;
		boolean WebEditEnterAndCompareValue=false;
		try {
			if(strExecEventFlag==1){
				strMaxLengthOfWebEdit=getTestData(testDataFilePathStatic, testComponentNameStatic,strMaxLegthColumn,gblrecordsCounterStatic);
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
				strExpectedData=getTestData(testDataFilePathStatic, testComponentNameStatic,strExpectedValueColumn,gblrecordsCounterStatic);
			}
			if(strMaxLengthOfWebEdit==null||strData==null || strExpectedData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the dataSheet.", false);
				return false;
			}
			actualfieldlength=selectByLocatorType(getValueFromPOM).getAttribute("maxlength");
			if((actualfieldlength.trim()).equalsIgnoreCase(strMaxLengthOfWebEdit.trim())){
				WebEditEnterAndCompareValue=true;
				ExtentTestManager.reportStepPass( ""+strTestObject+"'s  Max length value '" + actualResult + "' matches the Expected Max length '" + strExpectedData + "'");
			}else{
				WebEditEnterAndCompareValue=false;
				ExtentTestManager.reportStepFail(driver, ""+strTestObject+"'s Max length value '" + actualResult + "' does not match the Expected Max length '" + strExpectedData + "'", true);
			}

			selectByLocatorType(getValueFromPOM).sendKeys(Keys.chord(Keys.CONTROL, "a"));
			selectByLocatorType(getValueFromPOM).sendKeys(strData);
			ExtentTestManager.reportStepPass("Entering the Text '" +  strData + "' in the Textbox -  '"+strTestObject+"'");
			actualResult = selectByLocatorType(getValueFromPOM).getAttribute("value");
			if((actualResult.trim()).equalsIgnoreCase(strExpectedData.trim())){
				WebEditEnterAndCompareValue=true;
				ExtentTestManager.reportStepPass( ""+strTestObject+"'s  accepted value '" + actualResult + "' matches the Expected value '" + strExpectedData + "'");
			}else{
				WebEditEnterAndCompareValue=false;
				ExtentTestManager.reportStepFail(driver, ""+strTestObject+"'s accepted value '" + actualResult + "' does not match the Expected Value '" + strExpectedData + "'", true);
			}
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Exception occured while WebEdit CheckMaxLength And EnterValue CompareSame. Exception is "+e , true);
			WebEditEnterAndCompareValue=false;
			log.info("No Element Found to compare Text : " + e);
		}
		return WebEditEnterAndCompareValue;
	}

	public synchronized boolean WebElementVerifyElementColor(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag ){
		String strData=null;
		boolean WebEditEnterAndCompareValue=false;
		String colorOfElement=null;
		String colorInCode=null;
		try{
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the dataSheet.", false);
				return false;
			}

			colorOfElement=selectByLocatorType(getValueFromPOM).getCssValue("color");
			//color conversion:
			if(colorOfElement.trim().equalsIgnoreCase("rgba(0, 128, 0, 1)")){
				colorInCode="green";
			}else if(colorOfElement.trim().equalsIgnoreCase("rgba(255, 0, 0, 1)")){
				colorInCode="red";
			}else{
				colorInCode="No Color Conversion is done";
			}

			if((colorInCode.trim()).equalsIgnoreCase(strData.trim())){
				WebEditEnterAndCompareValue=true;
				ExtentTestManager.reportStepPass( ""+strTestObject+"'s  Color '" + colorInCode + "' matches the Expected Color '" + strData + "'");
			}else{
				WebEditEnterAndCompareValue=false;
				ExtentTestManager.reportStepFail(driver, ""+strTestObject+"'s Max length value '" + colorInCode + "' does not match the Expected Max length '" + strData + "'", true);
			}
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver, "Exception occured while comparing the color '"+strTestObject+"'", true);
			WebEditEnterAndCompareValue=false;
		}
		return WebEditEnterAndCompareValue;
	}

	public synchronized boolean webElementFutureDateCompare(String getValueFromPOM, String strtestobject, String date_format, String Days_to_add, int strExecEventFlag) {
		boolean webElementFutureDateCompare = false;
		String webElementValue = null;
		DateFormat dateformat = null;
		Date date = null;
		String futureDate = null;
		String dateFormat = null;
		String daystoadd = null;

		try{

			if(strExecEventFlag == 1){
				dateFormat = getTestData(testDataFilePathStatic, testComponentNameStatic, date_format,gblrecordsCounterStatic);
				daystoadd = getTestData(testDataFilePathStatic, testComponentNameStatic, Days_to_add,gblrecordsCounterStatic);
			}

			if(dateFormat==null || daystoadd ==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}

			int Add_Days = Integer.parseInt(daystoadd);
			webElementValue = selectByLocatorType(getValueFromPOM).getText();
			dateformat = new SimpleDateFormat(dateFormat);
			date = new Date();
			Calendar expdate = Calendar.getInstance();
			expdate.setTime(date);
			expdate.add(Calendar.DATE, Add_Days);
			futureDate = dateformat.format(expdate.getTime());
			if((webElementValue.split(" ")[0].trim()).equals(futureDate.trim())){
				ExtentTestManager.reportStepPass("Actual date '"+webElementValue+"' is matched with expected date '"+ futureDate +"' successfully ");
				webElementFutureDateCompare = true;
			}
			else{
				ExtentTestManager.reportStepFail(driver,"Actual date '"+webElementValue+"' is not matched with expected date '"+ futureDate +"' " , true); 
				webElementFutureDateCompare = false;
			}
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while getting the text from the WebElement '"+ strtestobject +"'and the error description is :"+e.getMessage(), true);
			webElementFutureDateCompare=false;
		}

		return webElementFutureDateCompare;
	}

	public synchronized boolean JSWebElementTextCompare(String getValueFromPOM, String strTestObject,String strColumn, int strExecEventFlag) {
		String expectedContent=null;
		Object actualResult=null;
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		actualResult = (jse.executeScript("return arguments[0].innerHTML;", selectByLocatorType(getValueFromPOM)));
		log.info("RetrievedMessage is : "+actualResult);
		try{
		if(strExecEventFlag==1){
			expectedContent=RetrieveTestDataValue(testDataFilePathStatic, testComponentNameStatic, strColumn,gblrecordsCounterStatic);
		}

		if(expectedContent==null){
			ExtentTestManager.reportStepFail(driver,"Required details are not provided in test data sheet.", false);
			return false;
		}
			if((actualResult.toString().trim()).equalsIgnoreCase(expectedContent.trim())){

				ExtentTestManager.reportStepPass("Actual Value '" +actualResult.toString()+ "' matches with the Expected value '"+expectedContent+ "' in the input field '"+strTestObject+"'");
				return true;
			}else{
				ExtentTestManager.reportStepFail(driver, "Actual Value '" +actualResult.toString()+ "' does not match with the Expected value '"+expectedContent+ "' in the input field '"+strTestObject+"'", true);
				return false;
			}
		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while comparing actual and expected values. Error description is :"+e.getMessage(), true);
			return false;
		}
	}

	public synchronized boolean WebElementTitleAttributeCompare(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag ){
		String actualResult=null;
		String strData=null;
		boolean WebElementValueCompare=false;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the datasheet.", false);
				return false;
			}
			actualResult = selectByLocatorType(getValueFromPOM).getAttribute("title");
			if((actualResult.trim()).equalsIgnoreCase(strData.trim())){
				WebElementValueCompare=true;
				ExtentTestManager.reportStepPass( "'"+strTestObject+"'s  Actual Value '" + actualResult + "' matches the Expected value '" + strData + "'");
			}else{
				WebElementValueCompare=false;
				ExtentTestManager.reportStepFail(driver, "'"+strTestObject+"'s Actual Value '" + actualResult + "' does not match the Expected Value '" + strData + "'", true);
			}
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver, "'"+strTestObject+"'s Actual Value '" + actualResult + "' does not match the Expected Value '" + strData + "'. Exception is "+e, true);
			WebElementValueCompare=false;
		}
		return WebElementValueCompare;
	}

	public synchronized boolean VerifyAddressModalPopupPresent(String strTestObject, String expectedValue) {
		Object strvalue="notdisplayed";
		boolean isModalPresent=false;
		try{
			log.info("Check element is appear");
			for(int i=0; i<60; i++){
				try{
					Thread.sleep(1000);
					log.info("Inside Loop... i is -> "+i);
					strvalue = ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML",driver.findElement(By.xpath("//strong[contains(text(),'Address List')]")));
					if(strvalue==null){
						log.info("Value is NULL");
					}else{
					log.info("Value is : "+strvalue.toString());
					}
					if(strvalue.toString().trim().equalsIgnoreCase(expectedValue.trim())){
						log.info("Element is appeared");
						isModalPresent=true;
						break;
					}
					
				}catch(WebDriverException e){
					log.info("WebDriverException caught inside for since Popup is not appeared "+e);
				}catch(NullPointerException e){
				log.info("NullPointerException caught inside for since Popup is not appeared "+e);
			}
			}

			if((boolean) isModalPresent){
				ExtentTestManager.reportStepPass("'The Modal Popup '"+ strTestObject +"' is displayed.");
				return true;
			}else{
				ExtentTestManager.reportStepPass("'The Modal Popup '"+ strTestObject +"' is not displayed within 60 secs.");
				return false;
			}

		}catch(StaleElementReferenceException e){
			return VerifyAddressModalPopupPresent(strTestObject, expectedValue);
		}
		catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Exception occured while VerifyAddressModalPopupPresent. Error message is "+e, true);
			return false;
		}
	}

	public synchronized boolean SQLDBCommonSelect(String Type,String sqltablename, String strsqlcolumnname,String strsqlcondition,String strExpectedvalue,String Days_to_add,int strExecEventFlag){

		boolean result=false;
		String actionType=null;
		try{

			if (strExecEventFlag==1){
				actionType=getTestData(testDataFilePathStatic, testComponentNameStatic,Type,gblrecordsCounterStatic);
			}
			if(actionType==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the data sheet.", false);
				return false;
			}

			if(actionType.trim().equalsIgnoreCase("Normal")){
				result= SQLDBSelect(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue,strExecEventFlag);
			}else if(actionType.trim().equalsIgnoreCase("Date")){
				result= SQLDBDateFormatCompare(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue,strExecEventFlag);
			}else if(actionType.trim().equalsIgnoreCase("FutureDate")){
				result= SQLDBFutureDateCompare(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue, Days_to_add,strExecEventFlag);
			}else{
				log.info("Invalid Action item from Excel");
				ExtentTestManager.reportStepFail(driver,"Invalid Action Type described in Excel sheet '"+actionType+"'", false);
			}
		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver, "Error occured while SQLDBCommonSelect. Error description is : "+ e.getMessage(), false);
			result= false;
		}
		return result;
	}

	public synchronized boolean WaitUntilNoLongerExist(String getValueFromPOM, String strTestObject) {
		boolean elementenable=false;
		try{
			String locatorType = getValueFromPOM.split("#")[0];
			String locatorValue = getValueFromPOM.split("#")[1];

			int a = 0;
			while(a < 60){
				try{
					Thread.sleep(1000);	
					switch(locatorType.toLowerCase()){
					case "id":
						driver.findElement(By.id(locatorValue));break;
					case "xpath":
						driver.findElement(By.xpath(locatorValue));break;
					case "css":
						driver.findElement(By.cssSelector(locatorValue));break;
					case "classname":	
						driver.findElement(By.className(locatorValue));break;
					case "name":
						driver.findElement(By.name(locatorValue));	break;
					case "linktext":
						driver.findElement(By.linkText(locatorValue));	break;
					case "tagname":
						driver.findElement(By.tagName(locatorValue)); break;
					case "partiallinktext":
						driver.findElement(By.partialLinkText(locatorValue)); break;
					default:
						throw new IllegalArgumentException("Check Locator used to indentify the Element");
					}	

					log.info("Element is present in the Page i is : "+a);
					a++;

				}catch(org.openqa.selenium.NoSuchElementException e){
					elementenable= true;
					break;

				}catch(org.openqa.selenium.StaleElementReferenceException e){
					elementenable= true;
					break;
				}
			}

			if(elementenable){
				ExtentTestManager.reportStepPass("Element '"+strTestObject+"' is not present as expected.");
			}else{
				ExtentTestManager.reportStepFail(driver,"Element '"+strTestObject+"' is still present in the Page.", true);
			}
		}catch(Exception e) {
			ExtentTestManager.reportStepFail(driver, "Error in WaitUntilNoLongerExist. Exception is : "+e, true);
		}
		return elementenable;
	}

	public synchronized boolean RRBSDBCommonSelect(String strType,String sqltablename, String strsqlcolumnname,String strsqlcondition,String strsqlcolumnvalue,String Date_Format,String Days_to_add,int strExecEventFlag){

		boolean result= false;
		String type=null;
		
		try {
			
			if (strExecEventFlag==1){
				type=getTestData(testDataFilePathStatic, testComponentNameStatic,strType,gblrecordsCounterStatic);
			}
			
			if(type==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}
			
			if(type.trim().equalsIgnoreCase("Normal")){
				result=RRBSDBSelect(sqltablename, strsqlcolumnname, strsqlcondition, strsqlcolumnvalue,strExecEventFlag);
			}else if(type.trim().equalsIgnoreCase("Date")){
				result=RRBSDBDateCompare(sqltablename, strsqlcolumnname, strsqlcondition, Date_Format,strExecEventFlag);
			}else if(type.trim().equalsIgnoreCase("FutureDate")){
				result=RRBSDBFutureDateCompare(sqltablename, strsqlcolumnname, strsqlcondition, Date_Format, Days_to_add,strExecEventFlag);
			}else{
				log.info("Invalid Action item from Excel");
				ExtentTestManager.reportStepFail(driver,"Invalid Action Type described in Excel sheet - "+type, false);
			}
	
		} catch (Exception e) { 
			result=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while RRBSDBCommonSelect. Error description is : "+ e.getMessage() +".", false);
		}
		return result;
	}
	
	public synchronized boolean EXIBSDBCommonPreCondition(String actionName,String sqltablename, String strsqlcolumnname,String strsqlcolumnvalue,String strsqlcondition,int strExecEventFlag){

		boolean result= false;
		String action=null;
		
		try {
			
			if (strExecEventFlag==1){
				action=getTestData(testDataFilePathStatic, testComponentNameStatic,actionName,gblrecordsCounterStatic);
			}
			if(action==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the data sheet.", false);
				return false;
			}
			
			if(action.trim().equalsIgnoreCase("Update")){
				result=EXIBSDBUpdate(sqltablename, strsqlcolumnname, strsqlcolumnvalue, strsqlcondition,strExecEventFlag);
			}else if(action.trim().equalsIgnoreCase("Delete")){
				result=EXIBSDBDelete(sqltablename, strsqlcondition,strExecEventFlag);
			}else{
				log.info("Invalid Action item from Excel");
				ExtentTestManager.reportStepFail(driver, "Invalid Action Type described in Excel sheet - "+action, false);
			}
	
		} catch (Exception e) { 
			result=false;
			ExtentTestManager.reportStepFail(driver,"Error occured while executing the SQL query.Error description is : "+ e.getMessage() +".", false);
			log.info("RRBSDBUpdate Error : " + e);
		}
		return result;
	}

	public synchronized boolean RRBSValueStoreInEnvVarFromEnvVarCondition(String rrbstablename, String rrbscolumnname, String rrbsconditionColumn,String strValueInVariable, String strEnvVariableToSave, int strExecEventFlag){
		boolean RetrieveRRBSValueStoresInEnvVar = false;
		String tablename = null;
		String conditionCol = null;
		String columnname = null;
		String actualvalue = null;
		String envVariable="";
		String SQL_condition_value = "";	
		String valueInVariableName=null;

		try{
			if(strExecEventFlag==1){
				tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbstablename,gblrecordsCounterStatic);
				conditionCol=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbsconditionColumn,gblrecordsCounterStatic);
				columnname=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbscolumnname,gblrecordsCounterStatic);
				envVariable=getTestData(testDataFilePathStatic, testComponentNameStatic,strEnvVariableToSave,gblrecordsCounterStatic);
				valueInVariableName=getTestData(testDataFilePathStatic, testComponentNameStatic,strValueInVariable,gblrecordsCounterStatic);

			}

			if(tablename==null || conditionCol==null || columnname==null || envVariable==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
				return false;
			}
			SQL_condition_value = Runtimevalue.getProperty(valueInVariableName);
			if(SQL_condition_value == null){
				SQL_condition_value="";
			}

			String query = "select "+ columnname +" from "+ tablename +" where "+ conditionCol+"='"+SQL_condition_value+"'";
			ResultSet rrbsresultset = rrbsstatement.executeQuery(query);
			while (rrbsresultset.next()){
				actualvalue = rrbsresultset.getString(1);
			}
			if(actualvalue==null){
				actualvalue="";
			}
			Runtimevalue.setProperty(envVariable, actualvalue);
			RetrieveRRBSValueStoresInEnvVar = true;
			ExtentTestManager.reportStepPass("DB Column value '"+ actualvalue + "' is stored in the environment variable :"+ envVariable +".");
		} catch (Exception e) { 
			RetrieveRRBSValueStoresInEnvVar = false;
			ExtentTestManager.reportStepFail(driver, "Error occured while RRBSValueStoreInEnvVarFromEnvVarCondition. Error description is : "+ e.getMessage() +".", false);
		}
		return RetrieveRRBSValueStoresInEnvVar;
	}

	public synchronized boolean RRBSDBDeleteCondtionEnv(String rrbstablename, String rrbsconditioncol,String envValueInVariable, int strExecEventFlag){
		boolean elementStatus= false;
		String tablename = null;
		String conditionCol = null;
		String envVariableName=null;
		String envValue=null;

		try{

			if(strExecEventFlag==1){
				tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbstablename,gblrecordsCounterStatic);
				conditionCol=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbsconditioncol,gblrecordsCounterStatic);
				envVariableName=getTestData(testDataFilePathStatic, testComponentNameStatic,envValueInVariable,gblrecordsCounterStatic);
			}

			if(tablename==null || conditionCol==null || envVariableName==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			envValue=Runtimevalue.getProperty(envVariableName);
			//Query to Execute      
			String check = "select * from "+tablename +" where "+conditionCol+"='"+envValue+"'";
			String query = "Delete from "+ tablename +" where "+ conditionCol+"='"+envValue+"'";
			ResultSet rs = null;
			rs = rrbsstatement.executeQuery(check);
			int temp = 0;
			while(rs.next()){
				temp++;
			}
			if(temp > 0){
				rrbsstatement.execute(query); 
				elementStatus=true;
				ExtentTestManager.reportStepPass("RRBS Delete Query '"+ query + "' executed successfully");

			}
			else if(temp < 1){
				ExtentTestManager.reportStepPass("RRBS Delete Query '"+ query + "' has NO Records available in DB");
				elementStatus = true;
			}

		} catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while executing the RRBSDBDeleteCondtionEnv query. Error description is : "+ e.getMessage() +".", false);
		}
		return elementStatus;
	}

	public synchronized boolean RRBSDBCommonSelectFromEnv(String strType,String sqltablename, String strsqlcolumnname,String strsqlconditionCol,String evnNameWhichHasValue, String strsqlcolumnvalue,String Date_Format,String Days_to_add,int strExecEventFlag){
		boolean result= false;
		String type=null;
		try {
			if (strExecEventFlag==1){
				type=getTestData(testDataFilePathStatic, testComponentNameStatic,strType,gblrecordsCounterStatic);
			}
			if(type==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}
			
			if(type.trim().equalsIgnoreCase("Normal")){
				result=RRBSDBSelectFromEnvCondition(sqltablename, strsqlcolumnname, strsqlconditionCol, evnNameWhichHasValue, strsqlcolumnvalue,strExecEventFlag);
			}else if(type.trim().equalsIgnoreCase("Date")){
				result=RRBSDBDateCompareFromEnvCondition(sqltablename, strsqlcolumnname, strsqlconditionCol,evnNameWhichHasValue, Date_Format,strExecEventFlag);
			}else if(type.trim().equalsIgnoreCase("FutureDate")){
				result=RRBSDBFutureDateCompareFromEnvCondition(sqltablename, strsqlcolumnname, strsqlconditionCol,evnNameWhichHasValue, Date_Format, Days_to_add,strExecEventFlag);
			}else{
				log.info("Invalid Action item from Excel");
				ExtentTestManager.reportStepFail(driver,"Invalid Action Type described in Excel sheet - "+type, false);
			}
		} catch (Exception e) { 
			result=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while executing the SQL query.Error description is : "+ e.getMessage() +".", true);
		}
		return result;
	}
	
	public synchronized boolean RRBSDBSelectFromEnvCondition(String sqltablename, String strsqlcolumnname,String strsqlconditionCol,String envVariableColumnName,String strExpectedvalue,int strExecEventFlag){
		boolean RRBSDBSelect= false;
		String query = null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		//String Expected_value = null;		
		String Expected_value = "";
		String Actual_Value = null;
		String envVariable=null;
		String evnCondtion=null;
		
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlconditionCol,gblrecordsCounterStatic);
				Expected_value=getTestData(testDataFilePathStatic, testComponentNameStatic,strExpectedvalue,gblrecordsCounterStatic);
				envVariable=getTestData(testDataFilePathStatic, testComponentNameStatic,envVariableColumnName,gblrecordsCounterStatic);
				evnCondtion=Runtimevalue.getProperty(envVariable);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null||envVariable==null||evnCondtion==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"='"+evnCondtion+"'";
			ResultSet rrbsresultset = rrbsstatement.executeQuery(query);
			rrbsresultset.next();
			Actual_Value = rrbsresultset.getString(1).trim();

			if(!rrbsresultset.wasNull()){      
				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value : '"+Actual_Value+"' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					RRBSDBSelect=true;
				}else if(!(Actual_Value.equals(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value : '"+Actual_Value+"' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);
					RRBSDBSelect=false;
				}
			}
			else if(rrbsresultset.wasNull()){        // If "NULL" value is present in the fired Query
				if(Expected_value.equals("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					RRBSDBSelect=true;
				}else if(!(Expected_value.equals("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);  	 
					RRBSDBSelect=false;
				}
			}
		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Exception occured while RRBSDBSelectFromEnvCondition query. Error description is : "+ e.getMessage() +".", true);
			RRBSDBSelect=false;
		}
		return RRBSDBSelect;
	}
	
	public synchronized boolean RRBSDBDateCompareFromEnvCondition(String sqltablename, String strsqlcolumnname,String strsqlcondition,String envVariableColumnName,String Date_Format,int strExecEventFlag){

		boolean RRBSDBDateCompare= false;
		String query = null;  
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String Expected_value = null;
		String Actual_Value = null;
		String Current_Date=null;
		String expected_db_Date = null;
		String envVariable=null;
		String evnCondtion=null;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				envVariable=getTestData(testDataFilePathStatic, testComponentNameStatic,envVariableColumnName,gblrecordsCounterStatic);
				evnCondtion=Runtimevalue.getProperty(envVariable);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null || envVariable==null||evnCondtion==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			Current_Date = dateFormat.format(date);
			Expected_value = Current_Date.trim();
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"='"+evnCondtion+"'";
			ResultSet rrbsresultset = rrbsstatement.executeQuery(query);
			rrbsresultset.next();
			Actual_Value = rrbsresultset.getString(1);
			String db_Date = Actual_Value.split(" ")[0];
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date dateToChange = dateFormat1.parse(db_Date);			
			SimpleDateFormat finalDateFormat = new SimpleDateFormat(Date_Format);
			expected_db_Date = finalDateFormat.format(dateToChange);

			if(!rrbsresultset.wasNull()){

				if(expected_db_Date.equals(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value : '"+Actual_Value+"' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					RRBSDBDateCompare=true;
				}else if(!(expected_db_Date.equals(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value : '"+Actual_Value+"' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);
					RRBSDBDateCompare=false;
				}
			}
			else if(rrbsresultset.wasNull()){        // If "NULL" value is present in the fired Query
				if(Expected_value.equals("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					RRBSDBDateCompare=true;
				}

				else if(!(Expected_value.equals("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);  	 
					RRBSDBDateCompare=false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Exception occured while RRBSDBDateCompareFromEnvCondition. Error description is : "+ e.getMessage() +".", true);
			RRBSDBDateCompare=false;
		}
		return RRBSDBDateCompare;
	}

	public synchronized boolean RRBSDBFutureDateCompareFromEnvCondition(String sqltablename, String strsqlcolumnname,String strsqlcondition,String envVariableColumnName,String Date_Format,String Days_to_add,int strExecEventFlag){

		boolean RRBSDBFutureDateCompare= false;
		String query = null;  
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String Expected_value = null;
		String Actual_Value = null;
		String Current_Date=null;
		String expected_db_Date = null;
		String daystoadd = null;

		String envVariable=null;
		String evnCondtion=null;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				daystoadd = getTestData(testDataFilePathStatic, testComponentNameStatic, Days_to_add,gblrecordsCounterStatic);
				envVariable=getTestData(testDataFilePathStatic, testComponentNameStatic,envVariableColumnName,gblrecordsCounterStatic);
				evnCondtion=Runtimevalue.getProperty(envVariable);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null|| envVariable==null||evnCondtion==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}

			int Add_Days = Integer.parseInt(daystoadd);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			Calendar expdate = Calendar.getInstance();
			expdate.setTime(date);
			expdate.add(Calendar.DATE, Add_Days);
			Current_Date = dateFormat.format(expdate.getTime());
			Expected_value = Current_Date.trim();
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"='"+evnCondtion+"'";
			ResultSet rrbsresultset = rrbsstatement.executeQuery(query);
			rrbsresultset.next();
			Actual_Value = rrbsresultset.getString(1);
			String db_Date = Actual_Value.split(" ")[0];
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date dateToChange = dateFormat1.parse(db_Date);			
			SimpleDateFormat finalDateFormat = new SimpleDateFormat(Date_Format);
			expected_db_Date = finalDateFormat.format(dateToChange);

			if(!rrbsresultset.wasNull()){          

				if(expected_db_Date.equals(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					RRBSDBFutureDateCompare=true;

				}else if(!(expected_db_Date.equals(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);
					RRBSDBFutureDateCompare=false;
				}
			}
			else if(rrbsresultset.wasNull()){       
				if(Expected_value.equals("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					RRBSDBFutureDateCompare=true;
				}

				else if(!(Expected_value.equals("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);  	 
					RRBSDBFutureDateCompare=false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,    "Error occured while RRBSDBFutureDateCompareFromEnvCondition .Error description is : "+ e.getMessage() +".", true);
			RRBSDBFutureDateCompare=false;
		}

		return RRBSDBFutureDateCompare;
	}

	public synchronized boolean RRBSStoreValueInEnvVar(String rrbstablename, String rrbscolumnname, String rrbscondition, String strenvvar, int strExecEventFlag){
		boolean RetrieveRRBSValueStoresInEnvVar = false;
		String tablename = null;
		String condition = null;
		String columnname = null;
		String actualvalue = null;
		String envVariableName="";
		try{
			if(strExecEventFlag==1){
				tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbstablename,gblrecordsCounterStatic);
				condition=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbscondition,gblrecordsCounterStatic);
				columnname=getTestData(testDataFilePathStatic, testComponentNameStatic,rrbscolumnname,gblrecordsCounterStatic);
				envVariableName=getTestData(testDataFilePathStatic, testComponentNameStatic,strenvvar,gblrecordsCounterStatic);
			}
			if(tablename==null || condition==null || columnname==null ){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
				return false;
			}

			String query = "select "+ columnname +" from "+ tablename +" where "+ condition;
			ResultSet rrbsresultset = rrbsstatement.executeQuery(query);
			while (rrbsresultset.next()){
				actualvalue = rrbsresultset.getString(1);
			}
			Runtimevalue.setProperty(envVariableName, actualvalue);
			RetrieveRRBSValueStoresInEnvVar = true;
			ExtentTestManager.reportStepPass("DB Column value '"+ actualvalue + "' is stored in the environment variable :"+ envVariableName +".");

		} catch (Exception e) { 
			RetrieveRRBSValueStoresInEnvVar = false;
			ExtentTestManager.reportStepFail(driver, "Exception occured while executing the RRBSStoreValueInEnvVar query. Error description is : "+ e.getMessage() +".", false);
		}
		return RetrieveRRBSValueStoresInEnvVar;
	}

	public synchronized boolean RRBSDateAndWebElementTextCompare(String getValueFromPOM, String sqltablename, String strsqlcolumnname,String strsqlcondition,String DateFormatCol,String strTestObject,int strExecEventFlag){

		boolean RRBSDBDateCompare= false;
		String query = null;  
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String Actual_Value = null;
		String expected_db_Date = null;
		String valueFromWebElement=null;
		String dateFormat=null;
		String expected_Object_Date=null;


		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				dateFormat=getTestData(testDataFilePathStatic, testComponentNameStatic,DateFormatCol,gblrecordsCounterStatic);
			}
			if(Table_name==null || Column_name==null || SQL_condition==null ||dateFormat==null ){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			valueFromWebElement = selectByLocatorType(getValueFromPOM).getText();
			SimpleDateFormat sDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
			Date dateToChange1 = sDateFormat.parse(valueFromWebElement);			
			SimpleDateFormat finalDateFormat1 = new SimpleDateFormat(dateFormat);
			expected_Object_Date = finalDateFormat1.format(dateToChange1);
			// Query to Execute      
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"";
			ResultSet rrbsresultset = rrbsstatement.executeQuery(query);
			rrbsresultset.next();

			Actual_Value = rrbsresultset.getString(1);
			String db_Date = Actual_Value.split(" ")[0];
			SimpleDateFormat SdateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date dateToChange = SdateFormat2.parse(db_Date);			
			SimpleDateFormat finalDateFormat = new SimpleDateFormat(dateFormat);
			expected_db_Date = finalDateFormat.format(dateToChange);

			if(!rrbsresultset.wasNull()){   
				if(expected_db_Date.equals(expected_Object_Date)){
					ExtentTestManager.reportStepPass("Actual Date '"+Actual_Value+"' for the Query '"+query+"' matches the expected value : '"+expected_Object_Date+"'");
					RRBSDBDateCompare=true;

				}else if(!(expected_db_Date.equals(expected_Object_Date))){
					ExtentTestManager.reportStepFail(driver,"Actual Date '"+Actual_Value+"' for the Query '"+query+"' does not match the expected value : '"+expected_Object_Date+"'", false);
					RRBSDBDateCompare=false;
				}
			}
			else if(rrbsresultset.wasNull()){        // If "NULL" value is present in the fired Query
				if(expected_Object_Date.equals("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the Query '"+query+"' matches the expected value '"+expected_Object_Date+"'");
					RRBSDBDateCompare=true;
				}

				else if(!(expected_Object_Date.equals("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the Query '"+query+"' does not match the expected value : '"+expected_Object_Date+"'", false);  	 
					RRBSDBDateCompare=false;
				}
			}
		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,    "Error occured while comparing the dates in SQL query.Error description is : "+ e.getMessage() +".", false);
			RRBSDBDateCompare=false;
		}
		return RRBSDBDateCompare;
	}

	public synchronized boolean WebElementAttributeValueCompare(String getValueFromPOM, String strTestObject,String strAttributeName,String strExpectedAttrValue,int strExecEventFlag ){
		boolean WebEditEnterAndCompareValue=false;
		String actualAttributeValue=null;
		String attributeName=null;
		String expectedAttributeValue=null;
		try {
			if(strExecEventFlag==1){
				attributeName=getTestData(testDataFilePathStatic, testComponentNameStatic,strAttributeName,gblrecordsCounterStatic);
				expectedAttributeValue=getTestData(testDataFilePathStatic, testComponentNameStatic,strExpectedAttrValue,gblrecordsCounterStatic);
			}
			
			if(attributeName==null || expectedAttributeValue==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the dataSheet.", false);
				return false;
			}
			actualAttributeValue = selectByLocatorType(getValueFromPOM).getAttribute(attributeName);
			
			if((actualAttributeValue.trim()).equalsIgnoreCase(expectedAttributeValue.trim())){
				WebEditEnterAndCompareValue=true;
				ExtentTestManager.reportStepPass( "The Actual attribute value '"+actualAttributeValue+"' of "+strTestObject+" matches with the expected value '" + actualAttributeValue + "' matches with the Expected value '" + expectedAttributeValue + "'");
				if(strAttributeName.trim().equalsIgnoreCase("disabled")){
					ExtentTestManager.reportStepPass( "The "+strTestObject+" is disabled");	
				}
			}else{
				WebEditEnterAndCompareValue=false;
				ExtentTestManager.reportStepFail(driver,  "The Actual attribute value '"+actualAttributeValue+"' of "+strTestObject+" does not match with the expected value '" + actualAttributeValue + "' matches with the Expected value '" + expectedAttributeValue + "'", true);
				if(strAttributeName.trim().equalsIgnoreCase("disabled")){
					ExtentTestManager.reportStepFail(driver, "The "+strTestObject+" is not disabled", true);
				}
			}
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver, "Error occured with Web element attribute value compare. Exception is "+e.getMessage(), true);
			WebEditEnterAndCompareValue=false;
		}
		return WebEditEnterAndCompareValue;
	}

	public synchronized boolean JavaScriptWebEditEnterText(String getValueFromPOM, String strTestObject, String strColumnName, int strExecEventFlag) {
		boolean elementStatus = false;
		String elementValue = null;
		try{
			if(strExecEventFlag==1){
				elementValue = getTestData(testDataFilePathStatic, testComponentNameStatic, strColumnName,gblrecordsCounterStatic);
			}
			if(elementValue == null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
				return false;
			}
			
			String elementFromPOM=null;
			elementFromPOM=getValueFromPOM;
			int count=elementFromPOM.split("#").length;
			//log.info("JS POM Count : "+count);
			if(count==2){
				getValueFromPOM=elementFromPOM.split("#")[1];
				//log.info("changed getValueFromPOM is : "+getValueFromPOM);
			}else{
				//log.info("Not changed getValueFromPOM is : "+getValueFromPOM);
			}

			JavascriptExecutor js = (JavascriptExecutor) driver;
			log.info("Scrip is : document.getElementById(\""+getValueFromPOM+"\").value = \""+elementValue+"\"");
			js.executeScript("document.getElementById(\""+getValueFromPOM+"\").value = \""+elementValue+"\"");
			ExtentTestManager.reportStepPass("Text '"+elementValue+"' is entered for the Element "+strTestObject+" using JavaScript");	
			elementStatus = true;
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Text '"+elementValue+"' is not entered for "+strTestObject+"  using JavaScript. Exception is "+e , true);  
			elementStatus = false;
		}
		return elementStatus;
	}
	
	public synchronized boolean VerifyDateModalPopupPresent(String strTestObject) {
		Object isModalPresent=false;
		try{
			for(int i=0; i<60; i++){
				try{
					Thread.sleep(1000);
					log.info("Inside Loop... i is -> "+i);
					isModalPresent = ((JavascriptExecutor)driver).executeScript("return document.activeElement.getElementsByClassName('modal-title')[0].innerHTML.trim()!=null;");
					if((boolean) isModalPresent){
						log.info("Element is appeared");
						break;
					}
				}catch(WebDriverException e){
					log.info("WebDriverException caught inside for since Popup is not appeared");
				}
			}
			if((boolean) isModalPresent){
				ExtentTestManager.reportStepPass("'The Modal Popup '"+ strTestObject +"' is displayed.");
				return true;
			}else{
				ExtentTestManager.reportStepPass("'The Modal Popup '"+ strTestObject +"' is not displayed within 60 secs.");
				return false;
			}

		}catch(StaleElementReferenceException e){
			return VerifyDateModalPopupPresent(strTestObject);
		}
		catch(Exception e){
			log.info("Element is not found :"+e);
			ExtentTestManager.reportStepFail(driver,"Exception occured. Error message is : "+ e +"." , true);
			return false;
		}
	}

	public synchronized boolean ESHOPDBCommonPreCondition(String actionName,String sqltablename, String strsqlcolumnname,String strsqlcolumnvalue,String strsqlcondition,int strExecEventFlag){

		boolean elementStatus= false;
		String action=null;
		
		try {
			
			if (strExecEventFlag==1){
				action=getTestData(testDataFilePathStatic, testComponentNameStatic,actionName,gblrecordsCounterStatic);
			}
			if(action==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}
			
			if(action.trim().equalsIgnoreCase("Update")){
				elementStatus=EshopSQLDBUpdate(sqltablename, strsqlcolumnname, strsqlcolumnvalue, strsqlcondition,strExecEventFlag);
			}else if(action.trim().equalsIgnoreCase("Delete")){
				elementStatus=EshopSQLDBDelete(sqltablename, strsqlcondition,strExecEventFlag);
			}else{
				log.info("Invalid Action item from Excel");
				ExtentTestManager.reportStepFail(driver, "Invalid Action Type described in Excel sheet - "+action, false);
			}
	
		} catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while executing the ESHOP SQL query.Error description is : "+ e.getMessage() +".", false);
		}
		return elementStatus;
	}

	public synchronized boolean EShopSQLAllSelect(String Type,String sqltablename, String strsqlcolumnname,String strsqlcondition,String strExpectedvalue,String Days_to_add,String strenvironmentvariable,int strExecEventFlag){
		
		boolean result=false;
		String actionType=null;
		try{
			
			if (strExecEventFlag==1){
				actionType=getTestData(testDataFilePathStatic, testComponentNameStatic,Type,gblrecordsCounterStatic);
			}
			if(actionType==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}
		
			if(actionType.trim().equalsIgnoreCase("Normal")){
				result= EShopSQLDBSelect(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue,strExecEventFlag);
			}else if(actionType.trim().equalsIgnoreCase("Date")){
				result= EShopSQLDBDateFormatCompare(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue,strExecEventFlag);
			}else if(actionType.trim().equalsIgnoreCase("FutureDate")){
				result= EShopSQLDBFutureDateCompare(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue, Days_to_add,strExecEventFlag);
			}else if(actionType.trim().equalsIgnoreCase("EnvVar")){
				result= EShopSQLDBSelectFromEnv(sqltablename, strsqlcolumnname, strsqlcondition, strenvironmentvariable,strExecEventFlag);
			}else if(actionType.trim().equalsIgnoreCase("EnvCon")){
				result= ESHOPSQLDBSelectConditionEnvvar1(sqltablename, strsqlcolumnname, strsqlcondition, strenvironmentvariable, strExpectedvalue,strExecEventFlag);
			}else if(actionType.trim().equalsIgnoreCase("EnvConSuffix")){
				result= ESHOPSQLDBSelectConditionEnvvarSuffix(sqltablename, strsqlcolumnname, strsqlcondition, strenvironmentvariable, strExpectedvalue,Days_to_add,strExecEventFlag);
			}else{
				log.info("Invalid Action item from Excel");
				ExtentTestManager.reportStepFail(driver, "Invalid Action Type described in Excel sheet - "+actionType, false);
			}
		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver, "Error occured while EShopSQLAllSelect .Error description is : "+ e.getMessage() +".", true);
			return false;
		}
		return result;
	}
	
	public synchronized boolean EShopSQLDBDateFormatCompare(String sqltablename, String strsqlcolumnname,String strsqlcondition,String dateFormatFromExcel,int strExecEventFlag){
		boolean SQLDBDateCompare= false;
		String query = null;  
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String Expected_value = null;
		String Actual_Value = null;
		String Current_Date=null;
		String expected_db_Date = null;
		String Date_Format=null;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				Date_Format=getTestData(testDataFilePathStatic, testComponentNameStatic,dateFormatFromExcel,gblrecordsCounterStatic);
			}
			
			if(Table_name==null || Column_name==null || SQL_condition==null || Date_Format ==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat dateFormat = new SimpleDateFormat(Date_Format);
			Date date = new Date();
			Current_Date = dateFormat.format(date);
			Expected_value = Current_Date.trim();
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"";
			ResultSet Eshop_SQLServer= EShopstmt.executeQuery(query);
			Eshop_SQLServer.next();
			Actual_Value = Eshop_SQLServer.getString(1);
			String db_Date = Actual_Value.split(" ")[0];

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date dateToChange = dateFormat1.parse(db_Date);
			SimpleDateFormat finalDateFormat = new SimpleDateFormat(Date_Format);
			expected_db_Date = finalDateFormat.format(dateToChange);	

			if(!Eshop_SQLServer.wasNull()){            // If some value is present in the fired Query
				if(expected_db_Date.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					SQLDBDateCompare=true;
				}else if(!(expected_db_Date.equalsIgnoreCase(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);
					SQLDBDateCompare=false;
				}
			}else if(Eshop_SQLServer.wasNull()){        // If "NULL" value is present in the fired Query
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					SQLDBDateCompare=true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);  	 
					SQLDBDateCompare=false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,    "Error occured while EShopSQLDBDateFormatCompare. Error description is : "+ e.getMessage() +".", false);
			SQLDBDateCompare=false;
		}
		return SQLDBDateCompare;
	}

	public synchronized boolean EShopSQLDBFutureDateCompare(String sqltablename, String strsqlcolumnname,String strsqlcondition,String Date_Format,String Days_to_add,int strExecEventFlag){
		boolean SQLDBDateCompare= false;
		String query = null;  
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String Expected_value = null;
		String Actual_Value = null;
		String Current_Date=null;
		String expected_db_Date = null;
		String daystoadd = null;
		
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				daystoadd = getTestData(testDataFilePathStatic, testComponentNameStatic, Days_to_add,gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			
			int Add_Days = Integer.parseInt(daystoadd);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			Calendar expdate = Calendar.getInstance();
			expdate.setTime(date);
			expdate.add(Calendar.DATE, Add_Days);
			Current_Date = dateFormat.format(expdate.getTime());
			Expected_value = Current_Date.trim();
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"";
			ResultSet Eshop_SQLServer= EShopstmt.executeQuery(query);
			Eshop_SQLServer.next();
			Actual_Value = Eshop_SQLServer.getString(1);
			String db_Date = Actual_Value.split(" ")[0];

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date dateToChange = dateFormat1.parse(db_Date);
			SimpleDateFormat finalDateFormat = new SimpleDateFormat(Date_Format);
			expected_db_Date = finalDateFormat.format(dateToChange);	

			if(!Eshop_SQLServer.wasNull()){            // If some value is present in the fired Query
				if(expected_db_Date.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					SQLDBDateCompare=true;
				}else if(!(expected_db_Date.equalsIgnoreCase(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);
					SQLDBDateCompare=false;
				}
			}else if(Eshop_SQLServer.wasNull()){        // If "NULL" value is present in the fired Query
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					SQLDBDateCompare=true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);  	 
					SQLDBDateCompare=false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,    "Error occured while EShopSQLDBFutureDateCompare. Error description is : "+ e.getMessage() +".", false);
			log.info("SQLDBDateCompare Error : " + e);
			SQLDBDateCompare=false;
		}
		return SQLDBDateCompare;
	}

	public synchronized boolean JSEnterLastDateOfAFutureYear(String getValueFromPOM,  String strTestObject,String strdateFormatInDataSheet,String strfutureYearsToAdd,String envVariable, int strExecEventFlag) {
		boolean functionStatus= false;
		String strDateFormat=null;
		String envVariableName=null;
		String futureYearsToAdd=null;
		String futureDateToEnter=null;
		
		try {
			if(strExecEventFlag==1){
				strDateFormat=getTestData(testDataFilePathStatic, testComponentNameStatic,strdateFormatInDataSheet,gblrecordsCounterStatic);
				envVariableName=getTestData(testDataFilePathStatic, testComponentNameStatic,envVariable,gblrecordsCounterStatic);
				futureYearsToAdd=getTestData(testDataFilePathStatic, testComponentNameStatic,strfutureYearsToAdd,gblrecordsCounterStatic);
			}else{
				strDateFormat=strdateFormatInDataSheet;
				envVariableName=envVariable;
				futureYearsToAdd=strfutureYearsToAdd;
			}

			SimpleDateFormat dateformatToGetYear = new SimpleDateFormat("yyyy");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, +(Integer.parseInt(futureYearsToAdd)));
			String addedYear=dateformatToGetYear.format(cal.getTime());
			
			SimpleDateFormat dateformatToEnter = new SimpleDateFormat(strDateFormat);
			Calendar calToGetFutureDate = Calendar.getInstance();
			calToGetFutureDate.set(Integer.parseInt(addedYear),  12, 31);
			calToGetFutureDate.add(Calendar.MONTH, -1);
			futureDateToEnter=dateformatToEnter.format(calToGetFutureDate.getTime());
			if(!(envVariableName.equalsIgnoreCase("NA"))){
				Runtimevalue.setProperty(envVariableName, futureDateToEnter);
				ExtentTestManager.reportStepPass("Dynamic Date value '"+futureDateToEnter+"' is stored in the Runtime Variable  "+envVariableName+"'");
			}
			
			String elementFromPOM=null;
			elementFromPOM=getValueFromPOM;
			int count=elementFromPOM.split("#").length;
			//log.info("JS POM Count : "+count);
			if(count==2){
				getValueFromPOM=elementFromPOM.split("#")[1];
				//log.info("changed getValueFromPOM is : "+getValueFromPOM);
			}else{
				//log.info("Not changed getValueFromPOM is : "+getValueFromPOM);
			}
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementById('"+getValueFromPOM+"').value = '"+futureDateToEnter+"'");
			ExtentTestManager.reportStepPass("Last date of the Future year '"+futureDateToEnter+"' is entered in the '"+strTestObject+"'");
			functionStatus=true;	
		} catch (Exception e) { 	
			ExtentTestManager.reportStepFail(driver, "Last date of the Future year '"+futureDateToEnter+"' is not entered in the '"+strTestObject+"'", true);
			functionStatus=false;
		}
		return functionStatus;
		
	}

	public synchronized boolean RRBSAllSelect(String Type,String sqltablename, String strsqlcolumnname,String strsqlcondition,String strExpectedvalue,String Days_to_add,String strenvironmentvariable,int strExecEventFlag){
		
		boolean result=false;
		String actionType=null;
		try{
			
			if (strExecEventFlag==1){
				actionType=RetrieveTestDataValue(testDataFilePathStatic, testComponentNameStatic,Type,gblrecordsCounterStatic);
			}
			if(actionType==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}
			if(actionType.trim().equalsIgnoreCase("Normal")){
				result=RRBSDBSelect(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue,strExecEventFlag);
			}else if(actionType.trim().equalsIgnoreCase("Date")){
				result=RRBSDBDateCompare(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue,strExecEventFlag);
			}else if(actionType.trim().equalsIgnoreCase("FutureDate")){
				result=RRBSDBFutureDateCompare(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue, Days_to_add,strExecEventFlag);
			}else if(actionType.trim().equalsIgnoreCase("EnvVar")){
				//result= SQLDBSelectFromEnv(sqltablename, strsqlcolumnname, strsqlcondition, strenvironmentvariable,gblrecordsCounterStatic);
			}else if(actionType.trim().equalsIgnoreCase("EnvCon")){
				//result= SQLDBSelectConditionEnvvar(sqltablename, strsqlcolumnname, strsqlcondition, strenvironmentvariable, strExpectedvalue,gblrecordsCounterStatic);
			}else if(actionType.trim().equalsIgnoreCase("DateInEnvVar")){
				//result= SQLDBDateCompareInEnvVar(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue, strenvironmentvariable);
			}else if(actionType.trim().equalsIgnoreCase("DateCompServer")){
				result= RRBSDateCompareFromDB(sqltablename, strsqlcolumnname, strsqlcondition, strExpectedvalue, strExecEventFlag);
			}
			
			else{
				log.info("Invalid Action item from Excel");
				ExtentTestManager.reportStepFail(driver, "Invalid Action Type described in Excel sheet - "+actionType, false);
			}
		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver, "Error occured while comparing the values in SQL query.Error description is : "+ e.getMessage() +".", true);
			return false;
		}
		return result;
	}

	public synchronized boolean RRBSDateCompareFromDB(String sqltablename, String strsqlcolumnname,String strsqlcondition,String Date_Format,int strExecEventFlag)throws Exception  {

		boolean RRBSDBDateCompare= false;
		
		String dateQuery = null;
		String query = null;  
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String datePattern = null;
		String dbDatevalue = null;
		String dbValue = null;
		String splittedDBValue = null;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
				datePattern = getTestData(testDataFilePathStatic, testComponentNameStatic, Date_Format, gblrecordsCounterStatic);
				
			}else{
				Table_name=sqltablename;
				Column_name=strsqlcolumnname;
				SQL_condition=strsqlcondition;
				datePattern=Date_Format;
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			//Date query to execute
			dateQuery = "SELECT TO_CHAR (SYSDATE, '"+datePattern+"') AS today FROM dual";

			ResultSet rs_SQLServerCheck = rrbsstatement.executeQuery(dateQuery);
				while (rs_SQLServerCheck.next()){
				dbDatevalue = rs_SQLServerCheck.getString(1).trim();
				}
				
			// Query to Execute      
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"";
			rs_SQLServerCheck = rrbsstatement.executeQuery(query);
			while (rs_SQLServerCheck.next()){
				dbValue = rs_SQLServerCheck.getString(1).trim();
				
				splittedDBValue = dbValue.split("\\ ")[0];
				
				}
			
			
			if(splittedDBValue.trim().equalsIgnoreCase(dbDatevalue)){
				
				ExtentTestManager.reportStepPass("Actual date : '"+splittedDBValue+"' in the column : '"+Column_name+"' of table : '"+Table_name+"' matches with the expected date : '"+dbDatevalue+" from the DB' ");
				RRBSDBDateCompare=true;
				
			}else{
				
				ExtentTestManager.reportStepFail(driver,"Actual date : '"+splittedDBValue+"' in the column : '"+Column_name+"' of table : '"+Table_name+"' is not matched with the expected date : '"+dbDatevalue+" from the DB' ",false);
				RRBSDBDateCompare=false;
				
			}
			
		}catch (Exception e){
			ExtentTestManager.reportStepFail( driver,"Error occured while executing the SQL query.Error description is : "+ e.getMessage(),false);
			log.info("RRBSDateCompareFromDB Error : " + e);
			RRBSDBDateCompare=false;
			}
		
		return RRBSDBDateCompare;
		
	}
	
	public synchronized boolean WebListSelectedOptionCompare(String getValueFromPOM, String strTestObject, String strColumnName, int strExecEventFlag) {
		String strData = null;
		boolean WebListSelectedValue = false;
		String selectedValue = null;
		try{
			if(strExecEventFlag==1){
				strData = getTestData(testDataFilePathStatic, testComponentNameStatic, strColumnName,gblrecordsCounterStatic);
			}	
			if(strData==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
				return false;
			}
			selectedValue = new Select(selectByLocatorType(getValueFromPOM)).getFirstSelectedOption().getText();
			if(selectedValue.trim().equalsIgnoreCase(strData.trim())){
				ExtentTestManager.reportStepPass(strTestObject +"'s selected dropdown value '"+selectedValue + "' matches with the Expected Value '"+strData+"'" );
				WebListSelectedValue = true;
			}else{
				ExtentTestManager.reportStepFail(driver,strTestObject +"'s selected dropdown value '"+selectedValue + "' does not matches with the Expected Value '"+strData+"'"  , true); 
				WebListSelectedValue=false;
			}
		}catch(StaleElementReferenceException e){
			return WebListSelectedOptionCompare(getValueFromPOM, strTestObject, strColumnName, strExecEventFlag);

		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Selected dropdown value is '" +  selectedValue + " is not shown in "+strTestObject+"" , true); 
			WebListSelectedValue=false;
		}
		return WebListSelectedValue;
	}
	
	public synchronized boolean SQLDBCheckValueNotExist(String sqltablename, String strsqlcolumnname,String strsqlcondition,int strExecEventFlag){
		boolean SQLDBCheckValueExist= false;
		String query=null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"";
			ResultSet rs_SQLServer = stmt.executeQuery(query);		

			int temp=0;	
			while(rs_SQLServer.next()){
				temp++;
			}

			if(temp >= 1){
				SQLDBCheckValueExist=false;
				ExtentTestManager.reportStepFail(driver,"Query '"+ query + "' executed successfully and RECORDS EXISTs for the Query.", false);

			}//If rows not available FALSE will be returned so no delete
			else if(temp < 1){
				ExtentTestManager.reportStepPass("Query '"+ query + "' has NO RECORDS availbale in DB");
				SQLDBCheckValueExist=true;
			}
			
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver, "Error occured while checking whether the executed query has any records (or) not. Error description is : "+ e.getMessage() +".", false);
			log.info("SQLDBCheckValueExist Error : " + e);
			SQLDBCheckValueExist=false;
		}
		return SQLDBCheckValueExist;
	}

	public synchronized boolean SQLDBCheckNoOfRowsExist(String sqltablename, String strsqlcolumnname,String strsqlcondition,String  strNoOfRowsShouldBePresent,int strExecEventFlag){
		boolean SQLDBCheckValueExist= false;
		String query=null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String noOfRowsShouldBePresent=null;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				noOfRowsShouldBePresent=getTestData(testDataFilePathStatic, testComponentNameStatic,strNoOfRowsShouldBePresent,gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"";
			ResultSet rs_SQLServer = stmt.executeQuery(query);		

			int temp=0;	
			while(rs_SQLServer.next()){
				temp++;
			}

			if(temp==(Integer.parseInt(noOfRowsShouldBePresent))){
				ExtentTestManager.reportStepPass("Actual No. of Rows '"+temp+"' for the Query *"+query+"* matches with expected No of Rows '"+noOfRowsShouldBePresent+"'");
				SQLDBCheckValueExist=true;
			}else{
				ExtentTestManager.reportStepFail(driver, "Actual No. of Rows '"+temp+"' for the Query *"+query+"* does not match with expected No of Rows '"+noOfRowsShouldBePresent+"'", false);
				SQLDBCheckValueExist=true;
			}

		}catch (NullPointerException e) {
				ExtentTestManager.reportStepFail(driver,"Null Pointer exception occured while comparing the No.of Records", false);
				SQLDBCheckValueExist=false;
				
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,    "Error occured while checking whether the executed query has any records (or) not. Error description is : "+ e.getMessage() +".", false);
			SQLDBCheckValueExist=false;
		}
		return SQLDBCheckValueExist;
	}

	public synchronized boolean WaitUntilPatternAppearsInLogFiles(String filePath,String startFileNameValue,String endFileNameValue,String strLineContains,String strPatterToCheck,String envVariableName ,int strExecEventFlag){

		boolean result= false;
		String path = null;
		File[] listOfFile = null;
		String fileName = null;
		Scanner in = null;
		boolean found=false;
		String lineContains=null;
		String patterToCheck=null;
		String envVariable=null;
		String startFileNameFromExcel=null;
		String endFileNameFromExcel=null;

		try{
			if(strExecEventFlag == 1){
				path = property.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic, filePath, strExecEventFlag));
				startFileNameFromExcel= getTestData(testDataFilePathStatic, testComponentNameStatic, startFileNameValue,gblrecordsCounterStatic);
				endFileNameFromExcel= getTestData(testDataFilePathStatic, testComponentNameStatic, endFileNameValue,gblrecordsCounterStatic);
				lineContains= getTestData(testDataFilePathStatic, testComponentNameStatic, strLineContains,gblrecordsCounterStatic);
				patterToCheck= getTestData(testDataFilePathStatic, testComponentNameStatic, strPatterToCheck,gblrecordsCounterStatic);
				envVariable= getTestData(testDataFilePathStatic, testComponentNameStatic, envVariableName,gblrecordsCounterStatic);
			}

			if(path==null ||startFileNameFromExcel==null ||endFileNameFromExcel==null ||lineContains==null||patterToCheck==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}

			File directory = new File("//\\" +path);
			boolean fileexist=false;
			int fileAppeartime=0;

			while(fileAppeartime<30){
				Thread.sleep(1000);
				listOfFile = directory.listFiles();
				if(listOfFile.length != 0){
					log.info("Directory has files");
					fileexist=true;
					break;

				}else{
					log.info("No File is available in directory. looping again with 60 secs");
				}

				log.info("No File is available in directory with 60 secs");
				fileAppeartime++;
			}

			if(fileexist){
				log.info("Files avialble in the directiory");
			}else{
				log.info("No File is available in directory for 60secs");
				ExtentTestManager.reportStepFail(driver,"No File is not available in the given directory" , false);
				return false;
			}

			boolean foundstatus=false;
			int time=0;
			while(time<30){
				Thread.sleep(1000);	
				listOfFile = directory.listFiles();
				for(int i = 0; i<listOfFile.length; i++){
					log.info("i is : "+i);
					
					if(listOfFile[i].isFile()){
						fileName = listOfFile[i].getName();
						log.info("Found a file and name is : "+fileName);
						
						if(fileName.startsWith(startFileNameFromExcel) && fileName.endsWith(endFileNameFromExcel)){
							log.info("FileName exact match : "+fileName);
							fileName=listOfFile[i].getName();
							foundstatus=true;
						}
					}
				}
				
				if(foundstatus){
					log.info("FileName exact match is found. Braking the loop");
					break;
				}else{
					log.info("FileName not found in path. Continue the loop");
				}
				time++;
			}

			if(fileName == null){
				ExtentTestManager.reportStepFail(driver,"File starting with Name '"+startFileNameFromExcel+"' file is not available in the directory" , false);
				return false;
			}

			File file =new File("//\\" +path +fileName);
			log.info(file.getAbsolutePath()+" || "+file.getName());
			int i=0;
			while(i<60){
				Thread.sleep(1000);
				
				try {
					in = new Scanner(file);
					
					while(in.hasNext())	{
						
						String line=in.nextLine();
						if(line.contains(lineContains)){
							log.info("Line contains Match found. Line -> "+line);
							Pattern p = Pattern.compile(patterToCheck);
							Matcher m = p.matcher(line);

							while (m.find()){
								found=true;
								log.info("Pattern Match found in the Line!");
								log.info("Match is : "+m.group(0));
								ExtentTestManager.reportStepPass("Log containing the word '"+lineContains+"' has the Matched Expected Pattern '"+m.group(0) +"'");
								if(!(envVariable.trim().equalsIgnoreCase("NA"))){
									log.info("Storing the Pattern Matched in the Env Variable '"+envVariable+"'");
									Runtimevalue.setProperty(envVariable, m.group(0));
									ExtentTestManager.reportStepPass("The Dynamic Value '"+m.group(0)+"' is successfully stored in the Runtime Varaible '"+envVariable+"'.");
								}
								break;
							}

							if(found){
								log.info("Pattern Match found breaking the HasNext Loop");
								break;
							}else{
								log.info("Pattern Match not found. Checking teh next line that contains expected word");
							}
						}
					}

					if(found){
						log.info("Match found, Breaking the Time Loop");
						result=true;
						break;
					}else{
						log.info("Match not found. Continue the Loop...");
					}

				} catch (FileNotFoundException e) {
					log.info("FileNotFoundException occured match founder... :"+e);
					//e.printStackTrace();
				}catch (Exception e) {
					log.info("Excption occured match founder... : "+e);
					ExtentTestManager.reportStepFail(driver, "Error occured while finding the pattern from Winservice Log. Error description is : "+ e.getMessage() +".", false);
					//e.printStackTrace();
				}
				
				i++;
			}

			if(result){
				ExtentTestManager.reportStepPass("Log file starting with name '"+startFileNameFromExcel+"' from Path '"+path+"' contains the Expected Log Pattern '"+patterToCheck+"'");
			}else{
				ExtentTestManager.reportStepFail(driver,"Log file starting with name '"+startFileNameFromExcel+"' from Path '"+path+"' does not contains the Expected Log Pattern '"+patterToCheck+"' within 60 secs", false);
			}

		} catch(Exception e) { 
			result=false;
			ExtentTestManager.reportStepFail(driver,"Error occured in the WinserviceWaitUntilPatternAppears. Error description is : "+ e.getMessage() +".", false);
			log.info("Error : " + e);
		}
		return result;
	}

	public synchronized boolean WebElementTextCompareFromGAF(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag){
		String actualResult=null;
		String strData=null;
		boolean WebElementValueCompareFromEnv=false;
		try{
			if (strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
				strData=GAFValue.getProperty(strData);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}
			actualResult = selectByLocatorType(getValueFromPOM).getText();

			if((actualResult.trim()).equalsIgnoreCase(strData.trim())){
				ExtentTestManager.reportStepPass("The  Actual Value '" +actualResult+ "' matches with the Expected value '"+strData+ "' in the input field '"+strTestObject+"'");
				WebElementValueCompareFromEnv=true;
			}else{
				ExtentTestManager.reportStepFail(driver, "The  Actual Value '" +actualResult+ "' does not match with the Expected value '"+strData+ "' in the input field '"+strTestObject+"'", true);
				WebElementValueCompareFromEnv=false;
			}
		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while WebElementTextCompareFromGAF. Error description is :"+e.getMessage(), true);
			WebElementValueCompareFromEnv=false;
		}
		return WebElementValueCompareFromEnv;
	}

	public synchronized boolean ESHOPSQLDBSelectConditionEnvvarSuffix(String sqltablename, String strsqlcolumnname,String strsqlcondition,String strenvironmentvariable,String strExpectedvalue,String strSuffixToADD,int strExecEventFlag){
		boolean ESHOPSQLDBSelectConditionEnvvar= false;
		String query = null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String SQL_condition_value = null;		
		String Expected_value = "";
		String Actual_Value = null;
		String suffixToADD =null;
		
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				Expected_value=getTestData(testDataFilePathStatic, testComponentNameStatic,strExpectedvalue,gblrecordsCounterStatic);
				SQL_condition_value = Runtimevalue.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic,strenvironmentvariable,strExecEventFlag));
				suffixToADD=getTestData(testDataFilePathStatic, testComponentNameStatic,strSuffixToADD,gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null||suffixToADD==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"='"+ SQL_condition_value+suffixToADD+"'";
			ResultSet Eshop_SQLServer = EShopstmt.executeQuery(query);
			//Newly added on  20-09-2016 to validate system IP against DB value
			if (Expected_value.equalsIgnoreCase("GET_HOST_IP")){
				InetAddress IP=InetAddress.getLocalHost();
				Expected_value=	IP.getHostAddress();
			}
			Eshop_SQLServer.next();
			Actual_Value = Eshop_SQLServer.getString(1).trim();
			if(!Eshop_SQLServer.wasNull()){     
				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					ESHOPSQLDBSelectConditionEnvvar=true;
				}else if(!(Actual_Value.equals(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);
					ESHOPSQLDBSelectConditionEnvvar=false;
				}
			}

			else if(Eshop_SQLServer.wasNull()){    
				if(Expected_value.equals("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					ESHOPSQLDBSelectConditionEnvvar=true;
				}else if(!(Expected_value.equals("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);  	 
					ESHOPSQLDBSelectConditionEnvvar=false;
				}
			}
		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,    "Error occured while comparing the values in SQL query.Error description is : "+ e.getMessage() +".", true);
			ESHOPSQLDBSelectConditionEnvvar=false;
		}
		return ESHOPSQLDBSelectConditionEnvvar;
	}

	public synchronized boolean waitUntilWebElementTextAppear(String getValueFromPOM,String strTestObject) {
		boolean WebEditTextCompare=false;
		String ActualValue=null;
		boolean appears=false;
		int i=0;
		try {

			while(!appears && i<30){
				ActualValue = selectByLocatorType(getValueFromPOM).getText();
				log.info("Actual value is : '"+ActualValue+"' and length is : '"+ActualValue.length()+"'");
				if(ActualValue.length() == 0){
					log.info("Text not appears and i is : "+i);
					i++;
					Thread.sleep(1000);
				}else{
					log.info("Text appeared and i is : "+i+" value is : "+ActualValue);
					appears=true;
				}
			}
		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Error occured while getting the text from the input field :'"+strTestObject+"'and the error description is :"+e.getMessage(), true);
			WebEditTextCompare=false;
		}

		try{
			if(appears){
				ExtentTestManager.reportStepPass("Text '" +ActualValue+ "' appeared in the Webelement '"+strTestObject+"' within 30 Secs");
				WebEditTextCompare=true;
			}else{
				ExtentTestManager.reportStepFail(driver, "No Text appeared in the Webelement '"+strTestObject+"' within 30 Secs", true);
				WebEditTextCompare=false;
			}
		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Error occured while waitUntilWebElementTextAppear. Error description is :"+e.getMessage(), true);
			WebEditTextCompare=false;
		}
		return WebEditTextCompare;
	}

	public synchronized boolean webElementClearText(String getValueFromPOM, String strTestObject){

		boolean elementStatus = false;
		try {
			if(selectByLocatorType(getValueFromPOM).isDisplayed()){
				selectByLocatorType(getValueFromPOM).sendKeys(Keys.DELETE);
				selectByLocatorType(getValueFromPOM).clear();			
				ExtentTestManager.reportStepPass("Text field is cleared successfully for the Element '"+strTestObject+"'");		
				elementStatus = true;
			}else{
				ExtentTestManager.reportStepFail(driver,"Element is not found and Text is not cleared for the Element '"+strTestObject+"'", true); 	
				elementStatus = false;
			}
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Exception occurred in 'webElementClearText' function", true); 	
			elementStatus = false;
		}
		return elementStatus;
	}

	public synchronized boolean sleepForFunction(){
		try{
			Thread.sleep(5000);
		}catch(Exception e){
			log.info("Issue in Thread.sleep "+e.getMessage());
		}
		return true;
	}

	public synchronized boolean weblistUICompareSQLDBValues(String getValueFromPOM, String strTestObject, String sqltablename, String strsqlcolumnname,String strsqlcondition,int itemscount_not_consider,int strExecEventFlag){

		boolean WeblistSQLDBitemsverify= false;
		String query = null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String[] sqldbvalues = null;
		String[] weblistvalues = null;
		ResultSet rs_SQLServer =null;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail("Required details are not provided in the Data Sheet",false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"";
			rs_SQLServer = stmt.executeQuery(query);
			List<String> rowValues = new ArrayList<String>();
			while (rs_SQLServer.next()) {
				rowValues.add(rs_SQLServer.getString(1));
			}   
			sqldbvalues = (String[]) rowValues.toArray(new String[rowValues.size()]);
			Select se = new Select(selectByLocatorType(getValueFromPOM));
			List<WebElement> options = se.getOptions();
			List<String> all_elements_text=new ArrayList<String>();

			for(int j=1; j<options.size(); j++){
				all_elements_text.add(options.get(j).getText());
			}

			weblistvalues = (String[]) all_elements_text.toArray(new String[all_elements_text.size()]);

			if(sqldbvalues.length == weblistvalues.length - itemscount_not_consider)
			{
				if(sqldbvalues.length==0){
					
					log.info("No value present in DB as well list");
					ExtentTestManager.reportStepPass("No Dropdown Value is Present in the Dropdown '"+strTestObject+"' and the corresponding Table");
					WeblistSQLDBitemsverify=true;
				
				}else{
					for (int i=0; i<sqldbvalues.length; i++){

						if (weblistvalues[i+1].equals(sqldbvalues[i])){
							ExtentTestManager.reportStepPass("Actual value '"+ weblistvalues[i+1] +"' in the dropdown : "+ strTestObject +"' matches the expected value : '"+ sqldbvalues[i] +"'.");
							WeblistSQLDBitemsverify=true;
						} else{
							ExtentTestManager.reportStepFail(driver,"Actual value : '"+ weblistvalues[i+1] +"' in the dropdown : "+ strTestObject +"' doesn't matches the expected value : '"+ sqldbvalues[i] +"'.", true);
							WeblistSQLDBitemsverify=false;
						}
					}
				}
			} else {
				ExtentTestManager.reportStepFail(driver,"No. of items present in the dropdown : "+ strTestObject +"' doesn't matches with the number of items in the table : '"+ Table_name +"' with condition : '"+ SQL_condition +"'.", true);
				WeblistSQLDBitemsverify=false;
			}

		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver,  "Error occured while performing weblistUICompareSQLDBValues. Error description is : "+ e.getMessage() +".", true);
			WeblistSQLDBitemsverify=false;
		}
		return WeblistSQLDBitemsverify;
	}
	
	public synchronized boolean WebElementisNotVisible(String getValueFromPOM, String strTestObject) {
		boolean WebElementisNotVisible = false;
		try {
			int elementcount=listSelectByLocatorType(getValueFromPOM).size();

			if(elementcount > 0){
				boolean elementvisible=selectByLocatorType(getValueFromPOM).isDisplayed();
				if(!elementvisible){
					WebElementisNotVisible = true;
					ExtentTestManager.reportStepPass("'"+strTestObject+"' is not displayed");	
				} else {
					WebElementisNotVisible = false;
					ExtentTestManager.reportStepFail(driver, "'"+strTestObject+"' is Displayed(visible)", true);
				}
			} else{
				WebElementisNotVisible = true;
				ExtentTestManager.reportStepPass("'"+strTestObject+"' is not displayed");
			}

		} catch (org.openqa.selenium.NoSuchElementException e) { 	

			WebElementisNotVisible = true;
			ExtentTestManager.reportStepPass("'"+strTestObject+"' is not Displayed");

		} catch (NullPointerException e){

			WebElementisNotVisible = true;
			ExtentTestManager.reportStepPass("'"+strTestObject+"' is not Displayed");
		}

		catch (Exception e) { 	
			ExtentTestManager.reportStepFail(driver, "Exception occured while checking WebElementisNotVisible. Exception is "+e, true);
			WebElementisNotVisible=false;
		}

		return WebElementisNotVisible;
	}

	public synchronized boolean selectEnterTextClickEnter(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag ){
		boolean selectEnterTextClickEnter = false;
		String strData=null;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
				return false;
			}
			selectByLocatorType(getValueFromPOM).sendKeys(Keys.chord(Keys.CONTROL, "a"));
			selectByLocatorType(getValueFromPOM).sendKeys(strData);
			selectByLocatorType(getValueFromPOM).sendKeys(Keys.ENTER);

			ExtentTestManager.reportStepPass( "Cleared Text field and '" +  strData + "' is entered in the Textbox -  '"+strTestObject+"' and clicked on 'ENTER' button successfully");
			selectEnterTextClickEnter = true;	
		} catch (Exception e) { 	
			ExtentTestManager.reportStepFail(driver,"Text '" + strData + "' was not cleared and entered in the Textbox - '"+strTestObject+"'", true);
			selectEnterTextClickEnter = false;
		}
		return selectEnterTextClickEnter;
	}

	public synchronized boolean webElementDateCompare(String getValueFromPOM, String dateFormat) {
		boolean web_Date_Compare = false;
		String webElementValue = null;
		DateFormat dateformat = null;
		Date date = null;
		String todayDate = null;
		String conValueFromWeb = null;

		try{
			webElementValue = selectByLocatorType(getValueFromPOM).getText();
			conValueFromWeb = webElementValue.split("\\ ")[0];
			dateformat = new SimpleDateFormat(dateFormat);
			date = new Date();
			todayDate = dateformat.format(date);

			if((conValueFromWeb.trim()).equals(todayDate.trim())){
				ExtentTestManager.reportStepPass("Expected date '"+conValueFromWeb+"' is mached with today's date '"+todayDate+"' successfully ");
				web_Date_Compare = true;
			}
			else{
				ExtentTestManager.reportStepFail(driver,"Expected date '"+conValueFromWeb+"' is not mached with today's date '"+todayDate+"' " , true); 
				web_Date_Compare = false;
			}
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"'"+conValueFromWeb+"' is not mached with today's date" , true); 
			web_Date_Compare = false;
		}
		return web_Date_Compare;
	}

	public synchronized boolean WebButtonClick(String getValueFromPOM, String strTestObject) {
		boolean WebButtonClick= false;
		try {
			selectByLocatorType(getValueFromPOM).click();
			ExtentTestManager.reportStepPass("Web Button '"+strTestObject+"'  clicked successfully");
			WebButtonClick=true;
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Web Button '"+strTestObject+"' Button was not clicked", true); 	
			WebButtonClick=false;
		}
		return WebButtonClick;
	}

	public synchronized boolean storeSQLDBValueInEnv(String sqltablename, String strsqlcolumnname, String strsqlcondition, int strExecEventFlag){
		boolean storeSQLDBValueInEnv= false;
		String query = null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String getIDValueFromRecord = null;
		ResultSet rs_SQLServer=null;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"";

			rs_SQLServer = stmt.executeQuery(query);
			rs_SQLServer.next();
			getIDValueFromRecord = rs_SQLServer.getString(1).trim();
			Runtimevalue.setProperty("getIDValueForPendingApprovals", getIDValueFromRecord);
			ExtentTestManager.reportStepPass("Selected DB value is stored in RunTime Environment successfully");
			storeSQLDBValueInEnv = true;
		} catch (Exception e) {           // If no record is present in the fired Query
			ExtentTestManager.reportStepFail(driver,"No Record available in DB for the query: "+query, false);
			storeSQLDBValueInEnv = false;
		}
		return storeSQLDBValueInEnv;
	}

	public synchronized boolean webElementTabClick(String getValueFromPOM, String strTestObject){
		boolean elementStatus = false;
		try {
			if(selectByLocatorType(getValueFromPOM).isDisplayed()){
				selectByLocatorType(getValueFromPOM).sendKeys(Keys.TAB);			
				ExtentTestManager.reportStepPass("Tab button is clicked successfully on '"+strTestObject+"' field");		
				elementStatus = true;
			}else{

				ExtentTestManager.reportStepFail(driver,"Element is not found and Tab button is not clicked on '"+strTestObject+"' field", true); 	
				elementStatus = false;

			}
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Exception occurred in 'webElementTabClick' function. Exception is "+e, true); 	
			elementStatus = false;
		}
		return elementStatus;
	}

	public synchronized boolean Webeditselectentertext(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag ){
		boolean Webeditselectentertext= false;
		String strData=null;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}
			selectByLocatorType(getValueFromPOM).sendKeys(Keys.chord(Keys.CONTROL, "a"));
			selectByLocatorType(getValueFromPOM).sendKeys(strData);
			ExtentTestManager.reportStepPass("The Text '" +  strData + "' is entered in the Textbox -  '"+strTestObject+"'  successfully");
			Webeditselectentertext=true;	
		} catch (Exception e) { 	
			ExtentTestManager.reportStepFail(driver, "The Text '" + strData + "' was not entered in the Textbox - '"+strTestObject+"'", true);
			Webeditselectentertext=false;
		}
		return Webeditselectentertext;
	}

	public synchronized boolean WebElementisVisible(String getValueFromPOM, String strTestObject) {
		boolean elementvisible;
		boolean WebElementisVisible = false;
		try {
			elementvisible=selectByLocatorType(getValueFromPOM).isDisplayed();
			String output=selectByLocatorType(getValueFromPOM).getText();
			if(elementvisible){
				WebElementisVisible = true;
				ExtentTestManager.reportStepPass( "'"+strTestObject+"' is Displayed with value '"+output+ "'");
			}else{
				WebElementisVisible=false;
				ExtentTestManager.reportStepFail(driver,"'"+strTestObject+"' is not Displayed", true);
			}
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Exception occured while verifying the Element is Visible. Exception is "+e, true);
			WebElementisVisible=false;
		}
		return WebElementisVisible;
	}

	public synchronized boolean webTableRadioButton(String getValueFromPOM, String strColumnName, int strExecEventFlag) {
		boolean elementStatus = false;
		String elementValue = null;
		String actualValue = null;
		try{
			if(strExecEventFlag==1){
				elementValue = getTestData(testDataFilePathStatic, testComponentNameStatic, strColumnName,gblrecordsCounterStatic);
			}
			if(elementValue == null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
				return false;
			}
			List<WebElement> element = listSelectByLocatorType(getValueFromPOM);
			for(WebElement checkElement : element){
				actualValue = checkElement.getText().trim();
				if(actualValue.equalsIgnoreCase(elementValue)){
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].style.border='2px groove white'", checkElement);
					checkElement.click();
					ExtentTestManager.reportStepPass("'"+elementValue+"' radio button is clicked from the web table successfully" );		
					return true;
				}
			}
			if(!actualValue.equalsIgnoreCase(elementValue)){
				ExtentTestManager.reportStepFail(driver,"'"+elementValue +"' radio button is not clicked from the web table" , true); 
				return false;
			}
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"'"+elementValue +"' radio button is not clicked from the web table. Exception is "+e , true); 
			elementStatus = false;
		}
		return elementStatus;
	}

	/*----------------------------------------- LAKSHMAN ***END***-----------------------------------------*/


	/*----------------------------------------- PRAVEEN CODE -----------------------------------------*/
	
	public synchronized boolean checkElementIsDisplayed(String getValueFromPOM, String strTestObject) {
		boolean elementvisible;
		boolean checkElementIsDisplayed = false;
		try {
			elementvisible=selectByLocatorType(getValueFromPOM).isDisplayed();
			if(elementvisible){
				checkElementIsDisplayed = true;
				ExtentTestManager.reportStepPass("Element '"+strTestObject+"' is Displayed successfully");
			}else{
				checkElementIsDisplayed = false;
				ExtentTestManager.reportStepFail(driver,"Element '"+strTestObject+"' is not Displayed", true);
			}
		} catch (Exception e) { 	
			checkElementIsDisplayed = false;
		}
		return checkElementIsDisplayed;
	}

	public synchronized boolean readLogTopUpBal(String filePath, String startsWith, String endsWith, String textName, int strExecEventFlag) {
		boolean functionStatus = false;
		String path = null;
		String startValue = null;
		String endValue = null;
		String textValue = null;
		File[] listOfFile = null;
		String fileName = null;
		String actualText = null;
		Pattern pattern = null;
		try{
			if(strExecEventFlag == 1){
				path = property.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic,filePath, strExecEventFlag));
				startValue = getTestData(testDataFilePathStatic, testComponentNameStatic,startsWith,gblrecordsCounterStatic);
				endValue = getTestData(testDataFilePathStatic, testComponentNameStatic,endsWith,gblrecordsCounterStatic);
				textValue = getTestData(testDataFilePathStatic, testComponentNameStatic,textName,gblrecordsCounterStatic);
			}

			if(path==null || startValue==null || endValue==null || textValue==null){
				ExtentTestManager.reportStepFail(driver,   "Required details are not provided in test data sheet.", false);
				return false;
			}

			File directory = new File("//\\" +path);
			listOfFile = directory.listFiles();
			if(listOfFile.length != 0){
				for(int i = 0; i<listOfFile.length; i++){
					if(listOfFile[i].isFile()){
						fileName = listOfFile[i].getName();
						if(fileName.startsWith(startValue) && fileName.endsWith(endValue)){
							File ff = new File("//\\" +path +fileName);
							String fileContent = IOUtils.toString(ff.toURI(),"UTF-8");
							actualText = "Text is not available in log file";
							pattern = Pattern.compile(textValue);
							Matcher matcher = pattern.matcher(fileContent);

							while(matcher.find()){
								actualText = matcher.group(1)+matcher.group(2)+matcher.group(3)+matcher.group(4)+matcher.group(5);
								log.info("Text is matched");
								ExtentTestManager.reportStepPass("Text '"+actualText+"' is matched with expected text in the File with Name '"+startValue+"'");
								return true;
							}

							log.info(actualText);
							functionStatus = false;
							ExtentTestManager.reportStepFail(driver,""+actualText+" is not matched with the contents in the file with Name '"+startValue+"'" , false);
						}
					}
				}

			}else{
				ExtentTestManager.reportStepFail(driver,"No files available in the given directory" , false);
				return false;
			}
		}catch(Exception e){
			functionStatus = false;
			ExtentTestManager.reportStepFail(driver,"Error occurred while match with expected text in the file with name '"+startValue+"'" , false);
		}
		return functionStatus;
	}

	public synchronized boolean readLogTopUpBundle(String filePath, String startsWith, String endsWith, String textName, int strExecEventFlag) {
		boolean functionStatus = false;
		String path = null;
		String startValue = null;
		String endValue = null;
		String textValue = null;
		File[] listOfFile = null;
		String fileName = null;
		String actualText = null;
		Pattern pattern = null;

		try{

			if(strExecEventFlag == 1){

				path = property.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic, filePath, strExecEventFlag));
				startValue = getTestData(testDataFilePathStatic, testComponentNameStatic, startsWith,gblrecordsCounterStatic);
				endValue = getTestData(testDataFilePathStatic, testComponentNameStatic, endsWith,gblrecordsCounterStatic);
				textValue = getTestData(testDataFilePathStatic, testComponentNameStatic, textName,gblrecordsCounterStatic);

			}

			if(path==null || startValue==null || endValue==null || textValue==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}

			File directory = new File("//\\" +path);
			listOfFile = directory.listFiles();
			if(listOfFile.length != 0){
				for(int i = 0; i<listOfFile.length; i++){
					if(listOfFile[i].isFile()){
						fileName = listOfFile[i].getName();
						if(fileName.startsWith(startValue) && fileName.endsWith(endValue)){

							File ff = new File("//\\" +path +fileName);
							String fileContent = IOUtils.toString(ff.toURI(),"UTF-8");
							actualText = "Text is not available in log file";
							pattern = Pattern.compile(textValue);
							Matcher matcher = pattern.matcher(fileContent);

							while(matcher.find()){
								actualText = matcher.group(1)+matcher.group(2)+matcher.group(3)+matcher.group(4)+matcher.group(5)+matcher.group(6)+matcher.group(7)+matcher.group(8)+matcher.group(9);
								log.info("Text is matched");
								ExtentTestManager.reportStepPass("Text '"+actualText+"' is matched with expected text in the File with Name '"+startValue+"'");
								return true;
							}
							log.info(actualText);
							functionStatus = false;
							ExtentTestManager.reportStepFail(driver,""+actualText+" is not matched with the contents in the file with Name '"+startValue+"'" , false);
						}	
					}
				}

			}else{
				ExtentTestManager.reportStepFail(driver,"No files available in the given directory" , false);
				return false;
			}
		}catch(Exception e){

			functionStatus = false;
			ExtentTestManager.reportStepFail(driver,"Error occurred while match with expected text in the file with name '"+startValue+"'" , false);
		}
		return functionStatus;
	}

	public synchronized boolean PerformAction_ModalHandle(String strTestObject) {
		boolean handle =false;
		Object scrollBarPresent=false;
		try{
			Thread.sleep(500);
			Thread.sleep(30000);
			for(int i=0; i<60; i++){
				try{
					Thread.sleep(1000);
					log.info("Insdie IF.... i is -> "+i);
					scrollBarPresent = ((JavascriptExecutor)driver).executeScript("return document.activeElement.getElementsByClassName('modal-footer')[0].innerHTML.trim()!=null;");
					if((boolean) scrollBarPresent){
						handle = true;
						ExtentTestManager.reportStepPass("'The element '"+ strTestObject +"' is displayed.");
						log.info("Element is appeared");
						break;
					}
				}catch(WebDriverException e){
					log.info("WebDriverException caught inside for since Popup is not appeared");
				}
			}

			if((boolean) scrollBarPresent){
			}else{
				ExtentTestManager.reportStepPass("'Element '"+ strTestObject +"' is not displayed.");
			}

		}catch(StaleElementReferenceException e){
			return PerformAction_ModalHandle(   strTestObject);
		}
		catch(Exception e){
			handle = false;
			ExtentTestManager.reportStepFail(driver,"Exception occured while PerformAction_ModalHandle. Error message is : "+ e +"." , true);
		}
		return handle;

	}

	public synchronized boolean HLRDBOpenConnection(String dbserver, String portnumber, String dbname, String dbusername, String dbpassword){
		boolean elementStatus= false;
		String serverName = property.getProperty(dbserver);
		String portNumber = property.getProperty(portnumber);
		String sid = property.getProperty(dbname);
		String dbUrl = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid; 
		String username = property.getProperty(dbusername);   
		String password = property.getProperty(dbpassword); 

		if(dbname==null || dbusername==null || dbpassword==null || dbserver==null || portnumber==null){
			ExtentTestManager.reportStepFail(driver, "Required details are not provided in the datasheet.", false);
			return false;
		}
		try {
			String driverName = "oracle.jdbc.OracleDriver";
			Class.forName(driverName);         
			hlrConnection = DriverManager.getConnection(dbUrl,username,password);
			hlrStatement = hlrConnection.createStatement(); 
			elementStatus=true;
			ExtentTestManager.reportStepPass("HLR DB Connection with DB '"+sid+"' in '"+serverName+"' is established Successfully.");

		} catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while establishing connection with HLR DB. Error description is : "+ e.getMessage() +".", false);
		}
		return elementStatus;
	}

	public synchronized boolean IMGDBOpenConnection(String dbserver, String portnumber, String dbname, String dbusername, String dbpassword){
		boolean elementStatus= false;
		String serverName = property.getProperty(dbserver);
		String portNumber = property.getProperty(portnumber);
		String sid = property.getProperty(dbname);
		String dbUrl = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid; 
		String username = property.getProperty(dbusername);   
		String password = property.getProperty(dbpassword); 

		if(dbname==null || dbusername==null || dbpassword==null || dbserver==null || portnumber==null){
			ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the datasheet.", false);
			return false;
		}
		try {
			String driverName = "oracle.jdbc.OracleDriver";
			Class.forName(driverName);         
			imgConnection = DriverManager.getConnection(dbUrl,username,password);
			imgStatement = imgConnection.createStatement(); 
			elementStatus=true;
			ExtentTestManager.reportStepPass("IMG DB Connection with DB '"+sid+"' in '"+serverName+"' is established Successfully.");

		} catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while establishing connection with IMG DB. Error description is : "+ e.getMessage() +".", false);
		}
		return elementStatus;
	}
	
	public synchronized boolean IMGDBCloseConnection(){
		boolean elementStatus= false;
		try {
			imgConnection.close(); 
			elementStatus=true;
			ExtentTestManager.reportStepPass("IMG DB Connection is disconnected successfully.");

		} catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver,"Error occured while closing the IMG DB connection. Error description is : "+ e.getMessage() +".", false);
		}
		return elementStatus;
	}

	public synchronized boolean IMGDBUpdate(String sqltablename, String strsqlcolumnname,String strsqlcolumnvalue,String strsqlcondition,int strExecEventFlag){
		String Table_name;
		String Column_name;
		String Column_Value;
		String SQL_condition;
		String query;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				Column_Value=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnvalue, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
			}else{
				Table_name=sqltablename;
				Column_name=strsqlcolumnname;
				Column_Value=strsqlcolumnvalue;
				SQL_condition=strsqlcondition;
			}

			if(Table_name==null || Column_name==null || Column_Value==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			if(Column_Value.contains("to_date")){
				query = "update "+Table_name+" set "+Column_name+"="+Column_Value+" where "+SQL_condition;
			}else{
				if(Column_Value.equalsIgnoreCase("null")){
					query = "update "+Table_name+" set "+Column_name+"=null where "+SQL_condition;
				}else{
					query = "update "+Table_name+" set "+Column_name+"='"+Column_Value+"' where "+SQL_condition;
				}
			}

			imgStatement.execute(query);
			ExtentTestManager.reportStepPass("IMG Update Query  "+ query + "  executed successfully.");
			return true;
		} catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver,"Error occured while executing IMG Update Query. Error description is : "+ e.getMessage(), false);
			return false;
		}
	}
	
	public synchronized boolean IMGDBSelect(String imgtablename, String imgcolumnname, String imgcondition, String imgcolumnvalue, int strExecEventFlag){
		boolean functionStatus=false;
		String query;
		String check;
		String Table_name;
		String Column_name;
		String SQL_condition;
		String Expected_value;
		String Actual_Value;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, imgtablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, imgcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, imgcondition, gblrecordsCounterStatic);
				Expected_value=getTestData(testDataFilePathStatic, testComponentNameStatic, imgcolumnvalue, gblrecordsCounterStatic);
			}else{
				Table_name=imgtablename;
				Column_name=imgcolumnname;
				SQL_condition=imgcondition;
				Expected_value=imgcolumnvalue;
			}

			if(Table_name==null || Column_name==null || SQL_condition==null||Expected_value==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			query = "SELECT "+Column_name+" FROM "+Table_name+" WHERE "+SQL_condition+" order by 1 desc";
			check = "SELECT "+Column_name+" FROM "+Table_name+" WHERE "+SQL_condition+" order by 1 desc";

			//Check for Record Available
			ResultSet rs_SQLServerCheck = imgStatement.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = imgStatement.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the IMG Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the IMG Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else if(!(rs_SQLServerCheck.wasNull())){

				ResultSet rs_SQLServer = imgStatement.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1).trim();

				if (Expected_value.contains("CURRENT_DATE")){
					String []ExpectedvalueWithFormat=Expected_value.split("#");
					String db_Date = Actual_Value.split(" ")[0];
					Actual_Value=db_Date;
					Date date=new Date();			
					SimpleDateFormat dateformat=new SimpleDateFormat(ExpectedvalueWithFormat[1]);
					Expected_value=dateformat.format(date);
				}

				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the IMG Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Actual_Value.equalsIgnoreCase(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the IMG Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in IMG query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean HLRDBCloseConnection(){
		boolean elementStatus= false;

		try {
			// closing DB Connection       
			hlrConnection.close(); 
			elementStatus=true;
			ExtentTestManager.reportStepPass("HLR DB Connection is disconnected successfully.");

		} catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while closing the HLR DB connection. Error description is : "+ e.getMessage() +".", false);
		}
		return elementStatus;
	}
	
	public synchronized boolean HLRDBUpdate(String sqltablename, String strsqlcolumnname,String strsqlcolumnvalue,String strsqlcondition,int strExecEventFlag){
		String Table_name;
		String Column_name;
		String Column_Value;
		String SQL_condition;
		String query;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				Column_Value=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnvalue, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
			}else{
				Table_name=sqltablename;
				Column_name=strsqlcolumnname;
				Column_Value=strsqlcolumnvalue;
				SQL_condition=strsqlcondition;
			}

			if(Table_name==null || Column_name==null || Column_Value==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			if(Column_Value.contains("to_date")){
				query = "update "+Table_name+" set "+Column_name+"="+Column_Value+" where "+SQL_condition;
			}else{
				if(Column_Value.equalsIgnoreCase("null")){
					query = "update "+Table_name+" set "+Column_name+"=null where "+SQL_condition;
				}else{
					query = "update "+Table_name+" set "+Column_name+"='"+Column_Value+"' where "+SQL_condition;
				}
			}

			hlrStatement.execute(query);
			ExtentTestManager.reportStepPass("HLR Update Query  "+ query + "  executed successfully.");
			return true;
		} catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver,"Error occured while executing HLR Update Query. Error description is : "+ e.getMessage(), false);
			return false;
		}
	}

	public synchronized boolean HLRDBSelect(String hlrtablename, String hlrcolumnname, String hlrcondition, String hlrcolumnvalue, int strExecEventFlag){
		boolean functionStatus=false;
		String query;
		String check;
		String Table_name;
		String Column_name;
		String SQL_condition;
		String Expected_value;
		String Actual_Value;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, hlrtablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, hlrcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, hlrcondition, gblrecordsCounterStatic);
				Expected_value=getTestData(testDataFilePathStatic, testComponentNameStatic, hlrcolumnvalue, gblrecordsCounterStatic);
			}else{
				Table_name=hlrtablename;
				Column_name=hlrcolumnname;
				SQL_condition=hlrcondition;
				Expected_value=hlrcolumnvalue;
			}

			if(Table_name==null || Column_name==null || SQL_condition==null||Expected_value==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			query = "SELECT "+Column_name+" FROM "+Table_name+" WHERE "+SQL_condition+" order by 1 desc";
			check = "SELECT "+Column_name+" FROM "+Table_name+" WHERE "+SQL_condition+" order by 1 desc";

			//Check for Record Available
			ResultSet rs_SQLServerCheck = hlrStatement.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = hlrStatement.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the HLR Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the HLR Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else if(!(rs_SQLServerCheck.wasNull())){

				ResultSet rs_SQLServer = hlrStatement.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1).trim();

				if (Expected_value.contains("CURRENT_DATE")){
					String []ExpectedvalueWithFormat=Expected_value.split("#");
					String db_Date = Actual_Value.split(" ")[0];
					Actual_Value=db_Date;
					Date date=new Date();			
					SimpleDateFormat dateformat=new SimpleDateFormat(ExpectedvalueWithFormat[1]);
					Expected_value=dateformat.format(date);
				}

				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the HLR Query "+query+" matches the expected value : '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Actual_Value.equalsIgnoreCase(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the HLR Query "+query+" does not match with the expected value '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in HLR query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}

	public synchronized boolean readLogCallForward(String filePath, String startsWith, String endsWith, String textName, int strExecEventFlag) {
		boolean functionStatus = false;
		String path = null;
		String startValue = null;
		String endValue = null;
		String textValue = null;
		File[] listOfFile = null;
		String fileName = null;
		String actualText = null;
		Pattern pattern = null;

		try{
			if(strExecEventFlag == 1){
				path = property.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic, filePath, strExecEventFlag));
				startValue = getTestData(testDataFilePathStatic, testComponentNameStatic, startsWith,gblrecordsCounterStatic);
				endValue = getTestData(testDataFilePathStatic, testComponentNameStatic, endsWith,gblrecordsCounterStatic);
				textValue = getTestData(testDataFilePathStatic, testComponentNameStatic, textName,gblrecordsCounterStatic);
			}

			if(path==null || startValue==null || endValue==null || textValue==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			File directory = new File("//\\" +path);
			listOfFile = directory.listFiles();
			if(listOfFile.length != 0){
				for(int i = 0; i<listOfFile.length; i++){
					if(listOfFile[i].isFile()){
						fileName = listOfFile[i].getName();
						if(fileName.startsWith(startValue) && fileName.endsWith(endValue)){
							File ff = new File("//\\" +path +fileName);
							String fileContent = IOUtils.toString(ff.toURI(),"UTF-8");
							actualText = "Text is not available in log file";
							pattern = Pattern.compile(textValue);
							Matcher matcher = pattern.matcher(fileContent);
							while(matcher.find()){
								actualText = matcher.group(1)+matcher.group(2)+matcher.group(3)+matcher.group(4)+matcher.group(5);
								log.info("Text is matched");
								ExtentTestManager.reportStepPass("Text '"+actualText+"' is matched with expected text in the File with Name '"+startValue+"'");
								return true;
							}
							functionStatus = false;
							ExtentTestManager.reportStepFail(driver,""+actualText+" is not matched with the contents in the file with Name '"+startValue+"'" , false);
							}
					}
				}
			}else{
				ExtentTestManager.reportStepFail(driver,"No files available in the given directory" , false);
				return false;
			}
		}catch(Exception e){
			functionStatus = false;
			ExtentTestManager.reportStepFail(driver,"Error occurred while match with expected text in the file with name '"+startValue+"'" , false);
		}
		return functionStatus;
	}

	public synchronized boolean msAccessDBOpenConnection(String filepath){
		boolean elementStatus= false;
		try{
			msAcsConnection=DriverManager.getConnection("jdbc:ucanaccess:"+filepath+"");
			msAcsStatement = msAcsConnection.createStatement();
			elementStatus=true;
			ExtentTestManager.reportStepPass( "MSAccess DB Connection is established from '"+filepath+"' Successfully.");
		}catch(Exception e){
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while connecting to the MSAccess DB Server.Error description is : "+ e.getMessage() +".", false);
		}
		return elementStatus;
	}

	public synchronized boolean msAccessCloseConnection(){
		boolean elementStatus= false;
		try {
			msAcsConnection.close(); 
			elementStatus=true;
			ExtentTestManager.reportStepPass("MSAccess DB Connection is disconnected successfully.");

		} catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while disconnecting the connection of MSAccess DB. Error description is : "+ e.getMessage() +".", false);
		}
		return elementStatus;
	}

	public synchronized boolean msAccessDBSelect(String msAcstablename, String msAcscolumnname, String msAcscondition, String msAcscolumnvalue, int strExecEventFlag){
		boolean elementStatus= false;
		String tablename = null;
		String condition = null;
		String columnname = null;
		String columnvalue = null;
		String actualvalue = null;
		try{
			if(strExecEventFlag==1){
				tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,msAcstablename,gblrecordsCounterStatic);
				condition=getTestData(testDataFilePathStatic, testComponentNameStatic,msAcscondition,gblrecordsCounterStatic);
				columnname=getTestData(testDataFilePathStatic, testComponentNameStatic,msAcscolumnname,gblrecordsCounterStatic);
				columnvalue=getTestData(testDataFilePathStatic, testComponentNameStatic,msAcscolumnvalue,gblrecordsCounterStatic);
			}

			if(tablename==null || condition==null || columnname==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			String query = "select "+ columnname +" from "+ tablename +" where "+ condition;
			ResultSet msAcsResultset = msAcsStatement.executeQuery(query);
			while (msAcsResultset.next()){
				actualvalue = msAcsResultset.getString(1);
			}
			if(actualvalue.equalsIgnoreCase(columnvalue))
			{
				elementStatus=true;
				ExtentTestManager.reportStepPass("Actual value '"+actualvalue+"' for the Query '"+query+"' matches the expected value : '"+columnvalue+"'");

			} else {
				elementStatus=false;
				ExtentTestManager.reportStepFail(driver,"Actual value '"+actualvalue+"' for the Query '"+query+"' does not match the expected value : '"+columnvalue+"'", false);	
			}
		} catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while executing msAccessDBSelect. Error description is : "+ e.getMessage() +".", false);
		}
		return elementStatus;
	}

	public synchronized boolean DateCompareSQLDBValue(String getValueFromPOM, String datePattern, String strTestObject){
		boolean elementStatus= false;
		String query = null;
		String actualvalue = null;
		String getDateValueFromWeb = null;
		String splittedValueFromWeb = null;
		try {
			
			if(datePattern.equalsIgnoreCase("dd/mm/yyyy")){
				query = "Select Convert(varchar(10),getDate(),103)";
			}else if(datePattern.equalsIgnoreCase("dd-mm-yyyy")){
				query = "Select Convert(varchar(10),getDate(),105)";
			}
			getDateValueFromWeb = selectByLocatorType(getValueFromPOM).getText();
			splittedValueFromWeb = getDateValueFromWeb.split("\\ ")[0];
			ResultSet rs_SQLServer = stmt.executeQuery(query);
			while (rs_SQLServer.next()){
				actualvalue = rs_SQLServer.getString(1);
			}

			if(actualvalue.equalsIgnoreCase(splittedValueFromWeb))
			{
				elementStatus=true;
				ExtentTestManager.reportStepPass("DB Actual value '"+actualvalue+"' for the SQL Query '"+query+"' matches with the expected value from webpage '"+splittedValueFromWeb+"'.");
			} else {
				elementStatus=false;
				ExtentTestManager.reportStepFail(driver,"DB Actual value '"+actualvalue+"' for the SQL Query '"+query+"' does not match with the expected value from webpage '"+splittedValueFromWeb+"'.", true);	
			}
		} catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver,"Exception occured while performing DateCompareSQLDBValue. Exception is "+e, true);
			log.info("DateCompareSQLDBValue Error : " + e);
		}
		return elementStatus;
	}

	public synchronized boolean DateCompareRRBSDBValue(String getValueFromPOM, String datePattern, String strTestObject){
		boolean elementStatus= false;
		String actualvalue = null;
		String getDateValueFromWeb = null;
		String splittedValueFromWeb = null;
		
		String query = "SELECT TO_CHAR (SYSDATE, '"+datePattern+"') AS today FROM dual";
		getDateValueFromWeb = selectByLocatorType(getValueFromPOM).getText();
		splittedValueFromWeb = getDateValueFromWeb.split("\\ ")[0];
		try {
			ResultSet rrbsresultset = rrbsstatement.executeQuery(query);
			while (rrbsresultset.next()){
				actualvalue = rrbsresultset.getString(1).trim();
			}
			
			if(actualvalue.equalsIgnoreCase(splittedValueFromWeb))
			{
				elementStatus=true;
				ExtentTestManager.reportStepPass("DB Actual value '"+actualvalue+"' for the SQL Query '"+query+"' matches with the expected value from webpage '"+splittedValueFromWeb+"'.");
			} else {
				elementStatus=false;
				ExtentTestManager.reportStepFail(driver,"DB Actual value '"+actualvalue+"' for the SQL Query '"+query+"' does not match with the expected value from webpage '"+splittedValueFromWeb+"'.", true);	
			}
		} catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver,"Exception occured while performing DateCompareRRBSDBValue. Exception is "+e, true);
			log.info("DateCompareRRBSDBValue Error : " + e);
		}
		return elementStatus;
	}

	public synchronized boolean readLogSMSFiles(String filePath, String startsWith, String endsWith, String textName, int strExecEventFlag) {

		boolean functionStatus = false;
		String path = null;
		String startValue = null;
		String endValue = null;
		String textValue = null;
		File[] listOfFile = null;
		String fileName = null;
		String actualText = null;
		Pattern pattern = null;

		try{
			if(strExecEventFlag == 1){
				path = property.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic, filePath, strExecEventFlag));
				startValue = getTestData(testDataFilePathStatic, testComponentNameStatic, startsWith,gblrecordsCounterStatic);
				endValue = getTestData(testDataFilePathStatic, testComponentNameStatic, endsWith,gblrecordsCounterStatic);
				textValue = getTestData(testDataFilePathStatic, testComponentNameStatic, textName,gblrecordsCounterStatic);
			}

			if(path==null || startValue==null || endValue==null || textValue==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			//Get the filename by given startName and endName
			File directory = new File("//\\" +path);
			listOfFile = directory.listFiles();
			if(listOfFile.length != 0){
				for(int i = 0; i<listOfFile.length; i++){
					if(listOfFile[i].isFile()){
						fileName = listOfFile[i].getName();
						if(fileName.startsWith(startValue) && fileName.endsWith(endValue)){
							//Read the file and match the expected value

							File ff = new File("//\\" +path +fileName);
							String fileContent = IOUtils.toString(ff.toURI(),"UTF-8");
							actualText = "Text is not available in log file";
							pattern = Pattern.compile(textValue);
							Matcher matcher = pattern.matcher(fileContent);

							while(matcher.find()){
								actualText = matcher.group(1)+matcher.group(2)+matcher.group(3);
								log.info("Text is matched");
								ExtentTestManager.reportStepPass("Text '"+actualText+"' is matched with expected text in the File with Name '"+startValue+"'");
								return true;
							}

							log.info(actualText);
							functionStatus = false;
							ExtentTestManager.reportStepFail(driver,""+actualText+" is not matched with the contents in the file with Name '"+startValue+"'" , false);
						}
					}
				}

			}else{
				ExtentTestManager.reportStepFail(driver,"No files available in the given directory" , false);
				return false;
			}
		}catch(Exception e){
			functionStatus = false;
			ExtentTestManager.reportStepFail(driver,"Error occurred while match with expected text in the file with name '"+startValue+"'" , false);
		}
		return functionStatus;
	}

	public synchronized boolean ESHOPSelectStoreValueInEnvVar(String sqltablename, String strsqlcolumnname,String strsqlcondition,String envVariable,int strExecEventFlag){
		boolean SQLDBSelect= false;
		String query = null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String Actual_Value = null;
		String envVariableName=null;
		ResultSet Eshop_SQLServer=null;

		try {
			if(strExecEventFlag==1){
				Table_name=RetrieveTestDataValue(testDataFilePathStatic, testComponentNameStatic, sqltablename,gblrecordsCounterStatic);
				Column_name=RetrieveTestDataValue(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=RetrieveTestDataValue(testDataFilePathStatic, testComponentNameStatic, strsqlcondition,gblrecordsCounterStatic);
				envVariableName=RetrieveTestDataValue(testDataFilePathStatic, testComponentNameStatic, envVariable,gblrecordsCounterStatic);
			}
			if(Table_name==null || Column_name==null || SQL_condition==null||envVariableName==null){
				ExtentTestManager.reportStepFail("Required details are not provided in test data sheet.", false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"";
			Eshop_SQLServer = EShopstmt.executeQuery(query);
			Eshop_SQLServer.next();
			Actual_Value = Eshop_SQLServer.getString(1).trim();
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"No Record available in DB for the query "+query, false);
			return false;
		}
		try{
			if(!Eshop_SQLServer.wasNull()){
				Runtimevalue.setProperty(envVariableName, Actual_Value);
				ExtentTestManager.reportStepPass("Dynamic value '"+Actual_Value+"' in column : '"+Column_name+"' of table : '"+Table_name+"' with condition : '"+SQL_condition+" is stored in the Runtime Variable '"+envVariableName+"'");
				SQLDBSelect=true;
			}
		}catch (Exception e){
			ExtentTestManager.reportStepFail("Error occured while stroing the values env variable from in SQL query. Error description is : "+ e.getMessage() +".", false);
			SQLDBSelect=false;
		}
		return SQLDBSelect;
	}

	public synchronized boolean WebElementValueCompareFromGAF(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag){
		String actualResult=null;
		String strData=null;
		boolean WebElementValueCompareFromGAF=false;
		try{
			if (strExecEventFlag==1){
				strData=RetrieveTestDataValue(testDataFilePathStatic, testComponentNameStatic, strColumnName,gblrecordsCounterStatic);
				strData=GAFValue.getProperty(strData);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail("Required details are not provided in the data sheet.", false);
				return false;
			}
			actualResult = selectByLocatorType(getValueFromPOM).getAttribute("value");

			if((actualResult.trim()).equalsIgnoreCase(strData.trim())){
				ExtentTestManager.reportStepPass("Actual Value '" +actualResult+ "' matches with the Expected value '"+strData+ "' in the input field '"+strTestObject+"'");
				WebElementValueCompareFromGAF=true;
			}else{
				ExtentTestManager.reportStepFail("Actual Value '" +actualResult+ "' does not match with the Expected value '"+strData+ "' in the input field '"+strTestObject+"'", true);
				WebElementValueCompareFromGAF=false;
			}
		} catch (Exception e){
			ExtentTestManager.reportStepFail("Error occured while comparing actual and expected values using GAF. Error description is :"+e.getMessage(), true);
			WebElementValueCompareFromGAF=false;
		}
		return WebElementValueCompareFromGAF;
	}

	public synchronized boolean DynamicElementClick(String getValueFromPOM, String strTestObject){
		boolean DynamicElementClick= false;
		try {
			selectByLocatorType(getValueFromPOM).click();
			ExtentTestManager.reportStepPass("'"+strTestObject+"' is clicked successfully ");	
			DynamicElementClick=true;
		}catch(StaleElementReferenceException e){
			return DynamicElementClick(getValueFromPOM, strTestObject);
		}catch (Exception e) {
			ExtentTestManager.reportStepFail("Exception occured while finding the element. Error description is : "+ e.getMessage() +"." , true); 
			DynamicElementClick=false;
		}
		return DynamicElementClick;
	}
	
	/*----------------------------------------- PRAVEEN ***END***-----------------------------------------*/


	/*----------------------------------------- MURALI CODE -----------------------------------------*/
	
	public synchronized boolean WebElementNotPresent(String getValueFromPOM, String strTestObject) {
		boolean elementStatus = false;
		try{
			String elementFromPOM=null;
			elementFromPOM=getValueFromPOM;
			int count=elementFromPOM.split("#").length;
			//log.info("JS POM Count : "+count);
			if(count==2){
				getValueFromPOM=elementFromPOM.split("#")[1];
				//log.info("changed getValueFromPOM is : "+getValueFromPOM);
			}else{
				//log.info("Not changed getValueFromPOM is : "+getValueFromPOM);
			}
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Object webElementObj = js.executeScript("return document.evaluate(\""+getValueFromPOM+"\",document.body,null,XPathResult.UNORDERED_NODE_ITERATOR_TYPE,null).iterateNext()!=null;");
			if(webElementObj.equals(false)){
				ExtentTestManager.reportStepPass("WebELement  '"+strTestObject+"' is not available on page");
				elementStatus = true;
			}else{				
				ExtentTestManager.reportStepFail(driver,"WebElement  '"+strTestObject+"' is available", false);
				elementStatus = true;
			}
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"WebElement is available on "+strTestObject+" page" , true);
			elementStatus = false;
		}
		return elementStatus;
	}

	public synchronized boolean WebListSelectByValue(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag){
		String strData=null;
		boolean WebListSelect=false;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}
			new Select(selectByLocatorType(getValueFromPOM)).selectByValue(strData);
			ExtentTestManager.reportStepPass("Item '" +  strData + "' is selected from the  '"+strTestObject+"' List box successfully" );
			WebListSelect=true;
		} catch (StaleElementReferenceException e) {
				return WebListSelectByValue(getValueFromPOM, strTestObject, strColumnName, strExecEventFlag);
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Item '" +  strData + " was not selected from the  '"+strTestObject+"' List box " , true); 
			WebListSelect=false;
		}
		return WebListSelect;
	}
	
	public synchronized boolean ScrollIntoElement(String getValueFromPOM,  String strTestObject) {
		boolean ScrollIntoElement=false;
		int timeout=30;
		try{
			for(int i=0;i<timeout;i++){
				WebElement elementenable=selectByLocatorType(getValueFromPOM);
				((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", elementenable);
				if(elementenable.isDisplayed()){
					ExtentTestManager.reportStepPass("Item focus moved to Element '"+strTestObject+"' successfully with in '"+i+"' seconds" );
					ScrollIntoElement=true;
					break;
				}
				Thread.sleep(1000L);
			}
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Item focus not moved to Element '"+strTestObject+"' waited for '"+timeout+"' seconds" , true); 
			return ScrollIntoElement;
		}
		return ScrollIntoElement;
	}

	public synchronized boolean readLogFiles_2(String filePath, String fileNameValue, String textName, int strExecEventFlag) {
		boolean functionStatus = false;
		String path = null;
		String textValue = null;
		File[] listOfFile = null;
		String fileName = null;
		String actualText = null;
		Pattern pattern = null;
		String fileNameFromExcel=null;

		try{
			if(strExecEventFlag == 1){
				path = property.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic, filePath, gblrecordsCounterStatic));
				textValue = getTestData(testDataFilePathStatic, testComponentNameStatic, textName,gblrecordsCounterStatic);
				fileNameFromExcel= getTestData(testDataFilePathStatic, testComponentNameStatic, fileNameValue,gblrecordsCounterStatic);
			}

			if(path==null || fileNameFromExcel==null || textValue==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in test data sheet.", false);
				return false;
			}
			log.info("FIle name to search is : "+fileNameFromExcel);
			File directory = new File("//\\" +path);
			boolean fileexist=false;
			int fileAppeartime=0;

			while(fileAppeartime<30){
				Thread.sleep(1000);
				listOfFile = directory.listFiles();
				if(listOfFile.length != 0){
					log.info("Directory has files");
					fileexist=true;
					break;

				}else{
					log.info("No File is available in directory. looping again with 60 secs");
				}

				log.info("No File is available in directory with 60 secs");
				fileAppeartime++;
			}

			if(fileexist){
				log.info("Files avialble in the directiory");

			}else{
				log.info("No File is available in directory for 60secs");
				ExtentTestManager.reportStepFail(driver,"No Files available in the Given directory" , false);
				return false;
			}

			boolean foundstatus=false;
			int time=0;
			while(time<30){
				Thread.sleep(1000);	
				for(int i = 0; i<listOfFile.length; i++){
					log.info("i is : "+time);
					if(listOfFile[i].isFile()){

						fileName = listOfFile[i].getName();
						log.info("Found a file and name is : "+fileName);
						if(fileName.equals(fileNameFromExcel)){
							log.info("FileName exact match : "+fileName);
							fileName=fileNameFromExcel;
							foundstatus=true;
							break;
						}
					}
				}
				if(foundstatus){
					log.info("FileName exact match is found. Braking the loop");
					break;
				}else{
					log.info("FileName not found in path. Continue the loop");
				}
				time++;
			}

			if(fileName == null){
				ExtentTestManager.reportStepFail(driver,"'"+fileNameFromExcel+"' file is not available in the directory" , false);
				log.info("Search File is not available in directory");
				return false;
			}

			if(!(fileName.equals(fileNameFromExcel))){
				ExtentTestManager.reportStepFail(driver,"'"+fileNameFromExcel+"' file is not available in the directory" , false);
				log.info("Search File is not available in directory");
				return false;
			}

			File ff = new File("//\\" +path +fileName);
			String fileContent = IOUtils.toString(ff.toURI(),"UTF-8");
			actualText = "Text is not available in log file";
			pattern = Pattern.compile(textValue);
			Matcher matcher = pattern.matcher(fileContent);

			while(matcher.find()){
				actualText = matcher.group();
				//	actualText = matcher.group();
				log.info("Text is matched");
				ExtentTestManager.reportStepPass("Text '"+actualText+"' is matched with expected text in the filename of '"+fileNameFromExcel+"'");
				return true;
			}
			functionStatus = false;
			ExtentTestManager.reportStepFail(driver,""+textValue+" in the filename of '"+fileNameFromExcel+"'" , false);
		}catch(Exception e){
			log.info("Exception occurs in read log file"+e.getMessage());
			functionStatus = false;
			ExtentTestManager.reportStepFail(driver,"Error occurred, while match with expected text in the filename of '"+fileNameFromExcel+"'" , false);
		}
		return functionStatus;
	}
	
	public synchronized boolean readLogFilesWithEnvironmentValue(String filePath, String startsWith, String endsWith, String fileNameFromExcel1,String textName1,String StrEnvironmentVariable,  String textName2,int strExecEventFlag) {

		boolean functionStatus = false;
		String path = null;
		String startValue = null;
		String endValue = null;
		String textValue1 = null;
		String textValue2 = null;
		File[] listOfFile = null;
		String fileName = null;
		String actualText = null;
		Pattern pattern = null;
		String fileNameFromExcel=null;
		String EnvironmentVariable=null;
		try{
			if(strExecEventFlag == 1){
				path = property.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic, filePath, gblrecordsCounterStatic));
				startValue = getTestData(testDataFilePathStatic, testComponentNameStatic, startsWith,gblrecordsCounterStatic);
				endValue = getTestData(testDataFilePathStatic, testComponentNameStatic, endsWith,gblrecordsCounterStatic);
				textValue1 = getTestData(testDataFilePathStatic, testComponentNameStatic, textName1,gblrecordsCounterStatic);
				EnvironmentVariable=Runtimevalue.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic, StrEnvironmentVariable, strExecEventFlag));
				textValue2 = getTestData(testDataFilePathStatic, testComponentNameStatic, textName2,gblrecordsCounterStatic);
				fileNameFromExcel = getTestData(testDataFilePathStatic, testComponentNameStatic, fileNameFromExcel1,gblrecordsCounterStatic);
			}

			if(path==null || startValue==null || endValue==null || textValue1==null||textValue2==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}
			File directory = new File("//\\" +path);
			boolean fileexist=false;
			int fileAppeartime=0;
			while(fileAppeartime<30){
				Thread.sleep(1000);
				listOfFile = directory.listFiles();
				if(listOfFile.length != 0){
					fileexist=true;
					break;

				}else{
					log.info("No File is available in directory. looping again with 60 secs");
				}

				log.info("No File is available in directory with 60 secs");
				fileAppeartime++;
			}

			if(fileexist){
				log.info("Files avialble in the directiory");
			}else{
				log.info("No File is available in directory for 60secs");
				ExtentTestManager.reportStepFail(driver,"No Files available in the Given directory" , false);
				return false;
			}
			
			boolean foundstatus=false;
			int time=0;
			while(time<30){
				Thread.sleep(1000);	
				for(int i = 0; i<listOfFile.length; i++){
					log.info("i is : "+time);
					if(listOfFile[i].isFile()){
						fileName = listOfFile[i].getName();
						log.info("Found a file and name is : "+fileName);
						if(fileName.equals(fileNameFromExcel)){
							log.info("FileName exact match : "+fileName);
							fileName=fileNameFromExcel;
							foundstatus=true;
							break;
						}
					}
				}
				if(foundstatus){
					log.info("FileName exact match is found. Braking the loop");
					break;
				}else{
					log.info("FileName not found in path. Continue the loop");
				}
				time++;
			}

			if(fileName == null){
				ExtentTestManager.reportStepFail(driver,"'"+fileNameFromExcel+"' file is not available in the directory" , false);
				log.info("Search File is not available in directory");
				return false;
			}

			if(!(fileName.equals(fileNameFromExcel))){
				ExtentTestManager.reportStepFail(driver,"'"+fileNameFromExcel+"' file is not available in the directory" , false);
				log.info("Search File is not available in directory");
				return false;
			}

			File ff = new File("//\\" +path +fileName);
			log.info(ff.getAbsolutePath()+" || "+ff.getName());
			String fileContent = IOUtils.toString(ff.toURI(),"UTF-8");
			String FramedPattern=textValue1+EnvironmentVariable+textValue2;
			pattern = Pattern.compile(FramedPattern);
			Matcher matcher = pattern.matcher(fileContent);
			int counter = 0;
			int counterLimit=30;
			while(counter<=counterLimit){
				if(matcher.find()){
				actualText = matcher.group();
				log.info("Text is matched:"+actualText+"in seconds:"+counter);
				ExtentTestManager.reportStepPass("Text '"+actualText+"' is matched with expected text in the filename of '"+fileNameFromExcel+"'");
				return true;
				}
				counter++;
			}
			functionStatus = false;
			ExtentTestManager.reportStepFail(driver,"Text'"+FramedPattern+" is not available in the filename of '"+fileNameFromExcel+"'" , false);
		}catch(Exception e){
			functionStatus = false;
			ExtentTestManager.reportStepFail(driver,"Error occurred, while match with expected text in the filename of '"+startValue+"'" , false);
		}
		return functionStatus;
	}

	public synchronized boolean WebListSelectByValueUntilExpectedValue(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag){
		int executionFlag=0;
		String strData=null;
		boolean WebListSelect=false;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the data sheet.", false);
				return false;
			}

			new Select(selectByLocatorType(getValueFromPOM)).selectByValue(strData);
			Thread.sleep(1000);
			String SelectedItem= new Select(selectByLocatorType(getValueFromPOM)).getFirstSelectedOption().getText();

			int counter=0;
			int counterLimit=10;
			if(!SelectedItem.trim().equals(strData.trim())){

				while(counter<counterLimit){
					new Select(selectByLocatorType(getValueFromPOM)).selectByValue(strData);
					Thread.sleep(1000);
					if(SelectedItem.trim().equals(strData.trim())){
						ExtentTestManager.reportStepPass("Item '"+strData+"' was  selected from the '"+strTestObject+"' List box ");
						return true;				
					}
					counter=counter+1;
					if(counter>10){
						executionFlag=0;
						break;
					}
				}

			}else{
				Thread.sleep(1000);
				if(!SelectedItem.trim().equals(strData.trim())){
					new Select(selectByLocatorType(getValueFromPOM)).selectByValue(strData);
				}
				executionFlag=1;
				WebListSelect=true;
				ExtentTestManager.reportStepPass("Item '"+strData+"' was  selected from the '"+strTestObject+"' List box ");
				return true;
			}
			if (executionFlag==0){
				ExtentTestManager.reportStepFail(driver,"Item '" +  strData + " was not selected from the  '"+strTestObject+"' List box " , true); 
				return false;
			}

		} catch (StaleElementReferenceException e) {
				return WebListSelectByValueUntilExpectedValue(getValueFromPOM, strTestObject, strColumnName, strExecEventFlag);
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Exception occured while performing WebListSelectByValueUntilExpectedValue. Exception is "+e, true); 
			WebListSelect=false;
		}
		return WebListSelect;
	}

	public synchronized boolean ESHOPSQLDBSelectConditionEnvvar1(String sqltablename, String strsqlcolumnname,String strsqlcondition,String strenvironmentvariable,String strExpectedvalue,int strExecEventFlag){
		boolean ESHOPSQLDBSelectConditionEnvvar= false;
		String query = null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String SQL_condition_value = null;		
		String Expected_value = "";
		String Actual_Value = null;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				Expected_value=getTestData(testDataFilePathStatic, testComponentNameStatic,strExpectedvalue,gblrecordsCounterStatic);
				SQL_condition_value = Runtimevalue.getProperty(getTestData(testDataFilePathStatic, testComponentNameStatic,strenvironmentvariable,strExecEventFlag));
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"='"+ SQL_condition_value +"'";
			ResultSet Eshop_SQLServer = EShopstmt.executeQuery(query);
			//Newly added on  20-09-2016 to validate system IP against DB value
			if (Expected_value.equalsIgnoreCase("GET_HOST_IP")){
				InetAddress IP=InetAddress.getLocalHost();
				Expected_value=	IP.getHostAddress();
			}
			Eshop_SQLServer.next();
			Actual_Value = Eshop_SQLServer.getString(1).trim();

			if(!Eshop_SQLServer.wasNull()){            // If some value is present in the fired Query
				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					ESHOPSQLDBSelectConditionEnvvar=true;
				}else if(!(Actual_Value.equals(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);
					ESHOPSQLDBSelectConditionEnvvar=false;
				}
			}

			else if(Eshop_SQLServer.wasNull()){        // If "NULL" value is present in the fired Query
				if(Expected_value.equals("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					ESHOPSQLDBSelectConditionEnvvar=true;
				}else if(!(Expected_value.equals("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);  	 
					ESHOPSQLDBSelectConditionEnvvar=false;
				}
			}
		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while performing ESHOPSQLDBSelectConditionEnvvar1. Error description is : "+ e.getMessage() +".", true);
			ESHOPSQLDBSelectConditionEnvvar=false;
		}
		return ESHOPSQLDBSelectConditionEnvvar;
	}
	
	public synchronized boolean WebListSelectFromGAFEnv(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag){
		String strData=null;
		boolean WebListSelectFromEnv=false;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
				strData=GAFValue.getProperty(strData);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the data sheet." , true);
				return false;
			}

			new Select(selectByLocatorType(getValueFromPOM)).selectByVisibleText(strData);
			ExtentTestManager.reportStepPass("Item '"+strData+"' is selected from the  '"+strTestObject+"' List box successfully" );
			WebListSelectFromEnv=true;

		} catch (StaleElementReferenceException e) {
				return WebListSelectFromGAFEnv(getValueFromPOM, strTestObject, strColumnName, strExecEventFlag);
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Item '" +  strData + "' was not selected from the  '"+strTestObject+"' List box- "+e.getMessage() , true); 
			WebListSelectFromEnv=false;
		}
		return WebListSelectFromEnv;
	}

	/*----------------------------------------- YOGENDRA CODE -----------------------------------------*/
	
	public synchronized boolean WeblistSQLDBitemsverify(String getValueFromPOM, String strTestObject, String sqltablename, String strsqlcolumnname,String strsqlcondition,int itemscount_not_consider,int strExecEventFlag){
		boolean WeblistSQLDBitemsverify= false;
		String query = null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String[] sqldbvalues = null;
		String[] weblistvalues = null;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the Data Sheet.", false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"";
			ResultSet rs_SQLServer = stmt.executeQuery(query);
			List<String> rowValues = new ArrayList<String>();
			while (rs_SQLServer.next()) {

				rowValues.add(rs_SQLServer.getString(1));
			}   
			// You can then put this back into an array if necessary
			sqldbvalues = (String[]) rowValues.toArray(new String[rowValues.size()]);
			Select se = new Select(selectByLocatorType(getValueFromPOM));
			List<WebElement> options = se.getOptions();

			//if you want to get all elements text into array list
			List<String> all_elements_text=new ArrayList<String>();

			for(int j=0; j<options.size(); j++){
				//loading text of each element in to array all_elements_text
				all_elements_text.add(options.get(j).getText());
			}

			weblistvalues = (String[]) all_elements_text.toArray(new String[all_elements_text.size()]);
			if(sqldbvalues.length == weblistvalues.length - itemscount_not_consider)
			{
				if(sqldbvalues.length==0){
					ExtentTestManager.reportStepPass("No Dropdown Value is Present in the Dropdown '"+strTestObject+"' and the corresponding Table");
					WeblistSQLDBitemsverify=true;
				}else{
					for (int i=0; i<sqldbvalues.length; i++){

						if (weblistvalues[i+1].equals(sqldbvalues[i])){
							ExtentTestManager.reportStepPass("Actual List value '"+ weblistvalues[i+1] +"' in the dropdown : "+ strTestObject +"' matches the expected value : '"+ sqldbvalues[i] +"'.");
							WeblistSQLDBitemsverify=true;
						} else{
							ExtentTestManager.reportStepFail(driver, "Actual List value '"+ weblistvalues[i+1] +"' in the dropdown : "+ strTestObject +"' doesn't matches the expected value : '"+ sqldbvalues[i] +"'.", true);
							WeblistSQLDBitemsverify=false;
						}
					}
				}
			} else {
				ExtentTestManager.reportStepFail(driver, "No. of items present in the dropdown : "+ strTestObject +"' doesn't matches with the number of items in the table : '"+ Table_name +"' with condition : '"+ SQL_condition +"'.", true);
				WeblistSQLDBitemsverify=false;
			}

		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Exception occured while performing WeblistSQLDBitemsverify. Error description is : "+ e.getMessage() +".", true);
			WeblistSQLDBitemsverify=false;
		}
		return WeblistSQLDBitemsverify;
	}

	public synchronized boolean WebElementMultilineTextCompare(String getValueFromPOM, String strTestObject,String strColumnName, int strExecEventFlag ){
		String actualResult=null;
		String strData=null;
		boolean WebElementMultilineTextCompare=false;

		try{
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			actualResult = selectByLocatorType(getValueFromPOM).getText();
			actualResult=actualResult.replaceAll("[\n\r]", "");

			if((actualResult.trim()).equalsIgnoreCase(strData.trim())){

				ExtentTestManager.reportStepPass("Actual Text '" +actualResult+ "' matches with the Expected value '"+strData+ "' in the input field '"+strTestObject+"'");
				WebElementMultilineTextCompare=true;
			}else{
				ExtentTestManager.reportStepFail(driver, "Actual Text '" +actualResult+ "' does not match with the Expected value '"+strData+ "' in the input field '"+strTestObject+"'", true);
				WebElementMultilineTextCompare=false;
			}
		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Exception occured while performing WebElementMultilineTextCompare. Error description is :"+e.getMessage(), true);
			WebElementMultilineTextCompare=false;
		}
		return WebElementMultilineTextCompare;
	}

	public synchronized boolean Webelementjavascriptclick(String getValueFromPOM, String strTestObject) {
		boolean Webelementjavascriptclick= false;
		try {
			((JavascriptExecutor)driver).executeScript("arguments[0].click()",selectByLocatorType(getValueFromPOM));
			ExtentTestManager.reportStepPass("Element '"+strTestObject+"' is clicked successfully");
			Webelementjavascriptclick=true;
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Element '"+strTestObject+"' is not clicked successfully", true); 
			Webelementjavascriptclick=false;
		}
		return Webelementjavascriptclick;
	}
	
	public synchronized boolean SQLDBSelectDelete(String sqltablename, String strsqlcolumnname,String strsqlcondition,String sqltablenamedelete,String strsqlcolumnnamedelete,int strExecEventFlag){
		boolean SQLDBSelectDelete= false;
		String query = null;
		String query2 = null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String Actual_Value = null;
		String Table_name_delete = null;
		String Column_name_delete = null;
		
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				Table_name_delete=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablenamedelete,gblrecordsCounterStatic);
				Column_name_delete=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnnamedelete,gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null || Table_name_delete==null || Column_name_delete==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			//Query to Execute      
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"";
			
			ResultSet rs_SQLServer = stmt.executeQuery(query);
			rs_SQLServer.next();
			Actual_Value = rs_SQLServer.getString(1).trim();

		} catch(NullPointerException e){
			ExtentTestManager.reportStepPass(  "No Record is present in the table '" +  Table_name + "' based on the condition  '"+SQL_condition+"'.");
			return true;
		} catch (Exception e) {
			ExtentTestManager.reportStepPass(  "No Record is present in the table '" +  Table_name + "' based on the condition  '"+SQL_condition+"'.");
			return true;
		}

		try{
			if(Actual_Value.length() != 0){
			query2 = "Delete from "+Table_name_delete+" where "+ Column_name_delete +" = "+ Actual_Value ;
			stmt.execute(query2);
			}
			ExtentTestManager.reportStepPass(  "The value '" +  Actual_Value + "' is successfully deleted from the table  '"+Table_name_delete+"'.");
			SQLDBSelectDelete=true;
			
		} catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver, "Exception occured while executing the query "+query2, false);
			log.info("SQLDBSelect Error : ");
			SQLDBSelectDelete=false;
		}
		return SQLDBSelectDelete;
	}

	public synchronized boolean WebEditEnterCalenderText(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag){
		boolean WebEditEnterCalenderText= false;
		String strData=null;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('onkeydown',arguments[1]);",selectByLocatorType(getValueFromPOM),"return true;");
			selectByLocatorType(getValueFromPOM).sendKeys(strData);
			ExtentTestManager.reportStepPass(  "Text '" +  strData + "' is entered in the Textbox -  '"+strTestObject+"'  successfully");
			WebEditEnterCalenderText=true;	
		} catch (Exception e) { 	
			ExtentTestManager.reportStepFail(driver,"Text '" + strData + "' was not entered in the Textbox - '"+strTestObject+"'", true);
			WebEditEnterCalenderText=false;
		}
		return WebEditEnterCalenderText;
	}
	
	public synchronized boolean Browsefiletoupload(String getValueFromPOM, String strTestObject,String strPropertyName,String strFilename,int strExecEventFlag){
		boolean Browsefiletoupload= false;
		String strData=null;
		String filename=null;
		String filepath = null;
		try {
			if(!(strPropertyName==null)){
				strData = System.getProperty("user.dir")+property.getProperty(strPropertyName);
			}
			
			if(strExecEventFlag == 1){
				filename = getTestData(testDataFilePathStatic, testComponentNameStatic, strFilename,gblrecordsCounterStatic);
			}

			if(strData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}
			
			filepath = strData + filename;
			selectByLocatorType(getValueFromPOM).sendKeys(filepath);

			ExtentTestManager.reportStepPass("Text '" +  filepath + "' is entered in the Textbox -  '"+strTestObject+"'  successfully");
			Browsefiletoupload=true;	
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver, "Text '" + filepath + "' was not entered in the Textbox - '"+strTestObject+"'", true);
			Browsefiletoupload=false;
		}
		return Browsefiletoupload;
	}
	
	public synchronized boolean RetrieveSQLValueStoresInEnvVar(String sqltablename, String strsqlcolumnname,String sqlcondition,String strEnvironmentVariable,int strExecEventFlag){
		boolean RetrieveSQLValueStoresInEnvVar= false;
		String query = null;
		String Table_name = null;
		String Column_Name = null;
		String SQL_condition = null;
		String Actual_Value = null;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_Name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,sqlcondition,gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_Name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}

			//Query to Execute
			query = "select "+Column_Name+" from "+Table_name+" where "+SQL_condition+"";
			ResultSet rs_SQLServer= stmt.executeQuery(query);
			rs_SQLServer.next();
			Actual_Value = rs_SQLServer.getString(1);
			Runtimevalue.setProperty(strEnvironmentVariable, Actual_Value);

			if(!(Actual_Value==null)){
				ExtentTestManager.reportStepPass("Actual Value '" +Actual_Value+ "' is stored in the Runtime variable '"+ strEnvironmentVariable +"' successfully");
				RetrieveSQLValueStoresInEnvVar=true;
			}else{
				ExtentTestManager.reportStepFail(driver, "No Value is stored in Runtime Variable: '"+ strEnvironmentVariable +"'", false);
				RetrieveSQLValueStoresInEnvVar=false;
			}
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver, "Exception occured while checking whether the SQL query value '"+Actual_Value+"' is null (or) not", false);
			RetrieveSQLValueStoresInEnvVar=false;
		}
		return RetrieveSQLValueStoresInEnvVar;
	}

	public synchronized boolean SQLDBSelectConditionEnvvar(String sqltablename, String strsqlcolumnname,String strsqlcondition,String strenvironmentvariable,String strExpectedvalue,int strExecEventFlag){
		boolean SQLDBSelectConditionEnvvar= false;
		String query = null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String SQL_condition_value = null;		
		String Expected_value = "";
		String Actual_Value = null;
		ResultSet rs_SQLServer=null; 
		
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				Expected_value=getTestData(testDataFilePathStatic, testComponentNameStatic,strExpectedvalue,gblrecordsCounterStatic);
				SQL_condition_value = Runtimevalue.getProperty(strenvironmentvariable);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"='"+ SQL_condition_value +"'";
			rs_SQLServer = stmt.executeQuery(query);
			rs_SQLServer.next();
			Actual_Value = rs_SQLServer.getString(1).trim();
			
		}catch (Exception e) {           // If no record is present in the fired Query
			ExtentTestManager.reportStepFail(driver, "NO RECORD found for the query "+query, false);
			return false;
		}

		try{
			if(!rs_SQLServer.wasNull()){            // If some value is present in the fired Query
				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					SQLDBSelectConditionEnvvar=true;
				}else if(!(Actual_Value.equals(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);
					SQLDBSelectConditionEnvvar=false;
				}
			}
			
			else if(rs_SQLServer.wasNull()){        // If "NULL" value is present in the fired Query
				if(Expected_value.equals("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					SQLDBSelectConditionEnvvar=true;
				}else if(!(Expected_value.equals("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);  	 
					SQLDBSelectConditionEnvvar=false;
				}
			}
		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Exception occured while comparing the values using SQLDBSelectConditionEnvvar. Error description is : "+ e.getMessage() +".", true);
			SQLDBSelectConditionEnvvar=false;
		}
		return SQLDBSelectConditionEnvvar;
	}
	
	public synchronized boolean Uploadfiletestdata(String strPropertyName, String strFilename, int strExecEventFlag) {

		boolean elementStatus = false;
		String strData=null; 
		String filename=null;
		String filepath = null;
		String getBrowserName = browserProperty.getProperty("testBrowser");
		
		try {
			if(!(strPropertyName==null)){
				strData = System.getProperty("user.dir")+property.getProperty(strPropertyName);
			}
			if(strExecEventFlag == 1){
				filename = getTestData(testDataFilePathStatic, testComponentNameStatic, strFilename,gblrecordsCounterStatic);
			}
			
			filepath = strData + filename;
			log.info("filepath is "+filepath);
			//Set the Path for DLL
			File file = new File("lib", "jacob-1.14.3-x86.dll");
			//Get the absolute path for DLL
			System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
			//Get the Jacob DLL path from local
			File jacobDLLPath = new File(property.getProperty("AutoIT_Jacob_DLL_Path"));
			String dLLAbsolutePath = jacobDLLPath.getAbsolutePath();
			//Get the Jacob DLL absolute path
			System.setProperty(LibraryLoader.JACOB_DLL_PATH, dLLAbsolutePath);
			LibraryLoader.loadJacobLibrary();
			File localPath = new File(filepath);
			//Get the upload file absolute path
			String absoluteFilepath = localPath.getAbsolutePath();
			AutoItX autoIT = new AutoItX();
			
			log.info("browser is "+getBrowserName);

			//Based on Browsers AutoIT tool will be executed
			if(getBrowserName.equalsIgnoreCase("IE")){
				autoIT.winActivate("Choose File to Upload");
				if(autoIT.winWaitActive("Choose File to Upload", "", 10)){
					if(autoIT.winExists("Choose File to Upload")){
						autoIT.send(absoluteFilepath);
						autoIT.send("{Enter}",false);	
						log.info("File has been uploaded successfully in IE browser");
						elementStatus = true;
					}
				}
				elementStatus = true;
			} 
			else if(getBrowserName.equalsIgnoreCase("Firefox")){
				autoIT.winActivate("File Upload");
				if(autoIT.winWaitActive("File Upload", "", 10)){
					if(autoIT.winExists("File Upload")){
						autoIT.sleep(500);
						autoIT.send(absoluteFilepath);	                
						autoIT.send("{Enter}",false);
						log.info("File has been uploaded successfully in Firefox browser");
						elementStatus = true;
					}
				}
				elementStatus = true;

			}else if(getBrowserName.equalsIgnoreCase("Chrome")){
				autoIT.winActivate("Open");
				if(autoIT.winWaitActive("Open", "", 10)){
					if(autoIT.winExists("Open")){
						autoIT.sleep(500);
						autoIT.send(absoluteFilepath);	                
						autoIT.send("{Enter}",false);
						log.info("File has been uploaded successfully in Chrome browser");
						elementStatus = true;
					}
				}
			}

		}catch(Exception e){
			e.printStackTrace();
			elementStatus = false;
		}
		
		if(elementStatus){
			ExtentTestManager.reportStepPass( "File '" +  filename + "' is uploaded successfully");
		} else{
			ExtentTestManager.reportStepFail(driver, "Error occured while uploading the file : '"+ filename +"'.", false);
		}

		return elementStatus;
	}

	public synchronized boolean jsh_Unix_Open_Connection(String strserver, String strusername, String strpassword) {
		boolean Executionstatus=false;
		jsch=new JSch();
		String ipaddress=property.getProperty(strserver);
		String username =property.getProperty(strusername);
		String password =property.getProperty(strpassword);

		try {
			JSHsession=jsch.getSession(username, ipaddress);
			JSHsession.setPassword(password);
			Properties JSHProperties=new Properties();
			JSHProperties.put("StrictHostKeyChecking", "no");
			//JSHsession.setConfig("PreferredAuthentications","publickey,keyboard-interactive,password");
			JSHsession.setConfig(JSHProperties);
			JSHsession.connect();

		
			if(JSHsession.isConnected()){
				ExtentTestManager.reportStepPass("UNIX Server with IP Address '"+ipaddress+"' has been connected successfully");
				Executionstatus=true;
			}else{
				ExtentTestManager.reportStepFail(driver, "Unable to connect to the UNIX Server with IP Address "+ipaddress+".", false);
				Executionstatus=true;
			}

		} catch (JSchException e) {
			ExtentTestManager.reportStepFail(driver,"Unable to connect to the UNIX Server with IP Address==> "+ipaddress+"."+e.getMessage(), false);
			return Executionstatus;
		}
		return Executionstatus;
	}

	public synchronized boolean Jsh_closeUnixSession() {
		boolean Executionstatus=false;
		try{
			JSHsession.disconnect();
			if(JSHsession.isConnected()){
				ExtentTestManager.reportStepFail(driver, "Unable to disconnect  UNIX Server with IP Address==> "+property.getProperty("Unix_ITG_Server_IP")+".", false);
				Executionstatus=false;
			}else{
				ExtentTestManager.reportStepPass("Unix Connection disconnected successfully");
				Executionstatus=true;
			}
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver, "Unable to disconnect  UNIX Server with IP Address "+property.getProperty("Unix_ITG_Server_IP")+".Exception Occured:"+e.getMessage(), false);
			return Executionstatus;
		}
		return Executionstatus;
	}

	public synchronized boolean Delete_File_Unix(String strfilelocation, String strfilename, int strExecEventFlag) {
		boolean Delete_File_Unix=false;
		String strData=null;
		String filename=null;
		String filepath=null;
		InputStream strpath=null;
		try {
			if(!(strfilelocation==null)){
				strData = property.getProperty(strfilelocation);
			}
			if(strExecEventFlag == 1){
				filename = getTestData(testDataFilePathStatic, testComponentNameStatic, strfilename,gblrecordsCounterStatic);
			}

			filepath = strData + filename;
			Channel channel=JSHsession.openChannel("sftp");
			channel.connect();
			ChannelSftp channelSftp = (ChannelSftp)channel ;

			try {
				strpath = channelSftp.get(filepath);
				if(strpath != null){
					channelSftp.rm(filepath);
					ExtentTestManager.reportStepPass("File '"+ filename +"' is successfully deleted from the location : '"+ strData +"'.");	
					Delete_File_Unix = true;
				}
			} catch(Exception e){
				ExtentTestManager.reportStepPass("File '"+ filename +"' is not present in the location : '"+ strData +"'.");	
				Delete_File_Unix = true;
			}

		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver, "Unable to delete the File '"+ filename +"' from the location : '"+ strData +"'. Error description is : "+ e.getMessage(), false);
			Delete_File_Unix = false;
		}
		return Delete_File_Unix;
	}

	public synchronized boolean Check_File_Unix(String strfilelocation, String strfilename, int strExecEventFlag) {
		boolean Check_File_Unix=false;
		String strData=null;
		String filename=null;
		String filepath=null;
		InputStream strpath=null;
		try {

			if(!(strfilelocation==null)){
				strData = property.getProperty(strfilelocation);
			}
			if(strExecEventFlag == 1){
				filename = getTestData(testDataFilePathStatic, testComponentNameStatic, strfilename,gblrecordsCounterStatic);
			}

			filepath = strData + filename;
			Channel channel=JSHsession.openChannel("sftp");
			channel.connect();
			ChannelSftp channelSftp = (ChannelSftp)channel ;
			try {
				strpath = channelSftp.get(filepath);
				if(strpath != null){
					ExtentTestManager.reportStepPass("File '"+ filename +"' is present in the location : '"+ strData +"'.");	
					Check_File_Unix = true;
				}
			} catch(Exception e){
				ExtentTestManager.reportStepFail(driver,  "File '"+ filename +"' is not present in the location : '"+ strData +"'.", false);	
				Check_File_Unix = false;
			}

		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,    "Unable to check the file : '"+ filename +"' in the location : '"+ strData +"'.Error description is : "+ e.getMessage(), false);
			Check_File_Unix = false;
		}
		return Check_File_Unix;
	}

	public synchronized boolean ReplaceStringStoreEnvvar(String strenvironmentvariable,String strstoreenvvariable,String strStringtoreplace,String strReplacementstring,int strExecEventFlag){
		boolean ReplaceStringStoreEnvvar= false;
		String SQL_condition_value = null;		
		String String_To_Replace = null;
		String Replacement_String = null;
		String Expected_value =null;
		try {
			if(strExecEventFlag==1){
				String_To_Replace=getTestData(testDataFilePathStatic, testComponentNameStatic,strStringtoreplace,gblrecordsCounterStatic);
				Replacement_String = getTestData(testDataFilePathStatic, testComponentNameStatic,strReplacementstring,gblrecordsCounterStatic);
				SQL_condition_value = Runtimevalue.getProperty(strenvironmentvariable);
			}
			if(SQL_condition_value==null){
				ExtentTestManager.reportStepFail(driver,    "The value in the environment variable : '"+ strenvironmentvariable +"' is empty.", false);
				return false;
			}
			Expected_value = SQL_condition_value.replaceAll(String_To_Replace, Replacement_String);
			Runtimevalue.setProperty(strstoreenvvariable, Expected_value);
			ExtentTestManager.reportStepPass("The value : '"+ Expected_value +"' is stored in the environment variable : '"+ strstoreenvvariable +"'.");
			ReplaceStringStoreEnvvar=true;
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,  "Error occured while storing the value in environment variable.Error description is : "+ e.getMessage() +".", true);
			ReplaceStringStoreEnvvar=false;
		}
		return ReplaceStringStoreEnvvar;
	}

	public synchronized boolean SQLDBDeleteConditionEnvvar(String sqltablename, String sqlcondition, String strenvironmentvariable, int strExecEventFlag){
		boolean elementStatus= false;
		String tablename = null;
		String condition = null;
		String SQL_condition_value = null;

		try{

			if(strExecEventFlag==1){
				tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				condition=getTestData(testDataFilePathStatic, testComponentNameStatic,sqlcondition,gblrecordsCounterStatic);
				SQL_condition_value = Runtimevalue.getProperty(strenvironmentvariable);
			}

			if(tablename==null || condition==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}

			String check = "select * from "+tablename +" where "+condition+"= '"+ SQL_condition_value +"'";
			String query = "Delete from "+ tablename +" where "+condition+"= '"+ SQL_condition_value +"'";
			ResultSet rs = null;
			rs = stmt.executeQuery(check);		

			int temp=0;	
			while(rs.next()){
				temp++;
			}
			if(temp >= 1){
				stmt.execute(query);
				elementStatus=true;
				ExtentTestManager.reportStepPass("SQL DB Query "+ query + " executed successfully.");

			}//If rows not available FALSE will be returned so no delete
			else if(temp < 1){
				ExtentTestManager.reportStepPass("No Records available in DB for the SQL Query  "+ query );
				elementStatus=true;
			}

		}catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver,    "Error occured while executing the SQL query with SQLDBDeleteConditionEnvvar. Error description is : "+ e.getMessage() +".", false);
		}
		return elementStatus;
	}

	public synchronized boolean ESHOPSQLDBDeleteConditionEnvvar(String sqltablename, String sqlcondition, String strenvironmentvariable, int strExecEventFlag){
		boolean elementStatus= false;
		String tablename = null;
		String condition = null;
		String SQL_condition_value = null;

		try{

			if(strExecEventFlag==1){
				tablename=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				condition=getTestData(testDataFilePathStatic, testComponentNameStatic,sqlcondition,gblrecordsCounterStatic);
				SQL_condition_value = Runtimevalue.getProperty(strenvironmentvariable);
			}

			if(tablename==null || condition==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}

			//String check = "IF EXISTS(SELECT * FROM "+tablename+" WHERE "+condition+") SELECT 'TRUE' as STATUS ELSE SELECT 'FALSE' as STATUS";

			String check = "select * from "+tablename +" where "+condition+"= '"+ SQL_condition_value +"'";

			String query = "Delete from "+ tablename +" where "+condition+"= '"+ SQL_condition_value +"'";

			ResultSet Eshop_SQLServer = null;

			Eshop_SQLServer = EShopstmt.executeQuery(check);		

			int temp=0;	
			while(Eshop_SQLServer.next()){
				temp++;
			}

			if(temp >= 1){

				EShopstmt.execute(query);
				elementStatus=true;

				ExtentTestManager.reportStepPass("SQL DB Query "+ query + " executed successfully.");

			}//If rows not available FALSE will be returned so no delete
			else if(temp < 1){
				ExtentTestManager.reportStepPass("No Records available in DB for the SQL Query "+ query);
				elementStatus=true;
			}

		}catch (Exception e) { 
			elementStatus=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while executing the SQL query using ESHOPSQLDBDeleteConditionEnvvar. Error description is : "+ e.getMessage() +".", false);
		}
		return elementStatus;
	}

	public synchronized boolean ESHOPSQLDBSelectConditionEnvvar(String sqltablename, String strsqlcolumnname,String strsqlcondition,String strenvironmentvariable,String strExpectedvalue,int strExecEventFlag){
		boolean ESHOPSQLDBSelectConditionEnvvar= false;
		String query = null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String SQL_condition_value = null;		
		String Expected_value = "";
		String Actual_Value = null;
		ResultSet Eshop_SQLServer=null;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				Expected_value=getTestData(testDataFilePathStatic, testComponentNameStatic,strExpectedvalue,gblrecordsCounterStatic);
				SQL_condition_value = Runtimevalue.getProperty(strenvironmentvariable);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"='"+ SQL_condition_value +"'";
			Eshop_SQLServer = EShopstmt.executeQuery(query);
			Eshop_SQLServer.next();
			Actual_Value = Eshop_SQLServer.getString(1).trim();
		} catch (Exception e) {           // If no record is present in the fired Query
			ExtentTestManager.reportStepFail(driver, "No Record avaialble in DB for the SQL Query "+query, false);
			ESHOPSQLDBSelectConditionEnvvar=false;
		}

		try{
			if(!Eshop_SQLServer.wasNull()){            // If some value is present in the fired Query
				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					ESHOPSQLDBSelectConditionEnvvar=true;
				}else if(!(Actual_Value.equals(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);
					ESHOPSQLDBSelectConditionEnvvar=false;
				}
			}

			else if(Eshop_SQLServer.wasNull()){        // If "NULL" value is present in the fired Query
				if(Expected_value.equals("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					ESHOPSQLDBSelectConditionEnvvar=true;
				}else if(!(Expected_value.equals("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);  	 
					ESHOPSQLDBSelectConditionEnvvar=false;
				}
			}
		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,    "Error occured while comparing the values in SQL query using ESHOPSQLDBSelectConditionEnvvar. Error description is : "+ e.getMessage() +".", true);
			ESHOPSQLDBSelectConditionEnvvar=false;
		}
		return ESHOPSQLDBSelectConditionEnvvar;
	}

	public synchronized boolean ESHOPSQLDBSelectCompConditionEnvvar(String sqltablename, String strsqlcolumnname,String strsqlcondition,String strenvironmentvariable,String strExpectedvalueenvvar,int strExecEventFlag){
		boolean ESHOPSQLDBSelectCompConditionEnvvar= false;
		String query = null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String SQL_condition_value = null;		
		String Expected_value = "";
		String Actual_Value = null;
		ResultSet Eshop_SQLServer=null;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				Expected_value=Runtimevalue.getProperty(strExpectedvalueenvvar);
				SQL_condition_value = Runtimevalue.getProperty(strenvironmentvariable);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"='"+ SQL_condition_value +"'";
			Eshop_SQLServer = EShopstmt.executeQuery(query);
			Eshop_SQLServer.next();
			Actual_Value = Eshop_SQLServer.getString(1).trim();

		}catch (Exception e) { 
			ExtentTestManager.reportStepFail(driver, "No Record avaialble in DB for the SQL Query "+query, false);
			ESHOPSQLDBSelectCompConditionEnvvar=false;
		}

		try{
			if(!Eshop_SQLServer.wasNull()){            // If some value is present in the fired Query
				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					ESHOPSQLDBSelectCompConditionEnvvar=true;
				}else if(!(Actual_Value.equals(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);
					ESHOPSQLDBSelectCompConditionEnvvar=false;
				}
			}

			else if(Eshop_SQLServer.wasNull()){        // If "NULL" value is present in the fired Query
				if(Expected_value.equals("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					ESHOPSQLDBSelectCompConditionEnvvar=true;
				}else if(!(Expected_value.equals("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);  	 
					ESHOPSQLDBSelectCompConditionEnvvar=false;
				}
			}
		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while comparing the values in SQL query using ESHOPSQLDBSelectCompConditionEnvvar. Error description is : "+ e.getMessage() +".", true);
			log.info("ESHOPSQLDBSelectCompConditionEnvvar Error : " + e);
			ESHOPSQLDBSelectCompConditionEnvvar=false;
		}
		return ESHOPSQLDBSelectCompConditionEnvvar;
	}

	public synchronized boolean webTableVerifyDetailshashtable(String getValueFromPOM, String strTestObject, String primaryCol, String secondaryCol, String expRowValue, String expTextFromTable, int strExecEventFlag) {
		boolean webTableVerifyDetailshashtable = false;
		int itemexist = 0;
		int testcasestatus = 0;
		String primaryColumnName = null;
		String secColumnName = null;
		String expectedRowValue = null;
		String expectedTextFromTable = null;
		String attributeID = null;
		String actualValueFromTable = null;
		String actualvalue = null;
		try{	
			if(strExecEventFlag == 1){
				primaryColumnName = getTestData(testDataFilePathStatic, testComponentNameStatic, primaryCol,gblrecordsCounterStatic);
				secColumnName = getTestData(testDataFilePathStatic, testComponentNameStatic, secondaryCol,gblrecordsCounterStatic);
				expectedRowValue = getTestData(testDataFilePathStatic, testComponentNameStatic, expRowValue,gblrecordsCounterStatic);
				expectedTextFromTable = getTestData(testDataFilePathStatic, testComponentNameStatic, expTextFromTable,gblrecordsCounterStatic);
			}

			if(primaryColumnName == null || secColumnName == null || expectedRowValue == null || expectedTextFromTable == null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
				return false;
			}

			attributeID = selectByLocatorType(getValueFromPOM).getAttribute("id");
			List<WebElement> headerColumns = driver.findElements(By.xpath("//*[@id='"+attributeID+"']/thead/tr[1]/th"));
			int findPrimaryColumn = 0;
			for(int headerCounterPrimaryColumn = 1;headerCounterPrimaryColumn <= headerColumns.size(); headerCounterPrimaryColumn++){
				String GetTitleHeaders = driver.findElement(By.xpath("//*[@id='"+attributeID+"']/thead/tr[1]/th["+ headerCounterPrimaryColumn +"]")).getText();
				if(GetTitleHeaders.equalsIgnoreCase(primaryColumnName)){
					findPrimaryColumn = headerCounterPrimaryColumn;
					break;
				}
			}
			if(findPrimaryColumn == 0){
				ExtentTestManager.reportStepFail(driver, "Primary key column '"+ primaryColumnName +"' in not available in the table "+ strTestObject +"'.", false);
				return false;
			}
			String[] headernames=secColumnName.split("\\|");
			String[] headervalues=expectedTextFromTable.split("\\|");
			Hashtable<String, String> hstable = new Hashtable<String, String>();
			for( int hdrcount = 0; hdrcount <= headernames.length - 1; hdrcount++)
			{
				hstable.put(headernames[hdrcount], headervalues[hdrcount]);
			}

			Hashtable<String, Integer> hstableheaders = new Hashtable<String, Integer>();
			for( int hdrcount = 0; hdrcount <= headernames.length - 1; hdrcount++)
			{
				int iheaderexist = 0;
				for( int headercolumn = 1; headercolumn <= headerColumns.size(); headercolumn++)
				{
					String headername = driver.findElement(By.xpath("//*[@id='"+attributeID+"']/thead/tr[1]/th["+ headercolumn +"]")).getText();
					if(headernames[hdrcount].equalsIgnoreCase(headername)){
						hstableheaders.put(headernames[hdrcount], headercolumn);
						iheaderexist = 1;
						break;
					}
				}
				if(iheaderexist == 0){
					hstableheaders.put(headernames[hdrcount], 0);
				}
			}
			List<WebElement> rows = driver.findElements(By.xpath("//*[@id='"+attributeID+"']/tbody/tr"));
			for(int row=1; row <= rows.size(); row++)
			{
				actualValueFromTable = driver.findElement(By.xpath("//table[@id='"+attributeID+"']/tbody/tr["+row+"]/td["+findPrimaryColumn+"]")).getText();

				if(actualValueFromTable.equals(expectedRowValue)){
					for( int hdrcount = 0; hdrcount <= headernames.length - 1; hdrcount++) {
						if(hstableheaders.get(headernames[hdrcount]) != 0){
							actualvalue = driver.findElement(By.xpath("//table[@id='"+attributeID+"']/tbody/tr["+row+"]/td["+ hstableheaders.get(headernames[hdrcount]) +"]")).getText();

							if(actualvalue.equals(hstable.get(headernames[hdrcount]))){
								ExtentTestManager.reportStepPass("Actual value '"+ actualvalue +"' in the table : "+ strTestObject +"' matches with the expected value : '"+ hstable.get(headernames[hdrcount]) +"'.");
							}else{
								ExtentTestManager.reportStepFail(driver,"Actual value '"+ actualvalue +"' in the table : "+ strTestObject +"' doesnot matches with the expected value : '"+ hstable.get(headernames[hdrcount]) +"'.", true);
								testcasestatus = 1;
							}
						}else{
							ExtentTestManager.reportStepFail(driver, "Required header '"+ headernames[hdrcount] +"' is not present in the table : '"+ strTestObject +"'.", true);
							testcasestatus = 1;
						}
					}
					itemexist = 1;
					break;
				}
			}

			if(itemexist == 0){
				ExtentTestManager.reportStepFail(driver, "The value '"+ expectedRowValue +"' is not present in the table : '"+ strTestObject +"'.", true);
				testcasestatus = 1;
			}
		}	catch(Exception e){
			ExtentTestManager.reportStepFail(driver, "Exception occured while performing webTableVerifyDetailshashtable. Error message is : "+ e +"'.", true);
			testcasestatus = 1;	 
		}
		if(testcasestatus == 0){
			webTableVerifyDetailshashtable = true;
		}
		return webTableVerifyDetailshashtable;
	}

	public synchronized boolean webTableVerifyMultiDetailshashtable(String getValueFromPOM, String strTestObject, String primaryCol_1, String primaryCol_2, String secondaryCol, String expRowValue_1, String expRowValue_2, String expTextFromTable, int strExecEventFlag) {

		boolean webTableVerifyMultiDetailshashtable = false;
		//Initialized the variables as dummy
		int itemexist = 0;
		int testcasestatus = 0;
		String primaryColumnName_1 = null;
		String primaryColumnName_2 = null;
		String secColumnName = null;
		String expectedRowValue_1 = null;
		String expectedRowValue_2 = null;
		String expectedTextFromTable = null;
		String attributeID = null;
		String actualValueFromTable_1 = null;
		String actualValueFromTable_2 = null;
		String actualvalue = null;
		try{	
			// Get the values from excel sheet to find the rows and columns from web table
			if(strExecEventFlag == 1){
				primaryColumnName_1 = getTestData(testDataFilePathStatic, testComponentNameStatic, primaryCol_1,gblrecordsCounterStatic);
				primaryColumnName_2 = getTestData(testDataFilePathStatic, testComponentNameStatic, primaryCol_2,gblrecordsCounterStatic);
				secColumnName = getTestData(testDataFilePathStatic, testComponentNameStatic, secondaryCol,gblrecordsCounterStatic);
				expectedRowValue_1 = getTestData(testDataFilePathStatic, testComponentNameStatic, expRowValue_1,gblrecordsCounterStatic);
				expectedRowValue_2 = getTestData(testDataFilePathStatic, testComponentNameStatic, expRowValue_2,gblrecordsCounterStatic);
				expectedTextFromTable = getTestData(testDataFilePathStatic, testComponentNameStatic, expTextFromTable,gblrecordsCounterStatic);

			}

			if(primaryColumnName_1 == null || primaryColumnName_2==null || secColumnName == null || expectedRowValue_1 == null || expectedRowValue_2 == null || expectedTextFromTable == null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
				return false;
			}


			//Get the ID from xpath(whole web table) 
			attributeID = selectByLocatorType(getValueFromPOM).getAttribute("id");


			List<WebElement> headerColumns = driver.findElements(By.xpath("//*[@id='"+attributeID+"']/thead/tr[1]/th"));

			int findPrimaryColumn_1 = 0;

			for(int headerCounterPrimaryColumn = 1;headerCounterPrimaryColumn <= headerColumns.size(); headerCounterPrimaryColumn++){
				String GetTitleHeaders = driver.findElement(By.xpath("//*[@id='"+attributeID+"']/thead/tr[1]/th["+ headerCounterPrimaryColumn +"]")).getText();
				if(GetTitleHeaders.equalsIgnoreCase(primaryColumnName_1)){
					findPrimaryColumn_1 = headerCounterPrimaryColumn;
					break;
				}
			}

			if(findPrimaryColumn_1 == 0){
				ExtentTestManager.reportStepFail(driver,    "The primary key column : '"+ primaryColumnName_1 +"' in not available in the table : "+ strTestObject +"'.", true);
				return false;
			}

			int findPrimaryColumn_2 = 0;

			for(int headerCounterPrimaryColumn = 1;headerCounterPrimaryColumn <= headerColumns.size(); headerCounterPrimaryColumn++){
				String GetTitleHeaders = driver.findElement(By.xpath("//*[@id='"+attributeID+"']/thead/tr[1]/th["+ headerCounterPrimaryColumn +"]")).getText();
				if(GetTitleHeaders.equalsIgnoreCase(primaryColumnName_2)){
					findPrimaryColumn_2 = headerCounterPrimaryColumn;
					break;
				}
			}

			if(findPrimaryColumn_2 == 0){
				ExtentTestManager.reportStepFail(driver, "Primary key column : '"+ primaryColumnName_2 +"' in not available in the table : "+ strTestObject +"'.", true);
				return false;
			}

			String[] headernames=secColumnName.split("\\|");
			String[] headervalues=expectedTextFromTable.split("\\|");

			Hashtable<String, String> hstable = new Hashtable<String, String>();

			for( int hdrcount = 0; hdrcount <= headernames.length - 1; hdrcount++)
			{
				hstable.put(headernames[hdrcount], headervalues[hdrcount]);
			}

			Hashtable<String, Integer> hstableheaders = new Hashtable<String, Integer>();
			for( int hdrcount = 0; hdrcount <= headernames.length - 1; hdrcount++)
			{
				int iheaderexist = 0;
				for( int headercolumn = 1; headercolumn <= headerColumns.size(); headercolumn++)
				{
					String headername = driver.findElement(By.xpath("//*[@id='"+attributeID+"']/thead/tr[1]/th["+ headercolumn +"]")).getText();
					if(headernames[hdrcount].equalsIgnoreCase(headername)){
						hstableheaders.put(headernames[hdrcount], headercolumn);
						iheaderexist = 1;
						break;
					}
				}

				if(iheaderexist == 0){
					hstableheaders.put(headernames[hdrcount], 0);
				}

			}

			List<WebElement> rows = driver.findElements(By.xpath("//*[@id='"+attributeID+"']/tbody/tr"));

			for(int row=1; row <= rows.size(); row++)
			{
				actualValueFromTable_1 = driver.findElement(By.xpath("//table[@id='"+attributeID+"']/tbody/tr["+row+"]/td["+findPrimaryColumn_1+"]")).getText();
				actualValueFromTable_2 = driver.findElement(By.xpath("//table[@id='"+attributeID+"']/tbody/tr["+row+"]/td["+findPrimaryColumn_2+"]")).getText();


				if(actualValueFromTable_1.equals(expectedRowValue_1) && actualValueFromTable_2.equals(expectedRowValue_2)){
					for( int hdrcount = 0; hdrcount <= headernames.length - 1; hdrcount++) {
						if(hstableheaders.get(headernames[hdrcount]) != 0){
							actualvalue = driver.findElement(By.xpath("//table[@id='"+attributeID+"']/tbody/tr["+row+"]/td["+ hstableheaders.get(headernames[hdrcount]) +"]")).getText();

							if(actualvalue.equals(hstable.get(headernames[hdrcount]))){
								ExtentTestManager.reportStepPass("Actual value '"+ actualvalue +"' in the table : "+ strTestObject +"' matches with the expected value : '"+ hstable.get(headernames[hdrcount]) +"'.");
							}else{
								ExtentTestManager.reportStepFail(driver, "Actual value : '"+ actualvalue +"' in the table : "+ strTestObject +"' doesnot matches with the expected value : '"+ hstable.get(headernames[hdrcount]) +"'.", true);
								testcasestatus = 1;
							}
						}else{
							ExtentTestManager.reportStepFail(driver, "Required header : '"+ headernames[hdrcount] +"' is not present in the table : '"+ strTestObject +"'.", true);
							testcasestatus = 1;
						}
					}
					itemexist = 1;
					break;
				}
			}

			if(itemexist == 0){
				ExtentTestManager.reportStepFail(driver,"The value '"+ expectedRowValue_1 +"' or '"+ expectedRowValue_2 +"' is not present in the table : '"+ strTestObject +"'.", true);
				testcasestatus = 1;
			}


		}	catch(Exception e){
			ExtentTestManager.reportStepFail(driver, "Exception occured while performing webTableVerifyMultiDetailshashtable. Error message is : "+ e +"'.", true);
			testcasestatus = 1;	 
		}
		if(testcasestatus == 0){
			webTableVerifyMultiDetailshashtable = true;
		}
		return webTableVerifyMultiDetailshashtable;
	}

	public synchronized boolean SQLDBSelectMultiConditionEnvvar(String sqltablename, String strsqlcolumnname,String strsqlcondition_1,String strsqlcondition_2,String strenvironmentvariable,String strExpectedvalue,int strExecEventFlag){
		boolean SQLDBSelectMultiConditionEnvvar= false;
		String query = null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition_1 = null;
		String SQL_condition_2 = null;
		String SQL_condition_value = null;		
		String Expected_value = "";
		String Actual_Value = null;
		ResultSet rs_SQLServer=null;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition_1=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition_1,gblrecordsCounterStatic);
				SQL_condition_2=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition_2,gblrecordsCounterStatic);
				Expected_value=getTestData(testDataFilePathStatic, testComponentNameStatic,strExpectedvalue,gblrecordsCounterStatic);
				SQL_condition_value = Runtimevalue.getProperty(strenvironmentvariable);
			}

			if(Table_name==null || Column_name==null || SQL_condition_1==null || SQL_condition_2==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition_1+"='"+ SQL_condition_value +"' and "+ SQL_condition_2;
			rs_SQLServer = stmt.executeQuery(query);
			rs_SQLServer.next();
			Actual_Value = rs_SQLServer.getString(1).trim();

		} catch (Exception e) {           // If no record is present in the fired Query
			ExtentTestManager.reportStepFail(driver, "No Record avaialble in DB for the SQL Query "+query, false);
			SQLDBSelectMultiConditionEnvvar=false;
		}

		try{
			if(!rs_SQLServer.wasNull()){            // If some value is present in the fired Query
				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					SQLDBSelectMultiConditionEnvvar=true;
				}else if(!(Actual_Value.equals(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);
					SQLDBSelectMultiConditionEnvvar=false;
				}
			}

			else if(rs_SQLServer.wasNull()){        // If "NULL" value is present in the fired Query
				if(Expected_value.equals("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the Query '"+query+"' matches the expected value : '"+Expected_value+"'");
					SQLDBSelectMultiConditionEnvvar=true;
				}else if(!(Expected_value.equals("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the Query '"+query+"' does not match the expected value : '"+Expected_value+"'", false);  	 
					SQLDBSelectMultiConditionEnvvar=false;
				}
			}
		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while comparing the values in SQL query using SQLDBSelectMultiConditionEnvvar. Error description is : "+ e.getMessage() +".", false);
			SQLDBSelectMultiConditionEnvvar=false;
		}
		return SQLDBSelectMultiConditionEnvvar;
	}

	public synchronized boolean webTableVerifyDetailshashtablelabel(String getValueFromPOM, String strTestObject, String primaryCol, String secondaryCol, String expRowValue, String expTextFromTable, int strExecEventFlag) {

		boolean webTableVerifyDetailshashtablelabel = false;
		//Initialized the variables as dummy
		int itemexist = 0;
		int testcasestatus = 0;
		String primaryColumnName = null;
		String secColumnName = null;
		String expectedRowValue = null;
		String expectedTextFromTable = null;
		String attributeID = null;
		String actualValueFromTable = null;
		String actualvalue = null;

		String Expected_value = null;
		String Current_Date=null;
		try{	

			if(strExecEventFlag == 1){
				primaryColumnName = getTestData(testDataFilePathStatic, testComponentNameStatic, primaryCol,gblrecordsCounterStatic);
				secColumnName = getTestData(testDataFilePathStatic, testComponentNameStatic, secondaryCol,gblrecordsCounterStatic);
				expectedRowValue = getTestData(testDataFilePathStatic, testComponentNameStatic, expRowValue,gblrecordsCounterStatic);
				expectedTextFromTable = getTestData(testDataFilePathStatic, testComponentNameStatic, expTextFromTable,gblrecordsCounterStatic);

			}
			if(primaryColumnName == null || secColumnName == null || expectedRowValue == null || expectedTextFromTable == null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			//Get the ID from xpath(whole web table) 
			attributeID = selectByLocatorType(getValueFromPOM).getAttribute("aria-labelledby");
			List<WebElement> headerColumns = driver.findElements(By.xpath("//table[@aria-labelledby='"+attributeID+"']/thead/tr[1]/th"));
			int findPrimaryColumn = 0;
			for(int headerCounterPrimaryColumn = 1;headerCounterPrimaryColumn <= headerColumns.size(); headerCounterPrimaryColumn++){
				String GetTitleHeaders = driver.findElement(By.xpath("//table[@aria-labelledby='"+attributeID+"']/thead/tr[1]/th["+ headerCounterPrimaryColumn +"]")).getText();
				if(GetTitleHeaders.trim().equalsIgnoreCase(primaryColumnName.trim())){
					findPrimaryColumn = headerCounterPrimaryColumn;
					break;
				}
			}

			if(findPrimaryColumn == 0){
				ExtentTestManager.reportStepFail(driver, "Primary key column : '"+ primaryColumnName +"' in not available in the table : "+ strTestObject +"'.", true);
				return false;
			}

			String[] headernames=secColumnName.split("\\|");
			String[] headervalues=expectedTextFromTable.split("\\|");

			Hashtable<String, String> hstable = new Hashtable<String, String>();

			for( int hdrcount = 0; hdrcount <= headernames.length - 1; hdrcount++)
			{
				hstable.put(headernames[hdrcount], headervalues[hdrcount]);
			}

			Hashtable<String, Integer> hstableheaders = new Hashtable<String, Integer>();
			for( int hdrcount = 0; hdrcount <= headernames.length - 1; hdrcount++)
			{
				int iheaderexist = 0;
				for( int headercolumn = 1; headercolumn <= headerColumns.size(); headercolumn++)
				{
					String headername = driver.findElement(By.xpath("//table[@aria-labelledby='"+attributeID+"']/thead/tr[1]/th["+ headercolumn +"]")).getText();
					if(headernames[hdrcount].trim().equalsIgnoreCase(headername.trim())){
						hstableheaders.put(headernames[hdrcount], headercolumn);
						iheaderexist = 1;
						break;
					}
				}

				if(iheaderexist == 0){
					hstableheaders.put(headernames[hdrcount], 0);
				}

			}
			List<WebElement> rows = driver.findElements(By.xpath("//table[@aria-labelledby='"+attributeID+"']/tbody/tr"));

			for(int row=1; row <= rows.size(); row++)
			{
				actualValueFromTable = driver.findElement(By.xpath("//table[@aria-labelledby='"+attributeID+"']/tbody/tr["+row+"]/td["+findPrimaryColumn+"]")).getText();

				if(actualValueFromTable.equals(expectedRowValue)){
					for( int hdrcount = 0; hdrcount <= headernames.length - 1; hdrcount++) {
						if(hstableheaders.get(headernames[hdrcount]) != 0){
							actualvalue = driver.findElement(By.xpath("//table[@aria-labelledby='"+attributeID+"']/tbody/tr["+row+"]/td["+ hstableheaders.get(headernames[hdrcount]) +"]")).getText();

							if(hstable.get(headernames[hdrcount]).contains("CURRENT_DATE")){

								DateFormat dateFormat = new SimpleDateFormat(hstable.get(headernames[hdrcount]).split("\\;")[1]);
								Date date = new Date();
								Current_Date = dateFormat.format(date);
								Expected_value = Current_Date.trim();
								actualvalue = actualvalue.split(" ")[0];
								if(actualvalue.equals(Expected_value)){
									ExtentTestManager.reportStepPass("Actual value : '"+ actualvalue +"' in the table : "+ strTestObject +"' matches with the expected value : '"+ Expected_value +"'.");
								}else{
									ExtentTestManager.reportStepFail(driver, "Actual value : '"+ actualvalue +"' in the table : "+ strTestObject +"' doesnot matches with the expected value : '"+ Expected_value +"'.", true);
									testcasestatus = 1;
								}

							} else if(hstable.get(headernames[hdrcount]).contains("ENVVARCOMPARE")){

								String strenvvar = hstable.get(headernames[hdrcount]).split("\\;")[1];
								Expected_value = Runtimevalue.getProperty(strenvvar);
								if(Expected_value != null){
									if(actualvalue.equals(Expected_value)){
										ExtentTestManager.reportStepPass( "Actual value : '"+ actualvalue +"' in the table : "+ strTestObject +"' matches with the expected value : '"+ Expected_value +"'.");
									}else{
										ExtentTestManager.reportStepFail(driver,"Actual value : '"+ actualvalue +"' in the table : "+ strTestObject +"' doesnot matches with the expected value : '"+ Expected_value +"'.", true);
										testcasestatus = 1;
									}
								} else {

									ExtentTestManager.reportStepFail(driver, "Value in the environment variable : '"+ strenvvar +"' is empty.", true);
									testcasestatus = 1;

								}
							}	else {
								if(actualvalue.equals(hstable.get(headernames[hdrcount]))){
									ExtentTestManager.reportStepPass( "Actual value '"+ actualvalue +"' in the table : "+ strTestObject +"' matches with the expected value : '"+ hstable.get(headernames[hdrcount]) +"'.");
								}else{
									ExtentTestManager.reportStepFail(driver, "Actual value '"+ actualvalue +"' in the table : "+ strTestObject +"' doesnot matches with the expected value : '"+ hstable.get(headernames[hdrcount]) +"'.", true);
									testcasestatus = 1;
								}
							}	
						}else{
							ExtentTestManager.reportStepFail(driver, "Required header '"+ headernames[hdrcount] +"' is not present in the table : '"+ strTestObject +"'.", true);
							testcasestatus = 1;
						}
					}
					itemexist = 1;
					break;
				}
			}

			if(itemexist == 0){
				ExtentTestManager.reportStepFail(driver, "The value '"+ expectedRowValue +"' is not present in the table : '"+ strTestObject +"'.", true);
				testcasestatus = 1;
			}


		}	catch(Exception e){
			ExtentTestManager.reportStepFail(driver, "Exception occured while performing webTableVerifyDetailshashtablelabel. Error message is : "+ e +"'.", true);
			testcasestatus = 1;	 
		}
		if(testcasestatus == 0){
			webTableVerifyDetailshashtablelabel = true;
		}
		return webTableVerifyDetailshashtablelabel;
	}

	public synchronized boolean Waitforfiletodownload(String strdownloadfilepath, String strColumnName, int strExecEventFlag ){
		long first;
		long second;
		String strData = null;
		int iflag =0;
		boolean Waitforfiletodownload=false;
		try{
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}

			if(strData == null){
				ExtentTestManager.reportStepFail(driver,"Required details are not provided in the data sheet.", false);
				return false;
			}
			while(true){
				File file = new File(System.getProperty("user.dir")+property.getProperty(strdownloadfilepath)+"\\");
				File files[] = file.listFiles();
				if(files.length > 0){

					first = files[0].length()/1024;
					Thread.sleep(1000);
					file = new File(System.getProperty("user.dir")+property.getProperty(strdownloadfilepath)+"\\");
					files = file.listFiles();
					second = files[0].length()/1024;
					if(first != second){
					}else{
						files[0].renameTo(new File(System.getProperty("user.dir")+property.getProperty(strdownloadfilepath)+"\\"+strData));
						iflag = 1;
						break;
					}
				}
			}

			if(iflag==1){
				ExtentTestManager.reportStepPass("File has been downloaded successfully in the path :'"+ System.getProperty("user.dir")+property.getProperty(strdownloadfilepath) +"' and the file has been renamed to :'"+ strData +"'.");
				Waitforfiletodownload=true;
			}
		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while downloading the file and rename that file. Error description is :"+e.getMessage(), true);
			Waitforfiletodownload=false;
		}
		return Waitforfiletodownload;
	}			

	public synchronized boolean WeblistCalenderYearsVerify(String getValueFromPOM, String strTestObject, String yearscounttoverify,int strExecEventFlag){

		boolean WeblistCalenderYearsVerify= false;
		String yearsconfigured = null;
		String[] yearvalues = null;
		String[] weblistvalues = null;
		Date date = null;
		String dateFormat = null;
		int stringToInteger = 0;
		String integerToString = null;

		try {
			if(strExecEventFlag==1){
				yearsconfigured=getTestData(testDataFilePathStatic, testComponentNameStatic,yearscounttoverify,gblrecordsCounterStatic);
			}

			if(yearsconfigured==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the Data Sheet.", false);
				return false;
			}
			int yearcount = Integer.parseInt(yearsconfigured);
			date = new Date();
			dateFormat = new SimpleDateFormat("yyyy").format(date);
			List<String> rowValues = new ArrayList<String>();
			for(int k = yearcount;k>=0;k--){
				stringToInteger = Integer.parseInt(dateFormat) - k;
				integerToString = Integer.toString(stringToInteger);
				rowValues.add(integerToString);
			}
			yearvalues = (String[]) rowValues.toArray(new String[rowValues.size()]);

		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,    "Error occured while executing the SQL query using WeblistCalenderYearsVerify. Error description is : "+ e.getMessage() +".", false);
			return false;
		}
		try{
			Select se = new Select(selectByLocatorType(getValueFromPOM));
			List<WebElement> options = se.getOptions();
			List<String> all_elements_text=new ArrayList<String>();
			for(int j=0; j<options.size(); j++){
				all_elements_text.add(options.get(j).getText());
			}
			weblistvalues = (String[]) all_elements_text.toArray(new String[all_elements_text.size()]);
			if(yearvalues.length == weblistvalues.length)
			{
				for (int i=0; i<yearvalues.length; i++){

					if (weblistvalues[i].equals(yearvalues[i])){
						ExtentTestManager.reportStepPass("Actual List value : '"+ weblistvalues[i] +"' in the dropdown : "+ strTestObject +"' matches the expected value : '"+ yearvalues[i] +"'.");
						WeblistCalenderYearsVerify=true;
					} else{
						ExtentTestManager.reportStepFail(driver, "Actual List value : '"+ weblistvalues[i] +"' in the dropdown : "+ strTestObject +"' doesn't matches the expected value : '"+ yearvalues[i] +"'.", true);
						WeblistCalenderYearsVerify=false;
					}
				}
			} else {
				ExtentTestManager.reportStepFail(driver, "No. of items present in the dropdown : "+ strTestObject +"' doesn't matches with the number of expected items : '"+ yearvalues.length +"'.", true);
				WeblistCalenderYearsVerify=false;
			}

		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver,    "Error occured while comparing the values in SQL query using WeblistCalenderYearsVerify. Error description is : "+ e.getMessage() +".", true);
			WeblistCalenderYearsVerify=false;
		}
		return WeblistCalenderYearsVerify;
	}

	public synchronized boolean SQLDBEnvironmentVariableCompare(String sqltablename, String strsqlcolumnname,String strsqlcondition,String Propertyfilename,String strEnvironmentVariable,int strExecEventFlag){
		boolean SQLDBEnvironmentVariableCompare= false;
		String query = null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String strExpectedvalue = null;
		String Actual_Value = null;
		ResultSet rs_SQLServer=null;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			if(Propertyfilename.equalsIgnoreCase("Runtime")){
				strExpectedvalue=Runtimevalue.getProperty(strEnvironmentVariable);
				strExpectedvalue=strExpectedvalue.trim();
			}

			if(Propertyfilename.equalsIgnoreCase("Param")){
				strExpectedvalue=property.getProperty(strEnvironmentVariable);
				strExpectedvalue=strExpectedvalue.trim();
			}

			if(strExpectedvalue==null){
				ExtentTestManager.reportStepFail(driver,    "The Value in environment variable: '"+ strEnvironmentVariable +"' is empty", false);
				SQLDBEnvironmentVariableCompare=false;
			}else{
				query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"";
				rs_SQLServer= stmt.executeQuery(query);
			}
		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver,    "Error occured while executing the SQL query.Error description is : "+ e.getMessage() +".", false);
			log.info("SQLDBEnvironmentVariableCompare Error : " + e);
			return false;
		}

		try{
			rs_SQLServer.next();
			Actual_Value = rs_SQLServer.getString(1);
		} catch (Exception NullPointerException) {           // If no record is present in the fired Query
			ExtentTestManager.reportStepFail(driver, "No Record avaialble in DB for the SQL Query "+query, false);
			return false;
		}

		try{
			if(!rs_SQLServer.wasNull()){            // If some value is present in the fired Query
				if(Actual_Value.equals(strExpectedvalue)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the Query '"+query+"' matches the expected value : '"+strExpectedvalue+"'");
					SQLDBEnvironmentVariableCompare=true;
				}else if(!(Actual_Value.equals(strExpectedvalue))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the Query '"+query+"' does not match the expected value : '"+strExpectedvalue+"'", false);
					SQLDBEnvironmentVariableCompare=false;
				}
			}else if(rs_SQLServer.wasNull()){        // If "NULL" value is present in the fired Query
				if(strExpectedvalue.equals("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the Query '"+query+"' matches the expected value : '"+strExpectedvalue+"'");
					SQLDBEnvironmentVariableCompare=true;
				}else if(!(strExpectedvalue.equals("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the Query '"+query+"' does not match the expected value : '"+strExpectedvalue+"'", false);  	 
					SQLDBEnvironmentVariableCompare=false;
				}
			}
		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,    "Error occured while comparing the values in SQL Query using SQLDBEnvironmentVariableCompare. Error description is : "+ e.getMessage() +".", false);
			SQLDBEnvironmentVariableCompare=false;
		}
		return SQLDBEnvironmentVariableCompare;
	}

	public synchronized boolean XMLTextUpdate_TestData(String Location,String AttributeXPath,String ValueToSet,int strExecEventFlag){
		boolean Executionstatus=false;
		try {
			if (Location==""){
				ExtentTestManager.reportStepFail(driver,    "Location Path for WebConfig is missing", true);
				return false;
			}
			if (AttributeXPath==""){
				ExtentTestManager.reportStepFail(driver,    "AttributeXPath Path for WebConfig is missing", true);
				return false;
			}
			if (ValueToSet==""){
				ExtentTestManager.reportStepFail(driver,    "ValueToSet in the node for WebConfig is missing", true);
				return false;
			}
			if(strExecEventFlag==1){
				Location=getTestData(testDataFilePathStatic, testComponentNameStatic,Location,gblrecordsCounterStatic);
				Location=property.getProperty(Location);
				AttributeXPath=getTestData(testDataFilePathStatic, testComponentNameStatic,AttributeXPath,gblrecordsCounterStatic);
				ValueToSet=getTestData(testDataFilePathStatic, testComponentNameStatic,ValueToSet,gblrecordsCounterStatic);
				if (ValueToSet==""){
					ExtentTestManager.reportStepFail(driver,    "Value present in Property File Seems to Empty.Please check the property file.", true);
					return false;
				}
			}

			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			File file=new File("//\\"+Location);

			Document document=builder.parse(file);
			document.getDocumentElement().normalize();
			NodeList nodeList=null;
			XPath xpath=XPathFactory.newInstance().newXPath();
			nodeList=(NodeList)xpath.compile(AttributeXPath).evaluate(document,XPathConstants.NODESET);
			nodeList.item(0).setTextContent(ValueToSet);

			TransformerFactory transFormerFactory = TransformerFactory.newInstance();
			Transformer transFormer = transFormerFactory.newTransformer();
			DOMSource source = new DOMSource(document);

			StreamResult result = new StreamResult(file);
			transFormer.transform(source, result);
			result.getOutputStream().close();
			Executionstatus=true;
			ExtentTestManager.reportStepPass("XML config File '"+Location+"' has been updated successfully for the tag '"+AttributeXPath+"' with the value set as '"+ValueToSet+"'");
			Thread.sleep(2000);
		} catch (ParserConfigurationException e) {
			ExtentTestManager.reportStepFail(driver,    "ValueToSet in the node for WebConfig is Not successfull due to reason: '"+e.getMessage()+"'", false);
			Executionstatus=false;
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,    "Exception occured during XMLTextUpdate_TestData. Reason: '"+e.getMessage()+"'", false);
			Executionstatus=false;
		}
		return Executionstatus;
	}

	public synchronized boolean XML_Find_and_Replace(String strLocation,String FromTag, String ToTag, int strExecEventFlag){
		boolean Executionstatus=false;
		String Location=null;	
		try {
		if (FromTag==""){
			ExtentTestManager.reportStepFail(driver,"ValueToSet in the node for WebConfig is missing", true);
			return false;
		}
		if (ToTag==""){
			ExtentTestManager.reportStepFail(driver, "ValueToSet in the node for WebConfig is missing", true);
			return false;
		}
		if(strExecEventFlag==1){
			Location=getTestData(testDataFilePathStatic, testComponentNameStatic,strLocation,gblrecordsCounterStatic);
            Location=property.getProperty(Location);
			FromTag=getTestData(testDataFilePathStatic, testComponentNameStatic,FromTag,gblrecordsCounterStatic);
			ToTag=getTestData(testDataFilePathStatic, testComponentNameStatic,ToTag,gblrecordsCounterStatic);
		}
			ArrayList<String> lines = new ArrayList<String>();
			String line = null;
			
		       File f1=null;
		        FileReader fr=null;
		        BufferedReader br=null;
		        FileWriter fw=null;
		        BufferedWriter out=null;
		        try {
		            f1 = new File(Location);
		            fr = new FileReader(f1);
		            br = new BufferedReader(fr);
		            while ((line = br.readLine()) != null) {
		                if (line.contains(FromTag))
		                    line = line.replace(FromTag, ToTag);
		                lines.add(line);
		                lines.add("\n");
		            }

		            fw = new FileWriter(f1);
		            out = new BufferedWriter(fw);
		            for (String s : lines)
		                out.write(s);
		            out.flush();

		        } catch (Exception ex) {
		            ex.printStackTrace();
					ExtentTestManager.reportStepFail(driver,    "Exception occured while replacing the from tag '"+FromTag+"' to tag '"+ToTag+"'. Reason: '"+ex.getMessage()+"'", false);
					Executionstatus=false;
		        } finally {
		            try{
		            fr.close();
		            br.close();
		            out.close();
		            }catch(IOException ioe){
		            ioe.printStackTrace();
		            }
		        }
				ExtentTestManager.reportStepPass("Tag '"+ToTag+"' has been updated successfully in the XML file '"+Location+"'");
				Executionstatus=true;
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,  "Exception occured while replacing the from tag '"+FromTag+"' to tag '"+ToTag+"'. Reason: '"+e.getMessage()+"'", false);
			Executionstatus=false;
		}
		return Executionstatus;
	}

	public synchronized boolean SQLDBPastDateUpdateCDR(String sqltablename, String strsqlcolumnname,String strsqlcondition, String seperator, int Monthdifference, String envvar, int strExecEventFlag){
		boolean SQLDBPastDateUpdateCDR= false;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			String query=null;
		    Calendar cal = Calendar.getInstance();
		    int month = cal.get(Calendar.MONTH) + 1 + Monthdifference;
		    int year = cal.get(Calendar.YEAR);
		    String expmonth = Integer.toString(month);
		    if (month / 10 == 0) {
		    	expmonth = "0" + expmonth;
	        }
		    String ExpectedDate = year + seperator +"01" + seperator + expmonth +" "+ "00:00:00.000";
		    String envvardate = "01" + "/" + expmonth + "/" + year;
		    Runtimevalue.setProperty(envvar, envvardate);
			query = "update "+Table_name+" set "+ Column_name + " = '"+ ExpectedDate +"' where "+SQL_condition;
			stmt.execute(query);
			SQLDBPastDateUpdateCDR=true;
			ExtentTestManager.reportStepPass("SQL DB Update Query : "+ query + " executed successfully.");

		} catch (Exception e) {
			SQLDBPastDateUpdateCDR=false;
			ExtentTestManager.reportStepFail(driver,    "Error occured while executing the SQL query using SQLDBPastDateUpdateCDR. Error description is : "+ e.getMessage() +".", true);
		}
		return SQLDBPastDateUpdateCDR;
	}

	public synchronized boolean SQLDBDateUpdateStoreinEnvVar(String sqltablename, String strsqlcolumnname,  String strsqlcondition, String DBType, String strCdrYearDay, String strCallDateDay,String seperator, int Monthdifference, String envvar, int strExecEventFlag){
		boolean SQLDBDateUpdateStoreinEnvVar= false;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String expectedType = null;
		String Cdr_Year_Day = null;
		String Call_date_Day = null;
		String ExpectedDate = null;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				expectedType  = getTestData(testDataFilePathStatic, testComponentNameStatic, DBType,gblrecordsCounterStatic);
				Cdr_Year_Day  = getTestData(testDataFilePathStatic, testComponentNameStatic, strCdrYearDay,gblrecordsCounterStatic);
				Call_date_Day = getTestData(testDataFilePathStatic, testComponentNameStatic, strCallDateDay,gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null || expectedType==null || Cdr_Year_Day==null || Call_date_Day==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			String query=null;
		    Calendar cal = Calendar.getInstance();
		    int month = cal.get(Calendar.MONTH) + 1 + Monthdifference;
		    int year = cal.get(Calendar.YEAR);
		    String expmonth = Integer.toString(month);
		    if (month / 10 == 0) {
		    	expmonth = "0" + expmonth;
	        }
		    
		    if(expectedType.equalsIgnoreCase("REPORTDB")){
		    ExpectedDate = year + seperator +"01" + seperator + expmonth +" "+ "00:00:00.000";
		    }
		    if(expectedType.equalsIgnoreCase("MONTHDB")){
		    ExpectedDate = year + seperator + expmonth + seperator + "01" +"000000";
		    }
		    Runtimevalue.setProperty(envvar, ExpectedDate);
			query = "update "+Table_name+" set "+ Column_name + " = '"+ ExpectedDate +"' where "+SQL_condition;
			stmt.execute(query);
			SQLDBDateUpdateStoreinEnvVar=true;
			ExtentTestManager.reportStepPass("SQL DB Update Query : "+ query + " executed successfully.");

		} catch (Exception e) {
			SQLDBDateUpdateStoreinEnvVar=false;
			ExtentTestManager.reportStepFail(driver, "Error occured while executing the SQL query using SQLDBDateUpdateStoreinEnvVar. Error description is : "+ e.getMessage() +".", false);
		}
		return SQLDBDateUpdateStoreinEnvVar;
	}
	
	public synchronized boolean javaScriptDatePickerEnv(String getValueFromPOM, String strTestObject, String envvar) {
		boolean elementStatus = false;
		String elementValue = null;
		try{
			String elementFromPOM=null;
			elementFromPOM=getValueFromPOM;
			int count=elementFromPOM.split("#").length;
			//log.info("JS POM Count : "+count);
			if(count==2){
				getValueFromPOM=elementFromPOM.split("#")[1];
				//log.info("changed getValueFromPOM is : "+getValueFromPOM);
			}else{
				//log.info("Not changed getValueFromPOM is : "+getValueFromPOM);
			}
			elementValue = Runtimevalue.getProperty(envvar);
			if(elementValue == null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the environment variable : '"+ envvar +"'.", false);
				return false;
			}
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementById('"+getValueFromPOM+"').value = '"+elementValue+"'");
			ExtentTestManager.reportStepPass("Date picker value '"+elementValue+"' is entered for "+strTestObject+" ");	
			elementStatus = true;
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Date picker value '"+elementValue+"' is not entered for "+strTestObject+" " , true);  
			elementStatus = false;
		}
		return elementStatus;
	}	

	public synchronized boolean webElementSelectedStatus(String getValueFromPOM, String strTestObject) {
		boolean elementStatus = false;
		try{
			if(selectByLocatorType(getValueFromPOM).getAttribute("disabled").equals("true")){
				ExtentTestManager.reportStepPass("The Status of the object '"+ strTestObject +"' is checked or selected as expected.");
				elementStatus = true;
			}

			else if(selectByLocatorType(getValueFromPOM).getAttribute("disabled").equals(null)){
				ExtentTestManager.reportStepFail(driver,  "The Status of the object '"+ strTestObject +"' is unchecked or unselected.", true);
				elementStatus = false;
			}
		}catch(StaleElementReferenceException e){
			return webElementSelectedStatus(getValueFromPOM, strTestObject);
		}catch(Exception e){
			ExtentTestManager.reportStepFail(driver,  "Error occured while checking the status of the element. Error description is : '"+ e.getMessage() +"'.", true);
			elementStatus = false;
		}
		return elementStatus;
	}

	public synchronized boolean webTableVerifyDetailshashtable_2(String getValueFromPOM, String strTestObject, String primaryCol, String secondaryCol, String expRowValue, String expTextFromTable, int strExecEventFlag) {
		boolean webTableVerifyDetailshashtable_2 = false;
		//Initialized the variables as dummy
		int itemexist = 0;
		int testcasestatus = 0;
		String primaryColumnName = null;
		String secColumnName = null;
		String expectedRowValue = null;
		String expectedTextFromTable = null;
		String attributeID = null;
		String actualValueFromTable = null;
		String actualvalue = null;
		try{	
			// Get the values from excel sheet to find the rows and columns from web table
			if(strExecEventFlag == 1){

				primaryColumnName = getTestData(testDataFilePathStatic, testComponentNameStatic, primaryCol,gblrecordsCounterStatic);
				secColumnName = getTestData(testDataFilePathStatic, testComponentNameStatic, secondaryCol,gblrecordsCounterStatic);
				expectedRowValue = getTestData(testDataFilePathStatic, testComponentNameStatic, expRowValue,gblrecordsCounterStatic);
				expectedTextFromTable = getTestData(testDataFilePathStatic, testComponentNameStatic, expTextFromTable,gblrecordsCounterStatic);

			}

			if(primaryColumnName == null || secColumnName == null || expectedRowValue == null || expectedTextFromTable == null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
				return false;
			}
			//Get the ID from xpath(whole web table) 
			attributeID = selectByLocatorType(getValueFromPOM).getAttribute("id");
			List<WebElement> headerColumns = driver.findElements(By.xpath("//*[@id='"+attributeID+"']//thead/tr[1]/th"));
			int findPrimaryColumn = 0;

			for(int headerCounterPrimaryColumn = 1;headerCounterPrimaryColumn <= headerColumns.size(); headerCounterPrimaryColumn++){
				String GetTitleHeaders = driver.findElement(By.xpath("//*[@id='"+attributeID+"']//thead/tr[1]/th["+ headerCounterPrimaryColumn +"]")).getText();
				if(GetTitleHeaders.equalsIgnoreCase(primaryColumnName)){
					findPrimaryColumn = headerCounterPrimaryColumn;
					break;
				}
			}

			if(findPrimaryColumn == 0){
				ExtentTestManager.reportStepFail(driver,    "Primary key column : '"+ primaryColumnName +"' in not available in the table : "+ strTestObject +"'.", true);
				return false;
			}
			String[] headernames=secColumnName.split("\\|");
			String[] headervalues=expectedTextFromTable.split("\\|");
			Hashtable<String, String> hstable = new Hashtable<String, String>();
			for( int hdrcount = 0; hdrcount <= headernames.length - 1; hdrcount++)
			{
				hstable.put(headernames[hdrcount], headervalues[hdrcount]);
			}

			Hashtable<String, Integer> hstableheaders = new Hashtable<String, Integer>();
			for( int hdrcount = 0; hdrcount <= headernames.length - 1; hdrcount++)
			{
				int iheaderexist = 0;
				for( int headercolumn = 1; headercolumn <= headerColumns.size(); headercolumn++)
				{
					String headername = driver.findElement(By.xpath("//*[@id='"+attributeID+"']//thead/tr[1]/th["+ headercolumn +"]")).getText();
					if(headernames[hdrcount].equalsIgnoreCase(headername)){
						hstableheaders.put(headernames[hdrcount], headercolumn);
						iheaderexist = 1;
						break;
					}
				}
				if(iheaderexist == 0){
					hstableheaders.put(headernames[hdrcount], 0);
				}
			}

			List<WebElement> rows = driver.findElements(By.xpath("//*[@id='"+attributeID+"']//tbody/tr"));
			for(int row=1; row <= rows.size(); row++)
			{
				actualValueFromTable = driver.findElement(By.xpath("//*[@id='"+attributeID+"']//tbody/tr["+row+"]/td["+findPrimaryColumn+"]")).getText();

				if(actualValueFromTable.equals(expectedRowValue)){
					for( int hdrcount = 0; hdrcount <= headernames.length - 1; hdrcount++) {
						if(hstableheaders.get(headernames[hdrcount]) != 0){
							actualvalue = driver.findElement(By.xpath("//*[@id='"+attributeID+"']//tbody/tr["+row+"]/td["+ hstableheaders.get(headernames[hdrcount]) +"]")).getText();

							if(actualvalue.equals(hstable.get(headernames[hdrcount]))){
								ExtentTestManager.reportStepPass( "Actual value '"+ actualvalue +"' in the table : "+ strTestObject +"' matches with the expected value : '"+ hstable.get(headernames[hdrcount]) +"'.");
							}else{
								ExtentTestManager.reportStepFail(driver,"Actual value : '"+ actualvalue +"' in the table : "+ strTestObject +"' doesnot matches with the expected value : '"+ hstable.get(headernames[hdrcount]) +"'.", true);
								testcasestatus = 1;
							}
						}else{
							ExtentTestManager.reportStepFail(driver," Required header : '"+ headernames[hdrcount] +"' is not present in the table : '"+ strTestObject +"'.", true);
							testcasestatus = 1;
						}
					}
					itemexist = 1;
					break;
				}
			}

			if(itemexist == 0){
				ExtentTestManager.reportStepFail(driver,"The Value : '"+ expectedRowValue +"' is not present in the table : '"+ strTestObject +"'.", true);
				testcasestatus = 1;
			}


		}	catch(Exception e){
			ExtentTestManager.reportStepFail(driver,"Exception occured while webTableVerifyDetailshashtable_2. Error message is : "+ e +"'.", true);
			testcasestatus = 1;	 
		}
		if(testcasestatus == 0){
			webTableVerifyDetailshashtable_2 = true;
		}
		return webTableVerifyDetailshashtable_2;
	}

	public synchronized boolean WebListSelectTooltip(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag){
		String strData=null;
		String tooltip=null;
		boolean WebListSelectTooltip=false;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,strColumnName,gblrecordsCounterStatic);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
				return false;
			}
			Select dropdown= new Select(selectByLocatorType(getValueFromPOM));
			List<WebElement> allSelectedOptions = dropdown.getOptions();
			for (WebElement webElement : allSelectedOptions)
			{
			tooltip = webElement.getAttribute("title");
				if(tooltip.trim().equalsIgnoreCase(strData)){
				new Select(selectByLocatorType(getValueFromPOM)).selectByVisibleText(webElement.getText());
				ExtentTestManager.reportStepPass("Item '" +  tooltip + "' is selected from the  '"+strTestObject+"' List box successfully" );
				WebListSelectTooltip=true;
				break;
				}
			}
		} catch (StaleElementReferenceException e) {
			return WebListSelectTooltip(getValueFromPOM, strTestObject, strColumnName, strExecEventFlag);
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Item '" +  tooltip + " was not selected from the  '"+strTestObject+"' List box " , true); 
			WebListSelectTooltip=false;
		}
		return WebListSelectTooltip;
	}

	public synchronized boolean webTableVerifyDetailshashtableenvvar(String getValueFromPOM, String strTestObject, String primaryCol, String secondaryCol, String expRowValue, String expTextFromTable, int strExecEventFlag) {

		boolean webTableVerifyDetailshashtableenvvar = false;
		int itemexist = 0;
		int testcasestatus = 0;
		String primaryColumnName = null;
		String secColumnName = null;
		String expectedRowValue = null;
		String expectedTextFromTable = null;
		String attributeID = null;
		String actualValueFromTable = null;
		String actualvalue = null;
		String Expected_value = null;
		String Current_Date=null;
		try{	
			if(strExecEventFlag == 1){
				primaryColumnName = getTestData(testDataFilePathStatic, testComponentNameStatic, primaryCol,gblrecordsCounterStatic);
				secColumnName = getTestData(testDataFilePathStatic, testComponentNameStatic, secondaryCol,gblrecordsCounterStatic);
				expectedRowValue = Runtimevalue.getProperty(expRowValue);
				expectedTextFromTable = getTestData(testDataFilePathStatic, testComponentNameStatic, expTextFromTable,gblrecordsCounterStatic);

			}

			if(primaryColumnName == null || secColumnName == null || expectedRowValue == null || expectedTextFromTable == null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
				return false;
			}
			attributeID = selectByLocatorType(getValueFromPOM).getAttribute("id");
			List<WebElement> headerColumns = driver.findElements(By.xpath("//*[@id='"+attributeID+"']//thead/tr[1]/th"));
			int findPrimaryColumn = 0;

			for(int headerCounterPrimaryColumn = 1;headerCounterPrimaryColumn <= headerColumns.size(); headerCounterPrimaryColumn++){
				String GetTitleHeaders = driver.findElement(By.xpath("//*[@id='"+attributeID+"']//thead/tr[1]/th["+ headerCounterPrimaryColumn +"]")).getText();
				if(GetTitleHeaders.equalsIgnoreCase(primaryColumnName)){
					findPrimaryColumn = headerCounterPrimaryColumn;
					break;
				}
			}

			if(findPrimaryColumn == 0){
				ExtentTestManager.reportStepFail(driver, "Primary key column : '"+ primaryColumnName +"' in not available in the table : "+ strTestObject +"'.", true);
				return false;
			}

			String[] headernames=secColumnName.split("\\|");
			String[] headervalues=expectedTextFromTable.split("\\|");
			Hashtable<String, String> hstable = new Hashtable<String, String>();
			for( int hdrcount = 0; hdrcount <= headernames.length - 1; hdrcount++)
			{
				hstable.put(headernames[hdrcount], headervalues[hdrcount]);
			}

			Hashtable<String, Integer> hstableheaders = new Hashtable<String, Integer>();
			for( int hdrcount = 0; hdrcount <= headernames.length - 1; hdrcount++)
			{
				int iheaderexist = 0;
				for( int headercolumn = 1; headercolumn <= headerColumns.size(); headercolumn++)
				{
					String headername = driver.findElement(By.xpath("//*[@id='"+attributeID+"']//thead/tr[1]/th["+ headercolumn +"]")).getText();
					if(headernames[hdrcount].equalsIgnoreCase(headername)){
						hstableheaders.put(headernames[hdrcount], headercolumn);
						iheaderexist = 1;
						break;
					}
				}

				if(iheaderexist == 0){
					hstableheaders.put(headernames[hdrcount], 0);
				}
			}

			List<WebElement> rows = driver.findElements(By.xpath("//*[@id='"+attributeID+"']//tbody/tr"));
			for(int row=1; row <= rows.size(); row++)
			{
				actualValueFromTable = driver.findElement(By.xpath("//*[@id='"+attributeID+"']//tbody/tr["+row+"]/td["+findPrimaryColumn+"]")).getText();

				if(actualValueFromTable.equals(expectedRowValue)){
					for( int hdrcount = 0; hdrcount <= headernames.length - 1; hdrcount++) {
						if(hstableheaders.get(headernames[hdrcount]) != 0){
							actualvalue = driver.findElement(By.xpath("//*[@id='"+attributeID+"']//tbody/tr["+row+"]/td["+ hstableheaders.get(headernames[hdrcount]) +"]")).getText();

							if(hstable.get(headernames[hdrcount]).contains("CURRENT_DATE")){

								DateFormat dateFormat = new SimpleDateFormat(hstable.get(headernames[hdrcount]).split("\\;")[1]);
								Date date = new Date();
								Current_Date = dateFormat.format(date);
								Expected_value = Current_Date.trim();
								actualvalue = actualvalue.split(" ")[0];
								if(actualvalue.equals(Expected_value)){
									ExtentTestManager.reportStepPass( "Actual value : '"+ actualvalue +"' in the table : "+ strTestObject +"' matches with the expected value : '"+ Expected_value +"'.");
								}else{
									ExtentTestManager.reportStepFail(driver, "Actual value : '"+ actualvalue +"' in the table : "+ strTestObject +"' doesnot matches with the expected value : '"+ Expected_value +"'.", true);
									testcasestatus = 1;
								}

							} else if(hstable.get(headernames[hdrcount]).contains("FUTUREDATECOMPARE")){

								DateFormat dateFormat = new SimpleDateFormat(hstable.get(headernames[hdrcount]).split("\\;")[1]);
								Date date = new Date();
								Calendar expdate = Calendar.getInstance();
								expdate.setTime(date);
								expdate.add(Calendar.DATE, Integer.parseInt(hstable.get(headernames[hdrcount]).split("\\;")[2]) - 1);
								Current_Date = dateFormat.format(expdate.getTime());
								Expected_value = Current_Date.trim();

								actualvalue = actualvalue.split(" ")[0];
								if(actualvalue.equals(Expected_value)){
									ExtentTestManager.reportStepPass("Actual value : '"+ actualvalue +"' in the table : "+ strTestObject +"' matches with the expected value : '"+ Expected_value +"'.");
								}else{
									ExtentTestManager.reportStepFail(driver, "Actual value : '"+ actualvalue +"' in the table : "+ strTestObject +"' doesnot matches with the expected value : '"+ Expected_value +"'.", true);
									testcasestatus = 1;
								}
							} else if(hstable.get(headernames[hdrcount]).contains("ENVVARCOMPARE")){

								String strenvvar = hstable.get(headernames[hdrcount]).split("\\;")[1];
								Expected_value = Runtimevalue.getProperty(strenvvar);
								if(Expected_value != null){
									if(actualvalue.equals(Expected_value)){
										ExtentTestManager.reportStepPass("Actual value : '"+ actualvalue +"' in the table : "+ strTestObject +"' matches with the expected value : '"+ Expected_value +"'.");
									}else{
										ExtentTestManager.reportStepFail(driver,"Actual value : '"+ actualvalue +"' in the table : "+ strTestObject +"' doesnot matches with the expected value : '"+ Expected_value +"'.", true);
										testcasestatus = 1;
									}
								} else {

									ExtentTestManager.reportStepFail(driver, "The Value in the environment variable : '"+ strenvvar +"' is empty.", true);
									testcasestatus = 1;

								}
							}	else {
								if(actualvalue.equals(hstable.get(headernames[hdrcount]))){
									ExtentTestManager.reportStepPass("Actual value : '"+ actualvalue +"' in the table : "+ strTestObject +"' matches with the expected value : '"+ hstable.get(headernames[hdrcount]) +"'.");
								}else{
									ExtentTestManager.reportStepFail(driver,"Actual value : '"+ actualvalue +"' in the table : "+ strTestObject +"' doesnot matches with the expected value : '"+ hstable.get(headernames[hdrcount]) +"'.", true);
									testcasestatus = 1;
								}
							}
						}else{
							ExtentTestManager.reportStepFail(driver, "Required header : '"+ headernames[hdrcount] +"' is not present in the table : '"+ strTestObject +"'.", true);
							testcasestatus = 1;
						}
					}
					itemexist = 1;
					break;
				}
			}

			if(itemexist == 0){
				ExtentTestManager.reportStepFail(driver, "The Value : '"+ expectedRowValue +"' is not present in the table : '"+ strTestObject +"'.", true);
				testcasestatus = 1;
			}


		}	catch(Exception e){
			ExtentTestManager.reportStepFail(driver, "Exception occured while webTableVerifyDetailshashtableenvvar. Error message is : "+ e +"'.", true);
			testcasestatus = 1;	 
		}
		if(testcasestatus == 0){
			webTableVerifyDetailshashtableenvvar = true;
		}
		return webTableVerifyDetailshashtableenvvar;
	}

	public synchronized boolean WebListCheckSelectedExist(String getValueFromPOM, String strTestObject, String strColumnName, int itemscount, int strExecEventFlag) {
		String strData = null;
		boolean WebListCheckSelectedExist = false;
		String selectedValue = null;
		Select select_dropdown = null;
		int itemlistcount;
		
		try{
			if(strExecEventFlag==1){
				strData = getTestData(testDataFilePathStatic, testComponentNameStatic, strColumnName,gblrecordsCounterStatic);
			}	
			if(strData==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in the data sheet.", false);
				return false;
			}
			selectedValue = new Select(selectByLocatorType(getValueFromPOM)).getFirstSelectedOption().getText();
			select_dropdown = new Select(selectByLocatorType(getValueFromPOM));  
			itemlistcount = select_dropdown.getOptions().size();
			if(selectByLocatorType(getValueFromPOM).isDisplayed()){

				if(selectedValue.trim().equalsIgnoreCase(strData.trim()) && itemlistcount == itemscount){
					ExtentTestManager.reportStepPass(strTestObject +"'s selected dropdown value '"+selectedValue + "' matches with the Expected Value '"+strData+"' and this is the only item present in the dropdown." );
					WebListCheckSelectedExist = true;
				}else{
					ExtentTestManager.reportStepFail(driver,strTestObject +"'s selected dropdown value '"+selectedValue + "' does not matches with the Expected Value '"+strData+"' and this is not the only item present in the dropdown."  , true); 
					WebListCheckSelectedExist=false;
				}
			}else{
				ExtentTestManager.reportStepFail(driver,strTestObject +"'s selected dropdown value '"+selectedValue + "'is not displayed" , true); 
				WebListCheckSelectedExist=false;
			}

		}catch(StaleElementReferenceException e){
			return WebListCheckSelectedExist(getValueFromPOM, strTestObject, strColumnName, itemscount, strExecEventFlag);
		}catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,"Selected dropdown value is '" +  selectedValue + " is not shown in "+strTestObject+"" , true); 
			WebListCheckSelectedExist=false;
		}
		return WebListCheckSelectedExist;
	}

	public synchronized boolean WebCheckboxUncheckedStatus(String getValueFromPOM, String strTestObject){
		boolean elementStatus = false;
		try{
			if(!selectByLocatorType(getValueFromPOM).getAttribute("checked").equals("true")){
				ExtentTestManager.reportStepPass("The feature '"+ strTestObject +"' is in deactive status.");
				elementStatus = true;
			}else {
				ExtentTestManager.reportStepFail("The feature '"+ strTestObject +"' is in active status.", true);
				elementStatus = false;
			}
		}catch(StaleElementReferenceException e){
			return WebCheckboxUncheckedStatus(getValueFromPOM, strTestObject);
		}catch(NullPointerException e){
				log.info("Null pointer exception occurred :"+e);
				ExtentTestManager.reportStepPass("The feature '"+ strTestObject +"' is in deactive status.");
				elementStatus = true;
		}catch(Exception e){
			ExtentTestManager.reportStepFail("Exception occured while finding the element. Error description is : "+ e.getMessage() +".", true);
			elementStatus = false;
		}
		return elementStatus;
	}

	public synchronized boolean WebCheckboxcheckedStatus(String getValueFromPOM, String strTestObject){
		boolean elementStatus = false;
		try{
			if(selectByLocatorType(getValueFromPOM).getAttribute("checked").equals("true")){
				ExtentTestManager.reportStepPass("The feature '"+ strTestObject +"' is in active status.");
				elementStatus = true;
			}else {
				ExtentTestManager.reportStepFail("The feature '"+ strTestObject +"' is in deactive status.", true);
				elementStatus = false;
			}
		}catch(StaleElementReferenceException e){
			return WebCheckboxcheckedStatus(getValueFromPOM, strTestObject);
		}catch(NullPointerException e){
			ExtentTestManager.reportStepFail("The feature '"+ strTestObject +"' is in deactive status.", true);
			elementStatus = false;
		}catch(Exception e){
			ExtentTestManager.reportStepFail( "Exception occured while finding the element. Error description is : "+ e.getMessage() +".", true);
			elementStatus = false;
		}
		return elementStatus;
	}

	public synchronized boolean Togglecheckdisabled(String getValueFromPOM, String strTestObject) {
		boolean elementStatus = false;
		try{
			if(selectByLocatorType(getValueFromPOM).getAttribute("disabled").equals("true")){
				ExtentTestManager.reportStepPass("The feature '"+ strTestObject +"' is disabled as expected.");
				elementStatus = true;
			}else {
				ExtentTestManager.reportStepFail("The feature '"+ strTestObject +"' is not disabled.", true);
				elementStatus = false;
			}
		}catch(StaleElementReferenceException e){
			return Togglecheckdisabled(getValueFromPOM, strTestObject);
		}catch(Exception e){
			ExtentTestManager.reportStepFail("Exception occured while finding the element. Error description is : "+ e.getMessage() +".", true);
			elementStatus = false;
		}
		return elementStatus;
	}

	public synchronized boolean eShopSQLDBCheckValueExist(String sqltablename, String strsqlcolumnname,String strsqlcondition,int strExecEventFlag){
		boolean functionStatus=false;
		String query;
		String check;
		String Table_name;
		String Column_name;
		String SQL_condition;
		String Actual_Value;

		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic, sqltablename, gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcolumnname, gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic, strsqlcondition, gblrecordsCounterStatic);
			}else{
				Table_name=sqltablename;
				Column_name=strsqlcolumnname;
				SQL_condition=strsqlcondition;
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";

			//Check for Record Available
			ResultSet rs_SQLServerCheck = EShopstmt.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = EShopstmt.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' exist for the SQL Query "+query+".", false);
					functionStatus= false;
			}else{
				ResultSet rs_SQLServer = EShopstmt.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1).trim();

				if(!(Actual_Value==null)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' exist for the SQL Query "+query+".");
					functionStatus= true;
				}else if(Actual_Value==null){
					ExtentTestManager.reportStepFail(driver,"Value does not exist for the SQL Query "+query+"'", false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in SQL query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}
	
	public synchronized boolean WebEditEnterTextFromGAFEnv(String getValueFromPOM, String strTestObject,String strColumnName,int strExecEventFlag)throws Exception  {
		String strData=null;
		boolean WebEditEnterTextFromGAFEnv=false;
		try {
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic, strColumnName, gblrecordsCounterStatic);
				strData=GAFValue.getProperty(strData);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail("Required details are not provided in the data sheet." , true);
				return false;
			}

			selectByLocatorType(getValueFromPOM).clear();
			selectByLocatorType(getValueFromPOM).sendKeys(strData);
			
			ExtentTestManager.reportStepPass("Text '"+strData+"' is entered in the  '"+strTestObject+"' successfully");
			WebEditEnterTextFromGAFEnv=true;

		} catch (StaleElementReferenceException e) {
				return WebEditEnterTextFromGAFEnv(getValueFromPOM, strTestObject, strColumnName,strExecEventFlag);
		} catch (Exception e) {
			ExtentTestManager.reportStepFail("Text '" +  strData + "' is not entered in the  '"+strTestObject+"'."+e.getMessage() , true); 
			WebEditEnterTextFromGAFEnv=false;
		}
		return WebEditEnterTextFromGAFEnv;
	}
	
	public synchronized boolean SQLDBSelectFromGAFEnv(String sqltablename, String strsqlcolumnname,String strsqlcondition,String strEnvVariableColumn,int strExecEventFlag){
		String query = null;
		String check;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String Expected_value = null;
		String envVariable=null;
		String Actual_Value = null;
		boolean functionStatus=false;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				envVariable=getTestData(testDataFilePathStatic, testComponentNameStatic,strEnvVariableColumn,gblrecordsCounterStatic);
			}
			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in test data sheet.", false);
				return false;
			}

			Expected_value=GAFValue.getProperty(envVariable);
			if(Expected_value==null){
				ExtentTestManager.reportStepFail(driver,"Dynamic Variable '"+envVariable+"' has NO VALUE", false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";
			check = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+" order by 1 desc";

			//Check for Record Available
			ResultSet rs_SQLServerCheck = stmt.executeQuery(check);
			int temp=0;	
			while(rs_SQLServerCheck.next()){
				temp++;
			}

			if(temp < 1){
				ExtentTestManager.reportStepFail( driver,"NO RECORDS available for the Query  "+ query + "  in DB",false);
				return false;
			}

			rs_SQLServerCheck = stmt.executeQuery(check);
			rs_SQLServerCheck.next();
			rs_SQLServerCheck.getObject(Column_name);

			if (rs_SQLServerCheck.wasNull()) {

				log.info("Acual value is nULL");

				if(Expected_value.equalsIgnoreCase("NULL")){
					ExtentTestManager.reportStepPass("Actual value 'NULL' for the SQL Query "+query+" matches the expected value from ENV Variable '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Expected_value.equalsIgnoreCase("NULL"))){
					ExtentTestManager.reportStepFail(driver,"Actual value 'NULL' for the SQL Query "+query+" does not match with the expected value from ENV Variable '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}else{
				ResultSet rs_SQLServer = stmt.executeQuery(query);
				rs_SQLServer.next();
				Actual_Value = rs_SQLServer.getString(1).trim();
				if(Actual_Value.equalsIgnoreCase(Expected_value)){
					ExtentTestManager.reportStepPass("Actual value '"+Actual_Value+"' for the SQL Query "+query+" matches the expected value from ENV Variable '"+Expected_value+"'");
					functionStatus= true;
				}else if(!(Actual_Value.equalsIgnoreCase(Expected_value))){
					ExtentTestManager.reportStepFail(driver,"Actual value '"+Actual_Value+"' for the SQL Query "+query+" does not match with the expected value from ENV Variable '"+Expected_value+"'", false);
					functionStatus= false;
				}
			}

		}catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing the values in SQL query.Error description is : "+ e.getMessage(), false);
			return false;
		}
		return functionStatus;
	}
	
	public synchronized boolean TootltipTextCompare(String getValueFromPOM, String strTestObject,String strtestData, int strExecEventFlag ){
		String actualResult=null;
		boolean functionStatus= false;
		String testData=null;

		try{
			if(strExecEventFlag==1)
				testData= getTestData(testDataFilePathStatic, testComponentNameStatic, strtestData, gblrecordsCounterStatic);
			else
				testData=strtestData;

			if(testData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the data sheet.", false);
				return false;
			}

			actualResult = selectByLocatorType(getValueFromPOM).getAttribute("data-original-title");

		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while getting the text from the WebElement '"+strTestObject+"' . Error description is :"+e.getMessage(),true);
			functionStatus=false;
		}

		try{
			if((actualResult.trim()).equalsIgnoreCase(testData.trim())){
				ExtentTestManager.reportStepPass("Actual value '" +actualResult+ "' matches with the expected value '"+testData+ "' in the input field '"+strTestObject+"'");
				functionStatus=true;
			}else{
				ExtentTestManager.reportStepFail(driver,"Actual Value '" +actualResult+ "' does not match with the Expected value '"+testData+ "' in the input field '"+strTestObject+"'",true);
				functionStatus=false;
			}
		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver,"Error occured while comparing actual and expected values. Error description is :"+e.getMessage(), true);
			functionStatus=false;
		}
		return functionStatus;
	}

	public synchronized boolean WeblistItemsCountVerify(String getValueFromPOM, String strTestObject, String totalNumbersColumn,int itemscount_not_consider,int strExecEventFlag){

		boolean WeblistItemsCountVerify= false;
		String strData=null;
		int totalNumber;

		try{
			if(strExecEventFlag==1){
				strData=getTestData(testDataFilePathStatic, testComponentNameStatic,totalNumbersColumn,gblrecordsCounterStatic);
			}
			if(strData==null){
				ExtentTestManager.reportStepFail(driver, "Required details are not provided in the datasheet.", false);
				return false;
			}

			totalNumber=Integer.parseInt(strData);

			Select se = new Select(selectByLocatorType(getValueFromPOM));
			List<WebElement> options = se.getOptions();

			if(totalNumber == options.size() - itemscount_not_consider)
			{
				ExtentTestManager.reportStepPass("The number of items present in the dropdown '"+ strTestObject +"' matches with the given number of items '"+ totalNumber +"'.");
				WeblistItemsCountVerify=true;
			} else {
				ExtentTestManager.reportStepFail(driver, "The number of items present in the dropdown '"+ strTestObject +"' doesn't matches with the given number of items '"+ totalNumber +"'.", true);
				WeblistItemsCountVerify=false;
			}

		} catch (Exception e){
			ExtentTestManager.reportStepFail(driver, "Error occured while comparing the values. Error description is : "+ e.getLocalizedMessage() +".", true);
			WeblistItemsCountVerify=false;
		}
		return WeblistItemsCountVerify;
	}

	public synchronized boolean ESHOPSQLDBCheckNoOfRowsExist(String sqltablename, String strsqlcolumnname,String strsqlcondition,String  strNoOfRowsShouldBePresent,int strExecEventFlag){
		boolean ESHOPSQLDBCheckNoOfRowsExist= false;
		String query=null;
		String Table_name = null;
		String Column_name = null;
		String SQL_condition = null;
		String noOfRowsShouldBePresent=null;
		try {
			if(strExecEventFlag==1){
				Table_name=getTestData(testDataFilePathStatic, testComponentNameStatic,sqltablename,gblrecordsCounterStatic);
				Column_name=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcolumnname,gblrecordsCounterStatic);
				SQL_condition=getTestData(testDataFilePathStatic, testComponentNameStatic,strsqlcondition,gblrecordsCounterStatic);
				noOfRowsShouldBePresent=getTestData(testDataFilePathStatic, testComponentNameStatic,strNoOfRowsShouldBePresent,gblrecordsCounterStatic);
			}

			if(Table_name==null || Column_name==null || SQL_condition==null){
				ExtentTestManager.reportStepFail(driver,    "Required details are not provided in test data sheet.", false);
				return false;
			}
			query = "select "+Column_name+" from "+Table_name+" where "+SQL_condition+"";
			ResultSet rs_SQLServer = EShopstmt.executeQuery(query);		

			int temp=0;	
			while(rs_SQLServer.next()){
				temp++;
			}

			if(temp==(Integer.parseInt(noOfRowsShouldBePresent))){
				ExtentTestManager.reportStepPass("Actual No. of Rows '"+temp+"' for the Query *"+query+"* matches with expected No of Rows '"+noOfRowsShouldBePresent+"'");
				ESHOPSQLDBCheckNoOfRowsExist=true;
			}else{
				ExtentTestManager.reportStepFail(driver, "Actual No. of Rows '"+temp+"' for the Query *"+query+"* does not match with expected No of Rows '"+noOfRowsShouldBePresent+"'", false);
				ESHOPSQLDBCheckNoOfRowsExist=true;
			}

		}catch (NullPointerException e) {
				ExtentTestManager.reportStepFail(driver,"Null Pointer exception occured while comparing the No.of Records", false);
				ESHOPSQLDBCheckNoOfRowsExist=false;
				
		} catch (Exception e) {
			ExtentTestManager.reportStepFail(driver,    "Error occured while checking whether the executed query has any records (or) not. Error description is : "+ e.getMessage() +".", false);
			ESHOPSQLDBCheckNoOfRowsExist=false;
		}
		return ESHOPSQLDBCheckNoOfRowsExist;
	}	
	
	
}



