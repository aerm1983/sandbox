package localhost.helper;


/**
 * <h1>Helper for Sku Pattern operations.</h1>
 * 
 * <p>Use {@link SkuPatternPojo SkuPatternPojo} constructors: from pattern generate positions, or viceversa.
 * 
 * @author Alberto Romero
 * @since 2024-09-12
 * 
 */
public class SkuPatternHelper {

	public static final String VERSION = "2024-09-12T20:21:00Z";

	public static final String DEFAULT_PATTERN = "%1$s_%2$s_%3$s";

	public static final String SITE_ABBREVIATION_FORMAT_ELEMENT = "%1$s";
	public static final String CONDITION_FORMAT_ELEMENT = "%2$s";
	public static final String SKU_FORMAT_ELEMENT = "%3$s";

	public static final String DELIMITER = "_";

	public static final String[] ACCEPTED_PATTERNS = { 
			// three elements
			"%3$s_%1$s_%2$s", "%3$s_%2$s_%1$s",
			"%2$s_%1$s_%3$s", "%2$s_%3$s_%1$s",
			"%1$s_%2$s_%3$s", "%1$s_%3$s_%2$s", 
			// two elements
			"%3$s_%2$s", "%3$s_%1$s",
			"%2$s_%3$s", 
			"%1$s_%3$s",
			// unique element
			"%3$s"
	};


	/*
	 * TESTS
	 */
	public static void main() {
		System.out.println("Hello from SkuPatternHelper main!");
		testPatternToPositions();
		testPositionsToPattern();
	}

	public static void testPatternToPositions() {
		System.out.println("Hello from testPatternToPositions!");

		SkuPatternPojo spp;
		int i = 0;

		// test00, default pattern, good
		spp = new SkuPatternPojo(DEFAULT_PATTERN);
		System.out.println("test00, spp: " + spp);

		// test01, test all ACCEPTED_PATTERNS
		System.out.println("test01, ACCEPTED_PATTERNS: ");
		for (i = 0; i < ACCEPTED_PATTERNS.length ; i++) {
			spp = new SkuPatternPojo(ACCEPTED_PATTERNS[i]);
			System.out.println("i: " + i + " ; spp: " + spp);
		}

		// test02, non valid patterns
		String[] nonValidPatterns = {
				// two elements, sku not present
				"%1$s_%2$s", "%2$s_%1$s", 
				// unique element, sku not present
				"%1$s", "%2$s",
				// no elements
				"", "_", "___",
				// other invalid patterns
				"a", "aA", "1Aa"
		};
		System.out.println("test02, nonValidPatterns: ");
		for (i = 0; i < nonValidPatterns.length ; i++) {
			spp = new SkuPatternPojo(nonValidPatterns[i]);
			System.out.println("i: " + i + "; patt: " + nonValidPatterns[i] + " ; spp: " + spp);
		}
	}

	public static void testPositionsToPattern() {
		System.out.println("Hello from testPositionsToPattern!");

		SkuPatternPojo spp;

		// VALID POSITION VALUES
		System.out.println("Positions, valid values");

		// testA00, valid positions, sku not null, unique
		spp = new SkuPatternPojo(null,null,1);
		System.out.println("testA00, pons: null,null,1, spp: " + spp);

		// testA01, valid positions, sku not null, two values
		spp = new SkuPatternPojo(null,2,1);
		System.out.println("testA01, pons: null,2,1, spp: " + spp);

		// testA02, valid positions, sku not null, two values
		spp = new SkuPatternPojo(2,null,1);
		System.out.println("testA02, pons: 2,null,1, spp: " + spp);

		// testA03, valid positions, sku not null, two values
		spp = new SkuPatternPojo(1,2,3);
		System.out.println("testA03, pons: 1,2,3, spp: " + spp);



		// NON VALID POSITION VALUES
		System.out.println("Positions, non valid values");

		// testB00, sku null
		spp = new SkuPatternPojo(1,1,null);
		System.out.println("testB00, pons: 1,1,null, spp: " + spp);

		// testB01, sku not null, unique, value different to 1
		spp = new SkuPatternPojo(null,null,2);
		System.out.println("testB01, pons: null,null,2, spp: " + spp);

		// testB02, elements with same value
		spp = new SkuPatternPojo(1,1,1);
		System.out.println("testB02, pons: 1,1,1, spp: " + spp);

		// testB03, elements with same value
		spp = new SkuPatternPojo(1,1,2);
		System.out.println("testB03, pons: 1,1,2, spp: " + spp);

		// testB04, element with value bigger than 3
		spp = new SkuPatternPojo(4,1,2);
		System.out.println("testB03, pons: 4,1,2, spp: " + spp);

	}



