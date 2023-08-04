package localhost.sandbox.Generics;

/**
 * As this class has a Generic Type attribute, such Generic Type 
 * should be applied to the whole class. 
 */
public class PersonDetailOne<T> {
	
	public T data ;
	
	public PersonDetailOne(T data) {
		this.data = data;
	}
	
	public String toString() {
		String string = "{Name:" + data.toString() + "}";
		return string ;
	}
	
	public void loadPDFour (PersonDetailFour pDFour) {
		
		if ( this.data instanceof String  ) {

			pDFour.name = (String) this.data;
		
		} else if ( this.data instanceof Integer ) {
		
			pDFour.age = (Integer) this.data;
		
		} else if ( this.data instanceof Double ) {
			
			pDFour.height = (Double) this.data;
		
		} else if ( this.data instanceof Boolean ) {
			
			pDFour.didService = (Boolean) this.data;
		
		}
		
	}
}
