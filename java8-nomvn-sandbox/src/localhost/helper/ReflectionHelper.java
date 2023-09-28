package localhost.helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;

public class ReflectionHelper {
	
	public static void main() {
		Example01SimplePojo();
		Example02PojoWithSuperClasses();
		Example03PojoWithAttributePojo();
	}
	
	public static void Example01SimplePojo() {
		
		System.out.println("\n---- REFLECTION, OBJECT COPY, Example01SimplePojo: SUPER CLASS RECURSION: NONE NEEDED ----");
		
		// initial
		SimplePerson simplePersonOne = new SimplePerson("Jose", 26, 1.78, true);
		SimplePerson simplePersonTwo = (SimplePerson) makeCopyWithSuperClassRecursionOf( (Object) simplePersonOne, 20 );
		boolean deepEq01 = Objects.deepEquals(simplePersonOne, simplePersonTwo);
		System.out.println("before changes -- personOne: " + simplePersonOne.toString() + " ; personTwo: " + simplePersonTwo.toString());
		System.out.println("before changes -- Objects.deepEquals(simplePersonOne, simplePersonTwo): " + deepEq01);
		
		// changes
		simplePersonOne.setName("Maria");
		simplePersonOne.setAge(18);
		simplePersonOne.setHeight(1.42);
		simplePersonOne.setDidService(false);
		System.out.println("after changes -- simplePersonOne: " + simplePersonOne.toString() + " ; simplePersonTwo: " + simplePersonTwo.toString());
		System.out.println("conclussion: attribute changes in simplePersonOne did not affect attributes in simplePersonTwo");
	}
	
	public static void Example02PojoWithSuperClasses () {
		System.out.println("\n---- REFLECTION, OBJECT COPY, Example02PojoWithSuperClasses: SUPER CLASS RECURSION: 4 ----");
		
		// initial
		MyGreatGrandFather myGreatGrandFather = new MyGreatGrandFather("Carlos");
		MyGrandFather myGrandFather = new MyGrandFather(myGreatGrandFather.getName(), 41);
		MyFather myFather = new MyFather(myGreatGrandFather.getName(), myGrandFather.getAge(), 1.92);
		Me meOne = new Me(myGreatGrandFather.getName(), myGrandFather.getAge(), myFather.getHeight(), true);
		Me meTwo = (Me) makeCopyWithSuperClassRecursionOf( (Object) meOne, 20 );
		boolean deepEq02 = Objects.deepEquals(meOne, meTwo);
		System.out.println("before changes -- meOne: " + meOne.toString() + " ; meTwo: " + meTwo.toString());
		System.out.println("before changes -- Objects.deepEquals(meOne, meTwo): " + deepEq02);

		// changes
		myGreatGrandFather.setName("OscarChanged");
		myGrandFather.setAge(1);
		myFather.setHeight(2.11);
		System.out.println("after changes -- meOne: " + meOne.toString() + " ; meTwo: " + meTwo.toString());
		System.out.println("conclussion: attribute changes in SuperClasses (GreatGrandFather, GrandFather, Father) for meOne did not affect clone meTwo.  Note that attributes changed are not objects containing other objects, but objects with an associated value");
	}
	
	public static void Example03PojoWithAttributePojo() {
		System.out.println("\n---- REFLECTION, OBJECT COPY, Example03PojoWithAttributePojo: SUPER CLASS RECURSION: NONE NEEDED, ATTRIBUTE RECURSION: SEVERAL ----");
		
		PersonWithParent myGreatGrandFather = new PersonWithParent("A_GreatGrandFather", null);
		PersonWithParent myGrandFather = new PersonWithParent("B_GrandFather", myGreatGrandFather);
		PersonWithParent myFather = new PersonWithParent("C_Father", myGrandFather);
		PersonWithParent meOne = new PersonWithParent("D_Me", myFather);
		PersonWithParent meTwo = (PersonWithParent) makeCopyWithSuperClassRecursionOf( (Object) meOne, 20 );
		boolean deepEq03 = Objects.deepEquals(meOne, meTwo);
		System.out.println("before changes -- meOne: " + meOne.toString() + " ; meTwo: " + meTwo.toString());
		System.out.println("before changes -- Objects.deepEquals(meOne, meTwo): " + deepEq03);

		// changes
		myGreatGrandFather.setName("Changed_GreatGrandFather");
		myGrandFather.setName("Changed_GrandFather");
		myFather.setName("Changed_Father");
		System.out.println("after changes -- meOne: " + meOne.toString() + " ; meTwo: " + meTwo.toString());
		System.out.println("conclussion: changes in attribute parent (GreatGrandFather, GrandFather, Father) for meOne did affect clone meTwo.  Note that attributes changed are objects containing other objects, not objects with an associated value");
	}
	
