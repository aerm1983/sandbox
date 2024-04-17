package localhost.CommandLineRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import localhost.test.ConfigBasicOps.ConfigBasicOpsMain;
import localhost.test.PmRaceCondition.PmRaceConditionMain;


@Component
public class HCPCommandLineRunner implements CommandLineRunner{

	@Autowired
	ConfigBasicOpsMain configBasicOpsMain;

	@Autowired
	PmRaceConditionMain pmRaceConditionMain;

	private static Logger log = LoggerFactory.getLogger(HCPCommandLineRunner.class);

	@Override
	public void run(String... args) throws Exception {
		log.info("Hello from HCPCommandLineRunner");
		//		configBasicOpsMain.main();
		pmRaceConditionMain.main();
	}

}
