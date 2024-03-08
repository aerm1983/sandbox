package localhost.sandbox.Reflection;

import java.lang.reflect.Field;

import localhost.sandbox.Reflection.Pojo.Person;

public class Test00_FieldExistsFieldValue {

	public static void test00_FieldExistsAndFieldValue() {
		
		System.out.println("test00 begin");
		
		Person person = new Person("Juan", 21, true);
		
		System.out.println("person, before reflection intervention: " + person.toString());
		
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
		
		String targetFieldName = "name";
		String oldFieldValue = null;
		String newFieldValue = "Zorro";
		
		for (Field field : fields) {

			if (targetFieldName.equals(field.getName())) {

				System.out.println("field '" + targetFieldName +  "' does exist in pojo!");

				fAccessible = field.isAccessible();
				field.setAccessible(true);
				try {
					oldFieldValue = (String) field.get(person);
					System.out.println("field '" + targetFieldName + "' has originally this value: '" + oldFieldValue + "'... but it's going to change soon!");
					field.set(person, newFieldValue);
				} catch (Exception e) {
					System.err.println("error -- e.getClass(): " + e.getClass() + " ; e.getMessage(): {}" + e.getMessage());
				}
				
				field.setAccessible(fAccessible);
				
			}
		}

		System.out.println("person, after reflection intervention: " + person.toString());

		System.out.println("test00 end");
	}

}
