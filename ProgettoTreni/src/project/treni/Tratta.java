/**
 * 
 */
package project.treni;

/**
 * @author user
 * 
 */
public class Tratta {

	Treno treno;

	Stazione staz;

	long oraArr;

	long oraPart;

	long tempoPercorrenza;

	public long getOraArr() {

		return oraArr;
	}

	public void setOraArr(long oraArr) {
		this.oraArr = oraArr;
	}

	public long getOraPart() {
		return oraPart;
	}

	public void setOraPart(long oraPart) {
		this.oraPart = oraPart;
	}

	public Stazione getStaz() {
		return staz;
	}

	public void setStaz(Stazione staz) {
		this.staz = staz;
	}

	public Treno getTreno() {
		return treno;
	}

	public void setTreno(Treno treno) {
		this.treno = treno;
	}

	public long getTempoPercorrenza() {
		return tempoPercorrenza;
	}

	public void setTempoPercorrenza(long tempoPercorrenza) {
		this.tempoPercorrenza = tempoPercorrenza;
	}

}
