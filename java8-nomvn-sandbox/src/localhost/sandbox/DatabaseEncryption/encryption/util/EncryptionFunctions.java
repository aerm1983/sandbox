package localhost.sandbox.DatabaseEncryption.encryption.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// import javax.validation.constraints.Size;

import localhost.helper.CipherAesEcbPkcs5Helper;
import localhost.helper.LogHelper;
import localhost.helper.ObjectCopyHelper;
import localhost.helper.Page;
import localhost.helper.Size;
import localhost.sandbox.DatabaseEncryption.app.model.PersonModel;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

// import com.github.pagehelper.Page;


/**
 * <p> This class contains methods to handle encryption into "*MapperWrapper" 
 * components.
 * 
 * <p> Without column encryption, interaction between application and database looks this way:
 * <ul>
 * <li> app --> db_mapper --> app
 * </ul>
 *  
 * <p> When implementing column encryption in db, interaction becomes:
 * <ul>
 * <li> app --> encrypt --> db_mapper --> decrypt --> app
 * </ul>
 * 
 * <p> From a functional standpoint, it can be noticed:
 * <ul>
 * <li> Encryption occurs on objects before db_mapper.
 * <li> Decryption occurs on objects after db_mapper.
 * <li> Depending on input(s), output(s), and sql field(s) involved, encryption and/or decryption may not be required.
 * </ul>
 * 
 * <p> Regarding simple input/output variables, where no encryption / decryption 
 * is needed, no special naming convention is suggested.  Regarding objects, a 
 * useful naming convention could be:
 * <ul>
 * <li> from_App_Unencrypted_Object
 * <li> to_Db_Encrypted_Object
 * <li> from_Db_Encrypted_Object
 * <li> to_App_Decrypted_Object
 * </ul>
 *  
 * <p> Methods "encrypt" and "decrypt" in this class are conveniently fault-tolerant.
 * 
 * <p> Three functions wrapping "decryption" are included, to manage implementation 
 * along db_mappers conveniently:
 * <ul>
 * <li> One for receiving and returning Optional of object.
 * <li> One for receiving and returning List of object(s).
 * <li> One for receiving and returning Page of object(s).
 * <ul> 
 * 
 * <p> Static attributes of class "CipherAesEcbPkcs5Helper" should be initialized in 
 * a CommandLineRunner (See "EncryptionInitializationCLR.java").
 * 
 * <p> Testing value for keyBase16Str: "983791D130AE8AC39E6BF5FD31C2768A" (see 
 * "CipherAesEcbPkcs5Helper.java" for more details").
 *  
 * @author Alberto Romero
 * @since 2023-09-28
 *  
 */
// @Component
public class EncryptionFunctions {
	
	// private static Logger log = LoggerFactory.getLogger(EncryptionFunctions.class);
	private static LogHelper log = new LogHelper();

	// @Value("${db.fieldEncryption.enabled}")
	// private boolean dbEnableFieldEncryptionEnabled;
	private boolean dbEnableFieldEncryptionEnabled = true;
	

	/**
	 * <p> Convenient wrapper of decryption function to manage Optional of object. 
	 * 
	 * @param <T>
	 * @param encryptedObjecOptional
	 * @return decryptedObjectOptional
	 */
	public <T extends PersonModel> Optional<T> decryptObjecOptional(Optional<T> encryptedObjecOptional) {
		if (!encryptedObjecOptional.isPresent()) {
			return encryptedObjecOptional;
		}
		T encryptedObject = encryptedObjecOptional.get();
		T decryptedObject = decryptObject(encryptedObject);
		Optional<T> decryptedObjectOptional = Optional.of(decryptedObject);
		return decryptedObjectOptional;
	}
	

