package hoomgroom.transaction.Wallet.service;

import hoomgroom.transaction.Wallet.model.Wallet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
    @Test
    void tesValidateCardNumberWithValidNumber() {
        String cardNumber = "4103601193829382";
        walletService.setCardNumber(cardNumber);
        assertEquals(walletService.getCardNumber(), cardNumber);
        assertTrue(walletService.validateCardNumber(cardNumber));
    }

    @Test
    void testValidateCardNumberWithInvalidNumberFormat() {
        String cardNumber = "4103601193ABCDEF";
        walletService.setCardNumber(cardNumber);
        assertEquals(walletService.getCardNumber(), cardNumber);
        assertFalse(walletService.validateCardNumber(cardNumber));
    }

    @Test
    void testValidateCardNumberWithInvalidNumber() {
        String cardNumber = "4539704384706391";
        walletService.setCardNumber(cardNumber);
        assertEquals(walletService.getCardNumber(), cardNumber);
        assertFalse(walletService.validateCardNumber(cardNumber));
    }

    @Test
    void testValidateCardAfterInvalid() {
        String cardNumber = "4539704384706391";
        walletService.setCardNumber(cardNumber);
        assertEquals(walletService.getCardNumber(), cardNumber);
        assertFalse(walletService.validateCardNumber(cardNumber));

        cardNumber = "4539704384706395";
        walletService.setCardNumber(cardNumber);
        assertEquals(walletService.getCardNumber(), cardNumber);
        assertTrue(walletService.validateCardNumber(cardNumber));
    }

    @Test
    void testInvalidCreditCardNumberLength() {
        String cardNumber = "45391";
        walletService.setCardNumber(cardNumber);
        assertEquals(walletService.getCardNumber(), cardNumber);
        assertFalse(walletService.validateCardNumber(cardNumber));
    }
}
