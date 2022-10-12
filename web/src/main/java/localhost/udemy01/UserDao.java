package localhost.udemy01;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.springframework.stereotype.Component;

// @Component
public class UserDao {
	
	private static ArrayList<UserPojo> userList = new ArrayList<>();
	private static int userListCount = 3;
	
	static {
		userList.add( new UserPojo( 1, "Alberto", new Date() ) );
		userList.add( new UserPojo( 2, "Mary", new Date() ) );
		userList.add( new UserPojo( 2, "Ponky", new Date() ) );
	}
	
	public ArrayList<UserPojo> findAll() {
		return userList;
	}
	
	
	
	public UserPojo findOne( int id ) {
		for (UserPojo user : userList) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	
	
	public UserPojo save( UserPojo user ) {
		if ( user.getId() == null ) {
			user.setId( ++userListCount );
		}
		userList.add(user);
		return user ;
	}


	public UserPojo deleteById( int id ) {
		Iterator<UserPojo> iterator = userList.iterator();
		while (iterator.hasNext()) {
			UserPojo user = iterator.next();
			if (user.getId() == id) {
				iterator.remove();
				return user;
			}
		}
		return null;
	}

	
}
