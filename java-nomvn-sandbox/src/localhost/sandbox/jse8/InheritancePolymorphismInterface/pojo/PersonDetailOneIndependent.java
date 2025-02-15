package localhost.sandbox.jse8.InheritancePolymorphismInterface.pojo;

import localhost.sandbox.jse8.InheritancePolymorphismInterface.PersonAbstract;

public class PersonDetailOneIndependent extends PersonAbstract {
	
	
	public PersonDetailOneIndependent() {
		
	}
	
	@Override
	public void printMyName() {
        System.out.println("PersonDetailOneIndependent -- extends abstract -- {Name:" + name + "}");
        return;
	}
	
	/**
	 * "@Override" annotation throws error on execution as this method is
	 * not implemented in superclass.
	 */
	// @Override
	public void iAmIndependent () {
	    System.out.println("PersonDetailOneIndependent -- I am independent!");
	}

}
