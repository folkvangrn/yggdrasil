package ovh.nixenos.tab.server.restcontrollers;

import java.util.ArrayList;

import com.google.gson.*;

import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ovh.nixenos.tab.server.dto.user.UserDTOInput;
import ovh.nixenos.tab.server.dto.user.UserDTOOutput;
import ovh.nixenos.tab.server.exceptions.InvalidArgumentException;
import ovh.nixenos.tab.server.exceptions.InvalidPasswordException;
import ovh.nixenos.tab.server.repositories.UserRepository;
import ovh.nixenos.tab.server.services.UserService;
import ovh.nixenos.tab.server.users.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @GetMapping()
  public ArrayList<UserDTOOutput> getAllUsers() {
    Iterable<User> listOfUsers = this.userService.findAll();
    ArrayList<UserDTOOutput> resultListOFUsers = new ArrayList<>();
    for (User user : listOfUsers) {
      resultListOFUsers.add(new UserDTOOutput(user));
    }
    return resultListOFUsers;
  }

  @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
  public UserDTOOutput createNewUser(@RequestBody UserDTOInput newUser) {
    String tempPass = newUser.getPassword();
    newUser.setPassword(passwordEncoder.encode(tempPass));
    User user = new User(newUser);
    this.userService.save(user);
    UserDTOOutput output = new UserDTOOutput(user);
    return output;
  }

  @GetMapping(value = "{id}")
  public UserDTOOutput getUserById(@PathVariable Integer id) {
    User user = userService.findById(id);
    if (user != null) {
      return new UserDTOOutput(user);
    }
    throw new ResponseStatusException(
        HttpStatus.NOT_FOUND, "Entity not found");
  }

}
