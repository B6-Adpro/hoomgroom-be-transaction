package hoomgroom.transaction.Wallet.service;

import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
        walletRepository.save(wallet);

        Optional<Wallet> found = walletRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertThat(found.isPresent());
        assertEquals(wallet, found.get());
    }

    @Test
    void testTopUpWithCreditCardValid() {
        Wallet wallet = new Wallet();
        wallet.setWalletId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        wallet.setBalance(100000);
        when(walletRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(java.util.Optional.of(wallet));

        String cardNumber = "4103601193829382";

        TopUpByCreditCard strategy = new TopUpByCreditCard(walletRepository);
        strategy.setCardNumber(cardNumber);
        walletService.setStrategy(strategy);

        walletService.topUp("eb558e9f-1c39-460e-8860-71af6af63bd6", 50000);

        assertEquals(150000, wallet.getBalance());
    }

//    @Test
//    void testTopUpWithInvalidCreditCard() {
//        Wallet wallet = new Wallet();
//        wallet.setWalletId("eb558e9f-1c39-460e-8860-71af6af63bd6");
//        wallet.setBalance(100000);
//        when(walletRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(java.util.Optional.of(wallet));
//
//        String cardNumber = "invalidCardNumber";
//
//        TopUpByCreditCard strategy = new TopUpByCreditCard(walletRepository);
//        strategy.setCardNumber(cardNumber);
//        walletService.setStrategy(strategy);
//
//        walletService.topUp("eb558e9f-1c39-460e-8860-71af6af63bd6", 50000);
//
//        assertEquals(100000, wallet.getBalance()); // Balance should remain unchanged
//    }
}
