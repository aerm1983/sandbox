package localhost.sandbox.ThreadGroupThreadMXBean;

public class CustomUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
	
	@Override
	public void uncaughtException(Thread t,Throwable e) {
		System.err.println(String.format("uuid: % -- error: %", t.getName(), e));
	}
	
}
