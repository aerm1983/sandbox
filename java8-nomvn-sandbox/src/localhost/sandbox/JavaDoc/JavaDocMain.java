package localhost.sandbox.JavaDoc;

/**
 * <h1>Class</h1>
 * 
 * <p>Reference to class, no label: 
 * {@link localhost.sandbox.JavaDoc.JavaDocMain.PersonDetail}. 
 *  
 * <p>Reference to class, labeled:  
 * {@link localhost.sandbox.JavaDoc.JavaDocMain.PersonDetail Person}.
 * 
 * 
 * <h1>Method</h1>
 * 
 * <p>Reference to method in same class, labeled: 
 * {@link #printSomething(String) print}.
 * 
 * <p>Reference to method in other class, labeled: 
 * {@link localhost.sandbox.JavaDoc.JavaDocMain.PersonDetail#toString() toString}.
 * 
 * 
 * <h1>Attribute</h1>
 * 
 * <p>Reference to attribute in same class, labeled: 
 * {@link #javaDocStr JavaDocAttribute}.
 * 
 * <p>Reference to attribute in other class, labeled: 
 * {@link localhost.sandbox.JavaDoc.JavaDocMain.PersonDetail#age Age}.
 * 
 * 
 * <h1>Special characters</h1>
 * 
 * <p>Look source comment: List<T> and List&lt;T&gt;.
 * 
 * 
 */
public class JavaDocMain {

	// This is a single line comment

	/*
	 * This is a regular multi-line comment
	 */


	/**
	 * <p>This is <i>javaDocStr</i> attribute.
	 */
	private static String javaDocStr;

	public static void main() {
		System.out.println("Hello from " + JavaDocMain.class + "!");
	}

	/**
	 * <p>This is <i>printSomething</i> method.
	 */
	public static void printSomething(String message) {
		System.out.println("javaDocStr: " + javaDocStr + " ; message: " + message);
	}



	/*
	 * PERSON_DETAIL CLASS
	 * 
	 */

	/**
	 * <p>This is PersonDetail class.
	 * 
	 */
	public class PersonDetail {

		private String name;
		/**
		 * <p>This is the <i>Age</i> attribute.
		 */
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

}	
