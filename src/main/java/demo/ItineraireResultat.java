package demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class ItineraireResultat {

	// http://pt.data.tisseo.fr/stopPointsList?stopAreaId=1970324837184714&displayLines=1&format=json&key=a03561f2fd10641d96fb8188d209414d8

	private HashMap<String, String> listeLignesArrivee;
	private HashMap<String, String> listeLignesDepart;
	private HashMap<String, String> listeLignesCommunes;
	private String xDepart;
	private String yDepart;

	public ItineraireResultat() {
		listeLignesArrivee = new HashMap<String, String>();
		listeLignesDepart = new HashMap<String, String>();
		//bbox depart : 1.461593 43.557055 1.467988 43.570054 -> on prend les milieux de la bbox
		xDepart="1.464790";
		yDepart="43.563554";
	}

	public HashMap<String, String> getResultat(String idStopAreaArrivee) {
		listeLignesArrivee = getLignes(idStopAreaArrivee, listeLignesArrivee);
		listeLignesDepart = getLignesDepart(listeLignesArrivee);
		
		listeLignesCommunes = matchLignes(listeLignesDepart, listeLignesArrivee);
		
		// on doit calculer la distance grace à la méthode passé par kevin
		// on doit choisir le plus rapide
		
		return null;
	}

	private HashMap<String, String> matchLignes(
			HashMap<String, String> listeLignesDepart2,
			HashMap<String, String> listeLignesArrivee2) {
		
			HashMap<String, String> res = listeLignesArrivee2;
			
			for (String mapKey : listeLignesDepart2.keySet()) {
				res.put(mapKey, listeLignesDepart2.get(mapKey));
			}
		
		return res;
	}

	private HashMap<String, String> getLignesDepart(
			HashMap<String, String> listeLignes) {

		RequeteLigne rl = new RequeteLigne();
		HashMap<String, String> resrl = rl.getResultat();

		for (String mapKey : resrl.keySet())
			listeLignes = getLignes(mapKey, listeLignes);

		return listeLignes;
	}

	public HashMap<String, String> getLignes(String idStopArea,
			HashMap<String, String> listeLignes) {

		try {
			URL url = new URL(
					"http://pt.data.tisseo.fr/stopPointsList?stopAreaId="
							+ idStopArea
							+ "&displayLines=1&format=json&key=a03561f2fd10641d96fb8188d209414d8");
			InputStream is = url.openStream();
			JsonReader rdr = Json.createReader(is);
			JsonObject obj = rdr.readObject();
			JsonObject physicalStops = obj.getJsonObject("physicalStops");
			JsonArray physicalStop = physicalStops.getJsonArray("physicalStop");
			for (JsonObject resultphysicalStop : physicalStop
					.getValuesAs(JsonObject.class)) {
				JsonArray destinations = resultphysicalStop
						.getJsonArray("destinations");
				for (JsonObject resultdestinations : destinations
						.getValuesAs(JsonObject.class)) {
					JsonArray line = resultdestinations.getJsonArray("line");
					for (JsonObject resultline : line
							.getValuesAs(JsonObject.class)) {
						listeLignes.put(resultline.getString("id"),
								resultline.getString("shortName"));

					}
				}
			}

		} catch (IOException e) {// handle exceptions

			System.out.println("Error reading file!");

		}

		return listeLignes;
	}

}
