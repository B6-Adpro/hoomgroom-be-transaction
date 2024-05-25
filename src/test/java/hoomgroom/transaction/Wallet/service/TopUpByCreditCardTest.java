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
public class TopUpByCreditCardTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private TopUpByCreditCard topUpByCreditCard;

    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet();
        wallet.setWalletId("1");
        wallet.setBalance(100.0);

        topUpByCreditCard.setCardNumber("4103601193829382"); // a valid card number for Luhn's algorithm
    }

    @Test
    void testValidateTopUpDetails_ValidCard() {
        assertTrue(topUpByCreditCard.validateTopUpDetails());
    }

    @Test
    void testValidateTopUpDetails_InvalidCardLength() {
        topUpByCreditCard.setCardNumber("12345678");
        assertFalse(topUpByCreditCard.validateTopUpDetails());
    }

    @Test
    void testValidateTopUpDetails_InvalidCardNonDigits() {
        topUpByCreditCard.setCardNumber("12345678abcd5670");
        assertFalse(topUpByCreditCard.validateTopUpDetails());
    }

    @Test
    void testValidateTopUpDetails_InvalidCardLuhnFail() {
        topUpByCreditCard.setCardNumber("1234567812345678"); // not valid for Luhn's algorithm
        assertFalse(topUpByCreditCard.validateTopUpDetails());
    }

    @Test
    void testTopUp_WalletExists() {
        when(walletRepository.findById("1")).thenReturn(Optional.of(wallet));

        topUpByCreditCard.topUp("1", 50.0);

        assertEquals(150.0, wallet.getBalance());
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    void testTopUp_WalletNotFound() {
        when(walletRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            topUpByCreditCard.topUp("1", 50.0);
        });

        assertEquals("Wallet not found", exception.getMessage());
    }
}
