package localhost.sandbox.Reflection;

import localhost.sandbox.Reflection.Pojo.Person;

public class Test01_ClassName {

	public static void test00_ClassName() {

		System.out.println("test00_ClassName begin");

		Class<?> clazz = Person.class;

		// Class into variable can also be set this way:
		// Person person = new Person("Juan", 21, true);
		// Class<?> clazz2 = person.getClass();

		System.out.println("clazz.getCanonicalName(): " + clazz.getCanonicalName());

		System.out.println("clazz.getName(): " + clazz.getName());

		System.out.println("clazz.getPackageName(): " + clazz.getPackageName());

		System.out.println("clazz.getSimpleName(): " + clazz.getSimpleName());

		System.out.println("clazz.getTypeName(): " + clazz.getTypeName());

		System.out.println("clazz.getPackage(): " + clazz.getPackage());

		System.out.println("test00_ClassName end");
	}

}
