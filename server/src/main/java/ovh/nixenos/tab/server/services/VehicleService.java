package ovh.nixenos.tab.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import ovh.nixenos.tab.server.dto.vehicle.VehicleDtoRequest;
import ovh.nixenos.tab.server.entities.Vehicle;
import ovh.nixenos.tab.server.entities.VehicleType;
import ovh.nixenos.tab.server.repositories.IVehicleRepository;

import java.util.Optional;

public class VehicleService {
    @Autowired
    private IVehicleRepository vehicleRepository;

    public Vehicle findByVin(Long vehicleVin) {
        Optional<Vehicle> result = vehicleRepository.findByVin(vehicleVin);
        if (result.isPresent())
            return result.get();
        else
            return null;
    }

    public Iterable<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public void addVehicle(VehicleDtoRequest vehicleDto) {

        Vehicle vehicle = new Vehicle();
        vehicle.setVin(vehicleDto.getVin());
        vehicle.setVehicleClass(VehicleType.valueOf(vehicleDto.getvehicleClass()));

        vehicleRepository.save(vehicle);
    }
}
