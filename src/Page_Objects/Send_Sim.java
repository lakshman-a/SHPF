package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class Send_Sim extends FunctionLibrary{
	
public synchronized String Send_Sim_Page(String locator){
		
		try{
		Hashtable<String, String> hs = new Hashtable<String, String>();
		hs.put("Send_Sim", "id#btnSendSim");
		hs.put("Title", "id#ddtitle");
		hs.put("First_Name", "id#FirstName");
		hs.put("Last_Name", "id#LastName");
		hs.put("Date_Of_Birth", "id#txtDOB1");
		hs.put("Country", "id#countryName");
		hs.put("State", "id#State1");
		hs.put("Find_Address", "xpath#.//*[@id='btnFindAddress']/span");
		hs.put("Address_List", "id#lstAddressResult");
		hs.put("Accept_Address", "xpath#.//*[@id='btnAcceptAddress']/span");
		hs.put("Postcode", "id#postCode");
		hs.put("City", "id#cityName");
		hs.put("House_Number", "id#houseNumber");
		hs.put("Street", "id#streetName");
		hs.put("Apartment_Number", "id#apartmentNo");
		hs.put("Billing_Address", "xpath#.//*[@id='Chkbillingaddress']");
		hs.put("Billing_Country", "id#BillingCountry1");
		hs.put("Billing_State", "id#BillingState1");
		hs.put("Billing_Postcode", "id#BillingCode1");
		hs.put("Billing_City", "id#Billingcity1");
		hs.put("Billing_House_Number", "id#BillingLine11");
		hs.put("Billing_Street", "id#BillingLine12");
		hs.put("Billing_Apartment_Number", "id#billingapartmentno");
		hs.put("Email", "id#Email1");
		hs.put("Contact_Number", "id#ContactNo1");
		hs.put("Cancel_Personal_Details", "id#Cancel");
		hs.put("Next_Personal_Details", "id#btnNextCircleCircle");
		hs.put("Number_Of_Sims", "id#ddlSIM");
		hs.put("Sim_Type_1", "id#ddlSIMNos1");
		hs.put("Sim_Type_2", "id#ddlSIMNos2");
		hs.put("Sim_Type_3", "id#ddlSIMNos3");
		hs.put("Next_Sim_Type", "id#btnNextCircleCircle");
		hs.put("Confirm_Title", "id#lblTitle");
		hs.put("Confirm_First_Name", "id#lblFirstName");
		hs.put("Confirm_Last_Name", "id#lblLastName");
		hs.put("Confirm_Date_Of_Birth", "id#lblDOB");
		hs.put("Confirm_Country", "id#lblCountry");
		hs.put("Confirm_State", "id#lblState");
		hs.put("Confirm_Postcode", "id#lblCode");
		hs.put("Confirm_City", "id#lblCity");
		hs.put("Confirm_House_Number", "id#lblLine1");
		hs.put("Confirm_Street", "id#lblLine2");
		hs.put("Confirm_Apartment_Number", "id#lblApartmentNo");
		hs.put("Confirm_Billing_Country", "id#lblBillingCountry");
		hs.put("Confirm_Billing_State", "id#lblBillingState");
		hs.put("Confirm_Billing_Postcode", "id#lblBillingCode");
		hs.put("Confirm_Billing_City", "id#lblBillingCity");
		hs.put("Confirm_Billing_House_Number", "id#lblBillingLine1");
		hs.put("Confirm_Billing_Street", "id#lblBillingLine2");
		hs.put("Confirm_Billing_Apartment_Number", "id#lblBillingApartmentNo");
		hs.put("Confirm_Email", "id#lblEmail");
		hs.put("Confirm_Contact_Number", "id#lblHomeContactNo");
		hs.put("Next_Confirm_Page", "id#btnNextCircleCircle");
		hs.put("Alert_Message_Without_Credit", "id#divcardwithoutpay");
		hs.put("Next_Payments_Page", "id#btnNextCircleCircle");
		hs.put("Submit_Payments_Page", "id#btnSubmit");
		hs.put("Confirm_Message", "id#btnCircleSendSim");
		hs.put("Check_Payment", "id#chkpayment");
		hs.put("Sim_Plan", "id#ddlplanSIMs");
		hs.put("radio_Topup", "id#rdTopup");
		hs.put("radio_Bundle", "id#rdBundle");
		hs.put("radio_Addon", "id#rdAddon");
		hs.put("Topup_Type", "id#ddlTopuptype");
		hs.put("Topup_Value", "id#ddlTopupValue");
		hs.put("Topup_Submit", "id#btnTopupsubmit");
		hs.put("Bundle_Dropdown", "id#ddlPlanBundle");
		hs.put("Number_Of_Months", "id#txtNoofMonths");
		hs.put("Bundle_Submit", "id#btnplanSubmit");
		hs.put("Bundles_Table", "id#tblBundles");
		hs.put("Confirm_Bundles_Table", "id#tblConfBundles");
		hs.put("Total_Amount", "id#lblSendSImVatTotalAmt");
		hs.put("Card_Type", "id#cardType");
		hs.put("Name_On_Card", "id#txtNameOncard");
		hs.put("Card_Number_1", "id#paymentCRM_cardNumber1");
		hs.put("Card_Number_2", "id#paymentCRM_cardNumber2");
		hs.put("Card_Number_3", "id#paymentCRM_cardNumber3");
		hs.put("Card_Number_4", "id#paymentCRM_cardNumber4");
		hs.put("Expiry_Date", "id#paymentCRM_expiryDate");
		hs.put("CVV_Number", "id#paymentCRM_cvvNumber");
		hs.put("Amount", "id#lblSendSImAmt");
		hs.put("Transaction_Number", "id#lblSendSImTransactionNo");
		hs.put("Red_Order_Id", "id#lblSendSImRedOrderID");
		hs.put("val_Title", "xpath#//select[@id='ddtitle' and contains(@aria-required, 'true')]");
		hs.put("val_FirstName", "xpath#//input[@id='FirstName' and contains(@aria-required, 'true')]");
		hs.put("val_LastName", "xpath#//input[@id='LastName' and contains(@aria-required, 'true')]");
		hs.put("val_DOB", "xpath#//input[@id='txtDOB1' and contains(@aria-required, 'true')]");
		hs.put("val_State", "xpath#//input[@id='State1' and contains(@aria-required, 'true')]");
		hs.put("val_Postcode", "xpath#//input[@id='Code1' and contains(@aria-required, 'true')]");
		hs.put("val_City", "xpath#//input[@id='city1' and contains(@aria-required, 'true')]");
		hs.put("val_Houseno", "xpath#//input[@id='Line11' and contains(@aria-required, 'true')]");
		hs.put("val_Street", "xpath#//input[@id='Line12' and contains(@aria-required, 'true')]");
		hs.put("val_Email", "xpath#//input[@id='Email1' and contains(@aria-required, 'true')]");
		hs.put("val_Contact", "xpath#//input[@id='ContactNo1' and contains(@aria-required, 'true')]");
		hs.put("Addon_Dropdown", "id#ddlPlanAddOn");
		hs.put("Number_Of_Months_Addon", "id#INAtxtNoofMonths");
		hs.put("Addon_Submit", "id#btnplanAddOnSubmit");
		hs.put("Promo_Code", "id#txtSendSimPromoCode");
		hs.put("Apply_Promo", "id#btnSenssimPromo");
		hs.put("close_button", "xpath#//span[@class='close-icon']");
		//ADDED BY MURALI
		hs.put("IssueDate", "id#paymentCRM_IssueDate");
		hs.put("IssueNumber", "id#txtIssueNumber");
		hs.put("INA_Auto_Renewal", "id#chkAddOnAutoRenewal");
		hs.put("NA_Auto_Renewal", "id#chkplanAutoRenewal");
		return hs.get(locator);
		
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}
	}

}
