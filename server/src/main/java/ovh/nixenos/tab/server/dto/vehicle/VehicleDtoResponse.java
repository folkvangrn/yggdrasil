package ovh.nixenos.tab.server.dto.vehicle;

public class VehicleDtoResponse {
    private String vin;
    private String vehicleClass;
    private int clientId;
    private String clientFirstName;
    private String clientLastName;
    //^^ maybe info about client will be useful?
    //private List<int> listRequests; -> I'm not sure if we need this - maybe no?
}
