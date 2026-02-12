package dev.muliroz.financial_ledger.dto;

import dev.muliroz.financial_ledger.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDTO (
        @NotNull(message = "Wallet identification key is mandatory")
        UUID walletId,

        @NotNull(message = "The transaction amount is mandatory")
        @Positive(message = "The transaction requires an amount greater than 0 (ZERO)")
        BigDecimal amount,

        @NotNull(message = "Please send one of these types: 'CREDIT' or 'DEBIT'")
        TransactionType type,

        @Size(max = 255, message = "Description has a limit of 255 characters")
        String description,

        @NotNull(message = "A transaction needs an idempotency key to ensure safety in the process")
        UUID idempotencyKey
) {}
