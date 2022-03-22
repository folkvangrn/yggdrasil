package com.workshop.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @JoinColumn(name ="personel_id", nullable = false)
    private Personel manager;

}
