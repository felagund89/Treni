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

public class Treno {

	private Integer codiceTreno;

	private Short oraArrivo;

	private Short oraPartenza;

	private List<Tratta> tratte = new ArrayList<Tratta>();

	private List<Stazione> tratta = new ArrayList<Stazione>();

	public Treno() {

	}

	// Se serve
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof Treno)) {
			return false;
		}

		Treno t = (Treno) obj;
		return t.getCodiceTreno().equals(this.codiceTreno);
	}

	public Integer getCodiceTreno() {
		return codiceTreno;
	}

	public void setCodiceTreno(Integer codiceTreno) {
		this.codiceTreno = codiceTreno;
	}

	public Short getOraArrivo() {
		return oraArrivo;
	}

	public void setOraArrivo(Short oraArrivo) {
		this.oraArrivo = oraArrivo;
	}

	public Short getOraPartenza() {
		return oraPartenza;
	}

	public void setOraPartenza(Short oraPartenza) {
		this.oraPartenza = oraPartenza;
	}

	public List<Stazione> getTratta() {
		return tratta;
	}

	public void setTratta(List<Stazione> tratta) {
		this.tratta = tratta;
	}

	public List<Tratta> getTratte() {
		return tratte;
	}

	public void setTratte(List<Tratta> tratte) {
		this.tratte = tratte;
	}

}
