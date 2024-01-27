package localhost.opencsv.sandbox;

import com.opencsv.bean.CsvBindByName;

public class Sample00BeanBindByName {

	@CsvBindByName(column = "letter")
	private String letter;

	@CsvBindByName(column = "number")
	private String number;

	// constructor, getters, setters

	public Sample00BeanBindByName() {
		super();
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

}
