package Page_Objects;

import java.util.Hashtable;

import utils.ReadExcel;
import wrappers.FunctionLibrary;

public class Subscribe_History extends FunctionLibrary {

	public synchronized String subHistoryPage(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();

			hs.put("simButton", "xpath#//a[@title='Sim']");
			hs.put("subscribeHistBtn", "xpath#//div[@title='Subscriber History']");
			hs.put("fromDate", "id#txtfromdate");
			hs.put("toDate", "id#txtToDate");
			hs.put("type", "id#ddType");
			hs.put("submit", "id#btnSubmit");
			hs.put("reset", "id#btnReset");

			hs.put("confirmMsg", "xpath#//span[@id='Msg' and contains(@style, 'rgb')]");
			hs.put("tableView", "id#divGETSubscriberHistory");

			//Alert validation
			hs.put("valFromDate", "xpath#//input[@id='txtfromdate' and contains(@aria-required, 'true')]");
			hs.put("valToDate", "xpath#//input[@id='txtToDate' and contains(@aria-required, 'true')]");
			hs.put("valType", "xpath#//select[@id='ddType' and contains(@aria-required, 'true')]");

			return hs.get(locator);

		}catch(Exception e){
			System.out.println("Error occurred in POM classes :"+e);
		}
		return null;
	}


	public synchronized String RetrieveTestDataValue(String filePath,String compName,String strColumnName,String gblrecordsCounter,int strExecEventFlag) throws Exception{
		String strData=null;
		ReadExcel readExcel=new ReadExcel(filePath);

		if(strExecEventFlag!=0){
			strData=readExcel.getCellData(compName, strColumnName, Integer.parseInt(gblrecordsCounter));
		}
		return strData;
	}

	public synchronized String tableValues(String filePath,String compName,String gblrecordsCounter,String locator, String tableValues) throws Exception{
		try{
			String value = RetrieveTestDataValue(filePath, compName, tableValues,gblrecordsCounter, 1);

			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("tableType", "xpath#//td[@aria-describedby='GETSubscriberHistory_type' and contains(@title, '"+value+"')]");
			hs.put("status", "xpath#//td[@aria-describedby='GETSubscriberHistory_type' and contains(@title, '"+value+"')]//following-sibling::td[@aria-describedby='GETSubscriberHistory_status']");
			hs.put("reason", "xpath#//td[@aria-describedby='GETSubscriberHistory_type' and contains(@title, '"+value+"')]//following-sibling::td[@aria-describedby='GETSubscriberHistory_reason']");
			hs.put("ticketID", "xpath#//td[@aria-describedby='GETSubscriberHistory_type' and contains(@title, '"+value+"')]//following-sibling::td[@aria-describedby='GETSubscriberHistory_ticketId']");
			hs.put("submittedDate", "xpath#//td[@aria-describedby='GETSubscriberHistory_type' and contains(@title, '"+value+"')]//following-sibling::td[@aria-describedby='GETSubscriberHistory_submitedDate']");
			hs.put("userName", "xpath#//td[@aria-describedby='GETSubscriberHistory_type' and contains(@title, '"+value+"')]//following-sibling::td[@aria-describedby='GETSubscriberHistory_userName']");
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}



}
