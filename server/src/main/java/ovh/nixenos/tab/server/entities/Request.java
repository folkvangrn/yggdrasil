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

    @ManyToOne()
    @JoinColumn(name ="vehicle_licensePlate", nullable = false)
    private Vehicle vehicle;

    @OneToMany(mappedBy = "request")
    private List<Activity> activities;

    @ManyToOne
    @JoinColumn(name ="presonnel_table_id", nullable = false)
    private User manager;

}
