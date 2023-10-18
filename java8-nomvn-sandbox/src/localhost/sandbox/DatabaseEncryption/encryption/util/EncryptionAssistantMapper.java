package localhost.sandbox.DatabaseEncryption.encryption.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import localhost.sandbox.DatabaseEncryption.app.mapper.PersonMapper;
import localhost.sandbox.DatabaseEncryption.app.model.PersonModel;

// import org.apache.ibatis.annotations.Mapper;
// import org.apache.ibatis.annotations.Param;
// import org.apache.ibatis.annotations.Select;

/**
 * <p> This class is originally intended to function as an ibatis interface.
 * 
 * <p> Implementation as class is only for testing purposes on Java SE.
 * 
 * @author Alberto Romero
 * @since 2023-10-18
 *
 */
// @Mapper
// public interface EncryptionAssistantMapper {
public class EncryptionAssistantMapper {

	
	/**
	 * <p> Property only for testing on Java SE.
	 */
	private static Map<Long, PersonModel> personMap = PersonMapper.getStaticPersonMap();
	
	
	// @Select("SELECT MAX(${tableFieldIdName}) FROM ${tableName}")
	// Long findMaxId(String tableName, String tableFieldIdName);
	/**
	 * <p> Method implementation only for testing on Java SE.
	 */
	public Long findMaxId(String tableName, String tableFieldIdName) {
		Set<Long> idSet = personMap.keySet();
		Optional<Long> maxIdOpt = idSet.stream().max(Comparator.naturalOrder());
		if (maxIdOpt.isPresent()) {
			return maxIdOpt.get();
		} else {
			return 0L;
		}
	}

	
	// @Select("SELECT COUNT(${tableFieldIdName}) FROM ${tableName}")
	// Long getCountId(String tableName, String tableFieldIdName);
	/**
	 * <p> Method implementation only for testing on Java SE.
	 */
	public Long getCountId(String tableName, String tableFieldIdName) {
		return (long) personMap.size();
	}
	
	
	// @Deprecated
	// @Select( "SELECT t.* " 
		// 	+ "FROM ( "
		//	+ "SELECT ${tableFieldIdName} as `field_id_value`, ${fieldName} as `field_value`, LENGTH(${fieldName}) AS `field_length` "
		//	+ "FROM ${tableName} "
		//	+ ") AS `t` "
		//	+ "WHERE t.field_length >= #{refSearchLength} "
		//	+ "ORDER BY t.field_length DESC" )
	// List<FieldDescriptorValues> findFieldLengths( /* @Param("tableName") */ String tableName, /* @Param("tableFieldIdName") */ String tableFieldIdName, /* @Param("fieldName") */ String fieldName, /* @Param("refSearchLength") */ Integer refSearchLength);
	public List<FieldDescriptorValues> findFieldLengths( String tableName, String tableFieldIdName, String fieldName, Integer refSearchLength) {
		return new ArrayList<FieldDescriptorValues>();
	}

}
