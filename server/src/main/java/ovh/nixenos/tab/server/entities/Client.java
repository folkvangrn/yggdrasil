package ovh.nixenos.tab.server.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CLIENTS")
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 40)
    private String companyName;

    @Column(nullable = false, length = 25)
    private String firstName;

    @Column(nullable = false, length = 25) // 40 chars :>
    private String lastName;

    @Column(nullable = false, length = 9)
    private String phoneNumber;

    @Column
    private String email;

    @Column
    private boolean isActive;

    @OneToMany(mappedBy = "client")
    private List<Vehicle> clientVehicles;

}
