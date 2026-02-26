package net.javamicros.orderservice.service;

import net.javamicros.basedomains.dto.OrderDb;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class StockService {

    private final RestClient restClient;

    public StockService(RestClient restClient) {
        this.restClient = restClient;
    }

    public String postOrder(OrderDb orderDb) {
        ResponseEntity<String> response = restClient.post()
                .uri("/orders")
                .contentType(APPLICATION_JSON)
                .body(orderDb)
                .retrieve()
                .toEntity(String.class);
        return response.getBody();
    }
}
