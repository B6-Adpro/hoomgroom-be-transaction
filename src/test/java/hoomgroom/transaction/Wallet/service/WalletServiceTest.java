package hoomgroom.transaction.Wallet.service;

import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {
    @Mock
    WalletRepository walletRepository;

    @InjectMocks
    WalletService walletService;

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
    void testTopUpWithCreditCardValid() {
        Wallet wallet = new Wallet();
        wallet.setWalletId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        wallet.setBalance(100000);
        when(walletRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(wallet);

        String cardNumber = "4103601193829382";

        TopUpByCreditCard strategy = new TopUpByCreditCard(walletRepository);
        strategy.setCardNumber(cardNumber);
        walletService.setStrategy(strategy);

        walletService.topUp("eb558e9f-1c39-460e-8860-71af6af63bd6", 50000);

        assertEquals(150000, wallet.getBalance());
    }
}
