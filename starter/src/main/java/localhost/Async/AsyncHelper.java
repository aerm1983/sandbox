package localhost.Async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class AsyncHelper {
	
	private static Logger log = LoggerFactory.getLogger(AsyncHelper.class);
	
	
	@Async
	public void asyncMain01 () {
        log.info("asyncMain01 begin -- ");
        
        try {
            Thread.sleep(2 * 1000);
        } catch (Exception e) {
            log.error("error on asyncMain01 -- e.getMessage(): {} -- e.getCause(): {}", e.getMessage(), e.getCause());
        }
        
        log.info("asyncMain01 end -- ");
	}

    @Async
    public void asyncMain02 () {
        log.info("asyncMain02 begin -- ");
        
        try {
            Thread.sleep(2 * 1000);
        } catch (Exception e) {
            log.error("error on asyncMain01 -- e.getMessage(): {} -- e.getCause(): {}", e.getMessage(), e.getCause());
        }
        
        log.info("asyncMain02 end -- ");
    }

	
}
