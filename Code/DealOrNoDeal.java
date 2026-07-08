
/**
 *@author Fazeleh Daneshmandi, Aleksey Leinweber, Felix Kuhlemann 
 */

import java.util.*;
import java.io.*;

public class DealOrNoDeal {

	// Variablen
	// Shakiba ANFANG
	static Scanner scan = new Scanner(System.in);
	static ArrayList<Integer> ungeoffneteKoffer = new ArrayList<>();
	static ArrayList<Integer> geoffneteKoffer = new ArrayList<>();
	static ArrayList<Double> betraege = new ArrayList<>();
	static ArrayList<Double> uebersicht = new ArrayList<>();
	static int runden = 1;
	static int kofferAnzahl = 10;
	private static int spielerKoffer;
	private static double spielerBetrag = 0; // Speichert den Geldbetrag im persönlichen Koffer des Spielers

	/**
	 * Initialisiert das Menü Objekt sowie die Spielereingabe und Menüauswahl wird
	 * verarbeitet.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Menue menue = new Menue();
		System.out.println(menue.begruessung);
		menue.playerInput();
	}
	// Shakiba ENDE

	/**
	 * Einstiegspunkt ins Spiel
	 */

	// Aleksey ANFANG
	public static void hauptSpiel() {
		initialisiereKoffer();
		readDateiUndInitialisiereBetraege();
		privaterKofferAuswahl();
		kofferZiehungen();
	}

	/**
	 * Koffern werden erzeugt.
	 */
	private static void initialisiereKoffer() {

		for (int i = 1; i < kofferAnzahl; i++) {
			ungeoffneteKoffer.add(i);

		}
	}

	/**
	 * Textdatei von Beträgen wird eingelesen und von String zu Double umgewandelt.
	 */
	private static void readDateiUndInitialisiereBetraege() {

		try (Scanner scan = new Scanner(new File("Geldbeträge.txt"))) {
			while (scan.hasNextLine()) {
				String zeile = scan.nextLine();
				betraege.add(Double.parseDouble(zeile));
			}
			Collections.shuffle(betraege);

		} catch (Exception e) {
			System.out.print("Datei konnte nicht gelesen werden!");
		}
	}

	/**
	 * Definition von Runden und der jeweiligen Kofferziehung
	 */
	
	private static void kofferZiehungen() {
		while (ungeoffneteKoffer.size() > 1) {
			int anzahlKofferZiehungen = 0;
			switch (runden) {
			case 1:
				anzahlKofferZiehungen = 3;
				break;
			case 2:
				anzahlKofferZiehungen = 2;
				break;
			case 3:
				anzahlKofferZiehungen = 2;
				break;
			default:
				anzahlKofferZiehungen = 1;
				break;
			}

			System.out.println("Runde " + runden);
			for (int i = 0; i < anzahlKofferZiehungen; i++) {
				
				if (ungeoffneteKoffer.size() <= 1)
					break;

				System.out.println("\nUngeöffnete Koffer: " + ungeoffneteKoffer);
				System.out.print("Welchen Koffer möchten Sie öffnen? ");
				
				int eingabe;
				try {
					eingabe = Integer.parseInt(scan.nextLine());
				} catch (Exception e) {
					System.err.println("Bitte geben Sie eine gültige Zahl ein!");
					i--;
					continue;
				}

				if (!ungeoffneteKoffer.contains(eingabe)) {
					System.err.println("Dieser Koffer existiert nicht oder wurde bereits geöffnet.");
				    i--;
				    continue;
				}

					int index = ungeoffneteKoffer.indexOf(eingabe);

					double wert = betraege.get(index);
					System.out.println("Im Koffer " + eingabe + " sind " + String.format("%.2f",wert) + " €");

					ungeoffneteKoffer.remove(index);
					betraege.remove(index);
					geoffneteKoffer.add(eingabe);

				System.out.println("Bereits geöffnete Koffer: " + geoffneteKoffer);
			}
			//Aleksey ENDE
			//Felix ANFANG
			// Bankangebot nach jeder abgeschlossenen Runde berechnen und anzeigen
			double angebot = berechneBankangebot();

			System.out.println("Die Bank bietet dir: " + String.format("%.2f", angebot) + " €");
			System.out.println("Deal or No Deal?");
			zeigeUebersichtDerBetraege();
			if (scan.nextLine().trim().toLowerCase().equals("deal")) {

				System.out.println("\n Du hast einen Deal gemacht!");
				System.out.println("Du erhältst: " + String.format("%.2f", angebot) + " €");
				System.out.println("Herzlichen Glückwunsch und danke fürs Spielen!");
				// Methode verlassen -> Spiel endet
				return;
			} else {
				System.out.println("NO DEAL! Das Spiel geht weiter...\n");
			}
			// Rundenzähler erhöhen für die nächste Iteration
			runden++;
		}
		// Nur noch der persönliche Koffer übrig -> Spiel endet automatisch
		System.out.println("\nNur noch 1 Koffer übrig! ");
		System.out.println("Diese beiden Beträge sind noch im Spiel:");
		System.out.println(" - " + String.format("%.2f", spielerBetrag) + " €");
		System.out.println(" - " + String.format("%.2f", betraege.get(0)) + " €");
		System.out.println("\nEiner davon ist in DEINEM Koffer.");
		System.out.println("\nMöchtest du deinen Koffer tauschen? (Ja/Nein) ");

		if (scan.nextLine().trim().toLowerCase().equals("ja")) {
			System.out.println("Das ist dein Gewinn: " + String.format("%.2f", betraege.get(0)) + " €"
					+ " \nHerzlichen Glückwunsch!");

		} else {
			System.out.println("Das ist dein Gewinn: " + String.format("%.2f", spielerBetrag) + " €"
					+ " \nHerzlichen Glückwunsch!");
		}
	}
	
	/**
	 * Spielerkoffer wird auswählt vom Spieler. Betrag wird vermerkt für Spielende.
	 */
	private static void privaterKofferAuswahl() {

		while (!(spielerKoffer >= 1 && spielerKoffer <= 10)) {

			try {
				System.out.println("\nUngeöffnete Koffer: " + ungeoffneteKoffer);

				System.out.print("Wähle deinen Koffer aus: ");

				spielerKoffer = Integer.parseInt(scan.nextLine());
				int index = ungeoffneteKoffer.indexOf(spielerKoffer);
				spielerBetrag = betraege.get(index);
				betraege.remove(index);
				ungeoffneteKoffer.remove(index);

				break;

			} catch (Exception e) {
				System.err.println("Bitte eine Zahl von 1-10 eingeben.");

			}
		}
	}

	/**
	 * Berechnet den Durchschnitt aller noch nicht geöffneten Beträge als
	 * Bankangebot
	 * 
	 * @return Durchschnitt aller nichtgeöffneten Beträge.
	 */
	private static double berechneBankangebot() {

		double summe = 0;

		// 1. Alle noch offenen Beträge addieren
		for (int i = 0; i < betraege.size(); i++) {
			summe = summe + betraege.get(i);
		}
		summe = summe + spielerBetrag;
		double durchschnitt = summe / (ungeoffneteKoffer.size() + 1);
		return durchschnitt;

	}

	private static void zeigeUebersichtDerBetraege() {
		uebersicht = new ArrayList<>(betraege);

		uebersicht.add(spielerBetrag);
		Collections.sort(uebersicht);

		for (double betrag : uebersicht) {
			System.out.println("Noch im Spiel befindliche Beträge: " + String.format("%.2f",betrag) + " €");
		}
	}
	//Felix ENDE
}
