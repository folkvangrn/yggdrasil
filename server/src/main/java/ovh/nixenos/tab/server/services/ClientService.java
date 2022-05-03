package ovh.nixenos.tab.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ovh.nixenos.tab.server.entities.Client;
import ovh.nixenos.tab.server.repositories.ClientRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Client findById(Long id) {
        return clientRepository.findClientById(id);
    }

    public List<Client> findByCompanyName(String companyName) {
        return clientRepository.findByCompanyName(companyName);
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    public void delete(Client client) {
        clientRepository.delete(client);
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id.intValue());
    }

    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }
}
