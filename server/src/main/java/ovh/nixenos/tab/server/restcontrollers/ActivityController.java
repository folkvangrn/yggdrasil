package ovh.nixenos.tab.server.restcontrollers;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ovh.nixenos.tab.server.dto.activity.ActivityRequest;
import ovh.nixenos.tab.server.dto.activity.ActivityResponse;
import ovh.nixenos.tab.server.entities.Activity;
import ovh.nixenos.tab.server.entities.Request;
import ovh.nixenos.tab.server.entities.Status;
import ovh.nixenos.tab.server.exceptions.InvalidArgumentException;
import ovh.nixenos.tab.server.services.ActivityDictionaryService;
import ovh.nixenos.tab.server.services.ActivityService;
import ovh.nixenos.tab.server.services.RequestService;
import ovh.nixenos.tab.server.services.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityDictionaryService activityDictionaryService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Endpoint that enables retrieving informations about specified activity
     * @param id Id of activity
     * @return Informations about activity with given id
     */
    @GetMapping(value = "/api/activities/{id}")
    public ActivityResponse findById(@PathVariable final Long id) {
        if(this.activityService.existsById(id))
            return this.modelMapper.map(activityService.findById(id), ActivityResponse.class);
        else
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Activity with id " + id + " does not exist!");
    }

    /**
     * Endpoint that enables retrieving informations about specified activities
     * @param workerId Id of worker that we want to see activities
     * @param status Status which by activities will be filtered
     * @return Informations about all activities that match parameters
     */
    @GetMapping(value = "/api/activities")
    public List<ActivityResponse> findActivitiesByWorkerIdOrStatus(@RequestParam(value = "workerid", required = true) final Long workerId,
                                                                   @RequestParam(value = "status", required = false) final String status) {
        if(this.userService.existsById(workerId)) {
            try {
                List<Activity> activities;
                if (status != null) {
                    activities = this.activityService.
                            findByWorkerIdAndStatus(workerId, Status.valueOf(status));
                } else {
                    activities = this.activityService.findByWorkerId(workerId);
                }
                List<ActivityResponse> activitiesResponse = new ArrayList<>();
                for (Activity act : activities)
                    activitiesResponse.add(this.modelMapper.map(act, ActivityResponse.class));

                return activitiesResponse;
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Provided " + status + " is not a valid request status");
            }
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Worker with id " + workerId + " does not exist!");
        }
    }

    /**
     * Endpoint that enables creating activity
     * @param newActivity Informations about activity that has to be created
     */
    @PostMapping(value = "/api/activities")
    public void createActivity(@RequestBody final ActivityRequest newActivity){
        //validate seq numb
        List<Activity> allActivities = this.activityService.findAllByRequestId(newActivity.getRequestId());
        if(newActivity.getSequenceNumber() != null && allActivities.size() > 0) {
            Long lastSeqNum = allActivities.get(allActivities.size() - 1).getSequenceNumber();
            if (newActivity.getSequenceNumber() <= lastSeqNum) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "New activity sequence number has to be greater than the previous: " + lastSeqNum);
            } else if(newActivity.getSequenceNumber() != lastSeqNum+1) {
                long newNumber = lastSeqNum+1;
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "New activity sequence number has to be: " + newNumber);
            }
        }
        if(!this.userService.findById(newActivity.getWorkerId()).getRole().equals("worker"))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "You have to assign worker to the activity.");
        try {
            Activity activity = this.modelMapper.map(newActivity, Activity.class);

            activity.setSequenceNumber(newActivity.getSequenceNumber());
            activity.setDateRequested(new Date());
            activity.setStatus(Status.OPEN);
            activity.setRequest(this.requestService.findById(newActivity.getRequestId()));
            activity.setWorker(this.userService.findById(newActivity.getWorkerId()));
            activity.setActivityDefinition(this.activityDictionaryService.findById(newActivity.getActivityDictionaryActivityType()));
            this.activityService.save(activity);
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
                    HttpStatus.BAD_REQUEST, "Error adding new activity");
        }
    }

    /**
     * Endpoint that enables updating existing activity
     * @param id Id of activity that has to be updated
     * @param updatedActivity Informations about activity that has to be updated
     */
    @PutMapping(value = "/api/activities/{id}")
    public void updateActivity(@RequestBody final ActivityRequest updatedActivity,
                               @PathVariable final Long id) {
        if(this.activityService.existsById(id)){
            Activity activity = this.activityService.findById(id);
            if(!this.userService.findById(updatedActivity.getWorkerId()).getRole().equals("worker"))
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "You have to assign worker to the activity.");
            if(activity.getStatus() == Status.CANCELED || activity.getStatus() == Status.FINISH)
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Activity with id " + id + " has status " + activity.getStatus() + " and cannot be modified!");
            if(!this.userService.existsById(updatedActivity.getWorkerId()))
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Worker with given id  " + updatedActivity.getWorkerId() + " does not exists!");
            try{
                if(Status.valueOf(updatedActivity.getStatus()) == Status.CANCELED ||
                        Status.valueOf(updatedActivity.getStatus()) == Status.FINISH)
                    activity.setDateClosed(new Date());

                activity.setDescription(updatedActivity.getDescription());
                activity.setResult(updatedActivity.getResult());
                activity.setStatus(Status.valueOf(updatedActivity.getStatus()));
                activity.setWorker(this.userService.findById(updatedActivity.getWorkerId()));
                activity.setActivityDefinition(this.activityDictionaryService.findById(updatedActivity.getActivityDictionaryActivityType()));
                this.activityService.save(activity);
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
                        HttpStatus.BAD_REQUEST, "Provided " + updatedActivity.getStatus() + " is not a valid activity status");
            } catch (Exception e){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Error while updating request");
            }
        }
        else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Activity with id " + id + "does not exist!");
        }
    }

    /**
     * Endpoint that enables retrieving informations about specified activities
     * @param id Id of request which activities will be returned
     * @return Informations about all activities that match parameters
     */
    @GetMapping(value = "/api/requests/{id}/activities")
    public List<ActivityResponse> findActivitiesForRequest(@PathVariable final Long id){
        if(this.requestService.existsById(id)){
            final Request request = this.requestService.findById(id);
            List<Activity> activities = request.getActivities();
            List<ActivityResponse> activitiesResponse = new ArrayList<>();
            for(Activity act : activities)
                activitiesResponse.add(this.modelMapper.map(act, ActivityResponse.class));

            return activitiesResponse;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request with id " + id + " does not exist!");
        }
    }

}
