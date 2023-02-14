package localhost.sandbox.history.off.InheritancePolymorphism.pojo;

public class PersonDetailThree extends PersonDetailTwo {
	
	public Double height;
	
	public PersonDetailThree() {
		
	}
	
	public String toString() {
		String string = "{Name:" + name +", Age:" + age +", height:" + height + ", didService: " + "}";

		return string ;
	}

}
