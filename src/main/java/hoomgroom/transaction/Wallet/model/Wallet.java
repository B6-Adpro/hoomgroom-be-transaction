package hoomgroom.transaction.Wallet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Wallet {

    @Id
    private String walletId;

    private UUID userId;

    private double balance;

    public Wallet(UUID userId) {
        this.walletId = UUID.randomUUID().toString(); // Optionally, generate UUID during object creation
        this.userId = userId;
    }

}
