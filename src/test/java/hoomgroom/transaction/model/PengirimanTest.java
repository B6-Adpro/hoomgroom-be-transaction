package hoomgroom.transaction.model;

import hoomgroom.transaction.pengiriman.model.Pengiriman;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PengirimanTest {

    @Test
    void testPengirimanCreation() {
        String transaksi = "TRX123";
        String alamatPengiriman = "Jl. Raya No. 123";
        String furniturePengiriman = "Meja";

        Pengiriman pengiriman = new Pengiriman(transaksi, alamatPengiriman, furniturePengiriman);

        assertEquals(transaksi, pengiriman.getTransaksiId());
        assertEquals(alamatPengiriman, pengiriman.getAlamatPengiriman());
        assertEquals(furniturePengiriman, pengiriman.getFurniturePengiriman());
    }

    @Test
    void testPengirimanSetters() {
        String transaksi = "TRX123";
        String alamatPengiriman = "Jl. Raya No. 123";
        String furniturePengiriman = "Meja";
        String newTransaksi = "TRX456";
        String newAlamatPengiriman = "Jl. Baru No. 456";
        String newFurniturePengiriman = "Kursi";

        Pengiriman pengiriman = new Pengiriman(transaksi, alamatPengiriman, furniturePengiriman);

        pengiriman.setTransaksiId(newTransaksi);
        pengiriman.setAlamatPengiriman(newAlamatPengiriman);
        pengiriman.setFurniturePengiriman(newFurniturePengiriman);

        assertEquals(newTransaksi, pengiriman.getTransaksiId());
        assertEquals(newAlamatPengiriman, pengiriman.getAlamatPengiriman());
        assertEquals(newFurniturePengiriman, pengiriman.getFurniturePengiriman());
    }

}