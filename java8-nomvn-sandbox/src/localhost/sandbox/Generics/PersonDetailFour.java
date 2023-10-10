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
	
	public <T extends Object> void loadFromPDOne(PersonDetailOne<T> pDOne) {
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
	
	/**
	 * <p> This is an example of a function accepting Generic.
	 * 
	 * <p> If input object is a primitive-type-wrapping-object, logic is forceful; in
	 * this case, this kind of function does not make much sense.
	 * 
	 * <p> But if input object is an object containing primitive-type-wrapping-objects,  
	 * and operations are performed on the later ones, this might make sense.
	 * 
	 * <p> Use of Reflection could also further soften logic within function.
	 * 
	 * <p> Examples:
	 * <ul>
	 * <li> For inputs like 1 or 1.6 or "hello", etc. this function's logic is forceful
	 * <li> For input like {name:"Juan",surname:"acosta"}, this makes better sense.
	 * </ul>
	 * 
	 * @param <T> type or class
	 * @param obj input object
	 * @return object of same type, modified
	 * @author Alberto Romero
	 * @since 2023-09-29
	 */
	@SuppressWarnings("unchecked")
	public <T extends Object> T doSomethingOnObject(T obj) {
		if (obj instanceof String) {
			obj = (T) ( (String) obj + " withSomethingAdded!" ) ;
		} else if (obj instanceof Integer) {
			obj = (T) Integer.valueOf( (Integer) obj + 1 ) ;
		} else if (obj instanceof Double) {
			obj = (T) Double.valueOf( (Double) obj + 3.1416 ) ;
		}
		return obj;
	}

}
