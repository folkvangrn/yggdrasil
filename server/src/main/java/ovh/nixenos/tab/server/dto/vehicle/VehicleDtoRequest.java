package ovh.nixenos.tab.server.dto.vehicle;

import ovh.nixenos.tab.server.entities.VehicleType;

public class VehicleDtoRequest {
    private String vin;
    private VehicleType vehicleClass;
    private Long clientId;

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setVehicleClass(VehicleType vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getVin() {
        return vin;
    }

    public VehicleType getVehicleClass() {
        return vehicleClass;
    }

    public Long getClientId() {
        return clientId;
    }
}