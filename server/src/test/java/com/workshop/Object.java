package com.workshop;

import javax.persistence.*;

@Entity
@Table(name = "OBJECTS")
public class Object {

    @ManyToOne()
    private Client client;
}
