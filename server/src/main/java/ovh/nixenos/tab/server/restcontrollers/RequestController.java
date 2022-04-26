package ovh.nixenos.tab.server.restcontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ovh.nixenos.tab.server.dto.request.CreateRequestRequest;
import ovh.nixenos.tab.server.dto.request.FindRequestResponse;
import ovh.nixenos.tab.server.entities.Request;
import ovh.nixenos.tab.server.services.RequestService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public void createRequest(@RequestBody CreateRequestRequest requestDTO){
        Request request = this.modelMapper.map(requestDTO, Request.class);

        //todo

        requestService.save(request);
    }

    @GetMapping
    public List<FindRequestResponse> findRequests(@RequestParam(value = "managerid", required = true) Long managerId,
                                      @RequestParam(value = "status", required = false) String status) {
        List<Request> requestsResult = requestService.findByManagerIdAndStatus(managerId, status);
        List<FindRequestResponse> requests = new ArrayList<>();

        for (Request rq : requestsResult) {
            FindRequestResponse requestResponse = this.modelMapper.map(rq, FindRequestResponse.class);
        }

        return requests;
    }

}
