package localhost.sandbox.ThreadExecutors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class __CustomThreadFactory__ implements ThreadFactory {
	
	static private AtomicInteger ai = new AtomicInteger();
	
	private String threadPrefix;
	
	public __CustomThreadFactory__ (String inThreadPrefix) {
		super();
		this.threadPrefix = inThreadPrefix;
	}

    @Override
    public Thread newThread(Runnable runnable) {
    	int i = ai.incrementAndGet();
    	return new Thread(runnable, String.format("%s-%s", threadPrefix, i));
    }
    
}
