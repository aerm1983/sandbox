package localhost.opencsv.sandbox;

public class Sample00BeanSimple {

	private String letter;

	private String number;

	// constructor, getters, setters

	public Sample00BeanSimple() {
		super();
	}

	public Sample00BeanSimple(String letter, String number) {
		super();
		this.letter = letter;
		this.number = number;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		String out = ""
				+ "{"
				+ "letter:" + this.letter + ","
				+ "number:" + this.number + ""
				+ "}"
				;
		return out;
	}

}
