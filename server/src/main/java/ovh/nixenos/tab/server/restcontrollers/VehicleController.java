package ovh.nixenos.tab.server.restcontrollers;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import ovh.nixenos.tab.server.dto.vehicle.VehicleRequest;
import ovh.nixenos.tab.server.dto.vehicle.VehicleResponse;
import ovh.nixenos.tab.server.entities.Client;
import ovh.nixenos.tab.server.entities.Vehicle;
import ovh.nixenos.tab.server.entities.VehicleType;
import ovh.nixenos.tab.server.exceptions.InvalidArgumentException;
import ovh.nixenos.tab.server.services.ClientService;
import ovh.nixenos.tab.server.services.VehicleService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<VehicleResponse> getAll(
            @RequestParam(value = "clientid", required = false) Long clientId) {
        List<Vehicle> vehicles;
        if(clientId == null)
            vehicles = this.vehicleService.findAll();
        else
            vehicles = this.vehicleService.findVehiclesByClientId(clientId);
        List<VehicleResponse> vehicleList = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            VehicleResponse vehicleDtoResponse = this.modelMapper.map(vehicle, VehicleResponse.class);
            vehicleList.add(vehicleDtoResponse);
        }
        return vehicleList;
    }

    @GetMapping(value = "/{vin}")
    public VehicleResponse findByVin(@PathVariable String vin) {
        if(this.vehicleService.existsById(vin))
            return this.modelMapper.map(this.vehicleService.findByVin(vin), VehicleResponse.class);
        else
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Vehicle with vin " + vin + " does not exist!");
    }

    @PostMapping
    public void addVehicle(@RequestBody VehicleRequest vehicleDto) {
        try {
            Vehicle vehicle = this.modelMapper.map(vehicleDto, Vehicle.class);
            Client client = clientService.findById(vehicleDto.getClientId());
            vehicle.setClient(client);
            this.vehicleService.addVehicle(vehicle);
        } catch ( MappingException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getCause().getCause().getCause().getMessage());
        } catch (InvalidArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error adding new vehicle");
        }
    }

    @GetMapping(value = "/types")
    public VehicleType[] getVehicleTypes() {
        return VehicleType.values();
    }

    @PutMapping(value = "/{vin}")
    public void updateVehicle(@RequestBody VehicleRequest newVehicle,
                              @PathVariable String vin){
        if(this.vehicleService.existsById(vin)){
            try {
                Vehicle vehicle = this.vehicleService.findByVin(vin);
                vehicle.setClient(this.clientService.findById(newVehicle.getClientId()));
                vehicle.setVehicleClass(newVehicle.getVehicleClass());
            } catch (InvalidArgumentException e) {
                throw new  ResponseStatusException(
                        HttpStatus.BAD_REQUEST, e.getMessage());
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Vehicle with vin " + vin + " does not exist!");
        }
    }

}
