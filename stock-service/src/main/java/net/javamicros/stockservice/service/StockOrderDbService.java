package net.javamicros.stockservice.service;

import net.javamicros.basedomains.dto.OrderDbModel;
import net.javamicros.stockservice.repository.StockOrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockOrderDbService {
    private final StockOrderRepository repository;

    public StockOrderDbService(StockOrderRepository repository) {
        this.repository = repository;
    }

    public void saveOrder(OrderDbModel orderDbModel) {
        repository.save(orderDbModel);
    }

    public Optional<OrderDbModel> findById(String id) {
        return repository.findById(Long.valueOf(id));
    }
}
