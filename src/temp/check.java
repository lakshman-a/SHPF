package temp;


public class check {

	 public static void main(String args[])
	    {
	        try {
	        	Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
	            System.out.println("done");
	        } catch (Throwable e) {
	            System.err.println(e);
	        }
	    }
	 
}
