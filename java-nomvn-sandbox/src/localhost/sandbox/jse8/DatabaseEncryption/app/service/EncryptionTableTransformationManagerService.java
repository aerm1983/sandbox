package localhost.sandbox.jse8.DatabaseEncryption.app.service;

import java.util.Optional;

import localhost.sandbox.jse8.A0Helper.LogHelper;
import localhost.sandbox.jse8.DatabaseEncryption.app.model.PersonModel;
import localhost.sandbox.jse8.DatabaseEncryption.encryption.mapper.PersonMapperWrapper;
import localhost.sandbox.jse8.DatabaseEncryption.encryption.util.EncryptionAssistantMapper;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;



/**
 * <p> Class with method to initialize a table, depending on YML 
 * parameter "db.fieldEncryption.enabled":
 * <ul>
 * <li> Encrypts fields (columns) annotated of table "person".
 * <li> Decrypts fields (columns) annotated of table "person".
 * </ul>
 * 
 * <p> Encryption / Decryption is fault-tolerant. Annotations are required
 * for methods to work properly.  See comments in "EncryptionFunctions.java".
 * 
 * @author Alberto Romero
 * @since 2023-10-02
 * 
 */
// @Service
public class EncryptionTableTransformationManagerService {
	
	// Logger log = LoggerFactory.getLogger(EncryptionTableTransformationManager.class);
	LogHelper log = new LogHelper();
	
	// @Value("${db.fieldEncryption.enabled}")
    // private Boolean dbFieldEncryptionEnabled;
	private Boolean dbFieldEncryptionEnabled = true;

	// @Value("${db.fieldEncryption.onAppStartEncryptTables}")
    // private Boolean onAppStartEncryptTables;
	private Boolean onAppStartEncryptTables = true;
	
	// @Autowired
	// private EncryptionAssistantMapper encryptionAssistantMapper;
	private EncryptionAssistantMapper encryptionAssistantMapper = new EncryptionAssistantMapper();
	
	// @Autowired
	// private PersonMapperWrapper personMapperWrapper;
	private PersonMapperWrapper personMapperWrapper = new PersonMapperWrapper();

	
	/**
	 * <p> This method is only intended for testing on java SE.
	 */
	public void initializeForJSETest(boolean inEnableEncryption) {
		dbFieldEncryptionEnabled = inEnableEncryption;
		personMapperWrapper.getEncryptionFunctionsForJSETest().initializeForJSETest(inEnableEncryption); // only for testing on java SE
	}

	
	/**
	 * <p> Main method, responsible of encryption transformations in table "person".
	 * 
	 * <p> Depending on overhead, it could be run always at application start.
	 * 
	 */
	public void cryptTransformTablePerson() {

		long countId = encryptionAssistantMapper.getCountId("person", "id");
		log.info("cryptTransformTablePerson() start -- dbFieldEncryptionEnabled: {} ; onAppStartEncryptTables: {} ; table: {} ; registersInTable: {}", dbFieldEncryptionEnabled, onAppStartEncryptTables, "person", countId);
		
		if (!onAppStartEncryptTables) {
			log.info("cryptTransformTablePerson() end -- exit without performing any action ; onAppStartEncryptTables: {}", onAppStartEncryptTables);
			return;
		}
		
		long maxId = encryptionAssistantMapper.findMaxId("person", "id");
		long id = maxId;
		Optional<PersonModel> personOpt = null;
		PersonModel person = null;
		int updInt = 0;
		int updRegsCounter = 0;
		long initTimeMillisec = System.currentTimeMillis();
		long iterTimeMillisec = 0;
		float iterDiffTimeSec = 0;
		long endTimeMillisec = 0;
		float endDiffTimeSec = 0;
		long i = 0;
		
		while (id >= 0) {
			try {
				personOpt = personMapperWrapper.findById(id);
				if (personOpt.isPresent()) {
					person = personOpt.get();
					updInt = personMapperWrapper.update(person);
					if (updInt != 1) {
						log.error("unexpected problem, value of updated registers is not '1', when updating in table 'person' ; id: {} ; updInt: {}", id, updInt);
					}
					updRegsCounter += updInt;
					i++;
				}
			} catch (Exception e) {
				log.error("error: ", e);
			} finally {
				iterTimeMillisec = System.currentTimeMillis();
				iterDiffTimeSec = (float) ( ( (double) ( iterTimeMillisec - initTimeMillisec ) ) / ( 1000.0 ) );
				if (i <= 1 || (i % 5000) == 0) {
					log.info("encryption transformation advance reference -- table: {} ; id: {} ; register {} of {} ; updated registers: {} ; elapsed time (secs): {}", "person", id, i, countId, updRegsCounter, iterDiffTimeSec);
				}
				id--;
				personOpt = null;
				person = null;
				updInt = 0;
			}
		}
		
		endTimeMillisec = System.currentTimeMillis();
		endDiffTimeSec = (float) ( ( (double) ( endTimeMillisec - initTimeMillisec ) ) / ( 1000.0 ) );
		log.info("cryptTransformTablePerson() end -- encryption transformation ended -- dbFieldEncryptionEnabled: {} ; table: {} ; registers in table: {} ; total registers updated: {} ; execution time (secs): {}", dbFieldEncryptionEnabled, "person", countId, updRegsCounter, endDiffTimeSec);
	}
	
	
}
