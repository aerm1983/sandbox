package localhost.SwaggerDemo.vehicle;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class VehicleDao {
	
	private static ArrayList<VehiclePojo> vehicleList;

	static {
		vehicleList = new ArrayList<>();
		vehicleList.add( new VehiclePojo("Chevrolet", 2020) );
		vehicleList.add( new VehiclePojo("Hyundai", 2021) );
		vehicleList.add( new VehiclePojo("Volkswagen", 2022) );
	}
	
	public ArrayList<VehiclePojo> getVehicles() {
		return vehicleList;
	}
	
	public VehiclePojo getVehicleByName(String name) {
		
		HashMap<String,VehiclePojo> vehicleMap = new HashMap<>();
		
		vehicleList.stream().forEach( u -> {
			vehicleMap.put(u.name, u);
		});
		
		VehiclePojo vehicle = vehicleMap.get(name);
		
		return vehicle;
	}
	
	public ArrayList<VehiclePojo> postVehicle(VehiclePojo vehicle) {
		vehicleList.add(vehicle);
		return vehicleList;
	}
	
	public ArrayList<VehiclePojo> patchVehicle(VehiclePojo vehicle) {
		VehiclePojo vehicleToUpdate = null;
		for (int i = 0; i < vehicleList.size(); i++ ) {
			if ( vehicleList.get(i).name.equals(vehicle.name) ) {
				vehicleToUpdate = vehicleList.get(i);
				vehicleToUpdate.name = vehicle.name;
				vehicleToUpdate.year = vehicle.year;
			}
		}
		return vehicleList;
	}
	
	public ArrayList<VehiclePojo> deleteVehicle(String name) {
		for (int i = 0; i < vehicleList.size() ; i ++) {
			if ( vehicleList.get(i).name.equals(name) ) {
				vehicleList.remove(i);
			}
		}
		return vehicleList;
	}
}
