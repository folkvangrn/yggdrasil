package ovh.nixenos.tab.server.dto.vehicle;

public class VehicleResponse {
    private String vin;
    private String vehicleClass;
    private Long clientId;
    private String clientFirstName;
    private String clientLastName;

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getVin() {
        return vin;
    }

    public String getVehicleClass() {
        return vehicleClass;
    }

    public Long getClientId() {
        return clientId;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }
//^^ maybe info about client will be useful?
    //private List<int> listRequests; -> I'm not sure if we need this - maybe no?
}
