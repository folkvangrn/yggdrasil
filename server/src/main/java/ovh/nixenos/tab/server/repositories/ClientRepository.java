package ovh.nixenos.tab.server.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ovh.nixenos.tab.server.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Integer> {
    Client findClientById(Long id);

    List<Client> findByCompanyName(String companyName);

    List<Client> findByFirstName(String firstName);

    List<Client> findByLastName(String lastName);

    Iterable<Client> findAll();
}
