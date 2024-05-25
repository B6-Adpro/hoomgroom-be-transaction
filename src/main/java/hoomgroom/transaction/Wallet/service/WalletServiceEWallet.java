package hoomgroom.transaction.Wallet.service;

import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.repository.WalletRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

public class WalletServiceEWallet implements WalletService{
    @Getter
    @Setter
    private String phoneNumber;
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

    public Boolean validatePhoneNumber(String phoneNumber) {
        //According to the international phone numbering plan (ITU-T E. 164), phone numbers cannot contain more than 15 digits.
        //The shortest international phone numbers in use contain seven digits
        String phonePattern = "^\\+?[0-9]\\d{6,14}$";

        Pattern pattern = Pattern.compile(phonePattern);
        return phoneNumber != null && pattern.matcher(phoneNumber).matches();
    }
}
