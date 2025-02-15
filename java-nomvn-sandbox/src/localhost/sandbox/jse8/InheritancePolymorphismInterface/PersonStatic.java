package localhost.sandbox.jse8.InheritancePolymorphismInterface;

import localhost.sandbox.jse8.InheritancePolymorphismInterface.pojo.PersonDetailOne;
import localhost.sandbox.jse8.InheritancePolymorphismInterface.pojo.PersonDetailOneIndependent;

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
