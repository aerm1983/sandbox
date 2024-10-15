package localhost.sandbox.Generics;

/**
 * See comment on interface regarding use of Generic Types. 
 */
public class Test03_InterfaceWithGeneric  {

	public static void test00_InterfaceWithGeneric () {

		MyInterfaceOne<String> miOne = new MyImplementationOne<>();
		miOne.doWithGeneric("hello-one!");


		MyInterfaceTwo miTwo = new MyImplementationTwo();
		miTwo.doWithGeneric("hello-two!");


		MyInterfaceThree miThree = new MyImplementationThree();
		// miThree.doWithGeneric("three!"); // does not work
		MyInterfaceThree.doWithGeneric("interface-three!");
		MyImplementationThree.doWithGeneric("implementation-three!");

	}


	/**
	 * Interfaces using Generic Types should apply such Generic to the 
	 * whole interface/class to avoid "name clash collision".
	 */
	public static interface MyInterfaceOne <T extends Object> {

		public void doWithGeneric(T input);

	}


	public static class MyImplementationOne<T> implements MyInterfaceOne<T> {

		public MyImplementationOne() {
			super();
		}


		public void doWithGeneric(T input) {

			// using instanceof
			if ( input instanceof String  ) {
				System.out.println("T is String! value: '" + input + "'");
			} else if ( input instanceof Integer ) {
				System.out.println("T is Integer! value: '" + input + "'");
			} else if ( input instanceof Double ) {
				System.out.println("T is Double! value: '" + input + "'");
			} else if ( input instanceof Boolean ) {
				System.out.println("T is Boolean! value: '" + input + "'");
			}


			// using Class<?>
			Class<?> clazz = input.getClass();
			if (clazz == String.class) {
				System.out.println("<T> input is String! value: '" + input + "'");
			} else if (clazz == Integer.class) {
				System.out.println("<T> input is Integer! value: '" + input + "'");
			} else if (clazz == Double.class) {
				System.out.println("<T> input is Double! value: '" + input + "'");
			} else if (clazz == Boolean.class) {
				System.out.println("<T> input is Boolean! value: '" + input + "'");
			}

		}
	}


	public static interface MyInterfaceTwo {

		public <T> void doWithGeneric(T input);

	}

	public static class MyImplementationTwo implements MyInterfaceTwo {

		public MyImplementationTwo() {
			super();
		}

		public <T> void doWithGeneric(T input) {
			MyInterfaceOne<T> miOne = new MyImplementationOne<>();
			miOne.doWithGeneric(input);
		}

	}



	public static interface MyInterfaceThree {

		public static <T> void doWithGeneric(T input) {
			System.out.println("MyInterfaceThree! input.getClass(): " + input.getClass());
		};

	}


	public static class MyImplementationThree implements MyInterfaceThree {

		public MyImplementationThree() {
			super();
		}

		public static <T> void doWithGeneric(T input) {
			System.out.println("MyImplementationThree! input.getClass(): " + input.getClass());
		}

	}


}