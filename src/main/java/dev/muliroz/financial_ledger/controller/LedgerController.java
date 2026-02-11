package dev.muliroz.financial_ledger.controller;

import dev.muliroz.financial_ledger.dto.LedgerResponseDTO;
import dev.muliroz.financial_ledger.dto.TransactionDTO;
import dev.muliroz.financial_ledger.model.Ledger;
import dev.muliroz.financial_ledger.service.LedgerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.UUID;

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
        return ResponseEntity.status(201).body("Transaction registered successfully!");
    }

    @GetMapping("/{id}/statement")
    public ResponseEntity<Page<LedgerResponseDTO>> generateStatement(
            @PathVariable UUID id,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atTime(LocalTime.MAX) : null;

        Page<Ledger> page = ledgerService.getStatement(id, startDateTime, endDateTime, pageable);

        return ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .lastModified(ZonedDateTime.now())
                .cacheControl(
                        CacheControl.maxAge(Duration.ofDays(1))
                                .mustRevalidate()
                                .cachePrivate()
                )
                .body(page.map(LedgerResponseDTO::fromEntity));
    }
}
