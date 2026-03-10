package net.javamicros.stockservice.repository;

import net.javamicros.basedomains.dto.OrderDbModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockOrderRepository extends JpaRepository<OrderDbModel, Long> {
}
