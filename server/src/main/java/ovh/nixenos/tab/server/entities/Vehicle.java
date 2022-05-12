package ovh.nixenos.tab.server.entities;

import ovh.nixenos.tab.server.exceptions.InvalidArgumentException;

import javax.persistence.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void setVin(String vin) throws InvalidArgumentException {
        String regex = "^[a-zA-Z0-9]+$";
        Matcher matcher = Pattern.compile("^[a-zA-Z0-9]+$").matcher(vin);
        if(!matcher.matches())
            throw new InvalidArgumentException("Invalid vin number " + vin);
        if(vin.isBlank() || vin.isEmpty())
            throw new InvalidArgumentException("Vin number cannot be null");
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

    public void setClient(Client client) throws InvalidArgumentException {
        if(client == null)
            throw new InvalidArgumentException("Client doesn't exist!");
        this.client = client;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) throws InvalidArgumentException {
        if(requests == null)
            throw new InvalidArgumentException("Request cannot be null");
        this.requests = requests;
    }
}
