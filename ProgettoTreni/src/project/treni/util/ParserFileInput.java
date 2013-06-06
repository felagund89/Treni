/**
 * 
 */
package project.treni.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import project.treni.Richiesta;
import project.treni.Stazione;
import project.treni.Tratta;
import project.treni.Treno;

/**
 * @author antonio
 * 
 */

public class ParserFileInput {

	private static int numeroTestCase = 0;

	private static int numeroStazioni = 0;

	private static int numeroRichieste = 0;

	private static int contatore;

	private static int contatoreTreni;

	private static int iterazioniTotali;

	private static Map<Integer, Map> rete = new HashMap<Integer, Map>();

	private static Map<Integer, Stazione> stazioni = new HashMap<Integer, Stazione>();

	private static List<Tratta> tratte = new ArrayList<Tratta>(0);

	private static List<Treno> treni = new ArrayList<Treno>(0);

	private static List<Richiesta> richieste = new ArrayList<Richiesta>();

	public ParserFileInput(File file) throws FileNotFoundException {

		loadDati(file);

		correttezzaDati();

	}

	/**
	 * Metodo che carica i dati dal file Input.txt
	 * 
	 */

	public static void loadDati(File nomeFile) throws FileNotFoundException {

		Scanner f = new Scanner(nomeFile);

		// ricavo numero test case
		f.hasNextLine();

		numeroTestCase = (Integer.parseInt(f.nextLine()));

		System.out.println("numero test case" + numeroTestCase);

		// scorro dati nei vari testCase

		// System.out.println(f.nextLine());

		for (int i = 0; i < numeroTestCase; i++) {
			iterazioniTotali++;
			System.out.println(f.nextLine());

			numeroStazioni = (Integer.parseInt(f.nextLine()));

			System.out.println("numero staz" + numeroStazioni);

			for (int j = 0; j < numeroStazioni; j++) { // per i che va da 0 al
														// numero di stazioni
														// del test case
				iterazioniTotali++;

				String staz = f.nextLine();
				String datiStazione[] = staz.split(" ");

				int codStaz = Integer.parseInt(datiStazione[0]);
				int numTrenStaz = Integer.parseInt(datiStazione[1]);

				Stazione stazione = new Stazione();

				stazione.setCodiceStazione(codStaz);
				stazione.setNumeroTreni(numTrenStaz);

				contatore++;

				for (int x = 0; x < numTrenStaz; x++) { // scorro i treni della

					// stazione
					String lineaTreno = f.nextLine();
					// System.out.println("stringaTreno" + treno);

					String stringaTreno[] = lineaTreno.split(" ");

					int cod = Integer.parseInt(stringaTreno[0]);
					short orArr = Short.parseShort(stringaTreno[1]);
					short orPar = Short.parseShort(stringaTreno[2]);

					Treno treno = new Treno();
					treno.setCodiceTreno(cod);
					treno.setOraArrivo(orArr);
					treno.setOraPartenza(orPar);
					treni.add(treno);

					Tratta tratta = new Tratta();
					tratta.setStaz(stazione);
					tratta.setOraArr(orArr);
					tratta.setOraPart(orPar);
					tratta.setTreno(treno);
					
					stazione.getTratte().add(tratta);

					// Popolo la mappa
					TreeMap<Short, Tratta> tratte;
					if(rete.containsKey(treno.getCodiceTreno()))
						tratte = (TreeMap<Short, Tratta>) rete.get(treno.getCodiceTreno());
					else
						tratte = new TreeMap<Short, Tratta>();
					tratte.put(orArr, tratta);
					rete.put(treno.getCodiceTreno(), tratte);
				}

				stazione.setPeso(0);
				stazioni.put(stazione.getCodiceStazione(), stazione);
			}

			// System.out.println("--------------------------");
			// for(Map.Entry<Integer, Map> t : rete.entrySet()) {
			// 	System.out.println("Treno: " + t.getKey());
			// 	Map<Short, Tratta> l = t.getValue();
			// 	for(Map.Entry<Short, Tratta> e : l.entrySet()) {
			// 		Tratta c = e.getValue();
			// 		System.out.println("Stazione: " + c.getStaz().getCodiceStazione() + " - Arrivo: " + c.getOraArr() + " - Partenza: " + c.getOraPart());
			// 	}
			// 	System.out.println("--------------------------");
			// }

			numeroRichieste = Integer.parseInt(f.nextLine());
			System.out.println("numeroRichieste" + numeroRichieste);

			Richiesta ric = new Richiesta();
			ric.setCodTestCase(i + 1);
			for (int s = 0; s < numeroRichieste; s++) {
				iterazioniTotali++;
				String richiesta = f.nextLine();
				String stringaRichiesta[] = richiesta.split(" ");
				String tipoRic = stringaRichiesta[0];

				if (tipoRic.equals("MINTEMPO")) {
					ric.getRichiesteMinTempo().add(richiesta);
					// ATTENZIONE: Porcate sprovviste di segnaletica
					
					// A parte scherzi, questa roba è qui solo per comodità.
					// VA SPOSTATA OVVIAMENTE :)
					Stazione s1 = stazioni.get(Integer.parseInt(stringaRichiesta[1]));
					Stazione s2 = stazioni.get(Integer.parseInt(stringaRichiesta[2]));

					Map<Integer, Integer> dist = new HashMap<Integer, Integer>();
					for (Map.Entry<Integer, Stazione> e : stazioni.entrySet())
						dist.put(e.getKey(), Integer.MAX_VALUE);

					Map<Integer, Integer> p = new HashMap<Integer, Integer>();
					for (Map.Entry<Integer, Stazione> e : stazioni.entrySet())
						p.put(e.getKey(), 0);
					
					dist.put(s1.getCodiceStazione(), 0);
					
					p.put(s1.getCodiceStazione(), s1.getCodiceStazione());

					Map<Integer, Map> h = new HashMap<Integer, Map>(rete);
					
					Stazione v = s1;

					Short ora_corrente = -1;
					
					while (h.size() > 0) {

						Tratta tratta_scelta = null;
						Short peso_corrente = Short.MAX_VALUE;

						System.out.println();
						System.out.println("Stazione: " + v.getCodiceStazione());
						System.out.println("Ora corrente: " + ora_corrente);
						System.out.println("--------------------------");
						
						if(v == s2)
							System.exit(0);

						for(Tratta w : v.getTratte()) {

							System.out.println("Treno: " + w.getTreno().getCodiceTreno() + " - Arrivo: " + w.getOraArr() + " - Partenza: " + w.getOraPart());

							if(w.getOraPart() == -1)
								continue;

							Tratta tratta = (Tratta) h.get(w.getTreno().getCodiceTreno()).get(w.getOraArr());
							Map.Entry<Short, Tratta> nodo_prossima_tratta = ((TreeMap<Short, Tratta>) h.get(w.getTreno().getCodiceTreno())).ceilingEntry(tratta.getOraPart());

							Tratta prossima_tratta;
							if(nodo_prossima_tratta != null) {
								prossima_tratta = (Tratta) nodo_prossima_tratta.getValue();
							} else {
								prossima_tratta = (Tratta) ((TreeMap<Short, Tratta>) h.get(w.getTreno().getCodiceTreno())).firstEntry().getValue();
							}
						
							//calcolo il peso della stazione di arrivo
							Short peso;
							if(ora_corrente == -1) {
								if(prossima_tratta.getOraArr() > tratta.getOraPart()) {
									peso = (short)(prossima_tratta.getOraArr() - tratta.getOraPart());
								} else {
									peso = (short)(1439 - tratta.getOraPart() + prossima_tratta.getOraArr());
								}
							} else {
								if(prossima_tratta.getOraArr() > tratta.getOraPart()) {
									peso = (short)(prossima_tratta.getOraArr() - tratta.getOraPart());
								} else {
									peso = (short)(1439 - tratta.getOraPart() + prossima_tratta.getOraArr());
								}
								if(ora_corrente < tratta.getOraPart()) {
									peso = (short)(peso + (tratta.getOraPart() - ora_corrente));
								} else {
									peso = (short)(peso + (1439 - ora_corrente + tratta.getOraPart()));
								}
							}

							if(peso < peso_corrente) {
								peso_corrente = peso;
								tratta_scelta = w;
								v = prossima_tratta.getStaz();
								ora_corrente = prossima_tratta.getOraArr();
							}
						}

						System.out.println("--------------------------");
						System.out.println("[Tratta scelta] Treno: " + tratta_scelta.getTreno().getCodiceTreno() + " - Arrivo: " + tratta_scelta.getOraArr() + " - Partenza: " + tratta_scelta.getOraPart());
						System.out.println("--------------------------");

						h.get(tratta_scelta.getTreno().getCodiceTreno()).remove(tratta_scelta.getOraArr());
						// Se il treno non ha più tratte lo tolgo dalla rete
						if(h.get(tratta_scelta.getTreno().getCodiceTreno()).size() == 0)
							h.remove(tratta_scelta.getTreno().getCodiceTreno());
								
					//	System.out.println("Peso prossimaTratta "+tratta_scelta.getStaz().getPeso());
						//Bisogna scegliere la tratta giusta
					//	System.out.println("staz partenza"+tratta.getStaz().getCodiceStazione());
					//	System.out.println("Staz arr"+tratta_scelta.getStaz().getCodiceStazione());
					
						// TODO: una stazione gia visitata non deve essere rivisitata, bisogna aggiungere l intervallo di tempo trascorso nella stazione
						// TODO: si puo settare il peso stazione di default con il tempo trascorso tra arrivo e partenza.

						// - IF Dist[w] > Dist[v] + p(v, w) THEN
						if(dist.get(tratta_scelta.getStaz().getCodiceStazione()) > dist.get(v.getCodiceStazione()) + (v.getPeso() + tratta_scelta.getStaz().getPeso())) {
							// - Dist[w] <- Dist[v] + p(v, w)
							dist.put(tratta_scelta.getStaz().getCodiceStazione(), dist.get(v.getCodiceStazione()) + (v.getPeso() + tratta_scelta.getStaz().getPeso()));
							// - P[w] <- v
							p.put(tratta_scelta.getStaz().getCodiceStazione(), v.getCodiceStazione());
						}
					}

					System.out.println("Exit");

					Set<Integer> keySet = dist.keySet();
					for(Integer key:keySet){
					     Integer value = dist.get(key);
					     System.out.println(dist.get(dist.get(value)));
					}
//					System.out.println("dist -------------- " + dist.size());
//					System.out.println(dist);

//					System.out.println("p --------------" + p.size());
//					System.out.println(p);

					System.exit(0);
					// Fine porcate
				}
				if (tipoRic.equals("MINORARIO"))
					ric.getRichiesteMinOrari().add(richiesta);
				if (tipoRic.equals("MINCAMBI"))
					ric.getRichiesteMinScambi().add(richiesta);
				// ric.getRichiesteTestCase().add(f.nextLine());
			}

			richieste.add(ric);

		}
		f.close();
	}

