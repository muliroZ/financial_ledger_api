package dev.muliroz.financial_ledger.exception;

public class WalletAlreadyRegisteredException extends RuntimeException {
    public WalletAlreadyRegisteredException(String message) {
        super(message);
    }
}
