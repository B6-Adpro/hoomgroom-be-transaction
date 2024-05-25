package hoomgroom.transaction.pengiriman.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class PengirimanData {
    private String id;
    private String transaksiId;
    private String alamat;
    private String furniture;
    private String stateString;
    private String metodeTransportasi;
}