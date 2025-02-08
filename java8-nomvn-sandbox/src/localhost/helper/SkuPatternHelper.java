package localhost.helper;


/**
 * <p>Helper for Sku Pattern operations.
 * 
 * <p>This class is substituted by {@link CustomSkuHelper}.
 * 
 * <p>Use {@link SkuPatternHelperPojo SkuPatternHelperPojo} constructors: from pattern generate positions, or viceversa.
 * 
 * @author Alberto Romero
 * @since 2024-09-12
 * 
 */
@Deprecated
public class SkuPatternHelper {

	public static final String VERSION = "2024-09-15T19:12:00Z";

	public static final String DEFAULT_PATTERN = "%1$s_%2$s_%3$s";

	public static final String SITE_ABBREVIATION_FORMAT_ELEMENT = "%1$s";
	public static final String CONDITION_ID_FORMAT_ELEMENT = "%2$s";
	public static final String SKU_FORMAT_ELEMENT = "%3$s";

	public static final String DELIMITER = "_";

	public static final String[] ACCEPTED_PATTERNS = {
			// (sku "%3$s" must always be present)
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
		testValidValues();
		testNonValidValues();
	}

	private static void testValidValues() {
		System.out.println("Hello from testValidValues!");

		SkuPatternHelperPojo sphpSrcPatt;
		SkuPatternHelperPojo sphpSrcPosn;
		int i = 0;

		// test00, test DEFAULT_PATTERN
		System.out.println("test00, DEFAULT_PATTERN: ");
		sphpSrcPatt = new SkuPatternHelperPojo(DEFAULT_PATTERN);
		System.out.println("sphpSrcPatt: " + sphpSrcPatt);
		sphpSrcPosn = new SkuPatternHelperPojo(sphpSrcPatt.siteAbbreviationPosition, sphpSrcPatt.conditionIdPosition, sphpSrcPatt.skuPosition);
		System.out.println("sphpSrcPosn: " + sphpSrcPosn);

		// test01, test all ACCEPTED_PATTERNS
		System.out.println("test01, ACCEPTED_PATTERNS: ");
		for (i = 0; i < ACCEPTED_PATTERNS.length ; i++) {
			sphpSrcPatt = new SkuPatternHelperPojo(ACCEPTED_PATTERNS[i]);
			System.out.println("i: " + i + " ; sphpSrcPatt: " + sphpSrcPatt);
			sphpSrcPosn = new SkuPatternHelperPojo(sphpSrcPatt.siteAbbreviationPosition, sphpSrcPatt.conditionIdPosition, sphpSrcPatt.skuPosition);
			System.out.println("i: " + i + " ; sphpSrcPosn: " + sphpSrcPosn);
		}
	}

	private static void testNonValidValues() {
		System.out.println("Hello from testNonValidValues!");

		SkuPatternHelperPojo sphp;
		int i = 0;

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
		System.out.println("test, non valid patterns: ");
		for (i = 0; i < nonValidPatterns.length ; i++) {
			sphp = new SkuPatternHelperPojo(nonValidPatterns[i]);
			System.out.println("i: " + i + "; patt: " + nonValidPatterns[i] + " ; sphp: " + sphp);
		}


		// NON VALID POSITION VALUES
		System.out.println("test, non valid positions");

		// testB00, sku null
		sphp = new SkuPatternHelperPojo(1,1,null);
		System.out.println("testB00, pons: 1,1,null, sphp: " + sphp);

		// testB01_a, sku not null, unique, value different to 1
		sphp = new SkuPatternHelperPojo(null,null,0);
		System.out.println("testB01_a, pons: null,null,0, sphp: " + sphp);

		// testB01_b, sku not null, unique, value different to 1
		sphp = new SkuPatternHelperPojo(null,null,2);
		System.out.println("testB01_b, pons: null,null,2, sphp: " + sphp);

		// testB01_c, sku not null, unique, value different to 1
		sphp = new SkuPatternHelperPojo(null,null,4);
		System.out.println("testB01_c, pons: null,null,4, sphp: " + sphp);

		// testB02, elements with same value
		sphp = new SkuPatternHelperPojo(1,1,1);
		System.out.println("testB02, pons: 1,1,1, sphp: " + sphp);

		// testB03, elements with same value
		sphp = new SkuPatternHelperPojo(1,1,2);
		System.out.println("testB03, pons: 1,1,2, sphp: " + sphp);

		// testB04, element with value bigger than 3
		sphp = new SkuPatternHelperPojo(4,1,2);
		System.out.println("testB03, pons: 4,1,2, sphp: " + sphp);

	}



	/*
	 * FUNCTIONS
	 */

	public static String customSkuBaseFormat(String pattern, String siteAbbreviation, Integer conditionId, String sku) {
		return String.format(pattern, siteAbbreviation, conditionId, sku);
	}