	/**
	 * 
	 */
	public static void correttezzaDati() {
		System.out.println("Iterazioni totali di tutti i for: "
				+ iterazioniTotali);
		System.out.println("Stazioni salvate " + contatore);
		System.out.println("Treni salvati " + contatoreTreni);

		// stampa richieste
		// System.out.println("Richieste:");
		// for (int n = 0; n < richieste.size(); n++) {
		// for (int m = 0; m < richieste.get(n).getRichiesteMinTempo().size();
		// m++)
		// System.out.println("richiesta min tempo:"
		// + richieste.get(n).getRichiesteMinTempo().get(m));
		// for (int m = 0; m < richieste.get(n).getRichiesteMinOrari().size();
		// m++)
		// System.out.println("richiesta min orario:"
		// + richieste.get(n).getRichiesteMinOrari().get(m));
		// for (int m = 0; m < richieste.get(n).getRichiesteMinScambi().size();
		// m++)
		// System.out.println("richiesta min scambi:"
		// + richieste.get(n).getRichiesteMinScambi().get(m));
		//
		// }

		// Stampo la tratta di ogni treno
		// for (int i = 0; i < treni.size(); i++) {
		// for (int j = 0; j < treni.get(i).getTratte().size(); j++) {
		// System.out.print("->"
		// + treni.get(i).getTratte().get(j).getOraArr());
		// }
		// System.out.println(" ");
		// }

		// // System.out.println("Lista Treni Totale");

		// for (int i = 0; i < treni.size(); i++) {
		//
		// System.out.println("Codice treno:" + treni.get(i).getCodiceTreno());
		//
		// System.out.println("  La tratta comprende "
		// + treni.get(i).getTratta().size() + " stazioni.");
		// }
		// for (int j = 0; j < treni.get(i).getTratta().size(); j++) {
		// // System.out.println("Tratta del treno:");
		// System.out.println("Codice stazione attraversata:"
		// + treni.get(i).getTratta().get(j).getCodiceStazione());
		//
		// }
		// System.out.println("");
		// }

	}

