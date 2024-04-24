package hoomgroom.transaction.Wallet.repository;

import hoomgroom.transaction.Wallet.model.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class WalletRepositoryTest {
    @InjectMocks
    WalletRepository walletRepository;
    @BeforeEach
    void SetUp() {}

    @Test
    void testAddAndFind() {
        Wallet wallet = new Wallet();
        wallet.setWalletId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        wallet.setBalance(100000);
        walletRepository.addWallet(wallet);

        Wallet found = walletRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals(found.getWalletId(), wallet.getWalletId());
        assertEquals(found.getBalance(), wallet.getBalance());
    }

}
