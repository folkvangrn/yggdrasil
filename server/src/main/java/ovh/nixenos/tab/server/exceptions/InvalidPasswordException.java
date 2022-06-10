package ovh.nixenos.tab.server.exceptions;

public class InvalidPasswordException extends Exception {
  public InvalidPasswordException() { super("Invalid password provided!"); }
}
