package localhost.SwaggerDemo.vehicle;

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
public class VehicleController {
	
	@Autowired
	private VehicleDao vehicleDao;
	
	
	@GetMapping ("vehicles")
	public ArrayList<VehiclePojo> getUsers () {
		return vehicleDao.getVehicles();
	}
	
	@GetMapping("vehicles/{name}")
	public VehiclePojo getUserByName(@PathVariable String name) {
		return vehicleDao.getVehicleByName(name);
	}
	
	@PostMapping("vehicles")
	public ArrayList<VehiclePojo> postUser(@RequestBody VehiclePojo vehicle) {
		return vehicleDao.postVehicle(vehicle);
	}
	
	@PatchMapping("vehicles")
	public ArrayList<VehiclePojo> patchUser(@RequestBody VehiclePojo vehicle) {
		return vehicleDao.patchVehicle(vehicle);
	}
	
	@DeleteMapping("vehicles/{name}")
	public ArrayList<VehiclePojo> deleteUser(@PathVariable String name) {
		return vehicleDao.deleteVehicle(name);
	}
	

}