	private static SkuPatternHelperPojo fromPatternToPositions(SkuPatternHelperPojo sphp) {
		try {
			sphp = patternIsValid(sphp);
			if (!sphp.valid) {
				return sphp;
			}
			boolean processOk = true;
			String[] e = sphp.pattern.split(DELIMITER);
			for (int i = 0 ; i < e.length ; i++) {
				if (SITE_ABBREVIATION_FORMAT_ELEMENT.equals(e[i])) {
					sphp.siteAbbreviationPosition = i + 1;
				} else if (CONDITION_ID_FORMAT_ELEMENT.equals(e[i])) {
					sphp.conditionIdPosition = i + 1;
				} else if (SKU_FORMAT_ELEMENT.equals(e[i])) {
					sphp.skuPosition = i + 1;
				} else {
					processOk = false;
				}
			}
			sphp = positionsAreValid(sphp);
			if (sphp.valid && sphp.errorMessage == null && !processOk) {
				sphp.valid = false;
				sphp.errorMessage = "condition (process fromPatternToPositions was OK), not fulfilled";
			}
			return sphp;
		} catch (Throwable e) {
			sphp.valid = false;
			sphp.errorMessage = "error -- exception: " + e.getMessage();
			return sphp;
		}
	}



	private static SkuPatternHelperPojo fromPositionsToPattern(SkuPatternHelperPojo sphp) {
		try {
			sphp = positionsAreValid(sphp);
			if (!sphp.valid) return sphp;
			String pattern = "";
			int i = 0;
			for (i = 1 ; i < 5 ; i++) {
				if (sphp.siteAbbreviationPosition != null && sphp.siteAbbreviationPosition == i) {
					if (i == 1) {
						pattern += SITE_ABBREVIATION_FORMAT_ELEMENT;
					} else {
						pattern += DELIMITER + SITE_ABBREVIATION_FORMAT_ELEMENT;
					}
				} else if (sphp.conditionIdPosition != null && sphp.conditionIdPosition == i) {
					if (i == 1) {
						pattern += CONDITION_ID_FORMAT_ELEMENT;
					} else {
						pattern += DELIMITER + CONDITION_ID_FORMAT_ELEMENT;
					}
				} else if (sphp.skuPosition != null && sphp.skuPosition == i) {
					if (i == 1) {
						pattern += SKU_FORMAT_ELEMENT;
					} else {
						pattern += DELIMITER + SKU_FORMAT_ELEMENT;
					}
				} 
			}
			sphp.pattern = pattern;
			sphp = patternIsValid(sphp);
			return sphp;
		} catch (Throwable e) {
			sphp.valid = false;
			sphp.errorMessage = "error -- exception: " + e.getMessage();
			return sphp;
		}
	}



	private static SkuPatternHelperPojo patternIsValid(SkuPatternHelperPojo sphp) {
		try {
			if (sphp.pattern == null) {
				sphp.valid = false;
				sphp.errorMessage = "condition (pattern not null), not fulfilled";
				return sphp;
			}
			sphp.valid = false;
			sphp.errorMessage = "condition (pattern valid), not fulfilled";
			for (String p : ACCEPTED_PATTERNS) {
				if (p.equals(sphp.pattern)) { 
					sphp.valid = true;
					sphp.errorMessage = null;
					break;
				}
			}
			return sphp;
		} catch (Throwable e) {
			sphp.valid = false;
			sphp.errorMessage = "error -- exception: " + e.getMessage();
			return sphp;
		}
	}



