package hoomgroom.transaction.controller;

import hoomgroom.transaction.pengiriman.controller.PengirimanController;
import hoomgroom.transaction.pengiriman.dto.PengirimanData;
import hoomgroom.transaction.pengiriman.model.Pengiriman;
import hoomgroom.transaction.pengiriman.service.PengirimanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PengirimanController.class)
public class PengirimanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PengirimanService pengirimanService;

    private PengirimanData pengirimanData;
    private Pengiriman pengiriman;

    @BeforeEach
    void setUp() {
        pengirimanData = new PengirimanData("1", "T123", "Some Address", "Some Furniture", "DALAM_PROSES");
        pengiriman = Pengiriman.builder()
                .transaksiId("T123")
                .alamatPengiriman("Some Address")
                .furniturePengiriman("Some Furniture")
                .stateString("DALAM_PROSES")
                .build();
    }

    @Test
    void testGetAllPengiriman() throws Exception {
        when(pengirimanService.getAllPengiriman()).thenReturn(Arrays.asList(pengirimanData));

        mockMvc.perform(get("/pengiriman/view"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].transaksiId").value("T123"))
                .andExpect(jsonPath("$[0].alamat").value("Some Address"))
                .andExpect(jsonPath("$[0].furniture").value("Some Furniture"))
                .andExpect(jsonPath("$[0].stateString").value("DALAM_PROSES"));
    }

    @Test
    void testGetPengirimanById() throws Exception {
        when(pengirimanService.getPengirimanById(anyLong())).thenReturn(Optional.of(pengirimanData));

        mockMvc.perform(get("/pengiriman/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.transaksiId").value("T123"))
                .andExpect(jsonPath("$.alamat").value("Some Address"))
                .andExpect(jsonPath("$.furniture").value("Some Furniture"))
                .andExpect(jsonPath("$.stateString").value("DALAM_PROSES"));
    }

    @Test
    void testCreatePengiriman() throws Exception {
        when(pengirimanService.createPengiriman(any(Pengiriman.class))).thenReturn(pengiriman);

        mockMvc.perform(post("/pengiriman/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"transaksiId\": \"T123\", \"alamatPengiriman\": \"Some Address\", \"furniturePengiriman\": \"Some Furniture\", \"stateString\": \"DALAM_PROSES\" }"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateStatePengiriman() throws Exception {
        doNothing().when(pengirimanService).updatePengiriman(anyLong());

        mockMvc.perform(put("/pengiriman/update/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePengiriman() throws Exception {
        doNothing().when(pengirimanService).deletePengiriman(anyLong());

        mockMvc.perform(delete("/pengiriman/delete/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeletePengiriman_NotFound() throws Exception {
        doThrow(new RuntimeException()).when(pengirimanService).deletePengiriman(anyLong());

        mockMvc.perform(delete("/pengiriman/delete/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}
