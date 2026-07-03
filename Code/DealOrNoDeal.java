import java.util.*;
import java.io.*;

public class DealOrNoDeal {
	static Scanner scan = new Scanner(System.in);
	static ArrayList<String> ungeoffneteKoffer = new ArrayList<>();
	static ArrayList<String> geoffneteKoffer = new ArrayList<>();
	static ArrayList<Double> betraege = new ArrayList();
	static int runden = 1;
	static int kofferAnzahl = 11;

	private static String spielerKoffer = "";
	// Speichert den Geldbetrag im persönlichen Koffer des Spielers
	static double spielerBetrag = 0;

	public static void main(String[] args) throws IOException {
		Menue menue = new Menue();
		System.out.println(menue.begruessung);
		menue.playerInput();

	}

	public static void HauptSpiel() {

		InitialisiereKoffer();
		InitialisiereGeldbetraege();
		System.out.print(betraege);

		PrivaterKofferAuswahl();

		KofferZiehungen();
	}

	private static void PrivaterKofferAuswahl() {
		System.out.println("\nUngeöffnete Koffer: " + ungeoffneteKoffer);
		System.out.print("Wähle deinen Koffer aus: ");
		spielerKoffer = scan.nextLine();
		int index = ungeoffneteKoffer.indexOf(spielerKoffer);
		// Betrag des persönlichen Koffers speichern (wird am Ende ggf. ausgegeben)
		spielerBetrag = betraege.get(index);
		// Betrag und Koffer aus den aktiven Listen entfernen
		// (der persönliche Koffer nimmt nicht am normalen Spielablauf teil)
		betraege.remove(index);
		ungeoffneteKoffer.remove(index);

	}

	private static void InitialisiereGeldbetraege() {

		try (Scanner scan = new Scanner(new File("Geldbeträge.txt"))) {
			while (scan.hasNextLine()) {
				String zeile = scan.nextLine();
				betraege.add(Double.parseDouble(zeile));
			}
			Collections.shuffle(betraege);

		} catch (Exception e) {
		}
	}

	private static void InitialisiereKoffer() {

		for (int i = 1; i < kofferAnzahl; i++) {
			ungeoffneteKoffer.add("Koffer" + i);

		}

	}

	// Berechnet den Durchschnitt aller noch nicht geöffneten Beträge als
	// Bankangebot
	private static double berechneBankangebot() {

		double summe = 0;

		// 1. Alle noch offenen Beträge addieren
		for (int i = 0; i < betraege.size(); i++) {
			summe = summe + betraege.get(i);
		}

		// 2. Spielerkoffer mit einbeziehen, weil der ist am Anfang ja rausgenommen
		// worden
		summe = summe + spielerBetrag;

		// 3. Durchschnitt berechnen
		double durchschnitt = summe / (ungeoffneteKoffer.size() + 1);

		// 4. Durchschnitt zurückgeben
		return durchschnitt;

	}

	private static void KofferZiehungen() {
		while (ungeoffneteKoffer.size() > 1) {
			// Definition von runden und der jeweiligen Kofferziehung
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
//			case 4:
//				anzahlKofferZiehungen = 3;
//				break;
//			case 5:
//				anzahlKofferZiehungen = 2;
//				break;
			default:
				anzahlKofferZiehungen = 1;
				break;
			}

			System.out.println("\nRunde " + runden);
			for (int i = 0; i < anzahlKofferZiehungen; i++) {
				// Abbruch falls nur noch 1 Koffer übrig
				if (ungeoffneteKoffer.size() <= 1)
					break;

				System.out.print(betraege);

				System.out.println("\nUngeöffnete Koffer: " + ungeoffneteKoffer);

				System.out.print("Welchen Koffer möchten Sie öffnen? ");
				String eingabe = scan.nextLine();

				if (ungeoffneteKoffer.contains(eingabe)) {
					int index = ungeoffneteKoffer.indexOf(eingabe);

					double wert = betraege.get(index);
					System.out.println("Im " + eingabe + " sind " + wert + " Euro.");

					ungeoffneteKoffer.remove(index);
					betraege.remove(index);
					geoffneteKoffer.add(eingabe);
				} else {
					System.err.println("Dieser Koffer existiert nicht oder wurde bereits geöffnet.");
					i--;
				}

				System.out.println("Geöffnete Koffer: " + geoffneteKoffer);
			}

			// Bankangebot nach jeder abgeschlossenen Runde berechnen und anzeigen
			double angebot = berechneBankangebot();

			// %=Platzhalter .2=2 Nachkommastellen f=float/double
			System.out.println("Die Bank bietet dir: " + String.format("%.2f", angebot) + " €");
			System.out.println("Deal or No Deal?");

	

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
		System.out.println(
				"Dein Koffer enthält: " + String.format("%.2f", spielerBetrag) + " €" + " übrig" + betraege.get(0));
		System.out.println("\nMöchtest du tauschen? (Ja/Nein) ");

		if (scan.nextLine().trim().toLowerCase().equals("ja")) {
			System.out.println("Das ist dein Gewinn: " + betraege.get(0) +  "Herzlichen Glückwunsch!");

		}else {
			System.out.println("Das ist dein Gewinn: " + spielerBetrag +  "Herzlichen Glückwunsch!");

		}

		// Auswahlmöglichkeit

	}

}
