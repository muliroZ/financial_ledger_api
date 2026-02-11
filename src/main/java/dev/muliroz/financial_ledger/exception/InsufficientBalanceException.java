package dev.muliroz.financial_ledger.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
    public InsufficientBalanceException() {
        super("Insufficient funds.");
    }
}
