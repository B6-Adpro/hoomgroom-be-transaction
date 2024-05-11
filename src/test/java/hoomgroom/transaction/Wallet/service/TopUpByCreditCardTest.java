package hoomgroom.transaction.Wallet.service;

import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TopUpByCreditCardTest {
    @Mock
    private WalletRepository walletRepository;

    @Captor
    private ArgumentCaptor<Wallet> walletCaptor;

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
    void testInvalidCardNumber() {
        String invalidCardNumber = "1234567890123456"; // 15 digits, invalid according to Luhn's algorithm
        strategy.setCardNumber(invalidCardNumber);
        assertEquals(invalidCardNumber, strategy.getCardNumber());
        assertFalse(strategy.validateTopUpDetails());
    }

    @Test
    void testNullCardNumber() {
        strategy.setCardNumber(null);
        assertNull(strategy.getCardNumber());
        assertFalse(strategy.validateTopUpDetails());
    }

    @Test
    void testTopUpMinimumBalance() {
        Wallet wallet = new Wallet();
        wallet.setWalletId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        wallet.setBalance(0); // Edge case: Minimum balance
        when(walletRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(Optional.of(wallet));

        strategy.topUp("eb558e9f-1c39-460e-8860-71af6af63bd6", 100);

        verify(walletRepository).save(walletCaptor.capture());
        assertEquals(100, wallet.getBalance());
    }

    @Test
    void testTopUpMaximumBalance() {
        Wallet wallet = new Wallet();
        wallet.setWalletId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        wallet.setBalance(Double.MAX_VALUE); // Edge case: Maximum balance
        when(walletRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(Optional.of(wallet));

        strategy.topUp("eb558e9f-1c39-460e-8860-71af6af63bd6", 100);

        verify(walletRepository).save(walletCaptor.capture());
        assertEquals(Double.MAX_VALUE, wallet.getBalance());
    }

}
