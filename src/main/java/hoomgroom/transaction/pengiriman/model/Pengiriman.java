package hoomgroom.transaction.pengiriman.model;

import hoomgroom.transaction.pengiriman.enums.PengirimanStatus;
import hoomgroom.transaction.pengiriman.service.State.PengirimanState;
import hoomgroom.transaction.pengiriman.service.State.ProcessingState;
import jakarta.persistence.*;
import java.util.UUID;

import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Pengiriman")
@Builder
public class Pengiriman {
    @Column(name = "id_transaksi")
    String transaksiId;

    @Id @Column(name = "id_pengiriman")
    String pengirimanId;

    @Column(name = "alamat_pengiriman")
    String alamatPengiriman;

    PengirimanState state;

    @Column(name = "state")
    String stateString;

    @Column(name = "furniture_pengiriman")
    String furniturePengiriman;

    public Pengiriman( String transaksi, String alamatPengiriman, String furniturePengiriman) {
        this.pengirimanId = UUID.randomUUID().toString();
        this.transaksiId = transaksi;
        this.alamatPengiriman = alamatPengiriman;
        this.furniturePengiriman = furniturePengiriman;
        setState(new ProcessingState()); //Untuk state design pattern
        this.stateString = PengirimanStatus.DALAM_PROSES.getValue(); //Untuk dimasukan ke database dalam bentu string


        if (furniturePengiriman.isBlank()) {
            throw new IllegalArgumentException();
        } else {
            this.furniturePengiriman = furniturePengiriman;
        }
    }

    public void setState(PengirimanState state) {
        this.state = state;
    }

    public void pengirimanInfo() {
        state.pengirimanInfo();
    }
}