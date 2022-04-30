package ovh.nixenos.tab.server.dto.vehicle;

public class VehicleDtoRequest {
    private Long vin;
    private String vehicleClass;

    // Settery chyba nie powinny być publiczne w DTO, ale to do przekminienia

    public Long getVin() {
        return vin;
    }

    public void setVin(Long vin) {
        this.vin = vin;
    }

    public String getvehicleClass() {
        return vehicleClass;
    }

    public void setvehicleClass(String id) {
        this.vehicleClass = vehicleClass;
    }
}