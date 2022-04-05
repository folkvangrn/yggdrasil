package ovh.nixenos.tab.server.entities;

import javax.persistence.*;
import java.util.Date;
import ovh.nixenos.tab.server.users.User;

@Entity
@Table(name = "ACTIVITY")
public class Activity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long sequenceNumber;

    @Column(length = 500)
    private String description;

    @Column
    private String result;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    @Temporal(TemporalType.DATE)
    private Date dateRequest;

    @Column
    @Temporal(TemporalType.DATE)
    private Date dateFinalized;

    @ManyToOne
    @JoinColumn(name ="request_id", nullable = false)
    private Request request;

    @ManyToOne
    @JoinColumn(name ="personnel_table_id", nullable = true)
    private User worker;

    @ManyToOne
    private ActvityDictionary activityDefinition;

}
