package net.javamicros.orderservice.controller;

import net.javamicros.basedomains.dto.OrderApiModel;
import net.javamicros.basedomains.dto.OrderDbModel;
import net.javamicros.orderservice.mapper.OrderMapper;
import net.javamicros.orderservice.service.OrderDbService;
import net.javamicros.orderservice.service.StockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final StockService stockService;
    private final OrderDbService orderDbService;
    private final OrderMapper orderMapper;

    public OrderController(StockService stockService, OrderDbService orderDbService, OrderMapper orderMapper) {
        this.stockService = stockService;
        this.orderDbService = orderDbService;
        this.orderMapper = orderMapper;
    }

    @PostMapping("/orders")
    public String placeHolder(@RequestBody OrderApiModel orderApiModel) {

        OrderDbModel order = orderMapper.toOrderDbModel(orderApiModel);

        orderDbService.addOrder(order);

        String stockOrderId = stockService.createOrderInStock(order);

        return stockOrderId;
    }

    @GetMapping("/orders")
    public List<OrderDbModel> getOrders() {
        return orderDbService.getOrders();
    }
}
