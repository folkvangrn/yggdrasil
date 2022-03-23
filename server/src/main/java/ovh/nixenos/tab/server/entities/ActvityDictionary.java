package ovh.nixenos.tab.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "ACTIVITY_DICTIONARY")
public class ActvityDictionary {

    @Id
    private String activityType;

    @Column(nullable = false)
    private String activityName;

    @Column
    private int estimatedDuration;

}
