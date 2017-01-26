package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class Bundle_Purchase extends FunctionLibrary{

	public synchronized String Bundle_Purchase_Page(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("Load_Type", "id#loadType");
			hs.put("Load_Parameter", "id#loadParameter");
			hs.put("Load_Subscriber", "id#btnLoadSubscriber");
			hs.put("Menu_Items", "xpath#//a[@class='menu-icon dropdown-toggle']");
			hs.put("Bundle_Menu", "xpath#//a[@class='catgLinkID']//span[@class='bundles-menu-image']");
			hs.put("Bundle_Purchase", "xpath#//div[@class='purchase-bundle-thumbnail']//div[@class='thumbnail-icon']");
			hs.put("Bundle_Name", "id#bundleName");
			hs.put("Bundle_Months", "id#bundleMonths");
			hs.put("Bundle_Amount", "id#bundleAmount");
			hs.put("Bundle_Category", "id#bundleType");
			hs.put("Bundle_Description", "id#bundleDesc");
			hs.put("Auto_Renewal", "id#chkAutoRenewal");
			hs.put("Query_Bundle", "id#btnCalcBundleCost");
			hs.put("Reset_Bundle", "id#btnResetBundle");
			hs.put("Query_Bundle_Response", "id#discountMsg");
			hs.put("Submit", "id#btnSubmitBundle");
			hs.put("Alert_Message", "xpath#.//*[@id='bundlePurchaseForm']//div[contains(@class,'errorMessage')]");
			hs.put("Bundle_Alert_Message", "xpath#.//*[@id='bundleBuyLoad']//div[contains(@class,'errorMessage')]");
			hs.put("Bundle_Purchase_Message", "id#dvBundleRespeMsg");
			hs.put("Total_Minutes", "id#totalMinsRecvd");
			hs.put("Total_SMS", "id#bundleSMS");
			hs.put("Onnet_SMS", "id#onNetSMS");
			hs.put("Offnet_SMS", "id#offNetSMS");
			hs.put("Onnet_Mins", "id#onNetCalls");
			hs.put("Offnet_Mins", "id#offNetCalls");
			hs.put("Bundle_Expiry_Date", "id#bundleExpiry");
			hs.put("Pre_Receiver_MSISDN", "id#preReceiverMSISDN");
			hs.put("Avail_Promo_Button", "id#btnAvailPromo");
			hs.put("Edit_Promo_Code", "id#promoCode");
			hs.put("Apply_Promo_Button", "id#btnBundlePromo");

			hs.put("close_button", "xpath#//li[@title='Subscriber Logout']");
			return hs.get(locator);

		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
		}
		return null;
	}

}
