package wrappers;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;


import report.ExtentTestManager;

/**
 * 
 * <h1>ApplicationWrapper</h1>
 * 
 * <p>
 * <b>Class holds all the TestNG Annotation for Creating Report</b> 
 * 
 * @author Lakshman A
 * @since OCT 1, 2016
 *
 */

public class ApplicationWrapper extends FunctionLibrary {

	public static int fileCount;
	public static String  reportFileName;
	static Set<Integer> set;
	static CountDownLatch latch;
	static CountDownLatch afterLatch;
	private static boolean calledMyMethod;

	/**
	 * <b>killBrowserInTaskManager</b><br>
	 * 
	 * <p><b>Objective : To Kill the browsers based on the Browser Name in Browser.Properties file<p></b>
	 * Note: Based on the killBrowser property value the Browser will be killed
	 * @author LAKSHMAN
	 * @since DEC 23, 2016
	 */

	public void killBrowserInTaskManager(){
		try {
			if (sysProperty.getProperty("killBrowser").equalsIgnoreCase("true")) {
				if (browserProperty.getProperty("testBrowser").equalsIgnoreCase("firefox")) {
					Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
				} else if (browserProperty.getProperty("testBrowser").equalsIgnoreCase("chrome")) {
					Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
				} else if (browserProperty.getProperty("testBrowser").equalsIgnoreCase("ie")) {
					Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
				}
				Thread.sleep(2000);
				log.info("Browser " + browserProperty.getProperty("testBrowser") + " killed successfully");
			}
		} catch (Exception e) {
			log.info("Exception while killBrowserInTaskManager. "+e);
			e.printStackTrace();
		}
	}

	/**
	 * <b>killPluginFFInTaskManager</b><br>
	 * 
	 * <p><b>Objective : To Kill the Plugin container deiaplayed during the firefox browser instantiate<p></b>
	 * @author LAKSHMAN
	 * @since DEC 23, 2016
	 */
	
	public void killPluginFFInTaskManager(){
		try {
			if (browserProperty.getProperty("testBrowser").equalsIgnoreCase("firefox")) {
				Runtime.getRuntime().exec("taskkill /F /IM plugin-container.exe");
				Thread.sleep(1000);
				log.info("Firefox Plugin Container exe killed successfully");
			}
		} catch (Exception e) {
			log.info("Exception while killing Plugin container. "+e);
			e.printStackTrace();
		}
	}

	/**
	 * <b>killBrowserInTaskManager</b><br>
	 * 
	 * <p><b>Objective : To Kill the browsers drivers based on the Browser Name in Browser.Properties file<p></b>
	 * Note: Browser drivers will be mandatorily killed when this function is triggered
	 * @author LAKSHMAN
	 * @since DEC 23, 2016
	 */

	public void killBrowserDriverInTaskManager(){
		try {
			if (browserProperty.getProperty("testBrowser").equalsIgnoreCase("firefox")) {
				Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
			} else if (browserProperty.getProperty("testBrowser").equalsIgnoreCase("chrome")) {
				Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			} else if (browserProperty.getProperty("testBrowser").equalsIgnoreCase("ie")) {
				Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			}
			Thread.sleep(2000);
			log.info("Browser Driver " + browserProperty.getProperty("testBrowser") + " killed successfully");

		} catch (Exception e) {
			log.info("Exception while killBrowserInTaskManager. "+e);
			e.printStackTrace();
		}
	}

	/**
	 * <b>reduceHTMLReportSize</b><br>
	 * 
	 * <p><b>Objective : To Reduce the HTML Report filze size by removing the New lines, Tabs and continous spaces<p></b>
	 * @author LAKSHMAN
	 * @since JAN 2, 2017
	 */
	
	public void reduceHTMLReportSize(String reportFilePath){
		
		try {
			log.info("Reducing the HTML Report File Size...!");
			String contents = FileUtils.readFileToString(new File("./"+sysProperty.getProperty("extentReportFilePath")+"/"+reportFilePath), "UTF-8");
			contents = contents.replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("\t", "");
			FileWriter output = new FileWriter("./"+sysProperty.getProperty("extentReportFilePath")+"/"+reportFilePath);
			BufferedWriter bw = new BufferedWriter(output);
			bw.write(contents);
			bw.close();
			log.info("HTML Report File Compressed...!");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception occured while reducing the file Size. Exception "+e);
		}

	}
	
