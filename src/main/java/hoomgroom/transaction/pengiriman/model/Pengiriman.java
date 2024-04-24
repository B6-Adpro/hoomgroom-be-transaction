package hoomgroom.transaction.pengiriman.model;


import lombok.Getter;
import lombok.Setter;

import hoomgroom.transaction.pengiriman.enums.PengirimanStatus;

@Getter @Setter
public class Pengiriman {
    private String pengirimanId;
    private String alamatPengiriman;
    private String penerimaPengiriman;
    private String pengirimanState;
    private State state;
    private String furniturePengiriman;

    public Pengiriman(String pengirimanId, String alamatPengiriman, String penerimaPengiriman, String furniturePengiriman) {
        this.pengirimanId = pengirimanId;
        this.alamatPengiriman = alamatPengiriman;
        this.penerimaPengiriman = penerimaPengiriman;
        this.furniturePengiriman = furniturePengiriman;
        this.pengirimanState = PengirimanStatus.DALAM_PROSES.getValue();

        if (furniturePengiriman.isBlank()) {
            throw new IllegalArgumentException();
        } else {
            this.furniturePengiriman = furniturePengiriman;
        }
    }    

    public void setStatus(String status) {
        if (pengirimanState.contains(status)) {
            this.pengirimanState = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void changeStatus (State state) {
        this.state = state;
    }

}