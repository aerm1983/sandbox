package localhost.opencsv.sandbox;

import com.opencsv.bean.CsvBindByPosition;

public class Sample00BeanBindByPosition {

	@CsvBindByPosition(position = 0)
	private String letter;

	@CsvBindByPosition(position = 1)
	private String number;

	// constructor, getters, setters

	public Sample00BeanBindByPosition() {
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
