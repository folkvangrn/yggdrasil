package ovh.nixenos.tab.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ovh.nixenos.tab.server.entities.Activity;
import ovh.nixenos.tab.server.repositories.ActivityRepository;

import java.util.Optional;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public Activity findById(final Long activityId) {
        Optional<Activity> result = this.activityRepository.findById(activityId);
        if(result.isPresent())
            return result.get();
        else
            return null;
    }

    public Iterable<Activity> findAll() {
        return this.activityRepository.findAll();
    }

    public void save(final Activity activity) {
        this.activityRepository.save(activity);
    }

    public boolean existsById(final Long id){
        return this.activityRepository.existsById(id);
    }

    /*
    // @TO_DElETE IN FINAL VERSION
    @Autowired
    private UserRepository userRepository;
    @PostConstruct
    public void populateTestData() {
        if( activityRepository.count() < 2){
            activityRepository.saveAll(
                    Stream.of("1 opisWStyluJavaBoParsujePoSpacjach",
                            "2 opisZadania",
                            "3 zadanieDoWykonaniaTechnikaTajska")
                    .map(data -> {
                        String[] split = data.split(" ");
                        Activity activity = new Activity();
                        try {
                            activity.setSequenceNumber(Long.parseLong(split[0]));
                            activity.setDescription(split[1]);
                            activity.setStatus(Status.OPEN);
                            Date d = new Date();
                            activity.setDateRequested(d);
                            activity.setWorker(userRepository.findByUsername("Test3"));
                        }
                        catch (InvalidArgumentException ex) {
                            ex.getMessage();
                        }
                        return activity;
                    }).collect(Collectors.toList()));
    * */

}