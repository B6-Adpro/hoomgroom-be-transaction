package hoomgroom.transaction.Wallet.controller;

import hoomgroom.transaction.Wallet.service.TopUpByCreditCard;
import hoomgroom.transaction.Wallet.service.TopUpByEWallet;
import hoomgroom.transaction.Wallet.service.TopUpStrategy;
import hoomgroom.transaction.Wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/topup")
    public ResponseEntity<String> topUpWallet(@RequestBody TopUpRequest request) {
        try {
            TopUpStrategy strategy;
            if ("creditCard".equals(request.getStrategy())) {
                strategy = new TopUpByCreditCard(walletService.getWalletRepository());
            } else if ("eWallet".equals(request.getStrategy())) {
                strategy = new TopUpByEWallet(walletService.getWalletRepository());
            } else {
                return ResponseEntity.badRequest().body("Invalid top-up strategy");
            }
            // Set the strategy in the service
            walletService.setStrategy(strategy);
            // Perform the top-up operation
            walletService.topUp(request.getWalletId(), request.getAmount());

            return ResponseEntity.ok("Wallet topped up successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        } finally {
            walletService.setStrategy(null); // Clear the top-up strategy after usage
        }
    }

    // Inner class to represent the request body for top-up request
    public static class TopUpRequest {
        private String walletId;
        private double amount;
        private String strategy; // Use a String to represent the selected strategy

        // Getters and setters
        public String getWalletId() {
            return walletId;
        }

        public void setWalletId(String walletId) {
            this.walletId = walletId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getStrategy() {
            return strategy;
        }

        public void setStrategy(String strategy) {
            this.strategy = strategy;
        }
    }
}