package localhost.sandbox.jse8.JavaLang.Miscelaneous;

import java.util.ArrayList;

public class Test01InnerClassesStaticNonStaticPojo {

	
	public static class SimplePojoOne {
		
		private String department;
		
		private ArrayList<String> otherDepartment;
		
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
	
	public static class SimplePojoTwo extends SimplePojoOne {
		
		@Override
		public String getDepartment() {
			return super.getDepartment();
		}
		
		@Override
		public ArrayList<String> getOtherDepartment() {
			return super.getOtherDepartment();
		}
		
	}
	
	
	public class SimplePojoThree {
		
		private String threeString;

		public String getThreeString() {
			return threeString;
		}

		public void setThreeString(String threeString) {
			this.threeString = threeString;
		}
	}
}