	/*
	 * FUNCTIONS
	 */

	public static String customSkuBaseFormat(String pattern, String siteAbbreviation, Integer condition, String sku) {
		return String.format(pattern, siteAbbreviation, condition, sku);
	}



	public static SkuPatternPojo fromPatternToPositions(SkuPatternPojo spp) {
		try {
			spp = patternIsValid(spp);
			if (!spp.valid) {
				return spp;
			}
			boolean processOk = true;
			String[] e = spp.pattern.split(DELIMITER);
			for (int i = 0 ; i < e.length ; i++) {
				if (SITE_ABBREVIATION_FORMAT_ELEMENT.equals(e[i])) {
					spp.siteAbbreviationPosition = i + 1;
				} else if (CONDITION_FORMAT_ELEMENT.equals(e[i])) {
					spp.conditionPosition = i + 1;
				} else if (SKU_FORMAT_ELEMENT.equals(e[i])) {
					spp.skuPosition = i + 1;
				} else {
					processOk = false;
				}
			}
			spp = positionsAreValid(spp);
			if (spp.valid && spp.errorMessage == null && !processOk) {
				spp.valid = false;
				spp.errorMessage = "condition (process fromPatternToPositions was OK), not fulfilled";
			}
			return spp;
		} catch (Throwable e) {
			spp.valid = false;
			spp.errorMessage = "error -- exception: " + e.getMessage();
			return spp;
		}
	}



	public static SkuPatternPojo fromPositionsToPattern(SkuPatternPojo spp) {
		try {
			spp = positionsAreValid(spp);
			if (!spp.valid) return spp;
			String pattern = "";
			int i = 0;
			for (i = 1 ; i < 5 ; i++) {
				if (spp.siteAbbreviationPosition != null && spp.siteAbbreviationPosition == i) {
					if (i == 1) {
						pattern += SITE_ABBREVIATION_FORMAT_ELEMENT;
					} else {
						pattern += DELIMITER + SITE_ABBREVIATION_FORMAT_ELEMENT;
					}
				} else if (spp.conditionPosition != null && spp.conditionPosition == i) {
					if (i == 1) {
						pattern += CONDITION_FORMAT_ELEMENT;
					} else {
						pattern += DELIMITER + CONDITION_FORMAT_ELEMENT;
					}
				} else if (spp.skuPosition != null && spp.skuPosition == i) {
					if (i == 1) {
						pattern += SKU_FORMAT_ELEMENT;
					} else {
						pattern += DELIMITER + SKU_FORMAT_ELEMENT;
					}
				} 
			}
			spp.pattern = pattern;
			spp = patternIsValid(spp);
			return spp;
		} catch (Throwable e) {
			spp.valid = false;
			spp.errorMessage = "error -- exception: " + e.getMessage();
			return spp;
		}
	}



	public static SkuPatternPojo patternIsValid(SkuPatternPojo spp) {
		try {
			if (spp.pattern == null) {
				spp.valid = false;
				spp.errorMessage = "condition (pattern not null), not fulfilled";
				return spp;
			}
			spp.valid = false;
			spp.errorMessage = "condition (pattern valid), not fulfilled";
			for (String p : ACCEPTED_PATTERNS) {
				if (p.equals(spp.pattern)) { 
					spp.valid = true;
					spp.errorMessage = null;
					break;
				}
			}
			return spp;
		} catch (Throwable e) {
			spp.valid = false;
			spp.errorMessage = "error -- exception: " + e.getMessage();
			return spp;
		}
	}



