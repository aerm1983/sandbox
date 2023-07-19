package localhost.sandbox.InheritancePolymorphismInterface;

import localhost.sandbox.InheritancePolymorphismInterface.pojo.PersonDetailOne;
import localhost.sandbox.InheritancePolymorphismInterface.pojo.PersonDetailOneIndependent;

public final class PersonStatic {
    
    /**
     * <p>As constructor is private, this class cannot be instantiated.
     * 
     * <p>The exception throwing prevents instantiation by reflection.
     */
    private PersonStatic () {
        throw new UnsupportedOperationException();
    }
	
    
    public static PersonInterface getPersonInterface() {
        return new PersonDetailOne();
    }

}
