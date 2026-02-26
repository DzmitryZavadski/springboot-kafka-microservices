package net.javamicros.stockservice.service;

import net.javamicros.basedomains.dto.OrderDb;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDbService {
    private final List<OrderDb> orderDbs;

    public OrderDbService() {
        orderDbs = new ArrayList<>();
    }

    public List<OrderDb> getOrders() {
        return orderDbs;
    }

    public void saveOrder(OrderDb orderDb) {
        orderDbs.add(orderDb);
    }

    public Optional<OrderDb> findOrderById(String id) {
        return orderDbs.stream().filter(order -> order.getOrderId().equals(id)).findFirst();
    }

    public void updateOrder(OrderDb orderDb) {
        var potentialOrderToUpdate = findOrderById(orderDb.getOrderId());
        if (potentialOrderToUpdate.isPresent()) {
            var updatedOrder = potentialOrderToUpdate.get();
            updatedOrder.setOrderName(orderDb.getOrderName());
            updatedOrder.setPrice(orderDb.getPrice());
            updatedOrder.setQuantity(orderDb.getQuantity());
        } else {
            orderDbs.add(orderDb);
        }
    }


}
