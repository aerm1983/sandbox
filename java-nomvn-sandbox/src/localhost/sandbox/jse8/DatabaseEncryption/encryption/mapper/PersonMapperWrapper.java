package localhost.sandbox.jse8.DatabaseEncryption.encryption.mapper;

import java.util.List;
import java.util.Optional;

import localhost.sandbox.jse8.A0Helper.Page;
import localhost.sandbox.jse8.DatabaseEncryption.app.mapper.PersonMapper;
import localhost.sandbox.jse8.DatabaseEncryption.app.model.PersonModel;
import localhost.sandbox.jse8.DatabaseEncryption.encryption.util.EncryptionFunctions;

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
	// private EncryptionFunctions ef
	private EncryptionFunctions ef = new EncryptionFunctions();

	
	/**
	 * <p> This method is only intended for testing on java SE.
	 */
	public EncryptionFunctions getEncryptionFunctionsForJSETest() {
		return ef;
	} 
	
	
	public int insert (PersonModel person) {
		PersonModel toDbEncryptedPerson = ef.encryptObject(person);
		Integer insertInt = pm.insert(toDbEncryptedPerson);
		person.setId(toDbEncryptedPerson.getId()); // id from db
		return insertInt;
	}
	
	
	public Optional<PersonModel> findById(long id) {
		Optional<PersonModel> fromDbEncryptedPersonOptional = pm.findById(id);
		Optional<PersonModel> toAppDecryptedPersonOptional = ef.decryptObjecOptional(fromDbEncryptedPersonOptional);
		return toAppDecryptedPersonOptional;
	}
	
	
	public List<PersonModel> findAll() {
		List<PersonModel> fromDbEncryptedPersonList = pm.findAll();
		List<PersonModel> toAppDecryptedPersonList = ef.decryptObjectList(fromDbEncryptedPersonList);
		return toAppDecryptedPersonList;
	}
	
	
	public Page<PersonModel> findAllPage() {
		Page<PersonModel> fromDbEncryptedPersonPage = pm.findAllPage();
		Page<PersonModel> toAppDecryptedPersonPage = ef.decryptObjectPage(fromDbEncryptedPersonPage);
		return toAppDecryptedPersonPage;
	}

		
	public int update(PersonModel person) {
		PersonModel toDbEncryptedPerson = ef.encryptObject(person);
		Integer updateInt = pm.update(toDbEncryptedPerson);
		return updateInt;
	}

}
