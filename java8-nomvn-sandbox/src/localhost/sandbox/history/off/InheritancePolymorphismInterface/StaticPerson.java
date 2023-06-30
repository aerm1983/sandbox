package localhost.sandbox.history.off.InheritancePolymorphismInterface;

import localhost.sandbox.history.off.InheritancePolymorphismInterface.pojo.PersonDetailOne;
import localhost.sandbox.history.off.InheritancePolymorphismInterface.pojo.PersonDetailOneIndependent;

public final class StaticPerson {
    
    /**
     * <p>As constructor is private, this class cannot be instantiated.
     * 
     * <p>The exception throwing prevents instantiation by reflection.
     */
    private StaticPerson () {
        throw new UnsupportedOperationException();
    }
	
    
    public static InterfacePerson getInterfacePerson() {
        return new PersonDetailOne();
    }

}
