package localhost.test.ConfigBasicOps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigBasicOpsMain {

	private static Logger log = LoggerFactory.getLogger(ConfigBasicOpsMain.class);

	@Autowired
	Test00_ConfigBasicOps test00_ConfigBasicOps;

	public void main() {
		log.info("Hello from ConfigBasicOps");
		test00_ConfigBasicOps.test00_ConfigBasicOps();
	}

}
