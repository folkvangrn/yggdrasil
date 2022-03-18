package com.workshop.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CLIENTS")
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 40) // company
    String name;

    @Column(nullable = false, length = 25)
    String firstName;

    @Column(nullable = false, length = 25)
    String secondName;

    @Column(nullable = false, length = 9)
    String phoneNumber;

    @OneToMany(mappedBy = "client")
    private List<Vehicle> clientVehicles;
}
