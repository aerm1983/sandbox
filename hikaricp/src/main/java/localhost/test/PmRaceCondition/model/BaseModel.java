package localhost.test.PmRaceCondition.model;

import java.io.Serializable;
import java.util.Date;

// import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	private Long id;

	//	@JsonIgnore
	private Date createdAt;

	//	@JsonIgnore
	private Date updatedAt;
}