	/**
	 * <b>createReport</b><br>
	 * 
	 * <p><b>Objective : To Create Report with the Given Report file name and to Inititate the Latchs based on Suite Type<p></b>
	 * Note: Browser drivers will be mandatorily killed when this function is triggered
	 * @author LAKSHMAN
	 * @since DEC 23, 2016
	 */

	public void createReport(String suiteType) throws Exception{

		if(suiteType.trim().equalsIgnoreCase(sysProperty.getProperty("ParallelSuiteType"))){
			latch = new CountDownLatch(Integer.parseInt(sysProperty.getProperty("No_of_Instances")));
			afterLatch = new CountDownLatch(Integer.parseInt(sysProperty.getProperty("No_of_Instances")));
		}

		fileCount++;
		String datetime=new SimpleDateFormat ("_dd_MM_yyyy_HHmmss").format(new Date());
		reportFileName=sysProperty.getProperty("extentReportFileName")+"_"+(browserProperty.getProperty("testBrowser").toUpperCase())+"_"+datetime+"_"+fileCount+".html";
		ExtentTestManager.startReportWithFileName(fileCount,reportFileName);
		ExtentTestManager.addReportInfo("Selenium Version", "2.53.0");
	}

	/**
	 * <b>beforeSuite</b><br>
	 * 
	 * <p><b>Objective : To Create Report at the Start of any TestNG Suite and kill the browser drivers and Opened browsers<p></b>
	 * @author LAKSHMAN
	 * @since DEC 23, 2016
	 */

	@BeforeSuite(alwaysRun=true)
	@Parameters({"suiteType"})
	public void beforeSuite(String suiteType) throws Exception{
		initializeFiles();
		log.info("***** TESTBATCH EXECUTION STARTS... *****");		
		createReport(suiteType);
		killBrowserInTaskManager();
		killBrowserDriverInTaskManager();
	}

	/**
	 * <b>beforeTest</b><br>
	 * 
	 * <p><b>Objective : To Create Test Node in the Report and to kill the browser/browserdrivers<p></b>
	 * @author LAKSHMAN
	 * @since DEC 23, 2016
	 */

	@BeforeTest(alwaysRun=true)
	@Parameters({"testcaseName","testcaseDesc","testCategory","testAuthor","suiteType"})
	public void beforeTest(String testcaseName, String testcaseDesc, String testCategory, String testAuthor,String suiteType){
		try {
			//Below function commented since this Window popup appears only while using the Selenium 3x and Gecko driver
			//Now we are currently using only Selenium 2x without Gecko
			//killPluginFFInTaskManager();
			if(suiteType.equalsIgnoreCase(sysProperty.getProperty("SerialSuiteType"))){
				killBrowserInTaskManager();
				killBrowserDriverInTaskManager();
			}
		} catch (Exception e) {
			log.info("Exception occured in beforeTest. "+e);
			e.printStackTrace();
		}
		String browserName=browserProperty.getProperty("testBrowser");
		ExtentTestManager.startTestCase(testcaseName, "<b>"+testcaseDesc+"</b>",browserName.toUpperCase()+","+testCategory, testAuthor);
	}

	/**
	 * <b>afterTest</b><br>
	 * 
	 * <p><b>Objective : To End the Test Node in the Report<p></b>
	 * <p>Note: This method also monitors the Report file size and when it crosses the configred size then New Report will be created
	 * Also for Parallel Suite type Thread Catch concept is included which is handles by Countdown latch and thread.await concept<p>
	 * @author LAKSHMAN
	 * @since DEC 23, 2016
	 */

