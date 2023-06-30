package localhost.sandbox.history.off.InheritancePolymorphismInterface.pojo;

import localhost.sandbox.history.off.InheritancePolymorphismInterface.InterfacePerson;

public class PersonDetailOne implements InterfacePerson {
	
	public String name ;
	
	public PersonDetailOne() {
		
	}
	
	public String toString() {
		String string = "{Name:" + name + "}";
		return string ;
	}
	
	public void printMyName() {
        System.out.println("PersonDetailOne -- interface -- {Name:" + name + "}");
        return;
	}

}
