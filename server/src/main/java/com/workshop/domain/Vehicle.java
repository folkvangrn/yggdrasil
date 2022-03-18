package com.workshop.domain;

import com.workshop.domain.Client;

import javax.persistence.*;

@Entity
@Table(name = "VEHICLES")
public class Vehicle {

    @ManyToOne()
    private Client client;
}
