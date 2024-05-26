package hoomgroom.transaction.transaksi.service;

import hoomgroom.transaction.transaksi.repository.TransaksiRepository;
import hoomgroom.transaction.transaksi.model.Transaksi;
import hoomgroom.transaction.transaksi.dto.TransaksiData;
import hoomgroom.transaction.transaksi.dto.RequestTransaksiData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;

@Service
public class TransaksiServiceImpl implements TransaksiService {
    @Autowired
    private TransaksiRepository transaksiRepository;

    public TransaksiData createTransaksi(RequestTransaksiData transaksiData) {
        Transaksi createdTransaksi = convertRequestToTransaksi(transaksiData);
        Transaksi savedTransaksi = transaksiRepository.save(createdTransaksi);
        return convertToDto(savedTransaksi);
    }

    public List<TransaksiData> findAll() {
        List<Transaksi> transaksiList = transaksiRepository.findAll();
        return transaksiList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TransaksiData findById(@NonNull UUID id) throws NoSuchElementException {
        Optional<Transaksi> transaksi = transaksiRepository.findById(id);
        if (transaksi.isEmpty()){
            throw new NoSuchElementException();
        }
        return convertToDto(transaksi.get());
    }

    public List<TransaksiData> findByFilter(String username, boolean time, boolean isAscending) {
        List<Transaksi> transaksiList;
        if (isAscending) {
            if (time) {
                transaksiList = transaksiRepository.findByUsernameOrderByTransactionTimeAsc(username);
            }
            else {
                transaksiList = transaksiRepository.findByUsernameOrderByTransactionTimeDesc(username);
            }
        }
        else {
            if (time) {
                transaksiList = transaksiRepository.findByUsernameOrderByFinalPriceAsc(username);
            }
            else {
                transaksiList = transaksiRepository.findByUsernameOrderByFinalPriceDesc(username);
            }
        }
        return transaksiList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        Optional<Transaksi> transaksi = transaksiRepository.findById(id);

        if (transaksi.isEmpty()) {
            throw new NoSuchElementException();
        }

        transaksiRepository.delete(transaksi.get());
    }

    private Transaksi convertRequestToTransaksi(RequestTransaksiData transaksiData) {
        Transaksi transaksi = new Transaksi(transaksiData.getUsername(), transaksiData.getProdukID(), transaksiData.getNamaProduk(),
                transaksiData.getLinkImage(), transaksiData.getPromoCode(), transaksiData.getOriginalPrice(), transaksiData.getDiscountPrice(),
                transaksiData.getPotonganPromo());
        return transaksi;
    }

    private TransaksiData convertToDto(Transaksi transaksi) {
        TransaksiData transaksiData = new TransaksiData();
        transaksiData.setTransaksiId(transaksi.getTransaksiId().toString());
        transaksiData.setProdukID(transaksi.getProdukID());
        transaksiData.setNamaProduk(transaksi.getNamaProduk());
        transaksiData.setLinkImage(transaksi.getLinkImage());
        transaksiData.setPromoCode(transaksi.getPromoCode());
        transaksiData.setUsername(transaksi.getUsername());
        transaksiData.setOriginalPrice(transaksi.getOriginalPrice());
        transaksiData.setDiscountPrice(transaksi.getDiscountPrice());
        transaksiData.setPotonganPromo(transaksi.getPotonganPromo());
        transaksiData.setFinalPrice(transaksi.getFinalPrice());
        transaksiData.setCreatedAt(transaksi.getCreatedAt());
        return transaksiData;
    }
}
