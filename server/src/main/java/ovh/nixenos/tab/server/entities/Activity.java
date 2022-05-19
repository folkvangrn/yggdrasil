package ovh.nixenos.tab.server.entities;

import javax.persistence.*;
import java.util.Date;

import ovh.nixenos.tab.server.exceptions.InvalidArgumentException;
import ovh.nixenos.tab.server.users.User;

@Entity
@Table(name = "ACTIVITIES")
public class Activity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private Long sequenceNumber;

    @Column(length = 500, nullable=false)
    private String description;

    @Column(length = 500)
    private String result;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRequested;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateClosed;

    @ManyToOne// ? (cascade = CascadeType.ALL)
    @JoinColumn(name ="request_id", nullable = false)
    private Request request;

    @ManyToOne
    @JoinColumn(name ="personnel_table_id", nullable = true)
    private User worker;


    @ManyToOne
    @JoinColumn(name ="activity_type", nullable = false)
    private ActivityDictionary activityDefinition;

    public Long getId() {
        return id;
    }

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) throws InvalidArgumentException {
        if (sequenceNumber == null) {
            throw new InvalidArgumentException("Sequnce number cannot be null");
        }
        if (sequenceNumber <= 0) {
            throw new InvalidArgumentException("Sequnce number has to be greater than 0");
        }
        this.sequenceNumber = sequenceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws InvalidArgumentException {
        if (description == null) {
            throw new InvalidArgumentException("Description cannot be null");
        }
        if ((description.isBlank())) {
            throw new InvalidArgumentException("Description cannot be blank");
        }
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) throws InvalidArgumentException {
        if (result == null) {
            throw new InvalidArgumentException("Result cannot be null");
        }
        if ((result.isBlank())) {
            throw new InvalidArgumentException("Result cannot be blank");
        }
        this.result = result;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(Date dateRequested) throws InvalidArgumentException {
        if( dateRequested == null) {
            throw new InvalidArgumentException("Date cannot be null");
        }
        this.dateRequested = dateRequested;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Date dateClosed) throws InvalidArgumentException {
        if( dateClosed == null) {
            throw new InvalidArgumentException("Date cannot be null");
        }
        this.dateClosed = dateClosed;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) throws InvalidArgumentException {
        if( request == null) {
            throw new InvalidArgumentException("Request cannot be null");
        }
        this.request = request;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) throws InvalidArgumentException {
        if( worker == null) {
            throw new InvalidArgumentException("Worker cannot be null (probably wrong id)");
        }
        this.worker = worker;
    }

    public ActivityDictionary getActivityDefinition() {
        return activityDefinition;
    }

    public void setActivityDefinition(ActivityDictionary activityDefinition) throws InvalidArgumentException {
        if( activityDefinition == null) {
            throw new InvalidArgumentException("Activity Dictionary cannot be null");
        }
        this.activityDefinition = activityDefinition;
    }
}