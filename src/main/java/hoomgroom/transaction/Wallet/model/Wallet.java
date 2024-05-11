package hoomgroom.transaction.Wallet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Wallet {

    @Id
    private String walletId;

    private double balance;

    public Wallet() {
        this.walletId = UUID.randomUUID().toString(); // Optionally, generate UUID during object creation
    }
}
