package ovh.nixenos.tab.server.exceptions;

public class ConstructorCreationException extends Exception {
  public ConstructorCreationException() {
    super("Error creating object instance!");
  }
}
