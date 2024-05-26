package hoomgroom.transaction.Wallet.service;

import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.repository.WalletRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class TopUpByEWallet implements TopUpStrategy {
    @Getter
    @Setter
    private String phoneNumber;
    private final WalletRepository walletRepository;

    @Autowired
    public TopUpByEWallet(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public boolean validateTopUpDetails() {
        //According to the international phone numbering plan (ITU-T E. 164), phone numbers cannot contain more than 15 digits.
        //The shortest international phone numbers in use contain seven digits
        String phonePattern = "^\\+?[0-9]\\d{6,14}$";

        Pattern pattern = Pattern.compile(phonePattern);
        return phoneNumber != null && pattern.matcher(phoneNumber).matches();
    }
    public void topUp(String walletId, double amount) {
        Optional<Wallet> found = walletRepository.findById(walletId);
        if (found.isPresent()) {
            Wallet wallet = found.get();
            wallet.setBalance(wallet.getBalance() + amount);
            walletRepository.save(wallet);
        } else {
            throw new IllegalArgumentException("Wallet not found");
        }
    }
}
