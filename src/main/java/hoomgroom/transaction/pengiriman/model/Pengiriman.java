package hoomgroom.transaction.pengiriman.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Pengiriman {
    private String pengirimanId;
    private String alamatPengiriman;
    private String penerimaPengiriman;
    private String pengirimanState;
    private String furniturePengiriman;
    private Date tanggalPengiriman;
}