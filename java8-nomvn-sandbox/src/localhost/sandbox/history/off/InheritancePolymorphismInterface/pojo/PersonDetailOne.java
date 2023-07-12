package localhost.sandbox.history.off.InheritancePolymorphismInterface.pojo;

import localhost.sandbox.history.off.InheritancePolymorphismInterface.PersonInterface;

public class PersonDetailOne implements PersonInterface {
	
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
