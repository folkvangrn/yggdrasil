package ovh.nixenos.tab.server.dto.request;

public class CreateRequestRequest {

    private String description;
    private String result;
    private String status;

    //vehicle
    private Long vin;
    private String vehicleClass;
    //client
    private Long id;

    //manager
    private Long managerId;

}
