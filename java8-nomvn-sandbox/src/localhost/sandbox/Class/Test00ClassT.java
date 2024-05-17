package localhost.sandbox.Class;

import java.util.ArrayList;
import java.util.List;

public class Test00ClassT {

	static public void checkClass() {
		// here, it is not convenient to use List<?>, in order
		// to have method "add" available
		List<String> stringList = new ArrayList<>();
		stringList.add("hello world");

		// test with instanceof
		System.out.println("( stringList.get(0) instanceof String ) : " + ( stringList.get(0) instanceof String ) );

		// test with Class<?> -- here, it is convenient to use Class<?>
		Class<?> clazz = stringList.get(0).getClass();
		System.out.println("( ( clazz == String.class ) ) : " + ( clazz == String.class ) );
	}

	static public void printClassNameVariations() {
		String myStr = "Hello World"; // variable of any type/class here
		Object obj = myStr;
		System.out.println("obj.getName(): " + myStr.getClass().getName());
		System.out.println("obj.getCanonicalName(): " + myStr.getClass().getCanonicalName());
		System.out.println("obj.getSimpleName(): " + myStr.getClass().getSimpleName());
		System.out.println("obj.getTypeName(): " + myStr.getClass().getTypeName());
	}

}
