package hoomgroom.transaction.transaksi.controller;

import hoomgroom.transaction.transaksi.dto.RequestTransaksiData;
import hoomgroom.transaction.transaksi.dto.TransaksiData;
import hoomgroom.transaction.transaksi.service.TransaksiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/transaksi")
public class TransaksiController {

    @Autowired
    private TransaksiService transaksiService;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public CompletableFuture<ResponseEntity<List<TransaksiData>>> getAllTransaksi() {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(transaksiService.findAll()));
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public CompletableFuture<ResponseEntity> getTransaksiById(@PathVariable UUID id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TransaksiData transaksiData = transaksiService.findById(id);
                return ResponseEntity.ok().body(transaksiData);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
            }
        });
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CompletableFuture<ResponseEntity> createTransaksi(@RequestBody RequestTransaksiData transaksiData) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TransaksiData transaksiData1 = transaksiService.createTransaksi(transaksiData);
                return ResponseEntity.ok().body(transaksiData1);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public CompletableFuture<ResponseEntity<Void>> deleteTransaksi(@PathVariable UUID id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                transaksiService.delete(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        });
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public CompletableFuture<ResponseEntity<List<TransaksiData>>> filterTransaksi(
            @RequestParam(required = false) String username,
            @RequestParam(required = false, defaultValue = "false") boolean time,
            @RequestParam(required = false, defaultValue = "true") boolean isAscending) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                List<TransaksiData> filteredTransaksi = transaksiService.findByFilter(username, time, isAscending);
                return ResponseEntity.ok(filteredTransaksi);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        });
    }
}
