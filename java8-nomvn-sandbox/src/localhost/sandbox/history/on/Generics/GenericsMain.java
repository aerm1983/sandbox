package localhost.sandbox.history.on.Generics;

public class GenericsMain {
	
	public static void main() {
		
		System.out.println("Hello from GenericsMain!");

		PersonDetailOne<?> pdName = new PersonDetailOne<String>("Alberto"); 
		
		PersonDetailOne<?> pdAge = new PersonDetailOne<Integer>(40);
		
		PersonDetailOne<?> pdHeight = new PersonDetailOne<Double>(1.73);
		
		PersonDetailOne<?> pdDidService = new PersonDetailOne<Boolean>(false);

	}
	
}
