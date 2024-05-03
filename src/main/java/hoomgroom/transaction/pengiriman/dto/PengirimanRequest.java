package hoomgroom.transaction.pengiriman.dto;

import hoomgroom.transaction.pengiriman.model.Pengiriman;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PengirimanRequest {
    private String id;
    private String transaksiId;
    private String alamat;
    private String furniture;
    private String stateString;


    public static PengirimanRequest fromPengirimanRequest(Pengiriman pengiriman){

        return PengirimanRequest.builder()
                .id(pengiriman.getPengirimanId())
                .transaksiId(pengiriman.getTransaksiId())
                .alamat(pengiriman.getAlamatPengiriman())
                .furniture(pengiriman.getAlamatPengiriman())
                .stateString(pengiriman.getStateString())
                .build();
    }
}