	private static SkuPatternHelperPojo positionsAreValid(SkuPatternHelperPojo sphp) {
		try {
			// skuPosition must always be present
			if (sphp.skuPosition == null) {
				sphp.valid = false;
				sphp.errorMessage = "condition (skuPosition != null), not fulfilled";
				return sphp;
			}
			if (sphp.skuPosition < 1 || sphp.skuPosition > 3 ) {
				sphp.valid = false;
				sphp.errorMessage = "condition (skuPosition not less than 1 nor bigger than 3), not fulfilled";
				return sphp;
			}
			// all three elements present
			if (sphp.siteAbbreviationPosition != null && sphp.conditionIdPosition != null) {
				if (sphp.siteAbbreviationPosition < 1 || sphp.siteAbbreviationPosition > 3 ) {
					sphp.valid = false;
					sphp.errorMessage = "condition (siteAbbreviationPosition not less than 1 nor bigger than 3), not fulfilled";
					return sphp;
				}
				if (sphp.conditionIdPosition < 1 || sphp.conditionIdPosition > 3 ) {
					sphp.valid = false;
					sphp.errorMessage = "condition (conditionPosition not less than 1 nor bigger than 3), not fulfilled";
					return sphp;
				}
				if (sphp.skuPosition == sphp.siteAbbreviationPosition || sphp.skuPosition == sphp.conditionIdPosition || sphp.siteAbbreviationPosition == sphp.conditionIdPosition) {
					sphp.valid = false;
					sphp.errorMessage = "condition (different elements must have different positions), not fulfilled";
					return sphp;
				}
				if (sphp.skuPosition + sphp.conditionIdPosition + sphp.siteAbbreviationPosition != 6) {
					sphp.valid = false;
					sphp.errorMessage = "condition (sumOfPositions == 6), not fulfilled";
					return sphp;
				}
			}
			// two elements present: sku and siteAbbreviation
			if (sphp.siteAbbreviationPosition != null && sphp.conditionIdPosition == null) {
				if (sphp.siteAbbreviationPosition < 1 || sphp.siteAbbreviationPosition > 3 ) {
					sphp.valid = false;
					sphp.errorMessage = "condition (siteAbbreviationPosition not less than 1 nor bigger than 3), not fulfilled";
					return sphp;
				}
				if (sphp.skuPosition == sphp.siteAbbreviationPosition) {
					sphp.valid = false;
					sphp.errorMessage = "condition (different elements must have different positions), not fulfilled";
					return sphp;
				}
				if (sphp.skuPosition + sphp.siteAbbreviationPosition != 3) {
					sphp.valid = false;
					sphp.errorMessage = "condition (sumOfPositions == 3), not fulfilled";
					return sphp;
				}
			}
			// two elements present: sku and conditionId
			if (sphp.conditionIdPosition != null && sphp.siteAbbreviationPosition == null) {
				if (sphp.conditionIdPosition < 1 || sphp.conditionIdPosition > 3 ) {
					sphp.valid = false;
					sphp.errorMessage = "condition (conditionIdPosition not less than 1 nor bigger than 3), not fulfilled";
					return sphp;
				}
				if (sphp.skuPosition == sphp.conditionIdPosition) {
					sphp.valid = false;
					sphp.errorMessage = "condition (different elements must have different positions), not fulfilled";
					return sphp;
				}
				if (sphp.skuPosition + sphp.conditionIdPosition != 3) {
					sphp.valid = false;
					sphp.errorMessage = "condition (sumOfPositions == 3), not fulfilled";
					return sphp;
				}
			}
			// unique element, sku
			if (sphp.siteAbbreviationPosition == null && sphp.conditionIdPosition == null) {
				if (sphp.skuPosition != 1) {
					sphp.valid = false;
					sphp.errorMessage = "condition (skuPosition == 1) if unique present element, not fulfilled";
					return sphp;
				}
			}
			sphp.valid = true;
			sphp.errorMessage = null;
			return sphp;
		} catch (Throwable e) {
			sphp.valid = false;
			sphp.errorMessage = "error -- exception: " + e.getMessage();
			return sphp;
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
	public static class SkuPatternHelperPojo {
		protected Source source;
		protected String pattern;
		protected Integer siteAbbreviationPosition;
		protected Integer conditionIdPosition;
		protected Integer skuPosition;
		protected boolean valid = false;
		protected String errorMessage;

		// constructors

		public SkuPatternHelperPojo() {
			super();
		}

		public SkuPatternHelperPojo(Source source, boolean valid, String errorMessage) {
			super();
			this.source = source;
			this.errorMessage = errorMessage;
		}

		public SkuPatternHelperPojo(String pattern) {
			super();
			this.source = Source.PATTERN;
			this.pattern = pattern;
			this.valid = false;
			fromPatternToPositions(this);
		}

		public SkuPatternHelperPojo(Integer siteAbbreviationPosition, Integer conditionIdPosition, Integer skuPosition) {
			super();
			this.source = Source.POSITIONS;
			this.siteAbbreviationPosition = siteAbbreviationPosition;
			this.conditionIdPosition = conditionIdPosition;
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
			if (conditionIdPosition != null) {
				out += ", " + "condIdPon: " + conditionIdPosition;
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

		public Integer getConditionIdPosition() {
			return conditionIdPosition;
		}

		public void setConditionIdPosition(Integer conditionIdPosition) {
			this.conditionIdPosition = conditionIdPosition;
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



	public static class SkuPatternHelperExtendedPojo extends SkuPatternHelperPojo {

		private String version = VERSION;

		// constructors

		public SkuPatternHelperExtendedPojo (SkuPatternHelperPojo spp) {
			super();
			this.source = spp.source;
			this.pattern = spp.pattern;
			this.siteAbbreviationPosition = spp.siteAbbreviationPosition;
			this.conditionIdPosition = spp.conditionIdPosition;
			this.skuPosition = spp.skuPosition;
			this.skuPosition = spp.skuPosition;
			this.valid = spp.valid;
		}

		public SkuPatternHelperExtendedPojo () {
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