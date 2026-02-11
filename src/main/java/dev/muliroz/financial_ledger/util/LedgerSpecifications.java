package dev.muliroz.financial_ledger.util;

import dev.muliroz.financial_ledger.model.Ledger;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

public class LedgerSpecifications {

    public static Specification<Ledger> withWalletId(UUID walletId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("wallet").get("id"), walletId);
    }

    public static Specification<Ledger> withDateRange(LocalDateTime start, LocalDateTime end) {
        return (root, query, criteriaBuilder) -> {
            if (start == null && end == null) return null;
            if (start != null && end != null) return criteriaBuilder.between(root.get("createdAt"), start, end);
            if (start != null) return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), start);
            return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), end);
        };
    }
}
