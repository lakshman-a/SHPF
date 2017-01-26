package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class EU_Configurations extends FunctionLibrary {

	public synchronized String euConfigurationsPage(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("simButton", "xpath#//a[@title='Sim']");
			hs.put("euConfigButton", "xpath#//div[@title='EU Configurations']");
			hs.put("viewUsage", "id#btnViewUsagetEU");
			hs.put("avalAmountText", "xpath#//b[text()='Available Amount']");
			hs.put("availAmount", "id#euAvailAmount");

			hs.put("thresLimit", "xpath#//input[@id='ueThrusAmount']");
			hs.put("submit", "id#btnSubmitEU");
			hs.put("reset", "id#btnResetEU");
			hs.put("confirmMsg", "xpath#//b[@id='configEuMessage' and contains(@class, 'true')]");
			hs.put("confirmMsgId", "id#configEuMessage");
			hs.put("confirmMsgXpath", "xpath#//b[@id='configEuMessage' and contains(@class, 'false')]");
			hs.put("history", "id#btnHistoryEU");
			hs.put("fromDate", "id#txtFDate");
			hs.put("toDate", "id#txtToDate");
			hs.put("view", "id#btnEDRBill");

			hs.put("validMsg", "id#edrHistMessage");
			hs.put("valthresLimit", "xpath#//input[@id='ueThrusAmount' and contains(@aria-required, 'true')]");
			hs.put("valFromDate", "xpath#//input[@id='txtFDate' and contains(@aria-required, 'true')]");
			hs.put("valToDate", "xpath#//input[@id='txtToDate' and contains(@aria-required, 'true')]");
			hs.put("selectYear", "xpath#//select[@class='ui-datepicker-year']");
			hs.put("selectMonth", "xpath#//select[@class='ui-datepicker-month']");
			hs.put("selectDate", "xpath#//a[text()='2']");

			hs.put("crdTypeID", "id#jqgh_edrTable_CDRType");
			hs.put("euTypeID", "xpath#//td[@title='107']");
			hs.put("closeButton", "xpath#(//div[@class='modal-header']//following-sibling::div[@class='modal-footer'])[5]//button");
			return hs.get(locator);

		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}

}
