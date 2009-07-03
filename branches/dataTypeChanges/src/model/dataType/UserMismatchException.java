package model.dataType;

public class UserMismatchException extends Exception {
    public UserMismatchException() {
        super("Wrong user type to add.");
    }
}
