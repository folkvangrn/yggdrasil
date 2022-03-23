package ovh.nixenos.tab.server.restcontrollers;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ovh.nixenos.tab.server.repositories.UserRepository;
import ovh.nixenos.tab.server.users.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired private UserRepository userRepository;

  @GetMapping
  public Iterable<User> getAllUsers() {
    return this.userRepository.findAll();
  }

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
  public String createNewUser(@RequestBody String newUserJsonString) {
    Gson jsonParser = new Gson();
    User newUser = jsonParser.fromJson(newUserJsonString, User.class);
    this.userRepository.save(newUser);
    return new String("Success!");
  }
}
