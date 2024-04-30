package hoomgroom.transaction.Wallet.service;

import hoomgroom.transaction.Wallet.model.Wallet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceEWalletTest {
    @InjectMocks
    WalletServiceEWallet walletService;

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
    void testValidatePhoneNumberValidNumber() {
        String phoneNumber = "+6287888405397";
        walletService.setPhoneNumber(phoneNumber);
        assertEquals(walletService.getPhoneNumber(), phoneNumber);
        assertTrue(walletService.validatePhoneNumber(phoneNumber));
    }

    @Test
    void testValidatePhoneNumberWithoutPlusSign() {
        String phoneNumber = "087888405397";
        walletService.setPhoneNumber(phoneNumber);
        assertEquals(walletService.getPhoneNumber(), phoneNumber);
        assertTrue(walletService.validatePhoneNumber(phoneNumber));
    }

    @Test
    void testValidatePhoneNumberWithInvalidNumberLength() {
        String phoneNumber = "0878884053976969";
        walletService.setPhoneNumber(phoneNumber);
        assertEquals(walletService.getPhoneNumber(), phoneNumber);
        assertFalse(walletService.validatePhoneNumber(phoneNumber));
    }

    @Test
    void testValidatePhoneNumberWithInvalidNumberFormat() {
        String phoneNumber = "0878BBA05397";
        walletService.setPhoneNumber(phoneNumber);
        assertEquals(walletService.getPhoneNumber(), phoneNumber);
        assertFalse(walletService.validatePhoneNumber(phoneNumber));
    }

    @Test
    void testValidatePhoneNumberWithShortNumber() {
        String phoneNumber = "1";
        walletService.setPhoneNumber(phoneNumber);
        assertEquals(walletService.getPhoneNumber(), phoneNumber);
        assertFalse(walletService.validatePhoneNumber(phoneNumber));

        phoneNumber = "123456";
        walletService.setPhoneNumber(phoneNumber);
        assertEquals(walletService.getPhoneNumber(), phoneNumber);
        assertFalse(walletService.validatePhoneNumber(phoneNumber));
    }

    @Test
    void testValidatePhoneNumberWithMinimumNumberWithPlus() {
        String phoneNumber = "+1234567";
        walletService.setPhoneNumber(phoneNumber);
        assertEquals(walletService.getPhoneNumber(), phoneNumber);
        assertTrue(walletService.validatePhoneNumber(phoneNumber));
    }

    @Test
    void testValidatePhoneNumberWithMinimumNumberWithoutPlus() {
        String phoneNumber = "1234567";
        walletService.setPhoneNumber(phoneNumber);
        assertEquals(walletService.getPhoneNumber(), phoneNumber);
        assertTrue(walletService.validatePhoneNumber(phoneNumber));
    }

    @Test
    void testValidatePhoneNumberWithMaximumNumberWithPlus() {
        String phoneNumber = "+628788840539769";
        walletService.setPhoneNumber(phoneNumber);
        assertEquals(walletService.getPhoneNumber(), phoneNumber);
        assertTrue(walletService.validatePhoneNumber(phoneNumber));
    }

    @Test
    void testValidatePhoneNumberWithMaximumNumberWithoutPlus() {
        String phoneNumber = "087888405397696";
        walletService.setPhoneNumber(phoneNumber);
        assertEquals(walletService.getPhoneNumber(), phoneNumber);
        assertTrue(walletService.validatePhoneNumber(phoneNumber));
    }
}
