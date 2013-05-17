/**
 * 
 */
package project.treni.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import project.treni.Richiesta;
import project.treni.Stazione;
import project.treni.Tratta;
import project.treni.Treno;

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

				long codStaz = Long.parseLong(datiStazione[0]);
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

						iterazioniTotali++;
						contatoreTreni++;
					}
				}
			}

			f.hasNextLine();

			numeroRichieste = ((Long.parseLong(f.nextLine())));
			System.out.println("numeroRichieste" + numeroRichieste);

			Richiesta ric = new Richiesta();
			ric.setCodTestCase(i + 1);

			for (int s = 0; s < numeroRichieste; s++) {
				iterazioniTotali++;
				ric.getRichiesteTestCase().add(f.nextLine());
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

		// Stampo la tratta di ogni treno
		for (int i = 0; i < treni.size(); i++) {
			for (int j = 0; j < treni.get(i).getTratte().size(); j++) {
				System.out.print("->"
						+ treni.get(i).getTratte().get(j).getOraArr());
			}
			System.out.println(" ");
		}

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