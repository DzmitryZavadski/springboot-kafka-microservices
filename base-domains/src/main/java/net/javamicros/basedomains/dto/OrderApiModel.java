package net.javamicros.basedomains.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class OrderApiModel {
    private String orderName;
    private int quantity;
    private double price;
}
