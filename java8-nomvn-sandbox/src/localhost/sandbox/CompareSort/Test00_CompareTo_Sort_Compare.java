package localhost.sandbox.CompareSort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * <p>For method "x.compareTo(y)", see interface "Comparable".
 * <p>For method "x.compare(x,y)", see interface "Comparator".
 * <p>Method "Collections.sort(...)" can make use of several combinations
 * of those two.
 * 
 * @since 2024-04-11
 */
public class Test00_CompareTo_Sort_Compare {

	/**
	 * <p>Types String, Integer, Double, have built-in implementations
	 * of "x.compareTo(y)" method.
	 * 
	 * @since 2024-04-11
	 */
	public static void test00_CompareTo() {
		System.out.println("Hello from test00_CompareTo!");

		// String, compareTo
		System.out.println(""
				+ "String -- " 
				+ "\"B\".compareTo(\"A\"): " + "B".compareTo("A") + " ; " 
				+ "\"B\".compareTo(\"C\"): " + "B".compareTo("C") + " ; " 
				+ "\"B\".compareTo(\"B\"): " + "B".compareTo("B") + " ; "
				);

		// String, compareToIgnoreCase
		System.out.println(""
				+ "String -- " 
				+ "\"B\".compareToIgnoreCase(\"A\"): " + "B".compareToIgnoreCase("A") + " ; " 
				+ "\"B\".compareToIgnoreCase(\"C\"): " + "B".compareToIgnoreCase("C") + " ; " 
				+ "\"B\".compareToIgnoreCase(\"B\"): " + "B".compareToIgnoreCase("B") + " ; "
				);

		// Integer
		System.out.println(""
				+ "Integer -- " 
				+ "((Integer) 2).compareTo(1): " + (((Integer) 2).compareTo(1)) + " ; " 
				+ "((Integer) 2).compareTo(3): " + (((Integer) 2).compareTo(3)) + " ; " 
				+ "((Integer) 2).compareTo(2): " + (((Integer) 2).compareTo(2)) + " ; "
				);

		// Double
		System.out.println(""
				+ "Double -- " 
				+ "((Double) 2.2).compareTo(1.1): " + (((Double) 2.2).compareTo(1.1)) + " ; " 
				+ "((Double) 2.2).compareTo(3.3): " + (((Double) 2.2).compareTo(3.3)) + " ; " 
				+ "((Double) 2.2).compareTo(2.2): " + (((Double) 2.2).compareTo(2.2)) + " ; "
				);
	}



	/**
	 * <p>Built-in implementations of "x.compareTo(y)" are used
	 * internally by "Collections.sort(Collection<T>)".
	 * 
	 * @since 2024-04-11
	 */
	public static void test01_Sort() {
		System.out.println("Hello from test01_Sort!");

		// String
		ArrayList<String> als = new ArrayList<>();
		als.add("1B");
		als.add("1C");
		als.add("00");
		als.add("1A");
		als.add("000");
		als.add("0");
		System.out.println("String -- array, before sort: " + als);
		Collections.sort(als);
		System.out.println("String -- array, after sort: " + als);

		// Integer
		ArrayList<Integer> ali = new ArrayList<>();
		ali.add(6);
		ali.add(4);
		ali.add(1);
		ali.add(9);
		System.out.println("Integer -- array, before sort: " + ali);
		Collections.sort(ali);
		System.out.println("Integer -- array, after sort: " + ali);

		// Double
		ArrayList<Double> ald = new ArrayList<>();
		ald.add(7.19);
		ald.add(4.33);
		ald.add(2.45);
		ald.add(9.03);
		System.out.println("Double -- array, before sort: " + ald);
		Collections.sort(ald);
		System.out.println("Double -- array, after sort: " + ald);

		// Float
		ArrayList<Float> alf = new ArrayList<>();
		alf.add(7.19f);
		alf.add(4.33f);
		alf.add(2.45f);
		alf.add(9.03f);
		System.out.println("Float -- array, before sort: " + alf);
		Collections.sort(alf);
		System.out.println("Float -- array, after sort: " + alf);

	}



