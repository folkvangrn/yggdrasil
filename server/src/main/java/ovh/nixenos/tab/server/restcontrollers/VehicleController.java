package ovh.nixenos.tab.server.restcontrollers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import ovh.nixenos.tab.server.dto.vehicle.VehicleDtoRequest;
import ovh.nixenos.tab.server.entities.Vehicle;
import ovh.nixenos.tab.server.services.VehicleService;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

        // @Autowired
        private VehicleService vehicleService;

        @GetMapping
        public Iterable<Vehicle> getAll(){
                return this.vehicleService.findAll();
        }

        @GetMapping(value = "/{id}")
        public Vehicle findById(@PathVariable Long id){
                return this.vehicleService.findById(id);
        }

        @PostMapping
        public void addVehicle(@RequestBody VehicleDtoRequest vehicle){
                this.vehicleService.addVehicle(vehicle);
        }
}
