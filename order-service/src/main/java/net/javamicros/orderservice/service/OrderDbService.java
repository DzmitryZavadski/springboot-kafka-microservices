package net.javamicros.orderservice.service;

import net.javamicros.basedomains.dto.OrderDbModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDbService {
    private final List<OrderDbModel> orderDbModels;

    public OrderDbService() {
        orderDbModels = new ArrayList<>();
    }

    public List<OrderDbModel> getOrders() {
        return orderDbModels;
    }

    public void addOrder(OrderDbModel orderDbModel) {
        orderDbModels.add(orderDbModel);
    }

    public Optional<OrderDbModel> findOrderById(String id) {
        return orderDbModels.stream().filter(order -> order.getOrderId().equals(id)).findFirst();
    }

    public void updateOrder(OrderDbModel orderDbModel) {
        var potentialOrderToUpdate = findOrderById(orderDbModel.getOrderId());
        if (potentialOrderToUpdate.isPresent()) {
            var updatedOrder = potentialOrderToUpdate.get();
            updatedOrder.setOrderName(orderDbModel.getOrderName());
            updatedOrder.setPrice(orderDbModel.getPrice());
            updatedOrder.setQuantity(orderDbModel.getQuantity());
            updatedOrder.setOrderStatus(orderDbModel.getOrderStatus());

        } else {
            orderDbModels.add(orderDbModel);
        }
    }
}
