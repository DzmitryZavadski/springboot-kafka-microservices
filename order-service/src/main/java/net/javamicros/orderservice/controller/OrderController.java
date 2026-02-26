package net.javamicros.orderservice.controller;

import net.javamicros.basedomains.dto.OrderDb;
import net.javamicros.basedomains.dto.OrderStatus;
import net.javamicros.orderservice.kafka.OrderProducer;
import net.javamicros.orderservice.service.OrderDbService;
import net.javamicros.orderservice.service.StockService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderProducer orderProducer;
    private final StockService stockService;
    private final OrderDbService orderDbService;

    public OrderController(OrderProducer orderProducer, StockService stockService, OrderDbService orderDbService) {
        this.orderProducer = orderProducer;
        this.stockService = stockService;
        this.orderDbService = orderDbService;
    }

    @PostMapping("/orders")
    public String placeHolder(@RequestBody OrderDb orderDb) {

        orderDb.setOrderId(UUID.randomUUID().toString());
        orderDb.setOrderStatus(OrderStatus.PENDING);
//        OrderEvent orderEvent = new OrderEvent();
//        orderEvent.setStatus(OrderStatus.PENDING);
//        orderEvent.setMessage("order status is in pending state");
//        orderEvent.setOrder(order);

        orderDbService.addOrder(orderDb);

        // service -> rest client with POST request to stock service
        String stockOrderId = stockService.postOrder(orderDb);

        return stockOrderId;
    }
}
