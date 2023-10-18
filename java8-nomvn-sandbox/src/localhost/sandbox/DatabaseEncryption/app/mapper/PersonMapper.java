package localhost.sandbox.DatabaseEncryption.app.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import localhost.sandbox.DatabaseEncryption.app.model.PersonModel;
import localhost.sandbox.DatabaseEncryption.encryption.mapper.PersonMapperInterface;

/**
 * <p> This class is intended to simulate an ibatis mapper interface.
 * 
 * <p> This would have to extend "PersonMapperInterface", using annotation
 * "Override" on all methods.
 * 
 * @author Alberto Romero
 * @since 2023-10-18
 *
 */
public class PersonMapper implements PersonMapperInterface {

	
	/**
	 * <p> Property only for testing on Java SE.
	 * <p> This HashMap represents a database target table.
	 */
	private static Map<Long,PersonModel> personMap;
	
	
	/**
	 * <p> Method only for testing on Java SE.
	 */
	static {
		PersonModel p00 = new PersonModel();
		p00.setId(0);
		p00.setName("Alberto");
		p00.setAddress("Caracas");
		p00.setComment("A very first comment");
		
		PersonModel p01 = new PersonModel();
		p01.setId(1);
		p01.setName("Ponky");
		p01.setAddress("Merida");
		p01.setComment("Nice pet");

		PersonModel p02 = new PersonModel();
		p02.setId(2);
		p02.setName("Luisa");
		p02.setAddress("Maracay");
		p02.setComment("Have to visit sometime");
		
		personMap = new HashMap<Long,PersonModel>();
		personMap.put(0L, p00);
		personMap.put(1L, p01);
		personMap.put(2L, p02);
		
	}
	
	
	@Override
	public Optional<PersonModel> findById(long id) {
		return Optional.of(personMap.get(id));
	}
	
	
	@Override
	public int update(PersonModel person) {
		personMap.put(person.getId(), person);
		return 1;
	}

	
	/**
	 * <p> Method only for testing on Java SE.
	 */
	public static Map<Long,PersonModel> getStaticPersonMap() {
		return personMap;
	}

	
	/**
	 * <p> Method only for testing on Java SE.
	 */
	public static String staticPersonMapToString() {
		String out = "[";
		Set<Long> keySet = personMap.keySet();
		for (long id : keySet) {
			out += personMap.get(id).toString() + ",";
		}
		out = out.substring(0, out.length()-1);
		out += "]";
		return out;
	}

}
