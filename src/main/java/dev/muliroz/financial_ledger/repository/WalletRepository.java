package dev.muliroz.financial_ledger.repository;

import dev.muliroz.financial_ledger.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    boolean existsByCpfCnpj(String cpfCnpj);
    boolean existsByEmail(String email);

    Optional<Wallet> findByCpfCnpj(String cpfCnpj);
}
