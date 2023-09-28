package localhost.helper;

import java.lang.reflect.Field;
import java.util.Objects;

public class ReflectionHelper {
	
	public static void main() {
		
		System.out.println("---- REFLECTION, OBJECT COPY, SUPER CLASS RECURSION: 0 (ZERO) ----");
		
		Person personOne = new Person("Jose", 26, 1.78, true);
		Person personTwo = (Person) makeCopyWithSuperClassRecursionOf( (Object) personOne, 0 );
		boolean deepEq = Objects.deepEquals(personOne, personTwo);
		System.out.println("before changes -- personOne: " + personOne.toString() + " ; personTwo: " + personTwo.toString());
		System.out.println("before changes -- Objects.deepEquals(personOne, personTwo): " + deepEq);
		
		personOne.setName("Maria");
		personOne.setAge(18);
		personOne.setHeight(1.42);
		personOne.setDidService(false);
		System.out.println("after changes -- personOne: " + personOne.toString() + " ; personTwo: " + personTwo.toString());
		
		
		System.out.println("---- REFLECTION, OBJECT COPY, SUPER CLASS RECURSION: 1 (ONE) ----");
		
		personOne = new Person("Jose", 26, 1.78, true);
		Pet petOne = new Pet("Ponky");
		PersonWithPet personWithPetOne = new PersonWithPet(personOne, petOne);
		PersonWithPet personWithPetTwo = (PersonWithPet) makeCopyWithSuperClassRecursionOf( (Object) personWithPetOne, 1 );
		boolean deepEq2 = Objects.deepEquals(personWithPetOne, personWithPetTwo);
		System.out.println("before changes -- personWithPetOne: " + personWithPetOne.toString() + " ; personWithPetTwo: " + personWithPetTwo.toString());
		System.out.println("before changes -- Objects.deepEquals(personOne, personTwo): " + deepEq2);
		
		personWithPetOne.setName("Maria");
		personWithPetOne.setAge(18);
		personWithPetOne.setHeight(1.42);
		personWithPetOne.setDidService(false);
		personWithPetOne.getPet().setName("Adrian");
		System.out.println("after changes -- personWithPetOne: " + personWithPetOne.toString() + " ; personWithPetTwo: " + personWithPetTwo.toString());

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
		try {
			clazz = inObj.getClass();
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
	static class Person {
		private String name;
		private Integer age;
		private Double height;
		private Boolean didService;
		
		public Person () {
		}
		
		public Person (String inName, Integer inAge, Double inHeight, Boolean inDidService) {
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
			String string = "{Name:" + name + ", Age:" + age + ", height:" + height + ", didService: " + didService + "}" ;
			return string ;
		}

	}
	
	static class Pet {
			
		private String name;
		
		Pet () {
		}
		
		Pet (String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
	static class PersonWithPet extends Person {
		
		private Pet pet;
		
		public PersonWithPet () {
		}
		
		public PersonWithPet (Person person, Pet pet) {
			super(person.getName(), person.getAge(), person.getHeight(), person.getDidService());
			this.pet = pet;
		}
		
		public String toString() {
			String string = "{Name:" + getName() + ", Age:" + getAge() + ", height:" + getHeight() + ", didService: " + getDidService() + ", pet: {name: " + pet.getName() + "}}" ;
			return string ;
		}

		public Pet getPet() {
			return pet;
		}

		public void setPet(Pet pet) {
			this.pet = pet;
		}
	}
}
