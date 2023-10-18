package localhost.sandbox.DatabaseEncryption.encryption.util;

public class FirstEncryptionPair {
	private String decrypted;
	private String encrypted;
	
	FirstEncryptionPair() {
	}
	
	FirstEncryptionPair(String inDecrypted, String inEncrypted) {
		this.decrypted = inDecrypted;
		this.encrypted = inEncrypted;
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
	
	public String toString() {
		String out = "{"
				+ "decrypted:\"" + decrypted + "\"" + ", "
				+ "encrypted:\"" + encrypted + "\"" + ""
				+ "}"
				;
		return out;
	}
}
