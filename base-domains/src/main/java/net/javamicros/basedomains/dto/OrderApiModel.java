package net.javamicros.basedomains.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class OrderApiModel {
    @Schema(description = "Order name", example = "ORD-123")
    private String orderName;

    @Schema(description = "Orders quantity", example = "2")
    private int quantity;

    @Schema(description = "Price for unit", example = "123.45")
    private double price;
}
