package localhost.sandbox.jse8.Generics;

import java.util.ArrayList;
import java.util.List;

import localhost.sandbox.jse8.Generics.Test00_ClassWithGeneric.PersonDetailOne;

/**
 * As this class is not extended by another, and method with Generic Type
 * is not overridden, such Generic Type can be applied only to the method, 
 * not the whole class.
 */
public class Test01_FunctionWithGeneric {

	// tests

	public static void test00_FunctionWithGeneric () {

		PersonDetailFour pdFour = new PersonDetailFour();
		// String
		Object oStr = pdFour.doSomethingOnObject("Hello");
		System.out.println("oStr: " + oStr);
		// Integer
		Object oInt = pdFour.doSomethingOnObject(1);
		System.out.println("oInt: " + oInt);
		// Double
		Object oDouble = pdFour.doSomethingOnObject(2.3);
		System.out.println("oDouble: " + oDouble);



		PersonDetailOne<String> pdOneA = new PersonDetailOne<>("AlexSyntek");
		PersonDetailOne<?> pdOneB = new PersonDetailOne<>(31.7);
		PersonDetailOne<?> pdOneC = new PersonDetailOne<Boolean>(true);
		pdFour.setAttributeFromPDOne(pdOneA);
		pdFour.setAttributeFromPDOne(pdOneB);
		pdFour.setAttributeFromPDOne(pdOneC);



		System.out.println("Function with Generics -- list");
		List<PairKeyValueOne> listPkvOne = new ArrayList<>();
		List<PairKeyValueTwo> listPkvTwo = new ArrayList<>();
		List<PairNameContent> listPnc = new ArrayList<>();
		listPkvOne.addAll(PersonDetailFour.processKeyValueString("name=Endika|age=29.7|male=true", PairKeyValueOne.class));
		listPkvTwo.addAll(PersonDetailFour.processKeyValueString("name=Endika|age=29.7|male=true", PairKeyValueTwo.class));
		listPnc.addAll(PersonDetailFour.processKeyValueString("name=Endika|age=29.7|male=true", PairNameContent.class));
		System.out.println("listPkvOne: " + listPkvOne);
		System.out.println("listPkvTwo: " + listPkvTwo);
		System.out.println("listPnc: " + listPnc);
	}

	public static class PersonDetailFour {

		public String name;
		public Integer age;
		public Double height;
		public Boolean didService;


		// constructors, functions, inner classes 

		public PersonDetailFour() {
			super();
		}

		public String toString() {
			String string = "{Name:" + name + ", Age:" + age + ", height:" + height + ", didService: " + didService + "}" ;
			return string ;
		}

		public <T extends Object> void setAttributeFromPDOne(PersonDetailOne<T> pDOne) {
			if ( pDOne instanceof PersonDetailOne ) {
				if ( pDOne.data instanceof String ) {
					this.name = (String) pDOne.data;
				} else if ( pDOne.data instanceof Integer) {
					this.age = (Integer) pDOne.data;
				} else if ( pDOne.data instanceof Double) {
					this.height = (Double) pDOne.data;
				} else if ( pDOne.data instanceof Boolean) {
					this.didService = (Boolean) pDOne.data;
				}
			}
		}


		/**
		 * <p> This is an example of a function accepting Generic.
		 * 
		 * <p> If input object is a primitive-type-wrapping-object, logic is forceful; in
		 * this case, this kind of function does not make much sense.
		 * 
		 * <p> But if input object is an object containing primitive-type-wrapping-objects,  
		 * and operations are performed on the later ones, this might make sense.
		 * 
		 * <p> Use of Reflection could also further soften logic within function.
		 * 
		 * <p> Examples:
		 * <ul>
		 * <li> For inputs like 1 or 1.6 or "hello", etc. this function's logic is forceful
		 * <li> For input like {name:"Juan",surname:"acosta"}, this makes better sense.
		 * </ul>
		 * 
		 * @param <T> type or class
		 * @param obj input object
		 * @return object of same type, modified
		 * @author Alberto Romero
		 * @since 2023-09-29
		 */
		@SuppressWarnings("unchecked")
		public <T extends Object> T doSomethingOnObject(T obj) {
			if (obj instanceof String) {
				obj = (T) ( (String) obj + " withSomethingAdded!" ) ;
			} else if (obj instanceof Integer) {
				obj = (T) Integer.valueOf( (Integer) obj + 1 ) ;
			} else if (obj instanceof Double) {
				obj = (T) Double.valueOf( (Double) obj + 3.1416 ) ;
			}
			return obj;
		}



		/**
		 * <p>Function with Generic, example, return List&lt;T&gt;.
		 * 
		 * 
		 * @autho Alberto Romero
		 * @since 2024-10-15
		 */
		@SuppressWarnings("unchecked")
		public static <T> List<T> processKeyValueString(String nameValueStr, Class<T> clazz) {
			List<T> typeList = new ArrayList<>();
			if (nameValueStr == null || "".equals(nameValueStr)) {
				return typeList;
			}
			if (!(clazz == PairKeyValueOne.class || clazz == PairKeyValueTwo.class || clazz == PairNameContent.class))
			{
				return typeList;
			} 
			final String[] keyValueArray = nameValueStr.split("\\|");
			for (String nameValue : keyValueArray) {
				String[] splittedKV = nameValue.split("=");
				if (splittedKV.length == 2) {
					if (clazz == PairKeyValueOne.class) 
					{
						typeList.add((T) new PairKeyValueOne(splittedKV[0], splittedKV[1]));
					} 
					else if (clazz == PairKeyValueTwo.class) 
					{
						typeList.add((T) new PairKeyValueTwo(splittedKV[0], splittedKV[1]));
					} 
					else if (clazz == PairNameContent.class) 
					{
						typeList.add((T) new PairNameContent(splittedKV[0], splittedKV[1]));
					}
				}
			}
			return typeList;
		}

	}


	public static class PairKeyValueOne {

		public String key;
		public String value;

		public PairKeyValueOne (String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}

		public String toString() {
			String out = "{ "
					+ "" + "key: " + key
					+ ", " + "value: " + value
					+ " }"
					;
			return out;
		}

	}


	public static class PairKeyValueTwo extends PairKeyValueOne {

		public PairKeyValueTwo (String key, String value) {
			super(key, value);
		}

	}


	public static class PairNameContent {

		public String name;
		public String content;

		public PairNameContent (String name, String content) {
			super();
			this.name = name;
			this.content = content;
		}

		public String toString() {
			String out = "{ "
					+ "" + "name: " + name
					+ ", " + "content: " + content
					+ " }"
					;
			return out;
		}

	}

}
