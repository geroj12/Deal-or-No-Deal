
/**
*@author Fazeleh Daneshmandi, Aleksey Leinweber, Felix Kuhlemann
*/
import java.util.*;


//Shakiba ANFANG
public class Menue {
	public String begruessung = "";
	private String playerName = "";
	public int eingabe;

	Menue() {
		begruessung = "Herzlich Willkommen, bei Deal or No Deal!\nBitte geben Sie ihren namen ein: ";

	}

	/**
	 * Liest die Eingabe die vom Spieler eingegeben wird über die Tastatur und leitet zum Hauptspiel hinüber.
	 */
	void playerInput() {
		Scanner scan = new Scanner(System.in);
		playerName = scan.nextLine();
		System.out.println("Hallo " + playerName + "!\n" + "\nWähle aus: \nRegeln&Spielstart(1)\nSpielstart(2)");

		while (true) {
			try {
				eingabe = Integer.parseInt(scan.nextLine());

				if (eingabe == 1 || eingabe == 2) {
					break;
				}

				System.err.println("Bitte nur 1 oder 2 eingeben.");

			} catch (Exception e) {
				System.err.println("Ungültige Eingabe! Bitte eine Zahl eingeben.");
			}
		}

		switch (eingabe) {
		case 1:
			// Regeln anzeigen, dann das Spiel starten
			System.out.println("\n----- SPIELREGELN -----");
			System.out.println("Es stehen 19 Koffer mit unterschiedlichen Geldbeträgen zur Auswahl.");
			System.out.println("Du wählst zuerst deinen persönlichen Koffer.");
			System.out.println("Danach öffnest du Runde für Runde weitere Koffer.");
			System.out.println("Nach jeder Runde macht dir die Bank ein Angebot.");
			System.out.println("Nimmst du es an (DEAL) oder spielst du weiter (NO DEAL)?");
			DealOrNoDeal.hauptSpiel();
			break;
		case 2:
			DealOrNoDeal.hauptSpiel();
			break;
		}
		scan.close();
	}
}
//Shakiba ENDE
