package ovh.nixenos.tab.server.restcontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import ovh.nixenos.tab.server.dto.vehicle.VehicleDtoRequest;
import ovh.nixenos.tab.server.dto.vehicle.VehicleDtoResponse;
import ovh.nixenos.tab.server.entities.Vehicle;
import ovh.nixenos.tab.server.services.VehicleService;

import java.util.ArrayList;
import java.util.List;

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
        public List<VehicleDtoResponse> getAll() {
                Iterable<Vehicle> vehiclesIterable = this.vehicleService.findAll();
                List<VehicleDtoResponse> vehicleList = new ArrayList<>();
                for(Vehicle vehicle: vehiclesIterable){
                        VehicleDtoResponse vehicleDtoResponse = this.modelMapper.map(vehicle, VehicleDtoResponse.class);
                        vehicleList.add(vehicleDtoResponse);
                }
                return vehicleList;
        }

        @GetMapping(value = "/{id}")
        public VehicleDtoResponse findByVin(@PathVariable String vin) {
                return this.modelMapper.map(this.vehicleService.findByVin(vin), VehicleDtoResponse.class);
        }


        @PostMapping
        public void addVehicle(@RequestBody VehicleDtoRequest vehicleDto) {
                Vehicle vehicle = this.modelMapper.map(vehicleDto, Vehicle.class);

                //Client client = clientService.findById(vehicleDto.getClientId());
                //vehicle.setClient(client);

                this.vehicleService.addVehicle(vehicle);
        }
}
