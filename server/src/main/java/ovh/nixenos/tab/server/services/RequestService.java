package ovh.nixenos.tab.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ovh.nixenos.tab.server.entities.Request;
import ovh.nixenos.tab.server.repositories.RequestRepository;

import java.util.Optional;

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

}
