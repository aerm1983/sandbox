package localhost.sandbox.history.on.Generics;

public class PersonDetailFour {
	
	public String name;
	public Integer age;
	public Double height;
	public Boolean didService;
	
	public PersonDetailFour() {
		
	}
	
	public String toString() {
		String string = "{Name:" + name + ", Age:" + age + ", height:" + height + ", didService: " + didService + "}" ;
		return string ;
	}
	
	

	public <T> void loadFromPDOne(PersonDetailOne<T> pDOne) {
		
		if ( pDOne instanceof PersonDetailOne ) {
			
			if ( pDOne.data instanceof String ) {

				this.name = (String) pDOne.data;

			} else if ( pDOne.data instanceof Integer) {

				this.age = (Integer) pDOne.data;

			} else if ( pDOne.data instanceof Double) {

				this.height = (Double) pDOne.data;

			} else if ( pDOne.data instanceof Boolean) {

				this.didService = (Boolean) pDOne.data;
			}
		
		}
		
		
	}

	
	/*
	public void loadDetail(PersonDetailOne<String> pdOne) {
		this.name = pdOne.data;
	}
	*/
	

}
