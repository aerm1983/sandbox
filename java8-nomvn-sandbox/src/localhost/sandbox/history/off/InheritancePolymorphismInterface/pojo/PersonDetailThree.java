package localhost.sandbox.history.off.InheritancePolymorphismInterface.pojo;

public class PersonDetailThree extends PersonDetailTwo {
	
	public Double height;
	
	public PersonDetailThree() {
		
	}
	
	public String toString() {
		String string = "{Name:" + name +", Age:" + age +", height:" + height + ", didService: " + "}";

		return string ;
	}

    public void printMyName() {
        System.out.println("PersonDetailThree -- interface -- {Name:" + name + "}");
        return;
    }

}
