package com.workshop.domain;

import javax.persistence.*;

@Entity
@Table(name = "VEHICLETYPE")
public class VehicleType {

    @Id
    public String codeType;

    @Column
    public String fullName;

    @Column
    public String vehicleGroup;

    @Column
    public String engineType;
}
