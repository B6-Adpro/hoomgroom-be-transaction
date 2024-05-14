package hoomgroom.transaction.pengiriman.service;

import hoomgroom.transaction.pengiriman.dto.PengirimanData;
import hoomgroom.transaction.pengiriman.enums.PengirimanStatus;
import hoomgroom.transaction.pengiriman.model.Pengiriman;

import hoomgroom.transaction.pengiriman.repository.PengirimanRepository;
import hoomgroom.transaction.pengiriman.service.State.ArrivedState;
import hoomgroom.transaction.pengiriman.service.State.PackagingState;
import hoomgroom.transaction.pengiriman.service.State.ShippingState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public List<PengirimanData> getAllPengiriman() {
        List<Pengiriman> pengirimanList = pengirimanRepository.findAll();
        return pengirimanList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<PengirimanData> getPengirimanById(Long id) {
        Optional<Pengiriman> pengirimanOptional = pengirimanRepository.findById(id);
        return pengirimanOptional.map(this::convertToDto);
    }

    private PengirimanData convertToDto(Pengiriman pengiriman) {
        PengirimanData pengirimanDTO = new PengirimanData();
        pengirimanDTO.setId(pengiriman.getPengirimanId().toString());
        pengirimanDTO.setTransaksiId(pengiriman.getTransaksiId());
        pengirimanDTO.setAlamat(pengiriman.getAlamatPengiriman());
        pengirimanDTO.setFurniture(pengiriman.getFurniturePengiriman());
        pengirimanDTO.setStateString(pengiriman.getStateString());
        return pengirimanDTO;
    }
}