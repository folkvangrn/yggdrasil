package ovh.nixenos.tab.server.restcontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import ovh.nixenos.tab.server.dto.vehicle.VehicleDtoRequest;
import ovh.nixenos.tab.server.dto.vehicle.VehicleDtoResponse;
import ovh.nixenos.tab.server.entities.Client;
import ovh.nixenos.tab.server.entities.Vehicle;
import ovh.nixenos.tab.server.entities.VehicleType;
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
    public List<VehicleDtoResponse> getAll(
            @RequestParam(value = "clientid", required = false) Long clientId) {
        List<Vehicle> vehicles;
        if(clientId == null)
            vehicles = this.vehicleService.findAll();
        else
            vehicles = this.vehicleService.findVehiclesByClientId(clientId);
        List<VehicleDtoResponse> vehicleList = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            VehicleDtoResponse vehicleDtoResponse = this.modelMapper.map(vehicle, VehicleDtoResponse.class);
            vehicleList.add(vehicleDtoResponse);
        }
        return vehicleList;
    }

    @GetMapping(value = "/{vin}")
    public VehicleDtoResponse findByVin(@PathVariable String vin) {
        if(this.vehicleService.existsById(vin))
            return this.modelMapper.map(this.vehicleService.findByVin(vin), VehicleDtoResponse.class);
        else
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Vehicle with vin " + vin + " does not exist!");
    }

    @PostMapping
    public void addVehicle(@RequestBody VehicleDtoRequest vehicleDto) {
        try {
            Vehicle vehicle = this.modelMapper.map(vehicleDto, Vehicle.class);
            Client client = clientService.findById(vehicleDto.getClientId());
            vehicle.setClient(client);
            this.vehicleService.addVehicle(vehicle);
        }
//          catch ( jakisCustomException? e ){
//              throw new ResponseStatusException(
//              HttpStatus.BAD_REQUEST, e.getMessage());
//          }
        catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error adding new vehicle");
        }
    }

    @GetMapping(value = "/types")
    public VehicleType[] getVehicleTypes() {
        return VehicleType.values();
    }

}
