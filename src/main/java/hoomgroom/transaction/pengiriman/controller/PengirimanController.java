package hoomgroom.transaction.pengiriman.controller;

import java.util.List;

import hoomgroom.transaction.pengiriman.dto.PengirimanData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;


import hoomgroom.transaction.pengiriman.model.Pengiriman;
import hoomgroom.transaction.pengiriman.service.PengirimanService;

@RestController
public class PengirimanController {

    @Autowired
    private PengirimanService pengirimanService;

    @RequestMapping(value = "/pengiriman/view", method = RequestMethod.GET)
    public List<PengirimanData> getAllPengiriman() {
        return pengirimanService.getAllPengiriman();
    }

    @RequestMapping(value = "/pengiriman/{id}", method = RequestMethod.GET)
    public ResponseEntity getPengirimanById(@PathVariable long id) {
        ResponseEntity responseEntity = null;
        try {
            pengirimanService.getPengirimanById(id);
            responseEntity = ResponseEntity.ok().body(pengirimanService.getPengirimanById(id));
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/pengiriman/create", method = RequestMethod.POST)
    public ResponseEntity createPengiriman(@RequestBody Pengiriman pengiriman) {
        ResponseEntity responseEntity = null;
        try {
            pengirimanService.createPengiriman(pengiriman);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Error!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}