package net.javamicros.orderservice.mapper;

import net.javamicros.basedomains.dto.OrderApiModel;
import net.javamicros.basedomains.dto.OrderDbModel;
import net.javamicros.basedomains.dto.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderMapper {

    public OrderDbModel toOrderDbModel(OrderApiModel orderApiModel) {
        OrderDbModel orderDbModel = new OrderDbModel();
        orderDbModel.setOrderId(UUID.randomUUID().toString());
        orderDbModel.setOrderStatus(OrderStatus.PENDING);
        orderDbModel.setOrderName("Order");
        orderDbModel.setQuantity(orderApiModel.getQuantity());
        orderDbModel.setPrice(orderApiModel.getPrice());

        return orderDbModel;
    }
}
