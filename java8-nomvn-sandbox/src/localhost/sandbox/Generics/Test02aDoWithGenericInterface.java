package localhost.sandbox.Generics;

/**
 * Interfaces using Generic Types should apply such Generic to the 
 * whole interface/class to avoid "name clash collision".
 */
public interface Test02aDoWithGenericInterface <T extends Object> {
	
	public void doWithGeneric(T input);

}
