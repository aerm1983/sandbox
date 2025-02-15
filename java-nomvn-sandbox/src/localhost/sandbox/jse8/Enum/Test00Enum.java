package localhost.sandbox.jse8.Enum;

public class Test00Enum {

	static public void test00_SimplestEnum() {
		System.out.println("Start test00_SimplestEnum");

		Animal cat = Animal.valueOf("CAT");
		System.out.println("cat: " + cat + " ; cat.name(): " + cat.name() + " ; cat.ordinal(): " + cat.ordinal());

		Animal dog = Enum.valueOf(Animal.class, "DOG");
		System.out.println("dog: " + dog + " ; dog.name(): " + dog.name() + " ; dog.ordinal(): " + dog.ordinal());

		try {
			Animal fish = Enum.valueOf(Animal.class, "_fish_");
			System.out.println("fish: " + fish + " ; fish.name(): " + fish.name() + " ; fish.ordinal(): " + fish.ordinal());

		} catch (Throwable ex) {
			System.err.println("error -- ex.getClass(): " + ex.getClass() + " -- ex.getMessage(): " + ex.getMessage());
		}

		Animal bird = Animal.valueOf(Animal.class, "BIRD");
		System.out.println("bird: " + bird + " ; bird.name(): " + bird.name() + " ; bird.ordinal(): " + bird.ordinal());

		System.out.println("Animal.values(): " + Animal.values());
		for (Animal a : Animal.values()) {
			System.out.println("animal: " + a);
		}
	}



	static public void test01_SimpleEnumInsensitive() {
		System.out.println("Start test01_SimpleEnumInsensitive");

		AnimalIns cat = AnimalIns.fromString("CAT");
		System.out.println("cat: " + cat + " ; cat.name(): " + cat.name() + " ; cat.ordinal(): " + cat.ordinal());

		AnimalIns dog = AnimalIns.fromString("dog");
		System.out.println("dog: " + dog + " ; dog.name(): " + dog.name() + " ; dog.ordinal(): " + dog.ordinal());

		try {
			AnimalIns fish = AnimalIns.fromString("_fish_");
			System.out.println("fish: " + fish + " ; fish.name(): " + fish.name() + " ; fish.ordinal(): " + fish.ordinal());
		} catch (Throwable ex) {
			System.err.println("error -- ex.getClass(): " + ex.getClass() + " -- ex.getMessage(): " + ex.getMessage());
		}

		AnimalIns bird = AnimalIns.fromString("Ird");
		System.out.println("bird: " + bird + " ; bird.name(): " + bird.name() + " ; bird.ordinal(): " + bird.ordinal());

		System.out.println("AnimalIns.values(): " + AnimalIns.values());
		for (AnimalIns ai : AnimalIns.values()) {
			System.out.println("animalIns: " + ai);
		}
	}



	static public void test02_EnumWithSeveralAttributes() {
		System.out.println("Start test02_EnumWithSeveralAttributes");

		CarBrand ford = CarBrand.fromString("ford");
		System.out.println("ford: " + ford);

		CarBrand chevy = CarBrand.valueOf("CHEVY");
		System.out.println("chevy" + chevy);
	}



	/*
	 * Enums
	 */

	private enum Animal {
		CAT,
		DOG,
		FISH,
		BIRD;
	}



	private enum AnimalIns {
		CAT,
		DOG,
		FISH,
		BIRD;

		/**
		 * <p> Method {@link java.lang.Enum#valueOf(String)} cannot be overridden.
		 */
		public static AnimalIns fromString(String input) {
			for (AnimalIns ai : AnimalIns.values()) {
				if (ai.name().matches("(?i)^.*" + input.toLowerCase() + ".*$")) {
					return ai;
				}
			}
			return null;
		}
	}



	/**
	 * <p> Attributes 'name' and 'ordinal' are incorporated, 
	 * even if not declared explicitly.
	 */
	private enum CarBrand {

		FORD("FORD", 2001, false),
		CHEVY("CHEVY", 2009, false),
		AUDI("AUDI", 2016, true);

		private String brand;

		private Integer year;

		private Boolean superCar;

		CarBrand(String brand, Integer year, Boolean superCar) {
			this.brand = brand;
			this.year = year;
			this.superCar = superCar;
		}

		String brand() {
			return this.brand;
		}

		Integer year() {
			return this.year;
		}

		Boolean superCar() {
			return this.superCar;
		}

		@Override
		public String toString() {
			String out = "{ "
					+ "" + "brand: " + this.brand
					+ ", " + "year: " + this.year
					+ ", " + "superCar: " + this.superCar
					+ " }" ;
			return out;

		}

		/**
		 * <p> Method {@link java.lang.Enum#valueOf(String)} cannot be overridden.
		 */
		public static CarBrand fromString(String input) {
			for (CarBrand cb : CarBrand.values()) {
				if ( cb.brand.matches("(?i)^.*" + input + ".*$") || cb.year.toString().matches("(?i)^.*" + input + ".*$") ) {
					return cb;
				}
			}
			return null;
		}
	}

}
