package dev.muliroz.financial_ledger.service;

import dev.muliroz.financial_ledger.dto.TransactionDTO;
import dev.muliroz.financial_ledger.exception.InsufficientBalanceException;
import dev.muliroz.financial_ledger.model.Ledger;
import dev.muliroz.financial_ledger.model.TransactionType;
import dev.muliroz.financial_ledger.model.Wallet;
import dev.muliroz.financial_ledger.repository.LedgerRepository;
import dev.muliroz.financial_ledger.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LedgerService {

    private final LedgerRepository ledgerRepository;
    private final WalletRepository walletRepository;

    public LedgerService(LedgerRepository ledgerRepository, WalletRepository walletRepository) {
        this.ledgerRepository = ledgerRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public void createTransaction(TransactionDTO request) {
        if (ledgerRepository.existsByIdempotencyKey(request.idempotencyKey())) {
            throw new RuntimeException();
        }

        if (request.type() == TransactionType.DEBIT) {
            BigDecimal actualBalance = ledgerRepository.calculateBalance(request.walletId());
            if (actualBalance.subtract(request.amount()).compareTo(BigDecimal.ZERO) < 0) {
                throw new InsufficientBalanceException("Insufficient balance. Make a deposit on your wallet to proceed.");
            }
        }

        Wallet walletProxy = walletRepository.getReferenceById(request.walletId());

        Ledger transaction = new Ledger(
                walletProxy,
                request.amount(),
                request.type(),
                request.description(),
                request.idempotencyKey()
        );

        ledgerRepository.save(transaction);
    }
}
