import java.util.*;


public class Menue {
	// Begrüßungstext, der in main() ausgegeben wird
	public String begruessung = "";
	// Name des Spielers, wird nach der Eingabe gespeichert
	private String playerName = "";
	// Speichert die Menüauswahl des Spielers (1 = Regeln + Spielstart, 2 = direkt Spielstart)
	public int eingabe;
	
	// Konstruktor: Wird beim Erstellen des Menü-Objekts automatisch aufgerufen
	Menue() {
		begruessung = "Herzlich Willkommen, bei Deal or No Deal!\nBitte geben Sie ihren namen ein: ";

	}
	// Verarbeitet die gesamte Spielerinteraktion im Menü (Name, Auswahl, ggf. Regelanzeige)
	void playerInput() { //warum throws IOException? Scanner wirft keine IOException zurück
		Scanner scan = new Scanner(System.in);
		// Name des Spielers einlesen
		playerName = scan.nextLine();
		System.out.println("Hallo " + "\u001B[33m" + playerName + "\u001B[0m" + "!\n" + "\nWähle aus: \nRegeln&Spielstart(1)\nSpielstart(2)");

		 // Eingabeschleife: Läuft so lange, bis eine gültige Zahl (1 oder 2) eingegeben wird
		while (true) {
			try {
				// Eingabe als Text einlesen und in eine Ganzzahl umwandeln
                // → Integer.parseInt wirft eine Exception bei ungültiger Eingabe
				eingabe = Integer.parseInt(scan.nextLine());
				// Nur 1 oder 2 sind gültige Optionen → Schleife verlassen
				if (eingabe == 1 || eingabe == 2) {
					break;
				}
				// Gültige Zahl, aber nicht 1 oder 2 → erneut fragen
				System.err.println("Bitte nur 1 oder 2 eingeben.");

			} catch (Exception e) {
				// Keine Zahl eingegeben (z.B. Buchstaben) → Fehlermeldung und erneut fragen
				System.err.println("Ungültige Eingabe! Bitte eine Zahl eingeben.");
			}
		}
		// Je nach Auswahl entsprechende Aktion ausführen
		switch (eingabe) {
		case 1:
			// Regeln anzeigen, dann das Spiel starten
			System.out.println("\n----- SPIELREGELN -----");
			System.out.println("Es stehen 19 Koffer mit unterschiedlichen Geldbeträgen zur Auswahl.");
			System.out.println("Du wählst zuerst deinen persönlichen Koffer.");
			System.out.println("Danach öffnest du Runde für Runde weitere Koffer.");
			System.out.println("Nach jeder Runde macht dir die Bank ein Angebot.");
			System.out.println("Nimmst du es an (DEAL) oder spielst du weiter (NO DEAL)?");
			 
			// Hauptspiel in DealOrNoDeal starten 
			DealOrNoDeal.HauptSpiel();
			break;
		case 2:
			// Direkt ins Spiel starten ohne Regelanzeige
			DealOrNoDeal.HauptSpiel();
			break;
		}
		scan.close();
	}
}
