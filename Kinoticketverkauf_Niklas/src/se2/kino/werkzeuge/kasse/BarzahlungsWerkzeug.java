package se2.kino.werkzeuge.kasse;

import java.util.Observable;

public class BarzahlungsWerkzeug extends Observable {

	private BarzahlungsWerkzeugUI _ui;
	private int _preis;
	private boolean _zahlungAusgefuehrt;

	public BarzahlungsWerkzeug() {
		_ui = new BarzahlungsWerkzeugUI();

		registriereListener();
	}

	/**
	 * Kann aufgerufen werden, wenn ein Geldbetrag mit Barzahlung eingezogen
	 * werden soll. Dann wird ein Barzahlungsfenster geöffnet.
	 * 
	 * @param preis
	 *            der zu zahlende Preis.
	 * @return true, wenn das Zahlen erfolgreich war, sonst false.
	 */
	public void ziehePreisEin(int preis) {
		_preis = preis;
		_zahlungAusgefuehrt = false;
		_ui.getPreisAnzeige().setText(preis + " Eurocent");
		_ui.getGeldEingabe().setText("");
		_ui.getRueckgeldAnzeige().setText("0 Eurocent");
		_ui.getOKButton().setEnabled(false);

		_ui.zeigeFenster();
	}

	/**
	 * Aktualisiert die Anzeige. Das bedeutet der OK Button wird aktiv oder
	 * inaktiv und das Rückgeld wird berechnet.
	 */
	private void aktualisiereAnzeige() {
		if (berechneRueckgeld() < 0) {
			_ui.getRueckgeldAnzeige().setText("0 Eurocent");
			_ui.getOKButton().setEnabled(false);
		} else {
			_ui.getRueckgeldAnzeige()
					.setText(berechneRueckgeld() + " Eurocent");
			_ui.getOKButton().setEnabled(true);
		}
		_ui.aktualisiereGroesse();
	}

	/**
	 * Berechnet das aktuell zu zahlende Rückgeld.
	 * 
	 * @return das Rückgeld.
	 */
	private int berechneRueckgeld() {
		// Kleiner Workaround mit der -1. Geht sicher schöner durch die
		// Implementierung von Geldbetrag
		return -1 * (_preis - getGeldeingabeInCent());
	}

	/**
	 * Gibt die eingegebene Geldsumme in Cent zurück.
	 * 
	 * @return gibt den Geldbetrag als int in Eurocent zurück.
	 */
	private int getGeldeingabeInCent() {
		String eingabeString = _ui.getGeldEingabe().getText();
		if (eingabeString.matches("[0-9]+")){
			//eingabeString = eingabeString.replace(",", "."); <<- nicht nötig nur Eurocent.
			return (int) (Float.parseFloat(eingabeString));
		} else {
			_ui.getGeldEingabe().setText("");
			return 0;
		}
	}

	/**
	 * Reagiert auf den OK Button.
	 */
	private void reagiereAufOKButton() {
		_zahlungAusgefuehrt = true;
		zahlungAbschließen();
	}

	/**
	 * Reagiert auf den Abbrechen Button.
	 */
	private void reagiereAufAbbrechenButton() {
		_zahlungAusgefuehrt = false;
		zahlungAbschließen();
	}

	/**
	 * Schließt die Zahlung ab. D.h. das Fenster wird geschlossen und die
	 * Observer werden informiert.
	 */
	private void zahlungAbschließen() {
		_ui.versteckeFenster();

		setChanged();
		notifyObservers();
	}

	/**
	 * Prüft ob die letzte Zahlung erfolgreich war.
	 * 
	 * @return true, wenn gezahlt wurde. false sonst.
	 */
	public boolean zahlungErfolgreich() {
		return _zahlungAusgefuehrt;
	}

	/**
	 * Registriert alle notwendigen Listener.
	 */
	private void registriereListener() {
		_ui.getOKButton().addActionListener(e -> {
			reagiereAufOKButton();
		});
		_ui.getAbbrechenButton().addActionListener(e -> {
			reagiereAufAbbrechenButton();
		});
		_ui.getGeldEingabe().addActionListener(e -> {
			aktualisiereAnzeige();
		});
	}
}
