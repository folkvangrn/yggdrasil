package ovh.nixenos.tab.server.restcontrollers;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ovh.nixenos.tab.server.exceptions.InvalidArgumentException;
import ovh.nixenos.tab.server.exceptions.InvalidPasswordException;
import ovh.nixenos.tab.server.repositories.UserRepository;
import ovh.nixenos.tab.server.users.User;

@RestController
//@RequestMapping("/api/users")
public class UserController {

  @Autowired private UserRepository userRepository;

  @Autowired PasswordEncoder passwordEncoder;

  @GetMapping("/api/users")
  public Iterable<User> getAllUsers() {
    return this.userRepository.findAll();
  }

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, value = "/api/users")
  public String createNewUser(@RequestBody String newUserJsonString) {
    Gson jsonParser = new Gson();
    User newUser = jsonParser.fromJson(newUserJsonString, User.class);
    String tempPass = newUser.getPassword();
    try {
      newUser.setPassword(passwordEncoder.encode(tempPass));
    } catch (InvalidArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this.userRepository.save(newUser);
    return new String("Success!");
  }

}
