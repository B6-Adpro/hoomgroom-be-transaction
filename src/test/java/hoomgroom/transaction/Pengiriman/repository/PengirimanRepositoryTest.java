package hoomgroom.transaction.Pengiriman.repository;

import hoomgroom.transaction.pengiriman.dto.PengirimanData;
import hoomgroom.transaction.pengiriman.dto.PengirimanUpdateRequest;
import hoomgroom.transaction.pengiriman.model.Pengiriman;
import hoomgroom.transaction.pengiriman.repository.PengirimanRepository;
import hoomgroom.transaction.pengiriman.service.PengirimanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PengirimanRepositoryTest {

    @Mock
    private PengirimanRepository pengirimanRepository;

    @InjectMocks
    private PengirimanService pengirimanService;

    private Pengiriman pengiriman;

    @BeforeEach
    void setUp() {
        pengiriman = Pengiriman.builder()
                .pengirimanId(1L)
                .transaksiId("1")
                .alamatPengiriman("Test Address")
                .furniturePengiriman("Test Furniture")
                .stateString("VERIFIKASI")
                .build();
    }

    @Test
    void testCreatePengiriman() {
        // Arrange
        when(pengirimanRepository.save(any(Pengiriman.class))).thenReturn(pengiriman);

        // Act
        Pengiriman createdPengiriman = pengirimanService.createPengiriman(pengiriman);

        // Assert
        assertNotNull(createdPengiriman);
        assertEquals(pengiriman.getPengirimanId(), createdPengiriman.getPengirimanId());
        ArgumentCaptor<Pengiriman> pengirimanCaptor = ArgumentCaptor.forClass(Pengiriman.class);
        verify(pengirimanRepository, times(1)).save(pengirimanCaptor.capture());
        Pengiriman capturedPengiriman = pengirimanCaptor.getValue();
        assertEquals(pengiriman.getTransaksiId(), capturedPengiriman.getTransaksiId());
        assertEquals(pengiriman.getAlamatPengiriman(), capturedPengiriman.getAlamatPengiriman());
    }

    @Test
    void testGetAllPengiriman() {
        when(pengirimanRepository.findAll()).thenReturn(List.of(pengiriman));

        List<PengirimanData> pengirimanList = pengirimanService.getAllPengiriman();

        assertNotNull(pengirimanList);
        assertEquals(1, pengirimanList.size());
        verify(pengirimanRepository, times(1)).findAll();
    }

    @Test
    void testGetPengirimanById() {
        when(pengirimanRepository.findById(1L)).thenReturn(Optional.of(pengiriman));

        Optional<PengirimanData> foundPengiriman = pengirimanService.getPengirimanById(1L);

        assertTrue(foundPengiriman.isPresent());
        assertEquals(pengiriman.getTransaksiId(), foundPengiriman.get().getTransaksiId());
        verify(pengirimanRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdatePengiriman() {
        when(pengirimanRepository.findById(1L)).thenReturn(Optional.of(pengiriman));
        when(pengirimanRepository.save(any(Pengiriman.class))).thenReturn(pengiriman);

        pengirimanService.updatePengiriman(1L, new PengirimanUpdateRequest());

        assertEquals("SEDANG_DIPROSES", pengiriman.getStateString());
        verify(pengirimanRepository, times(1)).findById(1L);
        verify(pengirimanRepository, times(1)).save(pengiriman);
    }

    @Test
    void testUpdatePengirimanMax() {
        when(pengirimanRepository.findById(1L)).thenReturn(Optional.of(pengiriman));
        when(pengirimanRepository.save(any(Pengiriman.class))).thenReturn(pengiriman);

        pengirimanService.updatePengiriman(1L, new PengirimanUpdateRequest());
        pengirimanService.updatePengiriman(1L, new PengirimanUpdateRequest());
        pengirimanService.updatePengiriman(1L, new PengirimanUpdateRequest());


        assertEquals("TELAH_TIBA", pengiriman.getStateString());
        verify(pengirimanRepository, times(3)).findById(1L);
        verify(pengirimanRepository, times(3)).save(pengiriman);
    }
}

