package hoomgroom.transaction.service;

import hoomgroom.transaction.transaksi.model.Transaksi;
import hoomgroom.transaction.transaksi.service.TransaksiService;
import hoomgroom.transaction.transaksi.repository.TransaksiRepository;

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
    @InjectMocks
    TransaksiService transaksiService;
    @Mock
    TransaksiRepository transaksiRepository;

    List<Transaksi> listTransaksi;

    @BeforeEach
    void setUp() {
        listTransaksi = new ArrayList<>();
        Transaksi transaksi1 = new Transaksi("DAVID", "ID123", "WOODEN ECO PANEL", "link1", "ADPRO123", 30000L, 4000L, 1000L);
        Transaksi transaksi2 = new Transaksi("JOHN", "ID124", "GALVANIZED SQUARE STEEL", "link3", "ADPRO123", 45000L, 4000L, 2000L);
        Transaksi transaksi3 = new Transaksi("JOHN2", "ID123", "WOODEN ECO PANEL", "link1", "ADPRO123", 30000L, 4000L, 1000L);
        Transaksi transaksi4 = new Transaksi("DAVID", "ID127", "GALVANIZED SQUARE STEEL ONE PACK", "link4", "ADPRO123", 45000L, 10000L, 5000L);
        listTransaksi.add(transaksi1);
        listTransaksi.add(transaksi2);
        listTransaksi.add(transaksi3);
        listTransaksi.add(transaksi4);
    }
}
