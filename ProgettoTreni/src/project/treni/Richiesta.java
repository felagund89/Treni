/**
 * 
 */
package project.treni;

import java.util.ArrayList;
import java.util.List;

/**
 * @author user
 *
 */
public class Richiesta {

	private long codTestCase;
	
	private List<String> richiesteTestCase = new ArrayList<String>();

	public long getCodTestCase() {
		return codTestCase;
	}

	public void setCodTestCase(long codTestCase) {
		this.codTestCase = codTestCase;
	}

	public List<String> getRichiesteTestCase() {
		return richiesteTestCase;
	}

	public void setRichiesteTestCase(List<String> richiesteTestCase) {
		this.richiesteTestCase = richiesteTestCase;
	}
	
	
	
	
	
}
