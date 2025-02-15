package localhost.sandbox.jse8.A0Helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;

public class ObjectCopyHelper {

	public static void main() {
		Example01SimplePojo();
		Example02PojoWithSuperClasses();
		Example03PojoWithAttributePojo();
	}

	/**
	 * <p> Make copy of object.  Uses reflection to copy field values.
	 * 
	 * <p> It is not mandatory to define second parameter (see overloadad function).  If used, here some guidance:
	 * 
	 * <ul>
	 * 
	 * <li> A value of "0" for maxSuperClassRecursion copies only fields from object's class.
	 * 
	 * <li> A value of "1" copies fields from object's parent (super) class.
	 * 
	 * <li> A value of "2" copies fields from object's parent-parent (super-super) class.
	 * 
	 * <li> A high value can be passed as second parameter, this function will manage super-class-recursion until reaching a null super-class.
	 * 
	 * </ul>
	 * 
	 * <p> Result may be deep or shallow copy:
	 * 
	 * <ul>
	 * 
	 * <li> If all attributes copied are primitive-type-wrapping-objects, a deep-copy is generated.
	 * 
	 * <li> If at least one attribute is an object containing other objects, a shallow-copy is generated.
	 * 
	 * </ul>
	 * 
	 * <p> Regarding deep and shallow copies.  Say objTwo is copy of objOne.  Then:
	 * 
	 * <ul>
	 * 
	 * <li> If changing attributes of objOne change also corresponding attributes of objTwo, then the later is shallow-copy.
	 * 
	 * <li> If changing attributes of objOne do not change corresponding attributes of objTwo, then the later is deep-copy.
	 * 
	 * </ul>
	 * 
	 * <p> Examples:
	 * 
	 * <ul>
	 * 
	 * <li> For {name:"Juan", age:31, didService:true}, a deep-copy will be generated (see example 01).
	 * 
	 * <li> In previous example, if each attribute is defined in a different super-class, a deep-copy will be generated (see example 02).
	 * 
	 * <li> For {name:"Juan", parent:{name:"Luis"}}, a shallow-copy will be generated (see example 03).
	 * 
	 * </ul>
	 * 
	 * @param inObj the object to copy
	 * 
	 * @param maxSuperClassRecursion how many super-classes to use for field value copying.
	 * 
	 * @return new object of the same class, with field values copied.
	 * 
	 * @author Alberto Romero
	 * 
	 * @since 2023-09-28
	 *  
	 */
	public static Object makeCopyOf(Object inObj, int maxSuperClassRecursion) {
		Class<?> clazz = null;
		Object outObj = null;
		boolean fAccessible = false;
		Field[] fields = null;
		int superClassRecursion = 0;
		ArrayList<String> classList = new ArrayList<String>();
		String outStr = null;
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
					outStr = ""
							+ "--> maxSuperClassRecursion (" + maxSuperClassRecursion + ") not reached"
							+ " -- exit at superClassRecursion: " + superClassRecursion 
							+ " -- classes: " + classList + ""
							;
					// System.out.println(); // debugging
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
	 * <p> Overloaded function, to avoid definition of "maxSuperClassRecursion" parameter.
	 * 
	 * @param inObj the object to copy 
	 * @return new object of the same class, with field values copied.
	 */
	public static Object makeCopyOf(Object inObj) {
		return makeCopyOf(inObj, 127);
	}



	/**
	 * Examples block.
	 */


	public static void Example01SimplePojo() {

		System.out.println("\n---- Example01SimplePojo: REFLECTION, OBJECT COPY, SUPER CLASS RECURSION: NONE NEEDED ----");

		// initial
		SimplePerson simplePersonOne = new SimplePerson("Jose", 26, 1.78, true);
		SimplePerson simplePersonTwo = (SimplePerson) makeCopyOf( simplePersonOne );
		boolean deepEq01 = Objects.deepEquals(simplePersonOne, simplePersonTwo);
		System.out.println("before changes -- personOne: " + simplePersonOne.toString() + " ; personTwo: " + simplePersonTwo.toString());
		System.out.println("before changes -- Objects.deepEquals(simplePersonOne, simplePersonTwo): " + deepEq01);

		// changes
		simplePersonOne.setName("Maria");
		simplePersonOne.setAge(18);
		simplePersonOne.setHeight(1.42);
		simplePersonOne.setDidService(false);
		System.out.println("after changes -- simplePersonOne: " + simplePersonOne.toString() + " ; simplePersonTwo: " + simplePersonTwo.toString());
		System.out.println("conclussion: attribute changes in simplePersonOne did not affect attributes in simplePersonTwo.  Note that changed attributes are not objects containing other objects, but primitive-type-wrapping-objects; thus, simplePersonTwo is a deep-copy of simplePersonOne");
	}

	public static void Example02PojoWithSuperClasses () {
		System.out.println("\n---- Example02PojoWithSuperClasses: REFLECTION, OBJECT COPY, SUPER CLASS RECURSION: 4 ----");

		// initial
		MyGreatGrandFather myGreatGrandFather = new MyGreatGrandFather("Carlos");
		MyGrandFather myGrandFather = new MyGrandFather(myGreatGrandFather.getName(), 41);
		MyFather myFather = new MyFather(myGreatGrandFather.getName(), myGrandFather.getAge(), 1.92);
		Me meOne = new Me(myGreatGrandFather.getName(), myGrandFather.getAge(), myFather.getHeight(), true);
		Me meTwo = (Me) makeCopyOf( meOne );
		boolean deepEq02 = Objects.deepEquals(meOne, meTwo);
		System.out.println("before changes -- meOne: " + meOne.toString() + " ; meTwo: " + meTwo.toString());
		System.out.println("before changes -- Objects.deepEquals(meOne, meTwo): " + deepEq02);

		// changes
		myGreatGrandFather.setName("OscarChanged");
		myGrandFather.setAge(1);
		myFather.setHeight(2.11);
		System.out.println("after changes -- meOne: " + meOne.toString() + " ; meTwo: " + meTwo.toString());
		System.out.println("conclussion: attribute changes in SuperClasses (GreatGrandFather, GrandFather, Father) for meOne did not affect clone meTwo.  Note that attributes changed are not objects containing other objects, but primitive-type-wrapping-objects; thus, meTwo is a deep-copy of meOne");
	}

	public static void Example03PojoWithAttributePojo() {
		System.out.println("\n---- Example03PojoWithAttributePojo: REFLECTION, OBJECT COPY, SUPER CLASS RECURSION: NONE NEEDED, ATTRIBUTE RECURSION: SEVERAL ----");

		PersonWithParent myGreatGrandFather = new PersonWithParent("A_GreatGrandFather", null);
		PersonWithParent myGrandFather = new PersonWithParent("B_GrandFather", myGreatGrandFather);
		PersonWithParent myFather = new PersonWithParent("C_Father", myGrandFather);
		PersonWithParent meOne = new PersonWithParent("D_Me", myFather);
		PersonWithParent meTwo = (PersonWithParent) makeCopyOf( meOne );
		boolean deepEq03 = Objects.deepEquals(meOne, meTwo);
		System.out.println("before changes -- meOne: " + meOne.toString() + " ; meTwo: " + meTwo.toString());
		System.out.println("before changes -- Objects.deepEquals(meOne, meTwo): " + deepEq03);

		// changes
		myGreatGrandFather.setName("Changed_GreatGrandFather");
		myGrandFather.setName("Changed_GrandFather");
		myFather.setName("Changed_Father");
		System.out.println("after changes -- meOne: " + meOne.toString() + " ; meTwo: " + meTwo.toString());
		System.out.println("conclussion: changes in attribute parent (GreatGrandFather, GrandFather, Father) for meOne did affect clone meTwo.  Note that attributes changed are objects containing other objects, not primitive-type-wrapping-objects; thus, meTwo is a shallow-copy of meOne");
	}



	/**
	 * Pojos used for examples
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
