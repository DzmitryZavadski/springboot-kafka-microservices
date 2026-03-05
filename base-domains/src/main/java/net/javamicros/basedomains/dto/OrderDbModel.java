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
    private String orderId;

    private String orderName;
    private int quantity;
    private double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}

//Api model
/**
 * OrderApiModel
 * orderName
 * quantity
 * price
 * */

//Rest client model
/**
 * orderId
 * orderName
 * quantity
 * price
 * */

//Db model
// !!!!! Can not use in controller in production code. NEVER!!!
/**
 * orderId
 * orderName
 * orderStatus
 * quantity
 * price
 * */