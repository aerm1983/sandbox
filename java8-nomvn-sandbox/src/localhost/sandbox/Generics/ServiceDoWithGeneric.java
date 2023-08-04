package localhost.sandbox.Generics;

/**
 * See comment on interface regarding use of Generic Types. 
 */
public class ServiceDoWithGeneric<T> implements DoWithGeneric<T> {
	
	public void doWithGeneric(T input) {
		if ( input instanceof String  ) {
			System.out.println("T2 is String! value: '" + input + "'");
		} else if ( input instanceof Integer ) {
			System.out.println("T2 is Integer! value: '" + input + "'");
		} else if ( input instanceof Double ) {
			System.out.println("T2 is Double! value: '" + input + "'");
		} else if ( input instanceof Boolean ) {
			System.out.println("T2 is Boolean! value: '" + input + "'");
		}
	}
}
