package localhost.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>Helper for CustomSku pattern operations</h1>
 * 
 * <p>Available operations:
 * <ul>
 * <li>Validation
 * <li>Generation, Reversal
 * </ul>
 * 
 * <h2>Validation</h2>
 * <p>Use {@link CustomSkuPatternValidationHelper CustomSkuPatternHelperPojo} constructors: from pattern generate positions, or viceversa.
 * 
 * <h2>Generation, Reversal</h2>
 * <p>Use {@link CustomSkuPatternValidationHelper CustomSkuPatternHelperPojo} constructors: from pattern generate positions, or viceversa.
 * 
 * <p>Delimiter character is '<b>_</b>' (underscore, <b>final constant</b>).
 * 
 * @author Alberto Romero
 * @since 2025-01-17
 * 
 */
public class CustomSkuHelperV2 {

	protected static final String VERSION = "2025-01-20T14:02:00Z";

	protected static final String DELIMITER = "_";

	/**
	 * <p>Components (fields) integrating customSku.
	 * 
	 * <p><b>Important:</b> SKU is the <b>only</b> component allowed
	 * to contain DELIMITER character.
	 * 
	 * @author Alberto Romero
	 * @since 2025-01-20
	 */
	protected static enum Component {
		SITE_ABBREVIATION("{siteAbbreviation}"),
		CONDITION_ID("{conditionId}"),
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

	protected static final String DEFAULT_PATTERN = Component.SITE_ABBREVIATION + DELIMITER + Component.CONDITION_ID + DELIMITER + Component.SKU;	

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
		// testValidationGoodInputs();
		// testValidationBadInputs();
		testGenerateReverseGoodInputs();
	}

	private static void testValidationGoodInputs() {
		System.out.println("Hello from testValidationGoodInputs!");

		CustomSkuPatternValidationHelper csphpSrcPatt;
		CustomSkuPatternValidationHelper csphpSrcPosn;
		int i = 0;

		// test00, test DEFAULT_PATTERN
		System.out.println("test00, DEFAULT_PATTERN: ");
		csphpSrcPatt = new CustomSkuPatternValidationHelper(DEFAULT_PATTERN);
		System.out.println("csphpSrcPatt: " + csphpSrcPatt);
		csphpSrcPosn = new CustomSkuPatternValidationHelper(csphpSrcPatt.siteAbbreviationPosition, csphpSrcPatt.conditionIdPosition, csphpSrcPatt.skuPosition);
		System.out.println("csphpSrcPosn: " + csphpSrcPosn);

		// test01, test all ACCEPTED_PATTERNS
		System.out.println("test01, ACCEPTED_PATTERNS: ");
		for (i = 0; i < ACCEPTED_PATTERNS.length ; i++) {
			csphpSrcPatt = new CustomSkuPatternValidationHelper(ACCEPTED_PATTERNS[i]);
			System.out.println("i: " + i + " ; csphpSrcPatt: " + csphpSrcPatt);
			csphpSrcPosn = new CustomSkuPatternValidationHelper(csphpSrcPatt.siteAbbreviationPosition, csphpSrcPatt.conditionIdPosition, csphpSrcPatt.skuPosition);
			System.out.println("i: " + i + " ; csphpSrcPosn: " + csphpSrcPosn);
		}
	}

	private static void testValidationBadInputs() {
		System.out.println("Hello from testValidationBadInputs!");

		CustomSkuPatternValidationHelper csphp;
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
			csphp = new CustomSkuPatternValidationHelper(nonValidPatterns[i]);
			System.out.println("i: " + i + "; patt: " + nonValidPatterns[i] + " ; csphp: " + csphp);
		}


		// NON VALID POSITION VALUES
		System.out.println("test, non valid positions");

		// testB00, sku null
		csphp = new CustomSkuPatternValidationHelper(1,1,null);
		System.out.println("testB00, pons: 1,1,null, csphp: " + csphp);

		// testB01_a, sku not null, unique, value different to 1
		csphp = new CustomSkuPatternValidationHelper(null,null,0);
		System.out.println("testB01_a, pons: null,null,0, csphp: " + csphp);

		// testB01_b, sku not null, unique, value different to 1
		csphp = new CustomSkuPatternValidationHelper(null,null,2);
		System.out.println("testB01_b, pons: null,null,2, csphp: " + csphp);

		// testB01_c, sku not null, unique, value different to 1
		csphp = new CustomSkuPatternValidationHelper(null,null,4);
		System.out.println("testB01_c, pons: null,null,4, csphp: " + csphp);

		// testB02, elements with same value
		csphp = new CustomSkuPatternValidationHelper(1,1,1);
		System.out.println("testB02, pons: 1,1,1, csphp: " + csphp);

		// testB03, elements with same value
		csphp = new CustomSkuPatternValidationHelper(1,1,2);
		System.out.println("testB03, pons: 1,1,2, csphp: " + csphp);

