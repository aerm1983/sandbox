package localhost.sandbox.ClassLoader;

import java.net.URL;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class Test00IntroSimple {

	/**
	 * <h1>ClassLoader types:</h1>
	 * 
	 * <ul>
	 * 
	 * <li><i>Bootstrap Class Loader:</i> JVM built-in; is <i>null</i> Java object,
	 * since it's native code, and is parent to all ClassLoader instances.
	 * 
	 * <li><i>Platform Class Loader:</i> Java SE classes, platform classes.
	 * 
	 * <li><i>System Class Loader:</i> or application class loader, loads classes
	 * on application class path; its direct parent is <i>Platform Class Loader</i>. 
	 * 
	 * </ul>
	 * 
	 * <p>A parent ClassLoader has visibility over its children's loaded classes,
	 * but not vice versa.
	 * 
	 * @since 2025-02-08
	 * 
	 */
	public static void test00ClassLoaderTypes() {

		System.out.println("Hello from test00ClassLoaderTypes!");

		System.out.println("Platform Classloader:" 
				+ ClassLoader.getPlatformClassLoader()); 

		System.out.println("System (Application) Classloader:" 
				+ ClassLoader.getSystemClassLoader());

		System.out.println("Classloader of this class:"
				+ Test00IntroSimple.class.getClassLoader());

		System.out.println("Classloader of DriverManager:"
				+ DriverManager.class.getClassLoader());

		System.out.println("Classloader of ArrayList (Bootstrap ClassLoader):"
				+ ArrayList.class.getClassLoader());
	}



	/**
	 * <p>Get URL of any file type reachable from class path.
	 * 
	 * <p>Note that different URLs may refer to resources having the
	 * same name, but not necessarily the same content.  If such 
	 * resources are <i>.class</i> files (Java byte code), compiler will
	 * complain accordingly.
	 * 
	 * <p>Example of URLs containing duplicated Java class:
	 * <ul>
	 * <li>jrt:/java.xml/org/w3c/dom/Element.class
	 * <li>jar:file:/C:/Users/my-home-folder/.m2/repository/xml-apis/xml-apis/1.3.04/xml-apis-1.3.04.jar!/org/w3c/dom/Element.class
	 * </ul>
	 * 
	 * <p>Then, to locate dependency including jar with duplicated resource:
	 * <br>
	 * <code> mvn dependency:tree</code>
	 * 
	 * <p>Example of URL, simple file:
	 * <ul>
	 * <li>file:/C:/Users/my-home-folder/Desktop/my-java-root-folder/bin/localhost/helper/LargeStringHelper.class
	 * </ul>
	 * 
	 * @author Alberto Romero
	 * @since 2025-02-08
	 * 
	 */
	public static void test01GetResourceUrl() {

		System.out.println("Hello from test01GetResourceUrl!");

		ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader(); // application's ClassLoader

		String resourceToSearch = "localhost/helper/LargeStringHelper.class"; // example indicates class, but it could be any file type
		Enumeration<URL> urlEnum = null;

		try {
			urlEnum = sysClassLoader.getResources(resourceToSearch);
		} catch (Throwable ex) {
			System.err.println("exception -- " + ex);
		}
		ArrayList<URL> urlList = Collections.list(urlEnum);
		System.out.println("resourceToSearch, location in classpath (.class file in this example, but it could be any type):");
		urlList.forEach(System.out::println);
	}



	/**
	 * <p>In this example, classes are imported dynamically
	 * (not explicitly imported in this class).
	 * 
	 * @author Alberto Romero
	 * @since 2025-02-08
	 */
	public static void test02LoadClass() {

		System.out.println("Hello from test02LoadClass!");

		ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader(); // application's ClassLoader

		// class

		String clazzToLoad = "localhost.helper.HexHelper"; // binary name, examples: "localhost.helper.LargeStringHelper", "localhost.helper.LargeStringHelper$LSH"

		Class<?> clazz = null;;
		try {
			clazz = sysClassLoader.loadClass(clazzToLoad);
		} catch (ClassNotFoundException ex) {
			System.err.println("exception -- " + ex);
		}

		if (clazz != null) {
			System.out.println("clazz.getName(): " + clazz.getName());
			System.out.println("clazz.getCanonicalName(): " + clazz.getCanonicalName());
		} else {
			System.out.println("clazz is null (no class loaded)");
		}

		// inner class

		String innerClazzToLoad = "localhost.helper.LargeStringHelper$LSH"; // binary name, examples: "localhost.helper.LargeStringHelper", "localhost.helper.LargeStringHelper$LSH"

		Class<?> innerClazz = null;;
		try {
			innerClazz = sysClassLoader.loadClass(innerClazzToLoad);
		} catch (ClassNotFoundException ex) {
			System.err.println("exception -- " + ex);
		}

		if (innerClazz != null) {
			System.out.println("innerClazz.getName(): " + innerClazz.getName());
			System.out.println("innerClazz.getCanonicalName(): " + innerClazz.getCanonicalName());
		} else {
			System.out.println("innerClazz is null (no inner class loaded)");
		}
	}

}
