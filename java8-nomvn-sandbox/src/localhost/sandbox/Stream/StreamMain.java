package localhost.sandbox.Stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;

public class StreamMain {

	public static void main() {
		// test00StreamMapCollectToList();
		// test01StreamMaxLongUsingComparator();
		test02StreamMaxLongUsingMapToLong();
		
	}
	
	/**
	 * <p> "stream()" is used here sequentally, but parallel process could
	 * be implemented with a small substitution: "parallelStream()".
	 * 
	 * @author Alberto Romero
	 * 
	 */
	public static void test00StreamMapCollectToList() {

		PersonDetail pd00 = new PersonDetail("alberto", 40, true);
		PersonDetail pd01 = new PersonDetail("blanca", 50, false);
		PersonDetail pd02 = new PersonDetail("carlos", 25, true);
		PersonDetail pd03 = new PersonDetail("juana", 31, false);

		List<PersonDetail> pdList = new ArrayList<PersonDetail>();
		pdList.add(pd00);
		pdList.add(pd01);
		pdList.add(pd02);
		pdList.add(pd03);
		
		List<String> nameList = pdList
				.stream()
				.map( (e) -> {
					return e.getName();
				})
				.collect(Collectors.toList());
		
		System.out.println("nameList: " + nameList);
	}
	
	
	
	/**
	 * <p> Gets max long / int from List, using stream, comparator. 
	 * 
	 * @author Alberto Romero
	 * @since 2023-10-18
	 */
	public static void test01StreamMaxLongUsingComparator() {
		
		List<Long> idList = new ArrayList<Long>();
		idList.add(0L);
		idList.add(1L);
		idList.add(2L);
		idList.add(3L);
		idList.add(4L);
		
		Optional<Long> maxIdOpt = idList.stream().max(Comparator.naturalOrder());
		long maxId = 0L;
		if (maxIdOpt.isPresent()) {
			maxId = maxIdOpt.get();
		}
		
		System.out.println("maxId: " + maxId);

	}
	
	
	/**
	 * <p> Gets max long / int from List, using stream. 
	 * 
	 * @author Alberto Romero
	 * @since 2023-10-18
	 */
	public static void test02StreamMaxLongUsingMapToLong() {
		
		List<Long> idList = new ArrayList<Long>();
		idList.add(0L);
		idList.add(1L);
		idList.add(2L);
		idList.add(3L);
		idList.add(4L);
		
		OptionalLong maxIdOpt = idList.stream().mapToLong(v -> v).max();
		long maxId = 0L;
		if (maxIdOpt.isPresent()) {
			maxId = maxIdOpt.getAsLong();
		}
		
		System.out.println("maxId: " + maxId);

	}

	
}	
