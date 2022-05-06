package ovh.nixenos.tab.server.restcontrollers;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ovh.nixenos.tab.server.dto.request.RequestRequest;
import ovh.nixenos.tab.server.dto.request.RequestResponse;
import ovh.nixenos.tab.server.entities.Request;
import ovh.nixenos.tab.server.entities.Status;
import ovh.nixenos.tab.server.exceptions.InvalidArgumentException;
import ovh.nixenos.tab.server.services.RequestService;
import ovh.nixenos.tab.server.services.UserService;
import ovh.nixenos.tab.server.services.VehicleService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public void createRequest(@RequestBody RequestRequest requestDTO) {
        try {
            Request request = this.modelMapper.map(requestDTO, Request.class);
            request.setDateRequest(new Date());
            request.setStatus(Status.OPEN);
            request.setManager(this.userService.findById(requestDTO.getManagerId()));
            requestService.save(request);
        } catch (InvalidArgumentException e){
          throw new ResponseStatusException(
                  HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (MappingException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getCause().getMessage());
        } catch (DataAccessException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error adding new request");
        }
    }

    @GetMapping
    public List<RequestResponse> findRequests(@RequestParam(value = "managerid", required = true) Long managerId,
                                              @RequestParam(value = "status", required = false) String status) {
        List<Request> requestsResult;
        try {
            if (status != null) {
                Status statusEnum = Status.valueOf(status);
                requestsResult = requestService.findByManagerIdAndStatus(managerId, statusEnum);
            } else {
                requestsResult = requestService.findByManagerId(managerId);
            }
            List<RequestResponse> requests = new ArrayList<>();
            for (Request rq : requestsResult) {
                RequestResponse requestResponse = this.modelMapper.map(rq, RequestResponse.class);
                requests.add(requestResponse);
            }
            return requests;
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Provided " + status + " is not a valid request status");
        }

    }

    @PutMapping(value = "/{id}")
    public void updateRequest(@RequestBody RequestRequest updatedRequest,@PathVariable Long id) {
        if(this.requestService.existsById(id)) {
            Request request = this.requestService.findById(id);
            if (request.getStatus() == Status.CANCELED || request.getStatus() == Status.FINISH)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request with id " + id + " is " + request.getStatus() + " and cannot be modified");
            try {
                if(Status.valueOf(updatedRequest.getStatus()) == Status.CANCELED ||
                        Status.valueOf(updatedRequest.getStatus()) == Status.FINISH)
                    request.setDateFinalized(new Date());

                request.setDescription(updatedRequest.getDescription());
                request.setResult(updatedRequest.getResult());
                request.setStatus(Status.valueOf(updatedRequest.getStatus()));
                request.setVehicle(this.vehicleService.findByVin(updatedRequest.getVehicleVin()));
                request.setManager(this.userService.findById(updatedRequest.getManagerId()));
                requestService.save(request);
                } catch (InvalidArgumentException e) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, e.getMessage());
                } catch (MappingException e) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, e.getCause().getMessage());
                } catch (DataAccessException e) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, e.getMessage());
                } catch (IllegalArgumentException e) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Provided " + updatedRequest.getStatus() + " is not a valid request status");
                } catch (Exception e){
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Error while updating request");
                }
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request with id " + id + " doesn't exist");
        }
    }

    @GetMapping(value = "/{id}")
    public RequestResponse getRequestById(@PathVariable Long id){
        if(this.requestService.existsById(id)){
            return this.modelMapper.map(this.requestService.findById(id), RequestResponse.class);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request with id " + id + " doesn't exist");
        }
    }

}
