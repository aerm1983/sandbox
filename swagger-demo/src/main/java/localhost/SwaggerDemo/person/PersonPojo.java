package localhost.SwaggerDemo.person;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PersonPojo {
	
	public String name;
	public Integer age;
	public Double height;
	public Boolean didService;
	public Date vaccineDate;
	public String birthdayStr;
	@JsonIgnore
	public Date birthdayLocal;
	@JsonIgnore
	public Date birthdayZoned;
	
	public PersonPojo (String name, Integer age, Double height, Boolean didService, Date vaccineDate, String birthdayStr) {
		this.name = name;
		this.age = age;
		this.height = height;
		this.didService = didService;
		this.vaccineDate = vaccineDate;
		this.setBirthdayStr(birthdayStr);
	}
	
	public void setBirthdayStr(String birthdayStr){
		// string
		this.birthdayStr = birthdayStr;
		// local
		DateTimeFormatter localFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.parse(birthdayStr + " 00:00:00", localFormatter);
		this.birthdayLocal = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		// zoned
		DateTimeFormatter zonedFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss z");
		ZonedDateTime zonedDateTime = ZonedDateTime.parse(birthdayStr + " 00:00:00 America/Caracas", zonedFormatter); // UTC/Greenwich, Europe/Madrid, America/Caracas US/Eastern,  US/Central, US/Pacific     
		this.birthdayZoned = Date.from(zonedDateTime.toInstant());
		return;
	}
}
