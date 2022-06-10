package ovh.nixenos.tab.server.restcontrollers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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

    /**
     * Endpoint that enables retrieving informations about all clients
     * 
     * @return Informations about all clients
     */
    @GetMapping
    List<ClientResponse> getAllClients() {
        Iterable<Client> listOfClients = this.clientService.findAll();
        ArrayList<ClientResponse> resultListOfClients = new ArrayList<>();
        for (Client client : listOfClients) {
            resultListOfClients.add(this.modelMapper.map(client, ClientResponse.class));
        }
        return resultListOfClients;
    }

    /**
     * Endpoint that enables creating client
     * 
     * @param newClientInput Informations about client that has to be created
     */
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    ClientResponse addNewClient(@RequestBody final ClientRequest newClientInput) {
            Pattern phoneRegexPattern = Pattern
                    .compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{3}$");
            Matcher phoneNumberMatcher = phoneRegexPattern.matcher(newClientInput.getPhoneNumber());
            if (!phoneNumberMatcher.matches()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Invalid phone number specified!");
            }
            // RFC 5322
            Pattern emailRegexPattern = Pattern
                    .compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
            Matcher emailMatcher = emailRegexPattern.matcher(newClientInput.getEmail());
            if (!emailMatcher.matches()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Invalid email specified!");
            }
        try {
            Client newClient = modelMapper.map(newClientInput, Client.class);
            clientService.save(newClient);
            return modelMapper.map(newClient, ClientResponse.class);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error adding new client");
        }
    }

    /**
     * Endpoint that enables retrieving informations about specified client
     * 
     * @param id Id of client that will be returned
     * @return Informations about client that matches parameters
     */
    @GetMapping(value = "{id}")
    ClientResponse getClientById(@PathVariable final Long id) {
        try {
            Client client = this.clientService.findById(id);
            return modelMapper.map(client, ClientResponse.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No client found");
        }
    }

    /**
     * Endpoint that enables updating existing client
     * 
     * @param id          Id of client that has to be updated
     * @param inputClient Informations about client that has to be updated
     */
    @PutMapping(value = "{id}")
    ClientResponse updateClientById(@PathVariable final Long id, @RequestBody final ClientRequest inputClient) {
            Pattern phoneRegexPattern = Pattern
                    .compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{3}$");
            Matcher phoneNumberMatcher = phoneRegexPattern.matcher(inputClient.getPhoneNumber());
            if (!phoneNumberMatcher.matches()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Invalid phone number specified!");
            }
            // RFC 5322
            Pattern emailRegexPattern = Pattern
                    .compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
            Matcher emailMatcher = emailRegexPattern.matcher(inputClient.getEmail());
            if (!emailMatcher.matches()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Invalid email specified!");
            }
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
    String deleteClientById(@PathVariable final Long id) {
        try {
            this.clientService.deleteById(id);
            return "OK";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad id provided");
        }
    }

}
