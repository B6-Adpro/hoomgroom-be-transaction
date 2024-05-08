package hoomgroom.transaction.pengiriman.model;

import hoomgroom.transaction.pengiriman.enums.PengirimanStatus;

import hoomgroom.transaction.pengiriman.service.state.PengirimanState;
import hoomgroom.transaction.pengiriman.service.state.ProcessingState;
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
    String stateString = PengirimanStatus.DALAM_PROSES.getValue();

    @Column(name = "furniture_pengiriman")
    String furniturePengiriman;

    public Pengiriman( String transaksi, String alamatPengiriman, String furniturePengiriman) {
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