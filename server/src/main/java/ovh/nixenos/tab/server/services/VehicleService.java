package ovh.nixenos.tab.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ovh.nixenos.tab.server.dto.vehicle.VehicleDtoRequest;
import ovh.nixenos.tab.server.entities.Vehicle;
import ovh.nixenos.tab.server.entities.VehicleType;
import ovh.nixenos.tab.server.repositories.VehicleRepository;

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

    public Iterable<Vehicle> findAll() {
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
}
