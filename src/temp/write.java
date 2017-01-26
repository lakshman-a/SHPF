package temp;


import java.util.Properties;

public class write {

	public static void main(String[] args) {
		
		Properties Runtimevalue=new Properties();
		
		Runtimevalue.setProperty("name", "Lakshman");
		
		Runtimevalue.setProperty("age", "25");
		
		System.out.println(Runtimevalue.getProperty("name"));
		
		
		Runtimevalue.clear();
		
		System.out.println("Done");
		
	}
	
}
