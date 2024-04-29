package hoomgroom.transaction.Wallet.service;

import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.repository.WalletRepository;
import lombok.Getter;
import lombok.Setter;

public class WalletServiceCreditCard implements WalletService{
    @Getter
    @Setter
    private String cardNumber;
    private final WalletRepository walletRepository = new WalletRepository();

    @Override
    public void add(Wallet wallet) {
        walletRepository.addWallet(wallet);
    }
    @Override
    public Wallet getWalletById(String walletId) {
        Wallet wallet = walletRepository.findById(walletId);
        return wallet;
    }

    @Override
    public void topUp(String walletId, double amount) {
        Wallet wallet = walletRepository.findById(walletId);
        wallet.setBalance(wallet.getBalance() + amount);
    }

}