	public static SkuPatternPojo positionsAreValid(SkuPatternPojo spp) {
		try {
			if (spp.skuPosition == null) {
				spp.valid = false;
				spp.errorMessage = "condition (skuPosition != null), not fulfilled";
				return spp;
			}
			if (spp.siteAbbreviationPosition == null && spp.conditionPosition == null && spp.skuPosition != 1) {
				spp.valid = false;
				spp.errorMessage = "condition (skuPosition == 1) if unique present element, not fulfilled";
				return spp;
			}
			if (spp.skuPosition != null && (spp.skuPosition == spp.siteAbbreviationPosition || spp.skuPosition == spp.conditionPosition)) {
				spp.valid = false;
				spp.errorMessage = "condition (different elements must have different positions), not fulfilled";
				return spp;
			}
			if (spp.conditionPosition != null && (spp.conditionPosition == spp.siteAbbreviationPosition)) {
				spp.valid = false;
				spp.errorMessage = "condition (different elements must have different positions), not fulfilled";
				return spp;
			}
			if (spp.skuPosition > 3 || (spp.conditionPosition != null && spp.conditionPosition > 3) || ( spp.siteAbbreviationPosition != null && spp.siteAbbreviationPosition > 3)) {
				spp.valid = false;
				spp.errorMessage = "condition (no element can have a position bigger than 3), not fulfilled";
				return spp;
			}
			spp.valid = true;
			spp.errorMessage = null;
			return spp;
		} catch (Throwable e) {
			spp.valid = false;
			spp.errorMessage = "error -- exception: " + e.getMessage();
			return spp;
		}
	}



	/*
	 * POJOS
	 */


	/**
	 * <p> Use constructors in this class to perform operations: from pattern
	 * generate positions, or viceversa.
	 * 
	 * @author Alberto Romero
	 * @since 2024-09-12
	 * 
	 */
	public static class SkuPatternPojo {
		protected Source source;
		protected String pattern;
		protected Integer siteAbbreviationPosition;
		protected Integer conditionPosition;
		protected Integer skuPosition;
		protected boolean valid = false;
		protected String errorMessage;

		// constructors

		public SkuPatternPojo() {
			super();
		}

		public SkuPatternPojo(Source source, boolean valid, String errorMessage) {
			super();
			this.source = source;
			this.errorMessage = errorMessage;
		}

		public SkuPatternPojo(String pattern) {
			super();
			this.source = Source.PATTERN;
			this.pattern = pattern;
			this.valid = false;
			fromPatternToPositions(this);
		}

		public SkuPatternPojo(Integer siteAbbreviationPosition, Integer conditionPosition, Integer skuPosition) {
			super();
			this.source = Source.POSITIONS;
			this.siteAbbreviationPosition = siteAbbreviationPosition;
			this.conditionPosition = conditionPosition;
			this.skuPosition = skuPosition;
			this.skuPosition = skuPosition;
			this.valid = false;
			fromPositionsToPattern(this);
		}

		// toString()

		public String toString() {
			String out = "{ " + "" + "source: " + source;
			if (pattern != null) {
				out += ", " + "pattern: " + pattern;
			}
			if (siteAbbreviationPosition != null) {
				out += ", " + "siteAbbrnPon: " + siteAbbreviationPosition;
			}
			if (conditionPosition != null) {
				out += ", " + "conditionPon: " + conditionPosition;
			}
			if (skuPosition != null) {
				out += ", " + "skuPon: " + skuPosition;
			}
			out += ", " + "valid: " + valid;
			if (errorMessage != null) {
				out += ", " + "errorMessage: " + errorMessage;
			}
			out += " }";
			return out;
		}

		// getters, setters

		public Source getSource() {
			return source;
		}

		public void setSource(Source source) {
			this.source = source;
		}

		public String getPattern() {
			return pattern;
		}

		public void setPattern(String pattern) {
			this.pattern = pattern;
		}

		public Integer getSiteAbbreviationPosition() {
			return siteAbbreviationPosition;
		}

		public void setSiteAbbreviationPosition(Integer siteAbbreviationPosition) {
			this.siteAbbreviationPosition = siteAbbreviationPosition;
		}

		public Integer getConditionPosition() {
			return conditionPosition;
		}

		public void setConditionPosition(Integer conditionPosition) {
			this.conditionPosition = conditionPosition;
		}

		public Integer getSkuPosition() {
			return skuPosition;
		}

		public void setSkuPosition(Integer skuPosition) {
			this.skuPosition = skuPosition;
		}

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

	}



	public static enum Source {
		PATTERN,
		POSITIONS
	}



	public static class SkuPatternExtendedPojo extends SkuPatternPojo {

		private String version = VERSION;

		// constructors

		public SkuPatternExtendedPojo (SkuPatternPojo spp) {
			super();
			this.source = spp.source;
			this.pattern = spp.pattern;
			this.siteAbbreviationPosition = spp.siteAbbreviationPosition;
			this.conditionPosition = spp.conditionPosition;
			this.skuPosition = spp.skuPosition;
			this.skuPosition = spp.skuPosition;
			this.valid = spp.valid;
		}

		public SkuPatternExtendedPojo () {
			super();
		}

		// getters, setters

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

	}

}