package hoomgroom.transaction.pengiriman.service;

import hoomgroom.transaction.pengiriman.enums.PengirimanStatus;
import hoomgroom.transaction.pengiriman.model.Pengiriman;

import hoomgroom.transaction.pengiriman.repository.PengirimanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PengirimanService {
    @Autowired
    private PengirimanRepository pengirimanRepository;
    Pengiriman createPengiriamn(String transaksiId, String alamat, String furniture){
        Pengiriman pengiriman = new Pengiriman(transaksiId, alamat, furniture);
        return pengirimanRepository.save(pengiriman);
    }
}