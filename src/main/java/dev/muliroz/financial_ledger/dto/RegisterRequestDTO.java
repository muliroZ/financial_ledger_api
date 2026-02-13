package dev.muliroz.financial_ledger.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotNull(message = "Username is mandatory")
        String username,

        @NotNull(message = "Password is mandatory")
        @Size(min = 6, message = "Password must be 6 characters long at least")
        String password
) {}
