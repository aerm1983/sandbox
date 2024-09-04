package localhost.test.PmRaceCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import localhost.test.PmRaceCondition.mapper.PublishErrorMapper;
import localhost.test.PmRaceCondition.model.PublishErrorModel;

@Service
public class Test00_PmRaceCondition {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	PublishErrorMapper pem;

	private static Logger log = LoggerFactory.getLogger(Test00_PmRaceCondition.class);

	public void test00_PmRaceCondition() {
		log.info("Hello from test00_PmRaceCondition");

		String originUuid = "00000000-0000-0000-0000-000000000000";
		List<PublishErrorModel> input = new ArrayList<>();
		// block to emulate PublishError -- begin
		PublishErrorModel pe = new PublishErrorModel();
		// pe.setOriginUuid(((ArrayList<T>) payload).get(0).getFileUuid());
		pe.setOriginUuid(originUuid);
		pe.setProductSiteId(310044L);
		pe.setErrorCode("409");
		pe.setErrorMessage("CONFLICT");
		pe.setLineNumber(0);
		pe.setMarketplace("amazon");
		// pem.save(pe);
		// block to emulate PublishError -- end
		input.add(pe);

		// SynchroWaitNotifyAllHelper
		SynchroWaitNotifyAllHelper swnah = new SynchroWaitNotifyAllHelper();


		// thread publish error controller
		AtomicReference<List<PublishErrorModel>> pemOutListAtRef = new AtomicReference<>();
		Thread tpec = new Thread (
				() -> {
					log.info("controller wait");
					swnah.doWait();
					log.info("controller start");
					List<PublishErrorModel> pemOutList = pem.findByOriginUuid("00000000-0000-0000-0000-000000000000");
					log.info("pemOutList.size(): {}", pemOutList.size());
					pemOutListAtRef.set(pemOutList);
					log.info("controller finish");
				}, 
				"tpec" );


		// thread uuid
		Thread tuuid = new Thread ( 
				() -> {
					log.info("uuid start");
					input.stream().forEach(inp -> {
						int iSave = pem.save(inp);
						log.info("iSave: {}", iSave);
					});
					// doSleep(10L);
					// tpec.start();
					// log.info("tpec called ; now sleep 20 secs");
					// pem.sleep(20);
					swnah.doNotifyAll();
					log.info("doNotifyAll() executed, now querying just inserted publish_error");
					List<PublishErrorModel> peUuidList = pem.findByOriginUuid("00000000-0000-0000-0000-000000000000");
					log.info("uuid finish -- peUuidList.size(): {}", peUuidList.size());
				}, "tuuid" ) ;


		// thread bulkHandler
		Thread tbh = new Thread( 
				() -> {
					log.info("handler start");
					tuuid.start();
					try {
						tuuid.join();
					} catch (Exception e) {
						System.err.println("error -- " + e);
					}
					log.info("handler finish");
				}, 
				"tbh") ;

		log.info("calling tpec, tbh start");
		tpec.start();
		tbh.start();


		// wait for threads to finish
		try {
			tbh.join();
			tpec.join();
		} catch (Exception e) {
			log.error("error -- ", e);
		}


		// indicate result
		log.info("pmOutListAtRef.get(): {}", pemOutListAtRef.get());


		// delete register
		for (PublishErrorModel p : pemOutListAtRef.get()) {
			int iDel = pem.delete(p.getId());
			log.info("iDel: {}", iDel);
		}
	}



	/**
	 * Internal helper method.
	 */
	private static void doSleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			log.error("error -- " + e);
		}
	}


	/**
	 * Internal helper classs.
	 */
	private static class SynchroWaitNotifyAllHelper {
		// wait() wrapper
		public synchronized void doWait() {
			try {
				wait();
			} catch (Exception e) {
				log.error("error -- ", e);
			}
		}
		// notifyAll() wrapper
		public synchronized void doNotifyAll() {
			notifyAll();
		}
	}

}
