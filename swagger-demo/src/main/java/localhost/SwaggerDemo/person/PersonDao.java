package localhost.SwaggerDemo.person;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class PersonDao {
	
	private static ArrayList<PersonPojo> userList;

	static {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		String dateInString = "2022-12-29";
		Date vaccineDate = null;
		try {
			vaccineDate = formatter.parse(dateInString);
		} catch (Exception e) {
			System.err.println("e.getMessage: " + e.getMessage() + " --- e.getCause(): " + e.getCause());
		}
		
		userList = new ArrayList<>();
		userList.add( new PersonPojo("Alberto", 40, 1.73, false,vaccineDate, "2000/10/26") );
		userList.add( new PersonPojo("Mary", 63, 1.60, false, vaccineDate, "2001/11/27") );
		userList.add( new PersonPojo("Ponky", 50, 0.40, true, vaccineDate, "2002/12/28") );
	}
	
	public ArrayList<PersonPojo> getUsers() {
		return userList;
	}
	
	public PersonPojo getUserByName(String name) {
		
		HashMap<String,PersonPojo> userMap = new HashMap<>();
		
		userList.stream().forEach( u -> {
			userMap.put(u.name, u);
		});
		
		PersonPojo user = userMap.get(name);
		
		return user;
	}
	
	public ArrayList<PersonPojo> postUser(PersonPojo user) {
		userList.add(user);
		return userList;
	}
	
	public ArrayList<PersonPojo> patchUser(PersonPojo user) {
		PersonPojo userToUpdate = null;
		for (int i = 0; i < userList.size(); i++ ) {
			if ( userList.get(i).name.equals(user.name) ) {
				userToUpdate = userList.get(i);
				userToUpdate.name = user.name;
				userToUpdate.age = user.age;
				userToUpdate.height = user.height;
				userToUpdate.didService = user.didService;
				userToUpdate.vaccineDate = user.vaccineDate;
				userToUpdate.setBirthdayStr(user.birthdayStr);
			}
		}
		return userList;
	}
	
	public ArrayList<PersonPojo> deleteUser(String name) {
		for (int i = 0; i < userList.size() ; i ++) {
			if ( userList.get(i).name.equals(name) ) {
				userList.remove(i);
			}
		}
		return userList;
	}
}
