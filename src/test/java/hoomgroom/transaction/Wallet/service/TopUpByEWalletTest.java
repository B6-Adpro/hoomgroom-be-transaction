package hoomgroom.transaction.Wallet.service;

import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TopUpByEWalletTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private TopUpByEWallet topUpByEWallet;

    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet();
        wallet.setWalletId("1");
        wallet.setBalance(100.0);

        topUpByEWallet.setPhoneNumber("+12345678901"); // a valid phone number
    }

    @Test
    void testValidateTopUpDetails_ValidPhoneNumber() {
        assertTrue(topUpByEWallet.validateTopUpDetails());
    }

    @Test
    void testValidateTopUpDetails_InvalidPhoneNumber() {
        topUpByEWallet.setPhoneNumber("12345"); // too short
        assertFalse(topUpByEWallet.validateTopUpDetails());
    }

    @Test
    void testValidateTopUpDetails_PhoneNumberWithLetters() {
        topUpByEWallet.setPhoneNumber("+1234abc678"); // contains letters
        assertFalse(topUpByEWallet.validateTopUpDetails());
    }

    @Test
    void testValidateTopUpDetails_NullPhoneNumber() {
        topUpByEWallet.setPhoneNumber(null); // null phone number
        assertFalse(topUpByEWallet.validateTopUpDetails());
    }

    @Test
    void testTopUp_WalletExists() {
        when(walletRepository.findById("1")).thenReturn(Optional.of(wallet));

        topUpByEWallet.topUp("1", 50.0);

        assertEquals(150.0, wallet.getBalance());
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    void testTopUp_WalletNotFound() {
        when(walletRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            topUpByEWallet.topUp("1", 50.0);
        });

        assertEquals("Wallet not found", exception.getMessage());
    }
}
