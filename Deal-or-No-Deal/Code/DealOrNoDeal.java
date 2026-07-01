import java.util.*;

public class DealOrNoDeal {
	// Scanner für die gesamte Klasse 
	static Scanner scan = new Scanner(System.in);
	 // Liste aller Koffer, die noch nicht geöffnet wurden
	static ArrayList<String> ungeoffneteKoffer = new ArrayList<>();
	// Liste aller bereits geöffneten Koffer (wird nur für die Anzeige verwendet)
	static ArrayList<String> geoffneteKoffer = new ArrayList<>();
	// Zählt die aktuelle Spielrunde – beginnt bei Runde 1
	static int runden = 1;

	public static void main(String[] args) { 
		// Menü-Objekt erstellen
		Menue menue = new Menue();
		// Begrüßungstext aus dem Menü-Objekt ausgeben
		System.out.println(menue.begruessung);
		// Spielereingabe (Name + Menüauswahl) verarbeiten
		menue.playerInput();

	}
	// Einstiegspunkt ins eigentliche Spiel – wird aus dem Menü aufgerufen
	public static void HauptSpiel() {
		// Koffer 1–19 in die Liste eintragen
		InitialisiereKoffer();
		// Geldbeträge zufällig den Koffern zuordnen
		InitialisiereBetraege();
		// Spieler wählt seinen persönlichen Koffer
		PrivaterKofferAuswahl();
		// Hauptspiel: Runden, Bankangebote, Deal-Entscheidung
		KofferZiehungen();
	}
	// Liste der Geldbeträg
	static ArrayList<Double> betraege = new ArrayList<>();

