package net.javamicros.orderservice.mapper;

import net.javamicros.basedomains.dto.OrderApiModel;
import net.javamicros.basedomains.dto.OrderDbModel;
import net.javamicros.basedomains.dto.OrderEventModel;
import net.javamicros.basedomains.dto.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class, OrderStatus.class})
public interface OrderMapper {

    // Маппинг для Kafka Event
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    OrderEventModel toEvent(OrderApiModel apiModel);

    // Маппинг для БД (замена старого ручного маппера)
    @Mapping(target = "orderId", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "orderStatus", constant = "PENDING")
    @Mapping(target = "orderName", constant = "Order")
    OrderDbModel toOrderDbModel(OrderApiModel apiModel);

    // Добавим маппинг из Event в DB для Consumer
    @Mapping(target = "orderStatus", source = "status")
    OrderDbModel eventToDbModel(OrderEventModel eventModel);
}
