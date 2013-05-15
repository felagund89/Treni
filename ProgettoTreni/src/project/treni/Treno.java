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

	private Long codiceTreno;
	
	private Long oraArrivo;
	
	private Long oraPartenza;
	
	private List<Stazione> tratta = new ArrayList<Stazione>();

	
	public Treno(){
		
		
	}
	
	
	
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
	  
	
	
	
	
	public Long getCodiceTreno() {
		return codiceTreno;
	}

	public void setCodiceTreno(Long codiceTreno) {
		this.codiceTreno = codiceTreno;
	}

	public Long getOraArrivo() {
		return oraArrivo;
	}

	public void setOraArrivo(Long oraArrivo) {
		this.oraArrivo = oraArrivo;
	}

	public Long getOraPartenza() {
		return oraPartenza;
	}

	public void setOraPartenza(Long oraPartenza) {
		this.oraPartenza = oraPartenza;
	}

	public List<Stazione> getTratta() {
		return tratta;
	}

	public void setTratta(List<Stazione> tratta) {
		this.tratta = tratta;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
