package ovh.nixenos.tab.server.users;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import ovh.nixenos.tab.server.exceptions.InvalidArgumentException;

/**
 * @TODO implement methods for this class
 * */
@Entity
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
                       String role, String passwordHash) {
    try {
      this.setUsername(username);
      this.setFirstName(firstName);
    } catch (InvalidArgumentException eInvalidArgumentExceptionInstance) {
      System.err.println(eInvalidArgumentExceptionInstance.getMessage());
    }
  }

  private void setUsername(String username) throws InvalidArgumentException {
    if (username == null) {
      throw new InvalidArgumentException("Username cannot be null");
    }
    if (username.isBlank()) {
      throw new InvalidArgumentException("Username cannot be empty");
    }
    this.username = username;
  }

  private void setFirstName(String firstName) throws InvalidArgumentException {
    if (firstName == null) {
      throw new InvalidArgumentException("User's name cannot be null");
    }
    if (firstName.isBlank()) {
      throw new InvalidArgumentException("User's name cannot be empty");
    }
    this.firstName = firstName;
  }
}
