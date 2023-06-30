package localhost.sandbox.history.off.InheritancePolymorphismInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import localhost.sandbox.history.off.InheritancePolymorphismInterface.pojo.PersonDetailOne;
import localhost.sandbox.history.off.InheritancePolymorphismInterface.pojo.PersonDetailOneIndependent;
import localhost.sandbox.history.off.InheritancePolymorphismInterface.pojo.PersonDetailTwo;
import localhost.sandbox.history.off.InheritancePolymorphismInterface.pojo.PersonDetailThree;
import localhost.sandbox.history.off.InheritancePolymorphismInterface.pojo.PersonDetailFour;

public class InterfaceMain {

	public static void main() {
		
		System.out.println("Hello from InterfaceMain!");
		
		// first part
		List<?> sl1 = new ArrayList<String>();
		List<?> sl2 = new LinkedList<String>();
		
		Map<?,?> ms1 = new HashMap<String,String>();
		Map<?,?> ms2 = new TreeMap<String,String>();
		
		InterfacePerson i0PersonOne = new PersonDetailOne();
		InterfacePerson i0PersonTwo = new PersonDetailTwo();
		InterfacePerson i0PersonThree = new PersonDetailThree();
		InterfacePerson i0PersonFour = new PersonDetailFour();
		
		// second part
		
		/**
		 * This is the best case scenario.
		 * Code is limited to use only methods indicated by interface.
		 */
		InterfacePerson interfacePerson = StaticPerson.getInterfacePerson();
		System.out.println("interfacePerson.getClass(): " + interfacePerson.getClass());
		
		/**
		 * This will work as long as StaticPerson.getIterfacePerson returns PersonDetailOne.
		 * If return type changes to other which cannot be cast to PersonDetailOne
		 * (for example, personDetailOneIndependent), this will throw a ClassCastException.
		 */
		PersonDetailOne personDetailOne = (PersonDetailOne) StaticPerson.getInterfacePerson();
		
		
		return;
		
		
	}

}
