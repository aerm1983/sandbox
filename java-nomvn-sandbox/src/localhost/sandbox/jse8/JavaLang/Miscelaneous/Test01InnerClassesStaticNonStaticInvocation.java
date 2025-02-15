package localhost.sandbox.jse8.JavaLang.Miscelaneous;


public class Test01InnerClassesStaticNonStaticInvocation {

	/**
	 * <p>Test instantiation of static and non-static inner Classes. 
	 */
	public static void test() {
		
		/**
		 * instantiation of static inner class
		 */
		Test01InnerClassesStaticNonStaticPojo.SimplePojoOne sp1 = new Test01InnerClassesStaticNonStaticPojo.SimplePojoOne();
		
		/**
		 * instantiation of non-static inner class
		 */
		Test01InnerClassesStaticNonStaticPojo.SimplePojoThree sp3 = new Test01InnerClassesStaticNonStaticPojo().new SimplePojoThree();
	}
	
}
