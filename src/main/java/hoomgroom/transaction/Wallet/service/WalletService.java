package hoomgroom.transaction.Wallet.service;

import hoomgroom.transaction.Wallet.controller.WalletController;
import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.repository.WalletRepository;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
    @Async("taskExecutor")
    public CompletableFuture<Wallet> add(UUID userId) {
        Logger logger = LoggerFactory.getLogger(WalletService.class);
        logger.info("testService");
        Wallet existingWallet = walletRepository.findByUserId(userId);
        logger.info("found");
        if (existingWallet != null) {
            throw new IllegalStateException("Wallet for user already exists");
        }

        Wallet wallet = new Wallet(userId);
        logger.info("newwallet");
        Wallet savedWallet = walletRepository.save(wallet);
        logger.info("created");
        return CompletableFuture.completedFuture(savedWallet);
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> topUp(String walletId, double amount) {
        if (strategy == null) {
            throw new IllegalArgumentException("TopUp strategy is not set");
        }

        if (!strategy.validateTopUpDetails()) {
            throw new IllegalArgumentException("Invalid top-up details");
        }

        strategy.topUp(walletId, amount);
        return CompletableFuture.completedFuture(null);
    }

    public Wallet findByUserId(UUID uid) {
        return walletRepository.findByUserId(uid);
    }

    @Async("taskExecutor")
    public CompletableFuture<String> pay(UUID userId, double amount) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) {
            return CompletableFuture.completedFuture("Wallet not found");
        }

        if (wallet.getBalance() < amount) {
            return CompletableFuture.completedFuture("Balance is not enough");
        }

        wallet.setBalance(wallet.getBalance() - amount);
        walletRepository.save(wallet);

        return CompletableFuture.completedFuture("Payment successful");
    }
}
