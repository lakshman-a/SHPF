package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class Voucher_TopUp_Page extends FunctionLibrary {

	public synchronized String Voucher_TopUp(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("services_Select", "id#loadType");
			hs.put("mobileNo_Textbox", "id#loadParameter");
			hs.put("loadSubscriber_Button", "id#btnLoadSubscriber");
			hs.put("staff_MenuDropdown", "xpath#//a[@class='menu-icon dropdown-toggle']");
			hs.put("topUp_Button", "xpath#//a[@class='catgLinkID']//span[@class='topup-menu-image']");
			hs.put("voucher_TopUp_Button", "xpath#//div[@class='voucher-topup-thumbnail']//div[@class='thumbnail-icon']");
			hs.put("voucherPin_Textbox", "id#voucherCode");
			hs.put("apply_Button", "id#btnVoucherTopup");
			hs.put("reset_Button", "id#btnResetVoucherTopup");
			hs.put("success_message", "xpath#.//div[@id='dvVoucherDetails']//b");
			hs.put("invalidPin_message", "xpath#//div[contains(text(), 'invalid')]");
			hs.put("New_Balance", "xpath#//Strong[contains(text(),'New Balance')]/parent::div/following-sibling::div");
			hs.put("Old_Balance", "xpath#//Strong[contains(text(),'Old Balance')]/parent::div/following-sibling::div");
			hs.put("Main_Balance", "xpath#//Strong[text()='Main Balance']/parent::div/following-sibling::div");
			hs.put("ValidityDate", "xpath#//Strong[text()='Validity Date']/parent::div/following-sibling::div");
			hs.put("PromoBalance", "xpath#//Strong[text()='Promo Balance']/parent::div/following-sibling::div");
			hs.put("PromoValidityDate", "xpath#//Strong[text()='Promo Validity Date']/parent::div/following-sibling::div");
			hs.put("FreeData", "xpath#//Strong[text()='Free Data']/parent::div/following-sibling::div");
			hs.put("FreeDataExpireDate", "xpath#//Strong[text()='Free Data Expire Date']/parent::div/following-sibling::div");
			hs.put("FaceValue", "xpath#//Strong[text()='Face Value / Topup Amount']/parent::div/following-sibling::div");
			hs.put("OnNetMinutes", "xpath#//td[normalize-space(text())='ON Net']/following::td[1]");
			hs.put("OnNetSMS", "xpath#//td[normalize-space(text())='ON Net']/following::td[2]");
			hs.put("MTOnNetMinutes", "xpath#//td[normalize-space(text())='MT ON Net']/following::td[1]");
			hs.put("MTOnNetSMS", "xpath#//td[normalize-space(text())='MT ON Net']/following::td[2]");
			hs.put("MTOffNetMinutes", "xpath#//td[normalize-space(text())='MT Off-Net']/following::td[1]");
			hs.put("MTOffNetSMS", "xpath#//td[normalize-space(text())='MT Off-Net']/following::td[2]");
			hs.put("TotalOffNetMinutes", "xpath#//td[normalize-space(text())='Total Off-Net']/following::td[1]");
			hs.put("TotalOffNetSMS", "xpath#//td[normalize-space(text())='Total Off-Net']/following::td[2]");
			hs.put("errorMessage", "xpath#.//*[@id='dvWorkArea']//div[normalize-space(@class)='errorMessage']");
			hs.put("errorMessage_1", "xpath#.//*[@id='dvVoucherDetails']//div[contains(@class,'errorMessage')]");
			hs.put("errorMessage_2", "xpath#.//div[@class='col-md-12 errorMessage']");
			hs.put("errorMessage_3", "id#resultdiv");
			hs.put("invalidVoucherError","xpath#.//*[@id='dvVoucherDetails']/div/div/div");
			hs.put("Reset_button", "id#btnResetVoucherTopup");
			hs.put("close_button", "xpath#//li[@title='Subscriber Logout']");
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}

	public synchronized String Voucher_Page_ResetObj(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("New_Balance", "//Strong[contains(text(),'New Balance')]/parent::div/following-sibling::div");
			hs.put("Old_Balance", "//Strong[contains(text(),'Old Balance')]/parent::div/following-sibling::div");
			hs.put("Main_Balance", "//Strong[text()='Main Balance']/parent::div/following-sibling::div");
			hs.put("ValidityDate", "//Strong[text()='Validity Date']/parent::div/following-sibling::div");
			hs.put("PromoBalance", "//Strong[text()='Promo Balance']/parent::div/following-sibling::div");
			hs.put("PromoValidityDate", "//Strong[text()='Promo Validity Date']/parent::div/following-sibling::div");
			hs.put("FreeData", "//Strong[text()='Free Data']/parent::div/following-sibling::div");
			hs.put("FreeDataExpireDate", "//Strong[text()='Free Data Expire Date']/parent::div/following-sibling::div");
			hs.put("FaceValue", "//Strong[text()='Face Value']/parent::div/following-sibling::div");
			hs.put("OnNetMinutes", "//td[normalize-space(text())='OnNet']//following-sibling::td[1]");
			hs.put("OnNetSMS", "//td[normalize-space(text())='OnNet']//following-sibling::td[2]");
			hs.put("MTOnNetMinutes", "//td[normalize-space(text())='MT OnNet']//following-sibling::td[1]");
			hs.put("MTOnNetSMS", "//td[normalize-space(text())='MT OnNet']//following-sibling::td[2]");	
			String locate = hs.get(locator);
			return locate;

		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}
}