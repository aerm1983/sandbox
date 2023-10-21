package localhost.helper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ThreadRegistryHelper {

	private static volatile Map<String,Thread> threadMap = Collections.synchronizedMap(new HashMap<String,Thread>());
	
	public static void add(Thread thread) {
		threadMap.put(thread.getName(), thread);
	}
	
	public static Thread get(String threadName) {
		return threadMap.get(threadName);
	}
	
	public static boolean containsKey(String threadName) {
		return threadMap.containsKey(threadName);
	}
	
	public static int size() {
		return threadMap.size();
	} 
	
	public static void remove(String threadName) {
		threadMap.remove(threadName);
	}
	
	public static Set<String> keySet () {
		return threadMap.keySet();
	}

}
