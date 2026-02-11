package dev.muliroz.financial_ledger.dto;

import dev.muliroz.financial_ledger.model.Ledger;
import dev.muliroz.financial_ledger.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record LedgerResponseDTO (
        UUID transactionId,
        BigDecimal amount,
        TransactionType type,
        String description,
        LocalDateTime createdAt,
        UUID idempotencyKey
) {
    public static LedgerResponseDTO fromEntity(Ledger ledger) {
        return new LedgerResponseDTO(
                ledger.getId(),
                ledger.getAmount(),
                ledger.getType(),
                ledger.getDescription(),
                ledger.getCreatedAt(),
                ledger.getIdempotencyKey()
        );
    }
}
