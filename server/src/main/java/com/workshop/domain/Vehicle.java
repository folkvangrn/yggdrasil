package com.workshop.domain;

import com.workshop.domain.Client;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "VEHICLES")
public class Vehicle {

    @Id
    @GeneratedValue
    private Long licensePlate;

    /*
    @ManyToOne
    @JoinColumn(name ="vehicle_code_type")
    private String codeType; //@TO DISCUSS - could also be a brandType
    */

    @ManyToOne()
    @JoinColumn(name ="client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "vehicle")
    private List<Request> requests;

}
