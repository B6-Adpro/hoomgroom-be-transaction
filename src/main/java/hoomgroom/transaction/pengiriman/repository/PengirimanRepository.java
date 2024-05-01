package hoomgroom.transaction.pengiriman.repository;

import hoomgroom.transaction.pengiriman.model.Pengiriman;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PengirimanRepository extends JpaRepository<Pengiriman, String> {

}