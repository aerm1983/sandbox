package localhost.sandbox.history.on.Miscellaneous;

public class MiscellaneousMain {

	public static void main() {
		
		System.out.println("Hello from MiscellaneousMain!");
		
		/* */
		String s = "hello";
		String s2 = "hello";
		
		if (s!=null && s.equalsIgnoreCase(s2)) {
		    System.out.println("one!");
		} else if (s!=null && s.equalsIgnoreCase(s2)) {
		    System.out.println("two!");
		}
		
		
		
		/*
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
		*/
		
	}
	
}	
