package hoomgroom.transaction.Wallet.service;

import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.repository.WalletRepository;
import lombok.Getter;
import lombok.Setter;

@Setter
public class WalletService {
    private final WalletRepository walletRepository = new WalletRepository();
    @Getter
    private String method;
    private TopUpStrategy strategy;
    public void add(Wallet wallet) {
        walletRepository.addWallet(wallet);
    }
    public Wallet getWalletById(String walletId) {
        Wallet wallet = walletRepository.findById(walletId);
        return wallet;
    }

    public void topUp(String walletId, double amount) {
        if (strategy.validateTopUpDetails()) {
            strategy.topUp(walletId, amount);
        }
    }
}
