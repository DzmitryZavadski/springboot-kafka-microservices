package net.javamicros.stockservice.mapper;

import net.javamicros.avro.OrderEvent;
import net.javamicros.avro.OrderStatus;
import net.javamicros.basedomains.dto.OrderDbModel;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class OrderEventMapper {

    public OrderEvent toAvroEvent(OrderDbModel orderDbModel) {

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderId(orderDbModel.getOrderId());
        orderEvent.setMessage("order status is in pending state");
        orderEvent.setStatus(OrderStatus.SUCCESS);
        orderEvent.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC).toInstant());

        return orderEvent;
    }
}
