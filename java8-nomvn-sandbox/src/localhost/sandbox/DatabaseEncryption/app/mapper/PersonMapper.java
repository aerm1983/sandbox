package localhost.sandbox.DatabaseEncryption.app.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import localhost.helper.Page;
// import com.github.pagehelper.Page;

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
	 * 
	 * <p> This List represents a database target table.
	 * 
	 * <p> For simplification sake, array-index should be equal 
	 * to PersonModel.id.
	 * 
	 * <p> Under close inspection, it's possible to realize that this
	 * ArrayList does not emulate perfectly a database table, but for
	 * encryption simulation purposes, it's close / acceptable enough,
	 * as long as:
	 * <ul>
	 * <li> no element is removed
	 * <li> there are no "jumps" between indexes
	 * </ul>
	 * 
	 */
	private static ArrayList<PersonModel> rawDbPersonList = new ArrayList<>();
	
	
	/**
	 * <p> Method only for testing on Java SE.
	 */
	static {
		
		PersonModel p = null;
		
		p = new PersonModel();
		p.setId( rawDbPersonList.size() );
		p.setName("Alberto Jose Gonzalez Pacheco");
		p.setAddress("Caracas, Sector La Urbe, Calle La Rue, No 12");
		p.setComment("A very first comment");
		rawDbPersonList.add(p);
		
		// p = new PersonModel();
		// p.setId( rawDbPersonList.size() );
		// p.setName("Ponky Straffer Schavosky Paetraeus");
		// p.setAddress("Merida, Sector Fielding, Av Americana, No 09");
		// p.setComment("Nice pet");
		// rawDbPersonList.add(p);
		
	}
	
	
	/**
	 * <p> Method only for testing on Java SE.
	 */
	public static ArrayList<PersonModel> getStaticRawDbPersonListForJSETest() {
		return rawDbPersonList;
	}
	

	@Override
	public int insert(PersonModel person) {
		person.setId( rawDbPersonList.size() );
		rawDbPersonList.add(person);
		return 1;
	}
	
	
	@Override
	public Optional<PersonModel> findById(long id) {
		return Optional.of(rawDbPersonList.get((int) id));
	}
	
	
	@Override
	public List<PersonModel> findAll() {
		return rawDbPersonList;
	}
	
	
	@Override
	public Page<PersonModel> findAllPage() {
		Page<PersonModel> page = new Page<>();
		for (PersonModel person : rawDbPersonList) {
			page.add(person);
		}
		return page;
	}
	
	
	@Override
	public int update(PersonModel person) {
		rawDbPersonList.set((int) person.getId(), person);
		return 1;
	}

}
