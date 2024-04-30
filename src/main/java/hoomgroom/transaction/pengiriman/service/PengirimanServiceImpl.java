package hoomgroom.transaction.pengiriman.service;

import hoomgroom.transaction.pengiriman.model.Pengiriman;
import hoomgroom.transaction.pengiriman.repository.PengirimanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PengirimanServiceImpl implements PengirimanService {
    @Autowired
    private PengirimanRepository PengirimanRepository;

    @Override
    public Pengiriman create(Pengiriman Pengiriman) {
        PengirimanRepository.create(Pengiriman);
        return Pengiriman;
    }

    @Override
    public List<Pengiriman> findAll() {
        Iterator<Pengiriman> PengirimanIterator = PengirimanRepository.findAll();
        List<Pengiriman> allPengiriman = new ArrayList<>();
        PengirimanIterator.forEachRemaining(allPengiriman::add);
        return allPengiriman;
    }

    @Override
    public Pengiriman findById(String PengirimanId){
        Pengiriman Pengiriman = PengirimanRepository.findById(PengirimanId);
        return Pengiriman;
    }

    @Override
    public void delete(String pengirimanId){
        PengirimanRepository.delete(pengirimanId);
    }
}
