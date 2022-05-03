package ovh.nixenos.tab.server.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
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

    public void setDateRequest(Date dateRequest) {
        this.dateRequest = dateRequest;
    }

    public Date getDateFinalized() {
        return dateFinalized;
    }

    public void setDateFinalized(Date dateFinalized) {
        this.dateFinalized = dateFinalized;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }
}