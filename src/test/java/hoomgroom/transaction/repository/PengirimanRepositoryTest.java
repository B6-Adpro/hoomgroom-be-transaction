package hoomgroom.transaction.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import hoomgroom.transaction.pengiriman.dto.PengirimanData;
import hoomgroom.transaction.pengiriman.service.PengirimanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import hoomgroom.transaction.pengiriman.model.Pengiriman;
import hoomgroom.transaction.pengiriman.repository.PengirimanRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PengirimanRepositoryTest {

    @Mock
    private PengirimanRepository pengirimanRepository;

    @InjectMocks
    private PengirimanService pengirimanService;

    @Test
    public void testFindAllPengiriman() {
        // Given
        Pengiriman pengiriman1 = new Pengiriman("TRX123", "Jl. Raya 123", "Meja");
        Pengiriman pengiriman2 = new Pengiriman("TRX456", "Jl. Raya 456", "Kursi");
        List<Pengiriman> pengirimanList = Arrays.asList(pengiriman1, pengiriman2);
        when(pengirimanRepository.findAll()).thenReturn(pengirimanList);

        // When
        List<PengirimanData> result = pengirimanService.getAllPengiriman();

        // Then
        assertEquals(2, result.size());
    }
}
