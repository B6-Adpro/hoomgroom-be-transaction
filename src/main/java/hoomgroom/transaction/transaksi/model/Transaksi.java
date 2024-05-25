package hoomgroom.transaction.transaksi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Transaksi {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaksi_id", updatable = false, nullable = false)
    private UUID transaksiId;

    @Column(name = "produk_id", updatable = false, nullable = false)
    private String produkID;

    @Column(name = "nama_produk", updatable = false, nullable = false)
    private String namaProduk;

    @Column(name = "link_image", updatable = false, nullable = false)
    private String linkImage;

    @Column(name = "promo_code", updatable = false, nullable = false)
    private String promoCode;

    @Column(name = "username", updatable = false, nullable = false)
    private String username;

    @Column(name = "original_price", updatable = false, nullable = false)
    private Long originalPrice;

    @Column(name = "discount_price",  updatable = false, nullable = false)
    private Long discountPrice;

    @Column(name = "potongan_promo", updatable = false, nullable = false)
    private Long potonganPromo;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "final_price", updatable = false, nullable = false)
    private Long finalPrice;

    public Transaksi(String username, String produkID, String namaProduk, String linkImage, String promoCode, Long originalPrice, Long discountPrice, Long potonganPromo) {
        this.produkID = produkID;
        this.namaProduk = namaProduk;
        this.linkImage = linkImage;
        this.promoCode = promoCode;
        this.username = username;
        this.originalPrice = originalPrice;
        this.discountPrice = discountPrice;
        this.potonganPromo = potonganPromo;
        this.finalPrice = discountPrice - potonganPromo;
    }
}