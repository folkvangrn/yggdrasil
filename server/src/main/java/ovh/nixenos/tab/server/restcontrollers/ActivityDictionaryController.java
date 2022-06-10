package ovh.nixenos.tab.server.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ovh.nixenos.tab.server.entities.ActivityDictionary;
import ovh.nixenos.tab.server.services.ActivityDictionaryService;

@RestController
@RequestMapping("/api/activity-dictionary")
public class ActivityDictionaryController {

    @Autowired
    private ActivityDictionaryService activityDictionaryService;

    /**
     * Endpoint that enables retrieving informations about data in activity dictionary
     * @return Informations about all activities dicionary in database
     */
    @GetMapping
    public Iterable<ActivityDictionary> getAll(){
        return this.activityDictionaryService.findAll();
    }

    /**
     * Endpoint that enables retrieving informations about specified activity dictionary
     * @param id Id of activity dictionary
     * @return Informations about activity dictionary with given id
     */
    @GetMapping(value = "/{id}")
    public ActivityDictionary findById(@PathVariable final String id){
        return this.activityDictionaryService.findById(id);
    }

    /**
     * Method that enables creating activity dicionary
     * @param activity Informations about activity dicionary that has to be created
     */
    @PostMapping
    public void save(@RequestBody ActivityDictionary activity){
        this.activityDictionaryService.save(activity);
    }

}
