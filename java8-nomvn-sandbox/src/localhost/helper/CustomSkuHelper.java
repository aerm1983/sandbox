package localhost.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>Helper for CustomSku operations</h1>
 * 
 * <p>This implementation is substitute to {@link SkuPatternHelper}.
 * 
 * <p>Available helping inner classes:
 * <ul>
 * <li>{@link CustomSkuPatternValidationHelper} (pattern validation)
 * <li>{@link CustomSkuProcessorHelper} (customSku generation, reversal) 
 * </ul>
 * 
 * <p>Delimiter character is '<b>_</b>' (underscore, <b>final constant</b>).
 * 
 * <p>See {@link CustomSkuProcessorHelper} for guidance regarding specific 
 * implementation into marketplaces.
 * 
 * <p>See {@link Component} for restrictions concerning acceptable values, 
 * regarding {@link CustomSkuHelper#DELIMITER DELIMITER}.
 * 
 * @author Alberto Romero
 * @since 2025-01-22
 * 
 */
public class CustomSkuHelper {

	protected static final String VERSION = "2025-01-24T12:00:00Z";

	/**
	 * <p>Hard-coded, never-changing, underscore character.
	 */
	protected static final String DELIMITER = "_";

	/**
	 * <p>Components (fields) integrating customSku.
	 * 
	 * <p><b>Important:</b> {@link Component#SKU SKU} is the <b>only</b> 
	 * component allowed to contain {@link CustomSkuHelper#DELIMITER DELIMITER} 
	 * character.
	 * 
	 * <p>See also: 
	 * <ul>
	 * <li>{@link Component#SITE_ABBREVIATION} 
	 * <li>{@link Component#CONDITION_ID}
	 * <li>{@link Component#SKU}
	 * </ul>
	 * 
	 * @author Alberto Romero
	 * @since 2025-01-22
	 */
	protected static enum Component {

		/**
		 * <p>Cannot contain {@link CustomSkuHelper#DELIMITER DELIMITER} character.  
		 * Examples: "ES", "IT", "FR".
		 */
		SITE_ABBREVIATION("{siteAbbreviation}"),

		/**
		 * <p>Cannot contain {@link CustomSkuHelper#DELIMITER DELIMITER} character.  
		 * Examples: "1000", "1200".
		 */
		CONDITION_ID("{conditionId}"),

		/**
		 * <p>May contain {@link CustomSkuHelper#DELIMITER DELIMITER} character.  
		 * Examples: "ABC", "A_B_C".
		 */
		SKU("{sku}");

		private String value;
		private String regexPatternPlaceholder;
		private String regexCustomSkuPlaceholder;

		private Component (String value) {
			this.value = value;
			this.regexPatternPlaceholder = value.replaceAll("\\{", "\\\\{").replaceAll("\\}", "\\\\}"); // first param: regex (curly bracket bears especial meaning); second param: simple string, which later will be used as regex
			// this.mayContainDelimiter = mayContainDelimiter;
			if (!"SKU".equals(this.name())) {
				this.regexCustomSkuPlaceholder = "[^_]+";
			} else {
				this.regexCustomSkuPlaceholder = ".+";
			}
		}

		public Component fromValue(String inValue) {
			if (inValue == null) {
				return null;
			}
			for (Component c : Component.values()) {
				if (c.value.equals(inValue)) {
					return c;
				}
			}
			return null;
		}

		public String toString() {
			return this.value;
		}

	}

	// protected static final String EXAMPLE_DEFAULT_PATTERN = Component.SITE_ABBREVIATION + DELIMITER + Component.CONDITION_ID + DELIMITER + Component.SKU;

	protected static final String[] ACCEPTED_PATTERNS = {
			// (sku must always be present)
			// three elements
			Component.SKU + DELIMITER + Component.SITE_ABBREVIATION + DELIMITER + Component.CONDITION_ID,
			Component.SKU + DELIMITER + Component.CONDITION_ID + DELIMITER + Component.SITE_ABBREVIATION,
			Component.CONDITION_ID + DELIMITER + Component.SITE_ABBREVIATION + DELIMITER + Component.SKU,
			Component.CONDITION_ID + DELIMITER + Component.SKU + DELIMITER + Component.SITE_ABBREVIATION,
			Component.SITE_ABBREVIATION + DELIMITER + Component.CONDITION_ID + DELIMITER + Component.SKU,
			Component.SITE_ABBREVIATION + DELIMITER + Component.SKU + DELIMITER + Component.CONDITION_ID,
			// two elements
			Component.SKU + DELIMITER + Component.CONDITION_ID,
			Component.SKU + DELIMITER + Component.SITE_ABBREVIATION,
			Component.CONDITION_ID + DELIMITER + Component.SKU,
			Component.SITE_ABBREVIATION + DELIMITER + Component.SKU,
			// unique element
			Component.SKU.toString()
	};


	/*
	 * TESTS
	 */
	public static void main() {
		System.out.println("Hello from SkuPatternHelper main!");
		/*
		 * (Easier to run one testing-method at a time)
		 */
		// testValidationGoodInputs();
		// testValidationBadInputs();
		testGenerateReverseGoodInputs();
	}

	private static void testValidationGoodInputs() {
		System.out.println("Hello from testValidationGoodInputs!");

		CustomSkuPatternValidationHelper cspvhSrcPatt;
		CustomSkuPatternValidationHelper cspvhSrcPosn;
		int i = 0;

		// test00, test all ACCEPTED_PATTERNS
		System.out.println("test01, ACCEPTED_PATTERNS: ");
		for (i = 0; i < ACCEPTED_PATTERNS.length ; i++) {
			cspvhSrcPatt = new CustomSkuPatternValidationHelper(ACCEPTED_PATTERNS[i]);
			System.out.println("i: " + i + " ; cspvhSrcPatt: " + cspvhSrcPatt);
			cspvhSrcPosn = new CustomSkuPatternValidationHelper(cspvhSrcPatt.siteAbbreviationPosition, cspvhSrcPatt.conditionIdPosition, cspvhSrcPatt.skuPosition);
			System.out.println("i: " + i + " ; cspvhSrcPosn: " + cspvhSrcPosn);
		}
	}

	private static void testValidationBadInputs() {
		System.out.println("Hello from testValidationBadInputs!");

		CustomSkuPatternValidationHelper cspvh;
		int i = 0;

		// test02, non valid patterns
		String[] nonValidPatterns = {
				// two elements, sku not present
				"{siteAbbreviation}_{conditionId}", "{conditionId}_{siteAbbreviation}", 
				// unique element, sku not present
				"{siteAbbreviation}", "{conditionId}",
				// no elements
				"", "_", "___",
				// other invalid patterns
				"a", "aA", "1Aa"
		};
		System.out.println("test, non valid patterns: ");
		for (i = 0; i < nonValidPatterns.length ; i++) {
			cspvh = new CustomSkuPatternValidationHelper(nonValidPatterns[i]);
			System.out.println("i: " + i + "; patt: " + nonValidPatterns[i] + " ; cspvh: " + cspvh);
		}


		// NON VALID POSITION VALUES
		System.out.println("test, non valid positions");

		// testB00, sku null
		cspvh = new CustomSkuPatternValidationHelper(1,1,null);
		System.out.println("testB00, pons: 1,1,null, cspvh: " + cspvh);

		// testB01_a, sku not null, unique, value different to 1
		cspvh = new CustomSkuPatternValidationHelper(null,null,0);
		System.out.println("testB01_a, pons: null,null,0, cspvh: " + cspvh);

		// testB01_b, sku not null, unique, value different to 1
		cspvh = new CustomSkuPatternValidationHelper(null,null,2);
		System.out.println("testB01_b, pons: null,null,2, cspvh: " + cspvh);

		// testB01_c, sku not null, unique, value different to 1
		cspvh = new CustomSkuPatternValidationHelper(null,null,4);
		System.out.println("testB01_c, pons: null,null,4, cspvh: " + cspvh);

		// testB02, elements with same value
		cspvh = new CustomSkuPatternValidationHelper(1,1,1);
		System.out.println("testB02, pons: 1,1,1, cspvh: " + cspvh);

		// testB03, elements with same value
		cspvh = new CustomSkuPatternValidationHelper(1,1,2);
		System.out.println("testB03, pons: 1,1,2, cspvh: " + cspvh);

		// testB04, element with value bigger than 3
		cspvh = new CustomSkuPatternValidationHelper(4,1,2);
		System.out.println("testB03, pons: 4,1,2, cspvh: " + cspvh);

	}

	private static void testGenerateReverseGoodInputs() {
		System.out.println("Hello from testGenerateReverseGoodInputs!");

		String pattern = null;
		String siteAbbreviation = "ES";
		String conditionId = "1000";
		String sku = "ABCXYZ";
		CustomSkuProcessorHelper csph = null;
		String customSku = null;
		ReversedCustomSkuPojo reversedCS = null;
		String customSkuAgain = null;

		System.out.println("- - - - - - - - - - - - - - - -");
		System.out.println("ACCEPTED_PATTERNS:");
		int i = 0;
		for (i = 0; i < ACCEPTED_PATTERNS.length ; i++) {
			pattern = ACCEPTED_PATTERNS[i];
			try {
				csph = new CustomSkuProcessorHelper(pattern);
			} catch (Throwable ex) {
				System.err.println("exception: " +  ex);
			}
			customSku = csph.generate(siteAbbreviation, conditionId, sku);
			reversedCS = csph.reverse(customSku);
			customSkuAgain = csph.generate(reversedCS.getSiteAbbreviation(), reversedCS.getConditionId(), reversedCS.getSku());

			System.out.println("i: " + i + " ; dbPattern: " + pattern +  " ; sku: " + sku + " ; customSku: " + customSku + " ; reversedCS: " + reversedCS + " ; customSkuAgain: " + customSkuAgain);
		}

		System.out.println("- - - - - - - - - - - - - - - -");
		System.out.println("SKU containing DELIMITER character:");
		sku = "A_B_C";
		for (i = 0; i < ACCEPTED_PATTERNS.length ; i++) {
			pattern = ACCEPTED_PATTERNS[i];
			try {
				csph = new CustomSkuProcessorHelper(pattern);
			} catch (Throwable ex) {
				System.err.println("exception: " +  ex);
			}
			customSku = csph.generate(siteAbbreviation, conditionId, sku);
			reversedCS = csph.reverse(customSku);
			customSkuAgain = csph.generate(reversedCS.getSiteAbbreviation(), reversedCS.getConditionId(), reversedCS.getSku());

			System.out.println("i: " + i + " ; dbPattern: " + pattern +  " ; sku: " + sku + " ; customSku: " + customSku + " ; reversedCS: " + reversedCS + " ; customSkuAgain: " + customSkuAgain);
		}

		System.out.println("- - - - - - - - - - - - - - - -");
		System.out.println("SKU containing and surrounded by DELIMITER character:");
		sku = "_A_B_C_";
		for (i = 0; i < ACCEPTED_PATTERNS.length ; i++) {
			pattern = ACCEPTED_PATTERNS[i];
			try {
				csph = new CustomSkuProcessorHelper(pattern);
			} catch (Throwable ex) {
				System.err.println("exception: " +  ex);
			}
			customSku = csph.generate(siteAbbreviation, conditionId, sku);
			reversedCS = csph.reverse(customSku);
			customSkuAgain = csph.generate(reversedCS.getSiteAbbreviation(), reversedCS.getConditionId(), reversedCS.getSku());

			System.out.println("i: " + i + " ; dbPattern: " + pattern +  " ; sku: " + sku + " ; customSku: " + customSku + " ; reversedCS: " + reversedCS + " ; customSkuAgain: " + customSkuAgain);
		}

	}




	/**
	 * <p> Class for CustomSku <b>validation</b> purposes.
	 * 
	 * <p> Use constructors to perform operations:
	 * <ul> 
	 * <li>from pattern generate positions 
	 * <li>viceversa
	 * </ul>
	 * 
	 * @author Alberto Romero
	 * @since 2025-01-20
	 * 
	 */
	public static class CustomSkuPatternValidationHelper {
		protected ValidationSource source;
		protected String pattern;
		protected Integer siteAbbreviationPosition;
		protected Integer conditionIdPosition;
		protected Integer skuPosition;
		protected boolean valid = false;
		protected String errorMessage;

		// constructors

		public CustomSkuPatternValidationHelper() {
			super();
		}

		public CustomSkuPatternValidationHelper(ValidationSource source, boolean valid, String errorMessage) {
			super();
			this.source = source;
			this.errorMessage = errorMessage;
		}

		public CustomSkuPatternValidationHelper(String pattern) {
			super();
			this.source = ValidationSource.PATTERN;
			this.pattern = pattern;
			this.valid = false;
			fromPatternToPositions(this);
		}

		public CustomSkuPatternValidationHelper(Integer siteAbbreviationPosition, Integer conditionIdPosition, Integer skuPosition) {
			super();
			this.source = ValidationSource.POSITIONS;
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


		/*
		 * Getters, setters
		 */

		public ValidationSource getSource() {
			return source;
		}

		public void setSource(ValidationSource source) {
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


		/*
		 * Validation Functions
		 */


		private static CustomSkuPatternValidationHelper fromPatternToPositions(CustomSkuPatternValidationHelper csphp) {
			try {
				csphp = patternIsValid(csphp);
				if (!csphp.valid) {
					return csphp;
				}
				boolean processOk = true;
				String[] e = csphp.pattern.split(DELIMITER);
				for (int i = 0 ; i < e.length ; i++) {
					if (Component.SITE_ABBREVIATION.toString().equals(e[i])) {
						csphp.siteAbbreviationPosition = i + 1;
					} else if (Component.CONDITION_ID.toString().equals(e[i])) {
						csphp.conditionIdPosition = i + 1;
					} else if (Component.SKU.toString().equals(e[i])) {
						csphp.skuPosition = i + 1;
					} else {
						processOk = false;
					}
				}
				csphp = positionsAreValid(csphp);
				if (csphp.valid && csphp.errorMessage == null && !processOk) {
					csphp.valid = false;
					csphp.errorMessage = "condition (process fromPatternToPositions was OK), not fulfilled";
				}
				return csphp;
			} catch (Throwable e) {
				csphp.valid = false;
				csphp.errorMessage = "error -- exception: " + e.getMessage();
				return csphp;
			}
		}



		private static CustomSkuPatternValidationHelper fromPositionsToPattern(CustomSkuPatternValidationHelper csphp) {
			try {
				csphp = positionsAreValid(csphp);
				if (!csphp.valid) return csphp;
				String pattern = "";
				int i = 0;
				for (i = 1 ; i < 5 ; i++) {
					if (csphp.siteAbbreviationPosition != null && csphp.siteAbbreviationPosition == i) {
						if (i == 1) {
							pattern += Component.SITE_ABBREVIATION;
						} else {
							pattern += DELIMITER + Component.SITE_ABBREVIATION;
						}
					} else if (csphp.conditionIdPosition != null && csphp.conditionIdPosition == i) {
						if (i == 1) {
							pattern += Component.CONDITION_ID;
						} else {
							pattern += DELIMITER + Component.CONDITION_ID;
						}
					} else if (csphp.skuPosition != null && csphp.skuPosition == i) {
						if (i == 1) {
							pattern += Component.SKU;
						} else {
							pattern += DELIMITER + Component.SKU;
						}
					} 
				}
				csphp.pattern = pattern;
				csphp = patternIsValid(csphp);
				return csphp;
			} catch (Throwable e) {
				csphp.valid = false;
				csphp.errorMessage = "error -- exception: " + e.getMessage();
				return csphp;
			}
		}



		private static CustomSkuPatternValidationHelper patternIsValid(CustomSkuPatternValidationHelper csphp) {
			try {
				if (csphp.pattern == null) {
					csphp.valid = false;
					csphp.errorMessage = "condition (pattern not null), not fulfilled";
					return csphp;
				}
				csphp.valid = false;
				csphp.errorMessage = "condition (pattern valid), not fulfilled";
				for (String p : ACCEPTED_PATTERNS) {
					if (p.equals(csphp.pattern)) { 
						csphp.valid = true;
						csphp.errorMessage = null;
						break;
					}
				}
				return csphp;
			} catch (Throwable e) {
				csphp.valid = false;
				csphp.errorMessage = "error -- exception: " + e.getMessage();
				return csphp;
			}
		}



		private static CustomSkuPatternValidationHelper positionsAreValid(CustomSkuPatternValidationHelper cspvh) {
			try {
				// skuPosition must always be present
				if (cspvh.skuPosition == null) {
					cspvh.valid = false;
					cspvh.errorMessage = "condition (skuPosition != null), not fulfilled";
					return cspvh;
				}
				if (cspvh.skuPosition < 1 || cspvh.skuPosition > 3 ) {
					cspvh.valid = false;
					cspvh.errorMessage = "condition (skuPosition not less than 1 nor bigger than 3), not fulfilled";
					return cspvh;
				}
				// all three elements present
				if (cspvh.siteAbbreviationPosition != null && cspvh.conditionIdPosition != null) {
					if (cspvh.siteAbbreviationPosition < 1 || cspvh.siteAbbreviationPosition > 3 ) {
						cspvh.valid = false;
						cspvh.errorMessage = "condition (siteAbbreviationPosition not less than 1 nor bigger than 3), not fulfilled";
						return cspvh;
					}
					if (cspvh.conditionIdPosition < 1 || cspvh.conditionIdPosition > 3 ) {
						cspvh.valid = false;
						cspvh.errorMessage = "condition (conditionPosition not less than 1 nor bigger than 3), not fulfilled";
						return cspvh;
					}
					if (cspvh.skuPosition == cspvh.siteAbbreviationPosition || cspvh.skuPosition == cspvh.conditionIdPosition || cspvh.siteAbbreviationPosition == cspvh.conditionIdPosition) {
						cspvh.valid = false;
						cspvh.errorMessage = "condition (different elements must have different positions), not fulfilled";
						return cspvh;
					}
					if (cspvh.skuPosition + cspvh.conditionIdPosition + cspvh.siteAbbreviationPosition != 6) {
						cspvh.valid = false;
						cspvh.errorMessage = "condition (sumOfPositions == 6), not fulfilled";
						return cspvh;
					}
				}
				// two elements present: sku and siteAbbreviation
				if (cspvh.siteAbbreviationPosition != null && cspvh.conditionIdPosition == null) {
					if (cspvh.siteAbbreviationPosition < 1 || cspvh.siteAbbreviationPosition > 3 ) {
						cspvh.valid = false;
						cspvh.errorMessage = "condition (siteAbbreviationPosition not less than 1 nor bigger than 3), not fulfilled";
						return cspvh;
					}
					if (cspvh.skuPosition == cspvh.siteAbbreviationPosition) {
						cspvh.valid = false;
						cspvh.errorMessage = "condition (different elements must have different positions), not fulfilled";
						return cspvh;
					}
					if (cspvh.skuPosition + cspvh.siteAbbreviationPosition != 3) {
						cspvh.valid = false;
						cspvh.errorMessage = "condition (sumOfPositions == 3), not fulfilled";
						return cspvh;
					}
				}
				// two elements present: sku and conditionId
				if (cspvh.conditionIdPosition != null && cspvh.siteAbbreviationPosition == null) {
					if (cspvh.conditionIdPosition < 1 || cspvh.conditionIdPosition > 3 ) {
						cspvh.valid = false;
						cspvh.errorMessage = "condition (conditionIdPosition not less than 1 nor bigger than 3), not fulfilled";
						return cspvh;
					}
					if (cspvh.skuPosition == cspvh.conditionIdPosition) {
						cspvh.valid = false;
						cspvh.errorMessage = "condition (different elements must have different positions), not fulfilled";
						return cspvh;
					}
					if (cspvh.skuPosition + cspvh.conditionIdPosition != 3) {
						cspvh.valid = false;
						cspvh.errorMessage = "condition (sumOfPositions == 3), not fulfilled";
						return cspvh;
					}
				}
				// unique element, sku
				if (cspvh.siteAbbreviationPosition == null && cspvh.conditionIdPosition == null) {
					if (cspvh.skuPosition != 1) {
						cspvh.valid = false;
						cspvh.errorMessage = "condition (skuPosition == 1) if unique present element, not fulfilled";
						return cspvh;
					}
				}
				cspvh.valid = true;
				cspvh.errorMessage = null;
				return cspvh;
			} catch (Throwable e) {
				cspvh.valid = false;
				cspvh.errorMessage = "error -- exception: " + e.getMessage();
				return cspvh;
			}
		}

	}



	public static enum ValidationSource {
		PATTERN,
		POSITIONS
	}




	/**
	 * <p> Class for CustomSku <b>generation / reversal</b> purposes.
	 * 
	 * <p> Use <i>pattern</i> to create instance of this class, then 
	 * {@link CustomSkuProcessorHelper#generate(String, String, String) generate} or 
	 * {@link CustomSkuProcessorHelper#reverse(String) reverse} customSku.
	 * 
	 * <p>Parameter <i>pattern</i> typically would be the outcome of a 
	 * decision-making process in the marketplace, (not strictly) similar 
	 * to this: 
	 * <ul> 
	 * <li>main or preferred value depends on storeId and siteId 
	 * <li>first fallback depends on storeId (siteId is null)
	 * <li>second fallback is defined by the marketplace (storeId and siteId are null)
	 * </ul>
	 * 
	 * <p>Note that <b>pattern depends always on the marketplace</b>, so this class must not
	 * suggest any pattern as fallback nor default.
	 * 
	 * @author Alberto Romero
	 * @since 2025-01-22
	 * 
	 */
	public static class CustomSkuProcessorHelper {

		private String pattern;

		private String siteAbbreviation;
		private String conditionId;
		private String sku;
		private String customSku;

		private String modifiedCustomSku;
		private String modifiedPattern;
		private boolean siteAbbreviationExtracted;
		private boolean conditionIdExtracted;
		private boolean skuExtracted;



		public CustomSkuProcessorHelper(String pattern) throws Throwable {

			CustomSkuPatternValidationHelper cspvh = null;

			if (pattern == null || pattern.isEmpty()) {
				throw new Exception("pattern cannot be null, nor be empty");
			}

			cspvh = new CustomSkuPatternValidationHelper(pattern);
			if (!cspvh.isValid()) {
				throw new Exception("customSku, dbPattern not valid");
			}
			this.pattern = pattern;
			return;
		}



		/*
		 * GENERATION / REVERSAL FUNCTIONS
		 */


		@Deprecated
		public static String customSkuBaseFormat(String pattern, String siteAbbreviation, Integer conditionId, String sku) {
			return String.format(pattern, siteAbbreviation, conditionId, sku);
		}


		/**
		 * <p>Generate customSku.
		 * 
		 * <p>Params <i>siteAbbreviation</i> and / or <i>conditionId</i> may be
		 * null if they are absent from <i>pattern</i> used for instantiation.
		 * 
		 * @author Alberto Romero
		 * @since 2025-01-20 
		 */
		public String generate(String siteAbbreviation, String conditionId, String sku) {
			if (this.pattern == null) {
				return null;
			}
			this.siteAbbreviation = siteAbbreviation;
			this.conditionId = conditionId;
			this.sku = sku;
			this.customSku = null;
			this.customSku = this.pattern
					.replaceAll(Component.SITE_ABBREVIATION.regexPatternPlaceholder, siteAbbreviation)
					.replaceAll(Component.CONDITION_ID.regexPatternPlaceholder, conditionId)
					.replaceAll(Component.SKU.regexPatternPlaceholder, sku);
			return this.customSku;
		}


		/**
		 * <p>Reverse customSku.
		 * 
		 * @author Alberto Romero
		 * @since 2025-01-20 
		 */
		public ReversedCustomSkuPojo reverse(String customSku) {
			if (this.pattern == null || customSku == null) {
				return null;
			}

			this.customSku = customSku;
			this.modifiedCustomSku = customSku;
			this.modifiedPattern = this.pattern;

			this.siteAbbreviation = null;
			this.siteAbbreviationExtracted = true;
			if (this.pattern.matches("^.*" + Component.SITE_ABBREVIATION.regexPatternPlaceholder + ".*$")) {
				this.siteAbbreviationExtracted = false;
			}

			this.conditionId = null;
			this.conditionIdExtracted = true;
			if (this.pattern.matches("^.*" + Component.CONDITION_ID.regexPatternPlaceholder + ".*$")) {
				this.conditionIdExtracted = false;
			}

			this.sku = null;
			this.skuExtracted = false;

			while (!this.siteAbbreviationExtracted || !this.conditionIdExtracted) {
				extractAssignComponentsNotSku();
			}

			extractAssignSku();

			ReversedCustomSkuPojo rcsp = new ReversedCustomSkuPojo(this.siteAbbreviation, this.conditionId, this.sku);
			return rcsp;
		}


		private void setValueForComponent(Component c, String v) {
			switch (c) {
			case SITE_ABBREVIATION:
				this.siteAbbreviation = v;
				break;
			case CONDITION_ID:
				this.conditionId = v;
				break;
			case SKU:
				this.sku = v;
				break;
			}
		}


		private void setExtractedForComponent(Component c, boolean v) {
			switch (c) {
			case SITE_ABBREVIATION:
				this.siteAbbreviationExtracted = v;
				break;
			case CONDITION_ID:
				this.conditionIdExtracted = v;
				break;
			case SKU:
				this.skuExtracted = v;
				break;
			}
		}


		private void extractAssignComponentsNotSku() {

			for (Component c : Component.values()) {

				if (c == Component.SKU) {
					continue;
				}

				for (Border b : Border.values()) {

					// regex objects for modifiedPattern
					String reSP1 = null;
					Pattern reP1 = null;
					Matcher reM1 = null;

					// regex objects for modifiedSku
					String reSP2 = null;
					Pattern reP2 = null;
					Matcher reM2 = null;

					// value
					String value = null;

					switch (b) {
					case LEFT:
						reSP1 = "^" + c.regexPatternPlaceholder + DELIMITER;
						reSP2 = "^" + c.regexCustomSkuPlaceholder + DELIMITER;
						break;
					case RIGHT:
						reSP1 = DELIMITER + c.regexPatternPlaceholder + "$";
						reSP2 = DELIMITER + c.regexCustomSkuPlaceholder + "$";
						break;
					}


					reP1 = Pattern.compile(reSP1);
					reM1 = reP1.matcher(modifiedPattern);
					if (reM1.find()) {

						reP2 = Pattern.compile(reSP2);
						reM2 = reP2.matcher(modifiedCustomSku);
						reM2.find();

						value = reM2.group();

						switch(b) {
						case LEFT:
							value = value.substring(0, value.length() -1); // remove pre-appended delimiter
							setValueForComponent(c, value);
							modifiedCustomSku = modifiedCustomSku.replaceFirst("^" + value + DELIMITER, "");
							break;
						case RIGHT:
							value = value.substring(1, value.length()); // remove post-appended delimiter
							setValueForComponent(c, value);
							modifiedCustomSku = modifiedCustomSku.replaceFirst(DELIMITER + value + "$", "");
							break;
						}

						modifiedPattern = modifiedPattern.replaceFirst(reSP1, "");
						setExtractedForComponent(c, true);
						return;
					}
				}
			}
		}


		private void extractAssignSku() {

			// SKU Component
			Component c = Component.SKU;

			// regex objects for modifiedPattern
			String reSP1 = null;
			Pattern reP1 = null;
			Matcher reM1 = null;

			// regex objects for modifiedSku
			String reSP2 = null;
			Pattern reP2 = null;
			Matcher reM2 = null;

			// value
			String value = null;

			// only sku must remain, no surrounding delimiter(s)
			reSP1 = "^" + c.regexPatternPlaceholder + "$";
			reSP2 = "^" + c.regexCustomSkuPlaceholder + "$";


			reP1 = Pattern.compile(reSP1);
			reM1 = reP1.matcher(modifiedPattern);
			if (reM1.find()) {

				reP2 = Pattern.compile(reSP2);
				reM2 = reP2.matcher(modifiedCustomSku);
				reM2.find();

				value = reM2.group();

				// only sku must remain, no surrounding delimiter(s)
				value = value.substring(0, value.length());
				setValueForComponent(c, value);
				modifiedCustomSku = modifiedCustomSku.replaceFirst("^" + value + "$", "");

				modifiedPattern = modifiedPattern.replaceFirst(reSP1, "");
				setExtractedForComponent(c, true);
			}
			return;
		}

	}



	private static enum Border {
		LEFT,
		RIGHT
	}



	public static class ReversedCustomSkuPojo {

		private String siteAbbreviation;
		private String conditionId;
		private String sku;

		public ReversedCustomSkuPojo (String siteAbbreviation, String conditionId, String sku) {
			this.siteAbbreviation = siteAbbreviation;
			this.conditionId = conditionId;
			this.sku = sku;
		}

		public String toString() {
			String out = "{ "
					+ "" + "siteAbbreviation: " + this.siteAbbreviation
					+ ", " + "conditionId: " + this.conditionId
					+ ", " + "sku: " + this.sku
					+ " }"
					;
			return out;
		}

		public String getSiteAbbreviation() {
			return siteAbbreviation;
		}
		public String getConditionId() {
			return conditionId;
		}
		public String getSku() {
			return sku;
		}

	}

}