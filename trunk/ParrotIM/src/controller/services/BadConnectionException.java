package controller.services;

public class BadConnectionException extends Exception {
    public BadConnectionException() {
        super("Connection error.");
    }
}
