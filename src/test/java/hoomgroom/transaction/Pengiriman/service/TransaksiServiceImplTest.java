package hoomgroom.transaction.service;

import hoomgroom.transaction.transaksi.model.Transaksi;
import hoomgroom.transaction.transaksi.service.TransaksiServiceImpl;
import hoomgroom.transaction.transaksi.repository.TransaksiRepository;
import hoomgroom.transaction.transaksi.dto.TransaksiData;
import hoomgroom.transaction.transaksi.dto.RequestTransaksiData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransaksiServiceImplTest {
    @Mock
    private TransaksiRepository transaksiRepository;

    @InjectMocks
    private TransaksiServiceImpl transaksiService;

    List<Transaksi> listTransaksi;

    private Transaksi transaksi;
    private RequestTransaksiData requestTransaksiData;

    @BeforeEach
    void setUp() {
        transaksi = new Transaksi("username", "produkID", "namaProduk", "linkImage", "promoCode", 100L, 50L, 10L);
        transaksi.setTransaksiId(UUID.fromString("b8d05afb-f4ef-4658-a1bd-b0d13e26f624"));

        requestTransaksiData = new RequestTransaksiData();
        requestTransaksiData.setUsername("username");
        requestTransaksiData.setProdukID("produkID");
        requestTransaksiData.setNamaProduk("namaProduk");
        requestTransaksiData.setLinkImage("linkImage");
        requestTransaksiData.setPromoCode("promoCode");
        requestTransaksiData.setOriginalPrice(100L);
        requestTransaksiData.setDiscountPrice(50L);
        requestTransaksiData.setPotonganPromo(10L);
    }

    @Test
    void testCreateTransaksi() {
        when(transaksiRepository.save(any(Transaksi.class))).thenReturn(transaksi);

        TransaksiData createdTransaksi = transaksiService.createTransaksi(requestTransaksiData);

        assertNotNull(createdTransaksi);
        assertEquals(transaksi.getTransaksiId().toString(), createdTransaksi.getTransaksiId());
        verify(transaksiRepository, times(1)).save(any(Transaksi.class));
    }

    @Test
    void testFindAll() {
        List<Transaksi> transaksiList = Arrays.asList(transaksi);
        when(transaksiRepository.findAll()).thenReturn(transaksiList);

        List<TransaksiData> foundTransaksi = transaksiService.findAll();

        assertFalse(foundTransaksi.isEmpty());
        assertEquals(transaksiList.size(), foundTransaksi.size());
        verify(transaksiRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        UUID id = transaksi.getTransaksiId();
        when(transaksiRepository.findById(id)).thenReturn(Optional.of(transaksi));

        TransaksiData foundTransaksi = transaksiService.findById(id);

        assertNotNull(foundTransaksi);
        assertEquals(transaksi.getTransaksiId().toString(), foundTransaksi.getTransaksiId());
        verify(transaksiRepository, times(1)).findById(id);
    }

    @Test
    void testFindById_NotFound() {
        UUID id = UUID.fromString("b8d05afb-f4ef-4658-a1bd-b0d13e26f625");
        when(transaksiRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> transaksiService.findById(id));
        verify(transaksiRepository, times(1)).findById(id);
    }

    @Test
    void testDelete() {
        UUID id = transaksi.getTransaksiId();
        when(transaksiRepository.findById(id)).thenReturn(Optional.of(transaksi));
        doNothing().when(transaksiRepository).delete(transaksi);

        transaksiService.delete(id);

        verify(transaksiRepository, times(1)).findById(id);
        verify(transaksiRepository, times(1)).delete(transaksi);
    }

    @Test
    void testDelete_NotFound() {
        UUID id = UUID.randomUUID();
        when(transaksiRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> transaksiService.delete(id));
        verify(transaksiRepository, times(1)).findById(id);
    }
}
