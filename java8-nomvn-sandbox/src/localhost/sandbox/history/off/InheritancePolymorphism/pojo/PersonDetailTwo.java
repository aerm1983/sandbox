package localhost.sandbox.history.off.InheritancePolymorphism.pojo;

public class PersonDetailTwo extends PersonDetailOne {
	
	public Integer age ;
	
	public PersonDetailTwo() {
		
	}
	
	public String toString() {
		String string = "{Name:" + name +", Age:" + age + "}" ;
		return string ;
	}

}
