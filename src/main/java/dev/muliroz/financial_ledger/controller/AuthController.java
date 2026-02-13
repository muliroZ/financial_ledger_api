package dev.muliroz.financial_ledger.controller;

import dev.muliroz.financial_ledger.dto.LoginRequestDTO;
import dev.muliroz.financial_ledger.dto.LoginResponseDTO;
import dev.muliroz.financial_ledger.dto.RegisterRequestDTO;
import dev.muliroz.financial_ledger.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = service.login(request);
        return ResponseEntity.status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .lastModified(ZonedDateTime.now(Clock.systemDefaultZone()))
                .cacheControl(CacheControl.noStore())
                .body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO request) {
        service.register(request);
        return ResponseEntity.status(201)
                .contentType(MediaType.TEXT_PLAIN)
                .body("User registered successfully!");
    }
}
