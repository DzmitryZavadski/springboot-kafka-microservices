package net.javamicros.stockservice.producer;

import net.javamicros.avro.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class StockProducer {
    private static final Logger log = LoggerFactory.getLogger(StockProducer.class);

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    private final String topicName;

    public StockProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate,
                         @Value("${spring.kafka.topic.name}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    public void sendMessage(OrderEvent orderEvent) {
        log.info(String.format("Sending order event => %s", orderEvent.toString()));

        //create a Message
        Message<OrderEvent> message = MessageBuilder
                .withPayload(orderEvent)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();

        kafkaTemplate.send(message)
                .whenComplete((res, ex) -> {
                    if (ex != null) log.error("Error sending message", ex);
                    else log.info("Message sent successfully: {}", res.getRecordMetadata());
                });
    }
}
