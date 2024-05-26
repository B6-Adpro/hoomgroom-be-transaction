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
import java.util.UUID;

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
        Transaksi transaksi1 = new Transaksi("DAVID", "ID123", "WOODEN ECO PANEL", "link1", "ADPRO123", 30000L, 4000L, 1000L, "a");
        Transaksi transaksi2 = new Transaksi("JOHN", "ID124", "GALVANIZED SQUARE STEEL", "link3", "ADPRO123", 45000L, 4000L, 2000L, "b");
        Transaksi transaksi3 = new Transaksi("JOHN2", "ID123", "WOODEN ECO PANEL", "link1", "ADPRO123", 30000L, 4000L, 1000L, "c");
        Transaksi transaksi4 = new Transaksi("DAVID", "ID127", "GALVANIZED SQUARE STEEL ONE PACK", "link4", "ADPRO123", 45000L, 10000L, 5000L, "d");
        listTransaksi.add(transaksi1);
        listTransaksi.add(transaksi2);
        listTransaksi.add(transaksi3);
        listTransaksi.add(transaksi4);
    }

    void checkTransaksi(Transaksi transaksi, Transaksi findResult) {
        assertEquals(transaksi.getTransaksiId(), findResult.getTransaksiId());
        assertEquals(transaksi.getProdukID(), findResult.getProdukID());
        assertEquals(transaksi.getNamaProduk(), findResult.getNamaProduk());
        assertEquals(transaksi.getLinkImage(), findResult.getLinkImage());
        assertEquals(transaksi.getPromoCode(), findResult.getPromoCode());
        assertEquals(transaksi.getUsername(), findResult.getUsername());
        assertEquals(transaksi.getOriginalPrice(), findResult.getOriginalPrice());
        assertEquals(transaksi.getDiscountPrice(), findResult.getDiscountPrice());
        assertEquals(transaksi.getPotonganPromo(), findResult.getPotonganPromo());
        assertEquals(transaksi.getIdWallet(), findResult.getIdWallet());
    }

    @Test
    void testSave() {
        Transaksi transaksi = listTransaksi.get(1);

        transaksiRepository.save(transaksi);
        Optional<Transaksi> findResultTransaksi = transaksiRepository.findById(listTransaksi.get(1).getTransaksiId());
        assertTrue(findResultTransaksi.isPresent());

        Transaksi findResult = findResultTransaksi.get();
        checkTransaksi(transaksi, findResult);
    }

    @Test
    public void testFindByIdIfIdFound() {
        transaksiRepository.saveAll(listTransaksi);

        Transaksi transaksi = listTransaksi.get(2);

        Optional<Transaksi> findResultTransaksi = transaksiRepository.findById(listTransaksi.get(2).getTransaksiId());
        assertTrue(findResultTransaksi.isPresent());

        Transaksi findResult = findResultTransaksi.get();
        checkTransaksi(transaksi, findResult);
    }

    @Test
    public void testFindByIdIfIdNotFound() {
        Optional<Transaksi> findResultTransaksi = transaksiRepository.findById(UUID.randomUUID());
        assertTrue(findResultTransaksi.isEmpty());
    }

    @Test
    public void testFindAll() {
        transaksiRepository.saveAll(listTransaksi);
        List<Transaksi> findResult = transaksiRepository.findAll();
        assertEquals(4, findResult.size());
    }

    @Test
    public void testFindByUsernameTimeDesc() {
        transaksiRepository.saveAll(listTransaksi);
        List<Transaksi> findResultList = transaksiRepository.findByUsernameOrderByTransactionTimeDesc("DAVID");
        assertEquals(2, findResultList.size());

        Transaksi transaksi = listTransaksi.get(3);
        Transaksi findResult = findResultList.get(0);
        checkTransaksi(transaksi, findResult);

        transaksi = listTransaksi.get(0);
        findResult = findResultList.get(1);
        checkTransaksi(transaksi, findResult);
    }

    @Test
    public void testFindByUsernameTimeAsc() {
        transaksiRepository.saveAll(listTransaksi);
        List<Transaksi> findResultList = transaksiRepository.findByUsernameOrderByTransactionTimeAsc("DAVID");
        assertEquals(2, findResultList.size());

        Transaksi transaksi = listTransaksi.get(0);
        Transaksi findResult = findResultList.get(0);
        checkTransaksi(transaksi, findResult);

        transaksi = listTransaksi.get(3);
        findResult = findResultList.get(1);
        checkTransaksi(transaksi, findResult);
    }

    @Test
    public void testFindByUsernamePriceAsc() {
        transaksiRepository.saveAll(listTransaksi);
        List<Transaksi> findResultList = transaksiRepository.findByUsernameOrderByFinalPriceAsc("DAVID");
        assertEquals(2, findResultList.size());

        Transaksi transaksi = listTransaksi.get(0);
        Transaksi findResult = findResultList.get(0);
        checkTransaksi(transaksi, findResult);

        transaksi = listTransaksi.get(3);
        findResult = findResultList.get(1);
        checkTransaksi(transaksi, findResult);
    }

    @Test
    public void testFindByUsernamePriceDesc() {
        transaksiRepository.saveAll(listTransaksi);
        List<Transaksi> findResultList = transaksiRepository.findByUsernameOrderByFinalPriceDesc("DAVID");
        assertEquals(2, findResultList.size());

        Transaksi transaksi = listTransaksi.get(3);
        Transaksi findResult = findResultList.get(0);
        checkTransaksi(transaksi, findResult);

        transaksi = listTransaksi.get(0);
        findResult = findResultList.get(1);
        checkTransaksi(transaksi, findResult);
    }

    @Test
    public void testUsernameNotFound() {
        transaksiRepository.saveAll(listTransaksi);
        List<Transaksi> findResultList = transaksiRepository.findByUsernameOrderByFinalPriceAsc("DAVID11");
        assertEquals(0, findResultList.size());

        findResultList = transaksiRepository.findByUsernameOrderByFinalPriceDesc("DAVID11");
        assertEquals(0, findResultList.size());

        findResultList = transaksiRepository.findByUsernameOrderByTransactionTimeAsc("DAVID11");
        assertEquals(0, findResultList.size());

        findResultList = transaksiRepository.findByUsernameOrderByTransactionTimeDesc("DAVID11");
        assertEquals(0, findResultList.size());
    }
}
