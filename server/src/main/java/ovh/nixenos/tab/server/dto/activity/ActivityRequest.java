package ovh.nixenos.tab.server.dto.activity;


public class ActivityRequest {

    private Long sequenceNumber;
    private String description;
    private String result;
    private String status;

    private Long requestId;

    private Long userId;

    private String activityDictionaryActivityType;
    private String activityDictionaryActivityName;
    private int activityDictionaryEstimatedDuration;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
