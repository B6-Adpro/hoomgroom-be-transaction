package hoomgroom.transaction.pengiriman.service;

import hoomgroom.transaction.pengiriman.dto.PengirimanData;
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
    public void createPengiriman(Pengiriman pengiriman){
        Pengiriman newPengiriman = pengiriman.builder()
                .transaksiId(pengiriman.getTransaksiId())
                .alamatPengiriman(pengiriman.getAlamatPengiriman())
                .furniturePengiriman(pengiriman.getFurniturePengiriman())
                .stateString(pengiriman.getStateString())
                .build();
        pengirimanRepository.save(newPengiriman);
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

    public void updatePengiriman(Long id) {
        Optional<Pengiriman> pengirimanOptional = pengirimanRepository.findById(id);
        if (pengirimanOptional.isPresent()) {
        Pengiriman pengiriman = pengirimanOptional.get();
            switch (pengiriman.getStateString()) {
                case "DALAM_PROSES" -> {
                    pengiriman.setState(new PackagingState());
                    pengiriman.setStateString("SEDANG_DIKEMAS");
                }
                case "SEDANG_DIKEMAS" -> {
                    pengiriman.setState(new ShippingState());
                    pengiriman.setStateString("SEDANG_DIKIRIM");
                }
                case "SEDANG_DIKIRIM" -> {
                    pengiriman.setState(new ArrivedState());
                    pengiriman.setStateString("TELAH_TIBA");
                }
            }
            pengirimanRepository.save(pengiriman);
        } else { throw new RuntimeException("Pengiriman with id " + id + " not found"); }
    }

}