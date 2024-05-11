package hoomgroom.transaction.Wallet.repository;

import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @Test
    public void testSaveWallet() {
        // Given
        Wallet wallet = new Wallet();
        wallet.setWalletId(UUID.randomUUID().toString());
        wallet.setBalance(0.0);

        // When
        Wallet savedWallet = walletRepository.save(wallet);

        // Then
        assertThat(savedWallet).isNotNull();
        assertThat(savedWallet.getWalletId()).isNotNull();
        assertEquals(savedWallet.getBalance(), 0.0);
    }

    @Test
    public void testFindById() {
        Wallet wallet = new Wallet();
        wallet.setWalletId(UUID.randomUUID().toString());
        wallet.setBalance(100.0);
        walletRepository.save(wallet);

        Optional<Wallet> foundWallet = walletRepository.findById(wallet.getWalletId());

        assertThat(foundWallet).isPresent();
        assertEquals(foundWallet.get().getWalletId(), wallet.getWalletId());
        assertEquals(foundWallet.get().getBalance(), wallet.getBalance());
    }

    @Test
    public void testUpdateWallet() {
        Wallet wallet = new Wallet();
        wallet.setWalletId(UUID.randomUUID().toString());
        wallet.setBalance(300.0);
        walletRepository.save(wallet);

        wallet.setBalance(400.0);
        Wallet updatedWallet = walletRepository.save(wallet);

        assertThat(updatedWallet).isNotNull();
        assertEquals(updatedWallet.getWalletId(), wallet.getWalletId());
        assertEquals(updatedWallet.getBalance(), wallet.getBalance());
    }

    @Test
    public void testFindIfMoreThanOne() {
        Wallet wallet = new Wallet();
        wallet.setWalletId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        wallet.setBalance(600.0);
        walletRepository.save(wallet);

        Wallet wallet2 = new Wallet();
        wallet2.setBalance(500.0);
        walletRepository.save(wallet2);

        Optional<Wallet> foundWallet = walletRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertThat(foundWallet).isPresent();
        assertNotEquals(wallet2.getWalletId(), foundWallet.get().getWalletId());
        assertNotEquals(wallet2.getBalance(), foundWallet.get().getBalance());

        assertEquals(wallet.getWalletId(), foundWallet.get().getWalletId());
        assertEquals(wallet.getBalance(), foundWallet.get().getBalance());
    }
}