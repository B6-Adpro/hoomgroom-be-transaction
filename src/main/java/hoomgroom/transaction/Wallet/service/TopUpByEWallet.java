package hoomgroom.transaction.Wallet.service;

import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.repository.WalletRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

public class TopUpByEWallet implements TopUpStrategy {
    @Getter
    @Setter
    private String phoneNumber;
    private final WalletRepository walletRepository = new WalletRepository();

    public boolean validateTopUpDetails() {
        //According to the international phone numbering plan (ITU-T E. 164), phone numbers cannot contain more than 15 digits.
        //The shortest international phone numbers in use contain seven digits
        String phonePattern = "^\\+?[0-9]\\d{6,14}$";

        Pattern pattern = Pattern.compile(phonePattern);
        return phoneNumber != null && pattern.matcher(phoneNumber).matches();
    }
    public void topUp(String walletId, double amount) {
        Wallet wallet = walletRepository.findById(walletId);
        wallet.setBalance(wallet.getBalance() + amount);
    }
}
