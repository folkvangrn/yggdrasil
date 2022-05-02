package ovh.nixenos.tab.server.repositories;

import org.springframework.data.repository.CrudRepository;
import ovh.nixenos.tab.server.entities.Vehicle;

import java.util.Optional;

public interface VehicleRepository extends CrudRepository<Vehicle, String> {
    Optional<Vehicle> findByVin(String vehicleVin);

    Iterable<Vehicle> findAll();
}
