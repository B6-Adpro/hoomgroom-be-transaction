package hoomgroom.transaction.model;

import hoomgroom.transaction.pengiriman.model.Transaksi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransaksiTest {
    private String produkID;
    private String namaProduk;
    private String linkImage;
    private String promoCode;
    private Long originalPrice;
    private Long discountPrice;
    private Long potonganPromo;

    @BeforeEach
    void setUp() {
        produkID = "aabbcswdf";
        namaProduk = "Meja IKEA 1 ton untuk anak 50 tahun";
        linkImage = "inilink";
        promoCode = "HOOMGROOMCERIA2209";
        originalPrice = 40000L;
        discountPrice = 30000L;
        potonganPromo = 5000L;
    }

    @Test
    void testTransaksiCreate() {
        Transaksi transaksi = new Transaksi(produkID, namaProduk, linkImage, promoCode, originalPrice, discountPrice, potonganPromo);
        assertEquals("aabbcswdf", transaksi.getProdukID());
        assertEquals("Meja IKEA 1 ton untuk anak 50 tahun", transaksi.getNamaProduk());
        assertEquals("inilink", transaksi.getLinkImage());
        assertEquals("HOOMGROOMCERIA2209", transaksi.getPromoCode());
        assertEquals(40000L, transaksi.getOriginalPrice());
        assertEquals(30000L, transaksi.getDiscountPrice());
        assertEquals(5000L, transaksi.getPotonganPromo());
    }
}