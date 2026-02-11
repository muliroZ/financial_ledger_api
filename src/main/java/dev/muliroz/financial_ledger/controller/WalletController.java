package dev.muliroz.financial_ledger.controller;

import dev.muliroz.financial_ledger.dto.BalanceResponseDTO;
import dev.muliroz.financial_ledger.dto.CreateWalletDTO;
import dev.muliroz.financial_ledger.model.Wallet;
import dev.muliroz.financial_ledger.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{cpfCnpj}")
    public ResponseEntity<Wallet> showWallet(@PathVariable String cpfCnpj) {
        if (cpfCnpj.length() > 14) throw new RuntimeException();

        Wallet wallet = walletService.findWalletByCpfCnpj(cpfCnpj);

        return ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .lastModified(ZonedDateTime.now())
                .cacheControl(
                        CacheControl.maxAge(Duration.ofHours(12))
                                .mustRevalidate()
                                .cachePrivate()
                )
                .body(wallet);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BalanceResponseDTO> getBalance(@PathVariable UUID id) {
        BalanceResponseDTO response = walletService.getBalance(id);
        return ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .lastModified(ZonedDateTime.now())
                .cacheControl(
                        CacheControl.maxAge(Duration.ofDays(1))
                                .mustRevalidate()
                                .cachePrivate()
                )
                .body(response);
    }

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@Valid @RequestBody CreateWalletDTO request) {
        walletService.createWallet(request);
        return ResponseEntity.status(201).body(walletService.findWalletByCpfCnpj(request.cpfCnpj()));
    }
}
