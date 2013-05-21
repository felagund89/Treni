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

	private Long TestCaseAppartenenza;

	private List<Tratta> tratte = new ArrayList<Tratta>();

	private int codiceStazione;

	private Long numeroTreni;

	private Integer peso;

	public Stazione() {

	}

	/**
	 * @return the testCaseAppartenenza
	 */
	public Long getTestCaseAppartenenza() {
		return TestCaseAppartenenza;
	}

	public List<Tratta> getTratte() {
		return tratte;
	}

	/**
	 * @param testCaseAppartenenza
	 *            the testCaseAppartenenza to set
	 */
	public void setTestCaseAppartenenza(Long testCaseAppartenenza) {
		TestCaseAppartenenza = testCaseAppartenenza;
	}

	/**
	 * @return the codiceStazione
	 */
	public int getCodiceStazione() {
		return codiceStazione;
	}

	/**
	 * @param codiceStazione
	 *            the codiceStazione to set
	 */
	public void setCodiceStazione(int codiceStazione) {
		this.codiceStazione = codiceStazione;
	}

	/**
	 * @return the numeroTreni
	 */
	public Long getNumeroTreni() {
		return numeroTreni;
	}

	/**
	 * @param numeroTreni
	 *            the numeroTreni to set
	 */
	public void setNumeroTreni(Long numeroTreni) {
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
