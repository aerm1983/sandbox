package localhost.sandbox.DatabaseEncryption.app.model;

public class PersonModel {

	private long id;
	
	private String name;
	
	private String address;
	
	private String comment;
	
	
	
	public PersonModel() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String toString () {
		String out = "{"
				+ "\"id\":" + id + ","
				+ "\"name\":" + "\"" + name + "\"" + ","
				+ "\"address\":" + "\"" + address + "\"" + ","
				+ "\"comment\":" + "\"" + comment + "\""
				+ "}"
				;
		return out;
		
		
	}
	
}
