package net.javamicros.stockservice.service;

import lombok.extern.slf4j.Slf4j;
import net.javamicros.avro.OrderEvent;
import net.javamicros.basedomains.dto.OrderDbModel;
import net.javamicros.stock.dto.OrderApiModel;
import net.javamicros.stockservice.mapper.OrderEventMapper;
import net.javamicros.stockservice.mapper.StockOrderMapper;
import net.javamicros.stockservice.producer.StockProducer;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class StockService {
    private final StockOrderMapper orderMapper;
    private final StockOrderDbService stockOrderDbService;
    private final StockProducer stockProducer;
    private final OrderEventMapper orderEventMapper;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public StockService(StockOrderMapper orderMapper,
                        StockOrderDbService stockOrderDbService,
                        StockProducer stockProducer,
                        OrderEventMapper orderEventMapper) {
        this.orderMapper = orderMapper;
        this.stockOrderDbService = stockOrderDbService;
        this.stockProducer = stockProducer;
        this.orderEventMapper = orderEventMapper;
    }

    public String processOrder(OrderApiModel orderApiModel) {
        // 1. Используем локальный маппер
        OrderDbModel orderDbModel = orderMapper.toEntity(orderApiModel);
        orderDbModel.setOrderId(orderApiModel.getOrderId());
        log.info("Mapped Order ID: {}", orderDbModel.getOrderId());
        // 2. Используем локальный сервис БД
        stockOrderDbService.saveOrder(orderDbModel);

        // 3. Отложенная отправка в Kafka
        executor.schedule(() -> {
            OrderEvent orderEvent = orderEventMapper.toAvroEvent(orderDbModel);
            stockProducer.sendMessage(orderEvent);
        }, 3, TimeUnit.SECONDS);

        return orderDbModel.getOrderId();
    }
}
