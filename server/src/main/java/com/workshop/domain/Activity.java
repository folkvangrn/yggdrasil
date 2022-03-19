package com.workshop.domain;

import javax.persistence.*;
import java.util.Date;

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
    private String status;

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
    @JoinColumn(name ="personel_id", nullable = true)
    private Personel worker;

}
