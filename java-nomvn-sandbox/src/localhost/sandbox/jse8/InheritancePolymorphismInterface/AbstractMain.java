package localhost.sandbox.jse8.InheritancePolymorphismInterface;

import java.util.Date;

import localhost.sandbox.jse8.InheritancePolymorphismInterface.pojo.PersonDetailOneIndependent;

public class AbstractMain {

	public static void main() {
		
		System.out.println("Hello from AbstractMain! " + new Date());
		
		/**
		 * Abstract classes cannot be instantiated, compile error
		 */
		try {
		    // PersonAbstract personAbstract = new PersonAbstract();
		} catch (Exception e) {
		    System.err.println("error, instantiate abstract class -- e.getMessage(): " + e.getMessage() + " -- e.getCause(): " + e.getCause());
		}
		
		
		/**
		 * Public static method can be called from abstract class
		 */
		PersonAbstract.printSomething();
		
		
		/**
		 * Public method, static or not, can be called from subclass
		 */
		PersonDetailOneIndependent pdoi = new PersonDetailOneIndependent();
		pdoi.printMyName();
		PersonDetailOneIndependent.printSomething();
		
		/**		
		 * This method is not implemented in superclass. 
		 * An exception is thrown due to "@Override" annotation.
		 * Execution stops at this point; try/catch block does not handle exception.
		 */
		try {
		    pdoi.iAmIndependent();
		} catch (Exception e) {
		    System.err.println("error, annotation @Override -- e.getMessage(): " + e.getMessage() + " -- e.getCause(): " + e.getCause());
		}
		
		System.out.println("done!");
		
        return;
		
	}

}