		// testB04, element with value bigger than 3
		csphp = new CustomSkuPatternValidationHelper(4,1,2);
		System.out.println("testB03, pons: 4,1,2, csphp: " + csphp);

	}

	private static void testGenerateReverseGoodInputs() {
		System.out.println("Hello from testGenerateReverseGoodInputs!");

		String dbPattern = null;
		String dbFallbackPattern = null;
		String siteAbbreviation = "ES";
		String conditionId = "1000";
		String sku = "ABCXYZ";
		String reversedSku = null;
		String customSku = null;

		System.out.println("ACCEPTED_PATTERNS:");
		CustomSkuProcessorHelper csph = null;
		int i = 0;
		for (i = 0; i < ACCEPTED_PATTERNS.length ; i++) {
			dbPattern = ACCEPTED_PATTERNS[i];
			dbFallbackPattern = ACCEPTED_PATTERNS[i];
			try {
				csph = new CustomSkuProcessorHelper(dbPattern, dbFallbackPattern);
			} catch (Throwable ex) {
				System.err.println("exception: " +  ex);
			}
			customSku = csph.generate(siteAbbreviation, conditionId, sku);
			reversedSku = csph.reverse(customSku);

			System.out.println("i: " + i + " ; dbPattern: " + dbPattern +  " ; sku: " + sku + " ; customSku: " + customSku + " ; reversedSku: " + reversedSku);
		}


		System.out.println("SKU containing DELIMITER character:");
		sku = "ABC_LMN_XYZ";
		for (i = 0; i < ACCEPTED_PATTERNS.length ; i++) {
			dbPattern = ACCEPTED_PATTERNS[i];
			dbFallbackPattern = ACCEPTED_PATTERNS[i];
			try {
				csph = new CustomSkuProcessorHelper(dbPattern, dbFallbackPattern);
			} catch (Throwable ex) {
				System.err.println("exception: " +  ex);
			}
			customSku = csph.generate(siteAbbreviation, conditionId, sku);
			reversedSku = csph.reverse(customSku);

			System.out.println("i: " + i + " ; dbPattern: " + dbPattern +  " ; sku: " + sku + " ; customSku: " + customSku + " ; reversedSku: " + reversedSku);
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



	public static class CustomSkuPatternHelperExtendedPojo extends CustomSkuPatternValidationHelper {

		private String version = VERSION;

		// constructors

		public CustomSkuPatternHelperExtendedPojo (CustomSkuPatternValidationHelper cspvh) {
			super();
			this.source = cspvh.source;
			this.pattern = cspvh.pattern;
			this.siteAbbreviationPosition = cspvh.siteAbbreviationPosition;
			this.conditionIdPosition = cspvh.conditionIdPosition;
			this.skuPosition = cspvh.skuPosition;
			this.skuPosition = cspvh.skuPosition;
			this.valid = cspvh.valid;
		}

		public CustomSkuPatternHelperExtendedPojo () {
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




	/**
	 * <p> Class for CustomSku <b>generation / reversal</b> purposes.
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
	public static class CustomSkuProcessorHelper {

		private String pattern;
		private ProcessorPatternSource processorPatternSource;

		private String siteAbbreviation;
		private String conditionId;
		private String sku;
		private String customSku;

		private String modifiedCustomSku;
		private String modifiedPattern;
		private boolean siteAbbreviationExtracted;
		private boolean conditionIdExtracted;
		private boolean skuExtracted;



		public CustomSkuProcessorHelper(String dbPattern, String dbFallbackPattern) throws Throwable {

			CustomSkuPatternValidationHelper cspvh = null;

			if (dbPattern != null) {
				cspvh = new CustomSkuPatternValidationHelper(dbPattern);
				if (!cspvh.isValid()) {
					throw new Exception("customSku, dbPattern not valid");
				}
				this.pattern = dbPattern;
				this.processorPatternSource = ProcessorPatternSource.DB;
				return;
			}

			if (dbFallbackPattern != null) {
				cspvh = new CustomSkuPatternValidationHelper(dbFallbackPattern);
				if (!cspvh.isValid()) {
					throw new Exception("customSku, dbFallbackPattern not valid");
				}
				this.pattern = dbFallbackPattern;
				this.processorPatternSource = ProcessorPatternSource.DB_FALLBACK;
				return;
			}

			this.pattern = DEFAULT_PATTERN;
			this.processorPatternSource = ProcessorPatternSource.DEFAULT;
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
		public String reverse(String customSku) {
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
			return this.sku;
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
						case Border.LEFT:
							value = value.substring(0, value.length() -1); // remove pre-appended delimiter
							setValueForComponent(c, value);
							modifiedCustomSku = modifiedCustomSku.replaceFirst("^" + value + DELIMITER, "");
							break;
						case Border.RIGHT:
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


	private static enum ProcessorPatternSource {
		DB,
		DB_FALLBACK,
		DEFAULT
	}


	private static enum Border {
		LEFT,
		RIGHT
	}

}