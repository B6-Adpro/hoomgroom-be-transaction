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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<ResponseEntity<?>> createWallet(@NonNull HttpServletRequest request) {
        Logger logger = LoggerFactory.getLogger(WalletController.class);
        logger.info("test");
        final String authHeader = request.getHeader(JWT_HEADER);
        if (authHeader == null) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"));
        }
        String jwtToken = authHeader.substring(JWT_TOKEN_PREFIX.length());
        User userDetails = jwtService.extractUser(jwtToken);

        UUID uid = userDetails.getId();
        logger.info(uid.toString());

        Wallet existingWallet = walletService.findByUserId(uid);
        if (existingWallet != null) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.CONFLICT).body(existingWallet));
        }

        return walletService.add(uid).thenApply(wallet -> ResponseEntity.status(HttpStatus.CREATED).body(wallet));
    }

    @PostMapping("/topup")
    public CompletableFuture<ResponseEntity<String>> topUpWallet(@NonNull HttpServletRequest request, @RequestBody TopUpRequest topUpRequest) {
        try {
            final String authHeader = request.getHeader(JWT_HEADER);
            if (authHeader == null) {
                return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"));
            }
            String jwtToken = authHeader.substring(JWT_TOKEN_PREFIX.length());
            User userDetails = jwtService.extractUser(jwtToken);
            UUID uid = userDetails.getId();
            Wallet wallet = walletService.findByUserId(uid);
            TopUpStrategy strategy;
            if ("Credit Card".equals(topUpRequest.getStrategy())) {
                TopUpByCreditCard creditCardStrategy = new TopUpByCreditCard(walletService.getWalletRepository());
                creditCardStrategy.setCardNumber(topUpRequest.getCardNumber());
                strategy = creditCardStrategy;
            } else if ("E-Wallet".equals(topUpRequest.getStrategy())) {
                TopUpByEWallet eWalletStrategy = new TopUpByEWallet(walletService.getWalletRepository());
                eWalletStrategy.setPhoneNumber(topUpRequest.getPhoneNumber());
                strategy = eWalletStrategy;
            } else {
                return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Invalid top-up strategy"));
            }
            // Set the strategy in the service
            walletService.setStrategy(strategy);
            // Perform the top-up operation
            return walletService.topUp(wallet.getWalletId(), topUpRequest.getAmount())
                    .thenApply(result -> ResponseEntity.ok("Wallet topped up successfully."))
                    .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request."));
        } catch (IllegalArgumentException e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body(e.getMessage()));
        }
    }

    @GetMapping("/get/{walletId}")
    public CompletableFuture<ResponseEntity<?>> getWalletById(@PathVariable String walletId, @NonNull HttpServletRequest request) {
        final String authHeader = request.getHeader(JWT_HEADER);
        if (authHeader == null) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"));
        }
        String jwtToken = authHeader.substring(JWT_TOKEN_PREFIX.length());
        User userDetails = jwtService.extractUser(jwtToken);
        UUID uid = userDetails.getId();
        Wallet wallet = walletService.findByUserId(uid);
        if (wallet != null && wallet.getWalletId().equals(walletId)) {
            return CompletableFuture.completedFuture(ResponseEntity.ok(wallet));
        } else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
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