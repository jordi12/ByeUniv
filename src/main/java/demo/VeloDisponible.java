package demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class VeloDisponible {

	private HashMap<String, Integer> listeStationVelo;

	public VeloDisponible() {

		listeStationVelo = new HashMap<String, Integer>();
	}

	/*
	 * 1.461593 43.557055 1.467988 43.570054
	 * coordonnées dans lesquelles on récupère les stations de vélo
	 */
	public HashMap<String, Integer> getResultat() {
		try {
			URL url = new URL(
					"https://api.jcdecaux.com/vls/v1/stations?apiKey=978fcf6054ef101994ef845d8ff434445bc3d00c");
			InputStream is = url.openStream();
			JsonReader velosFrance = Json.createReader(is);
			JsonArray listeVelosFrance = velosFrance.readArray();
			for (JsonObject result : listeVelosFrance.getValuesAs(JsonObject.class)) {
				String ville = result.getString("contract_name");
				String status = result.getString("status");
				//System.out.println(status + ville);
				if(ville.equals("Toulouse") && status.equals("OPEN")){
					JsonObject position = result.getJsonObject("position");
					JsonNumber lat  = position.getJsonNumber("lat");
					double latdouble = lat.doubleValue();
					JsonNumber lng  = position.getJsonNumber("lng");
					double lngdouble = lng.doubleValue();
					
					System.out.println(lat.toString() + " " + lng.toString()+"\n");
					if(latdouble > 43.557055 && latdouble < 43.570054 && lngdouble > 1.461593 && lngdouble < 1.467988) {
						int avalaibleBikes = result.getInt("available_bikes");
						String name = result.getString("name");
						listeStationVelo.put(name, avalaibleBikes);
					}
				}
			}
		} catch (IOException e) {// handle exceptions
			System.out.println("Error reading file!");
		}

		return listeStationVelo;

	}
}
