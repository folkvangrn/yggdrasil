package ovh.nixenos.tab.server.users;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.OneToMany;
import ovh.nixenos.tab.server.exceptions.ConstructorCreationException;
import ovh.nixenos.tab.server.exceptions.InvalidArgumentException;
import ovh.nixenos.tab.server.exceptions.InvalidPasswordException;
import ovh.nixenos.tab.server.entities.*;
/**
 * @TODO implement methods for this class
 *
 * class that reflects user's account in system
 * */
@Entity
@Table(name = "PERSONNEL_TABLE")
public class User {

  @Autowired
  @Transient
  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Id @GeneratedValue(strategy = GenerationType.AUTO) private Integer id;

  /*
   * username of the user
   */
  private String username;

  /*
   * hash of the user's password
   */
  private String password;

  /*
   * user's first name
   */
  private String firstName;

  /*
   * user's last name
   */
  private String lastName;

  /*
   * user's role in the system:
   *    - admin
   *    - manager
   *    - worker
   */
  private String role;

  /**
   * is this user's account active
   *
   * Checked as deletion of records in database is forbidden
   * */
  private Boolean active;

  // private List<GrantedAuthority> authorities;

  /**
   * mapping of requests assigned to a manager and vice versa
   * */
  @OneToMany(mappedBy = "manager")
  private List<Request> requests;

  /**
   * mapping of activities assigned to a worker and vice versa
   * */
  @OneToMany(mappedBy = "worker")
  private List<Activity> activities;

  /**
   * empty not-accessible default contructor: use public ones instead
   * */
  protected User() {}

  /**
   * default constructor to be used: taking username, first name, last name,
   * role and password hash as arguments; active flag is by default set to True
   * */
  public void Customer(String username, String firstName, String lastName,
                       String role, String password)
      throws ConstructorCreationException {
    try {
      this.setUsername(username);
      this.setFirstName(firstName);
      this.setLastName(lastName);
      this.passwordEncoder = new BCryptPasswordEncoder();
      this.setPassword(password);
      this.setRole(role);
    } catch (InvalidArgumentException eInvalidArgumentExceptionInstance) {
      System.err.println(eInvalidArgumentExceptionInstance.getMessage());
      throw new ConstructorCreationException();
    }
  }

  /**
   * setter method for username; throws InvalidArgumentException on null or
   * empty argument
   * */
  private void setUsername(String username) throws InvalidArgumentException {
    if (username == null) {
      throw new InvalidArgumentException("Username cannot be null");
    }
    if (username.isBlank()) {
      throw new InvalidArgumentException("Username cannot be empty");
    }
    this.username = username;
  }

  /**
   * setter method for user's first name; throws InvalidArgumentException on
   * null or empty argument
   * */
  private void setFirstName(String firstName) throws InvalidArgumentException {
    if (firstName == null) {
      throw new InvalidArgumentException("User's name cannot be null");
    }
    if (firstName.isBlank()) {
      throw new InvalidArgumentException("User's name cannot be empty");
    }
    this.firstName = firstName;
  }

  /**
   * setter method for user's last name; throws InvalidArgumentException on null
   * or empty argument
   * */
  private void setLastName(String lastName) throws InvalidArgumentException {
    if (lastName == null) {
      throw new InvalidArgumentException("User's last anme cannot be null");
    }
    if (lastName.isBlank()) {
      throw new InvalidArgumentException("User's last name cannot be empty");
    }
    this.lastName = lastName;
  }

  /**
   * setter method for users password, throws InvalidArgumentException
   * */
  public void setPassword(String password) throws InvalidArgumentException {
    if (password == null) {
      throw new InvalidArgumentException("Password cannot be null");
    }
    if ((password.isBlank())) {
      throw new InvalidArgumentException("Password cannot be blank");
    }
    this.password = password;
  }

  /**
   * method for hashing user's password
   * @TODO implement hashing
   * */
  private String hashPasswordString(String password) { 
    return "{bcrypt}" + passwordEncoder.encode(password);
  }

