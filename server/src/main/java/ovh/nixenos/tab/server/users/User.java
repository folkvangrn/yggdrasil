package ovh.nixenos.tab.server.users;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ovh.nixenos.tab.server.exceptions.ConstructorCreationException;
import ovh.nixenos.tab.server.exceptions.InvalidArgumentException;
import ovh.nixenos.tab.server.exceptions.InvalidPasswordException;

/**
 * @TODO implement methods for this class
 *
 * class that reflects user's account in system
 * */
@Entity
@Table(name = "USERS_TABLE")
public class User {

  @Id @GeneratedValue(strategy = GenerationType.AUTO) private Integer id;

  /*
   * username of the user
   */
  private String username;

  /*
   * hash of the user's password
   */
  private String passwordHash;

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
  private void setPassword(String password) throws InvalidArgumentException {
    if (password == null) {
      throw new InvalidArgumentException("Password cannot be null");
    }
    if ((password.isBlank())) {
      throw new InvalidArgumentException("Password cannot be blank");
    }
    this.passwordHash = hashPasswordString(password);
  }

  /**
   * method for hashing user's password
   * @TODO implement hashing
   * */
  private String hashPasswordString(String password) { return password; }

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
  private String getPasswordHash() { return this.passwordHash; }

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

  /**
   * check's whether user belongs to specified role
   * */
  public Boolean checkPermission(String role) throws InvalidArgumentException {
    ArrayList<String> possible_roles =
        new ArrayList<String>(Arrays.asList("admin, manager, worker"));
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
}
