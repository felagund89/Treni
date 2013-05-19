/**
 * 
 */
package project.treni;

import java.io.File;
import java.io.FileNotFoundException;

import project.treni.util.ParserFileInput;

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
		// String path = {"/src/input.txt"};
		File file = new File("input.txt");
		ParserFileInput parse = new ParserFileInput(file);

	}

}
