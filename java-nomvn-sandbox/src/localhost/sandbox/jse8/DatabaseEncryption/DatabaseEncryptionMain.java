package localhost.sandbox.jse8.DatabaseEncryption;

import localhost.sandbox.jse8.A0Helper.LogHelper;
import localhost.sandbox.jse8.DatabaseEncryption.app.mapper.PersonMapper;
import localhost.sandbox.jse8.DatabaseEncryption.app.model.PersonModel;
import localhost.sandbox.jse8.DatabaseEncryption.encryption.mapper.PersonMapperWrapper;
import localhost.sandbox.jse8.DatabaseEncryption.encryption.util.EncryptionInitializationCLR;

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
		log.info("---- PART ONE: rawDbPersonList, unencrypted only --- ");
		log.info("rawDbPersonList, unencrypted: {}", PersonMapper.getStaticRawDbPersonListForJSETest().toString());
		
		
		
		// execute CommandLineRunner (CLR), encryption enabled
		log.info("");
		log.info("---- PART TWO: run CLR, table-transform with encryption enabled, print rawDbPersonList encrypted, wrapper methods --- ");
		EncryptionInitializationCLR eic = new EncryptionInitializationCLR();
		personMapperWrapper.getEncryptionFunctionsForJSETest().initializeForJSETest(true); // only for testing on java SE
		eic.initializeForJSETest(true); // only for testing on java SE
		try {
			eic.run((String[]) null);
		} catch (Exception e) {
			log.error("error: {}", e);
		}
		// check rawDbPersonList and wrapper methods, encryption enabled
		PersonModel p = null;
		p = new PersonModel();
		p.setId( PersonMapper.getStaticRawDbPersonListForJSETest().size() );
		p.setName("AddedLuis Constancio Hernandez Hamurabibi");
		p.setAddress("Maracay, Sector Delicias, Av Panamericana, Esquina 2, No 07");
		p.setComment("Have to visit sometime");
		personMapperWrapper.insert(p);
		log.info("rawDbPersonList, encrypted: {}", PersonMapper.getStaticRawDbPersonListForJSETest().toString());
		log.info("personMapperWrapper.findById(0): {}", personMapperWrapper.findById(0).get().toString());
		log.info("personMapperWrapper.findAll(): {}", personMapperWrapper.findAll().toString());
		log.info("personMapperWrapper.findAllPage(): {}", personMapperWrapper.findAllPage().toString());
		
		
		
		// execute CommandLineRunner (CLR), encryption disabled
		log.info("");
		log.info("---- PART THREE: run CLR, table-transform with encryption disabled, print rawDbPersonList decrypted, wrapper methods --- ");
		personMapperWrapper.getEncryptionFunctionsForJSETest().initializeForJSETest(false); // only for testing on java SE
		eic.initializeForJSETest(false); // only for testing on java SE
		try {
			eic.run((String[]) null);
		} catch (Exception e) {
			log.error("error: {}", e);
		}
		// check rawDbPersonList and wrapper methods, encryption disabled
		p = null;
		p = new PersonModel();
		p.setId( PersonMapper.getStaticRawDbPersonListForJSETest().size() );
		p.setName("AddedClara Jimena Quevedo De Los Reyes");
		p.setAddress("Coro, Sector Lomas de Medano, Avenida El Fuerte, No 28");
		p.setComment("Beautiful medanos");
		personMapperWrapper.insert(p);
		log.info("rawDbPersonList, encrypted: {}", PersonMapper.getStaticRawDbPersonListForJSETest().toString());
		log.info("personMapperWrapper.findById(0): {}", personMapperWrapper.findById(0).get().toString());
		log.info("personMapperWrapper.findAll(): {}", personMapperWrapper.findAll().toString());
		log.info("personMapperWrapper.findAllPage(): {}", personMapperWrapper.findAllPage().toString());

	
		
		// end
		return;
	}

}
