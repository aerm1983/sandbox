package localhost.helper;

import java.lang.reflect.Field;

/**
 * <h1>Reflection Helper.
 *  
 * @author Alberto Romero
 * @since 2024-03-16
 *
 */
public class ReflectionHelper {


	/**
	 * <p>Tests.
	 * 
	 * @since 2024-03-16
	 */
	public static void main() {
		System.out.println("Hello from ReflectionHelper.main()!");

		TestFatherPojo testFatherPojo = new TestFatherPojo("Charles", "Carpenter", 32);
		TestSonPojo testSonPojo = new TestSonPojo(testFatherPojo);
		String fieldName = null;
		String fieldValue = null;
		ResultPojo result = null;


		// test 00, private field declared in super class
		fieldName = "fatherName";
		try {
			result = ReflectionHelper.getFieldValue(testSonPojo, fieldName);
		} catch (Throwable tw) {
			System.err.println("error: " + tw);
		}
		fieldValue = (String) result.getFieldValue();
		System.out.println("test 00, private field declared in super-class -- result: " + result + " ; fieldValue: " + fieldValue);


		// test 01, protected field declared  in super class
		fieldName = "fatherOccupation";
		try {
			result = ReflectionHelper.getFieldValue(testSonPojo, fieldName);
		} catch (Throwable tw) {
			System.err.println("error: " + tw);
		}
		fieldValue = (String) result.getFieldValue();
		System.out.println("test 01, protected field declared  in super-class -- result: " + result + " ; fieldValue: " + fieldValue);


		// test 02, non declared / non existing field (nor in class, nor in super-class(es))
		fieldName = "nonExistingField";
		try {
			result = ReflectionHelper.getFieldValue(testSonPojo, fieldName);
		} catch (Throwable tw) {
			System.err.println("error: " + tw);
		}
		fieldValue = (String) result.getFieldValue();
		System.out.println("test 02, non declared / non existing field (nor in class, nor in super-class(es)) -- result: " + result + " ; fieldValue: " + fieldValue);

	}



	/**
	 * <p>Method "(class).getDeclaredFields()" returns only fields 
	 * declared in that specific class.
	 * 
	 * <p>See corresponding super-class-recursing method in this class.
	 * 
	 * <p>If search field is found, return it; else return null.
	 * 
	 * @author Alberto Romero
	 * @since 2024-03-16
	 * 
	 */
	public static Field getFieldInClass(Class<?> clazz, String fieldName) {
		if (fieldName == null || clazz == null) {
			return null;
		}
		Field[] fields = clazz.getDeclaredFields();
		Field foundField = null;
		for (Field field : fields) {
			if (fieldName.equalsIgnoreCase(field.getName())) {
				foundField = field;
				break;
			}
		}
		return foundField;
	}



	/**
	 * <p>Eclipse IDE debugger is very kind: it shows not only fields
	 * declared in current class, but also fields declared in super 
	 * classes, even private ones.
	 * 
	 * <p>Method "(class).getDeclaredFields()" is not so kind: it return
	 * only fields declared in that specific class.
	 * 
	 * <p>Hence, the necessity of evaluating class and super classes recursively 
	 * until field of interest is found, or super class is null. 
	 * 
	 * <p>If searched field is found, return it; else return null.
	 *  
	 * @author Alberto Romero
	 * @since 2024-03-16
	 */
	public static FieldClazzPojo searchFieldInClassAndSuperClasses(Class<?> clazz, String fieldName) {
		Class<?> clazzIteration = clazz;
		Field field = null; 
		FieldClazzPojo fieldClazzPojo = new FieldClazzPojo();
		do {
			field = getFieldInClass(clazzIteration, fieldName);
			if (field == null) {
				clazzIteration = clazzIteration.getSuperclass();
			}
		} while (field == null && clazzIteration != null);
		if (field == null) {
			return fieldClazzPojo;
		}
		fieldClazzPojo.setField(field);
		fieldClazzPojo.setDeclaringClazz(clazzIteration);
		return fieldClazzPojo;
	}



