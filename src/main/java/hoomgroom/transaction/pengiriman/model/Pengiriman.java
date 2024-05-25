package hoomgroom.transaction.pengiriman.model;

import hoomgroom.transaction.pengiriman.enums.PengirimanStatus;

import hoomgroom.transaction.pengiriman.service.State.PengirimanState;
import hoomgroom.transaction.pengiriman.service.State.ProcessingState;
import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Pengiriman")
public class Pengiriman {
    @Column(name = "id_transaksi")
    String transaksiId;


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column(name = "id_pengiriman",updatable = false, nullable = false)
    Long pengirimanId;

    @Column(name = "alamat_pengiriman")
    String alamatPengiriman;

    @Transient @Builder.Default
    PengirimanState state = new ProcessingState();

    @Column(name = "state") @Builder.Default
    String stateString = PengirimanStatus.VERIFIKASI.getValue();

    @Column(name = "furniture_pengiriman", nullable = false)
    String furniturePengiriman;

    @Column(name = "metode_transportasi")
    String metodeTransportasi;

    public Pengiriman( String transaksi, String alamatPengiriman, String furniturePengiriman) {
        this.transaksiId = transaksi;
        this.alamatPengiriman = alamatPengiriman;
        this.furniturePengiriman = furniturePengiriman;
        setState(new ProcessingState()); //Untuk state design pattern


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