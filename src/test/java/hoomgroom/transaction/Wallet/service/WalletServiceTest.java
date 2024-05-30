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
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TopUpByEWallet topUpByEWallet;

    @Mock
    private TopUpByCreditCard topUpByCreditCard;

    @InjectMocks
    private WalletService walletService;

    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet();
        wallet.setWalletId("1");
        wallet.setBalance(100.0);
    }

    @Test
    void testGetWalletById_WalletExists() {
        when(walletRepository.findById("1")).thenReturn(Optional.of(wallet));

        Wallet foundWallet = walletService.getWalletById("1");

        assertEquals(wallet, foundWallet);
        verify(walletRepository, times(1)).findById("1");
    }

    @Test
    void testGetWalletById_WalletNotFound() {
        when(walletRepository.findById("1")).thenReturn(Optional.empty());

        Wallet foundWallet = walletService.getWalletById("1");

        assertNull(foundWallet);
        verify(walletRepository, times(1)).findById("1");
    }

    @Test
    void testAddWallet() {
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

        Wallet createdWallet = walletService.add(UUID.randomUUID()).join();

        assertNotNull(createdWallet);
        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    void testTopUp_WithValidEWalletStrategy() {
        walletService.setStrategy(topUpByEWallet);
        when(topUpByEWallet.validateTopUpDetails()).thenReturn(true);
        doNothing().when(topUpByEWallet).topUp("1", 50.0);

        walletService.topUp("1", 50.0);

        verify(topUpByEWallet, times(1)).validateTopUpDetails();
        verify(topUpByEWallet, times(1)).topUp("1", 50.0);
    }

    @Test
    void testTopUp_WithInvalidEWalletStrategy() {
        walletService.setStrategy(topUpByEWallet);
        when(topUpByEWallet.validateTopUpDetails()).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            walletService.topUp("1", 50.0);
        });

        assertEquals("Invalid top-up details", exception.getMessage());
        verify(topUpByEWallet, times(1)).validateTopUpDetails();
        verify(topUpByEWallet, times(0)).topUp(anyString(), anyDouble());
    }

    @Test
    void testTopUp_WithValidCreditCardStrategy() {
        walletService.setStrategy(topUpByCreditCard);
        when(topUpByCreditCard.validateTopUpDetails()).thenReturn(true);
        doNothing().when(topUpByCreditCard).topUp("1", 50.0);

        walletService.topUp("1", 50.0);

        verify(topUpByCreditCard, times(1)).validateTopUpDetails();
        verify(topUpByCreditCard, times(1)).topUp("1", 50.0);
    }

    @Test
    void testTopUp_WithInvalidCreditCardStrategy() {
        walletService.setStrategy(topUpByCreditCard);
        when(topUpByCreditCard.validateTopUpDetails()).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            walletService.topUp("1", 50.0);
        });

        assertEquals("Invalid top-up details", exception.getMessage());
        verify(topUpByCreditCard, times(1)).validateTopUpDetails();
        verify(topUpByCreditCard, times(0)).topUp(anyString(), anyDouble());
    }

    @Test
    void testTopUp_NoStrategySet() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            walletService.topUp("1", 50.0);
        });

        assertEquals("TopUp strategy is not set", exception.getMessage());
    }

    @Test
    void testPay_WithSufficientBalance() {
        Wallet wallet = new Wallet();
        wallet.setBalance(100.0);
        when(walletRepository.findByUserId(any(UUID.class))).thenReturn(wallet);

        CompletableFuture<String> result = walletService.pay(UUID.randomUUID(), 50.0);

        assertEquals("Payment successful", result.join());
        assertEquals(50.0, wallet.getBalance());
        verify(walletRepository, times(1)).findByUserId(any(UUID.class));
        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    void testPay_WithInsufficientBalance() {
        Wallet wallet = new Wallet();
        wallet.setBalance(30.0); // balance less than payment amount
        when(walletRepository.findByUserId(any(UUID.class))).thenReturn(wallet);

        CompletableFuture<String> result = walletService.pay(UUID.randomUUID(), 50.0);

        assertEquals("Balance is not enough", result.join());
        assertEquals(30.0, wallet.getBalance()); // balance should remain unchanged
        verify(walletRepository, times(1)).findByUserId(any(UUID.class));
        verify(walletRepository, times(0)).save(any(Wallet.class)); // wallet should not be saved
    }

    @Test
    void testPay_WalletNotFound() {
        when(walletRepository.findByUserId(any(UUID.class))).thenReturn(null);

        CompletableFuture<String> result = walletService.pay(UUID.randomUUID(), 50.0);

        assertEquals("Wallet not found", result.join());
        verify(walletRepository, times(1)).findByUserId(any(UUID.class));
        verify(walletRepository, times(0)).save(any(Wallet.class)); // wallet should not be saved
    }

}
