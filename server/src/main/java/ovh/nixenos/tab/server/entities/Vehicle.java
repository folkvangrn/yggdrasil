package ovh.nixenos.tab.server.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "VEHICLES")
public class Vehicle {

    @Id
    private String vin;

    @Column
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleClass;

    @ManyToOne()
    @JoinColumn(name ="client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "vehicle")
    private List<Request> requests;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public VehicleType getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(VehicleType vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}
