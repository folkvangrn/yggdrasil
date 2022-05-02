package ovh.nixenos.tab.server.repositories;

import org.springframework.data.repository.CrudRepository;
import ovh.nixenos.tab.server.entities.Vehicle;

import java.util.Optional;

public interface IVehicleRepository extends CrudRepository<Vehicle, String> {
    Optional<Vehicle> findByVin(Long vehicleVin);

    Iterable<Vehicle> findAll();
}
