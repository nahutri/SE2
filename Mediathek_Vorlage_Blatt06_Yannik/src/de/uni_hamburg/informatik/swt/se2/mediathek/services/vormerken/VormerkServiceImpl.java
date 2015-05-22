package de.uni_hamburg.informatik.swt.se2.mediathek.services.vormerken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Kunde;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Vormerkkarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.AbstractObservableService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.verleih.VerleihService;

/**
 * Diese Klasse implementiert das Interface VerleihService. Siehe dortiger
 * Kommentar.
 * 
 * @author SE2-Team
 * @version SoSe 2015
 */
public class VormerkServiceImpl extends AbstractObservableService implements
		VormerkService {
	/**
	 * Diese Map speichert für jedes eingefügte Medium die dazugehörige
	 * Verleihkarte. Ein Zugriff auf die Verleihkarte ist dadurch leicht über
	 * die Angabe des Mediums möglich. Beispiel: _verleihkarten.get(medium)
	 */

	private Map<Medium, Vormerkkarte> _vormerkkarten;
	private VerleihService _verleihService;

	/**
	 * Konstruktor. Erzeugt einen neuen VerleihServiceImpl.
	 * 
	 * @param kundenstamm
	 *            Der KundenstammService.
	 * @param medienbestand
	 *            Der MedienbestandService.
	 * @param initialBestand
	 *            Der initiale Bestand.
	 * 
	 * @require kundenstamm != null
	 * @require medienbestand != null
	 * @require initialBestand != null
	 */
	public VormerkServiceImpl() {
		_vormerkkarten = new HashMap<Medium, Vormerkkarte>();
	}

	@Override
	public void vormerkenAn(Kunde kunde, List<Medium> medien) {
		assert _verleihService.kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
		for (Medium medium : medien) {
			if (!istVorgemerkt(medium)) {
				List<Kunde> kunden = new ArrayList<Kunde>();
				kunden.add(kunde);
				Vormerkkarte vormerkkarte = new Vormerkkarte(medium, kunden);
				_vormerkkarten.put(medium, vormerkkarte);
			} else {
				getVormerkkarteFuer(medium).add(kunde);
			}
		}
		informiereUeberAenderung();
	}

	@Override
	public boolean istVorgemerkt(Medium medium) {
		assert _verleihService.mediumImBestand(medium) : "Vorbedingung verletzt: mediumExistiert(medium)";
		return _vormerkkarten.get(medium) != null;
	}

	public Vormerkkarte getVormerkkarteFuer(Medium medium) {
		assert istVorgemerkt(medium) : "Vorbedingung verletzt: istVerliehen(medium)";
		return _vormerkkarten.get(medium);
	}

	@Override
	public boolean istAusleihbarFuer(List<Medium> medien, Kunde kunde) {

		for (Medium medium : medien) {
			if (istAusleihbarFuer(medium, kunde) == false) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean istAusleihbarFuer(Medium medium, Kunde kunde) {
		if (!istVorgemerkt(medium)) {
			return true;
		}
		return getVormerkkarteFuer(medium).getVormerker(0).equals(kunde);
	}

	@Override
	public boolean istVorgemerktVon(List<Medium> medien, Kunde kunde) {
		for (Medium medium : medien) {
			if (istVorgemerktVon(medium, kunde)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean istVorgemerktVon(Medium medium, Kunde kunde) {
		if (!istVorgemerkt(medium)) {
			return false;
		}
		for (Kunde kunden : getVormerkkarteFuer(medium).getVormerker()) {
			if (kunde.equals(kunden)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setVerleihService(VerleihService verleihService) {
		_verleihService = verleihService;
	}

	@Override
	public boolean sindVormerkbarVon(List<Medium> medien, Kunde kunde) {
		for (Medium medium : medien) {

			if (istVorgemerkt(medium)
					&& _vormerkkarten.get(medium).getAnzahlVormerker() == 3) {
				return false;
			}
		}
		return !istVorgemerktVon(medien, kunde)
				&& !_verleihService.mindestensEinerVerliehenAn(kunde, medien);
	}
	
	@Override
	public void entferneVormerkkarte(Medium medium){
		_vormerkkarten.remove(medium);
	}
}