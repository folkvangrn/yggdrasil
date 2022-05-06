package ovh.nixenos.tab.server.restcontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ovh.nixenos.tab.server.entities.Activity;
import ovh.nixenos.tab.server.services.ActivityService;
import ovh.nixenos.tab.server.services.CustomUserDetailsService;
import ovh.nixenos.tab.server.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private String secret = "SuPeRsEcReTsTrInG";

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Activity> findActivitiesByWorkerId(){
        return null;
    }

    @GetMapping(value = "/{id}")
    public Activity findById(@PathVariable Long id) {
        return activityService.findById(id);
    }


}
