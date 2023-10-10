package localhost.sandbox.Annotation;

import java.lang.reflect.Field;
import java.util.Base64;

public class AnnotationMain {

	public static void main() {
		test01();
	}
	
	private static void test01() {
		
		Person p = new Person("Luis", 30, true);
		
		Class<?> clazz = p.getClass();
		
		Field[] fields = clazz.getDeclaredFields();
		
		boolean fAccessible = false;
		
		String fieldValue = null;
		
		for (Field field : fields) {
			
			fAccessible = field.isAccessible();
			
			field.setAccessible(true);
			
			if ( field.isAnnotationPresent(DbFieldEncrypt.class) ) {
				System.out.println("field: " + field.getName() + " ; annotation: " + DbFieldEncrypt.class.getName());
				
				try {
					fieldValue = (String) field.get(p);
				} catch (Exception e) {
					System.err.println("error -- e.getClass(): " + e.getClass() + " ; e.getMessage(): {}" + e.getMessage());
				}
				
				fieldValue = Base64.getEncoder().encodeToString(fieldValue.getBytes());
				
				try {
					field.set(p, fieldValue);
				} catch (Exception e) {
					System.err.println("error -- e.getClass(): " + e.getClass() + " ; e.getMessage(): {}" + e.getMessage());
				}
				
			}
			field.setAccessible(fAccessible);
		}
		
		System.out.println("person: " + p.toString());
		
	}

}
