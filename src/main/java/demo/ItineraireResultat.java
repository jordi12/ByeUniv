package demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
	private String xArrivee;
	private String yArrivee;

	public ItineraireResultat() {
		listeLignesArrivee = new HashMap<String, String>();
		listeLignesDepart = new HashMap<String, String>();
		// bbox depart : 1.461593 43.557055 1.467988 43.570054 -> on prend les
		// milieux de la bbox
		xDepart = "1.464790";
		yDepart = "43.563554";
	}

	public ArrayList<Horaire> getResultat(String idStopAreaArrivee, String xArr, String yArr) {
		listeLignesArrivee = getLignes(idStopAreaArrivee, listeLignesArrivee);
		listeLignesDepart = getLignesDepart(listeLignesDepart);

		listeLignesCommunes = matchLignes(listeLignesDepart, listeLignesArrivee);

		xArrivee=xArr;
		yArrivee=yArr;
		
		// on doit calculer la distance grace à la méthode
		double distanceDepartArriveeVolOiseau = distanceVolOiseauEntre2PointsSansPrécision(
				Double.parseDouble(xDepart), Double.parseDouble(yDepart),
				Double.parseDouble(xArrivee), Double.parseDouble(yArrivee));
		// on doit choisir le plus rapide :
		// pour ça on récupère les prochains départs
		ArrayList<Horaire> horairesDepart = new ArrayList<Horaire>();
		horairesDepart = horairesDepart();
		// on les classe par ordre et on garde que ceux correspondant à la
		// listeCommune

		System.out.println("getResultat : avant tribulle nb result "+ horairesDepart.size());
		horairesDepart = triLignes(horairesDepart, listeLignesCommunes);
		
		return horairesDepart;
	}
	
	public HashMap<String, Double> getResultatVelo(String xArr, String yArr) {
		VeloDisponible vd = new VeloDisponible();
		return vd.getStationPlusProche(Double.valueOf(xArr), Double.valueOf(yArr));
	}

	/**
	 * @param distance en km
	 * @param vitesse en km/h
	 * @return
	 */
	public static int calculTemp() {
		return 0;
		
	}
	
	private ArrayList<Horaire> triLignes(ArrayList<Horaire> horairesDepart,
			HashMap<String, String> listeLignesCommunes2) {

		ArrayList<Horaire> res = new ArrayList<Horaire>();
		
		
		System.out.println("HORAIRE DEPART : " + horairesDepart.toString());
		System.out.println("Ligne communes : " + listeLignesCommunes2.toString());
		// on garde les communs
		for (int i = 0; i < horairesDepart.size(); i++){
			for (String mapKey : listeLignesCommunes2.keySet()) {
				//System.out.println("LISTE COMMUNES" + listeLignesCommunes2.get(mapKey).toString());
				if (listeLignesCommunes2.get(mapKey).equals(horairesDepart.get(i).getLigne())) {
					res.add(horairesDepart.get(i));
				}
			}
		}

		// on trie par horaire PROBLEME POUR REMPLIR RES
		//System.out.println("RES RES RES" + res.toString());
		res=tribulles(res);
		return res;
	}

	public ArrayList<Horaire> tribulles(ArrayList<Horaire> horairesDepart) {
		for (int i = 0; i <= (horairesDepart.size() - 2); i++)
			for (int j = (horairesDepart.size() - 1); i < j; j--) {
				String s1 = horairesDepart.get(j).getHeure();
				Integer heures1 = Integer.parseInt((String) s1.subSequence(11,
						13));
				Integer minutes1 = Integer.parseInt((String) s1.subSequence(14,
						16));
				Integer secondes1 = Integer.parseInt((String) s1.subSequence(
						17, 19));
				String s2 = horairesDepart.get(j - 1).getHeure();
				Integer heures2 = Integer.parseInt((String) s2.subSequence(11,
						13));
				Integer minutes2 = Integer.parseInt((String) s2.subSequence(14,
						16));
				Integer secondes2 = Integer.parseInt((String) s2.subSequence(
						17, 19));
				if ((heures1 < heures2)
						|| (heures1 == heures2 && minutes1 < minutes2)
						|| (heures1 == heures2 && minutes1 == minutes2 && secondes1 < secondes2)) {
					Horaire aux = horairesDepart.get(j - 1);
					horairesDepart.set(j - 1, horairesDepart.get(j));
					horairesDepart.set(j, aux);
				}
			}
		//System.out.println("BULLE BULLE BULLE" + horairesDepart.toString());
		return horairesDepart;
	}

	private HashMap<String, String> matchLignes(
			HashMap<String, String> listeLignesDepart2,
			HashMap<String, String> listeLignesArrivee2) {

		HashMap<String, String> res = new HashMap<String,String>();

		for (String mapKeyArr : listeLignesArrivee2.keySet()) {
			for (String mapKeyDep : listeLignesDepart2.keySet()) {
				if(mapKeyArr.equals(mapKeyDep)) {
					res.put(mapKeyDep, listeLignesDepart2.get(mapKeyDep));
				}
			}	
		}
		//System.out.println("res : avant tribulle nb result "+ res.toString());
		//System.out.println("listeLignesArrivee2 : avant tribulle nb result "+ listeLignesArrivee2.toString());
		//System.out.println("listeLignesDepart2 : avant tribulle nb result "+ listeLignesDepart2.toString());

		return res;
	}

	public ArrayList<Horaire> horairesDepart() {
		Horaires h = new Horaires();
		RequeteLigne rl = new RequeteLigne();
		HashMap<String, String> listIdStopArea = rl.getResultat();
		ArrayList<Horaire> listHoraires = new ArrayList<Horaire>();

		for (String idArea : listIdStopArea.keySet()) {
			
			listHoraires.addAll(h.getResultat(idArea));
			//listHoraires.addAll(h.getResultat(idArea));
		}
		
		// Créer une liste de contenu unique basée sur les éléments de ArrayList
	    Set<Horaire> enleverDoublons = new HashSet<Horaire>(listHoraires);
	 
	    // Créer une Nouvelle ArrayList à partir de Set
	    ArrayList<Horaire> listeHoraireSansDoublons = new ArrayList<Horaire>(enleverDoublons);
		//System.out.println("coucou" + listIdStopArea.toString());
		//System.out.println("listeHoraireSansDoublons" + listeHoraireSansDoublons.toString());
		return listeHoraireSansDoublons;

	}

	private HashMap<String, String> getLignesDepart(
			HashMap<String, String> listeLignes) {

		RequeteLigne rl = new RequeteLigne();
		HashMap<String, String> resrl = rl.getResultat();

		for (String mapKey : resrl.keySet()){
			listeLignes = getLignes(mapKey, listeLignes);
		}
		//System.out.println("lol" + listeLignes.toString());
		return listeLignes;
	}

	public HashMap<String, String> getLignes(String idStopArea,
			HashMap<String, String> listeLignes) {
		//1970324837184714
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
		//System.out.println("lol" + listeLignes.toString());
		return listeLignes;
	}

	/**
	 * Distance entre 2 points GPS
	 * http://dotclear.placeoweb.com/post/Formule-de-
	 * calcul-entre-2-points-wgs84-
	 * pour-calculer-la-distance-qui-separe-ces-deux-points
	 * 
	 * La distance mesurée le long d'un arc de grand cercle entre deux points
	 * dont on connaît les coordonnées {lat1,lon1} et {lat2,lon2} est donnée par
	 * : d = acos(sin(lat1)*sin(lat2)+cos(lat1)*cos(lat2)*cos(lon1-lon2)) Le
	 * tout * 6366 pour l'avoir en km
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	public static double distanceVolOiseauEntre2PointsSansPrécision(
			double lat1, double lon1, double lat2, double lon2) {

		// d=acos(sin(lat1)*sin(lat2)+cos(lat1)*cos(lat2)*cos(lon1-lon2))

		return Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1)
				* Math.cos(lat2) * Math.cos(lon1 - lon2));

	}

}
