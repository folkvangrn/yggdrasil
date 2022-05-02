package ovh.nixenos.tab.server.restcontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import ovh.nixenos.tab.server.dto.vehicle.VehicleDtoRequest;
import ovh.nixenos.tab.server.entities.Vehicle;
import ovh.nixenos.tab.server.services.VehicleService;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

        @Autowired
        private VehicleService vehicleService;

        // @Autowired
        // private ClientService clientService;

        @Autowired
        private ModelMapper modelMapper;

        @GetMapping
        public Iterable<Vehicle> getAll() {
                return this.vehicleService.findAll();
        }

        @GetMapping(value = "/{id}")
        public Vehicle findByVin(@PathVariable String vin) {
                return this.vehicleService.findByVin(vin);
        }

        @PostMapping
        public void addVehicle(@RequestBody VehicleDtoRequest vehicleDto) {
                Vehicle vehicle = this.modelMapper.map(vehicleDto, Vehicle.class);

                //Client client = clientService.findById(vehicleDto.getClientId());
                //vehicle.setClient(client);

                this.vehicleService.addVehicle(vehicle);
        }
}
