package demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import BD.Connect;
import BD.RequetesBD;

@Controller
public class Controlleurs {

	@RequestMapping("/")
	public String start(Model model) {
		return "start";
	}

	@RequestMapping("/itineraire")
	public String itineraire(Model model) {
		return "itineraire";
	}

	@RequestMapping("/updateVote")
	public String updateVote(
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "like", required = true) String like,
			Model model) throws SQLException {

		Connect connection = new Connect();
		Connection conn = connection.connectionDB();
		Statement state = conn.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		// On va chercher une ligne dans la base de données
		ResultSet result = state
				.executeQuery("SELECT * FROM lignebus WHERE cast(id_ligne AS bigint)="
						+ id);
		result.first();
		int nb_likes = result.getInt("likes");

		// On met à jour les champs
		if (like.equals("true")) {
			result.updateInt("likes", nb_likes + 1);
		} else {
			result.updateInt("likes", nb_likes - 1);
		}

		// On valide
		result.updateRow();

		result.close();
		state.close();

		return "redirect:/vote";
	}

	@RequestMapping("/vote")
	public String vote(Model model) throws SQLException {
		Connect connection = new Connect();
		Connection conn = connection.connectionDB();
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM lignebus");

		String html = "<div><br/>" + "<div><br/>";

		while (result.next()) {
			html = html
					+ "<a data-role=\"button\" "
					+ "\" data-ajax= \"false\" data-transition=\"pop\" data-theme=\"c\">"
					+ " Ligne : " + result.getString("short_name")
					+ " Likes : " + result.getInt("likes") + "</a>";
			html = html
					+ "<a data-role=\"button\" href="
					+ "\"http://localhost:8080/updateVote?id="
					+ result.getString("id_ligne")
					+ "&like=true"
					+ "\" data-ajax= \"false\" data-transition=\"pop\" data-theme=\"b\">"
					+ "+1" + "</a>";
			html = html
					+ "<a data-role=\"button\" href="
					+ "\"http://localhost:8080/updateVote?id="
					+ result.getString("id_ligne")
					+ "&like=false"
					+ "\" data-ajax= \"false\" data-transition=\"pop\" data-theme=\"a\">"
					+ "-1" + "</a>";
			html = html + "<br/>";
		}
		html = html + "</div><br/></div><br/>";
		result.close();
		state.close();

		model.addAttribute("vote", html);
		return "vote";
	}

	@RequestMapping("/afficherheure")
	public String heure(
			@RequestParam(value = "line", required = true) String line,
			@RequestParam(value = "name", required = true) String name,
			Model model) {
		model.addAttribute("line", line);
		model.addAttribute("name", name);
		return "afficherheure";
	}

	@RequestMapping("/itineraireListeArrets")
	public String itineraireListArrets(
			@RequestParam(value = "terme", required = true) String terme,
			Model model) {

		ItineraireListeDestinations ild = new ItineraireListeDestinations();
		HashMap<String, CoordonneArret> resild = ild.getResultat(terme);

		String res = "<div><br/>"
				+ "<div><br/>";
		for (String mapKey : resild.keySet()) {
			res = res
					+ "<a data-role=\"button\" href="
					+ "\"http://localhost:8080/itineraireResultat?id="
					+ mapKey
					+ "&x="
					+ resild.get(mapKey).getX()
					+ "&y="
					+ resild.get(mapKey).getY()
					+ "&label="
					+ resild.get(mapKey).getLabel()
					+ "\" data-ajax= \"false\" data-transition=\"pop\" data-theme=\"b\">"
					+ resild.get(mapKey).getLabel() + "</a><br/>";

		}
		res = res + "</div><br/></div><br/>";

		model.addAttribute("itineraireListeArrets", res);

		return "itineraireListeArrets";
	}

	@RequestMapping("/arrets")
	public String arrets(Model model) {
		StopAreas rl = new StopAreas();
		HashMap<String, String> resrl = rl.getResultat();

		String res = "<div><br/>"
				+ "<div><br/>";
		for (String mapKey : resrl.keySet()) {
			res = res
					+ "<a data-role=\"button\" href="
					+ "\"http://localhost:8080/horaires?id="
					+ mapKey
					+ "\" data-ajax= \"false\" data-transition=\"pop\" data-theme=\"b\">"
					+ resrl.get(mapKey) + "</a><br/>";

		}
		res = res + "</div><br/></div><br/>";

		model.addAttribute("arrets", res);
		return "arrets";
	}

	@RequestMapping("/horaires")
	public String horaires(
			@RequestParam(value = "id", required = true) String id, Model model) {
		Horaires hs = new Horaires();
		ArrayList<Horaire> reshs = hs.getResultat(id);

		String res = "<div><br/>"
				+ "<div><br/>";
		for (int i = 0; i < reshs.size(); i++) {
			res = res
					+ "<a data-role=\"button\"" /* +href=" + "lien" + */
					+ " data-ajax= \"false\" data-transition=\"pop\" data-theme=\"c\">"
					+ " Ligne : " + reshs.get(i).getLigne() + " Horaire : "
					+ reshs.get(i).getHeure() + "</a><br/>";

		}
		res = res + "</div><br/></div><br/>";

		model.addAttribute("horaires", res);
		return "horaires";
	}

	@RequestMapping("/velo")
	public String velo(Model model) {
		VeloDisponible vd = new VeloDisponible();

		HashMap<String, Integer> resvd = vd.getResultat(43.557055, 1.461593,
				43.570054, 1.467988);

		String res = "<div><br/>"
				+ "<div><br/>";
		for (String mapKey : resvd.keySet()) {
			res = res
					+ "<a data-role=\"button\" data-ajax= \"false\" data-transition=\"pop\" data-theme=\"c\">"
					+ mapKey + " Vélos disponibles : "
					+ resvd.get(mapKey).toString() + "</a><br/>";

		}
		res = res + "</div><br/></div><br/>";

		model.addAttribute("velo", res);
		return "velo";
	}

	@RequestMapping("/itineraireResultat")
	public String itineraireResultat(
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "x", required = true) String x,
			@RequestParam(value = "y", required = true) String y,
			@RequestParam(value = "label", required = true) String label,
			Model model) throws SQLException {
		ItineraireResultat ir = new ItineraireResultat();
		ArrayList<Horaire> resir = ir.getResultat(id, x, y);
		RequetesBD bd = new RequetesBD();
		HashMap<String, Double> resirV = ir.getResultatVelo(x, y);

		double tempsBus = ItineraireResultat.calculTempToMinute(
				ir.getDistance(), 30);
		double tempsVelo = 0;
		for (String mapKey : resirV.keySet()) {
			tempsVelo = ItineraireResultat.calculTempToMinute(
					resirV.get(mapKey), 12);
		}

		String res = "<div class=\"ui-grid-a ui-responsive\"><br/>"
				+ "<div class=\"ui-block-a\">";
		for (int i = 0; i < resir.size(); i++) {
			res = res
					+ "<a data-role=\"button\"" /* +href=" + "lien" + */
					+ " data-ajax= \"false\" data-transition=\"pop\" data-theme=\"c\">"
					+ " Station départ : " + resir.get(i).getLabelLieu()
					+ " Temps estimé : " + (int) tempsBus + " minutes <br/>"
					+ " Ligne : " + resir.get(i).getLigne() + " ("
					+ bd.getLikes(resir.get(i).getLigne()) + " Likes) "
					+ " Horaire : " + resir.get(i).getHeure() + "</a><br/>";

		}

		res = res + "</div>" + "<div class=\"ui-block-b\">";

		res = res
				+ "<a data-role=\"button\"" /* +href=" + "lien" + */
				+ " data-ajax= \"false\" data-transition=\"pop\" data-theme=\"c\">"
				+ " Vélo " + "<br/>" + " Temps estimé : " + (int) tempsVelo
				+ " minutes <br/>" + "</a><br/>";

		res = res + "</div>" + "</div>";

		model.addAttribute("itineraireResultat", res);
		return "itineraireResultat";
	}
}
