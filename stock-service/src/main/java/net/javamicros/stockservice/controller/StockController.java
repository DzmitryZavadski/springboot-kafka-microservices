package net.javamicros.stockservice.controller;

import net.javamicros.stock.api.StockApi;
import net.javamicros.stock.dto.OrderApiModel;
import net.javamicros.stockservice.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController implements StockApi {
    /**
     * 1 Add mappers from api to db
     */
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @Override
    public ResponseEntity<String> placeOrder(OrderApiModel orderApiModel) {

        String orderId = stockService.processOrder(orderApiModel);

        return ResponseEntity.ok(orderId);
    }
}