	@AfterTest(alwaysRun=true)
	@Parameters({"suiteType"})
	public void afterTest(String suiteType) throws Exception{

		ExtentTestManager.endTestCase();

		File file =new File("./"+sysProperty.getProperty("extentReportFilePath")+"/"+reportFileName);
		double reportFileSizeInBytes = file.length();
		double reportFileSizeInKB = (reportFileSizeInBytes / 1024);
		int fileSize=(int) reportFileSizeInKB;
		log.info("FileSize in KB : "+fileSize);

		if(fileSize > Integer.parseInt(sysProperty.getProperty("MaxReportFileSize"))){

			int ID=(int) Thread.currentThread().getId();
			log.info("Thread -> '"+ID+"' reached New report condition");

			if(suiteType.trim().equalsIgnoreCase(sysProperty.getProperty("ParallelSuiteType"))){
				Thread t = new Thread() {
					public void run() {
						//System.out.println("Thread '"+ID+"' Going to count down...");
						latch.countDown();
					}
				};
				t.start();
				log.info("Thread -> '"+ID+"' started waiting for other threads.");
				//latch.await();
				try {
					latch.await(5,TimeUnit.MINUTES);
					log.info("Either All Threads reached here or Latch waited Long enough to Timeout!");
				} catch (InterruptedException e) {
					e.printStackTrace();
					log.info("InterruptedException in  Waited Long enough!");
				}
				//log.info("Thread '"+ID+"' Done waiting!");
			}

			createReportSynchronized(suiteType);

			if(suiteType.trim().equalsIgnoreCase(sysProperty.getProperty("ParallelSuiteType"))){
				Thread th = new Thread() {
					public void run() {
						//System.out.println("After Thread '"+ID+"' Going to count down...");
						afterLatch.countDown();
					}
				};
				th.start();
				log.info("After Process. Thread -> '"+ID+"' started waiting for other threads.");
				//afterLatch.await();
				try {
					afterLatch.await(20,TimeUnit.SECONDS);
					log.info("After Process. Either All Threads reached here or Latch waited Long enough to Timeout!");
				} catch (InterruptedException  e) {
					e.printStackTrace();
					log.info("InterruptedException in After Waited Long enough!");
				}
				//log.info("After Thread '"+ID+"' Done waiting!");
			}

			log.info("New Report name : "+reportFileName);
			log.info("*************************All Threads Released*******************");
			calledMyMethod=false;

		}else{
			log.info("New report condition not yet needed");
		}

	}

	/**
	 * <b>afterSuite</b><br>
	 * 
	 * <p><b>Objective : To End the Test Node in the Report<p></b>
	 * <p>Note: This Crete/finish the report for the currently executed TestNG XML Suite</p>
	 * @author LAKSHMAN
	 * @since DEC 23, 2016
	 */

	@AfterSuite(alwaysRun=true)
	public void afterSuite(){
		reduceHTMLReportSize(reportFileName);
		killBrowserInTaskManager();
		killBrowserDriverInTaskManager();
		log.info("***** EXECUTION COMPLETED. *****");
		log.info("\n\n\n***** Generating the Overview Html Report *****");
		log.info("Note: Kindly check the Script details Tracker is placed in the Path, else it wont get Updated.");
		try{
			//Thread.sleep(2000);
			//ScriptDetails.generateExcelReports();
			//log.info("***** Overview Html Report Generated & Script Details Tracker Updated *****");
		}catch(Exception e){
			log.info("Exception in afterSuite generateExcelReports. "+e);
			e.printStackTrace();
		}
	}

	/**
	 * <b>createReportSynchronized</b><br>
	 * 
	 * <p><b>Objective : To Create the Report file<p></b>
	 * <p>Note: This method will be executed only by one thread(So one report will be generated and used by other threads also) 
	 * incase of multiple threads alive</p>
	 * @author LAKSHMAN
	 * @since DEC 23, 2016
	 */

	public synchronized void createReportSynchronized(String suiteType) throws Exception{

		if(calledMyMethod) {
			return;
		} else {
			calledMyMethod = true;
			log.info("Synchronized Report Create Method. Thread Creating by Thread -> "+(int)Thread.currentThread().getId()+"");
			reduceHTMLReportSize(reportFileName);
			createReport(suiteType);
			killBrowserInTaskManager();
			killBrowserDriverInTaskManager();
			log.info("*** Report Created Successfully! ***");
		}     

	}

}
