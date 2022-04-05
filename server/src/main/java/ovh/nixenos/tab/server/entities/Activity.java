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
    private ActvityDictionary activityDefinition;

}