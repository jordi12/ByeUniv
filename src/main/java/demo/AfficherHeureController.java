package demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AfficherHeureController {
	 @RequestMapping("/afficherheure")
	    public String heure(@RequestParam(value="line", required=true) String line,
	    		@RequestParam(value="name", required=true) String name,Model model) {
	        model.addAttribute("line", line);
	        model.addAttribute("name", name);
	        return "afficherheure";
	 }

}
