package dev.muliroz.financial_ledger.exception;

public class IdempotencyKeyException extends RuntimeException {
    public IdempotencyKeyException(String message) {
        super(message);
    }
    public IdempotencyKeyException() {
        super("A request with the same idempotency key for the same operation is being processed or pending.");
    }
}
