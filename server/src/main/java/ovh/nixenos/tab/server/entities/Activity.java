package ovh.nixenos.tab.server.entities;

import javax.persistence.*;
import java.util.Date;
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

    @ManyToOne
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
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

    public Date getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(Date dateRequested) {
        this.dateRequested = dateRequested;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public ActivityDictionary getActivityDefinition() {
        return activityDefinition;
    }

    public void setActivityDefinition(ActivityDictionary activityDefinition) {
        this.activityDefinition = activityDefinition;
    }
}