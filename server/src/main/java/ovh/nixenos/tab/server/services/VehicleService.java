package ovh.nixenos.tab.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ovh.nixenos.tab.server.entities.Vehicle;
import ovh.nixenos.tab.server.repositories.VehicleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle findByVin(String vehicleVin) {
        Optional<Vehicle> result = vehicleRepository.findByVin(vehicleVin);
        if (result.isPresent())
            return result.get();
        else
            return null;
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public boolean existsById(final String id){
        if(vehicleRepository.existsById(id))
            return true;
        else
            return false;
    }

    public List<Vehicle> findVehiclesByClientId(Long id){
        return this.vehicleRepository.findVehiclesByClient_Id(id);
    }

    public void save(Vehicle vehicle) {this.vehicleRepository.save(vehicle);}
}
