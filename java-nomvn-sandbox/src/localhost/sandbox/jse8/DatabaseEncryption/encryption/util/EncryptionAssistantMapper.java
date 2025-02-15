package localhost.sandbox.jse8.DatabaseEncryption.encryption.util;

import java.util.ArrayList;
import java.util.List;

import localhost.sandbox.jse8.DatabaseEncryption.app.mapper.PersonMapper;

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

	
	// @Select("SELECT MAX(${tableFieldIdName}) FROM ${tableName}")
	// Long findMaxId(String tableName, String tableFieldIdName);
	/**
	 * <p> Method implementation only for testing on Java SE.
	 */
	public Long findMaxId(String tableName, String tableFieldIdName) {
		return (long) PersonMapper.getStaticRawDbPersonListForJSETest().size() - 1;
	}

	
	// @Select("SELECT COUNT(${tableFieldIdName}) FROM ${tableName}")
	// Long getCountId(String tableName, String tableFieldIdName);
	/**
	 * <p> Method implementation only for testing on Java SE.
	 */
	public Long getCountId(String tableName, String tableFieldIdName) {
		return (long) PersonMapper.getStaticRawDbPersonListForJSETest().size();
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
