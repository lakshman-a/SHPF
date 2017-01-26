package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class Balance_Transfer extends FunctionLibrary {

	public synchronized String balanceTransferPage(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();

			hs.put("simButton", "xpath#//a[@title='Sim']");
			hs.put("balance_Tns_Btn", "xpath#//div[@class='balance-transfer-thumbnail']//div[@class='thumbnail-icon']");
			hs.put("toMSISDN", "id#toMSISDN");
			hs.put("ticketID", "id#ticketID");
			hs.put("reason", "id#ticketReason");
			hs.put("comments", "id#ticketComment");
			hs.put("approve", "id#btnApprove");
			hs.put("submit", "id#btnSubmit");

			hs.put("reset", "id#btnPendingReset");
			hs.put("confirm_Message", "id#lblbBalTransMessage");

			hs.put("validationMsg", "id#lblresultdiv");
			hs.put("radio_Partial", "id#rdopartial");
			hs.put("Transfer_Amount", "id#txtamount");
			hs.put("Partial_Balance_Reason", "id#PartialTransferReason");
			//Validations
			hs.put("valTransferAmount", "xpath#//input[@id='txtamount' and contains(@aria-required, 'true')]");
			hs.put("valPartialBalanceReason", "xpath#//select[@id='PartialTransferReason' and contains(@aria-required, 'true')]");
			hs.put("valToMSISDN", "xpath#//input[@id='toMSISDN' and contains(@aria-required, 'true')]");
			hs.put("valReason", "xpath#//select[@id='ticketReason' and contains(@aria-required, 'true')]");
			hs.put("valComments", "xpath#//textarea[@id='ticketComment' and contains(@aria-required, 'true')]");
			hs.put("validationMsgAlert", "xpath#//span[@id='lblresultdiv' and contains(@style, 'color')]");
			return hs.get(locator);

		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
		}
		return null;
	}

}
