package localhost.SwaggerDemo.person;

// look at http://localhost:{port}/{context}/swager-ui.html

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	
	@Autowired
	private PersonDao userDao;
	
	
	@GetMapping ("persons")
	public ArrayList<PersonPojo> getUsers () {
		return userDao.getUsers();
	}
	
	@GetMapping("persons/{name}")
	public PersonPojo getUserByName(@PathVariable String name) {
		return userDao.getUserByName(name);
	}
	
	@PostMapping("persons")
	public ArrayList<PersonPojo> postUser(@RequestBody PersonPojo user) {
		return userDao.postUser(user);
	}
	
	@PatchMapping("persons")
	public ArrayList<PersonPojo> patchUser(@RequestBody PersonPojo user) {
		return userDao.patchUser(user);
	}
	
	@DeleteMapping("persons/{name}")
	public ArrayList<PersonPojo> deleteUser(@PathVariable String name) {
		return userDao.deleteUser(name);
	}
	

}
