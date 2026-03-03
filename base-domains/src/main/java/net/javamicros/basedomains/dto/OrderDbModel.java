package net.javamicros.basedomains.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class OrderDbModel {
    private String orderId;
    private String orderName;
    private int quantity;
    private double price;
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