  /**
   * setter method for user's role; throws InvalidArgumentException
   * */
  private void setRole(String role) throws InvalidArgumentException {
    ArrayList<String> possible_roles =
        new ArrayList<String>(Arrays.asList("admin, manager, worker"));
    if (role == null) {
      throw new InvalidArgumentException("Role cannot be set to null");
    }
    if (role.isBlank()) {
      throw new InvalidArgumentException("Role cannot be blank");
    }
    Boolean role_valid = possible_roles.contains(role);
    if (!role_valid) {
      throw new InvalidArgumentException("Provided role is not valid!");
    }
    this.role = role;
  }

  /**
   * getter for password hash (private)
   * */
  private String getPasswordHash() { return this.password; }

  /**
   * getter for username
   * */
  public String getUsername() { return this.username; }

  /**
   * getter for user's first name
   * */
  public String getFirstName() { return this.firstName; }

  /**
   * getter for user's last name
   * */
  public String getLastName() { return this.lastName; }

  /**
   * getter for user's role
   * */
  public String getRole() { return this.role; }

  /**
   * check if provided password matches password's hash in database
   * */
  public Boolean checkPassword(String password)
      throws InvalidArgumentException {
    if (password == null) {
      throw new InvalidArgumentException("Password cannot be null");
    }
    if (hashPasswordString(password).equals(this.getPasswordHash())) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * changes user's password provided correct old password and new not-null
   * password
   * */
  public void changePassword(String oldPassword, String newPassword)
      throws InvalidArgumentException, InvalidPasswordException {
    if (newPassword == null) {
      throw new InvalidArgumentException("Password cannot be null");
    }
    try {
      if (this.checkPassword(oldPassword)) {
        this.setPassword(newPassword);
      } else {
        throw new InvalidPasswordException();
      }
    } catch (InvalidArgumentException eInvalidArgumentExceptionInstance) {
      throw new InvalidArgumentException(
          eInvalidArgumentExceptionInstance.getMessage());
    }
  }

  /**
   * sets 'active' flag for user's account
   * */
  public void activateAccount() { this.active = true; }

  /**
   * unsets 'active' flag for user's account
   * */
  public void suspendAccount() { this.active = false; }


  public Boolean isActive() {return this.active;}

  /**
   * check's whether user belongs to specified role
   * */
  public Boolean checkPermission(String role) throws InvalidArgumentException {
    ArrayList<String> possible_roles =
        new ArrayList<String>(Arrays.asList("ADMIN, MANAGER, WORKER"));
    if (role == null) {
      throw new InvalidArgumentException("Role cannot be null");
    }
    if (role.isBlank()) {
      throw new InvalidArgumentException("Role cannot be blank");
    }
    Boolean role_status = possible_roles.contains(role);
    if (role_status) {
      return true;
    }
    return false;
  }

  /**
   * add new request to User
   * */
  public void addRequest(Request request) throws InvalidArgumentException {
    if (request == null) {
      throw new InvalidArgumentException("Argument cannot be null");
    }
    this.requests.add(request);
  }

  /**
   * add new activity assigned to user
   * */
  public void addActivity(Activity activity) throws InvalidArgumentException {
    if (activity == null) {
      throw new InvalidArgumentException("Argument cannot be null");
    }
    this.activities.add(activity);
  }

  public List<Request> getRequests() {
    return this.requests;
  }

  public List<Activity> getActivities() {
    return this.activities;
  }

  public String getPassword() {
    return this.password;
  }

  public Integer getId() {
    return this.id;
  }

  /*
  public List<GrantedAuthority> getAuthorities() {
      return this.authorities;
  }

  public void addAuthority(GrantedAuthority authority) throws InvalidArgumentException{
    if (authority == null) {
      throw new InvalidArgumentException("Provided authority cannot be null");
    }
    this.authorities.add(authority);
  }
  public void setAuthorities(List<GrantedAuthority> authorities) {
    this.authorities = authorities;
  }
  */

  public String jsonify() {
    return new String(
      "{ \"id\": \"" + this.getId().toString() + "\", \"username\": \"" + this.getUsername() + "\", \"firstName\": \"" + this.getFirstName() + 
      "\", \"lastName\": \"" + this.getLastName() + "\", \"role\": \"" + this.getRole() +  
      "\"}"
    );
  }

}
