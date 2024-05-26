package hoomgroom.transaction.Wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hoomgroom.transaction.Auth.model.User;
import hoomgroom.transaction.Auth.service.JwtService;
import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {

    @InjectMocks
    private WalletController walletController;

    @Mock
    private JwtService jwtService;

    @Mock
    private WalletService walletService;

    private MockMvc mockMvc;

    private static final String JWT_TOKEN = "Bearer mock-jwt-token";
    private static final String USER_ID = "123e4567-e89b-12d3-a456-426614174000";

    @BeforeEach
    public void setup() {
        walletController.jwtService=jwtService;
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
    }

    @Test
    public void testCreateWallet() throws Exception {
        UUID userId = UUID.fromString(USER_ID);
        Wallet wallet = new Wallet();
        wallet.setWalletId(UUID.randomUUID().toString());
        wallet.setUserId(userId);

        User user = new User();
        user.setId(userId);

        when(jwtService.extractUser(any(String.class))).thenReturn(user);
        when(walletService.findByUserId(userId)).thenReturn(null);
        when(walletService.add(userId)).thenReturn(CompletableFuture.completedFuture(wallet));

        mockMvc.perform(post("/wallet/create")
                        .header("Authorization", JWT_TOKEN))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.walletId").value(wallet.getWalletId()));

        verify(walletService, times(1)).add(userId);
    }

    @Test
    public void testCreateWalletConflict() throws Exception {
        UUID userId = UUID.fromString(USER_ID);
        Wallet existingWallet = new Wallet();
        existingWallet.setWalletId(UUID.randomUUID().toString());
        existingWallet.setUserId(userId);

        User user = new User();
        user.setId(userId);

        when(jwtService.extractUser(any(String.class))).thenReturn(user);
        when(walletService.findByUserId(userId)).thenReturn(existingWallet);

        mockMvc.perform(post("/wallet/create")
                        .header("Authorization", JWT_TOKEN))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.walletId").value(existingWallet.getWalletId()));

        verify(walletService, never()).add(userId);
    }

    @Test
    public void testTopUpWallet() throws Exception {
        WalletController.TopUpRequest topUpRequest = new WalletController.TopUpRequest();
        topUpRequest.setWalletId("wallet-id");
        topUpRequest.setAmount(100.0);
        topUpRequest.setStrategy("Credit Card");
        topUpRequest.setCardNumber("1234-5678-9012-3456");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(topUpRequest);

        UUID userId = UUID.fromString(USER_ID);
        User user = new User();
        user.setId(userId);

        Wallet wallet = new Wallet();
        wallet.setWalletId("wallet-id");
        wallet.setUserId(userId);

        when(jwtService.extractUser(any(String.class))).thenReturn(user);
        when(walletService.findByUserId(userId)).thenReturn(wallet);
        doNothing().when(walletService).setStrategy(any());
        when(walletService.topUp(anyString(), anyDouble())).thenReturn(CompletableFuture.completedFuture(null));

        mockMvc.perform(post("/wallet/topup")
                        .header("Authorization", JWT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Wallet topped up successfully."));

        verify(walletService, times(1)).topUp("wallet-id", 100.0);
    }

    @Test
    public void testGetWalletById() throws Exception {
        UUID userId = UUID.fromString(USER_ID);
        Wallet wallet = new Wallet();
        wallet.setWalletId("wallet-id");
        wallet.setUserId(userId);

        User user = new User();
        user.setId(userId);

        when(jwtService.extractUser(any(String.class))).thenReturn(user);
        when(walletService.findByUserId(userId)).thenReturn(wallet);

        mockMvc.perform(get("/wallet/get/wallet-id")
                        .header("Authorization", JWT_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(wallet.getWalletId()));

        verify(walletService, times(1)).findByUserId(userId);
    }

    @Test
    public void testGetWalletByIdNotFound() throws Exception {
        UUID userId = UUID.fromString(USER_ID);
        User user = new User();
        user.setId(userId);

        when(jwtService.extractUser(any(String.class))).thenReturn(user);
        when(walletService.findByUserId(userId)).thenReturn(null);

        mockMvc.perform(get("/wallet/get/wallet-id")
                        .header("Authorization", JWT_TOKEN))
                .andExpect(status().isNotFound());

        verify(walletService, times(1)).findByUserId(userId);
    }
}
