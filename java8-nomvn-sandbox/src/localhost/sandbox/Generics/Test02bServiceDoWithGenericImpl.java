package localhost.sandbox.Generics;

/**
 * See comment on interface regarding use of Generic Types. 
 */
public class Test02bServiceDoWithGenericImpl<T> implements Test02aDoWithGenericInterface<T> {
	
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
