package net.javamicros.orderservice.service;

import net.javamicros.basedomains.dto.OrderDbModel;
import net.javamicros.orderservice.client.StockServiceClient;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private final StockServiceClient stockServiceClient;

    public StockService(StockServiceClient stockServiceClient) {
        this.stockServiceClient = stockServiceClient;
    }

    public String createOrderInStock(OrderDbModel orderDbModel) {
        return stockServiceClient.postOrder(orderDbModel);
    }
}
