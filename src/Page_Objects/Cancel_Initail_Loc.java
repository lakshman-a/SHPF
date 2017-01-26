package Page_Objects;

import java.util.Hashtable;

import utils.ReadExcel;
import wrappers.FunctionLibrary;

public class Cancel_Initail_Loc extends FunctionLibrary {

	public synchronized String RetrieveTestDataValue(String filePath,String compName,String strColumnName,String gblrecordsCounter,int strExecEventFlag) throws Exception{
		String strData=null;
		ReadExcel readExcel=new ReadExcel(filePath);
		if(strExecEventFlag!=0){
			strData=readExcel.getCellData(compName, strColumnName, Integer.parseInt(gblrecordsCounter));
		}
		return strData;
	}

	public synchronized String cancelInitalLocPage(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("simButton", "xpath#//a[@title='Sim']");
			hs.put("initialLocBtn", "xpath#//div[@title='Cancel Initial Location']");
			hs.put("msisdn", "id#txtMsisdnImsi");
			hs.put("submitButton", "id#btnSubmit");
			hs.put("confirm_Message", "xpath#//span[@id='lblError' and contains(@style, 'rgb')]");
			hs.put("historyBtn", "id#btnView");
			hs.put("tableView", "id#gbox_mainGrdLocation");
			hs.put("validationMsg", "xpath#//b[@class='false']");
			hs.put("networkAccessDD", "id#ddNetworkAccess");
			hs.put("networkChkbox", "id#chkRetain");
			return hs.get(locator);

		}catch(Exception e){
			System.out.println("Error occurred in POM classes :"+e);
			return null;
		}
	}

	public synchronized String tableValues(String filePath,String compName,String gblrecordsCounter, String locator, String tableValues) throws Exception{

		String value =RetrieveTestDataValue(filePath, compName, tableValues,gblrecordsCounter, 1);
		try{

			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("msisdnTableValue", "xpath#//td[@aria-describedby='mainGrdLocation_MSISDN' and contains(@title, '"+value+"')]");
			hs.put("imsiTableValue", "xpath#//td[@aria-describedby='mainGrdLocation_MSISDN' and contains(@title, '"+value+"')]//following-sibling::td[@aria-describedby='mainGrdLocation_IMSI']");
			hs.put("submitByTableValue", "xpath#//td[@aria-describedby='mainGrdLocation_MSISDN' and contains(@title, '"+value+"')]//following-sibling::td[@aria-describedby='mainGrdLocation_submittedBy']");
			hs.put("submitDateTableValue", "xpath#//td[@aria-describedby='mainGrdLocation_MSISDN' and contains(@title, '"+value+"')]//following-sibling::td[@aria-describedby='mainGrdLocation_submittedDate']");
			hs.put("networkAcsTableValue", "xpath#//td[@aria-describedby='mainGrdLocation_MSISDN' and contains(@title, '"+value+"')]//following-sibling::td[@aria-describedby='mainGrdLocation_drpNAM']");
			hs.put("retailLtnTableValue", "xpath#//td[@aria-describedby='mainGrdLocation_MSISDN' and contains(@title, '"+value+"')]//following-sibling::td[@aria-describedby='mainGrdLocation_retainLocation']");
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}

}
