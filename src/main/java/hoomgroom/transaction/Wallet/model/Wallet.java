package hoomgroom.transaction.Wallet.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Wallet {
    private String walletId;
    private double balance;

    public Wallet() {
        this.walletId = UUID.randomUUID().toString();
    }
}
