package hoomgroom.transaction.Wallet.controller;
import hoomgroom.transaction.Auth.model.User;
import hoomgroom.transaction.Auth.service.JwtService;
import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.service.TopUpByCreditCard;
import hoomgroom.transaction.Wallet.service.TopUpByEWallet;
import hoomgroom.transaction.Wallet.service.TopUpStrategy;
import hoomgroom.transaction.Wallet.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    JwtService jwtService;
    private final WalletService walletService;
    private static final String JWT_HEADER = "Authorization";
    private static final String JWT_TOKEN_PREFIX = "Bearer";
    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/create")
    public ResponseEntity<Wallet> createWallet(@NonNull HttpServletRequest request) {
        final String authHeader = request.getHeader(JWT_HEADER);
        String jwtToken = authHeader.substring(JWT_TOKEN_PREFIX.length());
        User userDetails = jwtService.extractUser(jwtToken);
        UUID uid = userDetails.getId();

        // Check if a wallet already exists for the user
        Wallet existingWallet = walletService.findByUserId(uid);
        if (existingWallet != null) {
            // If a wallet already exists, return a conflict response
            return ResponseEntity.status(HttpStatus.CONFLICT).body(existingWallet);
        }

        // If no existing wallet, create a new one
        Wallet wallet = walletService.add(uid);
        return ResponseEntity.status(HttpStatus.CREATED).body(wallet);
    }

    @PostMapping("/topup")
    public ResponseEntity<String> topUpWallet(@RequestBody TopUpRequest request) {
        try {
            TopUpStrategy strategy;
            if ("Credit Card".equals(request.getStrategy())) {
                TopUpByCreditCard creditCardStrategy = new TopUpByCreditCard(walletService.getWalletRepository());
                creditCardStrategy.setCardNumber(request.getCardNumber());
                strategy = creditCardStrategy;
            } else if ("E-Wallet".equals(request.getStrategy())) {
                TopUpByEWallet eWalletStrategy = new TopUpByEWallet(walletService.getWalletRepository());
                eWalletStrategy.setPhoneNumber(request.getPhoneNumber());
                strategy = eWalletStrategy;
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

    @GetMapping("/get/{walletId}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable String walletId,@NonNull HttpServletRequest request) {
        final String authHeader = request.getHeader(JWT_HEADER);
        String jwtToken = authHeader.substring(JWT_TOKEN_PREFIX.length());
        User userDetails = jwtService.extractUser(jwtToken);
        UUID uid = userDetails.getId();
        Wallet wallet = walletService.findByUserId(uid);
        if (wallet != null && wallet.getWalletId().equals(walletId)) {
            return ResponseEntity.ok(wallet);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Inner class to represent the request body for top-up request
    public static class TopUpRequest {
        private String walletId;
        private double amount;
        private String strategy;
        private String cardNumber;
        private String phoneNumber;

        // Getters and setters
        public String getWalletId() {
            return walletId;
        }

        public double getAmount() {
            return amount;
        }

        public String getStrategy() {
            return strategy;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public void setPhoneNumber(String number) {
            this.phoneNumber = number;
        }

        public void setWalletId(String Id) {
            this.walletId=Id;
        }

        public void setAmount(double v) {
            this.amount=v;
        }

        public void setStrategy(String s) {
            this.strategy=s;
        }
    }
}