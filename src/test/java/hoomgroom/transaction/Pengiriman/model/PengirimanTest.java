package hoomgroom.transaction.Pengiriman.model;

import hoomgroom.transaction.pengiriman.model.Pengiriman;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PengirimanTest {

    private Pengiriman pengiriman;
    private String transaksi;
    private String alamatPengiriman;
    private String furniturePengiriman;

    @BeforeEach
    void setUp() {
        transaksi = "TRX123";
        alamatPengiriman = "Jl. Raya No. 123";
        furniturePengiriman = "Meja";
        pengiriman = new Pengiriman(transaksi, alamatPengiriman, furniturePengiriman);
    }

    @Test
    void testPengirimanCreation() {
        assertEquals(transaksi, pengiriman.getTransaksiId());
        assertEquals(alamatPengiriman, pengiriman.getAlamatPengiriman());
        assertEquals(furniturePengiriman, pengiriman.getFurniturePengiriman());
    }

    @Test
    void testPengirimanSetters() {
        String newTransaksi = "TRX456";
        String newAlamatPengiriman = "Jl. Baru No. 456";
        String newFurniturePengiriman = "Kursi";

        pengiriman.setTransaksiId(newTransaksi);
        pengiriman.setAlamatPengiriman(newAlamatPengiriman);
        pengiriman.setFurniturePengiriman(newFurniturePengiriman);

        assertEquals(newTransaksi, pengiriman.getTransaksiId());
        assertEquals(newAlamatPengiriman, pengiriman.getAlamatPengiriman());
        assertEquals(newFurniturePengiriman, pengiriman.getFurniturePengiriman());
    }
}
