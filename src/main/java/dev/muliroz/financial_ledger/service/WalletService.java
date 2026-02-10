package dev.muliroz.financial_ledger.service;

import dev.muliroz.financial_ledger.dto.BalanceResponse;
import dev.muliroz.financial_ledger.dto.CreateWalletDTO;
import dev.muliroz.financial_ledger.exception.ResourceNotFoundException;
import dev.muliroz.financial_ledger.exception.WalletAlreadyRegisteredException;
import dev.muliroz.financial_ledger.model.Wallet;
import dev.muliroz.financial_ledger.repository.LedgerRepository;
import dev.muliroz.financial_ledger.repository.WalletRepository;
import dev.muliroz.financial_ledger.util.Currency;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final LedgerRepository ledgerRepository;

    public WalletService(WalletRepository walletRepository, LedgerRepository ledgerRepository) {
        this.walletRepository = walletRepository;
        this.ledgerRepository = ledgerRepository;
    }

    public Wallet findWalletByCpfCnpj(String cpfCnpj) {
        return walletRepository.findByCpfCnpj(cpfCnpj)
                .orElseThrow(() -> new ResourceNotFoundException("No wallet found with this CPF/CNPJ"));
    }

    public BalanceResponse getBalance(UUID walletId) {
        BigDecimal currentBalance = ledgerRepository.calculateBalance(walletId);
        return new BalanceResponse(walletId, currentBalance, Currency.BRL, Instant.now());
    }

    @Transactional
    public void createWallet(CreateWalletDTO request) {
        if (walletRepository.existsByCpfCnpj(request.cpfCnpj()) || walletRepository.existsByEmail(request.email())) {
            throw new WalletAlreadyRegisteredException("This wallet has already been registered");
        }

        Wallet newWallet = new Wallet(request.fullName(), request.cpfCnpj(), request.email());

        walletRepository.save(newWallet);
    }
}
