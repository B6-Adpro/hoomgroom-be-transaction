package hoomgroom.transaction.Wallet.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletTest {
    Wallet wallet;
    @BeforeEach
    void SetUp() {
        this.wallet = new Wallet();
        this.wallet.setWalletId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.wallet.setBalance(100000);
    }

    @Test
    void testGetWalletId() {assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.wallet.getWalletId());}

    @Test
    void testGetWalletBalance() {assertEquals(100000, this.wallet.getBalance());}
}
