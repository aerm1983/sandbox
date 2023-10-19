package localhost.sandbox.DatabaseEncryption.encryption.mapper;

import java.util.List;
import java.util.Optional;

import localhost.helper.Page;
import localhost.sandbox.DatabaseEncryption.app.model.PersonModel;

/**
 * <p> This interface ensures PersonMapper and PersonMapperWrapper have the
 * same methods, and thus are inter-exchangeable within the application.
 * 
 * @author Alberto Romero
 * @since 2023-10-18
 *
 */
public interface PersonMapperInterface {
	
	public int insert(PersonModel person);
	
	public Optional<PersonModel> findById(long id);
	
	public List<PersonModel> findAll();
	
	public Page<PersonModel> findAllPage();
	
	public int update(PersonModel person);

}
