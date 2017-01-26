package utils;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * <h1>CreateBatFile</h1>
 * 
 * <p>
 * <b>Note: Used to Create .bat files based on the TestNG Creation class</b> 
 * 
 * @author Lakshman A
 * @since DEC 29, 2016
 *
 */

public class CreateBatFile {

	static BufferedWriter bw = null;
	static FileWriter fw = null;

	/**
	 * <b>createTestNGBatFile</b><br>
	 * 
	 * <p><b>Objective : To only Create .bat file with the given filename<p></b>
	 * @author LAKSHMAN
	 * @since Nov 29, 2016
	 */

	public static void createTestNGBatFile(String fileName){
		try {
			fw = new FileWriter(fileName+".bat");
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * <b>writeTestNGBatFile</b><br>
	 * 
	 * <p><b>Objective : To write into the .bat file with the given filename<p></b>
	 * @author LAKSHMAN
	 * @since Nov 29, 2016
	 */

	public static void writeTestNGBatFile(String fileName, String content){

		try {
			fw = new FileWriter(fileName+".bat", true);
			bw = new BufferedWriter(fw);

			bw.write("java -cp \"JarFiles\\*;bin\" org.testng.TestNG "+content+"\n");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}



}



