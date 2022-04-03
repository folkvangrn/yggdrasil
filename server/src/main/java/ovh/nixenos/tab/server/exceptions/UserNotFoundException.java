package ovh.nixenos.tab.server.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("Username not found in the database!");
    }
}
