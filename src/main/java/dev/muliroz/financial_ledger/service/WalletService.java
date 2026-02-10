package dev.muliroz.financial_ledger.service;

import dev.muliroz.financial_ledger.dto.CreateWalletDTO;
import dev.muliroz.financial_ledger.exception.WalletAlreadyRegisteredException;
import dev.muliroz.financial_ledger.model.Wallet;
import dev.muliroz.financial_ledger.repository.LedgerRepository;
import dev.muliroz.financial_ledger.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final LedgerRepository ledgerRepository;

    public WalletService(WalletRepository walletRepository, LedgerRepository ledgerRepository) {
        this.walletRepository = walletRepository;
        this.ledgerRepository = ledgerRepository;
    }

    public BigDecimal getBalance(UUID walletId) {
        return ledgerRepository.calculateBalance(walletId);
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
