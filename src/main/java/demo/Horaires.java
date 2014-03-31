package demo;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Horaires {

	private ArrayList<Horaire> listeHoraires;

	public Horaires() {
		listeHoraires = new ArrayList<Horaire>();
	}

	public ArrayList<Horaire> getResultat(String id) {
		String ligne = "";
		String heure = "";
		try {
			URL url = new URL(
					"http://pt.data.tisseo.fr/departureBoard?stopPointId="
							+ id
							+ "&format=json&key=a03561f2fd10641d96fb8188d209414d8");
			InputStream is = url.openStream();
			System.out.println("test1");
			JsonReader rdr = Json.createReader(is);
			JsonObject obj = rdr.readObject();
			JsonObject results0 = obj.getJsonObject("departures");
			JsonArray results = results0.getJsonArray("departure");
			for (JsonObject result : results.getValuesAs(JsonObject.class)) {
				System.out.println("test2 : " + result);
				try {
					System.out.println("test3 : " + result);
					heure = result.getString("dateTime");
					ligne = result.getJsonObject("line").getString("shortName");
					listeHoraires.add(new Horaire(ligne, heure));
				} catch (Exception e) {// handle exceptions
					System.out.println("Error reading file!");
				}
			}
		} catch (Exception e) {// handle exceptions
			System.out.println("Error reading file!");
		}

		System.out.println(listeHoraires.toString());
		return listeHoraires;

	}

}