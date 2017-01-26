package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class USA_Family_Plan extends FunctionLibrary {

	public synchronized String usaFamilyPlanPage(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("bundleButton", "xpath#//a[@title='Bundle']");
			hs.put("mng_FamilyPlan", "xpath#//div[@title='Manage Family Plan']");
			hs.put("parentMSISDN", "xpath#//strong[text()='Parent MSISDN']");
			hs.put("addButton", "id#bundleToggler");
			hs.put("childMSISDN", "id#txtMSISDN");
			hs.put("mainBal_RadioBtn", "id#rdbtnMainBalance");
			hs.put("CC_RadioBtn", "id#rdbtnCreditCard");
			hs.put("cardType", "id#cardType");
			hs.put("newCard", "id#inlineRadio1");
			hs.put("txtNameOncard", "id#txtNameOncard");
			hs.put("card1", "id#paymentCRM_cardNumber1");
			hs.put("card2", "id#paymentCRM_cardNumber2");
			hs.put("card3", "id#paymentCRM_cardNumber3");
			hs.put("card4", "id#paymentCRM_cardNumber4");
			hs.put("expiryDate", "id#paymentCRM_expiryDate");
			hs.put("cvv", "id#paymentCRM_cvvNumber");
			hs.put("USACountry", "xpath#//div[@id='countryName']/strong");
			hs.put("houseNo", "id#houseNumber");
			hs.put("stName", "id#streetName");
			hs.put("cityName", "id#cityName");
			hs.put("apartmentNo", "id#apartmentNo");
			hs.put("postCode", "id#postCode");
			hs.put("successLoadIcon", "id#btnrotate");
			hs.put("addressList", "id#lstAddressResult");
			hs.put("addressAccept", "xpath#//a[@id='btnAcceptAddress']/span");
			hs.put("stateName", "id#stateName");
			hs.put("emailTxt", "id#txtEmail");
			hs.put("search", "xpath#//a[@id='btnFindAddress']/span");
			hs.put("submit", "id#btnSubmit");
			hs.put("reset", "id#btnReset");
			hs.put("childCon_Msg", "id#AmountMsg");
			hs.put("confirmMsg", "id#ErrorMsgPurchase");
			hs.put("swapButton", "xpath#//a[@class='btnAction']//following::img[@title='Swap']");
			hs.put("swapYesBtn", "id#SwapYes");
			hs.put("swapSucessMsg", "id#swapsucess");
			hs.put("swapSucessOk", "id#btnHome");
			hs.put("deleteChild", "xpath#//img[@title='Delete']");
			hs.put("deleteConMsg", "xpath#//button[@id='No']//ancestor::div[@class='modal-content']//div[@class='modal-body']//p");
			hs.put("deleteYesBtn", "id#Yes");
			hs.put("childConfirmMsg", "id#ErrorMsg");
			hs.put("10DigitMSISDN", "id#txtMSISDN-error");
			hs.put("valChildMSISDN", "xpath#//input[@id='txtMSISDN' and contains(@aria-required, 'true')]");
			return hs.get(locator);

		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}
}
