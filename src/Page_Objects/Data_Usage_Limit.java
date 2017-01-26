package Page_Objects;

import java.util.Hashtable;

import wrappers.FunctionLibrary;

public class Data_Usage_Limit extends FunctionLibrary{

	public synchronized String Data_Usage_Limit_Page(String locator){

		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();
			hs.put("Load_Type", "id#loadType");
			hs.put("Load_Parameter", "id#loadParameter");
			hs.put("Load_Subscriber", "id#btnLoadSubscriber");
			hs.put("Menu_Items", "xpath#//a[@class='menu-icon dropdown-toggle']");
			hs.put("Bundle_Menu", "xpath#//a[@class='catgLinkID']//span[@class='bundles-menu-image']");
			hs.put("Data_Usage_Limit", "xpath#//div[@class='accounts-thumbnail']//div[@class='thumbnail-icon']");
			hs.put("Home_Financial_Limit", "id#HomeFinLimit");
			hs.put("Home_Data_Limit", "id#HomeDataLimit");
			hs.put("Home_Data_Limit_Dropdown", "id#ddHome_DataLimit");
			hs.put("Roam_Financial_Limit", "id#RoamFinLimit");
			hs.put("Roam_Data_Limit", "id#RoamDataLimit");
			hs.put("Roam_Data_Limit_Dropdown", "id#ddRoam_DataLimit");
			hs.put("Home_Network", "id#ddHome");
			hs.put("Roam_Network", "id#ddRoam");
			hs.put("Submit", "id#btnSubmit");
			hs.put("Clear", "id#btnCancel");
			hs.put("Confirm_Message", "id#DataUsageErrorMsg");
			hs.put("Home_Financial_Error", "id#HomeFinLimit-error");
			hs.put("Roam_Data_Limit_Error", "id#RoamDataLimit-error");
			hs.put("close_button", "xpath#//li[@title='Subscriber Logout']");
			return hs.get(locator);
		}catch(Exception e){
			log.info("Error occurred in POM classes :"+e);
			return null;
		}

	}

}
