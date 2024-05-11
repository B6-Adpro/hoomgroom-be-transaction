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
public class TopUpByEWalletTest {
    @Mock
    private WalletRepository walletRepository;

    @Captor
    private ArgumentCaptor<Wallet> walletCaptor;

    @InjectMocks
    TopUpByEWallet strategy;

    @Test
    void testValidPhoneNumber() {
        String validPhoneNumber = "+1234567890"; // Valid phone number
        strategy.setPhoneNumber(validPhoneNumber);
        assertEquals(validPhoneNumber, strategy.getPhoneNumber());
        assertTrue(strategy.validateTopUpDetails());
    }

    @Test
    void testInvalidPhoneNumber() {
        String invalidPhoneNumber = "123"; // Invalid phone number (less than 7 digits)
        strategy.setPhoneNumber(invalidPhoneNumber);
        assertEquals(invalidPhoneNumber, strategy.getPhoneNumber());
        assertFalse(strategy.validateTopUpDetails());
    }

    @Test
    void testNullPhoneNumber() {
        strategy.setPhoneNumber(null); // Null phone number
        assertNull(strategy.getPhoneNumber());
        assertFalse(strategy.validateTopUpDetails());
    }

    @Test
    void testInvalidFormatPhoneNumber() {
        String invalidFormatPhoneNumber = "ABC123"; // Invalid format (contains non-numeric characters)
        strategy.setPhoneNumber(invalidFormatPhoneNumber);
        assertEquals(invalidFormatPhoneNumber, strategy.getPhoneNumber());
        assertFalse(strategy.validateTopUpDetails());
    }

    @Test
    void testTopUp() {
        Wallet wallet = new Wallet();
        wallet.setWalletId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        wallet.setBalance(50000);

        when(walletRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(Optional.of(wallet));

        TopUpByEWallet strategy = new TopUpByEWallet(walletRepository);

        double topUpAmount = 10000;
        strategy.topUp("eb558e9f-1c39-460e-8860-71af6af63bd6", topUpAmount);

        verify(walletRepository).save(walletCaptor.capture());

        assertEquals(walletCaptor.getValue().getBalance(), 60000);
    }
}
