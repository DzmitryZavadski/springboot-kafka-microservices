package net.javamicros.orderservice.kafka;

import net.javamicros.basedomains.dto.OrderDbModel;
import net.javamicros.basedomains.dto.OrderEventModel;
import net.javamicros.orderservice.service.OrderDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);


    private final OrderDbService orderDbService;

    public OrderConsumer(OrderDbService orderDbService) {
        this.orderDbService = orderDbService;
    }

    /**
     * Add db service and change state to final from kafka event.
     * */


    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
//            groupId = "${spring.kafka.consumer.group-id}",
            groupId = "stock",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(OrderEventModel orderEvent) {
        log.info("Received order event in Stock-service => {}", orderEvent);

        // save the order event into the database
        // OrderEventModel -> OrderDbModel
        OrderDbModel orderDbModel = new OrderDbModel();
        orderDbModel.setOrderId(orderEvent.getOrderId());
        orderDbModel.setOrderStatus(orderEvent.getStatus());

        orderDbService.updateOrder(orderDbModel);

    }

}
