package hoomgroom.transaction.pengiriman.enums;

import lombok.Getter;

@Getter
public enum PengirimanStatus {
    VERIFIKASI("VERIFIKASI"),
    SEDANG_DIPROSES("SEDANG_DIPROSES"),
    SEDANG_DIKIRIM("SEDANG_DIKIRIM"),
    TELAH_TIBA("TELAH_TIBA"),
    DITERIMA("DITERIMA");

    private final String value;

    private PengirimanStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (PengirimanStatus pengirimanStatus : PengirimanStatus.values()) {
            if (pengirimanStatus.name().equals(param)) {
                return true;
            }
        }
        return false;
    }
}