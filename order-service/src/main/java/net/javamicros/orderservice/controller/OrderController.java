package net.javamicros.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.javamicros.basedomains.dto.OrderApiModel;
import net.javamicros.basedomains.dto.OrderDbModel;
import net.javamicros.orderservice.mapper.OrderMapper;
import net.javamicros.orderservice.service.OrderDbService;
import net.javamicros.orderservice.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Orders", description = "Methods for work with orders")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final StockService stockService;
    private final OrderDbService orderDbService;
    private final OrderMapper orderMapper;

    public OrderController(StockService stockService, OrderDbService orderDbService, OrderMapper orderMapper) {
        this.stockService = stockService;
        this.orderDbService = orderDbService;
        this.orderMapper = orderMapper;
    }

    @Operation(
            summary = "Make a new Order",
            description = "Receive OrderApiModel, save in Db and send an Event to Kafka"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order placed SUCCESSFULLY",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "INCORRECT entered data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderApiModel orderApiModel) {
        // 1. Маппинг API модели в модель базы данных
        OrderDbModel orderEntity = orderMapper.toOrderDbModel(orderApiModel);

        // 2. Сохранение в базу данных
        orderDbService.addOrder(orderEntity);

        // 3. Отправка события в Kafka через StockService
        String stockOrderId = stockService.createOrderInStock(orderEntity);

        return new ResponseEntity<>("Order created with ID: " + stockOrderId, HttpStatus.CREATED);
    }

    @Operation(summary = "Получить список всех заказов", description = "Возвращает список всех заказов из базы данных")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<OrderApiModel>> getOrders() {
        List<OrderDbModel> orders = orderDbService.getOrders();

        List<OrderApiModel> response = orderMapper.toApiModels(orders);

        return ResponseEntity.ok(response);
    }
}
