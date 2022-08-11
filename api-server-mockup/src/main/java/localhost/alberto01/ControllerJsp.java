package localhost.alberto01;

// curl -s -i -X 'GET' -H 'content-type:application/json' --url 'http://localhost:4000/jsp/'

import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ControllerJsp {

	private static final Logger log = LoggerFactory.getLogger(ControllerJsp.class);
	
	
	@GetMapping(path="0")
	public String first_index( Map<String, Object> model) {
		try {
			log.info("................ INICIO JSP JSP ................");
			model.put("time", new Date());
			model.put("message", "Hello Alberto");
			log.info("................ FIN JSP JSP ................");
			return "welcome";
		} catch (Exception e) {
			log.error("error: ", e);
			log.error("................ ERROR FIN JSP INDEX ................");
			return "welcome";
		}
	}

	
	@GetMapping(path="1")
	public String second_index( Map<String, Object> model) {
		try {
			log.info("................ INICIO JSP JSP ................");
			model.put("time", new Date());
			model.put("message", "Hello Alberto");
			log.info("................ FIN JSP JSP ................");
			return "scriptedpage";
		} catch (Exception e) {
			log.error("error: ", e);
			log.error("................ ERROR FIN JSP INDEX ................");
			return "scriptedpage";
		}
	}
	

}