package hoomgroom.transaction.transaksi.repository;

import hoomgroom.transaction.transaksi.model.Transaksi;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, UUID> {
    Optional<Transaksi> findById(@NonNull UUID transaksiId);
}