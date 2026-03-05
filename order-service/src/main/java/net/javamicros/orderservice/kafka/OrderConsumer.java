package net.javamicros.orderservice.kafka;

import net.javamicros.avro.OrderEvent;
import net.javamicros.basedomains.dto.OrderDbModel;
import net.javamicros.basedomains.dto.OrderStatus;
import net.javamicros.orderservice.mapper.OrderMapper;
import net.javamicros.orderservice.service.OrderDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    private final OrderDbService orderDbService;
    private final OrderMapper orderMapper;

    public OrderConsumer(OrderDbService orderDbService, OrderMapper orderMapper) {
        this.orderDbService = orderDbService;
        this.orderMapper = orderMapper;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "stock",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(OrderEvent orderEvent, Acknowledgment ack) {
        log.info("Received order event in Stock-service => {}", orderEvent);

        try {
            // 👉 ТЕСТ DLQ
            if (orderEvent.getStatus().equals(OrderStatus.FAILED)) {
                throw new RuntimeException("Test DLQ - status FAILED");
            }

            // save the order event into the database
            // OrderEventModel -> OrderDbModel
            OrderDbModel orderDbModel = orderMapper.eventToDbModel(orderEvent);

            orderDbService.updateOrder(orderDbModel);
            ack.acknowledge();
        } catch (Exception e) {
            // ТЕСТ DLQ
            log.error("Processing failed", e);
            throw e;
        }
    }
}