	/**
	 * <p>Explicit implementation of "compare(x,y)" method can be done
	 * using built-in implementations of "x.compareTo(y)".
	 * 
	 * <p>This test proposes a way to statically define several 
	 * "compare()" methods, then conveniently using anonymous class 
	 * of "Comparator" interface making use of such static definitions.
	 * 
	 * @since 2024-04-11
	 */
	public static void test02_Sort_Compare_CompareTo_OnPojo() {
		System.out.println("Hello from test02_Sort_Compare_CompareTo_OnPojo!");

		// pojos
		PersonPojo pJosefa = new PersonPojo("Josefa", 80, 1.50);
		PersonPojo pNena = new PersonPojo("Nena", 63, 1.61);
		PersonPojo pDavid = new PersonPojo("David", 29, 1.77);
		PersonPojo pDiego = new PersonPojo("Diego", 33, 1.73);

		// String, surname
		ArrayList<PersonPojo> as = new ArrayList<>();
		as.add(pJosefa);
		as.add(pNena);
		as.add(pDavid);
		as.add(pDiego);
		System.out.println("PersonPojo -- array, before sort by surname: " + as);
		Collections.sort(as, new Comparator<PersonPojo>() {
			@Override
			public int compare(PersonPojo o1, PersonPojo o2) {
				return PersonPojo.compareBySurname(o1, o2);
			}
		});
		System.out.println("PersonPojo -- array, after sort by surname: " + as);

		// Integer, age
		ArrayList<PersonPojo> aa = new ArrayList<>();
		aa.add(pJosefa);
		aa.add(pNena);
		aa.add(pDavid);
		aa.add(pDiego);
		System.out.println("PersonPojo -- array, before sort by age: " + aa);
		Collections.sort(aa, new Comparator<PersonPojo>() {
			@Override
			public int compare(PersonPojo o1, PersonPojo o2) {
				return PersonPojo.compareByAge(o1, o2);
			}
		});
		System.out.println("PersonPojo -- array, after sort by age: " + aa);

		// Double, height
		ArrayList<PersonPojo> ah = new ArrayList<>();
		ah.add(pJosefa);
		ah.add(pNena);
		ah.add(pDavid);
		ah.add(pDiego);
		System.out.println("PersonPojo -- array, before sort by height: " + ah);
		Collections.sort(aa, new Comparator<PersonPojo>() {
			@Override
			public int compare(PersonPojo o1, PersonPojo o2) {
				return PersonPojo.compareByHeight(o1, o2);
			}
		});
		System.out.println("PersonPojo -- array, after sort by height: " + ah);

	}

	/**
	 * POJO 
	 */
	private static class PersonPojo {

		private String surname;
		private Integer age;
		private Double height;

		public PersonPojo(String surname, Integer age, Double height) {
			super();
			this.surname = surname;
			this.age = age;
			this.height = height;
		}

		public String getSurname() {
			return surname;
		}
		public void setSurname(String surname) {
			this.surname = surname;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public Double getHeight() {
			return height;
		}
		public void setHeight(Double height) {
			this.height = height;
		}

		public String toString() {
			String out = ""
					+ "{ "
					+ "surname:" + this.surname + ", "
					+ "age:" + this.age + ", "
					+ "height:" + this.height + ""
					+ " }"
					;
			return out;
		}

		public static int compareBySurname(PersonPojo o1, PersonPojo o2) {
			// definitions for null values
			if ((o1 == null) && (o2 == null)) 
			{
				return 0;
			}
			else if ((o1 == null) && (o2 != null)) 
			{
				return -1;
			}
			else if ((o1 != null) && (o2 == null))
			{
				return +1;
			}
			// actual values definition
			else
			{
				return o1.getSurname().compareTo(o2.getSurname());
			}
		}

		public static int compareByAge(PersonPojo o1, PersonPojo o2) {
			// definitions for null values
			if ((o1 == null) && (o2 == null)) 
			{
				return 0;
			}
			else if ((o1 == null) && (o2 != null)) 
			{
				return -1;
			}
			else if ((o1 != null) && (o2 == null))
			{
				return +1;
			}
			// actual values definition
			else
			{
				// return Integer.valueOf(o1.getAge()).compareTo(Integer.valueOf(o2.getAge())); // if age is primitive 'int'
				return o1.getAge().compareTo(o2.getAge());
			}
		}

		public static int compareByHeight(PersonPojo o1, PersonPojo o2) {
			// definitions for null values
			if ((o1 == null) && (o2 == null)) 
			{
				return 0;
			}
			else if ((o1 == null) && (o2 != null)) 
			{
				return -1;
			}
			else if ((o1 != null) && (o2 == null))
			{
				return +1;
			}
			// actual values definition
			else
			{
				// return Double.valueOf(o1.getHeight()).compareTo(Double.valueOf(o2.getHeight())); // if height is primitive 'double'
				return o1.getHeight().compareTo(o2.getHeight());
			}
		}

	}

}