	/**
	 * <p>Search field in class and super-classes.
	 * 
	 * <p>If such field is found, and its value can be recovered, return the latter, as Object. 
	 * 
	 * <p>Else, throw Throwable / Exception. 
	 *  
	 * @author Alberto Romero
	 * @since 2024-03-16
	 */
	public static ResultPojo getFieldValue(Object object, String fieldName) {
		Class<?> clazz = object.getClass();
		ResultPojo result = new ResultPojo();
		result.setFieldName(fieldName);
		FieldClazzPojo fieldClazzPojo = searchFieldInClassAndSuperClasses(clazz, fieldName);
		if (fieldClazzPojo.getField() == null) {
			result.setDeclaringClazz(fieldClazzPojo.getDeclaringClazz());
			result.setErrorMessage("getFieldValue() -- error -- field named '" + fieldName + "' not found in class '" + clazz.getName() + "', nor in its super-class(es)");
			return result;
		}
		result.setFieldFoundOk(true);
		result.setDeclaringClazz(fieldClazzPojo.getDeclaringClazz());
		result.setField(fieldClazzPojo.getField());
		Field field = fieldClazzPojo.getField();
		Object fieldValue = null;
		Boolean originalAccessible = false;
		originalAccessible = field.isAccessible();
		field.setAccessible(true);
		try {
			fieldValue = field.get(object);
		} catch (Throwable tw) {
			result.setErrorMessage("getFieldValue() -- error -- field was found, but error occured when trying to get field value -- field name '" + fieldName + "' ; clas '" + clazz.getName() + "' -- error: " + tw);
			return result;
		}
		field.setAccessible(originalAccessible);
		result.setValueRetrievedOk(true);
		result.setFieldValue(fieldValue);
		return result;
	}



	/**
	 * <p>Result Pojo.
	 * 
	 * @author Alberto Romero
	 * @since 2024-03-16
	 */
	public static class ResultPojo {

		private String fieldName = null;
		private boolean fieldFoundOk = false;
		private boolean valueRetrievedOk = false;
		private Class<?> declaringClazz = null;
		private Field field = null;
		private Object fieldValue = null;
		private String errorMessage = null;

		public ResultPojo () {
			super();
		}

		public boolean isFieldFoundOk() {
			return fieldFoundOk;
		}

		public void setFieldFoundOk(boolean fieldFoundOk) {
			this.fieldFoundOk = fieldFoundOk;
		}

		public boolean isValueRetrievedOk() {
			return valueRetrievedOk;
		}

		public void setValueRetrievedOk(boolean valueRetrievedOk) {
			this.valueRetrievedOk = valueRetrievedOk;
		}

		public Object getFieldValue() {
			return fieldValue;
		}

		public void setFieldValue(Object fieldValue) {
			this.fieldValue = fieldValue;
		}

		public Class<?> getDeclaringClazz() {
			return declaringClazz;
		}

		public void setDeclaringClazz(Class<?> clazz) {
			this.declaringClazz = clazz;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		public Field getField() {
			return field;
		}

		public void setField(Field field) {
			this.field = field;
		}

		@Override
		public String toString() {
			String out = ""
					+ "{ "
					+ "fieldName: " + this.fieldName + ", "
					+ "fieldFoundOk: " + this.fieldFoundOk + ", "
					+ "valueRetrievedOk: " + this.valueRetrievedOk + ", "
					+ "declaringClazz: " + ( this.declaringClazz != null ? this.declaringClazz.getName() : null )  + ", "
					+ "fieldValue: " + this.fieldValue + ", " 
					+ "fieldValue.getClass(): " + ( this.fieldValue != null ? this.fieldValue.getClass().getName() : null ) + ", "
					+ "errorMessage: " + this.errorMessage + ""
					+ " }"
					;
			return out;
		}
	}


	/**
	 * Intermediate result pojo.
	 * 
	 * @author Alberto Romero
	 * @since 2024-03-16
	 *
	 */
	private static class FieldClazzPojo {

		private Class<?> declaringClazz = null;
		private Field field = null;

		public FieldClazzPojo () {
			super();
		}

		public Class<?> getDeclaringClazz() {
			return declaringClazz;
		}

		public void setDeclaringClazz(Class<?> clazz) {
			this.declaringClazz = clazz;
		}

		public Field getField() {
			return field;
		}

		public void setField(Field field) {
			this.field = field;
		}
	}



	/**
	 * <p>Testing Pojos.
	 * 
	 * @author Alberto Romero
	 * @since 2024-03-16
	 *
	 */
	private static class TestFatherPojo {

		private String fatherName;
		protected String fatherOccupation;
		private int fatherAge;

		public TestFatherPojo() {
			super();
		}

		public TestFatherPojo(String fatherName, String fatherOccupation, int fatherAge) {
			super();
			this.fatherName = fatherName;
			this.fatherOccupation = fatherOccupation;
			this.fatherAge = fatherAge;
		}

		public TestFatherPojo makeClone() {
			TestFatherPojo tfp = new TestFatherPojo(this.fatherName, this.fatherOccupation, this.fatherAge);
			return tfp;
		}

		public String getFatherName() {
			return this.fatherName;
		}

		public void setFatherName(String fatherName) {
			this.fatherName = fatherName;
		}

	}



	private static class TestSonPojo extends TestFatherPojo {

		public TestSonPojo (TestFatherPojo testFatherPojo) {
			super();
			this.setFatherName(testFatherPojo.getFatherName()); // protected field in super class
			this.fatherOccupation = testFatherPojo.fatherOccupation; // private field in super class
		}

	}

}
