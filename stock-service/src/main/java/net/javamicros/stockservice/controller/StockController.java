package net.javamicros.stockservice.controller;

import net.javamicros.basedomains.dto.OrderDbModel;
import net.javamicros.basedomains.dto.OrderEventModel;
import net.javamicros.stockservice.mapper.OrderEventMapper;
import net.javamicros.stockservice.producer.StockProducer;
import net.javamicros.stockservice.service.OrderDbService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
public class StockController {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private final OrderDbService orderDbService;

    private final StockProducer stockProducer;

    private final OrderEventMapper orderEventMapper;

    public StockController(OrderDbService orderDbService, StockProducer stockProducer, OrderEventMapper orderEventMapper) {
        this.orderDbService = orderDbService;
        this.stockProducer = stockProducer;
        this.orderEventMapper = orderEventMapper;
    }

    @PostMapping("/stock")
    public String stock(@RequestBody OrderDbModel orderDbModel) {

        orderDbService.saveOrder(orderDbModel);

        executor.schedule(() -> {
            OrderEventModel orderEvent = orderEventMapper.toOrderEventModel(orderDbModel);
            /**
             * Send event with final state to kafka for interraction with order-service.
             * */
            stockProducer.sendMessage(orderEvent);
        }, 3, TimeUnit.SECONDS);

        return orderDbModel.getOrderId();
    }
}
