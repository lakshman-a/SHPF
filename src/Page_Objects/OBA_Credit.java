package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class OBA_Credit extends FunctionLibrary {

	public synchronized String obaCreditPage(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();

			hs.put("bundleButton", "xpath#//a[@title='Bundle']");
			hs.put("obaCreditBtn", "xpath#//div[@title='OBA Credit Payment']");
			hs.put("bundleDataTbl", "id#tblBundleData");
			hs.put("noRecords", "xpath#//div[@id='dvBundleData']/b");
			hs.put("bundleName", "id#hdnBundleName");
			hs.put("expiryDate", "xpath#//input[@id='hdnBundleName']/parent::th/following-sibling::th[1]");
			hs.put("autoRenew", "xpath#//input[@id='hdnBundleName']/parent::th/following-sibling::th[2]");
			hs.put("mode", "xpath#//input[@id='hdnBundleName']/parent::th/following-sibling::th[3]");
			hs.put("allocated", "xpath#//input[@id='hdnBundleName']/parent::th/following-sibling::th[4]");
			hs.put("remaining", "xpath#//input[@id='hdnBundleName']/parent::th/following-sibling::th[5]");
			hs.put("consumed", "xpath#//input[@id='hdnBundleName']/parent::th/following-sibling::th[6]");
			hs.put("activateBtn", "id#aOBARenewal");
			hs.put("renewalChange", "xpath#//button[@id='closeRenewalChangeModal']/following-sibling::h4[@class='modal-title']");
			hs.put("radDeactiveBtn", "id#rdbDeActive");
			hs.put("radioActiveBtn", "id#rdbActive");
			hs.put("renSubmit", "id#btnRenewalSubmit");
			hs.put("confirmMsg", "xpath#//div[@id='obaMsg' and contains(@style, 'rgb')]");
			hs.put("errorMsg", "id#errMsg");
			//Cancel Bundle
			hs.put("payNow", "id#btnPayNow");
			hs.put("cancelBunButton", "id#btnCnlBundle");
			hs.put("selectCncBun", "id#ddlCancelBundle");
			hs.put("submitCncBun", "id#btnSubmitCnlBundle");
			hs.put("cancelBunConfirmMsg", "xpath#//div[@id='obaMsg' and contains(text(), 'OBA Contract')]");
			//Bundle Upgrade
			hs.put("bundleUpgrade", "id#btnUpgradeNow");
			hs.put("bunUpgRadioBtn", "id#rdbUpgradePlan");
			hs.put("bunUpgradeDD", "id#ddlBundles");
			//hs.put("bunUpgradeSubmit", "xpath#//button[@id='btnUpgradeSubmit']");
			hs.put("bunUpgradeSubmit", "id#btnUpgradeSubmit");
			//Bundle Credit
			hs.put("bunCreditRadioBtn", "id#rdbAddCreditOption");
			hs.put("bunCreditAmt", "id#txtAOR");
			//POM for Credit card details
			hs.put("payNewCard", "id#inlineRadio1");
			hs.put("cardType", "id#cardType");
			hs.put("cardName", "id#txtNameOncard");
			hs.put("card1", "id#paymentCRM_cardNumber1");
			hs.put("card2", "id#paymentCRM_cardNumber2");
			hs.put("card3", "id#paymentCRM_cardNumber3");
			hs.put("card4", "id#paymentCRM_cardNumber4");
			hs.put("expiryDateCard", "id#paymentCRM_expiryDate");
			hs.put("cvv", "id#paymentCRM_cvvNumber");
			hs.put("emailTxt", "id#txtEmail");
			hs.put("ccPayButton", "id#btnPayment");
			hs.put("cardRenewal", "id#chkDefaultCard");
			hs.put("ccConfirmMsg", "xpath#//div[@id='obaMsg' and contains(text(), 'Success')]");
			hs.put("UKCountry", "xpath#//div[@id='countryName']/strong");
			hs.put("postCode", "id#postCode");
			hs.put("search", "xpath#//a[@id='btnFindAddress']/span");
			hs.put("addressList", "id#lstAddressResult");
			hs.put("addressAccept", "xpath#//a[@id='btnAcceptAddress']/span");
			hs.put("houseNo", "id#houseNumber");
			hs.put("stName", "id#streetName");
			hs.put("cityName", "id#cityName");
			hs.put("apartmentNo", "id#apartmentNo");
			hs.put("successLoadIcon", "id#btnrotate");
			hs.put("stateName", "id#stateName");
			hs.put("submit", "id#btnSubmit");
			return hs.get(locator);
		}catch(Exception e){
			return null;
		}
	}

}
