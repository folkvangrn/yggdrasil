package ovh.nixenos.tab.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "ACTIVITY_DICTIONARY")
public class ActivityDictionary {

    @Id
    private String activityType;

    @Column(nullable = false)
    private String activityName;

    @Column
    private int estimatedDuration;

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }
}
