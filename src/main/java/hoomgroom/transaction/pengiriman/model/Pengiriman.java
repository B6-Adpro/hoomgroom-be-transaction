package hoomgroom.transaction.pengiriman.model;


import jakarta.persistence.*;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import hoomgroom.transaction.pengiriman.enums.PengirimanStatus;

@Getter @Setter @Entity @NoArgsConstructor @Table(name = "Pengiriman")
public class Pengiriman {
    @Column(name = "id_transaksi")
    String transaksiId;

    @Id @Column(name = "id_pengiriman")
    String pengirimanId;

    @Column(name = "alamat_pengiriman")
    String alamatPengiriman;

    @Column(name = "state")
    String state;

    @Column(name = "furniture_pengiriman")
    String furniturePengiriman;

    public Pengiriman(String pengirimanId, String alamatPengiriman, String penerimaPengiriman, String furniturePengiriman, String transaksi) {
        this.pengirimanId = UUID.randomUUID().toString();
        this.transaksiId = transaksi;
        this.alamatPengiriman = alamatPengiriman;
        this.furniturePengiriman = furniturePengiriman;
        this.state = PengirimanStatus.DALAM_PROSES.getValue();

        if (furniturePengiriman.isBlank()) {
            throw new IllegalArgumentException();
        } else {
            this.furniturePengiriman = furniturePengiriman;
        }
    }    
}