package ovh.nixenos.tab.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ovh.nixenos.tab.server.entities.Client;
import ovh.nixenos.tab.server.entities.Request;
import ovh.nixenos.tab.server.entities.Status;
import ovh.nixenos.tab.server.entities.Vehicle;
import ovh.nixenos.tab.server.exceptions.InvalidArgumentException;
import ovh.nixenos.tab.server.repositories.RequestRepository;
import ovh.nixenos.tab.server.users.User;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public Request findById(Long requestId){
        Optional<Request> result = requestRepository.findById(requestId);
        if(result.isPresent())
            return result.get();
        else
            return null;
    }

    public List<Request> findByStatus(String status) {
        Optional<List<Request>> result = requestRepository.findByStatus(status);
        if(result.isPresent())
            return result.get();
        else
            return null;
    }

    public List<Request> findByManagerIdAndStatus(Long id, String status) {
        Optional<List<Request>> result = requestRepository.findByManagerIdAndStatus(id, status);
        if(result.isPresent())
            return result.get();
        else
            return null;
    }

    public void save(final Request request){
        requestRepository.save(request);
    }

    /*@PostConstruct
    public void populateTestData() {
        if( requestRepository.count() < 2){
            requestRepository.saveAll(
                    Stream.of("1 opisWStyluJavaBoParsujePoSpacjach",
                                    "2 opisZadania",
                                    "3 zadanieDoWykonaniaTechnikaTajska")
                            .map(data -> {
                                String[] split = data.split(" ");
                                Request rq = new Request();
                                    rq.setDescription(split[1]);
                                    Date d = new Date();
                                    rq.setStatus(Status.OPEN);

                                    rq.setDateRequest(d);
                                Vehicle v = new Vehicle();
                                v.setVin(12345L);
                                Client c = new Client();
                                c.setFirstName("John");
                                c.setLastName("Kow");
                                c.setPhoneNumber("123456789");
                                v.setClient(c);
                                rq.setVehicle(v);
                                User u = new User();
                                try {
                                    u.setPassword("eh");
                                } catch (InvalidArgumentException e) {
                                    e.printStackTrace();
                                }
                                rq.setManager(u);
                                return rq;
                            }).collect(Collectors.toList()));
        }
    }*/

}
