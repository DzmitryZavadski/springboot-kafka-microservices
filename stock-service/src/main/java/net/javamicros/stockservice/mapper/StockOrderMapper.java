package net.javamicros.stockservice.mapper;

import net.javamicros.basedomains.dto.OrderDbModel;
import net.javamicros.stock.dto.OrderApiModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockOrderMapper {

    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderStatus", constant = "PENDING")
    OrderDbModel toEntity(OrderApiModel orderApiModel);
}