	public static Integer getNumeroTestCase() {
		return numeroTestCase;
	}

	public static void setNumeroTestCase(Integer numeroTestCase) {
		ParserFileInput.numeroTestCase = numeroTestCase;
	}

	public static Integer getNumeroRichieste() {
		return numeroRichieste;
	}

	public static void setNumeroRichieste(Integer numeroRichieste) {
		ParserFileInput.numeroRichieste = numeroRichieste;
	}

	public static List<Richiesta> getRichieste() {
		return richieste;
	}

	public static void setRichieste(List<Richiesta> richieste) {
		ParserFileInput.richieste = richieste;
	}

	public static Integer getNumeroStazioni() {
		return numeroStazioni;
	}

	public static void setNumeroStazioni(Integer numeroStazioni) {
		ParserFileInput.numeroStazioni = numeroStazioni;
	}

	public static int getContatore() {
		return contatore;
	}

	public static void setContatore(int contatore) {
		ParserFileInput.contatore = contatore;
	}

	public static List<Treno> getTreni() {
		return treni;
	}

	public static void setTreni(List<Treno> treni) {
		ParserFileInput.treni = treni;
	}

	public static List<Tratta> getTratte() {
		return tratte;
	}

	public static void setTratte(List<Tratta> tratta) {
		ParserFileInput.tratte = tratta;
	}

}