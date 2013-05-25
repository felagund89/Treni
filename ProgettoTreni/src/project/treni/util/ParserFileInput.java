/**
 * 
 */
package project.treni.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

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
					Map<Short, Tratta> tratte;
					if(rete.containsKey(treno.getCodiceTreno()))
						tratte = rete.get(treno.getCodiceTreno());
					else
						tratte = new TreeMap<Short, Tratta>();
					tratte.put(orArr, tratta);
					rete.put(treno.getCodiceTreno(), tratte);
				}

				stazione.setPeso(0); // TODO: Real weight
				stazioni.put(stazione.getCodiceStazione(), stazione);
			}

			f.hasNextLine();

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
					System.out.println("-----------");
					Stazione s1 = stazioni.get(Integer.parseInt(stringaRichiesta[1]));
					Stazione s2 = stazioni.get(Integer.parseInt(stringaRichiesta[2]));

					// - Dist: array delle distanze, inizializzato a infinito
					Map<Integer, Integer> dist = new HashMap<Integer, Integer>();
					for (Map.Entry<Integer, Stazione> e : stazioni.entrySet())
						dist.put(e.getKey(), Integer.MAX_VALUE);

					// - P: vettore dei padri, inizializzato a 0
					Map<Integer, Integer> p = new HashMap<Integer, Integer>();
					for (Map.Entry<Integer, Stazione> e : stazioni.entrySet())
						p.put(e.getKey(), 0);

					// - Dist[u] <- 0
					dist.put(s1.getCodiceStazione(), 0);

					// - P[u] <- u
					p.put(s1.getCodiceStazione(), s1.getCodiceStazione());

					// - H <- min-heap inizializzato con tutti i nodi e le
					// priorità sono i valori di Dist
					MinHeap h = new MinHeap(new ArrayList<Stazione>(
							stazioni.values()));

					// - WHILE H non è vuoto DO
					while (!h.isEmpty()) {
						// - v <- H.get_min() /* preleva il nodo con distanza
						// minima */
						Stazione v = h.min();
						// - FOR ogni adiacente w di v DO
						for(Tratta w : v.getTratte()) {
							// TODO: settare il peso a ogni w.stazione e aggiornare l'heap con remove+add

							// - IF Dist[w] > Dist[v] + p(v, w) THEN
							if(dist.get(w.getStaz().getCodiceStazione()) > dist.get(v.getCodiceStazione()) + (v.getPeso() + w.getStaz().getPeso())) {
								// - Dist[w] <- Dist[v] + p(v, w)
								dist.put(w.getStaz().getCodiceStazione(), dist.get(v.getCodiceStazione()) + (v.getPeso() + w.getStaz().getPeso()));
								// - P[w] <- v
								p.put(w.getStaz().getCodiceStazione(), v.getCodiceStazione());
								// - H.decrease(w) /* aggiorna l'heap a seguito del decremento */
								h.remove();
							}
						}
					}

					System.out.println("dist -------------- " + dist.size());
					System.out.println(dist);

					System.out.println("p --------------" + p.size());
					System.out.println(p);

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