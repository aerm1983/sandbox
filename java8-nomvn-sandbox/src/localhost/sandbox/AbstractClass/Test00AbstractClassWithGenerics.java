package localhost.sandbox.AbstractClass;

import java.util.List;

public class Test00AbstractClassWithGenerics {

	static public void test00_AbstractClassWithGenerics() {
		System.out.println("Start test00_AbstractClassWithGenerics");

		Volkswagen<Panasonic,Firestone> volkswagen = new Volkswagen<>();
		Chevrolet<Aiwa,Bridgestone> chevrolet = new Chevrolet<>();
		Ford<Sony,Pirelli> ford = new Ford<>();

		System.out.println("cars: " + volkswagen + ", " + chevrolet + ", " + ford);
	}


	/**
	 * Abstrac classes
	 */

	private static abstract class Car<T1 extends radio,T2 extends tyre> {
		protected T1 radio;
		protected List<T2> tyres;
	}

	private static abstract class radio {
		protected boolean isMp3Capable;
	}

	private static abstract class tyre {
		protected float nominalRadiusInches;
	}


	/**
	 * Classes extending car (each one may have its own additional attributes)
	 */

	private static class Volkswagen<T1 extends radio,T2 extends tyre> extends Car<T1,T2> {
	}

	private static class Chevrolet<T1 extends radio,T2 extends tyre> extends Car<T1,T2> {
	}

	private static class Ford<T1 extends radio,T2 extends tyre> extends Car<T1,T2> {
	}


	/**
	 * Classes extending radio (each one may have its own additional attributes)
	 */

	private static class Panasonic extends radio {
	}

	private static class Aiwa extends radio {
	}

	private static class Sony extends radio {
	}


	/**
	 * Classes extending tyre  (each one may have its own additional attributes)
	 */

	private static class Firestone extends tyre {
	}

	private static class Bridgestone extends tyre {
	}

	private static class Pirelli extends tyre {
	}

}
