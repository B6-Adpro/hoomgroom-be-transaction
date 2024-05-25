package hoomgroom.transaction.transaksi.repository;

import hoomgroom.transaction.transaksi.model.Transaksi;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, UUID> {
    
}