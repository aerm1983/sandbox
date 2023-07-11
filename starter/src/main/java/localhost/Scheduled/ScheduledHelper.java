package localhost.Scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledHelper {
	
	private static Logger log = LoggerFactory.getLogger(ScheduledHelper.class);
	
	
	@Scheduled(fixedDelayString = "${customScheduled.fixedDelayString01}" )
	public void scheduledFixedDelayString01 () {
        log.info("scheduledFixedDelayString01 begin -- ");
        
        try {
            Thread.sleep(2 * 1000);
        } catch (Exception e) {
            log.error("error on scheduledFixedDelayString01 -- e.getMessage(): {} -- e.getCause(): {}", e.getMessage(), e.getCause());
        }
        
        log.info("scheduledFixedDelayString01 end -- ");
	}
	
	
    @Scheduled(cron = "${customScheduled.cron01}" )
    public void scheduledCron01 () {
        log.info("scheduledCron01 begin -- ");
        
        try {
            Thread.sleep(2 * 1000);
        } catch (Exception e) {
            log.error("error on scheduledCron01 -- e.getMessage(): {} -- e.getCause(): {}", e.getMessage(), e.getCause());
        }
        
        log.info("scheduledCron01 end -- ");
    }

	
}
