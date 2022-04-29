package ovh.nixenos.tab.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import ovh.nixenos.tab.server.entities.ActivityDictionary;
import ovh.nixenos.tab.server.repositories.ActivityDictionaryRepository;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ActivityDictionaryService {

    @Autowired
    private ActivityDictionaryRepository activityDictionaryRepository;

    public ActivityDictionary findById(String type){
        Optional<ActivityDictionary> result = activityDictionaryRepository.findById(type);
        if(result.isPresent())
            return result.get();
        else
            return null;
    }

    public Iterable<ActivityDictionary> findAll(){
        return activityDictionaryRepository.findAll();
    }

    public void save(final ActivityDictionary activityDictionary){
        activityDictionaryRepository.save(activityDictionary);
    }

    //TODO - There should be better way to add some basic activity types
    @PostMapping
    public void populateData() {
        if(activityDictionaryRepository.count() < 1){
            activityDictionaryRepository.saveAll(
                    Stream.of("PNTD Paint the door",
                                    "RMVSB Remove seatbelt",
                                    "ADDSB Add seatbelt")
                            .map(data -> {
                                String[] split = data.split(" ");
                                ActivityDictionary ad = new ActivityDictionary();
                                ad.setActivityType(split[0]);
                                ad.setActivityName(split[1]);
                                ad.setEstimatedDuration(12);
                                return ad;
                            }).collect(Collectors.toList())
            );
        }
    }

}
