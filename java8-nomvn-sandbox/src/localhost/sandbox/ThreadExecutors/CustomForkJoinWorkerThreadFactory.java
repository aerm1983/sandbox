package localhost.sandbox.ThreadExecutors;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

public class CustomForkJoinWorkerThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {

	private String threadName;

	public CustomForkJoinWorkerThreadFactory(String threadName) {
		super();
		this.threadName = threadName;
	}

	@Override
	public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
		final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
		worker.setName(this.threadName + "-" + worker.getPoolIndex());
		return worker;
	}

}
