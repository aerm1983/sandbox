package localhost.test.PmRaceCondition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PmRaceConditionMain {

	private static Logger log = LoggerFactory.getLogger(PmRaceConditionMain.class);

	@Autowired
	Test00_PmRaceCondition test00_PmRaceCondition;

	public void main() {
		log.info("Hello from PmRaceConditionMain");
		test00_PmRaceCondition.test00_PmRaceCondition();
	}

}
