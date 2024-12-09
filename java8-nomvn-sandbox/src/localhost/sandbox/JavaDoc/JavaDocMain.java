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
 * <p>Reference to class using generic, label mentions generic:  
 * {@link localhost.sandbox.JavaDoc.JavaDocMain.PersonDetail Person&lt;T&gt;}.
 * 
 * 
 * 
 * <h1>Method</h1>
 * 
 * <p>Reference to method in same class, no label: 
 * {@link #printSomething(String)}.
 * 
 * <p>Reference to method in other class, labeled: 
 * {@link localhost.sandbox.JavaDoc.JavaDocMain.PersonDetail#toString() toStr}.
 * 
 * <p>Reference to method in other class, with generic type parameter, no label:
 * {@link localhost.sandbox.JavaDoc.JavaDocMain.PersonDetail#PersonDetail(String, Integer, Boolean, Object)}.
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
	public class PersonDetail<T> {

		private String name;
		/**
		 * <p>This is the <i>Age</i> attribute.
		 */
		private Integer age;
		private Boolean isMale;
		private T variant;

		/**
		 * <p> This is <i>PersonDetail</i> constructor with arguments. 
		 */
		public PersonDetail(String name, int age, boolean isMale, T variant) {
			this.name = name;
			this.age = age;
			this.isMale = isMale;
			this.variant = variant;
		}

		public String toString() {
			String out = "{ "
					+ "" + "name:" + name 
					+ ", " + "age:" + age 
					+ ", " + "isMale:" + isMale 
					+ ", " + "variant:" + variant
					+ " }";
			return out ;
		}

		public String getName() {
			return this.name;
		}

		public Integer getAge() {
			return this.age;
		}

		public Boolean getIsMale() {
			return this.isMale;
		}

		public T getVariant() {
			return this.variant;
		}

	}

}	
