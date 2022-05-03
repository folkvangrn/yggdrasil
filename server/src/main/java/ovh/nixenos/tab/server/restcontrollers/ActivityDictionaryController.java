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

    @GetMapping
    public Iterable<ActivityDictionary> getAll(){
        return this.activityDictionaryService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ActivityDictionary findById(@PathVariable String id){
        return this.activityDictionaryService.findById(id);
    }

    @PostMapping
    public void save(@RequestBody ActivityDictionary activity){
        this.activityDictionaryService.save(activity);
    }

}
