package hoomgroom.transaction.pengiriman.controller;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import hoomgroom.transaction.Auth.model.Role;
import hoomgroom.transaction.Auth.model.User;
import hoomgroom.transaction.Auth.service.JwtService;
import hoomgroom.transaction.pengiriman.dto.PengirimanData;
import hoomgroom.transaction.pengiriman.dto.PengirimanUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
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
    JwtService jwtService;
    private static final String JWT_HEADER = "Authorization";
    private static final String JWT_TOKEN_PREFIX = "Bearer";

    @Autowired
    private PengirimanService pengirimanService;

    @Async
    @RequestMapping(value = "/pengiriman/view", method = RequestMethod.GET)
    public CompletableFuture<ResponseEntity<List<PengirimanData>>> getAllPengiriman(@NonNull HttpServletRequest request) {
        final String authHeader = request.getHeader(JWT_HEADER);
        if (authHeader == null) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
        String jwtToken = authHeader.substring(JWT_TOKEN_PREFIX.length());
        User userDetails = jwtService.extractUser(jwtToken);

        String username = userDetails.getUsername();
        Role role = userDetails.getRole();
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
    public CompletableFuture<ResponseEntity> createPengiriman(@RequestBody Pengiriman pengiriman, @NonNull HttpServletRequest request) {
        final String authHeader = request.getHeader(JWT_HEADER);
        if (authHeader == null) {
            return CompletableFuture.supplyAsync(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"));
        }
        String jwtToken = authHeader.substring(JWT_TOKEN_PREFIX.length());
        User userDetails = jwtService.extractUser(jwtToken);
        System.out.println(userDetails);
        UUID user = userDetails.getId();
        ResponseEntity responseEntity = null;
        try {
            pengirimanService.createPengiriman(pengiriman, user.toString());
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Error!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CompletableFuture.completedFuture(responseEntity);
    }

    @Async
    @RequestMapping(value = "/pengiriman/update/{id}", method = RequestMethod.PUT)
    public CompletableFuture<ResponseEntity> updateStatePengiriman(@PathVariable Long id,@RequestBody PengirimanUpdateRequest request) {
        ResponseEntity responseEntity = null;
        try {
            pengirimanService.updatePengiriman(id, request);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
        return CompletableFuture.completedFuture(responseEntity);
    }

    @Async
    @RequestMapping(value = "/pengiriman/delete/{id}", method = RequestMethod.DELETE)
    public CompletableFuture<ResponseEntity<Void>> deletePengiriman(@PathVariable Long id) {
        try {
            pengirimanService.deletePengiriman(id);
            return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (RuntimeException e) {
            return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
    }
}