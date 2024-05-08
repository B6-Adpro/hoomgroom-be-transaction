package hoomgroom.transaction.Wallet.service;

import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TopUpByCreditCardTest {
    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    TopUpByCreditCard strategy;

    @Test
    void testValidateTopUpDetails() {
        String cardNumber = "4103601193829382";
        strategy.setCardNumber(cardNumber);
        assertEquals(strategy.getCardNumber(), cardNumber);
        assertTrue(strategy.validateTopUpDetails());
    }

    @Test
    void testTopUp() {
        // Mock wallet object
        Wallet wallet = new Wallet();
        wallet.setWalletId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        wallet.setBalance(50000);

        // Stub behavior of findById in walletRepository
        when(walletRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(wallet);

        // Initialize strategy with the mocked walletRepository
        strategy = new TopUpByCreditCard(walletRepository);

        // Perform top-up
        strategy.topUp("eb558e9f-1c39-460e-8860-71af6af63bd6", 50000);
        // Assert balance after top-up
        assertEquals(100000, wallet.getBalance());
    }
}
