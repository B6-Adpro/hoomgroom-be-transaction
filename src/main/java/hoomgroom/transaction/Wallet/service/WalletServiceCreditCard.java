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

    public Boolean validateCardNumber(String cardNumber) {
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
}
