package hoomgroom.transaction.transaksi.service;

import hoomgroom.transaction.transaksi.model.Transaksi;
import org.springframework.stereotype.Service;
import hoomgroom.transaction.transaksi.dto.TransaksiData;
import hoomgroom.transaction.transaksi.dto.RequestTransaksiData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface TransaksiService {
    List<TransaksiData> findAll();
    TransaksiData findById(UUID uuid);
    TransaksiData createTransaksi(RequestTransaksiData transaksiData);
    List<TransaksiData> findByFilter(String username, boolean time, boolean isAscending);
    void delete(UUID uuid);
    void updateTransaksi(UUID transaksiId);
}