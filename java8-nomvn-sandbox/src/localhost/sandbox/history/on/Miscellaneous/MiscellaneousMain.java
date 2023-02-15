package localhost.sandbox.history.on.Miscellaneous;

public class MiscellaneousMain {

	public static void main() {
		
		System.out.println("Hello from MiscellaneousMain!");
		
		String s = "Alberto";
		
		System.out.println("s.getClass(): " + s.getClass() );
		
 
		PersonDetail personDetail = new PersonDetail();
		
		personDetail.name = "Alberto";
		
		if (personDetail == null || personDetail.name == null) {
			System.out.println("personDetail or personDetail.name is null");
		}
		
		if (personDetail != null && personDetail.name != null) {
			System.out.println("personDetail and personDetail.name are different to null");
		}
		
	}
	
}	
