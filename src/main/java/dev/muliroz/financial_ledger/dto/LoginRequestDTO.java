package dev.muliroz.financial_ledger.dto;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
        @NotNull(message = "Please insert you username") String username,
        @NotNull(message = "Insert your password") String password
) {}
