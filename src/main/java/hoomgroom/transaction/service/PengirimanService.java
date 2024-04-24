package hoomgroom.transaction.service;

import hoomgroom.transaction.model.Pengiriman;

import java.util.List;

public interface PengirimanService {
    public Pengiriman create(Pengiriman pengiriman);

    public List<Pengiriman> findAll();

    Pengiriman findById(String pengirimanId);

    public void delete(String pengirimanId);
}