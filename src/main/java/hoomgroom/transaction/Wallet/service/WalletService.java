package hoomgroom.transaction.Wallet.service;

import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.repository.WalletRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Setter
@Getter
public class WalletService {
    private final WalletRepository walletRepository;
    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet getWalletById(String walletId) {
        return walletRepository.findById(walletId)
                .orElse(null);
    }

    private TopUpStrategy strategy;
    public Wallet add(UUID userId) {
        Wallet existingWallet = walletRepository.findByUserId(userId);
        if (existingWallet != null) {
            throw new IllegalStateException("Wallet for user already exists");
        }

        Wallet wallet = new Wallet(userId);
        return walletRepository.save(wallet);
    }

    public void topUp(String walletId, double amount) {
        if (strategy == null) {
            throw new IllegalArgumentException("TopUp strategy is not set");
        }

        if (!strategy.validateTopUpDetails()) {
            throw new IllegalArgumentException("Invalid top-up details");
        }

        strategy.topUp(walletId, amount);
    }

    public Wallet findByUserId(UUID uid) {
        return walletRepository.findByUserId(uid);
    }
}
