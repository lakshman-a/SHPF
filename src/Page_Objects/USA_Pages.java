package Page_Objects;

import java.util.Hashtable;

import utils.ReadExcel;
import wrappers.FunctionLibrary;

public class USA_Pages extends FunctionLibrary{

	public synchronized String RetrieveTestDataValue(String filePath,String compName,String strColumnName,String gblrecordsCounter,int strExecEventFlag) throws Exception{
		String strData=null;
		ReadExcel readExcel=new ReadExcel(filePath);
		if(strExecEventFlag!=0){
			strData=readExcel.getCellData(compName, strColumnName, Integer.parseInt(gblrecordsCounter));
		}
		return strData;
	}

	public synchronized String ChangePlan(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("services_Select", "id#loadType");
			hs.put("mobileNo_Textbox", "id#loadParameter");
			hs.put("loadSubscriber_Button", "id#btnLoadSubscriber");
			hs.put("staff_MenuDropdown", "xpath#//a[@class='menu-icon dropdown-toggle']");
			hs.put("Bundle_Icon", "xpath#//a[@class='catgLinkID' and @title='Bundle']//span");
			hs.put("ChangePlan_Icon", "xpath#//div[@title='Change Plan']/following::*");
			hs.put("currentPlan", "id#currentPlan");
			hs.put("disabledcurrentPlan", "xpath#//input[@id='currentPlan' and @disabled='disabled']");
			hs.put("ddlnewPlan", "id#ddlnewPlan");
			hs.put("reason", "id#reason");
			hs.put("btnSubmit", "id#btnSubmit");
			//admin to submit request
			hs.put("btnChangeplanAccept", "id#btnChangeplanAccept");
			hs.put("btnChangeplanReset", "id#btnChangeplanReset");
			//Admin to approve/Reject:
			hs.put("btnChangeplanApprove", "id#btnChangeplanApprove");
			hs.put("btnChangeplanReject", "id#btnChangeplanReject");
			//Reject Reason Box:
			hs.put("rejectReason_Id", "id#rejectReason_Id");
			hs.put("planChngeHistory", "id#planChngeHistory");
			hs.put("responseMessage", "xpath#//label[@id='responseMessage' and (@class='true' or @class='false')]");
			hs.put("disabledMessage", "xpath#//label[contains(@style,'color')]");
			hs.put("close_button", "xpath#//li[@title='Subscriber Logout']");
			//Approval
			hs.put("pendingApproval_Body_Type", "xpath#//b[contains(text(),'Type')]");
			hs.put("linkChangePlan", "linktext#Change Plan");
			//Full view
			hs.put("DOB_Icon", "xpath#//li[@title='Subscriber View']");
			hs.put("lbl_Plan", "xpath#//strong//following::*//span[contains(text(),'AUTO')]");
			//History table:
			hs.put("tbl_ChangePlanHistory", "xpath#//div[@class='bundle-open1 changeplanHistory']//table");
			hs.put("btn_OkPlanHistory", "id#OkPlanHistory");
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}

	public synchronized String Pending_MSISDN(String filePath,String compName,String gblrecordsCounter, String locator,String strMSISDN,int strEventFlag) throws Exception{
		String MSISDN=null;
		try{
			MSISDN =RetrieveTestDataValue(filePath, compName, strMSISDN,gblrecordsCounter, 1);
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("strPendingMSISDN", "xpath#//td[@aria-describedby='pendingItemTable_Msisdn']//a[@title='"+MSISDN+"']");
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}

	public synchronized String ChangePlanHistoryTable(String filePath,String compName,String gblrecordsCounter, String locator,String rowNumber,int strEventFlag) throws Exception{

		String rowNumberInTable=null;
		try{
			rowNumberInTable =RetrieveTestDataValue(filePath, compName, rowNumber,gblrecordsCounter, 1);
			Hashtable<String, String> hs = new Hashtable<String, String>();

			String rowType=null;
			if(rowNumberInTable.equalsIgnoreCase("header")||rowNumberInTable.equalsIgnoreCase("head")||rowNumberInTable.equalsIgnoreCase("primary")){
				rowType="th";
				rowNumberInTable="1";
			}else{
				rowType="td";
			}
			hs.put("tblChangePlan_Date", "xpath#//div[@class='bundle-open1 changeplanHistory']//table//tr["+rowNumberInTable+"]//"+rowType+"[1]");
			hs.put("tblChangePlan_Time", "xpath#//div[@class='bundle-open1 changeplanHistory']//table//tr["+rowNumberInTable+"]//"+rowType+"[2]");
			hs.put("tblChangePlan_CurrentPlan", "xpath#//div[@class='bundle-open1 changeplanHistory']//table//tr["+rowNumberInTable+"]//"+rowType+"[3]");
			hs.put("tblChangePlan_PreviousPlan", "xpath#//div[@class='bundle-open1 changeplanHistory']//table//tr["+rowNumberInTable+"]//"+rowType+"[4]");
			hs.put("tblChangePlan_Channel", "xpath#//div[@class='bundle-open1 changeplanHistory']//table//tr["+rowNumberInTable+"]//"+rowType+"[5]");
			hs.put("tblChangePlan_Reason", "xpath#//div[@class='bundle-open1 changeplanHistory']//table//tr["+rowNumberInTable+"]//"+rowType+"[6]");
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}
}
