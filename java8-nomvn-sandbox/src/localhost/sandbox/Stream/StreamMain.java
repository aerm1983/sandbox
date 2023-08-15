package localhost.sandbox.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMain {

	public static void main() {
		streamMapCollectToList();
	}
	
	/**
	 * 
	 * "stream()" is used here sequentally, but parallel process could
	 * be implemented with a small substitution: "parallelStream()".
	 * 
	 */
	public static void streamMapCollectToList() {

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
	
}	