	/**
	 * <p> Make copy of object.  Uses reflection to replicate field values.
	 * 
	 * <p> A value of "0" for maxSuperClassRecursion copies only fields from object's class.
	 * 
	 * <p> A value of "1" copies also fields from object's parent (super) class.
	 * 
	 * <p> A value of "2" copies also fields from object's parent-parent (super-super) class.
	 * 
	 * <p> And so on.
	 * 
	 * @param inObj the object to copy
	 * @param maxSuperClassRecursion how many super-classes to use for field value copying.
	 * @return new object of the same class, with field values copied.
	 */
	public static Object makeCopyWithSuperClassRecursionOf(Object inObj, int maxSuperClassRecursion) {
		Class<?> clazz = null;
		Object outObj = null;
		boolean fAccessible = false;
		Field[] fields = null;
		int superClassRecursion = 0;
		ArrayList<String> classList = new ArrayList<String>();
		try {
			clazz = inObj.getClass();
			classList.add(clazz.getName());
			outObj = (Object) clazz.newInstance();
			while (superClassRecursion <= maxSuperClassRecursion) {
				fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					fAccessible = field.isAccessible();
					field.setAccessible(true);
					field.set(outObj, field.get(inObj));
					field.setAccessible(fAccessible);
				}
				clazz = clazz.getSuperclass();
				if (clazz == null) {
					System.out.println("--> exit at superClassRecursion: " + superClassRecursion + " -- maxSuperClassRecursion (" + maxSuperClassRecursion + ") not reached -- classes: " + classList);
					break;
				}
				classList.add(clazz.getName());
				superClassRecursion++;
			}
		} catch (Exception e) {
			System.err.println("error: " + e);
			return null;
		}
		return outObj;
	}
	
	/**
	 * <p>Inner class intended only for testing purposes.
	 * 
	 * @author Alberto Romero
	 *
	 */
	static class SimplePerson {
		private String name;
		private Integer age;
		private Double height;
		private Boolean didService;
		
		public SimplePerson () {
		}
		
		public SimplePerson (String inName, Integer inAge, Double inHeight, Boolean inDidService) {
			this.name = inName;
			this.age = inAge;
			this.height = inHeight;
			this.didService = inDidService;
		}
		
		
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public Double getHeight() {
			return height;
		}

		public void setHeight(Double height) {
			this.height = height;
		}

		public Boolean getDidService() {
			return didService;
		}

		public void setDidService(Boolean didService) {
			this.didService = didService;
		}

		public String toString() {
			String string = "{Name:" + name + ",Age:" + age + ",height:" + height + ",didService: " + didService + "}" ;
			return string ;
		}

	}
	
	static class MyGreatGrandFather {
			
		private String name;
		
		MyGreatGrandFather () {
		}
		
		MyGreatGrandFather (String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public String toString() {
			String string = "{Name:" + getName() + "}" ;
			return string ;
		}
	}
	
	static class MyGrandFather extends MyGreatGrandFather {
		
		private Integer age;
		
		public MyGrandFather () {
		}
		
		public MyGrandFather (String name, Integer age) {
			super(name);
			this.age = age;
		}
		
		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public String toString() {
			String string = "{Name:" + getName() + ",Age:" + getAge() + "}" ;
			return string ;
		}
	}
	
	static class MyFather extends MyGrandFather {
		
		private Double height;
		
		MyFather() {
		}
		
		MyFather(String name, Integer age, Double height) {
			super(name, age);
			this.height = height;
		}

		public Double getHeight() {
			return height;
		}

		public void setHeight(Double height) {
			this.height = height;
		}
		
		public String toString() {
			String string = "{Name:" + getName() + ",Age:" + getAge() + ",height:" + height + "}" ;
			return string ;
		}
		
	}
	
	static class Me extends MyFather {
		
		private Boolean didService;
		
		Me () {
		}
		
		Me (String name, Integer age, Double height, Boolean didService) {
			super(name, age, height);
			this.didService = didService;
		}

		public Boolean getDidService() {
			return didService;
		}

		public void setDidService(Boolean didService) {
			this.didService = didService;
		}
		
		public String toString() {
			String string = "{Name:" + getName() + ",Age:" + getAge() + ",height:" + getHeight() + ",didService:" + didService + "}" ;
			return string ;
		}
	}
	
	static class PersonWithParent {
		
		private String name;
		private PersonWithParent parent;
		
		PersonWithParent () {
		}
		
		PersonWithParent (String name, PersonWithParent parent) {
			this.name = name;
			this.parent = parent;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public PersonWithParent getParent() {
			return parent;
		}

		public void setParent(PersonWithParent parent) {
			this.parent = parent;
		}
		
		public String toString() {
			String string = null;
			if (parent != null) {
				string = "{Name:" + getName() + ",Parent:" + parent.toString() + "}" ;	
			} else {
				string = "{Name:" + getName() + ",Parent:null}" ;
			}
			return string ;
		}
		
	}
}
