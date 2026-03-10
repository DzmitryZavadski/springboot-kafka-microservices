package net.javamicros.basedomains.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class OrderDbModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    private String orderName;
    private int quantity;
    private double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}