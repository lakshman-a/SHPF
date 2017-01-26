package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * <b>BrowserUpdate</b><br>
 * 
 * <p><b>Objective : To Update the Browser name in Browser Properties files based on the TestNG file and its Parameters executing this class<p></b>
 * @author LAKSHMAN
 * @since Nov 29, 2016
 */

public class BrowserUpdate {

	/**
	 * <b>updateTestBrowserName</b><br>
	 * 
	 * <p><b>Objective : Update the Name of the Browser in browser.properties file<p></b>
	 * @author LAKSHMAN
	 * @since Nov 29, 2016
	 */

	@Test
	@Parameters({"browserName","browserDesc"})
	public void updateTestBrowserName(String browserName,String browserDesc) {
		try {

			Properties browserProp =new Properties();
			browserProp.load(new FileInputStream(new File("./src/properties/browser.properties")));

			browserProp.setProperty("testBrowser", browserName);
			browserProp.setProperty("testBrowserDesc", browserDesc);

			FileOutputStream fileOut = new FileOutputStream(new File("./src/properties/browser.properties"));
			browserProp.store(fileOut, "Update the Browser details");
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
