package net.javamicros.orderservice.controller;

import net.javamicros.order.api.OrdersApi;
import net.javamicros.order.dto.OrderApiModel;
import net.javamicros.orderservice.mapper.OrderMapper;
import net.javamicros.orderservice.service.OrderDbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController implements OrdersApi {

    private final OrderDbService orderDbService;
    private final OrderMapper orderMapper;

    public OrderController(OrderDbService orderDbService, OrderMapper orderMapper) {
        this.orderDbService = orderDbService;
        this.orderMapper = orderMapper;
    }

    @Override
    public ResponseEntity<String> placeOrder(OrderApiModel orderApiModel) {
        String orderId = orderDbService.createOrder(orderApiModel);
        return new ResponseEntity<>("Order created with ID: " + orderId, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<OrderApiModel>> getOrders() {
        var orders = orderDbService.getOrders();
        return ResponseEntity.ok(orderMapper.toApiModels(orders));
    }
}
