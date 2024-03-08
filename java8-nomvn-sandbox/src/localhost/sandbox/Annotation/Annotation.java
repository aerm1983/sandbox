package localhost.sandbox.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Annotation {
	
	/**
	 * Annotation to indicate if encryption applies to string field.
	 * 
	 * @author Alberto Romero
	 *
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface DbFieldEncryptTest {

	}
	
	
	/**
	 * Annotation to indicate max size (length) of string field. 
	 * 
	 * @author Alberto Romero
	 *
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface SizeTest {
		public int max() default 0;
	}

}