	private static void InitialisiereBetraege() {
	// Alle 19 möglichen Geldbeträge hinzufügen
	betraege.add(100.0);		// 1
	betraege.add(200.0);		// 2
	betraege.add(300.0);		// 3
	betraege.add(400.0);		// 4
	betraege.add(500.0);		// 5
	betraege.add(750.0);		// 6
	betraege.add(1000.0);		// 7
	betraege.add(5000.0);		// 8
	betraege.add(10000.0);		// 9
	betraege.add(25000.0);		// 10
	betraege.add(50000.0);		// 11
	betraege.add(75000.0);		// 12
	betraege.add(100000.0);		// 13
	betraege.add(200000.0);		// 14
	betraege.add(300000.0);		// 15
	betraege.add(400000.0);		// 16
	betraege.add(500000.0);		// 17
	betraege.add(750000.0);		// 18
	betraege.add(1000000.0);	// 19
	// Liste zufällig mischen
	Collections.shuffle(betraege);

	}
	// Speichert den Geldbetrag im persönlichen Koffer des Spielers
	static double spielerBetrag = 0;
	// Lässt den Spieler seinen persönlichen Koffer auswählen und merkt sich den darin enthaltenen Betrag
	private static void PrivaterKofferAuswahl() {
		 // Alle verfügbaren Koffer anzeigen
		System.out.println("Ungeöffnete Koffer: " + ungeoffneteKoffer);
		System.out.print("Wähle deinen Koffer aus: ");
		
		String spielerKoffer = scan.nextLine();
		// Prüfen ob der eingegebene Koffer in der Liste existiert
		if (ungeoffneteKoffer.contains(spielerKoffer)) {
			// Index des gewählten Koffers ermitteln
            // -> gleicher Index gilt auch in der betraege-Liste (beide Listen sind synchron)
			int index = ungeoffneteKoffer.indexOf(spielerKoffer);
			// Betrag des persönlichen Koffers speichern (wird am Ende ggf. ausgegeben)
			spielerBetrag = betraege.get(index);
			// Betrag und Koffer aus den aktiven Listen entfernen
            // (der persönliche Koffer nimmt nicht am normalen Spielablauf teil)
			betraege.remove(index);
			ungeoffneteKoffer.remove(index);
			//DEBUG Kontrolle
			System.out.println("DEBUG Spieler Betrag: " + spielerBetrag);
			System.out.println("DEBUG Betraege Liste: " + betraege);
			System.out.println("DEBUG Ungeöffnete Koffer: " + ungeoffneteKoffer);
			 // Gewählten Koffer grün hinterlegt anzeigen
			System.out.print("\u001B[42m" + "Dein Koffer:" + spielerKoffer + "\u001B[0m ");
		}
	}
	// Erstellt die 19 Koffer als Strings ("Koffer1" bis "Koffer19") und fügt sie der Liste hinzu
	private static void InitialisiereKoffer() {
		// 19 Koffer initialisieren
		for (int i = 1; i < 20; i++) {
			ungeoffneteKoffer.add("Koffer" + i);
		}
	}
	// Hauptspielschleife: Verwaltet alle Runden, Kofferöffnungen und Bankangebote
	private static void KofferZiehungen() {
		// Spiel läuft solange mehr als 1 Koffer übrig ist
		while (ungeoffneteKoffer.size() > 1) {
			// Definition von runden und der jeweiligen Kofferziehung
			int anzahlKofferZiehungen = 0;
			switch (runden) {
			case 1:
				anzahlKofferZiehungen = 5;
				break;
			case 2:
				anzahlKofferZiehungen = 5;
				break;
			case 3:
				anzahlKofferZiehungen = 5;
				break;
			default:
				anzahlKofferZiehungen = 1;
				break;
			}
			// Aktuelle Rundennummer blau hinterlegt ausgeben
			System.out.println("\u001B[44m" + "\nRunde " + runden + "\u001B[0m ");
			
			// Schleife für die Kofferziehungen dieser Runde
			for (int i = 0; i < anzahlKofferZiehungen; i++) {
				 // Abbruch falls nur noch 1 Koffer übrig
				 if (ungeoffneteKoffer.size() <= 1) break;
				 // Verbleibende Koffer cyan hinterlegt anzeigen
				System.out.println("\u001B[46m" + "Ungeöffnete Koffer: " + ungeoffneteKoffer + "\u001B[0m ");

				System.out.print("Welchen Koffer möchtest du öffnen? ");
				String eingabe = scan.nextLine();
				
				// Prüfen ob der eingegebene Koffer noch verfügbar ist
				if (ungeoffneteKoffer.contains(eingabe)) {
					// Index des gewählten Koffers bestimmen
					int index = ungeoffneteKoffer.indexOf(eingabe);
					// Betrag aus der synchronen betraege-Liste auslesen (gleicher Index!)
					double betrag = betraege.get(index);
					// Koffer und zugehörigen Betrag aus den aktiven Listen entfernen
					ungeoffneteKoffer.remove(index);
					betraege.remove(index);
					// Koffer in die Liste der geöffneten Koffer verschieben
					geoffneteKoffer.add(eingabe);
					
					System.out.println("In diesem Koffer waren: " + String.format("%.2f", betrag) + " €");
					//Debug Kontrolle
					System.out.println("DEBUG: Noch offene Beträge: " + betraege);
					System.out.println("DEBUG: Ungeöffnete Koffer: " + ungeoffneteKoffer);
					System.out.println("DEBUG: Geöffnete Koffer: " + geoffneteKoffer);
					
					System.out.println("DEBUG: Größe betraege=" + betraege.size() + ", ungeöffnet=" + ungeoffneteKoffer.size() + ", geöffnet=" + geoffneteKoffer.size());
					
				} else {
					// Ungültige Eingabe: Fehlermeldung ausgeben und den Zähler i zurücksetzen
                    // -> i-- damit diese Runde nicht verloren geht
					System.err.println("Dieser Koffer existiert nicht oder wurde bereits geöffnet.");
					i--;
				}
				// Alle bereits geöffneten Koffer rot hinterlegt anzeigen
				System.out.println("\u001B[41m" + "Geöffnete Koffer: " + geoffneteKoffer + "\u001B[0m ");
			}
			// Bankangebot nach jeder abgeschlossenen Runde berechnen und anzeigen
			double angebot = berechneBankangebot();

		    //%=Platzhalter .2=2 Nachkommastellen f=float/double
		    System.out.println("Die Bank bietet dir: " + String.format("%.2f", angebot) + " €");
		    System.out.println("Deal or No Deal?");
		    
		 // Spielereingabe einlesen, Leerzeichen entfernen und in Kleinbuchstaben umwandeln
		    String antwort = scan.nextLine().trim().toLowerCase();
		    
		    if (antwort.equals("deal")) {
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
		System.out.println("\n Nur noch ein Koffer übrig! ");
	    System.out.println("Dein Koffer enthält: " + String.format("%.2f", spielerBetrag) + " €");
	    System.out.println("Das ist dein Gewinn! Herzlichen Glückwunsch!");
	}
	
	 // Berechnet den Durchschnitt aller noch nicht geöffneten Beträge als Bankangebot
	private static double berechneBankangebot() {

		double summe = 0;

		// 1. Alle noch offenen Beträge addieren
		for (int i = 0; i < betraege.size(); i++) {
		summe = summe + betraege.get(i); }

		// 2. Spielerkoffer mit einbeziehen, weil der ist am Anfang ja rausgenommen worden
	    summe = summe + spielerBetrag;
		
		// 3. Durchschnitt berechnen
		double durchschnitt = summe / betraege.size();

		// 4. Durchschnitt zurückgeben
		return durchschnitt;

		}

}
