package localhost.sandbox.jse8.DatabaseEncryption.encryption.util;

// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

@Deprecated
// @NoArgsConstructor
// @Getter
// @Setter
public class FieldDescriptorMetadata {
	
	private String tableName;
	
	private String fieldIdName;
	
	private String fieldName;
	
	private Integer maxAllowedLength;
	
	private Float refRatioEncToDec;

	public String toString() {
		String out = "{"
				+ "tableName:\"" + tableName + "\"" + ", "
				+ "fieldIdName:\"" + fieldIdName + "\"" + ", "
				+ "fieldName:\"" + fieldName + "\"" + ", "
				+ "maxAllowedLength:\"" + maxAllowedLength + "\"" + ", "
				+ "refRatioEncToDec:\"" + refRatioEncToDec + "\"" + ""
				+ "}"
				;
		return out;
	}
	
	public FieldDescriptorMetadata() {
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldIdName() {
		return fieldIdName;
	}

	public void setFieldIdName(String fieldIdName) {
		this.fieldIdName = fieldIdName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getMaxAllowedLength() {
		return maxAllowedLength;
	}

	public void setMaxAllowedLength(Integer maxAllowedLength) {
		this.maxAllowedLength = maxAllowedLength;
	}

	public Float getRefRatioEncToDec() {
		return refRatioEncToDec;
	}

	public void setRefRatioEncToDec(Float refRatioEncToDec) {
		this.refRatioEncToDec = refRatioEncToDec;
	}
	
	
}
