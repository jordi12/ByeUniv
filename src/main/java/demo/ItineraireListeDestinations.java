package demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class ItineraireListeDestinations {
	
	private HashMap<String,CoordonneArret> listeArrets;

	public ItineraireListeDestinations() {
		listeArrets = new HashMap<String, CoordonneArret>();
	}

		

	public HashMap<String, CoordonneArret> getResultat(String termeRecherche) {
		String id;
		CoordonneArret coor;
		try {
			URL url = new URL(
					"http://pt.data.tisseo.fr/placesList?term="+termeRecherche+"&displayOnlyStopAreas=1&format=json&key=a03561f2fd10641d96fb8188d209414d8");
			InputStream is = url.openStream();
			JsonReader rdr = Json.createReader(is);
			JsonObject obj = rdr.readObject();
			JsonObject results0 = obj.getJsonObject("placesList");
			JsonArray results = results0.getJsonArray("place");
			for (JsonObject result : results.getValuesAs(JsonObject.class)) {
				id = result.getString("id");
				coor = new CoordonneArret(result.getString("x"), result.getString("y"), result.getString("label"));
				listeArrets.put(id,coor);

			}
			System.out.println(listeArrets.toString());

		} catch (IOException e) {// handle exceptions

			System.out.println("Error reading file!");

		}

		return listeArrets;
	}
}
