package localhost.sandbox.Stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;

public class Test00_StreamVarious {

	/**
	 * <p> "stream()" is used here sequentally, but parallel process could
	 * be implemented with a small substitution: "parallelStream()".
	 * 
	 * @author Alberto Romero
	 * 
	 */
	public static void test00_StreamMapCollectToList() {

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
	public static void test01_StreamMaxLongUsingComparator() {

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
	public static void test02_StreamMaxLongUsingMapToLong() {

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


	public static void test03_StreamGroupPojoByFieldIntoMap() {

		PersonDetail pd00 = new PersonDetail("alberto", 40, true);
		PersonDetail pd01 = new PersonDetail("blanca", 50, false);
		PersonDetail pd02 = new PersonDetail("carlos", 30, true);
		PersonDetail pd03 = new PersonDetail("juana", 50, false);
		PersonDetail pd04 = new PersonDetail("vilma", 30, false);
		PersonDetail pd05 = new PersonDetail("luis", 40, false);

		ArrayList<PersonDetail> pdList = new ArrayList<>();
		pdList.add(pd00);
		pdList.add(pd01);
		pdList.add(pd02);
		pdList.add(pd03);
		pdList.add(pd04);
		pdList.add(pd05);

		Map<Integer, List<PersonDetail>> pdMapByAge = pdList.stream()
				.collect(Collectors.groupingBy(pd -> pd.getAge()));

		System.out.println("pdMapByAge: " + pdMapByAge);

	}


}
