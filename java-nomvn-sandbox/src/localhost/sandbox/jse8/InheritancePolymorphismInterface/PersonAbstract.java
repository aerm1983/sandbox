package localhost.sandbox.jse8.InheritancePolymorphismInterface;

public abstract class PersonAbstract {
    
    public String name ;
    
    public PersonAbstract() {
        
    }
    
    public String toString() {
        String string = "{Name:" + name + "}";
        return string ;
    }
    
    public void printMyName() {
        System.out.println("PersonDetailOne -- abstract -- {Name:" + name + "}");
        return;
    }
    
    public static void printSomething() {
        System.out.println("PersonDetailAbstract -- abstract -- this is just something to print");
        return;
    }


}
