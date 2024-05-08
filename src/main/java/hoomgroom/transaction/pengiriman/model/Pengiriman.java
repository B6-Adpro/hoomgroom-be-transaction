package hoomgroom.transaction.pengiriman.model;

import hoomgroom.transaction.pengiriman.enums.PengirimanStatus;
import hoomgroom.transaction.pengiriman.service.state.PengirimanState;
import hoomgroom.transaction.pengiriman.service.state.ProcessingState;
import hoomgroom.transaction.pengiriman.service.state.PengirimanState;
import jakarta.persistence.*;
import java.util.UUID;

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

    @Transient
    PengirimanState state = new ProcessingState();

    @Column(name = "state")
    String stateString = PengirimanStatus.DALAM_PROSES.getValue();;

    @Column(name = "furniture_pengiriman")
    String furniturePengiriman;



    public void setState(PengirimanState state) {
        this.state = state;
    }

    public void pengirimanInfo() {
        state.pengirimanInfo();
    }
}