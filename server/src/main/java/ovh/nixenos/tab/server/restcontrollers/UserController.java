package ovh.nixenos.tab.server.restcontrollers;

import com.google.gson.*;
import java.util.ArrayList;
import org.hibernate.annotations.NotFound;
import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @Autowired private UserService userService;

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired private ModelMapper modelMapper;

  @GetMapping()
  public ArrayList<UserDTOOutput> getAllUsers() {
    Iterable<User> listOfUsers = this.userService.findAll();
    ArrayList<UserDTOOutput> resultListOFUsers = new ArrayList<>();
    for (User user : listOfUsers) {
      resultListOFUsers.add(this.modelMapper.map(user, UserDTOOutput.class));
    }
    return resultListOFUsers;
  }

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
  public UserDTOOutput createNewUser(@RequestBody UserDTOInput newUser) {
    String tempPass = newUser.getPassword();
    newUser.setPassword(passwordEncoder.encode(tempPass));
    User user = this.modelMapper.map(newUser, User.class);
    user.activateAccount();
    this.userService.save(user);
    UserDTOOutput output = this.modelMapper.map(user, UserDTOOutput.class);
    return output;
  }

  @GetMapping(value = "{id}")
  public UserDTOOutput getUserById(@PathVariable Long id) {
    User user = userService.findById(id);
    if (user != null) {
      return this.modelMapper.map(user, UserDTOOutput.class);
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
  }

  @PutMapping(value = "{id}")
  public UserDTOOutput updateUserById(@PathVariable Long id,
                                      @RequestBody UserDTOInput entity) {
    User user = userService.findById(id);
    try {
      user.setFirstName(entity.getFirstName());
      user.setLastName(entity.getLastName());
      user.setRole(entity.getRole());
      if (!entity.getUsername().equals(user.getUsername())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                          "Cannot modify username!");
      }
      if (entity.getActive()) {
        user.activateAccount();
      } else {
        user.suspendAccount();
      }
      if (entity.getPassword() != null) {
        if (user.getPassword() != entity.getPassword()) {
          if (!entity.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(entity.getPassword()));
          }
        }
      }
    } catch (InvalidArgumentException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "Invalid data provided");
    }
    userService.save(user);
    return modelMapper.map(user, UserDTOOutput.class);
  }

  /*
  @DeleteMapping(value = "{id}")
  public String deleteUserById(@PathVariable Integer id) {
    userService.deleteById(id);
    return "{\"status\" : \"success\"}";
  }
  */
}
