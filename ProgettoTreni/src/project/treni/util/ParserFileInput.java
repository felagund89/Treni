/**
 * 
 */
package project.treni.util;

import java.lang.Integer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

import project.treni.Richiesta;
import project.treni.Stazione;
import project.treni.Tratta;
import project.treni.Treno;
import project.treni.util.MinHeap;

/**
 * @author antonio
 * 
 */

public class ParserFileInput {

	private static long numeroTestCase = 0;

	private static long numeroStazioni = 0;

	private static long numeroRichieste = 0;

	private static int contatore;

	private static int contatoreTreni;

	private static int iterazioniTotali;

	private static Map<Integer, Stazione> stazioni = new HashMap<Integer, Stazione>();

	private static Stazione stazi = new Stazione();

	private static List<Tratta> tratte = new ArrayList<Tratta>(0);

	private static Treno tren = new Treno();

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

		numeroTestCase = (Long.parseLong(f.nextLine()));

		System.out.println("numero test case" + numeroTestCase);

		// scorro dati nei vari testCase

		// System.out.println(f.nextLine());

		for (int i = 0; i < numeroTestCase; i++) {
			iterazioniTotali++;
			System.out.println(f.nextLine());

			numeroStazioni = (Long.parseLong(f.nextLine()));

			System.out.println("numero staz" + numeroStazioni);

			for (int j = 0; j < numeroStazioni; j++) { // per i che va da 0 al
														// numero di stazioni
														// del test case
				iterazioniTotali++;

				String staz = f.nextLine();
				String datiStazione[] = staz.split(" ");

				int codStaz = Integer.parseInt(datiStazione[0]);
				long numTrenStaz = Long.parseLong(datiStazione[1]);

				Stazione stazione = new Stazione();

				stazione.setCodiceStazione(codStaz);
				stazione.setNumeroTreni(numTrenStaz);
				stazi = stazione;

				contatore++;

				for (int x = 0; x < numTrenStaz; x++) { // scorro i treni della

					// stazione
					String treno = f.nextLine();
					// System.out.println("stringaTreno" + treno);

					String stringaTreno[] = treno.split(" ");

					long cod = Long.parseLong(stringaTreno[0]);
					long orArr = Long.parseLong(stringaTreno[1]);
					long orPar = Long.parseLong(stringaTreno[2]);

					// if (treni.isEmpty()) {
					// treni.add(treno2);
					// iterazioniTotali++;
					// contatoreTreni++;
					// } else
					int valore = contains2(cod);
					if (valore != -1) {

						// aggiungo la stazione...(vecchio metodo)
						treni.get(valore).getTratta().add(stazione);

						// nuovo metodo salvo i dati in oggetto tratta, ogni
						// treno ha una lista di tratte che indica il suo
						// percorso.
						Tratta tratta = new Tratta();
						tratta.setStaz(stazione);
						tratta.setOraArr(orArr);
						tratta.setOraPart(orPar);
						treni.get(valore).getTratte().add(tratta);
						// test Yuri
						stazione.getTratte().add(tratta);
					}

					else {

						// aggiungo treno
						Treno treno2 = new Treno();
						treno2.setCodiceTreno(cod);
						treno2.setOraArrivo(orArr);
						treno2.setOraPartenza(orPar);
						treni.add(treno2);
						// vecchio metodo
						treno2.getTratta().add(stazione);

						// nuovo metodo salvo i dati in oggetto tratta, ogni
						// treno ha una lista di tratte che indica il suo
						// percorso.
						Tratta tratta = new Tratta();
						tratta.setOraArr(orArr);
						tratta.setOraPart(orPar);
						tratta.setTreno(treno2);
						tratta.setStaz(stazione);
						tratte.add(tratta);
						// test Yuri
						stazione.getTratte().add(tratta);

						iterazioniTotali++;
						contatoreTreni++;
					}
				}

				stazione.setPeso(1); // TODO: Real weight
				stazioni.put(stazione.getCodiceStazione(), stazione);
			}

			f.hasNextLine();

			numeroRichieste = ((Long.parseLong(f.nextLine())));
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
					System.out.println("-----------");
					Stazione s1 = stazioni.get(Integer.parseInt(stringaRichiesta[1]));
					Stazione s2 = stazioni.get(Integer.parseInt(stringaRichiesta[2]));
					
					// - Dist: array delle distanze, inizializzato a infinito
					Map<Integer, Integer> dist = new HashMap<Integer, Integer>();
					for(Map.Entry<Integer, Stazione> e : stazioni.entrySet())
						dist.put(e.getKey(), Integer.MAX_VALUE);

					// - P: vettore dei padri, inizializzato a 0
					Map<Integer, Integer> p = new HashMap<Integer, Integer>();
					for(Map.Entry<Integer, Stazione> e : stazioni.entrySet())
						p.put(e.getKey(), 0);

					// - Dist[u] <- 0
					dist.put(s1.getCodiceStazione(), 0);

					// - P[u] <- u
					p.put(s1.getCodiceStazione(), s1.getCodiceStazione());

					// - H <- min-heap inizializzato con tutti i nodi e le priorità sono i valori di Dist
					MinHeap h = new MinHeap(new ArrayList<Stazione>(stazioni.values()));

					// - WHILE H non è vuoto DO
					while(!h.isEmpty()) {
						// - v <- H.get_min() /* preleva il nodo con distanza minima */
						Stazione stazione = h.min();
						// - FOR ogni adiacente w di v DO
						for(Tratta tratta : stazione.getTratte()) {
							// - IF Dist[w] > Dist[v] + p(v, w) THEN

						}
					}


					System.out.println("dist -------------- " + dist.size());
					System.out.println(dist);
					
					System.out.println("p --------------" + p.size());
					System.out.println(p);

					System.exit(0);
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
	 * @param codiceTreno
	 * @return
	 */
	public static int contains2(long codiceTreno) {
		for (int q = 0; q < treni.size(); q++) {
			if (treni.get(q).getCodiceTreno().equals(codiceTreno))
				return q;
			iterazioniTotali++;
		}

		return -1;
	}

	/**
	 * @param indiceTreno
	 */
	public static void sortTratta(int indiceTreno) {
		// List<Stazione> tratta = treni.get(indiceTreno).getTratta();
		// for (int j = 0; j < tratta.size(); j++) {
		//
		//

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

	public static long getNumeroTestCase() {
		return numeroTestCase;
	}

	public static void setNumeroTestCase(long numeroTestCase) {
		ParserFileInput.numeroTestCase = numeroTestCase;
	}

	public static long getNumeroRichieste() {
		return numeroRichieste;
	}

	public static void setNumeroRichieste(long numeroRichieste) {
		ParserFileInput.numeroRichieste = numeroRichieste;
	}

	public static List<Richiesta> getRichieste() {
		return richieste;
	}

	public static void setRichieste(List<Richiesta> richieste) {
		ParserFileInput.richieste = richieste;
	}

	public static long getNumeroStazioni() {
		return numeroStazioni;
	}

	public static void setNumeroStazioni(long numeroStazioni) {
		ParserFileInput.numeroStazioni = numeroStazioni;
	}

	public static int getContatore() {
		return contatore;
	}

	public static void setContatore(int contatore) {
		ParserFileInput.contatore = contatore;
	}

	public static Stazione getStazi() {
		return stazi;
	}

	public static void setStazi(Stazione stazi) {
		ParserFileInput.stazi = stazi;
	}

	public static Treno getTren() {
		return tren;
	}

	public static void setTren(Treno tren) {
		ParserFileInput.tren = tren;
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