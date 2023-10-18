package localhost.sandbox.DatabaseEncryption;

import localhost.helper.LogHelper;
import localhost.sandbox.DatabaseEncryption.encryption.util.EncryptionInitializationCLR;

public class DatabaseEncryptionMain {
	
	public static void main() {
		
		LogHelper log = new LogHelper();
		
		// first: CommandLineRunner
		EncryptionInitializationCLR eic = new EncryptionInitializationCLR();
		try {
			eic.run( (String[]) null);
		} catch (Exception e) {
			log.error("error: {}", e);
		}
		
		// second: check PersonModel / PersonMapper, unencrypted
		// TODO
		
		
	}

}
