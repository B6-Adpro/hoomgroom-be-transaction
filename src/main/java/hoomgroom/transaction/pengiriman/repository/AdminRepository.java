package hoomgroom.transaction.pengiriman.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hoomgroom.transaction.pengiriman.dto.PengirimanRequest;
import hoomgroom.transaction.pengiriman.model.Pengiriman;
import hoomgroom.transaction.pengiriman.service.State.PengirimanState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class AdminRepository {
    private RestTemplate restTemplate = new RestTemplate();

    public String createJsonBody(String idPengiriman, String transactionId, String alamat, String furniture, PengirimanState state) {
        return "{\"id\":\"" + idPengiriman + "\", \"transactionId\":\"" + transactionId + "\", \"alamat\":\"" + alamat + "\", \"furniture\":\"" + furniture + "\", \"state\":\"" + state + "\"}";
    }

    public ResponseEntity<String> createPengiriman(PengirimanRequest pengirimanRequest) {
        String url = "http://localhost:8080/pengiriman";
        String requestBody = convertToJson(pengirimanRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    private String convertToJson(PengirimanRequest pengirimanRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(pengirimanRequest);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
