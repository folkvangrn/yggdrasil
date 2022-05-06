package ovh.nixenos.tab.server.restcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ovh.nixenos.tab.server.dto.Client.ClientRequest;
import ovh.nixenos.tab.server.dto.Client.ClientResponse;
import ovh.nixenos.tab.server.entities.Client;
import ovh.nixenos.tab.server.services.ClientService;

@RestController
@RequestMapping(value = "/api/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    List<ClientResponse> getAllClients() {
        Iterable<Client> listOfClients = this.clientService.findAll();
        ArrayList<ClientResponse> resultListOfClients = new ArrayList<>();
        for (Client client : listOfClients) {
            resultListOfClients.add(this.modelMapper.map(client, ClientResponse.class));
        }
        return resultListOfClients;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    ClientResponse addNewClient(@RequestBody ClientRequest newClientInput) {
        try {
            Client newClient = modelMapper.map(newClientInput, Client.class);
            clientService.save(newClient);
            return modelMapper.map(newClient, ClientResponse.class);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error adding new client");
        }
    }

    @GetMapping(value = "{id}")
    ClientResponse getClientById(@PathVariable Long id) {
        try {
            Client client = this.clientService.findById(id);
            return modelMapper.map(client, ClientResponse.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No client found");
        }
    }

    @PutMapping(value = "{id}")
    ClientResponse updateClientById(@PathVariable Long id, @RequestBody ClientRequest inputClient) {
        try {
            Client client = this.clientService.findById(id);
            Client updatedClient = modelMapper.map(inputClient, Client.class);
            client.setFirstName(updatedClient.getFirstName());
            client.setLastName(updatedClient.getLastName());
            client.setCompanyName(updatedClient.getCompanyName());
            client.setEmail(updatedClient.getEmail());
            client.setPhoneNumber(updatedClient.getPhoneNumber());
            this.clientService.save(client);
            return modelMapper.map(client, ClientResponse.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad data provided");
        }
    }

    @DeleteMapping(value = "{id}")
    String deleteClientById(@PathVariable Long id) {
        try {
            this.clientService.deleteById(id);
            return "OK";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad id provided");
        }
    }

}
