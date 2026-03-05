package net.javamicros.orderservice.repository;

import net.javamicros.basedomains.dto.OrderDbModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderDbModel, Long> {
}
