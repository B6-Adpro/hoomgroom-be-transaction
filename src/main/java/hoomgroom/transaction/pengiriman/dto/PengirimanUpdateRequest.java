package hoomgroom.transaction.pengiriman.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PengirimanUpdateRequest {
    private String metodePengiriman;

    public void setMetodePengiriman(String metodePengiriman) {
        this.metodePengiriman = metodePengiriman;
    }
}