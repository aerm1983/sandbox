package localhost.sandbox.JavaLang.Miscelaneous;

import java.util.ArrayList;

public class Test00OverrideReplacePojoAttribute {

	/**
	 * <h1> Test regarding protected, private, change extend source clase
	 * 
	 * <p> Aspects to observe here:
	 * 
	 * <ul>
	 * 
	 * <li> SimplePojoOne attributes are protected, so SimplePojoTwo can 
	 * access them directly, without getters and setters.
	 * 
	 * <li> If SimplePojoOne attributes were private, SimplePojoTwo could 
	 * not directly access them directly; it would need getters and setters.
	 * 
	 * <li> The desired outcome of SimplePojoOne.department is String; SimplePojoTwo 
	 * acts as a "dumb class", which does nothing, only reminding the need to 
	 * change SimplePojoOne.
	 * 
	 */
	public static void test() {
		SimplePojoOne sp1 = new SimplePojoOne();
		SimplePojoTwo sp2 = new SimplePojoTwo();
	}
	
	private static class SimplePojoOne {
		
		/**
		 * Protected (not private) attributes, so 
		 * SimplePojoTwo can access them without 
		 * getters and setters.
		 * 
		 * Attribute department was originally 
		 * ArrayList<String>.
		 * 
		 */
		// protected ArrayList<String> department;
		protected String department;
		
		protected ArrayList<String> otherDepartment;

		
		/**
		 * Method "getDepartment" changed due to attribute
		 * "department" type change.
		 * 
		 */
//		public ArrayList<String> getDepartment() {
//			if (department == null) {
//				department = new ArrayList<String>();
//			}
//			return department;
//		}
		public String getDepartment() {
			if (department == null) {
				department = "";
			}
			return department;
		}

		
		public ArrayList<String> getOtherDepartment() {
			if (otherDepartment == null) {
				otherDepartment = new ArrayList<String>();
			}
			return otherDepartment;
		}
	}
	
	private static class SimplePojoTwo extends SimplePojoOne {
		
		@Override
		public String getDepartment() {
			return department;
		}
		
		@Override
		public ArrayList<String> getOtherDepartment() {
			if (otherDepartment == null) {
				otherDepartment = new ArrayList<String>();
			}
			return otherDepartment;
		}
		
	}
}
