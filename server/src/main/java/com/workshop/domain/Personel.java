package com.workshop.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PERSONEL")
public class Personel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 25)
    private String firstName;

    @Column(nullable = false, length = 25)
    private String lastName;

    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "manager")
    private List<Request> requests;

    @OneToMany(mappedBy = "worker")
    private List<Activity> activities;

}
