package localhost.sandbox.DatabaseEncryption.encryption.util;

import localhost.helper.CipherAesEcbPkcs5Helper;
import localhost.helper.LogHelper;
import localhost.sandbox.DatabaseEncryption.app.service.EncryptionTableTransformationManagerService;
import localhost.sandbox.DatabaseEncryption.encryption.mapper.PersonMapperWrapper;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;



// @Component
// @Order(1)
public class EncryptionInitializationCLR /* implements CommandLineRunner */ {

	// private static Logger log = LoggerFactory.getLogger(EncryptionInitializationCLR.class);
	private static LogHelper log = new LogHelper(); 
	
	// @Autowired
	// PersonMapperWrapper personMapperWrapper;
	PersonMapperWrapper personMapperWrapper = new PersonMapperWrapper();

	// @Autowired
	// EncryptionTableTransformationManager encryptionTableTransformationManager;
	EncryptionTableTransformationManagerService encryptionTableTransformationManager = new EncryptionTableTransformationManagerService();
	
	// @Value("${db.fieldEncryption.keyBase16}")
    // private String dbFieldEncryptionKeyBase16;
	private String dbFieldEncryptionKeyBase16 = "983791D130AE8AC39E6BF5FD31C2768A";
	
	// @Value("${db.fieldEncryption.enabled}")
    // private Boolean dbFieldEncryptionEnabled;
	private Boolean dbFieldEncryptionEnabled = true;
	
	
	/**
	 * <p> This method is only intended for testing on java SE.
	 */
	public void initializeForJSETest(boolean inEnableEncryption) {
		dbFieldEncryptionEnabled = inEnableEncryption;
	}
	
	
	// @Override
	public void run(String... args) throws Exception {
		
		CipherAesEcbPkcs5Helper.initialize(dbFieldEncryptionKeyBase16);
		
		log.info("Encryption initialization -- CipherAesEcbPkcs5Helper.isInitialized: {} ; dbFieldEncryptionKeyBase16.substr(0,4): {} ; dbFieldEncryptionEnabled: {}", CipherAesEcbPkcs5Helper.isInitialized(), dbFieldEncryptionKeyBase16.substring(0,4), dbFieldEncryptionEnabled);
		
		encryptionTableTransformationManager.initializeForJSETest(dbFieldEncryptionEnabled); // only for testing on java SE
		encryptionTableTransformationManager.cryptTransformTablePerson();
		
		return;
	}
}
