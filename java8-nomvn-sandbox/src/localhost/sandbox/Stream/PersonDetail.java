package localhost.sandbox.Stream;

public class PersonDetail {

	private String name;
	private Integer age;
	private Boolean isMale;

	public PersonDetail(String name, int age, boolean isMale) {
		this.name = name;
		this.age = age;
		this.isMale = isMale;
	}

	public String toString() {
		String out = "{ "
				+ "" + "name:" + name 
				+ ", " + "age:" + age 
				+ ", " + "isMale:" + isMale 
				+ " }";
		return out ;
	}

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}

	public Boolean getIsMale() {
		return isMale;
	}

}
