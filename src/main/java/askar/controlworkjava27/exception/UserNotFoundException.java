package askar.controlworkjava27.exception;

public class UserNotFoundException extends NotFoundEntryException {
    public UserNotFoundException() {
        super("User not found: ");
    }
}
