package localhost.test.PmRaceCondition.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PublishErrorModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String originUuid;
	private Long productSiteId;
	private String errorCode;
	private String errorMessage;
	private Integer lineNumber;
	private String marketplace;

	//	private ProductSite productSite;
}
