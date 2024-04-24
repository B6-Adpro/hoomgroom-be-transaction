package hoomgroom.transaction.Wallet.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class WalletServiceCreditCardTest {
    @InjectMocks
    WalletServiceCreditCard walletService;

    @Test
    void testAddAndGetWallet() {
        Wallet wallet = new Wallet();
        wallet.setWalletId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        wallet.setBalance(100000);
        walletService.add(wallet);

        Wallet found = walletService.getWalletById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals(found.getWalletId(), wallet.getWalletId());
        assertEquals(found.getBalance(), wallet.getBalance());
    }

    @Test
    void testTopUp() {
        Wallet wallet = new Wallet();
        wallet.setWalletId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        wallet.setBalance(100000);
        walletService.add(wallet);

        walletService.topUp("eb558e9f-1c39-460e-8860-71af6af63bd6", 20000);
        assertEquals(wallet.getBalance(), 120000);
    }
}