	/**
	 * <p> Convenient wrapper of decryption function to manage List of objects. 
	 * 
	 * @param <T>
	 * @param encryptedObjectList
	 * @return decryptedObjectList
	 */
	public <T extends PersonModel> List<T> decryptObjectList(List<T> encryptedObjectList) {
		if (encryptedObjectList.size() == 0) {
			return encryptedObjectList;
		}
		List<T> decryptedObjectList = new ArrayList<T>();
		T decryptedObject = null; 
		for (T encryptedObject : encryptedObjectList) {
			decryptedObject = decryptObject(encryptedObject);
			decryptedObjectList.add(decryptedObject);
		}
		return decryptedObjectList;
	}
	
	
	/**
	 * <p> Convenient wrapper of decryption function to manage Page of objects. 
	 * 
	 * @param <T>
	 * @param encryptedObjectPage
	 * @return decryptedObjectPage
	 */
	public <T extends PersonModel> Page<T> decryptObjectPage(Page<T> encryptedObjectPage) {
		if (encryptedObjectPage.size() == 0) {
			return encryptedObjectPage;
		}
		// temporal encrypted object list
		List<T> tmpEncObjList = new ArrayList<T>();
		for (T object : encryptedObjectPage) {
			tmpEncObjList.add(object);
		}
		while (encryptedObjectPage.size() > 0) {
			encryptedObjectPage.remove(0);
		}
		Page<T> decryptedObjectPage = encryptedObjectPage;
		T decryptedObject = null; 
		for (T encryptedObject : tmpEncObjList) {
			decryptedObject = decryptObject(encryptedObject);
			decryptedObjectPage.add(decryptedObject);
		}
		return decryptedObjectPage;
	}


	
	/**
	 * <p> Decryption of fields with annotation "@DbFieldEncrypt". 
	 * 
	 * <p> As mentioned in class description, this function is fault-tolerant.
	 * 
	 * <p> Decryption of input-object-field is performed only if such field
	 * is in fact encrypted (decryptable), which is verified within the function.
	 * 
	 * @param <T>
	 * @param encryptedObject
	 * @return decryptedObject
	 */
	public <T extends PersonModel> T decryptObject(T encryptedObject) {

		@SuppressWarnings("unchecked")
		T decryptedObject = (T) ObjectCopyHelper.makeCopyOf(encryptedObject);
		
		// decrypt fields
		Class<?> clazz = encryptedObject.getClass();
		String fieldValue = null;
		Field[] allFields = clazz.getDeclaredFields();
		ArrayList<String> fieldNamesToDecrypt = new ArrayList<String>();
		Field field = null;
		boolean fAccessible = false;
		
		// find annotated fields
		for (Field f : allFields) {
			if (f.isAnnotationPresent(DbFieldEncrypt.class)) {
				fieldNamesToDecrypt.add(f.getName());
			}
		}
		
		for (String fieldNameToDecrypt : fieldNamesToDecrypt) {
			try {
				field = clazz.getDeclaredField(fieldNameToDecrypt);
				fAccessible = field.isAccessible();
				field.setAccessible(true);
				fieldValue = (String) field.get(encryptedObject);
				
				// FIRST ENCRYPTION PAIR INTERVENTION BEGIN
				FirstEncryptionPair fpe = getFirstEncryptionPairOf(fieldValue);
				field.set(decryptedObject, fpe.getDecrypted());
				// FIRST ENCRYPTION PAIR INTERVENTION BEGIN
				
				field.setAccessible(fAccessible);
			} catch (Exception e) {
				log.error("error: ", e);
			}
		}
		return decryptedObject;
	}
	


