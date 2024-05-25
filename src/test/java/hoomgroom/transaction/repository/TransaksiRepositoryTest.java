package hoomgroom.transaction.repository;

import hoomgroom.transaction.transaksi.model.Transaksi;
import hoomgroom.transaction.transaksi.repository.TransaksiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransaksiRepositoryTest {
    @Autowired
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

    @Test
    void testSave() {
        Transaksi transaksi = listTransaksi.get(1);

        transaksiRepository.save(transaksi);
        Optional<Transaksi> findResultTransaksi = transaksiRepository.findById(listTransaksi.get(1).getTransaksiId());
        assertTrue(findResultTransaksi.isPresent());

        Transaksi findResult = findResultTransaksi.get();
        assertEquals(transaksi.getTransaksiId(), findResult.getTransaksiId());
        assertEquals(transaksi.getProdukID(), findResult.getProdukID());
        assertEquals(transaksi.getNamaProduk(), findResult.getNamaProduk());
        assertEquals(transaksi.getLinkImage(), findResult.getLinkImage());
        assertEquals(transaksi.getPromoCode(), findResult.getPromoCode());
        assertEquals(transaksi.getUsername(), findResult.getUsername());
        assertEquals(transaksi.getOriginalPrice(), findResult.getOriginalPrice());
        assertEquals(transaksi.getDiscountPrice(), findResult.getDiscountPrice());
        assertEquals(transaksi.getPotonganPromo(), findResult.getPotonganPromo());
    }
}
