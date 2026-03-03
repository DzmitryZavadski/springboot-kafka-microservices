package net.javamicros.stockservice.mapper;

import net.javamicros.basedomains.dto.OrderDbModel;
import net.javamicros.basedomains.dto.OrderEventModel;
import net.javamicros.basedomains.dto.OrderStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class OrderEventMapper {

    public OrderEventModel toOrderEventModel(OrderDbModel orderDbModel) {
        OrderEventModel orderEvent = new OrderEventModel();
        orderEvent.setOrderId(orderDbModel.getOrderId());
        orderEvent.setMessage("order status is in pending state");
        orderEvent.setStatus(OrderStatus.SUCCESS);
        orderEvent.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));

        return orderEvent;
    }
}
