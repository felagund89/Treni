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

	short oraArr;

	short oraPart;

	long tempoPercorrenza;

	public short getOraArr() {
		return oraArr;
	}

	public void setOraArr(short oraArr) {
		this.oraArr = oraArr;
	}

	public short getOraPart() {
		return oraPart;
	}

	public void setOraPart(short oraPart) {
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
