package hoomgroom.transaction.pengiriman.controller;

import java.util.HashMap;
import java.util.List;

import hoomgroom.transaction.pengiriman.dto.PengirimanRequest;
import hoomgroom.transaction.pengiriman.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;


import hoomgroom.transaction.pengiriman.model.Pengiriman;
import hoomgroom.transaction.pengiriman.service.PengirimanService;

@RestController
public class PengirimanController {

    @Autowired
    private AdminService service;
    @Autowired
    private PengirimanService pengirimanService;

    @RequestMapping(value = "/pengiriman/create", method = RequestMethod.POST)
    public ResponseEntity<String> createPengiriman(@RequestBody PengirimanRequest pengirimanRequest) {
        return service.createPengiriman(pengirimanRequest);
    }
}