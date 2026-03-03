package net.javamicros.orderservice.client;

import net.javamicros.basedomains.dto.OrderDbModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class StockServiceClient {

    private final RestClient restClient;

    public StockServiceClient(@Qualifier("stockRest") RestClient restClient) {
        this.restClient = restClient;
    }

    public String postOrder(OrderDbModel orderDbModel) {
        ResponseEntity<String> response = restClient.post()
                .uri("/stock")
                .contentType(APPLICATION_JSON)
                .body(orderDbModel)
                .retrieve()
                .toEntity(String.class);
        return response.getBody();
    }
}
