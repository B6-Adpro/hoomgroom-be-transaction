package hoomgroom.transaction.controller;

import hoomgroom.transaction.transaksi.dto.RequestTransaksiData;
import hoomgroom.transaction.transaksi.dto.TransaksiData;
import hoomgroom.transaction.transaksi.service.TransaksiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TransaksiController.class)
public class TransaksiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransaksiService transaksiService;

    private TransaksiData transaksiData;
    private RequestTransaksiData requestTransaksiData;

    @BeforeEach
    public void setup() {
        transaksiData = new TransaksiData();
        transaksiData.setTransaksiId(UUID.randomUUID().toString());
        transaksiData.setProdukID("ID123");
        transaksiData.setNamaProduk("WOODEN ECO PANEL");
        transaksiData.setLinkImage("link1");
        transaksiData.setPromoCode("ADPRO123");
        transaksiData.setUsername("DAVID");
        transaksiData.setOriginalPrice(30000L);
        transaksiData.setDiscountPrice(4000L);
        transaksiData.setPotonganPromo(1000L);
        transaksiData.setFinalPrice(3000L);
        transaksiData.setCreatedAt(LocalDateTime.now());

        requestTransaksiData = new RequestTransaksiData();
        requestTransaksiData.setProdukID("ID123");
        requestTransaksiData.setNamaProduk("WOODEN ECO PANEL");
        requestTransaksiData.setLinkImage("link1");
        requestTransaksiData.setPromoCode("ADPRO123");
        requestTransaksiData.setUsername("DAVID");
        requestTransaksiData.setOriginalPrice(30000L);
        requestTransaksiData.setDiscountPrice(4000L);
        requestTransaksiData.setPotonganPromo(1000L);
    }

    @Test
    public void testCreateTransaksi() throws Exception {
        Mockito.when(transaksiService.createTransaksi(any(RequestTransaksiData.class)))
                .thenReturn(transaksiData);

        mockMvc.perform(post("/api/transaksi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"produkID\": \"ID123\", \"namaProduk\": \"WOODEN ECO PANEL\", \"linkImage\": \"link1\", \"promoCode\": \"ADPRO123\", \"username\": \"DAVID\", \"originalPrice\": 30000, \"discountPrice\": 4000, \"potonganPromo\": 1000 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaksiId").value(transaksiData.getTransaksiId()))
                .andExpect(jsonPath("$.produkID").value(transaksiData.getProdukID()))
                .andExpect(jsonPath("$.namaProduk").value(transaksiData.getNamaProduk()))
                .andExpect(jsonPath("$.linkImage").value(transaksiData.getLinkImage()))
                .andExpect(jsonPath("$.promoCode").value(transaksiData.getPromoCode()))
                .andExpect(jsonPath("$.username").value(transaksiData.getUsername()))
                .andExpect(jsonPath("$.originalPrice").value(transaksiData.getOriginalPrice()))
                .andExpect(jsonPath("$.discountPrice").value(transaksiData.getDiscountPrice()))
                .andExpect(jsonPath("$.potonganPromo").value(transaksiData.getPotonganPromo()))
                .andExpect(jsonPath("$.finalPrice").value(transaksiData.getFinalPrice()));
    }

    @Test
    public void testGetAllTransaksi() throws Exception {
        List<TransaksiData> transaksiList = Arrays.asList(transaksiData);
        Mockito.when(transaksiService.findAll()).thenReturn(transaksiList);

        mockMvc.perform(get("/api/transaksi")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].transaksiId").value(transaksiData.getTransaksiId()))
                .andExpect(jsonPath("$[0].produkID").value(transaksiData.getProdukID()))
                .andExpect(jsonPath("$[0].namaProduk").value(transaksiData.getNamaProduk()))
                .andExpect(jsonPath("$[0].linkImage").value(transaksiData.getLinkImage()))
                .andExpect(jsonPath("$[0].promoCode").value(transaksiData.getPromoCode()))
                .andExpect(jsonPath("$[0].username").value(transaksiData.getUsername()))
                .andExpect(jsonPath("$[0].originalPrice").value(transaksiData.getOriginalPrice()))
                .andExpect(jsonPath("$[0].discountPrice").value(transaksiData.getDiscountPrice()))
                .andExpect(jsonPath("$[0].potonganPromo").value(transaksiData.getPotonganPromo()))
                .andExpect(jsonPath("$[0].finalPrice").value(transaksiData.getFinalPrice()));
    }

    @Test
    public void testGetTransaksiById() throws Exception {
        Mockito.when(transaksiService.findById(any(UUID.class))).thenReturn(transaksiData);

        mockMvc.perform(get("/api/transaksi/{id}", transaksiData.getTransaksiId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaksiId").value(transaksiData.getTransaksiId()))
                .andExpect(jsonPath("$.produkID").value(transaksiData.getProdukID()))
                .andExpect(jsonPath("$.namaProduk").value(transaksiData.getNamaProduk()))
                .andExpect(jsonPath("$.linkImage").value(transaksiData.getLinkImage()))
                .andExpect(jsonPath("$.promoCode").value(transaksiData.getPromoCode()))
                .andExpect(jsonPath("$.username").value(transaksiData.getUsername()))
                .andExpect(jsonPath("$.originalPrice").value(transaksiData.getOriginalPrice()))
                .andExpect(jsonPath("$.discountPrice").value(transaksiData.getDiscountPrice()))
                .andExpect(jsonPath("$.potonganPromo").value(transaksiData.getPotonganPromo()))
                .andExpect(jsonPath("$.finalPrice").value(transaksiData.getFinalPrice()));
    }

    @Test
    public void testGetTransaksiByFilter() throws Exception {
        List<TransaksiData> transaksiList = Arrays.asList(transaksiData);
        Mockito.when(transaksiService.findByFilter(anyString(), anyBoolean(), anyBoolean()))
                .thenReturn(transaksiList);

        mockMvc.perform(get("/api/transaksi/filter")
                        .param("username", "DAVID")
                        .param("time", "true")
                        .param("isAscending", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].transaksiId").value(transaksiData.getTransaksiId()))
                .andExpect(jsonPath("$[0].produkID").value(transaksiData.getProdukID()))
                .andExpect(jsonPath("$[0].namaProduk").value(transaksiData.getNamaProduk()))
                .andExpect(jsonPath("$[0].linkImage").value(transaksiData.getLinkImage()))
                .andExpect(jsonPath("$[0].promoCode").value(transaksiData.getPromoCode()))
                .andExpect(jsonPath("$[0].username").value(transaksiData.getUsername()))
                .andExpect(jsonPath("$[0].originalPrice").value(transaksiData.getOriginalPrice()))
                .andExpect(jsonPath("$[0].discountPrice").value(transaksiData.getDiscountPrice()))
                .andExpect(jsonPath("$[0].potonganPromo").value(transaksiData.getPotonganPromo()))
                .andExpect(jsonPath("$[0].finalPrice").value(transaksiData.getFinalPrice()));
    }

    @Test
    public void testDeleteTransaksi() throws Exception {
        mockMvc.perform(delete("/api/transaksi/{id}", transaksiData.getTransaksiId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
