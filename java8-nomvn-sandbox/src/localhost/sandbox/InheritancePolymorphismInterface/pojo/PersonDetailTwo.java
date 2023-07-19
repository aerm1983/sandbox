package localhost.sandbox.InheritancePolymorphismInterface.pojo;

public class PersonDetailTwo extends PersonDetailOne {
	
	public Integer age ;
	
	public PersonDetailTwo() {
		
	}
	
	public String toString() {
		String string = "{Name:" + name +", Age:" + age + "}" ;
		return string ;
	}

    public void printMyName() {
        System.out.println("PersonDetailTwo -- interface -- {Name:" + name + "}");
        return;
    }

}
