package localhost.udemy01;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



@Entity
public class UserPojo {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	private Date birthDate;
	
	UserPojo() {
	}
	
	UserPojo( Integer id, String name, Date birthDate ) {
		this.id = id ;
		this.name = name ;
		this.birthDate = birthDate ; 
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	
	
}
