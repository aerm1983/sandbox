package localhost.sandbox.Annotation;

import java.lang.reflect.Field;
import java.util.Base64;

import localhost.sandbox.Annotation.Annotation.DbFieldEncryptTest;
import localhost.sandbox.Annotation.Annotation.SizeTest;
import localhost.sandbox.Annotation.Pojo.Person;

public class Test00_AnnotationEncryption {
	
	/**
	 * A reduced example of encryption implementation using
	 * annotations (and reflection).
	 * 
	 * Prior to this example, it is recommended to see simpler
	 * examples in "Reflection" package.
	 * 
	 * @author Alberto Romero
	 * @since 2024-03-08
	 * 
	 */
	public static void test00_AnnotationEncryption() {
		
		System.out.println("test00 begin");
		
		Person person = new Person("Luis", 30, true);
		
		System.out.println("person, before encryption: " + person.toString());
		
		Class<?> clazz = person.getClass();
		
		/**
		 * By using `clazz.getDeclaredFields()`, all attributes (public,
		 * private, protected) are returned.
		 * 
		 * If `clazz.getField(String Name)` was used, attribute would be 
		 * returned only if it is public.
		 */
		Field[] fields = clazz.getDeclaredFields();
		
		boolean fAccessible = false;
		
		String fieldValue = null;
		
		SizeTest faSize = null;
		int ifaSize = 0;
		
		for (Field field : fields) {
			
			fAccessible = field.isAccessible();
			
			field.setAccessible(true);
			if ( field.isAnnotationPresent(SizeTest.class) ) {
				faSize = field.getDeclaredAnnotation(SizeTest.class);
				ifaSize = faSize.max();
				System.out.println("ifaSize: " + ifaSize);
 			}
			
			
			if ( field.isAnnotationPresent(DbFieldEncryptTest.class) ) {
				System.out.println("field: " + field.getName() + " ; annotation: " + DbFieldEncryptTest.class.getName());
				
				try {
					fieldValue = (String) field.get(person);
				} catch (Exception e) {
					System.err.println("error -- e.getClass(): " + e.getClass() + " ; e.getMessage(): {}" + e.getMessage());
				}
				
				fieldValue = Base64.getEncoder().encodeToString(fieldValue.getBytes());
				
				try {
					field.set(person, fieldValue);
				} catch (Exception e) {
					System.err.println("error -- e.getClass(): " + e.getClass() + " ; e.getMessage(): {}" + e.getMessage());
				}
				
			}
			field.setAccessible(fAccessible);
		}

		System.out.println("person, after encryption: " + person.toString());

		System.out.println("test00 end");

	}

}
