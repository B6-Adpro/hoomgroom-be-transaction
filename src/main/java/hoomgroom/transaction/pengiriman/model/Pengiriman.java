package hoomgroom.transaction.pengiriman.model;

import jakarta.persistence.*;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import hoomgroom.transaction.pengiriman.enums.PengirimanStatus;

@Getter @Setter
public class Pengiriman {
    String transaksiId;
    String pengirimanId;
    String alamatPengiriman;
    String penerimaPengiriman;
    String state;
    String furniturePengiriman;
}