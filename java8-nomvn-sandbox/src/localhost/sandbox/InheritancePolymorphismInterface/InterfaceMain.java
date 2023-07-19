package localhost.sandbox.InheritancePolymorphismInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import localhost.sandbox.InheritancePolymorphismInterface.pojo.PersonDetailFour;
import localhost.sandbox.InheritancePolymorphismInterface.pojo.PersonDetailOne;
import localhost.sandbox.InheritancePolymorphismInterface.pojo.PersonDetailOneIndependent;
import localhost.sandbox.InheritancePolymorphismInterface.pojo.PersonDetailThree;
import localhost.sandbox.InheritancePolymorphismInterface.pojo.PersonDetailTwo;

public class InterfaceMain {

	public static void main() {
		
		System.out.println("Hello from InterfaceMain!");
		
		// first part
		List<?> sl1 = new ArrayList<String>();
		List<?> sl2 = new LinkedList<String>();
		
		Map<?,?> ms1 = new HashMap<String,String>();
		Map<?,?> ms2 = new TreeMap<String,String>();
		
		PersonInterface i0PersonOne = new PersonDetailOne();
		PersonInterface i0PersonTwo = new PersonDetailTwo();
		PersonInterface i0PersonThree = new PersonDetailThree();
		PersonInterface i0PersonFour = new PersonDetailFour();
		
		// second part
		
		/**
		 * This is the best case scenario.
		 * Code is limited to use only methods indicated by interface.
		 */
		PersonInterface interfacePerson = PersonStatic.getPersonInterface();
		System.out.println("interfacePerson.getClass(): " + interfacePerson.getClass());
		
		/**
		 * This will work as long as StaticPerson.getIterfacePerson returns PersonDetailOne.
		 * If return type changes to other which cannot be cast to PersonDetailOne
		 * (for example, personDetailOneIndependent), this will throw a ClassCastException.
		 */
		PersonDetailOne personDetailOne = (PersonDetailOne) PersonStatic.getPersonInterface();
		
		
		return;
		
		
	}

}
