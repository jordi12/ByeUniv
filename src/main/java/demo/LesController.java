package demo;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LesController {

	@RequestMapping("/greeting")
	public String greeting(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
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

	@RequestMapping("/arrets")
	public String arrets(Model model) {
		RequeteLigne rl = new RequeteLigne();
		HashMap<String, String> resrl = rl.getResultat();

		String res = "<div class=\"ui-grid-a ui-responsive\"><br/>"
				+ "<div class=\"ui-block-a\"><br/>";
		for (String mapKey : resrl.keySet()) {
			res = res
					+ "<a data-role=\"button\" href="
					+ "\"http://localhost:8080/horaires?id="
					+ mapKey
					+ "\" data-ajax= \"false\" data-transition=\"pop\" data-theme=\"b\">"
					+ resrl.get(mapKey) + "</a><br/>";

		}
		res = res + "</div><br/></div><br/>";

		System.out.println(res);

		model.addAttribute("arrets", res);
		return "arrets";
	}

	@RequestMapping("/horaires")
	public String horaires(
			@RequestParam(value = "id", required = true) String id, Model model) {
		Horaires hs = new Horaires();
		ArrayList<Horaire> reshs = hs.getResultat(id);

		String res = "<div class=\"ui-grid-a ui-responsive\"><br/>"
				+ "<div class=\"ui-block-a\"><br/>";
		for (int i = 0; i < reshs.size(); i++) {
			res = res
					+ "<a data-role=\"button\"" /* +href=" + "lien" + */
					+ " data-ajax= \"false\" data-transition=\"pop\" data-theme=\"a\">"
					+ " Ligne : " + reshs.get(i).getLigne() + " Horaire : "
					+ reshs.get(i).getHeure() + "</a><br/>";

		}
		res = res + "</div><br/></div><br/>";

		System.out.println(res);

		model.addAttribute("horaires", res);
		return "horaires";
	}
}
