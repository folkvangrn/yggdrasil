package ovh.nixenos.tab.server.dto.vehicle;

public class VehicleDtoRequest {
    private String vin;
    private String vehicleClass;
    private int clientId;

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

}