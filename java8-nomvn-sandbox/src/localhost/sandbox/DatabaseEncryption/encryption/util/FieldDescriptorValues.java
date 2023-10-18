package localhost.sandbox.DatabaseEncryption.encryption.util;


@Deprecated
public class FieldDescriptorValues {
	
	private Long fieldIdValue;
	
	private String fieldValue;
	
	private Integer fieldLength;

	private String decrypted;

	private String encrypted;

	public String toString() {
		String out = "{"
				+ "fieldIdValue:\"" + fieldIdValue + "\"" + ", "
				+ "fieldValue:\"" + fieldValue + "\"" + ", "
				+ "fieldLength:\"" + fieldLength + "\"" + ", "
				+ "decrypted:\"" + decrypted + "\"" + ", "
				+ "encrypted:\"" + encrypted + "\"" + ""
				+ "}"
				;
		return out;
	}
	
	public FieldDescriptorValues() {
	}

	public Long getFieldIdValue() {
		return fieldIdValue;
	}

	public void setFieldIdValue(Long fieldIdValue) {
		this.fieldIdValue = fieldIdValue;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public Integer getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(Integer fieldLength) {
		this.fieldLength = fieldLength;
	}

	public String getDecrypted() {
		return decrypted;
	}

	public void setDecrypted(String decrypted) {
		this.decrypted = decrypted;
	}

	public String getEncrypted() {
		return encrypted;
	}

	public void setEncrypted(String encrypted) {
		this.encrypted = encrypted;
	}
	

}
