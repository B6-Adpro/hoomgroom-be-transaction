package hoomgroom.transaction.pengiriman.service;

import hoomgroom.transaction.pengiriman.dto.PengirimanRequest;
import hoomgroom.transaction.pengiriman.repository.AdminRepository;
import hoomgroom.transaction.pengiriman.repository.PengirimanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private PengirimanRepository pengirimanRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public ResponseEntity<String> createPengiriman(PengirimanRequest pengirimanRequest) {
        return adminRepository.createPengiriman(pengirimanRequest);
    }

}
