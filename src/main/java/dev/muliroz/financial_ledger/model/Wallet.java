package dev.muliroz.financial_ledger.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wallets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@ToString
public class Wallet {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 14)
    private String cpfCnpj;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Wallet(String fullName, String cpfCnpj, String email) {
        this.fullName = fullName;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }
}
