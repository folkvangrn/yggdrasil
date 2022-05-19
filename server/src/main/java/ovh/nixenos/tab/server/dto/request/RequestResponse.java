package ovh.nixenos.tab.server.dto.request;

import java.util.Date;

public class RequestResponse {

    private Long id;
    private String description;
    private String result;
    private String status;
    private Date dateRequest;
    private Date dateFinalized;
    private Long managerId;
    private String managerFirstName;
    private String managerLastName;

    //vehicle
    private String vehicleVin;
    private String vehicleClass;

    //client
    private Long vehicleClientId;
    private String vehicleClientCompanyName;
    private String vehicleClientFirstName;
    private String vehicleClientLastName;
    private String vehicleClientPhoneNumber;
    private String vehicleClientEmail;

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getVehicleClientId() {
        return vehicleClientId;
    }

    public void setVehicleClientId(Long vehicleClientId) {
        this.vehicleClientId = vehicleClientId;
    }

    public String getVehicleClientCompanyName() {
        return vehicleClientCompanyName;
    }

    public void setVehicleClientCompanyName(String vehicleClientCompanyName) {
        this.vehicleClientCompanyName = vehicleClientCompanyName;
    }

    public String getVehicleClientFirstName() {
        return vehicleClientFirstName;
    }

    public void setVehicleClientFirstName(String vehicleClientFirstName) {
        this.vehicleClientFirstName = vehicleClientFirstName;
    }

    public String getVehicleClientLastName() {
        return vehicleClientLastName;
    }

    public void setVehicleClientLastName(String vehicleClientLastName) {
        this.vehicleClientLastName = vehicleClientLastName;
    }

    public String getVehicleClientPhoneNumber() {
        return vehicleClientPhoneNumber;
    }

    public void setVehicleClientPhoneNumber(String vehicleClientPhoneNumber) {
        this.vehicleClientPhoneNumber = vehicleClientPhoneNumber;
    }

    public String getVehicleClientEmail() {
        return vehicleClientEmail;
    }

    public void setVehicleClientEmail(String vehicleClientEmail) {
        this.vehicleClientEmail = vehicleClientEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(Date dateRequest) {
        this.dateRequest = dateRequest;
    }

    public Date getDateFinalized() {
        return dateFinalized;
    }

    public void setDateFinalized(Date dateFinalized) {
        this.dateFinalized = dateFinalized;
    }

    public String getVehicleVin() {
        return vehicleVin;
    }

    public void setVehicleVin(String vehicleVin) {
        this.vehicleVin = vehicleVin;
    }

    public String getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public String getManagerFirstName() {
        return managerFirstName;
    }

    public void setManagerFirstName(String managerFirstName) {
        this.managerFirstName = managerFirstName;
    }

    public String getManagerLastName() {
        return managerLastName;
    }

    public void setManagerLastName(String managerLastName) {
        this.managerLastName = managerLastName;
    }
}
