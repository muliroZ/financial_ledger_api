package dev.muliroz.financial_ledger.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateWalletDTO (
        @NotNull(message = "Please insert the full name related to this wallet")
        @Size(max = 100, message = "Full name cannot exceed 100 characters")
        String fullName,

        @NotNull(message = "A CPF or a CNPJ is mandatory")
        @Size(max = 14, message = "CPF/CNPJ cannot exceed 14 characters")
        String cpfCnpj,

        @NotNull(message = "An e-mail is mandatory")
        @Size(max = 100, message = "Email cannot exceed 100 characters")
        @Email(message = "Please provide a valid email address")
        String email
) {}
