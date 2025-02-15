package localhost.sandbox.jse8.Object;

import java.util.HashSet;
import java.util.Set;

import localhost.sandbox.jse8.Object.Pojo.Person;

public class Test00ObjectHashCodeEquals {

	public static void test00PojoEquals () {
		System.out.println("Hello from test00PojoEquals!");

		Person pOne = new Person("Juan", 20, true);
		Person pTwo = new Person("Juan", 20, true);
		Person pThree = new Person("Luis", 20, true);
		Person pFour = new Person("Juan", 45, true);
		Person pFive = new Person("Juan", 20, false);

		System.out.println("pOne:   " + pOne);
		System.out.println("pTwo:   " + pTwo);
		System.out.println("pThree: " + pThree);
		System.out.println("pFour:  " + pFour);
		System.out.println("pFive:  " + pFive);

		System.out.println("pOne.equals(pTwo):   " + pOne.equals(pTwo));
		System.out.println("pOne.equals(pThree): " + pOne.equals(pThree));
		System.out.println("pOne.equals(pFour):  " + pOne.equals(pFour));
		System.out.println("pOne.equals(pFive):  " + pOne.equals(pFive));
	}


	public static void test01PojoHashCodeEqualsWithSet () {
		System.out.println("Hello from test01PojoEqualsWithSet!");

		Person pOne = new Person("Juan", 20, true);
		Person pTwo = new Person("Juan", 20, true);
		Person pThree = new Person("Luis", 20, true);
		Person pFour = new Person("Juan", 45, true);
		Person pFive = new Person("Juan", 20, false);

		System.out.println("pOne:   " + pOne);
		System.out.println("pTwo:   " + pTwo);
		System.out.println("pThree: " + pThree);
		System.out.println("pFour:  " + pFour);
		System.out.println("pFive:  " + pFive);

		Set<Person> pSet = new HashSet<>();
		pSet.add(pOne);
		pSet.add(pTwo);
		pSet.add(pThree);
		pSet.add(pFour);
		pSet.add(pFive);
		System.out.println("pSet:   " + pSet);
	}

}
