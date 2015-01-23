package de.lette;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;

import android.util.Log;

import com.google.gson.Gson;

import de.lette.mensaplan.ClientData;

public class ConnectionHandler {
	public static String urlToServer = "http://localhost:8080/MensaPlan/AppRequestHandler?startdate=2014-01-08";

	/**
	 * Diese Methode stellt eine Verbindung zum MensaplanServer her und läd die gewünschten Daten herunter und gibt sie als ClientData-Objekt zurück.
	 * 
	 * @return das ClientData-Objekt
	 * @throws IOException
	 *             wenn die Verbindung mit dem Server nicht aufgebaut werden konnte oder Fehler beim Lesen auftreten.
	 * @throws URISyntaxException
	 */
	public static ClientData getClientDataFromServer() throws IOException, URISyntaxException, ClientProtocolException {
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