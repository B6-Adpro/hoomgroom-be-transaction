package hoomgroom.transaction.Wallet.service;

import hoomgroom.transaction.Wallet.model.Wallet;

public interface WalletService {

    public void add(Wallet wallet);
    public Wallet getWalletById(String walletId);
    public void topUp(String walletId, double amount);

}
