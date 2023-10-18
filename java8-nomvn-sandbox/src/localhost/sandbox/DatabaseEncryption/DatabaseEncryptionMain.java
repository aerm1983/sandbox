package localhost.sandbox.DatabaseEncryption;

import java.util.Optional;

import localhost.helper.LogHelper;
import localhost.sandbox.DatabaseEncryption.app.mapper.PersonMapper;
import localhost.sandbox.DatabaseEncryption.app.model.PersonModel;
import localhost.sandbox.DatabaseEncryption.encryption.mapper.PersonMapperWrapper;
import localhost.sandbox.DatabaseEncryption.encryption.util.EncryptionInitializationCLR;

public class DatabaseEncryptionMain {
	
	static LogHelper log = new LogHelper();
	
	static PersonMapperWrapper personMapperWrapper = new PersonMapperWrapper();
	
	/**
	 * <p> Remember: static attribute "PersonMapper.personMap" represents
	 * target database table.
	 * 
	 * @author Alberto Romero
	 * @since 2023-10-18
	 */
	public static void main() {
		
		// check unencrypted personMap
		log.info("personMap, unencrypted: {}", PersonMapper.staticPersonMapToString());
		
		
		// execute CommandLineRunner (CLR)
		EncryptionInitializationCLR eic = new EncryptionInitializationCLR();
		try {
			eic.run( (String[]) null);
		} catch (Exception e) {
			log.error("error: {}", e);
		}
		
		
		// check encrypted personMap
		log.info("personMap, encrypted: {}", PersonMapper.staticPersonMapToString());
		
		
		// check decrypted person
		Optional<PersonModel> personOpt = personMapperWrapper.findById(0L);
		PersonModel person = personOpt.isPresent() ? personOpt.get() : null;
		log.info("person, decrypted: {}", person);

		
		// TODO add PersonMapper (and Wrapper) methods returning List and Page
		
		
		// end
		return;
	}

}
