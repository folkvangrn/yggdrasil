package ovh.nixenos.tab.server.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import ovh.nixenos.tab.server.exceptions.InvalidArgumentException;
import ovh.nixenos.tab.server.users.User;

@Entity
@Table(name = "REQUESTS")
public class Request {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 500, nullable = false)
    private String description;

    @Column(length = 500)
    private String result;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRequest;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFinalized;

    @ManyToOne()
    @JoinColumn(name ="vehicle_vin", nullable = false)
    private Vehicle vehicle;

    @OneToMany(mappedBy = "request")
    private List<Activity> activities;

    @ManyToOne
    @JoinColumn(name ="presonnel_table_id", nullable = false)
    private User manager;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws InvalidArgumentException {
        if (description == null)
            throw new InvalidArgumentException("Description cannot be null");
        if (description.isBlank())
            throw new InvalidArgumentException("Description cannot be empty");
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) throws InvalidArgumentException {
        this.result = result;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(Date dateRequest) throws InvalidArgumentException {
        if(dateRequest.equals(null))
            throw new InvalidArgumentException("Date cannot be null");
        this.dateRequest = dateRequest;
    }

    public Date getDateFinalized() {
        return dateFinalized;
    }

    public void setDateFinalized(Date dateFinalized) throws InvalidArgumentException {
        if(dateFinalized.before(new Date()))
            throw new InvalidArgumentException("Date finalized has to be in past");
        this.dateFinalized = dateFinalized;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) throws InvalidArgumentException {
        if(vehicle.equals(null))
            throw new InvalidArgumentException("Vehicle cannot be null");
        this.vehicle = vehicle;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) throws InvalidArgumentException {
        if(activities.isEmpty())
            throw new InvalidArgumentException("Activities list cannot be null");
        this.activities = activities;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) throws InvalidArgumentException {
        if(manager.equals(null))
            throw new InvalidArgumentException("Manager cannot be null");
        this.manager = manager;
    }
}