/**
 * 
 */
package project.treni;

import project.treni.util.ParserFileInput;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author antonio
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
//		String path = {"/home/user/Scrivania/Treni/src/project/treni/util/input.txt"};
        File file =  new File("project/treni/util/input.txt");
        ParserFileInput parse = new ParserFileInput(file);
       
	}

}
