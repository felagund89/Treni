/**
 * 
 */
package project.treni;

import java.util.ArrayList;
import java.util.List;

/**
 * @author antonio
 * 
 */
public class Stazione {

	private Integer TestCaseAppartenenza;

	private List<Tratta> tratte = new ArrayList<Tratta>();

	private Integer codiceStazione;

	private Integer numeroTreni;

	private Integer peso;

	public Stazione() {

	}

	/**
	 * @return the testCaseAppartenenza
	 */
	public Integer getTestCaseAppartenenza() {
		return TestCaseAppartenenza;
	}

	public List<Tratta> getTratte() {
		return tratte;
	}

	/**
	 * @param testCaseAppartenenza
	 *            the testCaseAppartenenza to set
	 */
	public void setTestCaseAppartenenza(Integer testCaseAppartenenza) {
		TestCaseAppartenenza = testCaseAppartenenza;
	}

	/**
	 * @return the codiceStazione
	 */
	public Integer getCodiceStazione() {
		return codiceStazione;
	}

	/**
	 * @param codiceStazione
	 *            the codiceStazione to set
	 */
	public void setCodiceStazione(Integer codiceStazione) {
		this.codiceStazione = codiceStazione;
	}

	/**
	 * @return the numeroTreni
	 */
	public Integer getNumeroTreni() {
		return numeroTreni;
	}

	/**
	 * @param numeroTreni
	 *            the numeroTreni to set
	 */
	public void setNumeroTreni(Integer numeroTreni) {
		this.numeroTreni = numeroTreni;
	}

	/**
	 * @return the peso
	 */
	public Integer getPeso() {
		return peso;
	}

	/**
	 * @param peso
	 *            the peso to set
	 */
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
}
