package dev.muliroz.financial_ledger.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
    public UserAlreadyExistsException() {
        super("User already registered.");
    }
}
