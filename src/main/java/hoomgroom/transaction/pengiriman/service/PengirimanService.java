package hoomgroom.transaction.pengiriman.service;

import hoomgroom.transaction.pengiriman.enums.PengirimanStatus;
import hoomgroom.transaction.pengiriman.model.Pengiriman;

import hoomgroom.transaction.pengiriman.repository.PengirimanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PengirimanService {
    @Autowired
    private PengirimanRepository pengirimanRepository;
    public Pengiriman createPengiriman(Pengiriman pengiriman){
        Pengiriman newPengiriman = pengiriman.builder()
                .transaksiId(pengiriman.getTransaksiId())
                .alamatPengiriman(pengiriman.getAlamatPengiriman())
                .furniturePengiriman(pengiriman.getFurniturePengiriman())
                .stateString(pengiriman.getStateString())
                .build();
        return pengirimanRepository.save(newPengiriman);
    }
}