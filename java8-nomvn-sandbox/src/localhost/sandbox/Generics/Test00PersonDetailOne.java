package localhost.sandbox.Generics;

/**
 * As this class has a Generic Type attribute, such Generic Type 
 * should be applied to the whole class. 
 */
public class Test00PersonDetailOne<T> {
	
	public T data ;
	
	
	public static void main() {
		Test00PersonDetailOne<?> pdName = new Test00PersonDetailOne<String>("Alberto"); 
		Test00PersonDetailOne<?> pdAge = new Test00PersonDetailOne<Integer>(40);
		Test00PersonDetailOne<?> pdHeight = new Test00PersonDetailOne<Double>(1.73);
		Test00PersonDetailOne<?> pdDidService = new Test00PersonDetailOne<Boolean>(false);
	}
	
	
	public Test00PersonDetailOne(T data) {
		this.data = data;
	}
	
	public String toString() {
		String string = "{Name:" + data.toString() + "}";
		return string ;
	}
	
	public void loadPDFour (Test01PersonDetailFour pDFour) {
		
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
