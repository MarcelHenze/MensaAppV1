package de.lette.mensaplan;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
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
	
	/**
	 * Sucht die Speise für den gegebenen Termin und gibt sie zurück.
	 * 
	 * @param termin
	 *            der Termin, zu dem die Speise gefunden werden soll
	 * @param speisen
	 *            die Speisen, die durchsucht werden müssen
	 * @return die Speise zu dem Termin oder null, falls keine Speise zum Termin existiert.
	 */
	public Speise getSpeisen(Termin termin, Set<Speise> speisen) {
		for(Speise s : speisen) {
			if(termin.getSpeiseId() == s.getId()) {
				return s;
			}
		}
		return null;
	}
	
	/**
	 * Sucht alle Speisen für die gegebenen Termine und gibt sie in einer Map zurück.
	 * Jedem Termin sind n Speisen zugeordnet.
	 * 
	 * @param termine
	 *            die Termine, zu denen die Speisen gesucht werden sollen
	 * @param speisen
	 *            die Speisen, die durchsucht werden müssen
	 * @return eine Map mit den Speisen zu den Terminen oder eine leere Map wenn keine Speisen zu Terminen gefunden wurden
	 */
	public Map<Termin, Speise> getSpeisen(Termin[] termine, Set<Speise> speisen) {
		Map<Termin, Speise> returnMap = new LinkedHashMap<Termin, Speise>();
		for(Termin t : termine) {
			for(Speise s : speisen) {
				if(t.getSpeiseId() == s.getId()) {
					returnMap.put(t, s);
				}
			}
		}
		return returnMap;
	}

	public void setSpeisen(Set<Speise> speisen) {
		this.speisen = speisen;
	}

	public Set<Zusatzstoff> getZusatzstoffe() {
		return zusatzstoffe;
	}
	
	/**
	 * Sucht zu einer Speise alle Zusatzstoffe und gibt sie in einem Set zurück.
	 * 
	 * @param speise die Speise, deren Zusatzstoffe ermittelt werden sollen.
	 * @return die Zusatzstoffe zu der Speise in einem Set oder ein leeres Set wenn keine Zusatzstoffe gefunden wurden.
	 */
	public Set<Zusatzstoff> getZusatzstoffe(Speise speise) {
		Set<Zusatzstoff> returnSet = new LinkedHashSet<Zusatzstoff>();
		for(Zusatzstoff z : zusatzstoffe) {
			for(Integer zI : speise.getZusatzstoffe()) {
				if(zI == z.getNummer()) {
					returnSet.add(z);
				}
			}
		}
		return returnSet;
	}

	public void setZusatzstoffe(Set<Zusatzstoff> zusatzstoffe) {
		this.zusatzstoffe = zusatzstoffe;
	}

	public String serialize() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}