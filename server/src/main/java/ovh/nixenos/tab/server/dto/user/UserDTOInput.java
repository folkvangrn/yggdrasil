package ovh.nixenos.tab.server.dto.user;

public class UserDTOInput {

  private Long id;

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
   * - admin
   * - manager
   * - worker
   */
  private String role;

  /**
   * is this user's account active
   *
   * Checked as deletion of records in database is forbidden
   */
  private Boolean active;

  public UserDTOInput() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
}
