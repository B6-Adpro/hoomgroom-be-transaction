package hoomgroom.transaction.pengiriman.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import hoomgroom.transaction.pengiriman.dto.PengirimanData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;


import hoomgroom.transaction.pengiriman.model.Pengiriman;
import hoomgroom.transaction.pengiriman.service.PengirimanService;

@RestController
@EnableAsync
public class PengirimanController {

    @Autowired
    private PengirimanService pengirimanService;

    @Async
    @RequestMapping(value = "/pengiriman/view", method = RequestMethod.GET)
    public CompletableFuture<ResponseEntity<List<PengirimanData>>> getAllPengiriman() {
        List<PengirimanData> pengirimanList = pengirimanService.getAllPengiriman();
        return CompletableFuture.completedFuture(ResponseEntity.ok(pengirimanList));
    }

    @Async
    @RequestMapping(value = "/pengiriman/{id}", method = RequestMethod.GET)
    public CompletableFuture<ResponseEntity> getPengirimanById(@PathVariable long id) {
        ResponseEntity responseEntity = null;
        try {
            pengirimanService.getPengirimanById(id);
            responseEntity = ResponseEntity.ok().body(pengirimanService.getPengirimanById(id));
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
        return CompletableFuture.completedFuture(responseEntity);
    }

    @Async
    @RequestMapping(value = "/pengiriman/create", method = RequestMethod.POST)
    public CompletableFuture<ResponseEntity> createPengiriman(@RequestBody Pengiriman pengiriman) {
        ResponseEntity responseEntity = null;
        try {
            pengirimanService.createPengiriman(pengiriman);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Error!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CompletableFuture.completedFuture(responseEntity);
    }

    @Async
    @RequestMapping(value = "/pengiriman/update/{id}", method = RequestMethod.PUT)
    public CompletableFuture<ResponseEntity> updateStatePengiriman(@PathVariable long id) {
        ResponseEntity responseEntity = null;
        try {
            pengirimanService.updatePengiriman(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
        return CompletableFuture.completedFuture(responseEntity);
    }

    @Async //Belum terimplementasi dengan benar wip
    @RequestMapping(value = "/pengiriman/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePengiriman(@PathVariable Long id) {
        try {
            pengirimanService.deletePengiriman(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}