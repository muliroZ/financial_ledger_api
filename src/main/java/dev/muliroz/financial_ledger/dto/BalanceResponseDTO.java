package dev.muliroz.financial_ledger.dto;

import dev.muliroz.financial_ledger.util.Currency;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record BalanceResponseDTO(
        UUID walletId,
        BigDecimal currentBalance,
        Currency currency,
        Instant checkedAt
) {}