	/**
	 * <p> Encryption of fields with annotation "@DbFieldEncrypt".
	 * 
	 * <p> Annotation "@Size", definig parameter "max", is also necessary
	 * for this method to work correctly.  
	 * 
	 * <p> As mentioned in class description, this function is fault-tolerant.
	 *
	 * <p> Encryption of input-object-field is performed only if such field
	 * is in fact unencrypted (encryptable), which is verified within the function.
	 * 
	 * @param <T>
	 * @param unencryptedObject
	 * @return encryptedObject
	 */
	public <T extends PersonModel> T encryptObject (T unencryptedObject) {
		
		@SuppressWarnings("unchecked")
		T encryptedObject = (T) ObjectCopyHelper.makeCopyOf(unencryptedObject);
		
		// encrypt fields
		Class<?> clazz = unencryptedObject.getClass();
		String fieldValue = null;
		Field[] allFields = clazz.getDeclaredFields();
		ArrayList<String> fieldNamesToEncrypt = new ArrayList<String>();
		Field field = null;
		boolean fAccessible = false;
		Size faSize = null;
		int iSize = -1 ; 
		
		// find annotated fields
		for (Field f : allFields) {
			if (f.isAnnotationPresent(DbFieldEncrypt.class)) {
				fieldNamesToEncrypt.add(f.getName());
			}
		}

		for (String fieldNameToEncrypt : fieldNamesToEncrypt) {
			try {
				// attempt decryption on field; if decryption fails, field is unencrypted
				field = clazz.getDeclaredField(fieldNameToEncrypt); // try catch required;
				fAccessible = field.isAccessible();
				field.setAccessible(true);
				fieldValue = (String) field.get(unencryptedObject);
				if (field.isAnnotationPresent(Size.class)) {
					faSize = field.getDeclaredAnnotation(Size.class);
					iSize = faSize.max();
				}
				
				// FIRST_ENCRYPTION_PAIR INTERVENTION BEGIN
				FirstEncryptionPair fpe = getFirstEncryptionPairOf(fieldValue);
				// YML property
				if (dbEnableFieldEncryptionEnabled) {
					// while block added to manage length overflow due to encryption
					while (fpe.getEncrypted() != null && iSize > 0 && fpe.getEncrypted().length() > iSize) {
						fieldValue = fieldValue.substring(0, fieldValue.length()-1);
						fpe = getFirstEncryptionPairOf(fieldValue);
					}
					field.set(encryptedObject, fpe.getEncrypted());	
				} else {
					field.set(encryptedObject, fpe.getDecrypted());
				}
				// FIRST_ENCRYPTION_PAIR INTERVENTION END
				
				field.setAccessible(fAccessible);
			} catch (Exception e) {
				log.error("error: ", e);
			}
		}
		return encryptedObject;
	}
	
	
	
	/**
	 * 
	 * <p> A string can be encrypted several times: <br>
	 * <ul> 
	 * <li> null --- unencrypted/decrypted_1st --- encrypted_1st --- encrypted_2nd --- ... --- encrypted_nth
	 * </ul>
	 * 
	 * <p> This function finds "decrypted_1st" and "encrypted_1st" strings. 
	 * 
	 * <p> This function aids with fault-tolerant behavior of "encrypt" and "decrypt"
	 * functions.
	 * 
	 * <p> This function is also present in "CipherAesEcbPkcs5Helper"; it is 
	 * redundantly placed here for convenience.
	 * 
	 * 
	 * @param input decrypted_1st, or any encrypted_nth string.
	 * @return FirstEncryptionPair: {decrypted:"String", encrypted:"String"}.
	 * 
	 */
	public FirstEncryptionPair getFirstEncryptionPairOf(String input) {
		
		if (input == null || input.isEmpty()) {
			return new FirstEncryptionPair();
		}
		
		// variables
		final String __UNKNOWN__ = "__#@%unKn0wn!$^__";
		String encryptedPlus = null;
		String encrypted = null;
		String decrypted = null;
		
		// initialization
		encryptedPlus = __UNKNOWN__;
		encrypted = input;
		decrypted = __UNKNOWN__;
		
		// fault-tolerant catching bucle
		while (true) {

			decrypted = CipherAesEcbPkcs5Helper.decrypt(encrypted);
			
			if (decrypted != null) {
				encryptedPlus = encrypted; 
				encrypted = decrypted;
				decrypted = __UNKNOWN__;
			} else {
				decrypted = encrypted;
				encrypted = encryptedPlus;
				encryptedPlus = __UNKNOWN__;
				break;
			}
		}
		
		// calculate encrypted if value not available
		if (encrypted == null || __UNKNOWN__.equals(encrypted)) {
			encrypted = CipherAesEcbPkcs5Helper.encrypt(decrypted);
		}
		
		// return
		FirstEncryptionPair fep = new FirstEncryptionPair(decrypted, encrypted);
		return fep;
	}

}
