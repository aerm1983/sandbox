package localhost.sandbox.Generics;

/**
 * As this class is not extended by another, and method with Generic Type
 * is not overridden, such Generic Type can be applied only to the method, 
 * not the whole class.
 */
public class PersonDetailFour {
	
	public String name;
	public Integer age;
	public Double height;
	public Boolean didService;
	
	public PersonDetailFour() {
		super();
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

}
