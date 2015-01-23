package de.lette.mensaplan;

import java.util.LinkedHashSet;
import java.util.Set;

import com.google.gson.Gson;

/**
 * Objekte dieser Klasse werden serialisiert und an den Client zurückgegeben.
 * Alle Daten, die der Client benötigt, kommen hier rein.
 * 
 * @author Tommy Schmidt
 *
 */
public class ClientData {
	private Set<Termin> termine;
	private Set<Speise> speisen;
	private Set<Zusatzstoff> zusatzstoffe;

	public ClientData() {
		termine = new LinkedHashSet<Termin>();
		speisen = new LinkedHashSet<Speise>();
		zusatzstoffe = new LinkedHashSet<Zusatzstoff>();
	}

	public ClientData(Set<Termin> termine, Set<Speise> speisen) {
		this.termine = termine;
		this.speisen = speisen;
	}

	public Set<Termin> getTermine() {
		return termine;
	}

	public void setTermine(Set<Termin> termine) {
		this.termine = termine;
	}

	public Set<Speise> getSpeisen() {
		return speisen;
	}

	public void setSpeisen(Set<Speise> speisen) {
		this.speisen = speisen;
	}

	public String serialize() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public Set<Zusatzstoff> getZusatzstoffe() {
		return zusatzstoffe;
	}

	public void setZusatzstoffe(Set<Zusatzstoff> zusatzstoffe) {
		this.zusatzstoffe = zusatzstoffe;
	}
}