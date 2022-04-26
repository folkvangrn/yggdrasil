package ovh.nixenos.tab.server.dto.request;

import java.util.Date;

public class FindRequestResponse {

    private Long id;

    private String description;

    private String result;

    private String status;

    private Date dateRequest;

    private Date dateFinalized;

    //vehicle
    private Long vin;
    private String vehicleClass;

    //client
    private Long client_id;
    private String companyName;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    private String manager_username;

}
