package localhost.helper;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

// import javax.validation.constraints.Size;

/**
 * This class intends to be inter-exchangeable with javax package "Size" annotation, 
 * but using only Java SE components.
 * 
 * Original import:
 * import javax.validation.constraints.Size;
 * 
 * @author Alberto Romero
 * @since 2023-10-18
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Size {
	public int max() default 0;
}
