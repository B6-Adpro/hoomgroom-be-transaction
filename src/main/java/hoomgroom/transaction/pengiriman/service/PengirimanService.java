package hoomgroom.transaction.pengiriman.service;

import hoomgroom.transaction.pengiriman.model.Pengiriman;

import java.util.List;

public interface PengirimanService {
    public Pengiriman create(Pengiriman pengiriman);

    public List<Pengiriman> findAll();

    Pengiriman findById(String pengirimanId);

    public void delete(String pengirimanId);
}