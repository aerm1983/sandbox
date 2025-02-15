package localhost.sandbox.jse8.Generics;

import localhost.sandbox.jse8.Generics.Test01_FunctionWithGeneric.PersonDetailFour;

/**
 * As this class has a Generic Type attribute, such Generic Type 
 * should be applied to the whole class. 
 */
public class Test00_ClassWithGeneric {

	public static void test00_ClassWithGeneric() {

		PersonDetailOne<String> pdName = new PersonDetailOne<>("Alberto"); 
		PersonDetailOne<Integer> pdAge = new PersonDetailOne<>(40);
		PersonDetailOne<?> pdHeight = new PersonDetailOne<Double>(1.73);
		PersonDetailOne<?> pdDidService = new PersonDetailOne<>(false);

		PersonDetailFour pdFour = new PersonDetailFour();
		PersonDetailOne<?> pdOneFromFour = new PersonDetailOne<Boolean>();
		pdOneFromFour.setTDataFromPDFour(pdFour);
	}


	public static class PersonDetailOne<T> {

		public T data ;

		public PersonDetailOne() {
			super();
		}

		public PersonDetailOne(T data) {
			super();
			this.data = data;
		}

		public String toString() {
			String string = "{Name:" + data.toString() + "}";
			return string ;
		}

		public void setTDataFromPDFour (PersonDetailFour pDFour) {
			if ( this.data instanceof String  ) {
				pDFour.name = (String) this.data;
			} else if ( this.data instanceof Integer ) {
				pDFour.age = (Integer) this.data;
			} else if ( this.data instanceof Double ) {
				pDFour.height = (Double) this.data;
			} else if ( this.data instanceof Boolean ) {
				pDFour.didService = (Boolean) this.data;
			}
		}

	}
}
