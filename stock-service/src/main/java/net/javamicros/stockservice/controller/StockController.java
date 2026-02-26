package net.javamicros.stockservice.controller;

import net.javamicros.basedomains.dto.OrderDb;
import net.javamicros.basedomains.dto.OrderEvent;
import net.javamicros.basedomains.dto.OrderStatus;
import net.javamicros.stockservice.producer.StockProducer;
import net.javamicros.stockservice.service.OrderDbService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
public class StockController {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private final OrderDbService orderDbService;

    private final StockProducer stockProducer;

    public StockController(OrderDbService orderDbService, StockProducer stockProducer) {
        this.orderDbService = orderDbService;
        this.stockProducer = stockProducer;
    }

    @PostMapping("/stock")
    public String stock(@RequestBody OrderDb orderDb) {

        orderDbService.saveOrder(orderDb);
        /*
        * Processing
        * */

        executor.scheduleWithFixedDelay(() -> {
            OrderEvent orderEvent = new OrderEvent();
            orderEvent.setMessage("order status is in pending state");
            orderEvent.setStatus(OrderStatus.SUCCESS);
            /**
             * Send event with final state to kafka for interraction with order-service.
             * */
            stockProducer.sendMessage(orderEvent);
        }, 3, 10, TimeUnit.SECONDS);

        return orderDb.getOrderId();
    }
}
