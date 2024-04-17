package localhost.test.PmRaceCondition.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import localhost.test.PmRaceCondition.model.PublishErrorModel;

@Mapper
public interface PublishErrorMapper {

	@Select("SELECT id, product_site_id, origin_uuid, error_code, error_message, line_number, marketplace FROM publish_error")
	List<PublishErrorModel> getAll();

	@Select("SELECT id, product_site_id, origin_uuid, error_code, error_message, line_number, marketplace FROM publish_error WHERE product_site_id = #{productSiteId}")
	List<PublishErrorModel> findByProductSite(Long productSiteId);

	@Select("SELECT id, product_site_id, origin_uuid, error_code, error_message, line_number, marketplace FROM publish_error WHERE origin_uuid = #{originUuid} ORDER BY line_number")
	//	@Results(value = {
	//			@Result(property = "id", column = "id", id = true),
	//			@Result(property = "productSite", column = "product_site_id", javaType = ProductSite.class, one = @One(select = "com.domobility.middleware.productmanagement.persistence.mapper.ProductSiteMapper.findByIdWithProduct")),
	//			@Result(property = "productSiteId", column = "product_site_id")
	//	})
	List<PublishErrorModel> findByOriginUuid(String originUuid);

	@Select("SELECT pe.id, pe.product_site_id, pe.origin_uuid, pe.error_code, pe.error_message, pe.line_number, pe.marketplace FROM publish_error pe INNER JOIN product_site ps ON ps.id=pe.product_site_id INNER JOIN product p ON p.id=ps.product_id WHERE p.product_sku = #{productSku} and p.condition_id = #{conditionId} and ps.site_id = #{siteId} and p.store_id = #{storeId} and pe.origin_uuid = #{originUuid}")
	List<PublishErrorModel> findByProductSiteAndUuid(String productSku, String conditionId, String siteId, String storeId, String originUuid);

	@Select("SELECT id, product_site_id, origin_uuid, error_code, error_message, line_number, marketplace FROM publish_error WHERE id = #{id}")
	Optional<PublishErrorModel> findById(Long id);

	@Insert("INSERT INTO publish_error (origin_uuid, product_site_id, error_code, error_message, line_number, marketplace, created_at, updated_at) "
			+ "VALUES (#{originUuid}, #{productSiteId}, #{errorCode}, #{errorMessage}, #{lineNumber}, #{marketplace}, NOW(), NOW())")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	Integer save(PublishErrorModel error);

	@Update("UPDATE publish_error set updated_at = NOW() WHERE id = #{id}")
	Integer update(PublishErrorModel error);

	@Delete("DELETE FROM publish_error where id = #{id}")
	Integer delete(Long id);

	@Select("SELECT SLEEP(#{secs})")
	Long sleep(int secs);

}
