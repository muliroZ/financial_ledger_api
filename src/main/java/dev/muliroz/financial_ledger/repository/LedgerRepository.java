package dev.muliroz.financial_ledger.repository;

import dev.muliroz.financial_ledger.model.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, UUID> {

    @Query("""
        SELECT COALESCE(SUM(
            CASE WHEN l.type = 'CREDIT' THEN l.amount
                ELSE -l.amount
            END
        ), 0)
        FROM Ledger l
        WHERE l.wallet.id == :wallet_id
    """)
    BigDecimal calculateBalance(@Param("wallet_id") UUID walletId);

    boolean existsByIdempotencyKey(UUID idempotencyKey);
}
