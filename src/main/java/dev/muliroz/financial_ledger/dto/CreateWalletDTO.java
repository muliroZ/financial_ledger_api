package dev.muliroz.financial_ledger.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public record CreateWalletDTO (
        @NotNull(message = "Please insert the full name related to this wallet") @Max(100) String fullName,
        @NotNull(message = "A CPF or a CNPJ is mandatory") @Max(14) String cpfCnpj,

        @NotNull(message = "An e-mail is mandatory") @Max(100)
        @Email(message = "Please provide a valid email address")
        String email
) {}
