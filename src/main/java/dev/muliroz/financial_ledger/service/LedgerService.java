package dev.muliroz.financial_ledger.service;

import dev.muliroz.financial_ledger.dto.TransactionDTO;
import dev.muliroz.financial_ledger.exception.IdempotencyKeyException;
import dev.muliroz.financial_ledger.exception.InsufficientBalanceException;
import dev.muliroz.financial_ledger.exception.ResourceNotFoundException;
import dev.muliroz.financial_ledger.model.Ledger;
import dev.muliroz.financial_ledger.model.TransactionType;
import dev.muliroz.financial_ledger.model.Wallet;
import dev.muliroz.financial_ledger.repository.LedgerRepository;
import dev.muliroz.financial_ledger.repository.WalletRepository;
import dev.muliroz.financial_ledger.util.LedgerSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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
            throw new IdempotencyKeyException();
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

    public Page<Ledger> getStatement(UUID walletId, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        if (!walletRepository.existsById(walletId)) {
            throw new ResourceNotFoundException("Wallet not found");
        }

        Specification<Ledger> spec = Specification
                .where(LedgerSpecifications.withWalletId(walletId))
                .and(LedgerSpecifications.withDateRange(start, end));

        return ledgerRepository.findAll(spec, pageable);
    }
}
