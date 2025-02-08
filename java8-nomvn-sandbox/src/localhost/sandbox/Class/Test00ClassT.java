package localhost.sandbox.Class;

import java.util.ArrayList;
import java.util.List;

import localhost.helper.JvmMemoryHelper;
import localhost.helper.JvmMemoryHelper.JvmMemorySnapshotPojo;

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


	/**
	 * <p>Note on Canonical an Binary class names:
	 * <ul>
	 * <li>Canonical: A.B <i>(is A a package?  or is B an inner class?)</i>
	 * <li>Binary: A$B <i>(A is class and B is inner class)</i>
	 * <li>Binary: A.B <i>(A is package and B is class)</i>
	 * </ul>
	 * 
	 * @since 2025-02-08
	 */
	static public void printClassNameVariations() {
		// variables
		Object obj = null;
		Class<?> clazz = null;

		// with String
		System.out.println("");
		System.out.println("---> with String");
		String myStr = "Hello World"; // variable of any type/class here
		obj = myStr;
		clazz = myStr.getClass();
		System.out.println("obj.getClass(): " + myStr.getClass());
		System.out.println("clazz.getName(): " + clazz.getName());
		System.out.println("clazz.getCanonicalName(): " + clazz.getCanonicalName());
		System.out.println("clazz.getSimpleName(): " + clazz.getSimpleName());
		System.out.println("clazz.getTypeName(): " + clazz.getTypeName());
		System.out.println("clazz.toString(): " + clazz.toString());
		System.out.println("clazz: " + clazz);

		// with JvmMemorySnapshotPojo
		System.out.println("");
		System.out.println("---> with JvmMemorySnapshotPojo");
		JvmMemorySnapshotPojo jmsp = JvmMemoryHelper.getSnapshot();
		obj = jmsp; // variable of any type/class here
		clazz = jmsp.getClass();
		System.out.println("obj.getClass(): " + myStr.getClass());
		System.out.println("clazz.getName(): " + clazz.getName());
		System.out.println("clazz.getCanonicalName(): " + clazz.getCanonicalName());
		System.out.println("clazz.getSimpleName(): " + clazz.getSimpleName());
		System.out.println("clazz.getTypeName(): " + clazz.getTypeName());
		System.out.println("clazz.toString(): " + clazz.toString());
		System.out.println("clazz: " + clazz);

	}

}
