package hoomgroom.transaction.Wallet.service;

import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.repository.WalletRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopUpByCreditCard implements TopUpStrategy {
    @Getter
    @Setter
    private String cardNumber;
    private final WalletRepository walletRepository;

    @Autowired
    public TopUpByCreditCard(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public boolean validateTopUpDetails() {
        // Check if cardNumberStr is not null and contains only digits
        if (cardNumber == null || !cardNumber.matches("\\d+")) {
            return false;
        }

        // Check if the length of cardNumberStr is exactly 16 digits
        if (cardNumber.length() != 16) {
            return false;
        }

        //applying Luhn's algorithm
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit % 10) + 1;
                }
            }
            sum += digit;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
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
