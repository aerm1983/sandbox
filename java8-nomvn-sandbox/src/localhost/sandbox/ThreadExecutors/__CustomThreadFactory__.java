package localhost.sandbox.ThreadExecutors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class __CustomThreadFactory__ implements ThreadFactory {
	
	private AtomicInteger ai;
	private String threadPrefix;
	
	public __CustomThreadFactory__ (String inThreadPrefix) {
		super();
		this.threadPrefix = inThreadPrefix;
		ai = new AtomicInteger(1);
	}

    @Override
    public Thread newThread(Runnable runnable) {
    	int i = ai.getAndIncrement();
    	return new Thread(runnable, String.format("%s-%s", threadPrefix, i));
    }
    
}
