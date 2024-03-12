package localhost.sandbox.Object;

import localhost.sandbox.Object.Pojo.Person;

public class Test00ObjectEquals {

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
}
