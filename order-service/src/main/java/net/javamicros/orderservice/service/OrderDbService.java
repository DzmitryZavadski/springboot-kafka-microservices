package net.javamicros.orderservice.service;

import net.javamicros.basedomains.dto.OrderDbModel;
import net.javamicros.orderservice.repository.OrderRepository;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDbService {
    private final OrderRepository orderRepository;

    public OrderDbService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDbModel> getOrders() {
        return orderRepository.findAll();
    }

    public void addOrder(OrderDbModel orderDbModel) {
        orderRepository.save(orderDbModel);
    }

    public Optional<OrderDbModel> findOrderById(String id) {
        return orderRepository.findById(Long.valueOf(id));
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
            orderRepository.save(orderDbModel);
        }
    }
}
