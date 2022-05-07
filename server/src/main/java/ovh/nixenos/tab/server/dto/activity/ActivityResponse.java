package ovh.nixenos.tab.server.dto.activity;

import ovh.nixenos.tab.server.entities.Status;

import java.util.Date;

public class ActivityResponse {

    private Long id;
    private Long sequenceNumber;
    private String description;
    private String result;
    private Status status;
    private Date dateRequested;
    private Date dateClosed;

    private Long requestId;
    // maybe we need more informations from request?

    private Long userId;
    private String userUsername;

    private String activityDictionaryActivityType;
    private String activityDictionaryActivityName;
    private int activityDictionaryEstimatedDuration;

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

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getActivityDictionaryActivityType() {
        return activityDictionaryActivityType;
    }

    public void setActivityDictionaryActivityType(String activityDictionaryActivityType) {
        this.activityDictionaryActivityType = activityDictionaryActivityType;
    }

    public String getActivityDictionaryActivityName() {
        return activityDictionaryActivityName;
    }

    public void setActivityDictionaryActivityName(String activityDictionaryActivityName) {
        this.activityDictionaryActivityName = activityDictionaryActivityName;
    }

    public int getActivityDictionaryEstimatedDuration() {
        return activityDictionaryEstimatedDuration;
    }

    public void setActivityDictionaryEstimatedDuration(int activityDictionaryEstimatedDuration) {
        this.activityDictionaryEstimatedDuration = activityDictionaryEstimatedDuration;
    }
}
