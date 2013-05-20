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

	private List<String> richiesteMinTempo = new ArrayList<String>();

	private List<String> richiesteMinOrari = new ArrayList<String>();

	private List<String> richiesteMinScambi = new ArrayList<String>();

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

	public List<String> getRichiesteMinTempo() {
		return richiesteMinTempo;
	}

	public void setRichiesteMinTempo(List<String> richiesteMinTempo) {
		this.richiesteMinTempo = richiesteMinTempo;
	}

	public List<String> getRichiesteMinOrari() {
		return richiesteMinOrari;
	}

	public void setRichiesteMinOrari(List<String> richiesteMinOrari) {
		this.richiesteMinOrari = richiesteMinOrari;
	}

	public List<String> getRichiesteMinScambi() {
		return richiesteMinScambi;
	}

	public void setRichiesteMinScambi(List<String> richiesteMinScambi) {
		this.richiesteMinScambi = richiesteMinScambi;
	}

}
