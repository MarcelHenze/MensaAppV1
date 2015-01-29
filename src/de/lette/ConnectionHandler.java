package de.lette;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;

import android.util.Log;

import com.google.gson.Gson;

import de.lette.mensaplan.ClientData;
import de.lette.mensaplan.Speise;
import de.lette.mensaplan.SpeiseArt;
import de.lette.mensaplan.Termin;
import de.lette.mensaplan.Zusatzstoff;

public class ConnectionHandler {
	public static String urlToServer = "";
	private static ClientData data;
	
	public static ClientData getClientData() throws ClientProtocolException, IOException, URISyntaxException {
//		data = getClientDataFromServer();
		if(data != null) return data;
		data = new ClientData();

		Set<Speise> alleSpeisen = new LinkedHashSet<Speise>();
		Speise s = new Speise(1, "Hamburger", SpeiseArt.VOLLKOST, false,
				"Fleisch", 9000, 9000, 9000, 9000);
		s.getZusatzstoffe().add(1);
		s.getZusatzstoffe().add(2);
		alleSpeisen.add(s);
		alleSpeisen.add(new Speise(2, "Caesar Salat", SpeiseArt.VORSPEISE,
				false, "Salat", 100, 100, 100, 100));
		alleSpeisen.add(new Speise(3, "Suppe", SpeiseArt.VORSPEISE, false,
				"Suppe", 200, 200, 200, 200));
		alleSpeisen.add(new Speise(4, "Steak", SpeiseArt.VOLLKOST, false,
				"Fleisch", 9000, 9000, 9000, 9000));
		alleSpeisen.add(new Speise(5, "Bio Burger", SpeiseArt.VOLLKOST, true,
				"Bio Fleisch", 0, 0, 0, 0));
		alleSpeisen.add(new Speise(6, "Eis im Eimer", SpeiseArt.DESSERT, false,
				"Fleisch", 9000, 9000, 9000, 9000));
		data.setSpeisen(alleSpeisen);

//		Log.d("MensaPlan", "Speisen:");
//		for(Speise sp : alleSpeisen) Log.d("MensaPlan", sp.getName());
		
		Set<Termin> termine = new LinkedHashSet<Termin>();
		termine.add(new Termin(1, new Date(120000), 1, new BigDecimal(3.75), false));
		termine.add(new Termin(2, new Date(125000), 2, new BigDecimal(2.75), true));
		termine.add(new Termin(3, new Date(127500), 3, new BigDecimal(1.75), true));
		termine.add(new Termin(4, new Date(130000), 4, new BigDecimal(1.75), false));
		termine.add(new Termin(5, new Date(130000), 5, new BigDecimal(2.00), false));
		termine.add(new Termin(6, new Date(130000), 6, new BigDecimal(2.50), false));
		data.setTermine(termine);

//		Log.d("MensaPlan", "Termine:");
//		for(Termin t : termine) Log.d("MensaPlan", "" + t.getDatum());
		
		Set<Zusatzstoff> zusätze = new LinkedHashSet<Zusatzstoff>();
		zusätze.add(new Zusatzstoff(1, 1, "Kohlenstoffdioxid"));
		zusätze.add(new Zusatzstoff(2, 2, "Kohlenstofftrioxid"));
		zusätze.add(new Zusatzstoff(3, 3, "Zucker"));
		data.setZusatzstoffe(zusätze);

//		Log.d("MensaPlan", "Zusätze:");
//		for(Zusatzstoff z : zusätze) Log.d("MensaPlan", "" + z.getName());
		
		return data;
	}

	/**
	 * Diese Methode stellt eine Verbindung zum MensaplanServer her, lädt die gewünschten Daten herunter und gibt sie als ClientData-Objekt zurück.
	 * 
	 * @return das ClientData-Objekt
	 * @throws IOException
	 *             wenn die Verbindung mit dem Server nicht aufgebaut werden konnte oder Fehler beim Lesen auftreten.
	 * @throws URISyntaxException
	 */
	private static ClientData getClientDataFromServer() throws IOException, URISyntaxException, ClientProtocolException {
		// Prepare Connection //
		URL url = new URL(urlToServer);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

		// Read Stream //
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String result = "";
		String currentLine = "";
		while((currentLine = br.readLine()) != null) {
			result += currentLine;
		}
		Log.d("MensaplanData", "" + result);
		br.close();

		// Get the ClientData
		Gson gson = new Gson();
		return gson.fromJson(result, ClientData.class);
	}
}