package hoomgroom.transaction.pengiriman.service;

import hoomgroom.transaction.pengiriman.dto.PengirimanRequest;
import org.springframework.http.ResponseEntity;


public interface AdminService {
    ResponseEntity<String> createPengiriman(PengirimanRequest pengirimanRequest);
}