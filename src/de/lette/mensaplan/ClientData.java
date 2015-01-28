package de.lette.mensaplan;

import java.util.LinkedHashSet;
import java.util.Set;

import com.google.gson.Gson;

/**
 * Objekte dieser Klasse werden serialisiert und an den Client zurückgegeben. Alle Daten, die der Client benötigt, kommen hier rein.
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

	/**
	 * Filtert alle Speisen nach der gegebenen SpeiseArt und gibt ein Set mit den gefundenen Speisen zurück. Das Set ist leer, wenn keine Speisen zu
	 * der gegebenen SpeiseArt vorhanden sind.
	 * 
	 * @param speiseArt
	 *            die Art der Speise nach der gefiltert werden soll
	 * @return ein Set, welches alle Speisen beinhaltet, die der SpeiseArt entsprechen oder ein leeres Set wenn keine Speisen gefunden wurden
	 */
	public Set<Speise> getSpeisen(SpeiseArt speiseArt) {
		return getSpeisen(new SpeiseArt[] { speiseArt });
	}

	/**
	 * Filtert alle Speisen nach den gegebenen SpeiseArten und gibt ein Set mit den gefundenen Speisen zurück. Das Set ist leer, wenn keine Speisen zu
	 * den gegebenen SpeiseArten vorhanden sind.
	 * 
	 * @param speiseArten
	 *            die Arten der Speise nach denen gefiltert werden soll
	 * @return ein Set, welches alle Speisen beinhaltet, die den SpeiseArten entsprechen oder ein leeres Set wenn keine Speisen gefunden wurden
	 */
	public Set<Speise> getSpeisen(SpeiseArt[] speiseArten) {
		Set<Speise> returnSet = new LinkedHashSet<Speise>();
		for(Speise s : speisen) {
			for(SpeiseArt art : speiseArten) {
				if(s.getArt() == art) returnSet.add(s);
			}
		}
		return returnSet;
	}

	public void setSpeisen(Set<Speise> speisen) {
		this.speisen = speisen;
	}

	public Set<Zusatzstoff> getZusatzstoffe() {
		return zusatzstoffe;
	}

	public void setZusatzstoffe(Set<Zusatzstoff> zusatzstoffe) {
		this.zusatzstoffe = zusatzstoffe;
	}

	public String serialize() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}