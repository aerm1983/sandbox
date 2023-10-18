package localhost.sandbox.DatabaseEncryption.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import localhost.helper.CipherAesEcbPkcs5Helper;
import localhost.helper.LogHelper;
import localhost.sandbox.DatabaseEncryption.app.model.PersonModel;
import localhost.sandbox.DatabaseEncryption.encryption.mapper.PersonMapperWrapper;
import localhost.sandbox.DatabaseEncryption.encryption.util.EncryptionAssistantMapper;
import localhost.sandbox.DatabaseEncryption.encryption.util.FieldDescriptorMetadata;
import localhost.sandbox.DatabaseEncryption.encryption.util.FieldDescriptorValues;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;




/**
 * <p> Class with method to initialize a table, depending on YML 
 * parameter "db.fieldEncryption.enabled":
 * <ul>
 * <li> Encrypts fields (columns) annotated of table buy_order.
 * <li> Decrypts fields (columns) annotated of table buy_order.
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
	 * <p> Main method, responsible of encryption transformations in table "buy_order".
	 * 
	 * <p> Depending on overhead, it could be run always at application start.
	 * 
	 */
	public void cryptTransformTablePerson() {

		long countId = encryptionAssistantMapper.getCountId("buy_order", "id");
		log.info("cryptTransformTableBuyOrder() start -- dbFieldEncryptionEnabled: {} ; onAppStartEncryptTables: {} ; table: {} ; registersInTable: {}", dbFieldEncryptionEnabled, onAppStartEncryptTables, "buy_order", countId);
		
		if (!onAppStartEncryptTables) {
			log.info("cryptTransformTableBuyOrder() end -- exit without performing any action ; onAppStartEncryptTables: {}", onAppStartEncryptTables);
			return;
		}
		
		long maxId = encryptionAssistantMapper.findMaxId("buy_order", "id");
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
		
		while (id > 0) {
			try {
				personOpt = personMapperWrapper.findById(id);
				if (personOpt.isPresent()) {
					person = personOpt.get();
					updInt = personMapperWrapper.update(person);
					if (updInt != 1) {
						log.error("unexpected problem, value of updated registers is not '1', when updating in table 'buy_order' ; id: {} ; updInt: {}", id, updInt);
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
					log.info("encryption transformation advance reference -- table: {} ; id: {} ; register {} of {} ; updated registers: {} ; elapsed time (secs): {}", "buy_order", id, i, countId, updRegsCounter, iterDiffTimeSec);
				}
				id--;
				personOpt = null;
				person = null;
				updInt = 0;
			}
		}
		
		endTimeMillisec = System.currentTimeMillis();
		endDiffTimeSec = (float) ( ( (double) ( endTimeMillisec - initTimeMillisec ) ) / ( 1000.0 ) );
		log.info("cryptTransformTableBuyOrder() end -- encryption transformation ended -- dbFieldEncryptionEnabled: {} ; table: {} ; registers in table: {} ; total registers updated: {} ; execution time (secs): {}", dbFieldEncryptionEnabled, "buy_order", countId, updRegsCounter, endDiffTimeSec);
	}
	
	
	
	/**
	 * <p> Method no longer necessary.
	 */
	@Deprecated
	public boolean verifyEncryptedLengthForFieldsOnTableBuyOrder() {
		
		if (!dbFieldEncryptionEnabled) {
			log.info("decrypton of fields reduces lengths, so no verification is required -- dbFieldEncryptionEnabled: {}", dbFieldEncryptionEnabled);
			return true;
		}
		
		
		// table "buy_order", field "buyer_user_firstname"
		FieldDescriptorMetadata fdm00 = new FieldDescriptorMetadata();
		fdm00.setTableName("buy_order");
		fdm00.setFieldIdName("id");
		fdm00.setFieldName("buyer_user_firstname");
		fdm00.setMaxAllowedLength(128);
		fdm00.setRefRatioEncToDec(2.50f);
		
		// table "buy_order", field "buyer_user_lastname"
		FieldDescriptorMetadata fdm01 = new FieldDescriptorMetadata();
		fdm01.setTableName("buy_order");
		fdm01.setFieldIdName("id");
		fdm01.setFieldName("buyer_user_lastname");
		fdm01.setMaxAllowedLength(128);
		fdm01.setRefRatioEncToDec(2.50f);

		// table "buy_order", field "buyer_user_lastname"
		FieldDescriptorMetadata fdm02 = new FieldDescriptorMetadata();
		fdm02.setTableName("buy_order");
		fdm02.setFieldIdName("id");
		fdm02.setFieldName("buyer_email");
		fdm02.setMaxAllowedLength(128);
		fdm02.setRefRatioEncToDec(2.50f);

		// verification
		boolean success00 = verifyEncryptedLengthForField (fdm00);
		boolean success01 = verifyEncryptedLengthForField (fdm01);
		boolean success02 = verifyEncryptedLengthForField (fdm02);
		
		if (success00 && success01 && success02) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	/**
	 * <p> Method no longer necessary. 
	 */
	@Deprecated
	public boolean verifyEncryptedLengthForField (FieldDescriptorMetadata fdm) {
		
		boolean success = true;
		
		int refSearchLength = (int) Math.ceil( fdm.getMaxAllowedLength() / fdm.getRefRatioEncToDec() ) ;
		// adjustment for testing, comment this for production
		/*
		int oldRSL = refSearchLength;
		if (refSearchLength > 40) {
			refSearchLength = 40;
			log.info("refSearchLength adjusted for testing purposes --- old value: {} ; adjusted value for testing: {}", oldRSL, refSearchLength);
		}
		*/
		
		List<FieldDescriptorValues> fdvList = encryptionAssistantMapper.findFieldLengths(fdm.getTableName(), fdm.getFieldIdName(), fdm.getFieldName(), refSearchLength);
		int totalRegsExceedMaxAllowedLength = 0;
		List<Long> idExceedMaxAllowedLengthList = new ArrayList<Long>();
		
		if (fdvList.size() > 0) {
			
			log.info("table: {}, field_name: {} -- there are {} registers with unencrypted length larger than {} characters; now will calculate encrypted values and check if their lengths do not exceed maxAllowedLength {}", fdm.getTableName(), fdm.getFieldIdName(), fdvList.size(), refSearchLength, fdm.getMaxAllowedLength());
			
			for (FieldDescriptorValues fdv : fdvList) {
				fdv.setDecrypted(fdv.getFieldValue());
				fdv.setEncrypted( CipherAesEcbPkcs5Helper.encrypt( fdv.getFieldValue() ) );
				
				if ( fdv.getEncrypted().length() > fdm.getMaxAllowedLength() ) {
					success = false;
					// log.error("relocate here!"); // place error message here
					log.info("this field value exceeds maxAllowedLength after encryption -- table: {} ; id: {} ; field_name: {} ; field_value: {} ; decrypted_field_length: {} ; encrypted_field_length: {} ; maxAllowedLength: {}", fdm.getTableName(), fdv.getFieldIdValue(), fdm.getFieldName(), fdv.getFieldValue().substring(0, 5) + "***" + fdv.getFieldValue().substring(fdv.getFieldValue().length() - 4), fdv.getDecrypted().length(), fdv.getEncrypted().length(), fdm.getMaxAllowedLength());
					totalRegsExceedMaxAllowedLength++;
					idExceedMaxAllowedLengthList.add(fdv.getFieldIdValue());
				}
				// log.info("for loop iteration end"); // debugging
			}
			// log.info("if end"); // debugging
		} 
		log.info("table: {}, field_name: {} -- there are {} registers which encrypted value exceeds maxAllowedLength -- ids: {}", fdm.getTableName(), fdm.getFieldName(), totalRegsExceedMaxAllowedLength, idExceedMaxAllowedLengthList);
		
		return success;
	}
}
