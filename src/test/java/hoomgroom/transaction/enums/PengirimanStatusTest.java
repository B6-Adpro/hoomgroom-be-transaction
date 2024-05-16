package hoomgroom.transaction.enums;

import hoomgroom.transaction.pengiriman.enums.PengirimanStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PengirimanStatusTest {

    @Test
    public void testPengirimanStatusValues() {
        assertEquals("DALAM_PROSES", PengirimanStatus.DALAM_PROSES.getValue());
        assertEquals("SEDANG_DIKEMAS", PengirimanStatus.SEDANG_DIKEMAS.getValue());
        assertEquals("SEDANG_DIKIRIM", PengirimanStatus.SEDANG_DIKIRIM.getValue());
        assertEquals("TELAH_TIBA", PengirimanStatus.TELAH_TIBA.getValue());
        assertEquals("DITERIMA", PengirimanStatus.DITERIMA.getValue());
    }

    @Test
    public void testContains() {
        assertTrue(PengirimanStatus.contains("DALAM_PROSES"));
        assertTrue(PengirimanStatus.contains("SEDANG_DIKEMAS"));
        assertTrue(PengirimanStatus.contains("SEDANG_DIKIRIM"));
        assertTrue(PengirimanStatus.contains("TELAH_TIBA"));
        assertTrue(PengirimanStatus.contains("DITERIMA"));

        assertFalse(PengirimanStatus.contains("UNKNOWN_STATUS"));
        assertFalse(PengirimanStatus.contains("dalam_proses")); // case sensitivity test
        assertFalse(PengirimanStatus.contains(""));
        assertFalse(PengirimanStatus.contains(null));
    }
}
