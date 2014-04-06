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

	public HashMap<String, Integer> getResultat(double latPointBasGauche, double lngPointBasGauche, double latPointHautDroit, double lngPointHautDroit) {
		try {
			URL url = new URL(
					"https://api.jcdecaux.com/vls/v1/stations?apiKey=978fcf6054ef101994ef845d8ff434445bc3d00c");
			InputStream is = url.openStream();
			JsonReader velosFrance = Json.createReader(is);
			JsonArray listeVelosFrance = velosFrance.readArray();
			for (JsonObject result : listeVelosFrance.getValuesAs(JsonObject.class)) {
				String ville = result.getString("contract_name");
				String status = result.getString("status");
				
				if(ville.equals("Toulouse") && status.equals("OPEN")){
					JsonObject position = result.getJsonObject("position");
					JsonNumber lat  = position.getJsonNumber("lat");
					double latdouble = lat.doubleValue();
					JsonNumber lng  = position.getJsonNumber("lng");
					double lngdouble = lng.doubleValue();
					
					if(latdouble > latPointBasGauche && latdouble < latPointHautDroit && lngdouble > lngPointBasGauche && lngdouble < lngPointHautDroit) {
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
	
	public HashMap<String, Double> getStationPlusProche(double destlat, double destlng) {
		double reslat;
		double reslng;
		String resName = null;
		int resNbVelos;
		int resNbPlacesLibres;
		double resdist=500000000.0;
		try {
			URL url = new URL(
					"https://api.jcdecaux.com/vls/v1/stations?apiKey=978fcf6054ef101994ef845d8ff434445bc3d00c");
			InputStream is = url.openStream();
			JsonReader velosFrance = Json.createReader(is);
			JsonArray listeVelosFrance = velosFrance.readArray();
			for (JsonObject result : listeVelosFrance.getValuesAs(JsonObject.class)) {
				String ville = result.getString("contract_name");
				String status = result.getString("status");
				if(ville.equals("Toulouse") && status.equals("OPEN")){
					JsonObject position = result.getJsonObject("position");
					JsonNumber lat  = position.getJsonNumber("lat");
					double latdouble = lat.doubleValue();
					JsonNumber lng  = position.getJsonNumber("lng");
					double lngdouble = lng.doubleValue();
					
					if( ItineraireResultat.distanceVolOiseauEntre2PointsSansPrécision(destlat, destlng, lat.doubleValue(), lng.doubleValue()) < resdist) {
						resNbVelos = result.getInt("available_bikes");
						resNbPlacesLibres =  result.getInt("available_bike_stands");
						resName = result.getString("name");
						resdist = ItineraireResultat.distanceVolOiseauEntre2PointsSansPrécision(destlat, destlng, lat.doubleValue(), lng.doubleValue());
					}
				}
			}
		} catch (IOException e) {// handle exceptions
			System.out.println("Error reading file!");
		}

		resdist = ItineraireResultat.distanceVolOiseauEntre2PointsSansPrécision(destlat, destlng, Double.parseDouble(ItineraireResultat.yDepart), Double.parseDouble(ItineraireResultat.xDepart));
		HashMap<String, Double> resreturn = new HashMap<String, Double>();
		resreturn.put(resName, resdist);
		return resreturn;
	}
}
