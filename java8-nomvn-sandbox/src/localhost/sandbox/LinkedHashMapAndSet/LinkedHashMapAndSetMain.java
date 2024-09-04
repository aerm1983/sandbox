package localhost.sandbox.LinkedHashMapAndSet;

import java.util.LinkedHashSet;
import java.util.Set;

public class LinkedHashMapAndSetMain {

	public static void main() {
		// TODO Auto-generated method stub
		test01LinkedHashSet();

	}

	public static void pending() {
		/*

        InputStream inputStream = BulkConfigHandleThreadPool.class.getClassLoader().getResourceAsStream("application-alberto.yml");
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        LinkedHashMap<String,?> lhm0 = null;
        LinkedHashMap<String,?> lhm1 = null;
        LinkedHashMap<String,?> lhm2 = null;

        try {
            lhm0 = objectMapper.readValue(inputStream, LinkedHashMap.class);
            lhm1 = (LinkedHashMap<String,?>) lhm0.get("rabbitmq-integration");
            lhm2 = (LinkedHashMap<String,?>) lhm1.get("aggregating-message-handler");

            rabbitAggregatingHandlerPoolSize = (String) lhm2.get("pool-size");
            rabbitAggregatingHandlerDaemon = (String) lhm2.get("daemon");
            rabbitAggregatingHandlerThreadNamePrefix = (String) lhm2.get("thread-name-prefix");
            rabbitAggregatingHandlerThreadGroupName = (String) lhm2.get("thread-group-name");
        } catch (Exception e) {
            log.error("error: ", e);
        }

		 */

	}

	public static void test01LinkedHashSet() {

		Set<Integer> setNumbers = new LinkedHashSet<Integer>(); 

		setNumbers.add(1); 
		setNumbers.add(13); 
		setNumbers.add(2); 
		setNumbers.add(4); 

		for (Integer number : setNumbers) { 
			System.out.println(number); 
		} 

		System.out.println("done!");
	}
}