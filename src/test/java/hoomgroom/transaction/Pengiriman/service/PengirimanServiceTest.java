package hoomgroom.transaction.Pengiriman.service;

import hoomgroom.transaction.pengiriman.dto.PengirimanData;
import hoomgroom.transaction.pengiriman.model.Pengiriman;
import hoomgroom.transaction.pengiriman.repository.PengirimanRepository;
import hoomgroom.transaction.pengiriman.service.PengirimanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PengirimanServiceTest {
    @Mock
    private PengirimanRepository pengirimanRepository;

    @InjectMocks
    private PengirimanService pengirimanService;

    @Test
    void testCreatePengiriman() {
        Pengiriman pengiriman = new Pengiriman();
        pengirimanService.createPengiriman(pengiriman, "");
        verify(pengirimanRepository, times(1)).save(any(Pengiriman.class));
    }

    @Test
    void testGetPengirimanById() {
        Pengiriman pengiriman = new Pengiriman();
        pengiriman.setPengirimanId(1L);
        when(pengirimanRepository.findById(pengiriman.getPengirimanId())).thenReturn(Optional.of(pengiriman));
        Optional<PengirimanData> result = pengirimanService.getPengirimanById(pengiriman.getPengirimanId());
        assertEquals(pengiriman.getPengirimanId().toString(), result.get().getId());
    }
}