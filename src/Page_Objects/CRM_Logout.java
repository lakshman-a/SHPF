package Page_Objects;

import java.util.Hashtable;

public class CRM_Logout {

	public synchronized String Logout_Page(String locator){
		try{
			Hashtable<String, String> hs = new Hashtable<String, String>();			
			hs.put("menu_Dropdown", "id#dropdownMenu1");
			hs.put("logout_Button", "id#logoutCRM");
			return hs.get(locator);
		}catch(Exception e){
			System.out.println("Error occurred in POM classes :"+e);
			return null;
		}

	}

}
