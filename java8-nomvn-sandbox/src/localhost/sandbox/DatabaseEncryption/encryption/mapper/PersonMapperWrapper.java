package localhost.sandbox.DatabaseEncryption.encryption.mapper;

import java.util.Optional;

import localhost.sandbox.DatabaseEncryption.app.mapper.PersonMapper;
import localhost.sandbox.DatabaseEncryption.app.model.PersonModel;
import localhost.sandbox.DatabaseEncryption.encryption.util.EncryptionFunctions;

/**
 * <p> This class is responsible for applying encryption / decryption on
 * objects coming-from / going-to database. 
 * 
 * <p> See comment on "EncryptionFunctions" class.
 * 
 * @author Alberto Romero
 * @since 2023-10-18
 *
 */
public class PersonMapperWrapper implements PersonMapperInterface {

	// @Autowired
	// private PersonMapper pm
	private PersonMapper pm = new PersonMapper();

	
	// @Autowired
	// private PersonMapper pm
	private EncryptionFunctions ef = new EncryptionFunctions();
	
	public Optional<PersonModel> findById(long id) {
		Optional<PersonModel> fromDbEncryptedPersonOptional = pm.findById(id);
		Optional<PersonModel> toAppDecryptedPersonOptional = ef.decryptObjecOptional(fromDbEncryptedPersonOptional);
		return toAppDecryptedPersonOptional;
	}
	
	public int update(PersonModel person) {
		PersonModel toDbEncryptedPerson = ef.encryptObject(person);
		Integer updateInt = pm.update(toDbEncryptedPerson);
		return updateInt;
	}

}
