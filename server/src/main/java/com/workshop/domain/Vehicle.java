package com.workshop.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "VEHICLES")
public class Vehicle {

    @Id
    private Long vin;

    @Column
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleClass;

    @ManyToOne()
    @JoinColumn(name ="client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "vehicle")
    private List<Request> requests;

}
