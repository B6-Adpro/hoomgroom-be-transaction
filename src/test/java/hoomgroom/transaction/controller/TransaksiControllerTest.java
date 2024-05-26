package hoomgroom.transaction.controller;

import hoomgroom.transaction.transaksi.dto.RequestTransaksiData;
import hoomgroom.transaction.transaksi.dto.TransaksiData;
import hoomgroom.transaction.transaksi.service.TransaksiService;
import hoomgroom.transaction.transaksi.controller.TransaksiController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.UUID;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TransaksiController.class)
public class TransaksiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransaksiService transaksiService;

    private TransaksiData transaksiData;
    private RequestTransaksiData requestTransaksiData;

    @BeforeEach
    void setUp() {
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
    void testCreateTransaksi() throws Exception {
        when(transaksiService.createTransaksi(any(RequestTransaksiData.class)))
                .thenReturn(transaksiData);

        MvcResult mvcResult = mockMvc.perform(post("/api/transaksi/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"produkID\": \"ID123\", \"namaProduk\": \"WOODEN ECO PANEL\", \"linkImage\": \"link1\", \"promoCode\": \"ADPRO123\", \"username\": \"DAVID\", \"originalPrice\": 30000, \"discountPrice\": 4000, \"potonganPromo\": 1000 }"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
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
    void testGetAllTransaksi() throws Exception {
        when(transaksiService.findAll()).thenReturn(Collections.singletonList(transaksiData));

        MvcResult mvcResult = mockMvc.perform(get("/api/transaksi/view"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
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
    void testGetTransaksiById() throws Exception {
        when(transaksiService.findById(any(UUID.class))).thenReturn(transaksiData);

        MvcResult mvcResult = mockMvc.perform(get("/api/transaksi/view/{id}", transaksiData.getTransaksiId()))
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(asyncDispatch(mvcResult))
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
    void testDeleteTransaksi() throws Exception {
        doNothing().when(transaksiService).delete(any(UUID.class));

        MvcResult mvcResult = mockMvc.perform(delete("/api/transaksi/delete/{id}", transaksiData.getTransaksiId()))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNoContent());
    }

    @Test
    void testFilterTransaksi() throws Exception {
        List<TransaksiData> filteredTransaksiList = Collections.singletonList(transaksiData);
        when(transaksiService.findByFilter(anyString(), anyBoolean(), anyBoolean()))
                .thenReturn(filteredTransaksiList);

        MvcResult mvcResult = mockMvc.perform(get("/api/transaksi/filter")
                        .param("username", "DAVID")
                        .param("time", "true")
                        .param("isAscending", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(request().asyncStarted())
                        .andReturn();
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].transaksiId").exists())  // Check if transaksiId exists in the response
                // Add more assertions as needed for other fields in TransaksiData
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }
}
