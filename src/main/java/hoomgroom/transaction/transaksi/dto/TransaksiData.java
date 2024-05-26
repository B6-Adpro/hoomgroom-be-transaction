package hoomgroom.transaction.transaksi.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class TransaksiData {
    private String transaksiId;
    private String produkID;
    private String namaProduk;
    private String linkImage;
    private String promoCode;
    private String username;
    private Long originalPrice;
    private Long discountPrice;
    private Long potonganPromo;
    private LocalDateTime createdAt;
    private Long finalPrice;
    private boolean isPaid;
}
