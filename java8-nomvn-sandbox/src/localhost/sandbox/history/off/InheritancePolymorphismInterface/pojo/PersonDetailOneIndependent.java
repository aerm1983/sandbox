package localhost.sandbox.history.off.InheritancePolymorphismInterface.pojo;

import localhost.sandbox.history.off.InheritancePolymorphismInterface.InterfacePerson;

public class PersonDetailOneIndependent implements InterfacePerson {
	
	public String name ;
	
	public PersonDetailOneIndependent() {
		
	}
	
	public String toString() {
		String string = "{Name:" + name + "}";
		return string ;
	}
	
	public void printMyName() {
        System.out.println("PersonDetailOneIndependent -- interface -- {Name:" + name + "}");
        return;
	}
	
	public void iAmIndependent () {
	    System.out.println("PersonDetailOneIndependent -- I am independent!");
	}

}
