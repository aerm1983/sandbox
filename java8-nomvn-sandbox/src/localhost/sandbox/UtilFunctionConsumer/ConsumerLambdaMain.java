package localhost.sandbox.UtilFunctionConsumer;

import java.util.function.Consumer;

public class ConsumerLambdaMain {

	public static void main() {
		
		String myS = "initial string";
	    
	    Consumer<String> c = (s) -> {
		    System.out.println("one, from consumer lambda, s: " + s);
		    s = "value has changed";
		    System.out.println("two, from consumer lambda, s: " + s);
            String oS = "just another local string";
            System.out.println("three, from consumer lambda, oS: " + oS);
		};
		
		System.out.println("one, from main function, myS: " + myS);
		c.accept(myS);
		System.out.println("two, from main function, myS: " + myS);

	}

}
