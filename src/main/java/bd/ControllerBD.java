package bd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ControllerBD {

	@Autowired
	LigneService ligneService;

	/*
	@RequestMapping("/register")
	public ModelAndView registerUser(@ModelAttribute Ligne ligne) {

		List<String> genderList = new ArrayList<String>();
		genderList.add("male");
		genderList.add("female");

		List<String> cityList = new ArrayList<String>();
		cityList.add("delhi");
		cityList.add("gurgaon");
		cityList.add("meerut");
		cityList.add("noida");

		Map<String, List> map = new HashMap<String, List>();
		map.put("genderList", genderList);
		map.put("cityList", cityList);
		return new ModelAndView("register", "map", map);
	}
	 */
	@RequestMapping("/insert")
	public String inserData(@ModelAttribute Ligne ligne) {
		if (ligne != null)
			ligneService.insertData(ligne);
		return "redirect:/getList";
	}

	
	@RequestMapping("/getList")
	public ModelAndView getLigneList() {
		List<Ligne> ligneList = ligneService.getLigneList();
		return new ModelAndView("ligneList", "ligneList", ligneList);
	}

	/*
	@RequestMapping("/edit")
	public ModelAndView editUser(@RequestParam String id,
			@ModelAttribute User user) {

		user = userService.getUser(id);

		List<String> genderList = new ArrayList<String>();
		genderList.add("male");
		genderList.add("female");

		List<String> cityList = new ArrayList<String>();
		cityList.add("delhi");
		cityList.add("gurgaon");
		cityList.add("meerut");
		cityList.add("noida");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("genderList", genderList);
		map.put("cityList", cityList);
		map.put("user", user);

		return new ModelAndView("edit", "map", map);

	}
	*/

	@RequestMapping("/update")
	public String updateUser(@ModelAttribute Ligne ligne) {
		ligneService.updateData(ligne);
		return "redirect:/getList";

	}
	/*
	@RequestMapping("/delete")
	public String deleteUser(@RequestParam String id) {
		System.out.println("id = " + id);
		userService.deleteData(id);
		return "redirect:/getList";
	}
	*/
}