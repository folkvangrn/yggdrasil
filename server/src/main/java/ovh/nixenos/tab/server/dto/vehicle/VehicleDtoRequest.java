package ovh.nixenos.tab.server.dto.vehicle;

public class VehicleDtoRequest {
    private String vin;
    private String vehicleClass;
    private int clientId;

    public int getClientId() {
        return clientId;
    }
}