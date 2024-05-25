package hoomgroom.transaction.Pengiriman.enums;

import hoomgroom.transaction.pengiriman.enums.PengirimanStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PengirimanStatusTest {

    @Test
    public void testPengirimanStatusValues() {
        assertEquals("VERIFIKASI", PengirimanStatus.VERIFIKASI.getValue());
        assertEquals("SEDANG_DIPROSES", PengirimanStatus.SEDANG_DIPROSES.getValue());
        assertEquals("SEDANG_DIKIRIM", PengirimanStatus.SEDANG_DIKIRIM.getValue());
        assertEquals("TELAH_TIBA", PengirimanStatus.TELAH_TIBA.getValue());
        assertEquals("DITERIMA", PengirimanStatus.DITERIMA.getValue());
    }

    @Test
    public void testContains() {
        assertTrue(PengirimanStatus.contains("VERIFIKASI"));
        assertTrue(PengirimanStatus.contains("SEDANG_DIPROSES"));
        assertTrue(PengirimanStatus.contains("SEDANG_DIKIRIM"));
        assertTrue(PengirimanStatus.contains("TELAH_TIBA"));
        assertTrue(PengirimanStatus.contains("DITERIMA"));

        assertFalse(PengirimanStatus.contains("UNKNOWN_STATUS"));
        assertFalse(PengirimanStatus.contains("dalam_proses")); // case sensitivity test
        assertFalse(PengirimanStatus.contains(""));
        assertFalse(PengirimanStatus.contains(null));
    }
}
