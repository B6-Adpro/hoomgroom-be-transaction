package hoomgroom.transaction.Wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hoomgroom.transaction.Wallet.model.Wallet;
import hoomgroom.transaction.Wallet.service.TopUpByCreditCard;
import hoomgroom.transaction.Wallet.service.TopUpByEWallet;
import hoomgroom.transaction.Wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WalletController.class)
@ExtendWith(SpringExtension.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    private Wallet wallet;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        wallet = new Wallet();
        wallet.setWalletId("1");
        wallet.setBalance(100.0);
    }

    @Test
    void testCreateWallet() throws Exception {
        when(walletService.add()).thenReturn(wallet);

        mockMvc.perform(post("/wallets/create"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.walletId").value("1"))
                .andExpect(jsonPath("$.balance").value(100.0));

        verify(walletService, times(1)).add();
    }

    @Test
    void testGetWalletById_WalletExists() throws Exception {
        when(walletService.getWalletById("1")).thenReturn(wallet);

        mockMvc.perform(get("/wallets/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.walletId").value("1"))
                .andExpect(jsonPath("$.balance").value(100.0));

        verify(walletService, times(1)).getWalletById("1");
    }

    @Test
    void testGetWalletById_WalletNotFound() throws Exception {
        when(walletService.getWalletById("1")).thenReturn(null);

        mockMvc.perform(get("/wallets/1"))
                .andExpect(status().isNotFound());

        verify(walletService, times(1)).getWalletById("1");
    }

    @Test
    void testTopUpWallet_CreditCard_Success() throws Exception {
        WalletController.TopUpRequest request = new WalletController.TopUpRequest();
        request.setCardNumber("1234567812345678");
        request.setWalletId("1");
        request.setAmount(50.0);
        request.setStrategy("Credit Card");

        when(walletService.getWalletById("1")).thenReturn(wallet);
        doNothing().when(walletService).setStrategy(any(TopUpByCreditCard.class));
        doNothing().when(walletService).topUp(anyString(), anyDouble());

        mockMvc.perform(post("/wallets/topup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Wallet topped up successfully."));

        verify(walletService, times(1)).setStrategy(any(TopUpByCreditCard.class));
        verify(walletService, times(1)).setStrategy(null); // Clear strategy
        verify(walletService, times(1)).topUp(anyString(), anyDouble());
    }

    @Test
    void testTopUpWallet_CreditCard_InvalidDetails() throws Exception {
        WalletController.TopUpRequest request = new WalletController.TopUpRequest();
        request.setCardNumber("invalid");
        request.setWalletId("1");
        request.setAmount(50.0);
        request.setStrategy("Credit Card");

        when(walletService.getWalletById("1")).thenReturn(wallet);
        doThrow(new IllegalArgumentException("Invalid top-up details")).when(walletService).topUp(anyString(), anyDouble());

        mockMvc.perform(post("/wallets/topup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid top-up details"));

        verify(walletService, times(1)).setStrategy(any(TopUpByCreditCard.class));
        verify(walletService, times(1)).setStrategy(null); // Clear strategy
        verify(walletService, times(1)).topUp(anyString(), anyDouble());
    }

    @Test
    void testTopUpWallet_EWallet_Success() throws Exception {
        WalletController.TopUpRequest request = new WalletController.TopUpRequest();
        request.setPhoneNumber("+12345678901");
        request.setWalletId("1");
        request.setAmount(50.0);
        request.setStrategy("E-Wallet");

        when(walletService.getWalletById("1")).thenReturn(wallet);
        doNothing().when(walletService).setStrategy(any(TopUpByEWallet.class));
        doNothing().when(walletService).topUp(anyString(), anyDouble());

        mockMvc.perform(post("/wallets/topup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Wallet topped up successfully."));

        verify(walletService, times(1)).setStrategy(any(TopUpByEWallet.class));
        verify(walletService, times(1)).setStrategy(null); // Clear strategy
        verify(walletService, times(1)).topUp(anyString(), anyDouble());
    }

    @Test
    void testTopUpWallet_EWallet_InvalidDetails() throws Exception {
        WalletController.TopUpRequest request = new WalletController.TopUpRequest();
        request.setPhoneNumber("invalid");
        request.setWalletId("1");
        request.setAmount(50.0);
        request.setStrategy("E-Wallet");

        when(walletService.getWalletById("1")).thenReturn(wallet);
        doThrow(new IllegalArgumentException("Invalid top-up details")).when(walletService).topUp(anyString(), anyDouble());

        mockMvc.perform(post("/wallets/topup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid top-up details"));

        verify(walletService, times(1)).setStrategy(any(TopUpByEWallet.class));
        verify(walletService, times(1)).setStrategy(null); // Clear strategy
        verify(walletService, times(1)).topUp(anyString(), anyDouble());
    }
}
