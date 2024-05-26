package hoomgroom.transaction.transaksi.repository;

import hoomgroom.transaction.transaksi.model.Transaksi;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, UUID> {
    Optional<Transaksi> findById(@NonNull UUID transaksiId);

    @Query("SELECT t FROM Transaksi t WHERE t.username = :username ORDER BY t.createdAt DESC")
    List<Transaksi> findByUsernameOrderByTransactionTimeDesc(@Param("username") String username);

    @Query("SELECT t FROM Transaksi t WHERE t.username = :username ORDER BY t.createdAt ASC")
    List<Transaksi> findByUsernameOrderByTransactionTimeAsc(@Param("username") String username);

    @Query("SELECT t FROM Transaksi t WHERE t.username = :username ORDER BY t.finalPrice DESC")
    List<Transaksi> findByUsernameOrderByFinalPriceDesc(@Param("username") String username);

    @Query("SELECT t FROM Transaksi t WHERE t.username = :username ORDER BY t.finalPrice ASC")
    List<Transaksi> findByUsernameOrderByFinalPriceAsc(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("UPDATE Transaksi t SET t.isPaid = TRUE WHERE t.transaksiId = :transaksiId")
    int updateTransaksi(@Param("transaksiId") UUID transaksiId);
}