package net.javamicros.orderservice.service;

import lombok.extern.slf4j.Slf4j;
import net.javamicros.basedomains.dto.OrderDbModel;
import net.javamicros.client.api.StockApi;
import net.javamicros.order.dto.OrderApiModel;
import net.javamicros.orderservice.mapper.OrderMapper;
import net.javamicros.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderDbService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final StockApi stockClient;

    public OrderDbService(OrderRepository orderRepository, OrderMapper orderMapper, StockApi stockClient) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.stockClient = stockClient;
    }

    public List<OrderDbModel> getOrders() {
        return orderRepository.findAll();
    }

    public void saveOrder(OrderDbModel orderDbModel) {
        orderRepository.save(orderDbModel);
    }

    public void addOrder(OrderDbModel orderDbModel) {
        saveOrder(orderDbModel);
    }

    public Optional<OrderDbModel> findOrderById(String id) {
        try {
            return orderRepository.findById(Long.valueOf(id));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

//    @Transactional
    public String createOrder(OrderApiModel orderApiModel) {
        try {
            log.info("Creating order for: {}", orderApiModel.getOrderName());

            // 1. Сохранение в локальную БД
            OrderDbModel orderEntity = orderMapper.toOrderDbModel(orderApiModel);
            saveOrder(orderEntity);
            log.info("Order saved locally with ID: {}", orderEntity.getOrderId());

            log.info("Step 2: Sending request to Stock Service...");

            // 2. Вызов внешнего микросервиса Stock
            var clientDto = orderMapper.toClientDto(orderApiModel);
            var response = stockClient.placeOrder(clientDto);

            log.info("Stock service responded: {}", response);
            return orderEntity.getOrderId();

        } catch (Exception e) {
            log.error("CRITICAL ERROR in createOrder: {}", e.getMessage(), e);
            throw e; // Пробрасываем дальше, чтобы транзакция откатилась
        }
    }

    public void updateOrder(OrderDbModel orderDbModel) {
        var potentialOrderToUpdate = findOrderById(orderDbModel.getOrderId());
        if (potentialOrderToUpdate.isPresent()) {
            OrderDbModel updatedOrder = potentialOrderToUpdate.get();
            updatedOrder.setOrderName(orderDbModel.getOrderName());
            updatedOrder.setPrice(orderDbModel.getPrice());
            updatedOrder.setQuantity(orderDbModel.getQuantity());
            updatedOrder.setOrderStatus(orderDbModel.getOrderStatus());

            orderRepository.save(updatedOrder);
        } else {
            orderRepository.save(orderDbModel);
        }
    }
}
