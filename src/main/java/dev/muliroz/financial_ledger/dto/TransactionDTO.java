package dev.muliroz.financial_ledger.dto;

import dev.muliroz.financial_ledger.model.Ledger;
import dev.muliroz.financial_ledger.model.TransactionType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDTO (
        @NotNull UUID walletId,

        @NotNull(message = "The transaction amount is mandatory")
        @Positive(message = "The transaction requires an amount greater than 0 (ZERO)")
        BigDecimal amount,

        @NotNull(message = "Please send one of these types: 'CREDIT' or 'DEBIT'") TransactionType type,
        @Max(value = 255, message = "The description has a limit of 255 characters") String description,
        @NotNull UUID idempotencyKey
) {}
