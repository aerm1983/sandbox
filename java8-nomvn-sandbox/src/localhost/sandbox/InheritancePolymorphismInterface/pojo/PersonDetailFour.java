package localhost.sandbox.InheritancePolymorphismInterface.pojo;

public class PersonDetailFour extends PersonDetailThree {
	
	public Boolean didService;
	
	public PersonDetailFour() {
		
	}
	
	public String toString() {
		String string = "{Name:" + name + ", Age:" + age + ", height:" + height + ", didService: " + didService + "}" ;
		return string ;
	}

    public void printMyName() {
        System.out.println("PersonDetailFour -- interface -- {Name:" + name + "}");
        return;
    }

}
