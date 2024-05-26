package hoomgroom.transaction.transaksi.controller;

import hoomgroom.transaction.Auth.model.Role;
import hoomgroom.transaction.Auth.model.User;
import hoomgroom.transaction.Auth.service.JwtService;
import hoomgroom.transaction.transaksi.dto.RequestTransaksiData;
import hoomgroom.transaction.transaksi.dto.TransaksiData;
import hoomgroom.transaction.transaksi.service.TransaksiService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/transaksi")
public class TransaksiController {
    @Autowired
    JwtService jwtService;

    @Autowired
    private TransaksiService transaksiService;

    private static final String JWT_HEADER = "Authorization";
    private static final String JWT_TOKEN_PREFIX = "Bearer";

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public CompletableFuture<ResponseEntity> getAllTransaksi(@NonNull HttpServletRequest request) {
        final String authHeader = request.getHeader(JWT_HEADER);
        if (authHeader == null) {
            return CompletableFuture.supplyAsync(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"));
        }
        String jwtToken = authHeader.substring(JWT_TOKEN_PREFIX.length());
        User userDetails = jwtService.extractUser(jwtToken);

        String username = userDetails.getUsername();
        Role role = userDetails.getRole();
        if (role == Role.USER) {
            return CompletableFuture.supplyAsync(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"));
        }
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(transaksiService.findAll()));
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public CompletableFuture<ResponseEntity> getTransaksiById(@NonNull HttpServletRequest request, @PathVariable UUID id) {
        final String authHeader = request.getHeader(JWT_HEADER);
        if (authHeader == null) {
            return CompletableFuture.supplyAsync(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"));
        }
        String jwtToken = authHeader.substring(JWT_TOKEN_PREFIX.length());
        User userDetails = jwtService.extractUser(jwtToken);

        String username = userDetails.getUsername();
        Role role = userDetails.getRole();
        return CompletableFuture.supplyAsync(() -> {
            try {
                TransaksiData transaksiData = transaksiService.findById(id);
                if (transaksiData.getUsername().equals(username) || role == Role.ADMIN) {
                    return ResponseEntity.ok().body(transaksiData);
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
            }
        });
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CompletableFuture<ResponseEntity> createTransaksi(@NonNull HttpServletRequest request, @RequestBody RequestTransaksiData transaksiData) {
        final String authHeader = request.getHeader(JWT_HEADER);
        if (authHeader == null) {
            return CompletableFuture.supplyAsync(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"));
        }
        String jwtToken = authHeader.substring(JWT_TOKEN_PREFIX.length());
        User userDetails = jwtService.extractUser(jwtToken);

        String username = userDetails.getUsername();
        transaksiData.setUsername(username);
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
    public CompletableFuture<ResponseEntity> deleteTransaksi(@NonNull HttpServletRequest request, @PathVariable UUID id) {
        final String authHeader = request.getHeader(JWT_HEADER);
        if (authHeader == null) {
            return CompletableFuture.supplyAsync(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"));
        }
        String jwtToken = authHeader.substring(JWT_TOKEN_PREFIX.length());
        User userDetails = jwtService.extractUser(jwtToken);

        String username = userDetails.getUsername();
        Role role = userDetails.getRole();
        return CompletableFuture.supplyAsync(() -> {
            try {
                TransaksiData transaksiData = transaksiService.findById(id);
                if (transaksiData.getUsername().equals(username) || role == Role.ADMIN) {
                    transaksiService.delete(id);
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
                }
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        });
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public CompletableFuture<ResponseEntity> filterTransaksi(
            @NonNull HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "false") boolean time,
            @RequestParam(required = false, defaultValue = "true") boolean isAscending) {
        final String authHeader = request.getHeader(JWT_HEADER);
        if (authHeader == null) {
            return CompletableFuture.supplyAsync(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"));
        }
        String jwtToken = authHeader.substring(JWT_TOKEN_PREFIX.length());
        User userDetails = jwtService.extractUser(jwtToken);

        String username = userDetails.getUsername();
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
