package dev.muliroz.financial_ledger.controller;

import dev.muliroz.financial_ledger.dto.TransactionDTO;
import dev.muliroz.financial_ledger.service.LedgerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class LedgerController {

    private final LedgerService ledgerService;

    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @PostMapping
    public ResponseEntity<String> createTransaction(@Valid @RequestBody TransactionDTO request) {
        ledgerService.createTransaction(request);
        return ResponseEntity.ok().body("Wallet registered successfully!");
    }
}
