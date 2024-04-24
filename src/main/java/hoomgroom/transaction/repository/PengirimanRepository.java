package hoomgroom.transaction.repository;

import hoomgroom.transaction.model.Pengiriman;

import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Repository
public class PengirimanRepository {
    private List<Pengiriman> pengirimanData = new ArrayList<>();

    public Pengiriman create(Pengiriman pengiriman){
        if(pengiriman.getPengirimanState() == null){
            UUID uuid = UUID.randomUUID();
            pengiriman.setPengirimanId(uuid.toString());
        }
        pengirimanData.add(pengiriman);
        return pengiriman;
    }

    public Iterator<Pengiriman> findAll(){
        return pengirimanData.iterator();
    }

    public Pengiriman findById(String id) {
        for (Pengiriman savedPengiriman : pengirimanData) {
            if (savedPengiriman.getPengirimanId().equals(id)) {
                return savedPengiriman;
            }
        }
        return null;
    }

    public void delete(String id) {
        pengirimanData.remove(findById(id));
    }

    public List<Pengiriman> findAllByPenerima(String author) {
        List<Pengiriman> result = new ArrayList<>();
        for (Pengiriman savedPengiriman : pengirimanData) {
            if (savedPengiriman.getPenerimaPengiriman().equals(author)) {
                result.add(savedPengiriman);
            }
        }
        return result;
    }
}